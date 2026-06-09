package com.thai.pham.storageroutingservice.configs;

@Configuration // Decalre that this is a source of beans - which means beans is generated in this class using function with @Bean annotation
@EnableJpaRepositories( //Annotation which makes spring scan the package of configured class for Spring data repository and implement it
  basePackages = "thai.pham.storageroutingservice", // package for component scanning
  excludeFilters = @ComponentScan.Filter(ReadOnlyRepository.class), // Specify the component which is excludedd from component scanning
  entityManagerFactoryRef = "primaryEntityManagerFactory" // name of EntityManagerFactory which create repository beans scanned by this class -- TODO later check for better understanding
)
public class PrimaryDataSourceConfiguration {
  private String url;
  private String userName;
  private String password;
  private String driverClassName;
  private Integer tomcatMaxActiveConnections; // Maximum NUMBER OF active connection at the same time
  private Integer tomcatMaxIdleConnection; // Maximum nnumber of idle connection at the same time
  private Integer tomcatTimeBetweenEvictionRunInMillis;// The interval between the execution of eviciton thread, > 0 to activate
  private Integer tomcatMinTimeForEvictionEligibilityInMillis; // The maximum idle time of database connection before remove by eviction
  private Boolean tomcatRemoveAbandonedConnections; // To force remove active connection but not close after long time -> may not close after use and cause memory leak
  private Boolean showSQL;

  @Autowired
  public PrimaryDataSourceConfiguration(
    @Value("${spring.datasource.url}") String url,
    @Value("${spring.datasource.username}") String userName,
    @Value("${spring.datasource.password}") String password,
    @Value("${spring.datasource.driver-class-name}") String driverClassName,
    @Value("${spring.datasource.tomcat.max-active}") Integer tomcatMaxActiveConnections,
    @Value("${spring.datasource.tomcat.max-idle}") Integer tomcatMaxIdleConnection, 
    @Value("${spring.datasource.tomcat.time-between-eviction-runs-millis}") Integer tomcatTimeBetweenEvictionRunInMillis,
    @Value("${spring.datasource.tomcat.min-evictable-idle-time-millis}") Integer tomcatMinTimeForEvictionEligibilityInMillis,
    @Value("${spring.datasource.tomcat.remove-abandoned}") Boolean tomcatRemoveAbandonedConnections,
    @Value("${spring.jpa.show-sql}") Boolean showSQL
  ) {
    this.url = url,
    this.userName = userName;
    this.password = password;
    this.driverClassName = driverClassName;
    this.tomcatMaxActiveConnections = tomcatMaxActiveConnections;
    this.tomcatMaxIdleConnection = tomcatMaxIdleConnection;
    this.tomcatTimeBetweenEvictionRunInMillis = tomcatTimeBetweenEvictionRunInMillis;
    this.tomcatMinTimeForEvictionEligibilityInMillis = tomcatMinTimeForEvictionEligibilityInMillis;
    this.tomcatRemoveAbandonedConnections = tomcatRemoveAbandonedConnections;
    this.showSQL = showSQL;
  }

  @Bean
  @Primary
  public DataSource primaryDataSource() {
    PoolProperties properties = new PoolProperties();
    poolProperties.setUrl(url);
    poolProperties.setUsername(username);
    poolProperties.setPassword(password);
    poolProperties.setDriverClassName(driverClassName);
    poolProperties.setMaxActive(tomcatMaxActiveConnections);
    poolProperties.setMaxIdle(tomcatMaxIdleConnections);
    poolProperties.setTimeBetweenEvictionRunsMillis(tomcatTimeBetweenEvictionRunsInMillis);
    poolProperties.setMinEvictableIdleTimeMillis(tomcatMinTimeForEvictionEligibilityInMillis);
    poolProperties.setRemoveAbandoned(tomcatRemoveAbandonedConnections);

    DataSource dataSource = new DataSource();
    dataSource.setPoolProperties(properties);
    return dataSource;
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(false); // Disable database schema action
    Map<String, String> properties = new HashMap<>();
    properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"); // determine logical name if not explicit defined via annotation
    properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"); // Format & Use the logical name for physical name generation in database 
    properties.put("hibernate.show_sql", String.valueOf(showSQL)); // Display sql statement 
    
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(primaryDataSource());
    factoryBean.setPackagesToScan("thai.pham.storageroutingservice");
    factoryBean.setJpaVendorAdapter(vendorAdapter):
    factoryBean.getJpaPropertyMap().putAll(properties);
    return factoryBean;
  }
}
