package cafein.util;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
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
		final String bodyOfResponse = "This should be application specific";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
		final String bodyOfResponse = "This should be application specific";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	// 500
	@ExceptionHandler({ IllegalAPIPathException.class })
	public ResponseEntity<Object> handlPathError(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final String bodyOfResponse = "API Pathvariable wrong";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final String bodyOfResponse = "empty message";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}
	
	@ExceptionHandler({  })
	public ResponseEntity<Object> handleMessageLength(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final String bodyOfResponse = "empty message";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}
}
