package jason.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import jason.model.BlogInfo;

@Path("/edit")
public class EditorResource {

	@POST
	@Path("/process")
	@Consumes(MediaType.APPLICATION_JSON)
	public void processBlog(BlogInfo blogInfo){
		System.out.println(blogInfo.content);
	}
}
