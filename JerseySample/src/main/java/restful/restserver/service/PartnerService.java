package restful.restserver.service;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.Partner;
import restful.restserver.representation.BaseResponse;

@Configuration
public interface PartnerService {

	BaseResponse partnerSync(List<Partner> partners);
}
