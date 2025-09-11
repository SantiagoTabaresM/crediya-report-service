package co.com.pragma.usecase.approvedloans;

import co.com.pragma.model.approvedloans.ApprovedLoans;
import co.com.pragma.model.approvedloans.gateways.ApprovedLoansRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApprovedLoansUseCase implements IApprovedLoansUseCase {

    private final ApprovedLoansRepository approvedLoansRepository;

    @Override
    public Mono<ApprovedLoans> getTotalApprovedLoans() {
        return approvedLoansRepository.getApprovedLoans("APPROVED_REPORT");
    }


    @Override
    public Mono<Void> incrementApprovedLoans() {
        return null;
    }

}
