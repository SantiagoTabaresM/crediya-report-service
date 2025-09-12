package co.com.pragma.usecase.approvedloans;

import co.com.pragma.model.approvedloans.ApprovedLoans;
import co.com.pragma.model.approvedloans.gateways.ApprovedLoansRepository;
import co.com.pragma.model.approvedloans.gateways.LoggerPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class ApprovedLoansUseCase implements IApprovedLoansUseCase {

    private final ApprovedLoansRepository approvedLoansRepository;
    private final LoggerPort logger;

    @Override
    public Mono<ApprovedLoans> getTotalApprovedLoans() {
        logger.info("Starting getTotalApprovedLoans use case");
        return approvedLoansRepository.getApprovedLoans("APPROVED_REPORT")
                .doOnNext(loans -> logger.debug("Approved loans retrieved:" + loans))
                .doOnError(e -> logger.error("Error while retrieving total approved loans", e));
    }


    @Override
    public Mono<Void> incrementApprovedLoans(Double incrementValue) {
        logger.info("Incrementing approved loans with value: " + incrementValue);

        return approvedLoansRepository.getApprovedLoans("APPROVED_REPORT")
                .doOnSubscribe(sub -> logger.debug("Fetching ApprovedLoans entity from repository..."))
                .flatMap(approvedLoans -> {
                    Double newTotalAmount = approvedLoans.getTotalAmount() + incrementValue;
                    int newCount = approvedLoans.getTotalCount() + 1;

                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowFormatted = sdf.format(now);

                    approvedLoans.setTotalAmount(newTotalAmount);
                    approvedLoans.setTotalCount(newCount);
                    approvedLoans.setLastUpdated(nowFormatted);

                    logger.debug("Updated ApprovedLoans entity: totalAmount="+newTotalAmount+" totalCount="+newCount+ "lastUpdated=" + nowFormatted);
                    return Mono.just(approvedLoans);
                })
                .flatMap(approvedLoansRepository::updateApprovedLoans)
                .doOnSuccess(result -> logger.info("ApprovedLoans entity successfully updated"))
                .doOnError(e -> logger.error("Error while updating ApprovedLoans entity", e))
                .then();
    }

}
