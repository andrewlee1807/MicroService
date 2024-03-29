SET search_path to fcvsmartshopper;

CREATE TABLE MailTemplate (
  mailTemplateID BIGSERIAL NOT NULL PRIMARY KEY,
  templateName VARCHAR (255) NOT NULL,
  subject VARCHAR(255) NOT NULL,
  body TEXT NOT NULL,
  status INTEGER NOT NULL DEFAULT 1, -- 1: Active, 0: Inactive
  createdDate TIMESTAMP NOT NULL,
  modifiedDate TIMESTAMP,
  UNIQUE(TemplateName)
);

CREATE TABLE MailTracking (
  mailTrackingID BIGSERIAL NOT NULL PRIMARY KEY,
  recipient VARCHAR(255) NOT NULL,
  cc VARCHAR(255),
  bcc VARCHAR(255),
  subject VARCHAR(255) NOT NULL,
  body TEXT NOT NULL,
  status INTEGER NOT NULL DEFAULT 1, -- 1: Sent, 0: Waiting, -1: Fail
  note VARCHAR (255),
  createdDate TIMESTAMP NOT NULL,
  sentDate TIMESTAMP
);

INSERT INTO mailtemplate (templatename,subject,body,status,createddate,modifieddate) VALUES
('SUBMIT_ORDER','YOUR ORDER [[${orderId}]] CONFIRMATION','<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <link href=''https://fonts.googleapis.com/css?family=Roboto'' rel=''stylesheet''>
</head>

<body style=" margin: 0; font-family: ''Roboto'';font-size: 16px;color: #121111;">
<header style="background: linear-gradient(98.56deg, #1F054C -0.07%, #552263 51.01%, #D11287 99%);padding: 15px 20px; color: #ffffff; display: flex;">
    <div class="nav-brand" style="margin-right: 40px;"></div>
    <div class="flex-container" style="display: flex;width: 100%;">
        <div class="tni-info">
            <div class="tni-title" style="font-weight: bold; line-height: 150%; margin-bottom: 5px">
            </div>
            <span class="divine"></span>
            <div class="tni-address" style="width: 230px">
            </div>
        </div>
        <div class="tni-hotline" style="font-size: 18px; line-height: 150%; font-weight: bold; margin-top: 20px"></div>
    </div>
</header>
<section style=" padding: 30px;">
    <div class="section section-open">
        <p style="margin: 20px 0;">Dear <span th:text="${customerName}" style="color: red"></span>,</p>
        <div class="order-info" style="padding-top: 21px">
            <div class="order-title" style="line-height: 150%; margin-bottom: 18px">
                We’re happy to let you know that we have received your order. You can follow the status of your order by
                clicking on the link below:
            </div>
        </div>
        <p><a th:href=''${link}'' style="color:blue">Order Infomation</a></p>
    </div>
    <div class="more" style="margin-top: 20px">
        <p>If you have any questions, please call us at <span th:text="${hotline}" style="color: red"></span></p>
        <p>Thank you for choosing us and we hope that you will visit us again soon.</p>
    </div>
    <div class="signature" style="margin-top: 30px;">
        <p>Cheers,</p>
        <b>Customer Support</b>
    </div>
</section>
<footer style="padding: 25px 30px;background: #292F37; color: #F2F2F2;opacity: 0.8; display: flex">
    <div class="copywrite" style="width: 80%">
    </div>
    <div class="social">
    </div>
</footer>

</body>
</html>',1,'2020-07-24 00:00:00.000',NULL)
;
