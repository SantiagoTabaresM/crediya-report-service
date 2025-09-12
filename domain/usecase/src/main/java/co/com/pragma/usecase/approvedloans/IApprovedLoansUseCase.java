package co.com.pragma.usecase.approvedloans;

import co.com.pragma.model.approvedloans.ApprovedLoans;
import reactor.core.publisher.Mono;

public interface IApprovedLoansUseCase {

    Mono<ApprovedLoans> getTotalApprovedLoans ();

    Mono<Void> incrementApprovedLoans(Double incrementValue);


}
