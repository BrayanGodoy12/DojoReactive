package co.com.bancolombia.model.esubscription;

import co.com.bancolombia.model.agreement.Agreement;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprisevalidation.EnterpriseValidation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ESubscription {

    private EnterpriseValidation validations;
    private Enterprise enterprise;
    private Agreement agreement;

    public static ESubscription createSubscriotion(EnterpriseValidation ev) {
        Agreement agreement = Agreement.builder().build();
        return ESubscription.builder()
                .validations(ev)
                .enterprise(ev.getEnterprise())
                .agreement(agreement)
                .build();
    }
}
