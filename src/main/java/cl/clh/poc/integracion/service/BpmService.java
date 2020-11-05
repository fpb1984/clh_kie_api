package cl.clh.poc.integracion.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskComment;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.SolverServicesClient;
import org.kie.server.client.UIServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

import cl.clh.poc.integracion.KieAlternative;
import cl.clh.poc.integracion.dto.RequestKieServerClientTO;
import cl.clh.poc.model.Propuesta;


@Service
public class BpmService {

	
	private static Logger log = LoggerFactory.getLogger(BpmService.class);

	//@Autowired
	private KieServicesClient kieServicesClient;
		
	private ProcessServicesClient processClient;

	private CredentialsProvider authPam;
	
	private SolverServicesClient solverClient;

	private QueryServicesClient queryClient;
	
	private UIServicesClient uiClient;
    
	private UserTaskServicesClient userTaskClient;
	
	private KieAlternative kieAlt;
	

	@Value("${pam.kie.server.container-id}")
	String containerId;
	
	@Value("${pam.kie.server.url}")
	String kieServerUrl;
	
	String variableCausaPenal = "causa";

	//@Value("${pam.kie.server.process-id}")
	//String processDefinition;
	
	Integer page = 0;	
	Integer pageSize = 1000;
	    
    
	/*@PostConstruct
    private void postConstruct() {
    		processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
    		queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
    		userTaskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
    		uiClient = kieServicesClient.getServicesClient(UIServicesClient.class);
    }*/

	
	private KieServicesClient kieServicesClientPam(String user, String pass) {
		String URL = kieServerUrl;
	    String USER = user;
	    String PASSWORD = pass;
	    
	    MarshallingFormat FORMAT = MarshallingFormat.JSON;

	    KieServicesConfiguration conf;
	    
	    conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        conf.setMarshallingFormat(FORMAT);
        
        //conf.s
        //If you use custom classes, such as Obj.class, add them to the configuration.
        //Set<Class<?>> extraClassList = new HashSet<Class<?>>();
        //extraClassList.add(Causa.class);
        //conf.addExtraClasses(extraClassList);
        
        return KieServicesFactory.newKieServicesClient(conf);
	}
	
	
	
    
	public ServiceResponse<KieServerInfo> getServerInfo(String processDefinition, String deplId) {
		log.debug("Devolviendo listado de instancias de procesos activas");
		ServiceResponse<KieServerInfo> resp = kieServicesClient.getServerInfo();
		return resp;	
	}
	
	
	// metodos de proceso //
	public Long startProcess(RequestKieServerClientTO request) {
		processClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(ProcessServicesClient.class);
		Long idInstance = processClient.startProcess(containerId, request.getProcessId(), request.getParams());
		return idInstance;
	}
	
	
	public boolean abortProcess(Long processInstanceId){
		try {
			processClient.abortProcessInstance(containerId, processInstanceId);			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	
	
	// metodos de proceso //
	
	public String viewDiagramProcessInstance(RequestKieServerClientTO request) {
		uiClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UIServicesClient.class);
		
		String idInstance = uiClient.getProcessInstanceImage(containerId, request.getProcessInstanceId());
		return idInstance;
	}
	
	public String viewDiagramProcess(RequestKieServerClientTO request) {
		uiClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UIServicesClient.class);
		
		String idInstance = uiClient.getProcessImage(containerId, request.getProcessId());
		return idInstance;
	}
	
	public List<ProcessDefinition> getProcessByContainerId(RequestKieServerClientTO request) {
		queryClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(QueryServicesClient.class);
		List<ProcessDefinition> list = queryClient.findProcessesByContainerId(containerId, page, 10);
		return list;
	}
	
	
	
	// metodos de instancias //
	
	public List<ProcessInstance> getListInstances(RequestKieServerClientTO request){				
		log.debug("Devolviendo listado de instancias de procesos activas");
		queryClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(QueryServicesClient.class);
		List<Integer> status = new ArrayList<>();			
		status.add(1);
		status.add(2);
		List<ProcessInstance> resp = queryClient.findProcessInstancesByContainerId(containerId, status, page, pageSize, "Id", false);
		return resp;		
	}
	
	public ProcessInstance getProcessInstance(RequestKieServerClientTO request) {
		processClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(ProcessServicesClient.class);		
		
		return processClient.getProcessInstance(containerId, request.getProcessInstanceId());		
	}
		
	
	public Map<String, Object> getVariablesProcessInstance(RequestKieServerClientTO request) {
		processClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(ProcessServicesClient.class);
		return processClient.getProcessInstanceVariables(containerId, request.getProcessInstanceId());		
	}
	
	
	public Object getVariableCausaPenal(RequestKieServerClientTO request) {
		processClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(ProcessServicesClient.class);
		
		//Causa causa = processClient.getProcessInstanceVariable(containerId, request.getProcessInstanceId(), this.variableCausaPenal, Causa.class);
		
		Object causaa = processClient.getProcessInstanceVariable(containerId, request.getProcessInstanceId(), this.variableCausaPenal);
		log.info("OBJETO : "+causaa.toString());
		
		return causaa;
	}
	
	public boolean checkInstanceAlreadyDone(RequestKieServerClientTO request, String varName, String varVal, String periodo){				
		queryClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(QueryServicesClient.class);
		List<Integer> status = new ArrayList<>();			
		status.add(2);
		List<ProcessInstance> instances = queryClient.findProcessInstancesByVariableAndValue(varName, varVal, status, page, pageSize);
		List<ProcessInstance> instances2 = queryClient.findProcessInstancesByVariableAndValue("periodo", periodo, status, page, pageSize);
		
		for(ProcessInstance pi:instances)
			for(ProcessInstance pi2:instances2)
				if(pi.getId()==pi2.getId())
					return true;
			
		return false;		
	}
	
	
	
	// ------ METHODS TASKS ------ //
	
	public List<TaskSummary> getListTasks(RequestKieServerClientTO request) {	
		userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
		
		List<String> status = new ArrayList<>();
		status = request.getStatusTask();

		List<TaskSummary> tasks = new ArrayList<TaskSummary>();
		
		//tasks = userTaskClient.findTasksAssignedAsBusinessAdministrator(userId, status, page, pageSize);
		//log.info("findTasks --> : {}", tasks.size());
		//tasks = userTaskClient.findTasksOwned(userId, status, page, pageSize);
		//log.info("findTasksOwned --> : {}", tasks.size());
		
		tasks = userTaskClient.findTasksAssignedAsPotentialOwner(request.getUserId(), status, page, pageSize);
		log.info("findTasksAssignedAsPotentialOwner --> : {}", tasks.size());
		
		for(TaskSummary task : tasks){
			task.setDescription(userTaskClient.getTaskInstance(containerId, task.getId()).getFormName());
		}
		
		
		return tasks;
	}
	
	public TaskInstance getCurrentTask(RequestKieServerClientTO request) {

		userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
		
		List<String> status = new ArrayList<>();
		status = request.getStatusTask();

		List<TaskSummary> tasks = new ArrayList<TaskSummary>();
				
		tasks = userTaskClient.findTasksAssignedAsPotentialOwner(request.getUserId(), status, page, pageSize);
		
		long lastId = 0;
		for(TaskSummary task: tasks) {
			if(task.getId()>lastId)
				lastId = task.getId();
		}
		
		request.setTaskId(lastId);
		TaskInstance task = this.userTaskClient.getTaskInstance(containerId, request.getTaskId());
		Map<String, Object> variables = this.userTaskClient.getTaskInputContentByTaskId(containerId, lastId);
		//Map<String, Object> variables = this.getVariableTaskAlternative(request);			
		task.setInputData(variables);			
		return task;			
		
	}
	
	public Map<String, Object> getCurrentTaskVars(RequestKieServerClientTO request) {

		userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
		
		List<String> status = new ArrayList<>();
		status = request.getStatusTask();

		List<TaskSummary> tasks = new ArrayList<TaskSummary>();
				
		tasks = userTaskClient.findTasksAssignedAsPotentialOwner(request.getUserId(), status, page, pageSize);
		
		Long lastId = tasks.get(tasks.size()-1).getId();
		request.setTaskId(lastId);
		//Map<String, Object> variables = this.userTaskClient.getTaskInputContentByTaskId(containerId, lastId);
		Map<String, Object> variables = this.getVariableTaskAlternative(request);			
		log.info("VARIABLES TAREA : "+variables.toString());
		
		return variables;
		
	}
	
	
	public TaskInstance getTask(RequestKieServerClientTO request) {
		try {
			userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);			
			TaskInstance task = this.userTaskClient.getTaskInstance(containerId, request.getTaskId());
			Map<String, Object> variables = this.getVariableTaskAlternative(request);			
			task.setInputData(variables);			
			return task;			
		} catch (Exception e) {
			new Exception("Se capturo un error tratando de delegar la tarea "+ request.getTaskId() + ": ", e);
			return null;
		}
	}
	
	
	public TaskInstance getNextTask(RequestKieServerClientTO request) {
		try {
			userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
			List<String> status = new ArrayList<>();			
			status.add("Ready");
			status.add("Reserved");
			List<TaskSummary> tasks = userTaskClient.findTasksAssignedAsPotentialOwner(request.getUserId(), page, pageSize);
			
			TaskInstance task = null;
			for(TaskSummary taskAux : tasks){			
				if(taskAux.getProcessInstanceId().equals(request.getProcessInstanceId())) {
					task = userTaskClient.getTaskInstance(containerId, taskAux.getId());
					break;
				}
			}
			
			return task;
		} catch (Exception e) {
			new Exception("Se capturo un error - tarea "+ request.getTaskId() + " : ", e);
			return null;
		}
	}
	
	
	public TaskInstance completeNextTasks(RequestKieServerClientTO request) {
		try {
			userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
			List<String> status = new ArrayList<>();
			status.add("Ready");
			status.add("Reserved");
			List<TaskSummary> tasks = userTaskClient.findTasksByStatusByProcessInstanceId(request.getProcessInstanceId(), status, page, pageSize);
			for(TaskSummary task : tasks){
				log.info("-----> "+task.getName());
				log.info("taskService - start tarea: " + task.getId());
				userTaskClient.startTask(containerId, task.getId(), request.getUserId());
				log.info("taskService - complete tarea: " + task.getId());
				userTaskClient.completeTask(containerId, task.getId(), request.getUserId(), request.getParams());				
			}
			
			return null;
		} catch (Exception e) {
			new Exception("Se capturo un error tratando de delegar la tarea "+ request.getTaskId() + ": ", e);
			return null;
		}
	}
	
	
	
	
	
	public Map<String, Object> getVariableTask(RequestKieServerClientTO request) {
		userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
		
		Map<String, Object> variables = userTaskClient.getTaskInputContentByTaskId(containerId, request.getTaskId());
		log.info("VARIABLES TAREA : "+variables.toString());
		
		return variables;
	}
	

	
	
	public Map<String, Object> getVariableTaskAlternative(RequestKieServerClientTO request) {
		
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("containerId", containerId);
		params.put("taskId", request.getTaskId().toString());
		Map<String, Object> result = new HashMap<String, Object>();
		Response response;

		try {
			kieAlt = new KieAlternative(new URL(kieServerUrl), request.getUserId(), request.getPassword());
			
			response = kieAlt.execute(Command.TASK_VARIABLES, params,
					null, null, request.getUserId(), request.getPassword());
			
			result = (Map<String, Object>) response.getEntity();

			List<Map<String, Object>> propuestaAux = (List<Map<String, Object>>) result.get("propuestas");
			List<Propuesta> propuestas = new ArrayList<Propuesta>();		
			for(Map a:propuestaAux) {
				propuestas.add(easyFIX(a));
			}
			
	    
		    result.put("propuestas", propuestas);
			/*Set<Class<?>> classesSet = new HashSet<>(); 
		    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();;
		    classesSet.add(Causa.class);
		    classesSet.add(Denuncia.class);
		    JSONMarshaller jm = new JSONMarshaller(classesSet, classLoader);*/
		    //HashMap<String, Object> causaAux2 = (HashMap) causaAux.get("cl.fiscalia.rgp.causapenal.model.Causa");
		    //HashMap<String, Object> denunciaAux = (HashMap) causaAux2.get("denuncia");
		    
		    return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new Exception("Se capturo un error : {}", e);
			return null;
		}

		
	}
	
	
	public boolean completeTask(RequestKieServerClientTO request) throws Exception {
		boolean exito = false;
		
		userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);		
		
		TaskInstance task = userTaskClient.findTaskById(request.getTaskId());
		if((task.getStatus().equals("Ready")) || (task.getStatus().equals("Reserved"))){			
			log.info("taskService - start tarea: " + request.getTaskId());
			userTaskClient.startTask(containerId, request.getTaskId(), request.getUserId());
			log.info("taskService - complete tarea: " + request.getTaskId());
			
			
			List<Propuesta> propuestas = this.toList((List<Map<String,Object>>)request.getParams().get("propuestas"));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("propuestasOUT", propuestas);
			
//			Map<String,Object> params = new HashMap<String,Object>();
//			params.put("propuestas", toListWrapper((List<Map<String, Object>>) request.getParams().get("propuestas")));
//			
			userTaskClient.completeTask(containerId, request.getTaskId(), request.getUserId(), params);
			exito = true;
		}
		if(task.getStatus().equals("InProgress")){
			
			List<Propuesta> propuestas = this.toList((List<Map<String,Object>>)request.getParams().get("propuestas"));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("propuestasOUT", propuestas);
			
			log.info("taskService - complete tarea: " + request.getTaskId());
			userTaskClient.completeTask(containerId, request.getTaskId(), request.getUserId(), params);
			exito = true;
		}

		return exito;
	}
	
	
	public boolean completeMultipleTasks(RequestKieServerClientTO request) {
		try {
			userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
			log.info("---------> "+request.getListTasks().size());
			TaskInstance task = null;
			for(Long taskAux : request.getListTasks()){
				log.info("---------> "+taskAux);
				task = userTaskClient.findTaskById(taskAux);
				//request.getParams().put("definirDecidirPropuesta", 1L);
				log.info("taskService - start tarea: " + taskAux);
				userTaskClient.startTask(containerId, taskAux, request.getUserId());
				log.info("taskService - complete tarea: " + taskAux);
				userTaskClient.completeTask(containerId, taskAux, request.getUserId(), request.getParams());
				
				request.setProcessInstanceId(task.getProcessInstanceId());
				
				this.completeNextTasks(request);
			}
						
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	
	

	public boolean claimTask(RequestKieServerClientTO request) {
		try {
			userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
			
			this.userTaskClient.claimTask(containerId, request.getTaskId(), request.getUserId());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	public boolean startTask(RequestKieServerClientTO request) {
		try {
			userTaskClient = kieServicesClientPam(request.getUserId(), request.getPassword()).getServicesClient(UserTaskServicesClient.class);
			
			this.userTaskClient.startTask(containerId, request.getTaskId(), request.getUserId());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	public boolean saveTask(RequestKieServerClientTO request) {
		try {
			this.userTaskClient.saveTaskContent(containerId, request.getTaskId(), request.getParams());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	public void delegateTask(Long taskId, String username, String password, String newUser) {
		try {
			userTaskClient.delegateTask(containerId, taskId, username, newUser);
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
		}
	}
	
	public boolean releaseTask(RequestKieServerClientTO request) {		
		try {
			this.userTaskClient.releaseTask(containerId, request.getTaskId(), request.getUserId());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	public boolean activateTask(RequestKieServerClientTO request) {		
		try {
			this.userTaskClient.activateTask(containerId, request.getTaskId(), request.getUserId());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	public boolean exitTask(RequestKieServerClientTO request) {		
		try {
			this.userTaskClient.exitTask(containerId, request.getTaskId(), request.getUserId());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	public boolean resumeTask(RequestKieServerClientTO request) {		
		try {
			this.userTaskClient.resumeTask(containerId, request.getTaskId(), request.getUserId());
			return true;
		} catch (Exception e) {
			new Exception("Se capturo un error : {}", e);
			return false;
		}
	}
	
	
	
	
	
	
	
	public void addTaskComent(Long taskId, String comment, String userId) {
		try {
			userTaskClient.addTaskComment(containerId, taskId, comment, userId, new Date());
		} catch (Exception e) {
			new Exception("Se captur\u00F3 un error tratando comentar la tarea "+ taskId + ": ", e);
		}
	}
	
	
	public List<TaskComment> getComments(Long taskId) {
		try {
			List<TaskComment> comments= userTaskClient.getTaskCommentsByTaskId(containerId, taskId);
			return comments;
		} catch (Exception e) {
			new Exception("Se captur\u00F3 un error tratando buscar comentarios de tarea "+ taskId + ": ", e);
			return null;
		}
	}
	
	
	public TaskComment getComment(Long commentId) {
		try {
			TaskComment comment = userTaskClient.getTaskCommentById(containerId, commentId, commentId);
			return comment;
		} catch (Exception e) {
			new Exception("Se captur\u00F3 un error buscando comentario "+ commentId + ": ", e);
			return null;
		}
	}
	
	
	public void deleteComment(Long commentId, Long taskId){
		try {
			userTaskClient.deleteTaskComment(containerId, taskId, commentId);
		} catch (Exception e) {
			new Exception("Se captur\u00F3 un error tratando de eliminar comentario "+ commentId + ": ", e);
		}		
	}
	
	
	
	
	
	private List<Propuesta> refactorObject(List<Map<String, Object>> in) {
		List<Propuesta> propuesta = new ArrayList<Propuesta>();
		
		try {
			ObjectMapper objectM = new ObjectMapper();

		    //Reflections reflections = new Reflections();
		    Set<Class<? extends Object>> classesSet = new HashSet<>(); 

		    classesSet.add(java.util.Date.class);

		   	    		    
		    TypeResolverBuilder<?> typer = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL) {
		        @Override
		        public boolean useForType(JavaType t) {
		            if (classesSet.contains(t.getRawClass())) {
		                return true;
		            }
		            return false;
		        }
	        };

	        typer = typer.init(JsonTypeInfo.Id.CLASS, null);
	        typer = typer.inclusion(JsonTypeInfo.As.WRAPPER_OBJECT);
	        
	        objectM.setDefaultTyping(typer);
	        objectM.setConfig(objectM.getSerializationConfig().with(SerializationFeature.INDENT_OUTPUT));
	        objectM.setConfig(objectM.getDeserializationConfig()
	        		.with(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)                
	            .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));

	        objectM.registerSubtypes(classesSet);
	        
	        propuesta = objectM.convertValue(in, new TypeReference<List<Propuesta>>(){});
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return propuesta;
	}
	
	public Propuesta easyFIX(Map<String, Object>in) {
		
		Propuesta propuesta = new Propuesta();
		ObjectMapper objM = new ObjectMapper();
		
		return objM.convertValue(in.get("cl.clh.tramos.dto.Propuesta"), Propuesta.class);
		
	}
	
	public List<Propuesta>toList(List<Map<String,Object>> in){
		
		ObjectMapper mapper = new ObjectMapper();

	    List<Propuesta> propuestas = mapper.convertValue(in, new TypeReference<List<Propuesta>>(){});
		
	    return propuestas;
		
	}
	
public List<Map<String,Object>>toListWrapper(List<Map<String,Object>> in){
		
		List<Map<String,Object>> out = new ArrayList<>();
		
		
	    for(Map<String,Object> aux:in) {
	    	Map<String,Object> aux2= new HashMap<>();
	    	aux2.put("cl.clh.tramos.dto.Propuesta", aux);
	    	out.add(aux2);
	    }
	    
	    return out;
		
	}
	
	
}