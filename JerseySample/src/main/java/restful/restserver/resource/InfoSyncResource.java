package restful.restserver.resource;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import restful.restserver.model.PBC;
import restful.restserver.model.Partner;
import restful.restserver.representation.BaseResponse;
import restful.restserver.representation.SignRequest;
import restful.restserver.resource.util.MyConvertor;
import restful.restserver.resource.util.MD5Util;
import restful.restserver.service.PartnerService;
import restful.restserver.service.PolicyService;

/**
 * 
 * @author JasonChen1
 *
 */
@Path("/infoSync")
public class InfoSyncResource {

	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private PolicyService policyService;
	
	private String KEY = "456987";
	
	/**
	 * @param signRequest
	 * @return
	 */
	@POST
	@Path("/partnerSync")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse partnerInfoSync(SignRequest signRequest){
		BaseResponse baseResponse = null;
		if(signRequest != null && !signRequest.getBody().isEmpty()){
			String sign = MD5Util.MD5(signRequest.getBody() + KEY);
			if(sign != null && sign.toUpperCase().equals(signRequest.getSign())){
				Partner[] partners = MyConvertor.parseContent2Object(signRequest.getBody(), Partner[].class);
				if(partners != null && partners.length > 0){
					baseResponse = partnerService.partnerSync(Arrays.asList(partners));
				}else{
					baseResponse = new BaseResponse();
					baseResponse.setStatus("9999");
					baseResponse.setMessage("接收json数据存在问题");
				}
			}
		}
		return baseResponse;
	}
	
	/**
	 * @param signRequest
	 * @return
	 */
	@POST
	@Path("/policySync")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse policyInfoSync(SignRequest signRequest){
		BaseResponse baseResponse = null;
		if(signRequest != null && !signRequest.getBody().isEmpty()){
			String sign = MD5Util.MD5(signRequest.getBody() + KEY);
			if(sign != null && sign.toUpperCase().equals(signRequest.getSign())){
				PBC[] policyCoverBenefits = MyConvertor.parseContent2Object(signRequest.getBody(), PBC[].class);
				if(policyCoverBenefits != null && policyCoverBenefits.length > 0){
					baseResponse = policyService.policyCoverBenefitSync(Arrays.asList(policyCoverBenefits));
				}
			}
		}
		return baseResponse;
	}
}
