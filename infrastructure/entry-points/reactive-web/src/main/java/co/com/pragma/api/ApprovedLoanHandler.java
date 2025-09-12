package co.com.pragma.api;

import co.com.pragma.api.mapper.ApprovedLoanDTOMapper;
import co.com.pragma.usecase.approvedloans.IApprovedLoansUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Report Approved Loan Application API", description = "Reactive Report Management")
public class ApprovedLoanHandler {

    private final IApprovedLoansUseCase approvedLoansUseCase;
    private final ApprovedLoanDTOMapper approvedLoanDTOMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> listenGetApprovedLoans(ServerRequest serverRequest) {
        log.info("Request received: GET total approved loans");
        return approvedLoansUseCase.getTotalApprovedLoans()
                .map(approvedLoanDTOMapper::toDTO)
                .flatMap(loansCountDTO ->{
                        log.info("Returning response with approved loans data");
                        return  ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(loansCountDTO);
                })
                .doOnError(e -> log.error("Error while retrieving approved loans", e));
    }

}
