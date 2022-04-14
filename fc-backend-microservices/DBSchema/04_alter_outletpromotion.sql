set search_path to fcvsmartshopper;

alter table outletpromotion add column newpromotionjson text;
alter table outletpromotion add column promotionrule text;
alter table outletpromotion add column priority Integer;
alter table outletpromotion add column promotionproperty Integer;
alter table outletpromotion add column maxPerPromotion Integer;
alter table outletpromotion add column maxpercustomer Integer;
alter table outletpromotion add column maxperorder Integer;
alter table outletpromotion add column penetrationtype Integer;
alter table outletpromotion add column penetrationvalue text;
alter table outletpromotion add column promotionadmin bigint;
alter table outletpromotion add column applyshopbyadmin text;
ALTER TABLE outletpromotion RENAME COLUMN promotionproperty TO iscombined;
ALTER TABLE outletpromotion ALTER COLUMN masterpromotionid DROP NOT NULL;
ALTER TABLE adminpromotion ALTER COLUMN masterpromotionid DROP NOT NULL;

