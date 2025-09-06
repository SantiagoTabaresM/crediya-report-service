package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.ApprovedLoansCountDTO;
import co.com.pragma.model.approvedloans.ApprovedLoans;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApprovedLoanDTOMapper {

    ApprovedLoans toModel(ApprovedLoansCountDTO approvedLoansDTO);
    ApprovedLoansCountDTO toDTO(ApprovedLoans approvedLoans);
}
