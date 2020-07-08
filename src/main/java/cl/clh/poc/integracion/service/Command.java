package cl.clh.poc.integracion.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.kie.server.api.model.instance.TaskSummaryList;



public enum Command {
	TASK_VARIABLES("/containers/{containerId}/tasks/{taskId}/contents/input", HashMap.class, MediaType.APPLICATION_JSON);
	
	
	private String wsUrl;
	private Class<?> clazz;
	private String content;
	
	
	private Command(String wsUrl, Class<?> clazz, String content){
		this.wsUrl=wsUrl;
		this.clazz = clazz;
		this.content=content;
	}
	
	private Command(String wsUrl, String content){
		this.wsUrl=wsUrl;
		this.clazz=TaskSummaryList.class;
		this.content=content;
	}
	
	/**
	 * @return the clazz
	 */
	public Class<?> getResponseClass() {
		return clazz;
	}

	public String getUrl(){
		return wsUrl;
	}
	
	public String getUrl(Map<String,String> params){
		if(params!=null)
			return withPathParams(params);
		return getUrl();
	}
	
	private String withPathParams(Map<String,String> params){
		String url = new String(wsUrl);
		for (String key : params.keySet()) {
			String value = params.get(key);
			url = url.replaceAll("\\{"+key+"\\}", value);
		}
		return url;
	}
	
	public String getUrlWhitQuery(HashMap<String, List<String>> parameters) {
		String url = new String(wsUrl);
		String param;
		for (String key : parameters.keySet()) {
			if(parameters.get(key)!=null){
				for (String value : parameters.get(key)) {
					if(value!=null){
						param = key + "=" + value + "&";
						url = url + param;
					}
				}
			}
		}
		url= url.substring(0, url.length()-1);
		return url;
	}

	public String getContentType() {
		return content;
	}
}