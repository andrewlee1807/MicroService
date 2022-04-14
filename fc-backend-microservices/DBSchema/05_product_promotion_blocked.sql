set search_path to fcvsmartshopper;

CREATE TABLE productpromotionblocked (
	productpromotionblockedid bigserial NOT NULL,
	productoutletid int8 NULL,
	outletid int8 NULL,
	PRIMARY KEY (productpromotionblockedid),
	FOREIGN KEY (outletid) REFERENCES outlet(outletid),
	FOREIGN KEY (productoutletid) REFERENCES productoutlet(productoutletid)
);