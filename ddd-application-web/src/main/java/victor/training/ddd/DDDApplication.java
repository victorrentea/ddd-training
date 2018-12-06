package victor.training.ddd;

import javax.sql.DataSource;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.ddd.spring.repo.EntityRepositoryFactoryBean;
import victor.training.ddd.spring.thread.ThreadScope;
import victor.training.ddd.spring.thread.ThreadScopeListener;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableTransactionManagement
public class DDDApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DDDApplication.class)
			.profiles("standalone")
		    .run(args);
	}

	@Bean
	public ThreadScopeListener threadScopeListener() {
		return new ThreadScopeListener();
	}

	@Bean
	public static BeanFactoryPostProcessor registerThreadScope() {
		return beanFactory -> beanFactory.registerScope("thread", new ThreadScope());
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@Profile("standalone")
	@ConfigurationProperties("peps.datasource")
	public DataSource pepsDataSource() {
		return new DriverManagerDataSource();
	}

	@Bean
	@Profile("!standalone")
	public DataSource pepsDataSourceFromJndi(@Value("${peps.datasource.jndi-name}") String jndi) {
		return new JndiDataSourceLookup().getDataSource(jndi);
	}

	@Bean
	@Profile("standalone")
	public TomcatEmbeddedServletContainerFactory containerFactory() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.addConnectorCustomizers(connector -> {
			((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
		});
		return factory;
	}
}
