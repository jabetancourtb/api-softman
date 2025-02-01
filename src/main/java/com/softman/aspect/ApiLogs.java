package com.softman.aspect;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.softman.dto.APIExceptionResponseDTO;
import com.softman.entity.LogHistory;
import com.softman.enumeration.ExceptionType;
import com.softman.enumeration.RequestType;
import com.softman.exception.APIException;
import com.softman.service.LogService;
import com.softman.utility.JsonUtility;


@Component
@Aspect
public class ApiLogs {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogs.class);

	private LogService logService;
	
	JsonUtility jsonUtility;
	
	@Autowired
	public void setLoggerController(LogService logService) {
		this.logService = logService;
	}
	
	@Autowired
	public void setJsonUtility(JsonUtility jsonUtility) {
		this.jsonUtility = jsonUtility;
	}
	

	@Pointcut(value = "execution(* com.softman.controller..*(..))")
	public void allResources() {
	}
	
	@Around("allResources()")
	public Object processTime(ProceedingJoinPoint pjp) throws Throwable {
		
		long startTime = (System.currentTimeMillis());
	
		LogHistory logHistory = new LogHistory();

		try {
			
			setRequest(pjp, logHistory);

			// Execute endpoints from restcontroller
			Object obj = pjp.proceed();
			
			setResponse(obj, logHistory);
				
			setProcessTimeInMs(logHistory, startTime);
	
			logService.setHistory(logHistory);
			
			String logHistoryToJson = jsonUtility.objectToJson(logHistory);
			
			LOGGER.info(logHistoryToJson);
							
			return obj;
		}
		catch(APIException apiException) {

			setApiException(logHistory, apiException);
			
			setProcessTimeInMs(logHistory, startTime);
			
			logService.setHistory(logHistory);

            return new ResponseEntity<>(apiException.getResponseObjectException(), 
            		HttpStatus.valueOf(apiException.getResponseStatus()));
		}
		catch(Exception exception) {
			
			LOGGER.error("Error en capa Aspect");
			LOGGER.error(exception.toString());
			LOGGER.error("*********************************");
			LOGGER.error(exception.getMessage());
			LOGGER.error("*********************************");
             
            APIExceptionResponseDTO apiExceptionResponseDTO = new APIExceptionResponseDTO();
 			
            apiExceptionResponseDTO.setType(ExceptionType.SERVER_EXCEPTION.getValue());
            apiExceptionResponseDTO.setMessage(exception.getMessage());
            apiExceptionResponseDTO.setExceptionClass(exception.getClass().toString());
            apiExceptionResponseDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            
            setException(logHistory, apiExceptionResponseDTO);
			
			setProcessTimeInMs(logHistory, startTime);
			
			logService.setHistory(logHistory);

			return new ResponseEntity<>(apiExceptionResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	private void setRequest(ProceedingJoinPoint pjp, LogHistory logHistory) throws Exception {
		
		Timestamp requestDateTime = new Timestamp(System.currentTimeMillis());
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String requestQueryString = request.getQueryString();
		String requestBody = getRequestBody(pjp);

		if(requestQueryString == null && requestBody == null) {
			
			if(request.getMethod().equalsIgnoreCase(RequestType.GET.getValue())) {
				
				requestQueryString = "";
				
				for(String nombreParametro : request.getParameterMap().keySet()) {
					requestQueryString += nombreParametro + "=" + request.getParameter(nombreParametro).replaceAll("&", "=") + "&";
				}
			}
			else {

				requestBody = "";
				
				for(String nombreParametro : request.getParameterMap().keySet()) {
					requestBody += nombreParametro + "=" + request.getParameter(nombreParametro).replaceAll("&", "=") + ";";
				}
			}
				
		}
		
		logHistory.setRequestDateTime(requestDateTime);
		logHistory.setRequestUrl(request.getRequestURL().toString());
		logHistory.setRequestMethodHttp(request.getMethod());
		logHistory.setRequestScheme(request.getScheme());
		logHistory.setRequestHost(request.getRemoteHost());
		logHistory.setRequestPath(request.getContextPath());
		logHistory.setRequestQueryUrl(requestQueryString);
		logHistory.setRequestApiResource(pjp.getSignature().getDeclaringTypeName());
		logHistory.setRequestApiResourceMethodName(pjp.getSignature().getName());
		logHistory.setRequestBody(requestBody);
	}
	
	
	private void setResponse(Object obj, LogHistory logHistory) throws Exception {
		
		Timestamp responseDateTime = new Timestamp(System.currentTimeMillis());
		
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();	

		// Obtener Body de la respuesta
		Gson gson = new Gson();
		String responseBody = gson.toJson(obj);
		
		int responseStatus = (response != null) ? response.getStatus() : 400;
		
		logHistory.setResponseDateTime(responseDateTime);
		logHistory.setResponseBody(responseBody);
		logHistory.setResponseStatus(responseStatus);
	}
	
	
	private void setApiException(LogHistory logHistory, APIException apiException) throws Exception {	

		Timestamp responseDateTime = new Timestamp(System.currentTimeMillis());
		
		String messageException = (apiException.getException().getMessage() != null ? apiException.getException().getMessage()
				: "Exception " + apiException.getClass().toString() + " -> "
						+ apiException.getStackTrace()[0].toString());
		
		String objectException = jsonUtility.objectToJson(apiException.getResponseObjectException());
		
		logHistory.setResponseDateTime(responseDateTime);
		logHistory.setMessageException(messageException);
		logHistory.setObjectException(objectException);
		logHistory.setResponseStatus(apiException.getResponseStatus());
		
		LOGGER.error(messageException);
	}
	
	
	private void setException(LogHistory logHistory, APIExceptionResponseDTO apiExceptionResponseDTO) throws Exception {	

		Timestamp responseDateTime = new Timestamp(System.currentTimeMillis());
		
		String objectException = jsonUtility.objectToJson(apiExceptionResponseDTO.getMessage());
		
		logHistory.setResponseDateTime(responseDateTime);
		logHistory.setMessageException(apiExceptionResponseDTO.getMessage());
		logHistory.setObjectException(objectException);
		logHistory.setResponseStatus(apiExceptionResponseDTO.getHttpStatus().value());
		
		LOGGER.error(apiExceptionResponseDTO.getMessage());
	}
	
	
	private void setProcessTimeInMs(LogHistory logHistory, Long startTime) throws Exception {
		
		long endTime = (System.currentTimeMillis());
		
		Integer processTimeInMs = (int) (endTime - startTime);
		
		logHistory.setProcessTimeInMs(processTimeInMs);
	}

	
	private String getRequestBody(ProceedingJoinPoint pjp) throws JsonProcessingException {
		
		String stringRequestBody = null;
		
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
	    Annotation[][] annotationMatrix = methodSignature.getMethod().getParameterAnnotations();

	    int index = -1;
	    
	    for (Annotation[] annotations : annotationMatrix) {
	    	
	      index++;
	      
	      for (Annotation annotation : annotations) {
	    	  
	        if (!(annotation instanceof RequestBody))
	          continue;
	        
	        Object requestBody = pjp.getArgs()[index];
	        
	        ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
	     
			stringRequestBody = mapper.writeValueAsString(requestBody); 
	        
	      }
	    }
	    
	    return stringRequestBody;
	}

	
}
	
	