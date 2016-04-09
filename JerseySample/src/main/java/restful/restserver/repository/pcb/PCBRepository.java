package restful.restserver.repository.pcb;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.PBC;
import restful.restserver.repository.util.GenericRepository;

@Configuration
public interface PCBRepository extends GenericRepository<PBC, BigDecimal> {

	boolean merge(List<PBC> pBCs);
}
