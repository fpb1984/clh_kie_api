package cl.clh.poc.integracion.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.clh.poc.integracion.dto.RequestKieServerClientTO;
import cl.clh.poc.integracion.dto.ResponseAPI;
import cl.clh.poc.integracion.service.BpmService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pam")
public class KieServerRest {
	
	private static final Logger logger = LoggerFactory.getLogger(KieServerRest.class);
	
	private ResponseAPI response;
	
	@Autowired
	private BpmService service;

	
	@GetMapping(value = "/info")
	public ResponseEntity<?> info(){
		logger.info("[KieServerRest - info server kie]");
		response = new ResponseAPI();
		
		ServiceResponse<KieServerInfo> list = service.getServerInfo("", "");
		response.setData(list.getResult());
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/getvars")
	public ResponseEntity<?> vars(@RequestBody RequestKieServerClientTO request) throws Exception{
		logger.info("[KieServerRest - ontener server kie]");
		response = new ResponseAPI();
		
		service.getVariableTaskAlternative(request);
		
		response.setData(true);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	// ######### - METHODS PROCESS & INSTANCES - ######### //
	
	///{processId}
	//@PathVariable String processId
	
	@PostMapping(value = "/startInstanceProcess")
	public ResponseEntity<?> startInstanceProcess(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - iniciar instancia de proceso]");
		response = new ResponseAPI();

		//Map<String, Object> params = new HashMap<>();
		
		Long id = service.startProcess(request);
		
		response.setData(id);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	//{processInstanceId}
	//@PathVariable String processInstanceId
	@PostMapping(value = "/abortingInstanceProcess")
	public ResponseEntity<?> abortingInstanceProcess(@RequestBody String processInstanceId){
		logger.info("[KieServerRest - abortar instancia de proceso]");
		response = new ResponseAPI();
		
		Boolean resp = service.abortProcess(Long.parseLong(processInstanceId));
		
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/listInstances")
	public ResponseEntity<?> listInstances(){
		logger.info("[KieServerRest - lista instancias]");
		response = new ResponseAPI();
		RequestKieServerClientTO request = new RequestKieServerClientTO();
		request.setUserId("analista1");
		request.setPassword("redhat.01");
		
		List<ProcessInstance> list = service.getListInstances(request);
		response.setData(list);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/listProcess")
	public ResponseEntity<?> listProcess(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - lista procesos]");
		response = new ResponseAPI();
		
		List<ProcessDefinition> list = service.getProcessByContainerId(request);
		response.setData(list);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/getProcessInstance")
	public ResponseEntity<?> processInstance(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - obtener instancia de proceso]");
		response = new ResponseAPI();
		
		ProcessInstance resp = service.getProcessInstance(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/diagramProcessInstance")
	public ResponseEntity<?> diagramProcessInstance(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - obtener diagrama de instancia de proceso]");
		response = new ResponseAPI();
		
		String resp = service.viewDiagramProcessInstance(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/diagramProcess")
	public ResponseEntity<?> diagramProcess(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - obtener diagrama de instancia de proceso]");
		response = new ResponseAPI();
		
		String resp = service.viewDiagramProcess(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/getVariableCausaPenal")
	public ResponseEntity<?> getVariableCausaPenal(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - obtener variable 'causa penal']");
		response = new ResponseAPI();
		
		Object resp = service.getVariableCausaPenal(request);
		logger.info("---->" + resp.toString());
		
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/getVariablesByProcessInstance")
	public ResponseEntity<?> getVariablesByProcessInstance(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - obtener variables de instancia]");
		response = new ResponseAPI();
		
		Map<String, Object> resp = service.getVariablesProcessInstance(request);
		logger.info("---->" + resp.toString());
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	
	// ######### - METHODS TASK - ######### //
	
	@GetMapping(value = "/listTasks")
	public ResponseEntity<?> listTasks(@RequestParam String userId, @RequestParam String pass){
		logger.info("[KieServerRest - lista de tareas]");
		response = new ResponseAPI();
		
		
		RequestKieServerClientTO request = new RequestKieServerClientTO();
		request.setUserId(userId);
		request.setPassword(pass);
		List<TaskSummary> list = service.getListTasks(request);
		logger.info("[KieServerRest - cantidad de tareas: {} ]", list.size());
		//response.setData(list);
		//response.setMessage("operación realizada exitosamente");
		//response.setSuccess(true);
		return new ResponseEntity<List<TaskSummary>> (list, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/getTask")
	public ResponseEntity<?> getTask(@RequestParam String userId, @RequestParam String pass, @RequestParam Long taskId){
		logger.info("[KieServerRest - obtener tarea]");
		response = new ResponseAPI();
		RequestKieServerClientTO request = new RequestKieServerClientTO();
		request.setUserId(userId);
		request.setPassword(pass);
		request.setTaskId(taskId);
		TaskInstance task = service.getTask(request);
		
		return new ResponseEntity<TaskInstance> (task, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/getCurrentTaskVars")
	public ResponseEntity<?> getCurrentTask(@RequestParam String userId, @RequestParam String pass){
		logger.info("[KieServerRest - obtener tarea Actual]");
		response = new ResponseAPI();
		RequestKieServerClientTO request = new RequestKieServerClientTO();
		request.setUserId(userId);
		request.setPassword(pass);
		Map<String, Object> vars = service.getCurrentTaskVars(request);
		response.setData(vars);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/checkInstanceAlreadyDone")
	public boolean checkInstance(@RequestParam String userId, @RequestParam String pass, @RequestParam String varName, @RequestParam String varVal, @RequestParam String periodo){
		logger.info("[KieServerRest - obtener tarea Actual]");
		response = new ResponseAPI();
		RequestKieServerClientTO request = new RequestKieServerClientTO();
		request.setUserId(userId);
		request.setPassword(pass);
		
		return service.checkInstanceAlreadyDone(request, varName, varVal, periodo);
		
	}
	
	
	@PostMapping(value = "/getVariablesTask")
	public ResponseEntity<?> getVariablesTask(@RequestBody RequestKieServerClientTO request){
		logger.info("[KieServerRest - obtener tarea]");
		response = new ResponseAPI();
		
		Map<String, Object> vars = service.getVariableTask(request);
		response.setData(vars);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	
	
	@PostMapping(value = "/completeStartTask")
	public ResponseEntity<?> completeStartTask(@RequestBody HashMap<String, Object> vars, @RequestParam String userId, @RequestParam String pass) throws Exception {
		logger.info("[KieServerRest - inicio completo de ejecucion de tarea]");
		response = new ResponseAPI();
		
		RequestKieServerClientTO request = new RequestKieServerClientTO();
		request.setUserId(userId);
		request.setPassword(pass);
		request.setParams(vars);
		Long taskId = service.getCurrentTask(request).getId();
		request.setTaskId(taskId);
		boolean resp = service.completeTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/completeTask")
	public ResponseEntity<?> completeTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - completar tarea]");
		response = new ResponseAPI();
		
		boolean resp = service.completeTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/completeMultipleTasks")
	public ResponseEntity<?> completeMulipleTasks(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - completar multiple tareas]");
		response = new ResponseAPI();
		
		boolean resp = service.completeMultipleTasks(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/claimTask")
	public ResponseEntity<?> claimTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - reservar tarea]");
		response = new ResponseAPI();
		
		boolean resp = service.claimTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/startTask")
	public ResponseEntity<?> startTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - inicio ejecucion de tarea]");
		response = new ResponseAPI();
		
		boolean resp = service.startTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/saveTask")
	public ResponseEntity<?> saveTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - guardar tarea]");
		response = new ResponseAPI();		
		
		boolean resp = service.saveTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/resumeTask")
	public ResponseEntity<?> resumeTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - resume tarea]");
		response = new ResponseAPI();
		
		boolean resp = service.resumeTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/activateTask")
	public ResponseEntity<?> activateTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - activar tarea]");
		response = new ResponseAPI();
		
		boolean resp = service.activateTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/nextTask")
	public ResponseEntity<?> nextTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - siguiente tarea]");
		response = new ResponseAPI();
		
		TaskInstance resp = service.getNextTask(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/completeNextTasks")
	public ResponseEntity<?> completeNextTask(@RequestBody RequestKieServerClientTO request) throws Exception {
		logger.info("[KieServerRest - completar las siguientes tareas]");
		response = new ResponseAPI();
		
		TaskInstance resp = service.completeNextTasks(request);
		response.setData(resp);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
			
	}
	
		
	@PostMapping(value = "/returnDiligencia/{processInstanceId}/{diligenciaId}")
	public ResponseEntity<?> returnDiligencia(@PathVariable String processInstanceId, @PathVariable String diligenciaId) throws Exception {
		logger.info("[KieServerRest - respuesta a la diligencia instruida y materializada]");
		response = new ResponseAPI();
		logger.info("[KieServerRest - processInstanceId : "+processInstanceId +", deligenciaId: "+diligenciaId);
		//TaskInstance resp = service.completeNextTasks(request);
		response.setData(true);
		response.setMessage("operación realizada exitosamente");
		response.setSuccess(true);
		return new ResponseEntity<ResponseAPI> (response, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
}
