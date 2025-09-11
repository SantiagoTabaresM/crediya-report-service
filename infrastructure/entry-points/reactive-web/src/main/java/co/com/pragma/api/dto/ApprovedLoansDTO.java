package co.com.pragma.api.dto;

public record ApprovedLoansDTO(
    String id,
    Integer totalCount,
    Double totalAmount,
    String lastUpdated
) {
}
