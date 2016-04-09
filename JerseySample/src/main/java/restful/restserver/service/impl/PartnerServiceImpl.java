package restful.restserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import restful.restserver.model.Partner;
import restful.restserver.repository.partner.PartnerRepository;
import restful.restserver.representation.BaseResponse;
import restful.restserver.service.PartnerService;

@Configuration
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;
	
	@Override
	public BaseResponse partnerSync(List<Partner> partners) {
		boolean mergeResult = true;
		BaseResponse baseResponse = null;
		if(partners != null && partners.size() > 0){
			mergeResult = partnerRepository.merge(partners);
		}
		if(mergeResult){
			baseResponse = new BaseResponse();
			baseResponse.setStatus("00");
			baseResponse.setMessage("");
		}else{
			baseResponse = new BaseResponse();
			baseResponse.setStatus("01");
			baseResponse.setMessage("Í¬²½´íÎó");
		}
		return baseResponse;
	}

}
