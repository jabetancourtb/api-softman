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
import com.softman.dto.TipoDocumentoDTO;
import com.softman.entity.TipoDocumento;
import com.softman.enumeration.ExceptionType;
import com.softman.enumeration.ResponseType;
import com.softman.exception.APIException;
import com.softman.exception.BusinessException;
import com.softman.mapper.TipoDocumentoMapper;
import com.softman.service.TipoDocumentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping
public class TipoDocumentoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoDocumentoController.class);

	
	@Autowired
	private TipoDocumentoMapper tipoDocumentoMapper;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	
	@Operation(summary = "Crear tipo documento")
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = TipoDocumentoDTO.class)) }
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
	@PostMapping(URIConstants.TIPOS_DOCUMENTOS)
	public ResponseEntity<?> guardarTipoDocumento(@RequestBody @Valid TipoDocumentoDTO tipoDocumentoDTO) throws APIException {
		try {
			TipoDocumento nuevoTipoDocumentoEntity = tipoDocumentoMapper.tipoDocumentoDTOToTipoDocumento(tipoDocumentoDTO);
									
			nuevoTipoDocumentoEntity = tipoDocumentoService.guardarTipoDocumento(nuevoTipoDocumentoEntity);
			
			tipoDocumentoDTO = tipoDocumentoMapper.tipoDocumentoToTipoDocumentoDTO(nuevoTipoDocumentoEntity);
			
			return new ResponseEntity<>(tipoDocumentoDTO, HttpStatus.OK);
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
	
	
	@Operation(summary = "Actualizar tipo documento por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = TipoDocumentoDTO.class)) }
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
	@PutMapping(URIConstants.TIPOS_DOCUMENTOS+"/{idTipoDocumento}")
	public ResponseEntity<?> actualizarTipoDocumentoPorId(@RequestBody @Valid TipoDocumentoDTO tipoDocumentoDTO, @PathVariable Long guidTipoDocumento) throws APIException {
		try {	
			TipoDocumento tipoDocumentoEntityDB = tipoDocumentoService.buscarTipoDocumentoPorId(guidTipoDocumento);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(tipoDocumentoEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ACTUALIZAR_NO_ENCONTRADO.getValue());
			}
			
			TipoDocumento nuevoTipoDocumentoEntity = tipoDocumentoMapper.tipoDocumentoDTOToTipoDocumento(tipoDocumentoDTO);
			
			BeanUtils.copyProperties(nuevoTipoDocumentoEntity, tipoDocumentoEntityDB);
		
			nuevoTipoDocumentoEntity = tipoDocumentoService.guardarTipoDocumento(tipoDocumentoEntityDB);
			
			tipoDocumentoDTO = tipoDocumentoMapper.tipoDocumentoToTipoDocumentoDTO(nuevoTipoDocumentoEntity);

			return new ResponseEntity<>(tipoDocumentoDTO, httpStatus);
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
	
	
	@Operation(summary = "Eliminar  tipo documento por id")
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
	@DeleteMapping(URIConstants.TIPOS_DOCUMENTOS+"/{idTipoDocumento}")
	public ResponseEntity<?> eliminarTipoDocumentoPorId(@PathVariable Long idTipoDocumento) throws APIException {
		try {
			TipoDocumento tipoDocumentoEntityDB = tipoDocumentoService.buscarTipoDocumentoPorId(idTipoDocumento);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(tipoDocumentoEntityDB == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				
				return  ResponseEntity
						.status(httpStatus)
						.body(ResponseType.RECURSO_A_ELIMINAR_NO_ENCONTRADO.getValue());
			}
			
			tipoDocumentoService.eliminarTipoDocumentoPorId(idTipoDocumento);

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
	
	
	@Operation(summary = "Buscar tipo documento por id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "No content", content =  @Content),
        @ApiResponse(responseCode = "200", description = "Successful operation", 
        		content = { @Content(mediaType = "application/json", 
        	    schema = @Schema(implementation = TipoDocumentoDTO.class)) }
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
	@GetMapping(URIConstants.TIPOS_DOCUMENTOS+"/{idTipoDocumento}")
	public ResponseEntity<?> buscarTipoDocumentoPorId(@PathVariable("guidTipoDocumento") Long idTipoDocumento) throws Exception {
		
		try {
			TipoDocumento TipoDocumentoEntity = tipoDocumentoService.buscarTipoDocumentoPorId(idTipoDocumento);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(TipoDocumentoEntity == null) {
				httpStatus = HttpStatus.NO_CONTENT;
			}
			
			TipoDocumentoDTO TipoDocumentoDTO = tipoDocumentoMapper.tipoDocumentoToTipoDocumentoDTO(TipoDocumentoEntity);
			
			return new ResponseEntity<>(TipoDocumentoDTO, httpStatus);	
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
	

	@Operation(summary = "Buscar tipos documento por paginación")
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
	@GetMapping(URIConstants.TIPOS_DOCUMENTOS)
	public ResponseEntity<?> buscarTipoDocumentosPorPaginacion(
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "name", required = false) String field,
			@RequestParam(defaultValue = "true", required = false) boolean asc) throws Exception {
		try {
			page = page - 1;
			
			Page<TipoDocumento> tipoDocumentoesEntity = tipoDocumentoService.buscarTipoDocumentosPorPaginacion(page, pageSize, field, asc);
			
			List<TipoDocumentoDTO> tipoDocumentoesDTO = tipoDocumentoMapper.tipoDocumentoListToTipoDocumentoDTOList(tipoDocumentoesEntity.getContent());
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(tipoDocumentoesDTO.isEmpty()) {
				httpStatus = HttpStatus.NO_CONTENT;
			}

			return new ResponseEntity<>(
					APIResponseDTO.builder()
					.recordCountPerPage(tipoDocumentoesEntity.getSize())
					.totalRecordCount(tipoDocumentoesEntity.getTotalElements())
					.totalPages(tipoDocumentoesEntity.getTotalPages())
					.content(tipoDocumentoesDTO)
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
