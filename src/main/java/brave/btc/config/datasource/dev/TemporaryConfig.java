package brave.btc.config.datasource.dev;

//
// @EnableJpaRepositories(
// 	basePackages = "brave.btc.repository.temporary", // temporary repository 경로
// 	entityManagerFactoryRef = "temporaryEntityManager",
// 	transactionManagerRef = "temporaryTransactionManager"
// )
// @RequiredArgsConstructor
// @Profile("dev")
// // @Configuration
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
