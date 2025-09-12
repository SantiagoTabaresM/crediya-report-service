package co.com.pragma.usecase.approvedloans;

import co.com.pragma.model.approvedloans.ApprovedLoans;
import co.com.pragma.model.approvedloans.gateways.ApprovedLoansRepository;
import co.com.pragma.model.approvedloans.gateways.LoggerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApprovedLoansUseCaseTest {

    @Mock
    private ApprovedLoansRepository approvedLoansRepository;

    @Mock
    private LoggerPort logger;

    @InjectMocks
    private ApprovedLoansUseCase useCase;

    private ApprovedLoans approvedLoans;

    @BeforeEach
    void setUp() {
        approvedLoans = new ApprovedLoans();
        approvedLoans.setId("APPROVED_REPORT");
        approvedLoans.setTotalAmount(1000.0);
        approvedLoans.setTotalCount(5);
        approvedLoans.setLastUpdated("2025-09-10 12:00:00");
    }

    @Test
    void getTotalApprovedLoans_ShouldReturnApprovedLoans() {
        when(approvedLoansRepository.getApprovedLoans("APPROVED_REPORT"))
                .thenReturn(Mono.just(approvedLoans));

        StepVerifier.create(useCase.getTotalApprovedLoans())
                .expectNextMatches(result ->
                        result.getId().equals("APPROVED_REPORT") &&
                                result.getTotalAmount().equals(1000.0) &&
                                result.getTotalCount() == 5
                )
                .verifyComplete();

        verify(approvedLoansRepository, times(1)).getApprovedLoans("APPROVED_REPORT");
        verify(logger).info("Starting getTotalApprovedLoans use case");
        verify(logger).debug("Approved loans retrieved:" + approvedLoans);
    }

    @Test
    void incrementApprovedLoans_ShouldUpdateApprovedLoans() {
        when(approvedLoansRepository.getApprovedLoans("APPROVED_REPORT"))
                .thenReturn(Mono.just(approvedLoans));
        when(approvedLoansRepository.updateApprovedLoans(any(ApprovedLoans.class)))
                .thenReturn(Mono.just(approvedLoans));

        StepVerifier.create(useCase.incrementApprovedLoans(500.0))
                .verifyComplete();

        verify(approvedLoansRepository, times(1)).getApprovedLoans("APPROVED_REPORT");
        verify(approvedLoansRepository, times(1)).updateApprovedLoans(any(ApprovedLoans.class));
        verify(logger).info("Incrementing approved loans with value: 500.0");
        verify(logger).info("ApprovedLoans entity successfully updated");
    }

    @Test
    void incrementApprovedLoans_ShouldHandleRepositoryError() {
        when(approvedLoansRepository.getApprovedLoans("APPROVED_REPORT"))
                .thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(useCase.incrementApprovedLoans(200.0))
                .expectError(RuntimeException.class)
                .verify();

        verify(logger).info("Incrementing approved loans with value: 200.0");
        verify(logger).debug("Fetching ApprovedLoans entity from repository...");
        verify(logger).error(eq("Error while updating ApprovedLoans entity"), any(RuntimeException.class));
    }
}