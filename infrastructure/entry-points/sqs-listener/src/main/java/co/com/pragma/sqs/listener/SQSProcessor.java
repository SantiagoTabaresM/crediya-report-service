package co.com.pragma.sqs.listener;

import co.com.pragma.sqs.listener.dto.ApprovedLoanMessage;
import co.com.pragma.usecase.approvedloans.IApprovedLoansUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSProcessor implements Function<Message, Mono<Void>> {

    private final IApprovedLoansUseCase approvedLoansUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        log.info("Message received: {}", message.body());
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), ApprovedLoanMessage.class))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(pm -> approvedLoansUseCase.incrementApprovedLoans(pm.amount()));
    }
}
