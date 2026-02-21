package com.ded.BTS.Exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ProblemDetailFactory factory;

	public GlobalExceptionHandler(ProblemDetailFactory factory) {
		super();
		this.factory = factory;
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ProblemDetail handleHttpMsgNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
		
		Throwable root = ex.getMostSpecificCause();
		
		ProblemDetail pd =
		            factory.build(
		                    HttpStatus.BAD_REQUEST,
		                    "Validation Failed",
		                    "Request validation failed",
		                    "VALIDATION_FAILED",
		                    request.getRequestURI(),
		                    Map.of(
		                            "field",
		                            root.getMessage()
		                        )
		            );

		    return pd;
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationException(
	        MethodArgumentNotValidException ex,
	        HttpServletRequest request) {

	    Map<String, String> errors =
	            ex.getBindingResult()
	              .getFieldErrors()
	              .stream()
	              .collect(Collectors.toMap(
	                      FieldError::getField,
	                      FieldError::getDefaultMessage,
	                      (msg1, msg2) -> msg1
	              ));

	    ProblemDetail pd =
	            factory.build(
	                    HttpStatus.BAD_REQUEST,
	                    "Validation Failed",
	                    "Request validation failed",
	                    "VALIDATION_FAILED",
	                    request.getRequestURI(),
	                    errors
	            );


	    return pd;
	}


	@ExceptionHandler(InvalidEnumException.class)
	public ProblemDetail handleInvalidEnum(InvalidEnumException ex, HttpServletRequest request) {

		List<String> allowed = Arrays.stream(ex.getEnumType().getEnumConstants()).map(Enum::name).toList();

		return factory.build(HttpStatus.BAD_REQUEST, "Invalid Enum Value", ex.getMessage(), "INVALID_ENUM_VALUE",
				request.getRequestURI(), Map.of("allowedValues", allowed));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ProblemDetail handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {

		return factory.build(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage(), "RESOURCE_NOT_FOUND",
				request.getRequestURI(), null);
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail debug(Exception ex, HttpServletRequest request) {

		ex.printStackTrace(); // IMPORTANT

		return factory.build(HttpStatus.INTERNAL_SERVER_ERROR, "Server issue", ex.getMessage(), "INTERNAL_SERVER_ERROR",
				request.getRequestURI(), null);	}

}
