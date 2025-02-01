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
import com.softman.dto.DireccionDTO;
import com.softman.entity.Direccion;
import com.softman.enumeration.ExceptionType;
import com.softman.enumeration.ResponseType;
import com.softman.exception.APIException;
import com.softman.exception.BusinessException;
import com.softman.mapper.DireccionMapper;
import com.softman.service.DireccionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping
public class DireccionController {


	private static final Logger LOGGER = LoggerFactory.getLogger(DireccionController.class);

	
	@Autowired
	private DireccionMapper direccionMapper;
	
	@Autowired
	private DireccionService direccionService;
	
	
	@Operation(summary = "Crear dirección")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = DireccionDTO.class)) }
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
	@PostMapping(URIConstants.DIRECCIONES)
	public ResponseEntity<?> guardarDireccion(@RequestBody @Valid DireccionDTO direccionDTO) throws APIException {
		try {
			Direccion nuevaDireccionEntity = direccionMapper.direccionDTOToDireccion(direccionDTO);
									
			nuevaDireccionEntity = direccionService.guardarDireccion(nuevaDireccionEntity);
			
			direccionDTO = direccionMapper.direccionToDireccionDTO(nuevaDireccionEntity);
			
			return new ResponseEntity<>(direccionDTO, HttpStatus.OK);
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
	
	
	@Operation(summary = "Actualizar dirección por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = DireccionDTO.class)) }
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
	@PutMapping(URIConstants.DIRECCIONES+"/{idDireccion}")
	public ResponseEntity<?> actualizarDireccionPorId(@RequestBody @Valid DireccionDTO direccionDTO, @PathVariable Long guidDireccion) throws APIException {
		try {	
			Direccion direccionEntityDB = direccionService.buscarDireccionPorId(guidDireccion);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(direccionEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ACTUALIZAR_NO_ENCONTRADO.getValue());
			}
			
			Direccion nuevaDireccionEntity = direccionMapper.direccionDTOToDireccion(direccionDTO);
			
			BeanUtils.copyProperties(nuevaDireccionEntity, direccionEntityDB);
		
			nuevaDireccionEntity = direccionService.guardarDireccion(direccionEntityDB);
			
			direccionDTO = direccionMapper.direccionToDireccionDTO(nuevaDireccionEntity);

			return new ResponseEntity<>(direccionDTO, httpStatus);
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
	
	
	@Operation(summary = "Eliminar dirección por id")
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
	@DeleteMapping(URIConstants.DIRECCIONES+"/{idDireccion}")
	public ResponseEntity<?> eliminarDireccionPorId(@PathVariable Long idDireccion) throws APIException {
		try {
			Direccion direccionEntityDB = direccionService.buscarDireccionPorId(idDireccion);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(direccionEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ELIMINAR_NO_ENCONTRADO.getValue());
			}
			
			direccionService.eliminarDireccionPorId(idDireccion);

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
	
	
	@Operation(summary = "Buscar dirección por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = DireccionDTO.class)) }
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
	@GetMapping(URIConstants.DIRECCIONES+"/{idDireccion}")
	public ResponseEntity<?> buscarDireccionPorId(@PathVariable("guidDireccion") Long idDireccion) throws Exception {
		
		try {
			Direccion DireccionEntity = direccionService.buscarDireccionPorId(idDireccion);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(DireccionEntity == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			DireccionDTO DireccionDTO = direccionMapper.direccionToDireccionDTO(DireccionEntity);
			
			return new ResponseEntity<>(DireccionDTO, httpStatus);	
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
	

	@Operation(summary = "Buscar direcciones por paginación")
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
	@GetMapping(URIConstants.USUARIOS+"{idUsuario}"+URIConstants.DIRECCIONES)
	public ResponseEntity<?> buscarDireccionesPorPaginacionYIdUsuario(
			@PathVariable Long idUsuario,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Direccion> direccionesEntity = direccionService.buscarDireccionesPorPaginacionYIdUsuario(idUsuario, page, pageSize, field, asc);
			
			List<DireccionDTO> direccionesDTO = direccionMapper.direccionListToDireccionDTOList(direccionesEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(direccionesDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(direccionesEntity.getSize())
					.totalRecordCount(direccionesEntity.getTotalElements())
					.totalPages(direccionesEntity.getTotalPages())
					.content(direccionesDTO)
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
