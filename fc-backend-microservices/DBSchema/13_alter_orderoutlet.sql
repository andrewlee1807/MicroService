set search_path to fcvsmartshopper;

alter table orderoutlet add column receiveStartTime timestamp with time zone;
alter table orderoutlet add column receiveEndTime timestamp with time zone;
