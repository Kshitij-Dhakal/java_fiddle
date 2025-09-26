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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class TransactionMapper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @SneakyThrows
  public static TxPushTransaction map(String jsonInput) {
    var rootNode = objectMapper.readTree(jsonInput);
    var transactions = rootNode.get("transactions");

    List<TxPushTransaction.TransactionRecord> recordList = new ArrayList<>();

    List<JsonNode> transactionNodes = new ArrayList<>();
    Map<String, LocalDate> dateMap = new HashMap<>();

    var today = LocalDate.now();
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    for (var t : transactions) {
      var dateStr = t.get("transactionDatetime").asText();
      var txDate = LocalDateTime.parse(dateStr, formatter).toLocalDate();
      transactionNodes.add(t);
      dateMap.put(t.get("id").asText(), txDate);
    }
    var referenceNode =
        transactionNodes.stream()
            .map(t -> dateMap.get(t.get("id").asText()))
            .peek(e -> log.info("Mapped to date : {}", e))
            .filter(t -> t.isBefore(today))
            .peek(e -> log.info("After filter : {}", e))
            .min(Comparator.comparing(d -> Math.abs(ChronoUnit.DAYS.between(today, d))))
            .orElse(null);

    var referenceDate = referenceNode != null ? referenceNode : today;

    for (var t : transactions) {
      var transaction = new TxPushTransaction.TransactionRecord();
      transaction.setId(t.get("id").asText());
      log.info("Id : {}", transaction.getId());
      var amount = t.get("amount").asDouble();
      var income = t.has("income") && t.get("income").asBoolean(false);
      transaction.setAmount(income ? Math.abs(amount) : -Math.abs(amount));

      // Optionally map a hash or constant for accountId if you can't convert string to long
      // ! record.setAccountId(t.get("accountId").asText().toString()); // or use a lookup map
      transaction.setAccountId("7019762892");

      transaction.setCustomerId("9000004569"); // default or infer if needed
      var status = t.get("status").asText();
      //      var ignored = t.get("ignored").asBoolean();
      //      log.info("Status : {}, Ignored : {}", status, ignored);
      //      if (ignored) {
      //        transaction.setStatus("ignored");
      //      } else {
      //      }
      transaction.setStatus(status);
      transaction.setDescription(t.get("merchantName").asText(null));
      transaction.setMemo(t.get("memo").asText(null));
      transaction.setType(t.get("type").asText());

      // Convert ISO 8601 to epoch seconds
      var transactionDatetime = t.get("transactionDatetime").asText();
      transaction.setTransactionDate(parseIsoToEpoch(transactionDatetime));
      var createdDatetime = t.get("createdDatetime");
      var createdDateTimeExtracted = createdDatetime != null ? createdDatetime.asText() : "";
      log.info(
          "Created date time : {}, {}, {}, {}",
          createdDatetime,
          createdDatetime != null,
          createdDateTimeExtracted,
          !StringUtils.equalsIgnoreCase(createdDateTimeExtracted, "null"));
      transaction.setCreatedDate(
          parseIsoToEpoch(
              createdDatetime != null
                      && createdDateTimeExtracted != null
                      && !StringUtils.equalsIgnoreCase(createdDateTimeExtracted, "null")
                  ? createdDatetime.asText()
                  : transactionDatetime));
      transaction.setPostedDate(transaction.getTransactionDate());

      // Categorization
      var cat = new TxPushTransaction.TransactionRecord.Categorization();
      cat.setNormalizedPayeeName(t.get("userDisplayMerchantName").asText(null));
      // var categoryName = t.get("categoryName").asText(null);
      // cat.setCategory(StringUtils.replace(categoryName, "Other - ", ""));
      cat.setCategory(t.get("parentCategoryName").asText());
      cat.setBestRepresentation(t.get("whDisplayCategoryName").asText(null));
      cat.setCountry("USA");

      transaction.setCategorization(cat);
      transaction.setCheckNum(null);
      transaction.setOfxCheckNumber(null);
      //      transaction.setPriority(t.get("priority").asText());

      var txDate = dateMap.get(transaction.getId());
      var offset = (int) ChronoUnit.DAYS.between(txDate, referenceDate);
      transaction.setDateOffset(offset);
      recordList.add(transaction);
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
