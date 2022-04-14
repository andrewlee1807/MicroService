CREATE TABLE RawBrand
(
    RawBrandId   BIGSERIAL NOT NULL PRIMARY KEY,
    BrandName         varchar(256) ,
    BrandPriority         Integer,
    CreatedDate timestamp
);

CREATE TABLE RawCategory
(
    RawCategoryId   BIGSERIAL NOT NULL PRIMARY KEY,
    CategoryCode         varchar(128) ,
    CategoryName         varchar(256) ,
    Description         text,
    CreatedDate timestamp
);

CREATE TABLE RawProduct
(
    RawProductId   BIGSERIAL NOT NULL PRIMARY KEY,
    CategoryCode         varchar(128) ,
    CategoryName         varchar(256) ,
    BrandName         varchar(256) ,
    ProductCode         varchar(128) ,
    ProductName         varchar(256) ,
    UnitOfMeasure         varchar(128) ,
    BarCode         varchar(128) ,
    CostPrice         double precision ,
    SalesPrice         double precision ,
    PricePerCarton         double precision ,
    UnitsInCarton         integer,
    UnitsInVirtualPack        integer,
    Status        integer,
    CreatedDate timestamp
);

CREATE TABLE RawStore
(
    RawStoreId   BIGSERIAL NOT NULL PRIMARY KEY,
    StoreCode         varchar(128) ,
    StoreName         varchar(256) ,
    CompanyStoreCode         varchar(128) ,
    Birthday         varchar(256) ,
    CountryCode         varchar(64) ,
    Email         varchar(256) ,
    Address         varchar(256) ,
    CustomerGroup         varchar(256) ,
    Status         integer ,
    SalesmanCode         varchar(128) ,
    Latitude         double precision ,
    Longitude         double precision ,
    DistributorCode         varchar(128) ,
    CreatedDate timestamp
);

CREATE TABLE RawDistributor
(
    RawDistributorId   BIGSERIAL NOT NULL PRIMARY KEY,
    DistributorCode         varchar(128) ,
    DistributorName         varchar(256) ,
    DistributorPhone         varchar(128) ,
    Status         integer ,
    DistributorAddress         varchar(256) ,
    Latitude         double precision ,
    Longitude         double precision ,
    CreatedDate timestamp
);

alter table RawStore add column PhoneNumber varchar(128);