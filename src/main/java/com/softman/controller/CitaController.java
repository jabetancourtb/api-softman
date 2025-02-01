package com.softman.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softman.constant.SwaggerConstants;
import com.softman.constant.URIConstants;
import com.softman.dto.APIExceptionResponseDTO;
import com.softman.dto.APIResponseDTO;
import com.softman.dto.CitaDTO;
import com.softman.dto.CitaPartialUpdateDTO;
import com.softman.entity.Cita;
import com.softman.enumeration.ExceptionType;
import com.softman.enumeration.ResponseType;
import com.softman.exception.APIException;
import com.softman.exception.BusinessException;
import com.softman.mapper.CitaMapper;
import com.softman.service.CitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping
public class CitaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CitaController.class);

	
	@Autowired
	private CitaMapper citaMapper;
	
	@Autowired
	private CitaService citaService;
	
	
	@Operation(summary = "Crear cita")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = CitaDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@PostMapping(URIConstants.CITAS)
	public ResponseEntity<?> guardarCita(@RequestBody @Valid CitaDTO citaDTO) throws APIException {
		try {
			Cita nuevaCitaEntity = citaMapper.citaDTOToCita(citaDTO);
												
			nuevaCitaEntity = citaService.guardarCita(nuevaCitaEntity);
			
			CitaDTO citaDetailedDTO = citaMapper.citaToCitaDTO(nuevaCitaEntity);
			
			return new ResponseEntity<>(citaDetailedDTO, HttpStatus.OK);
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}
	}
	
	
	@Operation(summary = "Actualizar cita por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = CitaDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@PutMapping(URIConstants.CITAS+"/{idCita}")
	public ResponseEntity<?> actualizarCitaPorId(@RequestBody @Valid CitaDTO citaDTO, @PathVariable Long idCita) throws APIException {
		try {	
			Cita citaEntityDB = citaService.buscarCitaPorId(idCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ACTUALIZAR_NO_ENCONTRADO.getValue());
			}
			
			Cita nuevaCitaEntity = citaMapper.citaDTOToCita(citaDTO);
			
			BeanUtils.copyProperties(nuevaCitaEntity, citaEntityDB);
					
			nuevaCitaEntity = citaService.guardarCita(citaEntityDB);
			
			citaDTO = citaMapper.citaToCitaDTO(nuevaCitaEntity);

			return new ResponseEntity<>(citaDTO, httpStatus);
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}
	}
	
	
	@Operation(summary = "Actualizar estado cita por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = CitaDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@PatchMapping(URIConstants.CITAS+"/{idCita}")
	public ResponseEntity<?> actualizarEstadoCitaPorId(@RequestBody @Valid CitaPartialUpdateDTO citaPartialUpdateDTO, @PathVariable Long idCita) throws APIException {
		try {	
			Cita citaEntityDB = citaService.buscarCitaPorId(idCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ACTUALIZAR_NO_ENCONTRADO.getValue());
			}
			
			Cita citaEstadoActualizado = citaService.actualizarEstadoCitaPorId(citaEntityDB, citaPartialUpdateDTO.getIdEstadoCita());
					
			citaEstadoActualizado = citaService.guardarCita(citaEntityDB);
			
			CitaDTO citaDTO = citaMapper.citaToCitaDTO(citaEstadoActualizado);

			return new ResponseEntity<>(citaDTO, httpStatus);
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}
	}
	
	
	@Operation(summary = "Eliminar cita por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@DeleteMapping(URIConstants.CITAS+"/{idCita}")
	public ResponseEntity<?> eliminarCitaPorId(@PathVariable Long idCita) throws APIException {
		try {
			Cita citaEntityDB = citaService.buscarCitaPorId(idCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ELIMINAR_NO_ENCONTRADO.getValue());
			}
			
			citaService.eliminarCitaPorId(idCita);

			return ResponseEntity
					.status(httpStatus)
					.body(ResponseType.RECURSO_ELIMINADO.getValue());
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);	
		}
	}
	
	
	@Operation(summary = "Buscar cita por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = CitaDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.CITAS+"/{idCita}")
	public ResponseEntity<?> findCitaByGuid(@PathVariable Long idCita) throws Exception {
		
		try {
			Cita citaEntity = citaService.buscarCitaPorId(idCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citaEntity == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			CitaDTO citaDTO = citaMapper.citaToCitaDTO(citaEntity);
			
			return new ResponseEntity<>(citaDTO, httpStatus);
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);		
		}	
	}
	
	
	// estados-citas/{idEstadoCita}/citas
	@Operation(summary = "Buscar citas por paginación y por idEstadoCita")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS)
	public ResponseEntity<?> findCitaesWithPaginationAndSorting(
			@PathVariable Long idEstadoCita,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Cita> citasEntity = citaService.buscarCitasPorPaginacionYPorIdEstadoCita(idEstadoCita, page, pageSize, field, asc);
			
			List<CitaDTO> citasDTO = citaMapper.citaListToCitaDTOList(citasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(citasEntity.getSize())
					.totalRecordCount(citasEntity.getTotalElements())
					.totalPages(citasEntity.getTotalPages())
					.content(citasDTO)
					.build(), 
					httpStatus);	
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			LOGGER.error(exception.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}	
	}
	
	
	// medicos/{idMedico}/estados-citas/{idEstadoCita}/citas
	@Operation(summary = "Buscar citas por paginación, por idMedico y idEstado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.MEDICOS+"/{idMedico}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS)
	public ResponseEntity<?> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdMedico(
			@PathVariable Long idMedico,
			@PathVariable Long idEstadoCita,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Cita> citasEntity = citaService.buscarCitasPorPaginacionPorIdEstadoCitaYPorIdMedico(idEstadoCita, idMedico, page, pageSize, field, asc);
			
			List<CitaDTO> citasDTO = citaMapper.citaListToCitaDTOList(citasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(citasEntity.getSize())
					.totalRecordCount(citasEntity.getTotalElements())
					.totalPages(citasEntity.getTotalPages())
					.content(citasDTO)
					.build(), 
					httpStatus);	
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}	
	}
	
	
	// especialidades/{idEspecialidad}/estados-citas/{idEstadoCita}/citas
	@Operation(summary = "Buscar citas por paginación, por idEspecialidad y idEstado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.ESPECIALIDADES+"/{idEspecialidad}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS)
	public ResponseEntity<?> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdEspecialidad(
			@PathVariable Long idEspecialidad,
			@PathVariable Long idEstadoCita,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Cita> citasEntity = citaService.buscarCitasPorPaginacionPorIdEstadoCitaYPorIdEspecialidad(idEstadoCita, idEspecialidad, page, pageSize, field, asc);
			
			List<CitaDTO> citasDTO = citaMapper.citaListToCitaDTOList(citasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(citasEntity.getSize())
					.totalRecordCount(citasEntity.getTotalElements())
					.totalPages(citasEntity.getTotalPages())
					.content(citasDTO)
					.build(), 
					httpStatus);	
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}	
	}
	
	
	// centros-medicos/{idCentroMedico}/medicos/{idMedico}/estados-citas/{idEstadoCita}/citas
	@Operation(summary = "Buscar citas por paginación, por idCentroMedico, idMedico y idEstado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.CENTROS_MEDICOS+"/{idCentroMedico}"+URIConstants.MEDICOS+"/{idMedico}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS)
	public ResponseEntity<?> buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(
			@PathVariable Long idCentroMedico,
			@PathVariable Long idMedico,
			@PathVariable Long idEstadoCita,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Cita> citasEntity = citaService.buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(idEstadoCita, idMedico, idCentroMedico, page, pageSize, field, asc);
			
			List<CitaDTO> citasDTO = citaMapper.citaListToCitaDTOList(citasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(citasEntity.getSize())
					.totalRecordCount(citasEntity.getTotalElements())
					.totalPages(citasEntity.getTotalPages())
					.content(citasDTO)
					.build(), 
					httpStatus);	
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}	
	}
	
	
	// centros-medicos/{idCentroMedico}/especialidades/{idEspecialidad}/estados-citas/{idEstadoCita}/citas
	@Operation(summary = "Buscar citas por paginación, por idCentroMedico, idEspecialidad y idEstado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.CENTROS_MEDICOS+"/{idCentroMedico}"+URIConstants.ESPECIALIDADES+"/{idEspecialidad}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS)
	public ResponseEntity<?> buscarCitasPorPaginacionPorIdEstadoCitaPorIdEspecialidadYPorIdCentroMedico(
			@PathVariable Long idCentroMedico,
			@PathVariable Long idEspecialidad,
			@PathVariable Long idEstadoCita,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Cita> citasEntity = citaService.buscarCitasPorPaginacionPorIdEstadoCitaPorIdEspecialidadYPorIdCentroMedico(idEstadoCita, idEspecialidad, idCentroMedico, page, pageSize, field, asc);
			
			List<CitaDTO> citasDTO = citaMapper.citaListToCitaDTOList(citasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(citasEntity.getSize())
					.totalRecordCount(citasEntity.getTotalElements())
					.totalPages(citasEntity.getTotalPages())
					.content(citasDTO)
					.build(), 
					httpStatus);	
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}	
	}
	
	
	// pacientes/{idPaciente}/estados-citas/{idEstadoCita}/citas
	@Operation(summary = "Buscar citas por paginación, por idPaciente y idEstado")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "400", description = "Bad request", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
		        content = { @Content(mediaType = "application/json", 
			    schema = @Schema(implementation = APIExceptionResponseDTO.class)) }
        ) 
    })
	@SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
	@GetMapping(URIConstants.PACIENTES+"/{idPaciente}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS)
	public ResponseEntity<?> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdPaciente(
			@PathVariable Long idPaciente,
			@PathVariable Long idEstadoCita,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Cita> citasEntity = citaService.buscarCitasPorPaginacionPorIdEstadoCitaYPorIdPaciente(idEstadoCita, idPaciente, page, pageSize, field, asc);
			
			List<CitaDTO> citasDTO = citaMapper.citaListToCitaDTOList(citasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(citasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(citasEntity.getSize())
					.totalRecordCount(citasEntity.getTotalElements())
					.totalPages(citasEntity.getTotalPages())
					.content(citasDTO)
					.build(), 
					httpStatus);	
		}
		catch(BusinessException businessException) {
			LOGGER.error(businessException.getMessage());
			
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.BUSINESS_EXCEPTION.getValue())
					.message(businessException.getMessage())
					.exceptionClass(businessException.getClass().toString())
					.httpStatus(HttpStatus.BAD_REQUEST)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					businessException);
		}
		catch(Exception exception) {
			APIExceptionResponseDTO apiExceptionResponse = APIExceptionResponseDTO.builder()
					.type(ExceptionType.SERVER_EXCEPTION.getValue())
					.message(exception.getMessage())
					.exceptionClass(exception.getClass().toString())
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
			
			throw new APIException(apiExceptionResponse, 
					apiExceptionResponse.getHttpStatus().value(),
					exception);
		}	
	}
	
}
