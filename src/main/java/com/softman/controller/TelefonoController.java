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
import com.softman.dto.TelefonoDTO;
import com.softman.entity.Telefono;
import com.softman.enumeration.ExceptionType;
import com.softman.enumeration.ResponseType;
import com.softman.exception.APIException;
import com.softman.exception.BusinessException;
import com.softman.mapper.TelefonoMapper;
import com.softman.service.TelefonoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping
public class TelefonoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TelefonoController.class);

	
	@Autowired
	private TelefonoMapper telefonoMapper;
	
	@Autowired
	private TelefonoService telefonoService;
	
	
	@Operation(summary = "Crear teléfono")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = TelefonoDTO.class)) }
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
	@PostMapping(URIConstants.TELEFONOS)
	public ResponseEntity<?> guardarTelefono(@RequestBody @Valid TelefonoDTO telefonoDTO) throws APIException {
		try {
			Telefono nuevoTelefonoEntity = telefonoMapper.telefonoDTOToTelefono(telefonoDTO);
									
			nuevoTelefonoEntity = telefonoService.guardarTelefono(nuevoTelefonoEntity);
			
			telefonoDTO = telefonoMapper.telefonoToTelefonoDTO(nuevoTelefonoEntity);
			
			return new ResponseEntity<>(telefonoDTO, HttpStatus.OK);
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
	
	
	@Operation(summary = "Actualizar teléfono por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = TelefonoDTO.class)) }
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
	@PutMapping(URIConstants.TELEFONOS+"/{idTelefono}")
	public ResponseEntity<?> actualizarTelefonoPorId(@RequestBody @Valid TelefonoDTO telefonoDTO, @PathVariable Long guidTelefono) throws APIException {
		try {	
			Telefono telefonoEntityDB = telefonoService.buscarTelefonoPorId(guidTelefono);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(telefonoEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ACTUALIZAR_NO_ENCONTRADO.getValue());
			}
			
			Telefono nuevoTelefonoEntity = telefonoMapper.telefonoDTOToTelefono(telefonoDTO);
			
			BeanUtils.copyProperties(nuevoTelefonoEntity, telefonoEntityDB);
		
			nuevoTelefonoEntity = telefonoService.guardarTelefono(telefonoEntityDB);
			
			telefonoDTO = telefonoMapper.telefonoToTelefonoDTO(nuevoTelefonoEntity);

			return new ResponseEntity<>(telefonoDTO, httpStatus);
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
	
	
	@Operation(summary = "Eliminar teléfono por id")
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
	@DeleteMapping(URIConstants.TELEFONOS+"/{idTelefono}")
	public ResponseEntity<?> eliminarTelefonoPorId(@PathVariable Long idTelefono) throws APIException {
		try {
			Telefono telefonoEntityDB = telefonoService.buscarTelefonoPorId(idTelefono);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(telefonoEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ELIMINAR_NO_ENCONTRADO.getValue());
			}
			
			telefonoService.eliminarTelefonoPorId(idTelefono);

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
	
	
	@Operation(summary = "Buscar teléfono por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = TelefonoDTO.class)) }
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
	@GetMapping(URIConstants.TELEFONOS+"/{idTelefono}")
	public ResponseEntity<?> buscarTelefonoPorId(@PathVariable("guidTelefono") Long idTelefono) throws Exception {
		
		try {
			Telefono TelefonoEntity = telefonoService.buscarTelefonoPorId(idTelefono);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(TelefonoEntity == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			TelefonoDTO TelefonoDTO = telefonoMapper.telefonoToTelefonoDTO(TelefonoEntity);
			
			return new ResponseEntity<>(TelefonoDTO, httpStatus);	
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
	

	@Operation(summary = "Buscar teléfonos por paginación")
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
	@GetMapping(URIConstants.USUARIOS+"{idUsuario}"+URIConstants.TELEFONOS)
	public ResponseEntity<?> buscarTelefonosPorPaginacionYIdUsuario(
			@PathVariable Long idUsuario,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<Telefono> telefonoesEntity = telefonoService.buscarTelefonosPorPaginacionYPorIdUsuario(idUsuario, page, pageSize, field, asc);
			
			List<TelefonoDTO> telefonoesDTO = telefonoMapper.telefonoListToTelefonoDTOList(telefonoesEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(telefonoesDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(telefonoesEntity.getSize())
					.totalRecordCount(telefonoesEntity.getTotalElements())
					.totalPages(telefonoesEntity.getTotalPages())
					.content(telefonoesDTO)
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
