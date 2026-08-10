ALTER TABLE inventory
ADD CONSTRAINT chk_inventory_qty_on_hand
CHECK (qty_on_hand >= 0);
ALTER TABLE inventory
ADD CONSTRAINT chk_inventory_qty_reserved
CHECK (qty_reserved >= 0);
ALTER TABLE inventory
ADD CONSTRAINT chk_inventory_qty_available
CHECK (qty_available >= 0);