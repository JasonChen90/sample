package task.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author JasonChen1
 *
 */
public class MyHttpRequest {

	private static Logger logger = Logger.getLogger(MyHttpRequest.class);
	
	public <T, A> T jsonRequest(StringEntity stringEntity, String url, Class<T> returnClass){
		
		if(stringEntity == null || url == null || url.isEmpty() || returnClass == null){
			return null;
		}
		HttpResponse httpResponse = null;
		T obj = null;
		try {
			httpResponse = Request.Post(url).version(HttpVersion.HTTP_1_1).body(stringEntity).execute().returnResponse();
			if(httpResponse != null){
				StatusLine statusLine = httpResponse.getStatusLine();
				if(statusLine != null && statusLine.getStatusCode() >= 300){
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				}
				HttpEntity httpEntity = httpResponse.getEntity();
				if(httpEntity == null){
					throw new ClientProtocolException("Response contains no content");
				}
				Gson gson = new GsonBuilder().create();
				ContentType contentType = ContentType.getOrDefault(httpEntity);
				Charset charset = contentType.getCharset();
				Reader reader = new InputStreamReader(httpEntity.getContent(), charset);
				obj = gson.fromJson(reader, returnClass);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return obj;
	}
	
	public <T, A> List<T> jsonRequest2(StringEntity stringEntity, String url, Class<T> returnClass){
		
		if(stringEntity == null || url == null || url.isEmpty() || returnClass == null){
			return null;
		}
		HttpResponse httpResponse = null;
		List<T> obj = null;
		try {
			httpResponse = Request.Post(url).version(HttpVersion.HTTP_1_1).body(stringEntity).execute().returnResponse();
			if(httpResponse != null){
				StatusLine statusLine = httpResponse.getStatusLine();
				if(statusLine != null && statusLine.getStatusCode() >= 300){
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				}
				HttpEntity httpEntity = httpResponse.getEntity();
				if(httpEntity == null){
					throw new ClientProtocolException("Response contains no content");
				}
				Gson gson = new GsonBuilder().create();
				ContentType contentType = ContentType.getOrDefault(httpEntity);
				Charset charset = contentType.getCharset();
				Reader reader = new InputStreamReader(httpEntity.getContent(), charset);
				obj = gson.fromJson(reader, new TypeToken<List<T>>(){}.getType());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return obj;
	}
}
