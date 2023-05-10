
create table sms_certification (
       sms_certification_id bigint AUTO_INCREMENT PRIMARY KEY ,
       certification_number varchar(255),
       created_at timestamp(6),
       phone_number varchar(255)
);

create table REFRESH_TOKEN (
       REFRESH_TOKEN_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
       REFRESH_TOKEN_USE_PERSON_PHONE_NUMBER varchar(255) not null,
       REFRESH_TOKEN_INFO varchar(255) not null,
       primary key (REFRESH_TOKEN_ID)
);