package com.prasad.nithin.subscription.exception.handler;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.prasad.nithin.subscription.exception.CustomExcpetion;
import com.prasad.nithin.subscription.exception.ErrorBody;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
		return Optional.ofNullable(e).map(ConstraintViolationException::getConstraintViolations)
				.map(this::createException).orElseGet(this::generateGenericError);
	}

	private ResponseEntity<Object> generateGenericError() {
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> createException(Set<ConstraintViolation<?>> constraintViolations) {
		List<ErrorBody> build = constraintViolations.stream().map(this::convertError).collect(Collectors.toList());
		return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);
	}

	private ErrorBody createException(FieldError fieldError) {
		return ErrorBody.builder().code(fieldError.getCode()).message(fieldError.getDefaultMessage())
				.field(fieldError.getField()).value(fieldError.getRejectedValue()).build();
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErrorBody> errorBodies = ex.getBindingResult().getFieldErrors().stream().map(this::createException)
				.collect(Collectors.toList());
		errorBodies.addAll(ex.getBindingResult().getGlobalErrors().stream().map(this::createException)
				.collect(Collectors.toList()));
		return handleExceptionInternal(ex, errorBodies, headers, HttpStatus.BAD_REQUEST, request);
	}

	private ErrorBody createException(ObjectError objectError) {
		return ErrorBody.builder().code(objectError.getCode()).message(objectError.getDefaultMessage())
				.field(objectError.getObjectName()).value(objectError.getArguments().toString()).build();
	}

	private ErrorBody convertError(ConstraintViolation<?> constraintViolation) {
		return ErrorBody.builder().code(constraintViolation.getMessage()).message(constraintViolation.getMessage())
				.field(constraintViolation.getPropertyPath().toString()).value(constraintViolation.getInvalidValue())
				.build();
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return prepareExceptionResponse(ex);
	}

	private ResponseEntity<Object> prepareExceptionResponse(Exception ex) {
		return buildResponseEntity(ErrorBody.builder().code(HttpStatus.BAD_REQUEST.toString()).message(Optional.ofNullable(ex.getMessage()).orElse(ex.getLocalizedMessage()))
				.field(Optional.ofNullable(ex.getCause()).map(Throwable::toString).orElse(ex.toString())).build());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return prepareExceptionResponse(ex);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
		return prepareExceptionResponse(ex);
	}

	@ExceptionHandler(CustomExcpetion.class)
	protected ResponseEntity<Object> handleCustomError(CustomExcpetion ex, WebRequest request) {
		return new ResponseEntity(ex.getErrorBodies(), ex.getStatus());
	}

	private ResponseEntity<Object> buildResponseEntity(ErrorBody apiError) {
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

}