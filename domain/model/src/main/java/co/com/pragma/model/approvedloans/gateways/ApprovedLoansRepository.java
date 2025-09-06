package co.com.pragma.model.approvedloans.gateways;

import co.com.pragma.model.approvedloans.ApprovedLoans;
import reactor.core.publisher.Mono;

public interface ApprovedLoansRepository {

    Mono<ApprovedLoans> getApprovedLoans (String id);

   // Mono<Void> incrementApprovedLoans();


}
