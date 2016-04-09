package restful.restserver.repository.partner;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.Partner;
import restful.restserver.repository.util.GenericRepository;

@Configuration
public interface PartnerRepository extends GenericRepository<Partner, BigDecimal> {

	boolean merge(List<Partner> partners);
}
