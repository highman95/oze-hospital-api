--liquibase formatted sql
--changeset Emma NWAMAIFE:create-tables-001

CREATE TABLE staffs (
    id BIGINT auto_increment, 
    name VARCHAR NOT NULL, 
    uuid UUID NOT NULL default random_uuid(), 
    registration_date DATE default current_date(), 
    PRIMARY KEY (id), 
    UNIQUE (uuid)
);

CREATE TABLE patients (
    id BIGINT auto_increment, 
    name VARCHAR NOT NULL, 
    age TINYINT NOT NULL, 
    last_visit_date DATE default current_date(), 
    PRIMARY KEY (id)
);
