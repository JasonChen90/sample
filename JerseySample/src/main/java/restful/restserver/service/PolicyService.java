package restful.restserver.service;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.PBC;
import restful.restserver.representation.BaseResponse;

@Configuration
public interface PolicyService {

	BaseResponse policyCoverBenefitSync(List<PBC> pBCs);
}
