package co.com.bancolombia.usecase.esubscription;

import co.com.bancolombia.model.creditstate.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprise.execption.EnterpriseNotFoundException;
import co.com.bancolombia.model.enterprise.gateways.EnterpriseService;
import co.com.bancolombia.model.enterprisereports.EnterpriseReports;
import co.com.bancolombia.model.enterprisevalidation.EnterpriseValidation;
import co.com.bancolombia.model.esubscription.ESubscription;
import co.com.bancolombia.model.esubscription.exception.SubscriptionNotCreateException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@RequiredArgsConstructor
public class ESubscriptionUseCase {

    private final EnterpriseService enterpriseService;

    public Mono<ESubscription> SubscribeEnterprise(Enterprise enterprise) {
        return enterpriseService.validateEnterprise(enterprise)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new EnterpriseNotFoundException())))
                .flatMap(this::searchRestrictionsEnterprise)
                .flatMap(this::searchCreditStateAndReports)
                .map(this::setInfoToValidation)
                .map(ESubscription::createSubscriotion)
                .onErrorMap(e -> !(e instanceof EnterpriseNotFoundException),
                        (e2) -> new SubscriptionNotCreateException(e2.getMessage()));
    }

    private EnterpriseValidation setInfoToValidation(Tuple3<CreditState, EnterpriseReports, EnterpriseValidation> tuple) {
        return tuple.getT3().toBuilder()
                .enterprise(tuple.getT3().getEnterprise())
                .creditState(tuple.getT1())
                .enterpriseReports(tuple.getT2())
                .build();
    }

    private Mono<Tuple3<CreditState, EnterpriseReports, EnterpriseValidation>> searchCreditStateAndReports(EnterpriseValidation ev) {
        return Mono.zip(enterpriseService.searchCreditState(ev.getEnterprise()),
                enterpriseService.searchEnterpriseReports(ev.getEnterprise()),
                Mono.just(ev));
    }


    private Mono<EnterpriseValidation> searchRestrictionsEnterprise(EnterpriseValidation enterpriseValidation) {
        return enterpriseService.searchRestrictions(enterpriseValidation.getEnterprise())
                .map(enterpriseFind -> {
                    boolean hasRestrictions = enterpriseFind.getRestrictions().size() != 0;
                    return enterpriseValidation.toBuilder()
                            .enterprise(enterpriseValidation.getEnterprise())
                            .hasRestrictions(hasRestrictions)
                            .build();
                });
    }
}
