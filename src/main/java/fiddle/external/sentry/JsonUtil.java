package fiddle.external.sentry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

public class JsonUtil {
  private static ObjectMapper MAPPER;
  private static ObjectWriter WRITER;

  static {
    initializeMapper();
  }

  @SuppressWarnings("deprecation")
  private static void initializeMapper() {
    MAPPER = new ObjectMapper();
    MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    //    MAPPER.getSerializerProvider().setNullKeySerializer(new IncludeNullKeySerializer());
    //    MAPPER.registerModule(new JavaTimeModule());
    WRITER = MAPPER.writer();
  }

  public static JsonNode readTree(String json) throws JsonProcessingException {
    return MAPPER.readTree(json);
  }

  @SneakyThrows
  public static String toJson(Object object) {
    return WRITER.writeValueAsString(object);
  }
}
