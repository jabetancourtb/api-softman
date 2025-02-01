package com.softman.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.softman.dto.EstadoCitaDTO;
import com.softman.entity.EstadoCita;
import com.softman.enumeration.ExceptionType;
import com.softman.enumeration.ResponseType;
import com.softman.exception.APIException;
import com.softman.exception.BusinessException;
import com.softman.mapper.EstadoCitaMapper;
import com.softman.service.EstadoCitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping
public class EstadoCitaController {


	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoCitaController.class);

	
	@Autowired
	private EstadoCitaMapper estadoCitaMapper;
	
	@Autowired
	private EstadoCitaService estadoCitaService;
	
	
	@Operation(summary = "Crear estado cita")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = EstadoCitaDTO.class)) }
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
	@PreAuthorize("hasAnyRole('ADMIN')") 
	@PostMapping(URIConstants.ESTADOS_CITAS)
	public ResponseEntity<?> guardarEstadoCita(@RequestBody @Valid EstadoCitaDTO estadoCitaDTO) throws APIException {
		try {
			EstadoCita nuevoEstadoCitaEntity = estadoCitaMapper.estadoCitaDTOToEstadoCita(estadoCitaDTO);
									
			nuevoEstadoCitaEntity = estadoCitaService.guardarEstadoCita(nuevoEstadoCitaEntity);
			
			estadoCitaDTO = estadoCitaMapper.estadoCitaToEstadoCitaDTO(nuevoEstadoCitaEntity);
			
			return new ResponseEntity<>(estadoCitaDTO, HttpStatus.OK);
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
        	    schema = @Schema(implementation = EstadoCitaDTO.class)) }
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
	@PreAuthorize("hasAnyRole('ADMIN')") 
	@PutMapping(URIConstants.ESTADOS_CITAS+"/{idEstadoCita}")
	public ResponseEntity<?> actualizarEstadoCitaPorId(@RequestBody @Valid EstadoCitaDTO estadoCitaDTO, @PathVariable Long idEstadoCita) throws APIException {
		try {	
			EstadoCita estadoCitaEntityDB = estadoCitaService.buscarEstadoCitaPorId(idEstadoCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(estadoCitaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ACTUALIZAR_NO_ENCONTRADO.getValue());
			}
			
			EstadoCita nuevoEstadoCitaEntity = estadoCitaMapper.estadoCitaDTOToEstadoCita(estadoCitaDTO);
			
			BeanUtils.copyProperties(nuevoEstadoCitaEntity, estadoCitaEntityDB);
		
			nuevoEstadoCitaEntity = estadoCitaService.guardarEstadoCita(estadoCitaEntityDB);
			
			estadoCitaDTO = estadoCitaMapper.estadoCitaToEstadoCitaDTO(nuevoEstadoCitaEntity);

			return new ResponseEntity<>(estadoCitaDTO, httpStatus);
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
	
	
	@Operation(summary = "Eliminar estado cita por id")
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
	@PreAuthorize("hasAnyRole('ADMIN')") 
	@DeleteMapping(URIConstants.ESTADOS_CITAS+"/{idEstadoCita}")
	public ResponseEntity<?> eliminarEstadoCitaPorId(@PathVariable Long idEstadoCita) throws APIException {
		try {
			EstadoCita estadoCitaEntityDB = estadoCitaService.buscarEstadoCitaPorId(idEstadoCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(estadoCitaEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ELIMINAR_NO_ENCONTRADO.getValue());
			}
			
			estadoCitaService.eliminarEstadoCitaPorId(idEstadoCita);

			return ResponseEntity
					.status(httpStatus)
					.body(ResponseType.RECURSO_ELIMINADO.getValue());
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
	
	
	@Operation(summary = "Buscar estado cita por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = EstadoCitaDTO.class)) }
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
	@GetMapping(URIConstants.ESTADOS_CITAS+"/{idEstadoCita}")
	public ResponseEntity<?> buscarEstadoCitaPorId(@PathVariable Long idEstadoCita) throws Exception {
		
		try {
			EstadoCita estadoCitaEntity = estadoCitaService.buscarEstadoCitaPorId(idEstadoCita);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(estadoCitaEntity == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			EstadoCitaDTO estadoCitaDTO = estadoCitaMapper.estadoCitaToEstadoCitaDTO(estadoCitaEntity);
			
			return new ResponseEntity<>(estadoCitaDTO, httpStatus);	
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
	

	@Operation(summary = "Buscar estados citas por paginación")
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
	@GetMapping(URIConstants.ESTADOS_CITAS)
	public ResponseEntity<?> buscarEstadosCitasPorPaginacion(
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<EstadoCita> estadosCitasEntity = estadoCitaService.buscarEstadosCitasPorPaginacion(page, pageSize, field, asc);
			
			List<EstadoCitaDTO> estadosCitasDTO = estadoCitaMapper.estadoCitaListToEstadoCitaDTOList(estadosCitasEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(estadosCitasDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(estadosCitasEntity.getSize())
					.totalRecordCount(estadosCitasEntity.getTotalElements())
					.totalPages(estadosCitasEntity.getTotalPages())
					.content(estadosCitasDTO)
					.build(), 
					httpStatus);	
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
	
}
