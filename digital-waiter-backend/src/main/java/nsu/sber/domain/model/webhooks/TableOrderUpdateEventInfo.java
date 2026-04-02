package nsu.sber.domain.model.webhooks;

import lombok.Data;

import java.util.List;

@Data
public class TableOrderUpdateEventInfo {
    private String id;
    private String organizationId;
    private Long timestamp;
    private String creationStatus;
    private ErrorInfo errorInfo;
    private Order order;

    @Data
    public static class ErrorInfo {
        private String code;
        private String message;
        private String errorReason;
    }

    @Data
    public static class Order {
        private List<String> tableIds;
        private String status;
        private Double sum;
        private Integer number;
        private GuestsInfo guestsInfo;
        private List<Item> items;
        private OrderType orderType;
        private String terminalGroupId;
        private Double processedPaymentsSum;

        @Data
        public static class GuestsInfo {
            private Integer count;
            private Boolean splitBetweenPersons;
        }

        @Data
        public static class Item {
            private Product product;
            private Double price;
            private Double cost;
            private Boolean pricePredefined;
            private String type;
            private String status;
            private Double amount;

            @Data
            public static class Product {
                private String id;
                private String name;
            }
        }

        @Data
        public static class OrderType {
            private String id;
            private String name;
            private String orderServiceType;
        }
    }
}
