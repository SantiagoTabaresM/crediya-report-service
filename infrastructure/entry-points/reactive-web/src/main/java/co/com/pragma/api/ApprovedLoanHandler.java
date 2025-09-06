package co.com.pragma.api;

import co.com.pragma.api.mapper.ApprovedLoanDTOMapper;
import co.com.pragma.usecase.approvedloans.IApprovedLoansUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApprovedLoanHandler {

    private final IApprovedLoansUseCase approvedLoansUseCase;
    private final ApprovedLoanDTOMapper approvedLoanDTOMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> listenGetApprovedLoansCount(ServerRequest serverRequest) {
        return approvedLoansUseCase.getTotalApprovedLoans()
                .map(approvedLoanDTOMapper::toDTO)
                .flatMap(loansCountDTO ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(loansCountDTO)
                );
    }

}
