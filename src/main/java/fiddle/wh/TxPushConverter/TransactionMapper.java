package fiddle.wh.TxPushConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiddle.utils.ResourceUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import lombok.SneakyThrows;

public class TransactionMapper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @SneakyThrows
  public static TxPushTransaction map(String jsonInput) {
    JsonNode rootNode = objectMapper.readTree(jsonInput);
    JsonNode transactions = rootNode.get("transactions");

    List<TxPushTransaction.TransactionRecord> recordList = new ArrayList<>();

    List<JsonNode> transactionNodes = new ArrayList<>();
    Map<JsonNode, LocalDate> dateMap = new HashMap<>();

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    for (JsonNode t : transactions) {
      String dateStr = t.get("transactionDatetime").asText();
      LocalDate txDate = LocalDateTime.parse(dateStr, formatter).toLocalDate();
      transactionNodes.add(t);
      dateMap.put(t, txDate);
    }
    JsonNode referenceNode = transactionNodes.stream()
            .filter(t -> !dateMap.get(t).isBefore(today))
            .min(Comparator.comparing(d -> Math.abs(ChronoUnit.DAYS.between(today, dateMap.get(d)))))
            .orElse(null);

    LocalDate referenceDate = referenceNode != null ? dateMap.get(referenceNode) : today;


    for (JsonNode t : transactions) {
      TxPushTransaction.TransactionRecord record = new TxPushTransaction.TransactionRecord();

      record.setId(Long.parseLong(t.get("id").asText()) + "");
      record.setAmount(t.get("amount").asDouble());

      // Optionally map a hash or constant for accountId if you can't convert string to long
      record.setAccountId(t.get("accountId").asText().toString()); // or use a lookup map

      record.setCustomerId("9000004569"); // default or infer if needed
      record.setStatus(t.get("status").asText());
      record.setDescription(t.get("merchantName").asText(null));
      record.setMemo(t.get("memo").asText(null));
      record.setType(t.get("type").asText());

      // Convert ISO 8601 to epoch seconds
      record.setTransactionDate(parseIsoToEpoch(t.get("transactionDatetime").asText()));
      record.setCreatedDate(parseIsoToEpoch(t.get("createdDatetime").asText()));
      record.setPostedDate(record.getTransactionDate());

      // Categorization
      TxPushTransaction.TransactionRecord.Categorization cat = new TxPushTransaction.TransactionRecord.Categorization();
      cat.setNormalizedPayeeName(t.get("userDisplayMerchantName").asText(null));
      cat.setCategory(t.get("categoryName").asText(null));
      cat.setBestRepresentation(t.get("whDisplayCategoryName").asText(null));
      cat.setCountry("USA");

      record.setCategorization(cat);
      record.setCheckNum(null);
      record.setOfxCheckNumber(null);

      LocalDate txDate = dateMap.get(t);
      int offset = (int) ChronoUnit.DAYS.between(txDate, referenceDate);
      record.setDateOffset(offset);
      recordList.add(record);
    }

    TxPushTransaction.Event event = new TxPushTransaction.Event();
    event.setClazz("transaction");
    event.setType("created");
    event.setRecords(recordList);

    TxPushTransaction result = new TxPushTransaction();
    result.setEvent(event);
    return result;
  }

  private static long parseIsoToEpoch(String iso) {
    return java.time.LocalDateTime
            .parse(iso, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .atZone(java.time.ZoneId.systemDefault()) // or use a fixed zone
            .toEpochSecond();
  }

  @SneakyThrows
  public static void main(String[] args) {
    var json = ResourceUtils.readResourceAsString("wh/transaction.json");
    TxPushTransaction map = TransactionMapper.map(json);
    ResourceUtils.writeStringToFile("out/tx_trans.json", objectMapper.writeValueAsString(map));
  }
}
