package one.digitalinnovation.gof.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class ClienteExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSource messageSource;

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {

		String userMessage = "Cliente não encontrado";
		String devMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> erros = createListErrors(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Error> createListErrors(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();

		bindingResult.getFieldErrors().forEach(e -> {
			String userMessage = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			String devMessage = e.toString();
			errors.add(new Error(userMessage, devMessage));
		});

		return errors;
	}

	public static class Error {

		private String userMessage;
		private String devMessage;
		

		public Error(String userMessage, String devMessage) {
			this.userMessage = userMessage;
			this.devMessage = devMessage;			
		}

		public String getUserMessage() {
			return userMessage;
		}

		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}

		public String getDevMessage() {
			return devMessage;
		}

		public void setDevMessage(String devMessage) {
			this.devMessage = devMessage;
		}
	}

}
