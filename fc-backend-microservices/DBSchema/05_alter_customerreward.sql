set search_path to fcvsmartshopper;

alter table customerReward add column productoutletskuid BIGINT;
alter table customerReward add column typediscount INTEGER; -- 0 : percentOff; 1 : amountOff
alter table customerReward add column valuediscount DOUBLE PRECISION;
alter table customerReward add constraint fk_customerreward_productoutletsku foreign key (productoutletskuid) references productoutletsku(productoutletskuid);

alter table customerReward add column expireddate timestamp without time zone;  -- expired date of Promotion Code exists for NextPurchase

alter table customerReward add column outletpromotionid BIGINT;
alter table customerReward add constraint fk_customerreward_outletpromotion foreign key (outletpromotionid) references outletpromotion(outletpromotionid);

alter table customerReward add column orderoutletid BIGINT;
alter table customerReward add constraint fk_customerreward_orderoutlet foreign key (orderoutletid) references orderoutlet(orderoutletid);


