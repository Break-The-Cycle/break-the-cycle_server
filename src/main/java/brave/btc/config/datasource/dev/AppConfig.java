package brave.btc.config.datasource.dev;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@EnableJpaRepositories(
	basePackages = "brave.btc.repository.app", // app Repository 경로
	entityManagerFactoryRef = "appEntityManager",
	transactionManagerRef = "appTransactionManager"
)
@RequiredArgsConstructor
@Profile("dev")
@Configuration
public class AppConfig {

	private final Environment environment;

	@Primary
	@Bean
	@ConfigurationProperties("spring.datasource.app")
	public DataSource appDataSource() {
		return DataSourceBuilder.create()
			.url(environment.getProperty("spring.datasource.jdbc-url"))
			.build();
	}

	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean appEntityManager() {
		LocalContainerEntityManagerFactoryBean em=
			new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(appDataSource());
		em.setPackagesToScan("brave.btc.domain.app");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return em;
	}


	@Primary
	@Bean
	public PlatformTransactionManager appTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(appEntityManager().getObject());
		return transactionManager;
	}
}
