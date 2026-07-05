\set ON_ERROR_STOP on
SET synchronous_commit = local;
CREATE USER myuser_readonly WITH PASSWORD 'strong_password';
GRANT CONNECT ON DATABASE inventory_db TO myuser_readonly;
GRANT USAGE ON SCHEMA public TO myuser_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO myuser_readonly;
GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO myuser_readonly;
ALTER DEFAULT PRIVILEGES FOR ROLE myuser IN SCHEMA public GRANT SELECT ON TABLES TO myuser_readonly;
ALTER DEFAULT PRIVILEGES FOR ROLE myuser IN SCHEMA public GRANT SELECT ON SEQUENCES TO myuser_readonly;

