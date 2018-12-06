package victor.training.ddd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import victor.training.ddd.spring.web.RequestContextExtractor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private RequestContextExtractor requestContextExtractor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(requestContextExtractor);
	}

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/api/**")
					.allowedMethods("*");
		corsRegistry.addMapping("/dummy-login")
					.allowedMethods("*");
		corsRegistry.addMapping("/logout")
					.allowedMethods("*");
	}
}
