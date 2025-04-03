package fiddle;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.JaccardSimilarity;

@Slf4j
public class TextSimilarityTest {
  public static void main(String[] args) {
    // Example texts
    List<Pair<String, String>> strPairs = new LinkedList<>();
    strPairs.add(Pair.of("I ate apple yesterday.", "Yesterday, I ate an apple."));
    strPairs.add(Pair.of("I want to visit Nepal and climb mountains.", "I want to visit Nepal."));
    strPairs.add(Pair.of("Teacher is very strict.", "Sir is rude."));
    strPairs.add(Pair.of("He is handsome.", "He is dashing."));
    strPairs.add(Pair.of("He is handsome.", "He ate banana."));
    strPairs.add(Pair.of("Truck is expensive.", "Truck cost a lot."));
    var jaccardSimilarity = new JaccardSimilarity();

    for (var pair : strPairs) {
      log.info(
          "Similarity : [{}, {}] -> {}",
          pair.getLeft(),
          pair.getRight(),
          jaccardSimilarity.apply(pair.getLeft(), pair.getRight()));
    }
  }
}
