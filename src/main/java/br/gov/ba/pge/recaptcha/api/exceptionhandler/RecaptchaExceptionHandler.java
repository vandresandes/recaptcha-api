package br.gov.ba.pge.recaptcha.api.exceptionhandler;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RecaptchaExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getCause().toString();
		Error apiError = new Error(status, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, headers, status);
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
	    String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
	    Error apiError = new Error(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	public class Error {
		 
	    private HttpStatus status;
	    private String message;
	    private List<String> errors;
	 
	    public Error(HttpStatus status, String message, List<String> errors) {
	        super();
	        this.status = status;
	        this.message = message;
	        this.errors = errors;
	    }
	 
	    public Error(HttpStatus status, String message, String error) {
	        super();
	        this.status = status;
	        this.message = message;
	        errors = Arrays.asList(error);
	    }

		public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}
	    
	    
	}
}
