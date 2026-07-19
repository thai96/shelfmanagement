\set ON_ERROR_STOP on
SET synchronous_commit = local;

DO
$$
BEGIN
    IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE rolname = 'inventory_readonly'
   ) THEN
        CREATE ROLE inventory_readonly;
        GRANT CONNECT ON DATABASE inventory_db TO inventory_readonly;
        GRANT USAGE ON SCHEMA public TO inventory_readonly;
        GRANT SELECT ON ALL TABLES IN SCHEMA public TO inventory_readonly;
        GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO inventory_readonly;
        ALTER DEFAULT PRIVILEGES FOR ROLE myuser IN SCHEMA public GRANT SELECT ON TABLES TO inventory_readonly;
        ALTER DEFAULT PRIVILEGES FOR ROLE myuser IN SCHEMA public GRANT SELECT ON SEQUENCES TO inventory_readonly;
    END IF;

    IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE rolname = 'myuser_readonly'
   ) THEN
    CREATE USER myuser_readonly WITH PASSWORD 'strong_password';
    GRANT inventory_readonly TO myuser_readonly;
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RAISE NOTICE 'Error Code: %', SQLSTATE;
        RAISE NOTICE 'Error Message: %', SQLERRM;
END
$$