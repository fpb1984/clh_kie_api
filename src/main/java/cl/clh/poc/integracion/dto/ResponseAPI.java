package cl.clh.poc.integracion.dto;

import java.io.Serializable;
import java.util.Date;


public class ResponseAPI implements Serializable{

	private static final long serialVersionUID = 1L;

	private Object data;

	private Boolean success;

	private String message;


	
	public ResponseAPI() {}

	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	

	




}
