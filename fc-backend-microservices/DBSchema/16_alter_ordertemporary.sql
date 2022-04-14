set search_path = fcvsmartshopper;

alter table ordertemporary add column paymentstatus varchar(20);
alter table ordertemporary add column fromsource varchar(50);

alter table ordertemporary add column orderdetail text;

alter table ordertemporary add column orderId bigint;