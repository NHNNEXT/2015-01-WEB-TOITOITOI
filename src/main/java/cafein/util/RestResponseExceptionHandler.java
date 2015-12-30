package cafein.util;

import java.sql.SQLException;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	public RestResponseExceptionHandler() {
		super();
	}

	// 400
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
		final String bodyOfResponse = "Already has Duplicated data";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
		final String bodyOfResponse = "Failed to DataAccess cause of long Data";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRunRequest(final DataIntegrityViolationException ex, final WebRequest request) {
		final String bodyOfResponse = "RuntimeException";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	

	// 500
	@ExceptionHandler({ IllegalAPIPathException.class })
	public ResponseEntity<Object> handlPathError(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final String bodyOfResponse = "API path variable wrong";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}
	
	@ExceptionHandler({IllegalArgumentLengthException.class})
	public ResponseEntity<Object> handleMessageLength(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final String bodyOfResponse = "Longer than MAX";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final String bodyOfResponse = "Empty message";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}
}
