package restful.restserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import restful.restserver.model.PBC;
import restful.restserver.repository.pcb.PCBRepository;
import restful.restserver.representation.BaseResponse;
import restful.restserver.service.PolicyService;

@Configuration
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	private PCBRepository pCBRepository;
	
	@Override
	public BaseResponse policyCoverBenefitSync(List<PBC> pBCs) {
		boolean mergeResult = true;
		BaseResponse baseResponse = null;
		if(pBCs != null && pBCs.size() > 0){
			mergeResult = pCBRepository.merge(pBCs);
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
