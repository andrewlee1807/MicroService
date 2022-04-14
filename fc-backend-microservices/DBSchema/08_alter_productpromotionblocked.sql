set search_path = fcvsmartshopper;

ALTER TABLE productpromotionblocked RENAME COLUMN productoutletid TO productoutletskuid;
ALTER TABLE productpromotionblocked DROP CONSTRAINT productpromotionblocked_productoutletid_fkey;
ALTER TABLE productpromotionblocked ADD FOREIGN KEY (productoutletskuid) REFERENCES productoutletsku;

ALTER TABLE productpromotionblocked ADD COLUMN productid int8 NULL;
ALTER TABLE productpromotionblocked ADD FOREIGN KEY (productid) REFERENCES product;
