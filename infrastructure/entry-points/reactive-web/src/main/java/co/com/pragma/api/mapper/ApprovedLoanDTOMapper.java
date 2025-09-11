package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.ApprovedLoansDTO;
import co.com.pragma.model.approvedloans.ApprovedLoans;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApprovedLoanDTOMapper {

    ApprovedLoans toModel(ApprovedLoansDTO approvedLoansDTO);
    ApprovedLoansDTO toDTO(ApprovedLoans approvedLoans);
}
