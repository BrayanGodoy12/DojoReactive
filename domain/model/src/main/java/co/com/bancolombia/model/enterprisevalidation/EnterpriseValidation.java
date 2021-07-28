package co.com.bancolombia.model.enterprisevalidation;

import co.com.bancolombia.model.creditstate.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprisereports.EnterpriseReports;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EnterpriseValidation {
    private Enterprise enterprise;
    private boolean nitIsValidity;
    private boolean hasRestrictions;
    private CreditState creditState;
    private EnterpriseReports enterpriseReports;
}
