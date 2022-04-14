set search_path to fcvsmartshopper;

alter table outletpromotion add column modifieddate timestamp without time zone;
alter table outletpromotion add column notifysentdate timestamp without time zone;
ALTER TABLE outletpromotion ADD COLUMN easyordercode varchar(200);
