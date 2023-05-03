// package brave.btc.config.datasource.dev;
//
// import java.util.HashMap;
//
// import javax.sql.DataSource;
//
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.core.env.Environment;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
// import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
// import lombok.RequiredArgsConstructor;
//
// @EnableJpaRepositories(
// 	basePackages = "brave.btc.repository.temporary", // temporary repository 경로
// 	entityManagerFactoryRef = "temporaryEntityManager",
// 	transactionManagerRef = "temporaryTransactionManager"
// )
// @RequiredArgsConstructor
// @Profile("dev")
// @Configuration
// public class TemporaryConfig {
//
// 	private final Environment env;
//
// 	@Bean
// 	@Qualifier("temporaryDataSource")
// 	public DataSource temporaryDataSource() {
// 		return new EmbeddedDatabaseBuilder()
// 			.setType(EmbeddedDatabaseType.H2)
// 			.addScript("sql/temporary-schema-h2.sql")
// 			.build();
// 	}
//
// 	@Bean
// 	public LocalContainerEntityManagerFactoryBean temporaryEntityManager() {
// 		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
// 		em.setDataSource(temporaryDataSource());
// 		em.setPackagesToScan("brave.btc.domain.temporary");
//
// 		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
// 		em.setJpaVendorAdapter(vendorAdapter);
// 		HashMap<String, Object> properties = new HashMap<>();
// 		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
// 		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
// 		em.setJpaPropertyMap(properties);
// 		return em;
// 	}
// }
