package rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class FileUploadResource {
	
	private static final String PIC_DIR = "D://temp/";

	@POST
	@Path("/upload")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void fileUpload(@FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition contentDisposition){
		String fileLocation = PIC_DIR + contentDisposition.getFileName();
		try{
			OutputStream out = new FileOutputStream(new File(fileLocation));
			byte[] buffer = new byte[2048];
			int bytes = 0;
			while(bytes != -1){
				bytes = inputStream.read(buffer);
				out.write(buffer,0,bytes);
			}
			out.flush();
			out.close();
			inputStream.close();
		}catch(Exception ex){
			
		}
	}
}
