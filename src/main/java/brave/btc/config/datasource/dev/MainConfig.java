// package brave.btc.config.datasource.dev;
//
// import javax.sql.DataSource;
//
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
// import org.springframework.context.annotation.Profile;
// import org.springframework.core.env.Environment;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
// import org.springframework.transaction.PlatformTransactionManager;
//
// import lombok.RequiredArgsConstructor;
//
// @EnableJpaRepositories(
// 	basePackages = "brave.btc.repository.persistence", // main Repository 경로
// 	entityManagerFactoryRef = "mainEntityManager",
// 	transactionManagerRef = "mainTransactionManager"
// )
// @RequiredArgsConstructor
// @Profile("dev")
// @Configuration
// public class MainConfig {
//
// 	private final Environment env;
//
//
// 	@Primary
// 	@Bean
// 	@ConfigurationProperties("spring.datasource")
// 	public DataSource mainDataSource() {
// 		return DataSourceBuilder.create().build();
// 	}
//
// 	@Primary
// 	@Bean
// 	public LocalContainerEntityManagerFactoryBean mainEntityManager() {
// 		LocalContainerEntityManagerFactoryBean em =
// 			new LocalContainerEntityManagerFactoryBean();
// 		em.setDataSource(mainDataSource());
// 		em.setPackagesToScan("brave.btc.domain.persistence");
// 		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
// 		return em;
// 	}
//
//
// 	@Primary
// 	@Bean
// 	public PlatformTransactionManager mainTransactionManager() {
// 		JpaTransactionManager transactionManager = new JpaTransactionManager();
// 		transactionManager.setEntityManagerFactory(mainEntityManager().getObject());
// 		return transactionManager;
// 	}
// }
