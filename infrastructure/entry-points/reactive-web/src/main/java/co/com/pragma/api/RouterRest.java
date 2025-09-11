package co.com.pragma.api;

import co.com.pragma.api.config.ApprovedLoansPath;
import co.com.pragma.api.dto.ApprovedLoansDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;

import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final ApprovedLoansPath approvedLoansPath;
    private final ApprovedLoanHandler approvedLoanHandler;
    private static final String LOAN_APPLICATION_COUNT = "/api/v1/report/count";

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = LOAN_APPLICATION_COUNT,
                    method = {RequestMethod.GET},
                    beanClass = ApprovedLoanHandler.class,
                    beanMethod = "listenGetApprovedLoans",
                    operation = @Operation(
                            operationId = "getTotalApprovedLoans",
                            summary = "Get report of count of approved loan applications",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Report of count of approved loans",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApprovedLoansDTO.class))))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(ApprovedLoanHandler approvedLoanHandler ) {
        return route(GET(approvedLoansPath.getApprovedLoansCount()), approvedLoanHandler::listenGetApprovedLoans);
    }
}
