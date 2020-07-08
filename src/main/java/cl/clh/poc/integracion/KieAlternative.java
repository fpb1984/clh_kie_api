package cl.clh.poc.integracion;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.clh.poc.integracion.service.Command;


public class KieAlternative {
	
	private static Logger log = LoggerFactory.getLogger(KieAlternative.class);
	
	private String userId;
	
	private String password;
	
	private URL baseRestUrl;
	
	public KieAlternative(URL baseRestUrl,String userId, String password){
		this.userId = userId;
		this.password = password;
		this.baseRestUrl = baseRestUrl;
	}
	
	
	public ClientResponse<?> execute(Command command) throws Exception{
		return execute(command,null,null,null, null, null);
	}
	
	public ClientResponse<?> execute(Command command, Map<String,String> pathParams, Boolean union) throws Exception{
		return execute(command,pathParams,null,null,null,null);
	}
	
	public ClientResponse<?> execute(Command command, Map<String,String> pathParams, HashMap<String,List<String>> queryParams, Boolean union, String userId, String password) throws Exception{
		ClientRequestFactory requestFactory;
        
		
		String commandUrl="";
		
		if(pathParams!=null){
			commandUrl = baseRestUrl.getPath() + command.getUrl(pathParams);
		}else if(queryParams!=null){
			commandUrl = baseRestUrl.getPath() + command.getUrlWhitQuery(queryParams);
			if(union!=null && union){
				commandUrl=commandUrl+ "&union=" + Boolean.toString(union);
			}
		}
		
        String urlString = new URL(baseRestUrl, commandUrl).toExternalForm();
        
        	requestFactory = RestRequestHelper.createRestRequest(this.baseRestUrl.toString(), this.userId, this.password ,30);
        	
		ClientRequest request = requestFactory.createRequest(urlString);
		
		request.accept(command.getContentType());
		
		if(queryParams==null)
			return request.get(command.getResponseClass());
		else
			return request.get(command.getResponseClass());
		
	}
	
	
	
	public ClientResponse<?> executePost(Command command, Map<String,String> pathParams, HashMap<String,List<String>> queryParams, Boolean union, String userId, String password) throws Exception{
		ClientRequestFactory requestFactory;
        	
		String commandUrl="";
		
		if(pathParams!=null){
			commandUrl = baseRestUrl.getPath() + command.getUrl(pathParams);
		}
		else if(queryParams!=null){
			commandUrl = baseRestUrl.getPath() + command.getUrlWhitQuery(queryParams);
			if(union!=null && union){
				commandUrl=commandUrl+ "&union=" + Boolean.toString(union);
			}
		}
		
        String urlString = new URL(baseRestUrl, commandUrl).toExternalForm();
        
        requestFactory = RestRequestHelper.createRestRequest(baseRestUrl.toString(), userId, password,30);
     
		ClientRequest request = requestFactory.createRequest(urlString);
		request.accept(command.getContentType());
		
		if(queryParams==null)
			return request.post(command.getResponseClass());
		else
			return request.post(command.getResponseClass());
		
	}

}