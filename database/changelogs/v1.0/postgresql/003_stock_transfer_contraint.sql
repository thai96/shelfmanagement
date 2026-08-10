ALTER TABLE stock_transfer
ADD CONSTRAINT chk_stock_transfer_status
CHECK (status IN ('PENDING', 'IN_TRANSIS', 'COMPLETED', 'DISCREPANCY'));