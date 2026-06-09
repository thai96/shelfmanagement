package com.thai.pham.storageroutingservice.configs;

@Configuration
@EnableJpaRepositories(
  basePackages = "thai.pham.storageroutingservice",
  includeFilters = @ComponentScan.Filter(ReadOnlyRepository.class),
  entityManagerFactoryRef = "readOnlyEntityManagerFactory"
)
class ReadDataSourceSourceConfig {
  private String url;
  private String username;
  private String password;
  private String driverClassName;
  private Integer tomcatMaxActiveConnections;
  private Integer tomcatMaxIdleConnections;
  private Integer tomcatTimeBetweenEvictionRunsInMillis;
  private Integer tomcatMinTimeForEvictionEligibilityInMillis;
  private Boolean tomcatRemoveAbandonedConnections;
  private Boolean showSQL;

  @AutoWired
  public ReadDataSourceSourceConfig(
    @Value("${spring.datasource.read_url}") String url,
    @Value("${spring.datasource.read_username}") String username,
    @Value("${spring.datasource.read_password}") String password,
    @Value("${spring.datasource.driver-class-name}") String driverClassName,
    @Value("${spring.datasource.tomcat.max-active}") Integer tomcatMaxActiveConnections,
    @Value("${spring.datasource.tomcat.max-idle}") Integer tomcatMaxIdleConnections,
    @Value("${spring.datasource.tomcat.time-between-eviction-runs-millis}") Integer tomcatTimeBetweenEvictionRunsInMillis,
    @Value("${spring.datasource.tomcat.min-evictable-idle-time-millis}") Integer tomcatMinTimeForEvictionEligibilityInMillis,
    @Value("${spring.datasource.tomcat.remove-abandoned}") Boolean tomcatRemoveAbandonedConnections,
    @Value("${spring.jpa.show-sql}") Boolean showSQL
  ) {
    this.url = url;
    this.username = username;
    this.password = password;
    this.driverClassName = driverClassName;
    this.tomcatMaxActiveConnections = tomcatMaxActiveConnections;
    this.tomcatMaxIdleConnections = tomcatMaxIdleConnections;
    this.tomcatTimeBetweenEvictionRunsInMillis = tomcatTimeBetweenEvictionRunsInMillis;
    this.tomcatMinTimeForEvictionEligibilityInMillis = tomcatMinTimeForEvictionEligibilityInMillis;
    this.tomcatRemoveAbandonedConnections = tomcatRemoveAbandonedConnections;
    this.showSQL = showSQL;  
  }

  @Bean
    public DataSource readDataSource() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl(url);
        poolProperties.setUsername(username);
        poolProperties.setPassword(password);
        poolProperties.setDriverClassName(driverClassName);
        poolProperties.setMaxActive(tomcatMaxActiveConnections);
        poolProperties.setMaxIdle(tomcatMaxIdleConnections);
        poolProperties.setTimeBetweenEvictionRunsMillis(tomcatTimeBetweenEvictionRunsInMillis);
        poolProperties.setMinEvictableIdleTimeMillis(tomcatMinTimeForEvictionEligibilityInMillis);
        poolProperties.setRemoveAbandoned(tomcatRemoveAbandonedConnections);

        org.apache.tomcat.jdbc.pool.DataSource readDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        readDataSource.setPoolProperties(poolProperties);
        return readDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.show_sql", String.valueOf(showSQL));
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(readDataSource());
        factoryBean.setPackagesToScan("com.abhinavjain.masterslavedatasources");
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.getJpaPropertyMap().putAll(properties);
        return factoryBean;
    }
}
