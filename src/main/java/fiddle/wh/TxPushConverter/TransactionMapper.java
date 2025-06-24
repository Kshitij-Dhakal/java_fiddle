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
import org.apache.commons.lang3.StringUtils;

public class TransactionMapper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @SneakyThrows
  public static TxPushTransaction map(String jsonInput) {
    var rootNode = objectMapper.readTree(jsonInput);
    var transactions = rootNode.get("transactions");

    List<TxPushTransaction.TransactionRecord> recordList = new ArrayList<>();

    List<JsonNode> transactionNodes = new ArrayList<>();
    Map<JsonNode, LocalDate> dateMap = new HashMap<>();

    var today = LocalDate.now();
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    for (var t : transactions) {
      var dateStr = t.get("transactionDatetime").asText();
      var txDate = LocalDateTime.parse(dateStr, formatter).toLocalDate();
      transactionNodes.add(t);
      dateMap.put(t, txDate);
    }
    var referenceNode =
        transactionNodes.stream()
            .filter(t -> !dateMap.get(t).isBefore(today))
            .min(
                Comparator.comparing(d -> Math.abs(ChronoUnit.DAYS.between(today, dateMap.get(d)))))
            .orElse(null);

    var referenceDate = referenceNode != null ? dateMap.get(referenceNode) : today;

    for (var t : transactions) {
      var record = new TxPushTransaction.TransactionRecord();

      record.setId(Long.parseLong(t.get("id").asText()) + "");
      var amount = t.get("amount").asDouble();
      var income = t.has("income") && t.get("income").asBoolean(false);
      record.setAmount(income ? Math.abs(amount) : -Math.abs(amount));

      // Optionally map a hash or constant for accountId if you can't convert string to long
      // ! record.setAccountId(t.get("accountId").asText().toString()); // or use a lookup map
      record.setAccountId("7019762892");

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
      var cat = new TxPushTransaction.TransactionRecord.Categorization();
      cat.setNormalizedPayeeName(t.get("userDisplayMerchantName").asText(null));
      var categoryName = t.get("categoryName").asText(null);
      cat.setCategory(StringUtils.replace(categoryName, "Other - ", ""));
      cat.setBestRepresentation(t.get("whDisplayCategoryName").asText(null));
      cat.setCountry("USA");

      record.setCategorization(cat);
      record.setCheckNum(null);
      record.setOfxCheckNumber(null);

      var txDate = dateMap.get(t);
      var offset = (int) ChronoUnit.DAYS.between(txDate, referenceDate);
      record.setDateOffset(offset);
      recordList.add(record);
    }

    var event = new TxPushTransaction.Event();
    event.setClazz("transaction");
    event.setType("created");
    event.setRecords(recordList);

    var result = new TxPushTransaction();
    result.setEvent(event);
    return result;
  }

  private static long parseIsoToEpoch(String iso) {
    return java.time.LocalDateTime.parse(
            iso, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .atZone(java.time.ZoneId.systemDefault()) // or use a fixed zone
        .toEpochSecond();
  }

  @SneakyThrows
  public static void main(String[] args) {
    var json = ResourceUtils.readResourceAsString("wh/transaction.json");
    var map = TransactionMapper.map(json);
    ResourceUtils.writeStringToFile(
        "out/tx_trans.json", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
  }
}
