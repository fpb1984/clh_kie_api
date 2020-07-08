package cl.clh.poc.integracion.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.fiscalia.rgp.causapenal.model.Causa;


public class RequestKieServerClientTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String password;
	
	private Long processInstanceId;
	
	private String processId;
	
	private Long taskId;

	private String variableName;
	
	private HashMap<String, Object> params;
	
	private List<String> statusTask;
	
	private List<Long> listTasks;
	
	private Causa causa;


	public RequestKieServerClientTO() {}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getVariableName() {
		return variableName;
	}


	public void setVariableName(String varName) {
		this.variableName = varName;
	}
	
	public Long getProcessInstanceId() {
		return processInstanceId;
	}


	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public String getProcessId() {
		return processId;
	}


	public void setProcessId(String processId) {
		this.processId = processId;
	}


	public Long getTaskId() {
		return taskId;
	}


	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public HashMap<String, Object> getParams() {
		return params;
	}


	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}


	public Causa getCausa() {
		return causa;
	}


	public void setCausa(Causa causa) {
		this.causa = causa;
	}


	public List<String> getStatusTask() {
		return statusTask;
	}


	public void setStatusTask(List<String> statusTask) {
		this.statusTask = statusTask;
	}


	public List<Long> getListTasks() {
		return listTasks;
	}


	public void setListTasks(List<Long> listTasks) {
		this.listTasks = listTasks;
	}

	
	
}
