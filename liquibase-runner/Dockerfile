FROM liquibase/liquibase:5.0.3

COPY database/changelogs /liquibase/changelog
COPY COPY liquibase-runner/liquibase.properties /liquibase/liquibase.properties 
COPY database/drivers/*.jar /liquibase/lib/ 

ENTRYPOINT ["liquibase"] 
CMD ["--defaultsFile=/liquibase/liquibase.properties", "update"]