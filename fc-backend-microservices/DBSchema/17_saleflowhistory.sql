CREATE TABLE salesflowhistory
(
    requestId   BIGSERIAL    NOT NULL PRIMARY KEY,
    uri         varchar(255) NOT NULL,
    requestBody text         NOT NULL,
    response    text         NOT NULL,
    status      INTEGER      NOT NULL,
    sendTime    timestamp    NOT NULL
);
