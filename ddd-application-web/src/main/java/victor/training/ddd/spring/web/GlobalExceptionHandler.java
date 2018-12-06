package victor.training.ddd.spring.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import victor.training.ddd.MyRequestContext;
import victor.training.ddd.TechnicalException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MyRequestContext requestContext;

	@ResponseBody
	@ExceptionHandler(TechnicalException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleBusinessException(HttpServletRequest request, TechnicalException exception) {
		return exceptionToErrorResponse(exception, exception.getParametersObject());
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		return exceptionToErrorResponse(exception, new Object[0]);
	}

	private ErrorResponse exceptionToErrorResponse(Exception exception, Object[] messageParameters) {
		String messageKey = exception.getMessage();
		log.debug("Converting business exception, code: " + messageKey, exception);
		Locale locale = new Locale(requestContext.getLanguage());
		String userMessage = messageSource.getMessage(messageKey, messageParameters, messageKey, locale);
		return new ErrorResponse(messageKey, userMessage);
	}
}
