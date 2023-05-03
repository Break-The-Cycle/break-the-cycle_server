
create table sms_certification (
       sms_certification_id bigint not null,
       certification_number varchar(255),
       created_at timestamp(6),
       phone_number varchar(255),
       primary key (sms_certification_id)
);