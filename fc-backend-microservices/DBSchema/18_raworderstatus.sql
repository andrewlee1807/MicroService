CREATE TABLE rawOrderStatus
(
    orderStatusId BIGSERIAL   NOT NULL PRIMARY KEY,
    orderCode     varchar(50) NOT NULL,
    orderStatus   text        NOT NULL,
    createdDate   timestamp   NOT NULL,
    processStatus int         NOT NULL default 0
);
