package brave.btc.config.datasource.dev;

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
