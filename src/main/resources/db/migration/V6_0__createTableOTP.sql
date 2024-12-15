set search_path TO auth_server;

create table if not exists ec_user_otp
(
    ec_user_otp_id  bigserial primary key,
    user_id      integer       not null,
    otp_hash        varchar(255) not null,
    created_at      timestamp    not null,
    used_at         timestamp,
    expiration_date timestamp    not null,
    constraint fk_user_otp_user foreign key (user_id) references ec_user (user_id)
);


