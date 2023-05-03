create table if not exists `user` (
    `id`  bigint auto_increment primary key,
    `email`      varchar(255) not null unique,
    `first_name` varchar(255) not null,
    `last_name`  varchar(255) not null,
    `password`   varchar(255) not null
);

create table if not exists `refresh_token` (
    `id` bigint auto_increment primary key,
    `expiration` datetime(6) not null,
    `revoked`    bit         not null,
    `user_id`   bigint      not null
);

create table if not exists `url`  (
    `id` bigint auto_increment primary key,
    `alias`      varchar(255) null unique,
    `created_at` datetime(6)  not null,
    `full_url`   varchar(255) not null,
    `updated_at` datetime(6)  not null,
    `user_id`    bigint       null
);

create table if not exists `kgs_keys` (
    `id` bigint auto_increment primary key,
    `machine_id` smallint
);

create table if not exists `image` (
    `id` bigint auto_increment primary key,
    `type` varchar(64) not null,
    `owner_id` bigint not null,
    `name` varchar(255) not null
)
