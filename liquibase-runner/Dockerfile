FROM liquibase/liquibase:5.0.3

COPY database/changelogs /liquibase/changelog
COPY liquibase-runner/liquibase.properties /liquibase/changelog/liquibase.properties
COPY database/drivers/*.jar /liquibase/lib/ 
COPY liquibase-runner/entrypoint.sh /entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"] 