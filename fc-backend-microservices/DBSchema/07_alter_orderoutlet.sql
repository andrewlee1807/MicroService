SET SEARCH_PATH to fcvsmartshopper;

ALTER TABLE orderoutlet ADD COLUMN grabtxid varchar(200);
ALTER TABLE orderoutlet ADD COLUMN grabgrouptxid varchar(200);
ALTER TABLE orderoutlet ADD COLUMN token varchar(2000);

