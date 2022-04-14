set search_path to fcvsmartshopper;

CREATE TABLE ordertemporary
(
    ordertemporaryid    bigserial not null primary key,
    customerid          bigint,
    orderoutletcode     varchar(50),
    amount              double precision,
    status              varchar(50),
    createdby           bigint,
    createdate          timestamp,
    promotioncode       varchar(50),
    receiverName        varchar(50),
    receiverPhone       varchar(50),
    receiverAddress     varchar(100),
    receiverLat         double precision,
    receiverLng         double precision,
    deliveryMethod      varchar(100),
    salesManUserId      bigint,
    usedPoint           integer,
    amountAfterUsePoint DOUBLE PRECISION,
    payment             varchar(100),
    note                varchar(200),
    deliveryDate        timestamp,
    grabTxId            varchar(100),
    grabGroupTxId       varchar(100),
    token               varchar(100),
    isOrderOnBeHalf     BOOLEAN,
    isConfirmed         BOOLEAN,
    state               varchar(100)
)
