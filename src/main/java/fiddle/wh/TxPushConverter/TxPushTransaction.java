package fiddle.wh.TxPushConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TxPushTransaction {
    private Event event;

    @Data
    public static class Event {
        @JsonProperty("class")
        private String clazz;
        private String type;
        private List<TransactionRecord> records;
    }

    @Data
    public static class TransactionRecord {
        private String id;
        private double amount;
        private String accountId;
        private String customerId;
        private String status;
        private String description;
        private String checkNum;
        private String memo;
        private String type;
        private long postedDate;
        private long transactionDate;
        private long createdDate;
        private Categorization categorization;
        private String ofxCheckNumber;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer dateOffset;
        @Data
        public static class Categorization {
            private String normalizedPayeeName;
            private String category;
            private String bestRepresentation;
            private String country;
        }
    }
}
