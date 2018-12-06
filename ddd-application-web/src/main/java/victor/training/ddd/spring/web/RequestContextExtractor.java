package victor.training.ddd.spring.web;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import victor.training.ddd.MyRequestContext;

@Component
public class RequestContextExtractor implements WebRequestInterceptor {

	private static final int REQUEST_ID_LENGTH = 4;

	private static final Logger log = LoggerFactory.getLogger(RequestContextExtractor.class);

	@Autowired
	private MyRequestContext requestContext;

	@Override
	public void preHandle(WebRequest webRequest) throws Exception {
		requestContext.setRequestId(RandomStringUtils.randomAlphanumeric(REQUEST_ID_LENGTH));
		Authentication auth = SecurityContextHolder.getContext()
												   .getAuthentication();
		if (auth != null) {
			requestContext.setUsername(auth.getName());
			requestContext.setLanguage("en");
		} else {
			log.debug("Not authenticated request: {}", webRequest.getDescription(false));
		}
	}

	@Override
	public void afterCompletion(WebRequest arg0, Exception arg1) throws Exception {
	}

	@Override
	public void postHandle(WebRequest arg0, ModelMap arg1) throws Exception {
	}
}
