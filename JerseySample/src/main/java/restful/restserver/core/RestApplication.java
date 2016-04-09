package restful.restserver.core;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import restful.restserver.core.filter.CORSRequestFilter;
import restful.restserver.core.filter.CORSResponseFilter;

public class RestApplication extends ResourceConfig {
	public RestApplication() {  
		  
        packages("restful.restserver.resource");
        packages("jason.resource");  // TODO DELETE
		//packages("restful.*");
  
        //register(RequestContextFilter.class);
        register(CORSRequestFilter.class);
        register(CORSResponseFilter.class);
        //register(StringProvider.class).register(new MyInterceptionBinder());
        //register(MyReaderInterceptor.class).register(MyWriterInterceptor.class);
        register(LoggingFilter.class).register(JacksonFeature.class);  
    }  

}
