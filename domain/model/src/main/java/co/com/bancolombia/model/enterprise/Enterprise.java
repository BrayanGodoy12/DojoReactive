package co.com.bancolombia.model.enterprise;

import co.com.bancolombia.model.restrictions.Restrictions;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;

@Data
@Builder(toBuilder = true)
public class Enterprise {
    private boolean validity;
    private LinkedList<Restrictions> restrictions;

}
