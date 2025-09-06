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
        return approvedLoansRepository.getApprovedLoans("REPORT");
    }

    @Override
    public Mono<ApprovedLoans> getTotalApprovedAmount() {
        return null;
    }

    @Override
    public Mono<Void> incrementApprovedLoansCount() {
        return null;
    }

    @Override
    public Mono<Void> incrementApprovedAmount(Long amount) {
        return null;
    }
}
