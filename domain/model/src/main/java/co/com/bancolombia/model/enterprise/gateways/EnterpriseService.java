package co.com.bancolombia.model.enterprise.gateways;

import co.com.bancolombia.model.creditstate.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprisereports.EnterpriseReports;
import co.com.bancolombia.model.enterprisevalidation.EnterpriseValidation;
import reactor.core.publisher.Mono;

public interface EnterpriseService {

    Mono<EnterpriseValidation> validateEnterprise(Enterprise enterprise);

    Mono<Enterprise> searchRestrictions(Enterprise enterprise);

    Mono<CreditState> searchCreditState(Enterprise enterprise);

    Mono<EnterpriseReports> searchEnterpriseReports(Enterprise enterprise);

}
