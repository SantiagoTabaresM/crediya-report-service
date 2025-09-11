package co.com.pragma.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;


@DynamoDbBean
public class ApprovedLoansEntity {

    private String id;
    private String lastUpdated;
    private Double totalAmount;
    private Integer totalCount;


    public ApprovedLoansEntity() {}

    public ApprovedLoansEntity(String id, String lastUpdated, Double totalAmount, Integer totalCount) {
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.totalAmount = totalAmount;
        this.totalCount = totalCount;
    }


    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("last_updated")
    public String getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @DynamoDbAttribute("total_amount")
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @DynamoDbAttribute("total_count")
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
