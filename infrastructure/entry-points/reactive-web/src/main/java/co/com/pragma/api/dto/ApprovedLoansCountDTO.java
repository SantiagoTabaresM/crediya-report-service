package co.com.pragma.api.dto;

public record ApprovedLoansCountDTO(
    String id,
    Integer totalCount,
    Long totalAmount
) {
}
