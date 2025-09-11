package co.com.pragma.usecase.approvedloans;

import co.com.pragma.model.approvedloans.ApprovedLoans;
import co.com.pragma.model.approvedloans.gateways.ApprovedLoansRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class ApprovedLoansUseCase implements IApprovedLoansUseCase {

    private final ApprovedLoansRepository approvedLoansRepository;

    @Override
    public Mono<ApprovedLoans> getTotalApprovedLoans() {
        return approvedLoansRepository.getApprovedLoans("APPROVED_REPORT");
    }


    @Override
    public Mono<Void> incrementApprovedLoans(Double incrementValue) {
        return approvedLoansRepository.getApprovedLoans("APPROVED_REPORT")
                .flatMap(approvedLoans -> {
                    approvedLoans.setTotalAmount(approvedLoans.getTotalAmount() + incrementValue);
                    approvedLoans.setTotalCount(approvedLoans.getTotalCount()+ 1);
                    Date now = new Date(); // fecha actual
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowFormatted = sdf.format(now);
                    approvedLoans.setLastUpdated(nowFormatted);

                    return Mono.just(approvedLoans);
                })
                .flatMap(approvedLoansRepository::updateApprovedLoans)
                .then();
    }

}
