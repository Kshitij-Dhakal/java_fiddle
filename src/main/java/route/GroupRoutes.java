package route;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GroupRoutes {
    public static void main(String[] args) {
        var csv = loadCsv("source.csv");
        var routes = csvToRoutes(csv);
        var groupedRoutes = groupRoutes(routes);
        writeToFile("out.conf", format(groupedRoutes).getBytes());
    }

    private static String format(List<RouteDetail> groupedRoutes) {
        var format = """
                location %s {
                    proxy_pass http://%s_server;
                }
                """;
        return groupedRoutes
                .stream()
                .map(it -> String.format(format, it.route().trim(), it.handler().trim()))
                .collect(Collectors.joining("\n\n"));
    }

    @SneakyThrows
    private static void writeToFile(String out, byte[] bytes) {
        Files.write(bytes, new File(out));
    }

    public static List<RouteDetail> csvToRoutes(String csv) {
        var routes = new ArrayList<RouteDetail>();
        for (var line : csv.split("\n")) {
            var split = line.split(",");
            var route = sanitizePath(split[1]);
            var handler = split[2];
            routes.add(new RouteDetail(route, handler));
        }
        return routes;
    }

    private static String sanitizePath(String route) {
        var path = route.trim().split("/");
        var i = 0;
        var paths = new LinkedList<String>();
        for (var s : path) {
            if (s.startsWith(":")) {
                paths.add(":id" + i);
            } else {
                paths.add(s);
            }
        }
        return String.join("/", paths);
    }

    private static List<RouteDetail> groupRoutes(List<RouteDetail> routes) {
        var firstPartMap = new HashMap<String, RouteDetail>(); //first part -> handlers
        routes.stream()
                .collect(Collectors.groupingBy(GroupRoutes::firstPart))
                .forEach((path, list) -> {
                    var handlers = list.stream()
                            .map(RouteDetail::handler)
                            .collect(Collectors.toSet());
                    if (handlers.size() == 1) {
                        firstPartMap.put(path, new RouteDetail(path, list.get(0).handler()));
                    }
                });
        log.info("First part routes : {}", firstPartMap.size());

        var first2PartMap = new HashMap<String, RouteDetail>();
        routes.stream()
                .filter(it -> {
                    var firstPart = firstPart(it);
                    return !firstPartMap.containsKey(firstPart);
                })
                .collect(Collectors.groupingBy(GroupRoutes::first2Part))
                .forEach((path, list) -> {
                    var handlers = list.stream()
                            .map(RouteDetail::handler)
                            .collect(Collectors.toSet());
                    if (handlers.size() == 1) {
                        first2PartMap.put(path, new RouteDetail(path, list.get(0).handler()));
                    }
                });
        log.info("First 2 part routes : {}", first2PartMap.size());

        var first3PartMap = new HashMap<String, RouteDetail>();
        routes.stream()
                .filter(it -> {
                    var firstPart = firstPart(it);
                    return !firstPartMap.containsKey(firstPart);
                })
                .filter(it -> {
                    var first2Part = first2Part(it);
                    return !first2PartMap.containsKey(first2Part);
                })
                .collect(Collectors.groupingBy(GroupRoutes::first3Part))
                .forEach((path, list) -> {
                    var handlers = list.stream()
                            .map(RouteDetail::handler)
                            .collect(Collectors.toSet());
                    if (handlers.size() == 1) {
                        first3PartMap.put(path, new RouteDetail(path, list.get(0).handler()));
                    }
                });
        log.info("First 3 part routes : {}", first3PartMap.size());

        var remainingMap = new HashMap<String, RouteDetail>();
        routes.stream()
                .filter(it -> {
                    var firstPart = firstPart(it);
                    return !firstPartMap.containsKey(firstPart);
                })
                .filter(it -> {
                    var first2Part = first2Part(it);
                    return !first2PartMap.containsKey(first2Part);
                })
                .filter(it -> {
                    var first3Part = first3Part(it);
                    return !first3PartMap.containsKey(first3Part);
                })
                .collect(Collectors.groupingBy(RouteDetail::route))
                .forEach((path, list) -> remainingMap.put(path, list.get(0)));
        log.info("Remaining routes : {}", remainingMap.size());

        var convertedRoutes = new HashMap<String, RouteDetail>();
        convertedRoutes.putAll(firstPartMap);
        convertedRoutes.putAll(first2PartMap);
        convertedRoutes.putAll(first3PartMap);
        convertedRoutes.putAll(remainingMap);
        //this final step of filtering is necessary because
        //some routes like /conversation/:id0 isn't added in first2Part because similar routes point to multiple APIs
        //but is later added in first3Map or remaining
        var filteredRoutes = new HashMap<String, RouteDetail>();
        convertedRoutes
                .values()
                .stream()
                .sorted(Comparator.comparingInt(o -> o.route().split("/").length))
                .forEach(it -> {
                    var matchingFirstPartExists = filteredRoutes.containsKey(firstPart(it)) &&
                            filteredRoutes.get(firstPart(it)).handler().equals(it.handler());
                    var matchingFirst2PartExists = filteredRoutes.containsKey(first2Part(it)) &&
                            filteredRoutes.get(first2Part(it)).handler().equals(it.handler());
                    var matchingFirst3PartExists = filteredRoutes.containsKey(first3Part(it)) &&
                            filteredRoutes.get(first3Part(it)).handler().equals(it.handler());
                    if (!matchingFirstPartExists && !matchingFirst2PartExists && !matchingFirst3PartExists) {
                        filteredRoutes.put(it.route(), it);
                    }
                });
        return new ArrayList<>(filteredRoutes.values())
                .stream()
                .sorted((o1, o2) -> Integer
                        .compare(o2.route().split("/").length, o1.route().split("/").length))
                .toList();
    }

    private static String firstPart(RouteDetail it) {
        return firstNPart(it, 2);
    }

    private static String first2Part(RouteDetail it) {
        return firstNPart(it, 3);
    }

    private static String first3Part(RouteDetail it) {
        return firstNPart(it, 4);
    }

    private static String firstNPart(RouteDetail it, int maxSize) {
        return Arrays.stream(it.route().split("/"))
                .limit(maxSize).collect(Collectors.joining("/"));
    }

    @SneakyThrows
    public static String loadCsv(String in) {
        var resource = Resources.getResource(in);
        return Resources.toString(resource, Charset.defaultCharset());
    }
}
