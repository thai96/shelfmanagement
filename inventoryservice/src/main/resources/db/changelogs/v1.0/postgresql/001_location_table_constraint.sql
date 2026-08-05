ALTER TABLE location
ADD CONSTRAINT chk_location_type
CHECK (type IN ('WAREHOUSE','STORE'));