package co.com.pragma.periodictasklauncher;


import co.com.pragma.usecase.approvedloans.IApprovedLoansUseCase;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailSchedulerReactive {

    private final SesClient sesClient;
    private final IApprovedLoansUseCase approvedLoansUseCase;


    private static final String REPORT_RECIPIENT = "samoralesta@gmail.com";
    private static final String REPORT_SENDER = "satabaresmo@gmail.com";
    private static final LocalTime RUN_AT = LocalTime.of(19, 32); // 8:00 AM
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @EventListener(ApplicationReadyEvent.class)
    public void scheduleEmail() {
        log.info("Scheduling daily email report task at {}", RUN_AT);

        // Calcula la espera hasta las 08:00 AM
        Duration initialDelay = untilNextRun(RUN_AT);

        Flux.interval(initialDelay, Duration.ofDays(1))
                .flatMap(tick -> generateReport())
                .doOnError(error -> log.error("Unexpected error in scheduled task", error))
                .subscribe(report -> {
                    String subject = "📊 Reporte Diario de Préstamos Aprobados";
                    sendEmail(REPORT_RECIPIENT, subject, report);
                    log.info("Daily report sent successfully at {}", LocalDateTime.now());
                });
    }

    private Duration untilNextRun(LocalTime runAt) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Bogota"));
        LocalDateTime nextRun = now.withHour(runAt.getHour()).withMinute(runAt.getMinute()).withSecond(0);

        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }
        return Duration.between(now, nextRun);
    }

    private Mono<String> generateReport() {
        return approvedLoansUseCase.getTotalApprovedLoans()
                .map(report ->
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                " 📊 Reporte Diario de Préstamos Aprobados\n" +
                                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                                " ✅ Total de préstamos: " + report.getTotalCount() + "\n" +
                                " 💰 Monto aprobado: " + report.getTotalAmount() + "\n" +
                                " 📅 Fecha de generación: " + LocalDateTime.now().format(DATE_FORMATTER) + "\n" +
                                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                )
                .doOnError(e -> log.error("Error generating daily report", e));
    }

    private void sendEmail(String to, String subject, String body) {
        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(d -> d.toAddresses(to))
                .message(m -> m
                        .subject(s -> s
                                .data(subject)
                                .charset("UTF-8"))
                        .body(b -> b
                                .text(t -> t
                                        .data(body)
                                        .charset("UTF-8"))))
                .source(REPORT_SENDER)
                .build();

        sesClient.sendEmail(emailRequest);
    }

}