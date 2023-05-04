package brave.btc.config.datasource.dev;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@EnableJpaRepositories(
	basePackages = "brave.btc.repository.bo", // bo Repository 경로
	entityManagerFactoryRef = "boEntityManager",
	transactionManagerRef = "boTransactionManager"
)
@RequiredArgsConstructor
@Profile("dev")
@Configuration
public class BOConfig {

	private final Environment environment;

	@Bean
	@ConfigurationProperties("spring.datasource.bo")
	public DataSource boDataSource() {
		return DataSourceBuilder.create()
			.url(environment.getProperty("spring.datasource.jdbc-url"))
			.build();
	}


	@Bean
	public LocalContainerEntityManagerFactoryBean boEntityManager() {
		LocalContainerEntityManagerFactoryBean em=
			new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(boDataSource());
		em.setPackagesToScan("brave.btc.domain.bo");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return em;
	}


	@Bean
	public PlatformTransactionManager boTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(boEntityManager().getObject());
		return transactionManager;
	}
}
