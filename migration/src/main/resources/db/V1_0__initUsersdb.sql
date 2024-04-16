create database application;

--User for migration flyway
create role migrusr login createdb createrole replication password 'migrusr';
create role applusr login createdb createrole replication password 'applusr';
GRANT ALL ON DATABASE fynative_application TO migrusr, applusr;
GRANT ALL ON DATABASE fynative_audit TO migrusr, applusr;

create role dfyna_app nocreatedb nocreaterole login password 'dfyna_app';

ALTER ROLE dfyna_app SET search_path TO application;

ALTER ROLE applusr SET search_path TO application;