-- Database: xmen_db

-- DROP DATABASE IF EXISTS xmen_db;

CREATE DATABASE xmen_db
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- Table: public.mutant_id

-- DROP TABLE IF EXISTS public.mutant_id;

CREATE TABLE IF NOT EXISTS public.mutant_id
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    adn_sequence text[] COLLATE pg_catalog."default",
    mutant boolean,
    CONSTRAINT mutant_id_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.mutant_id
    OWNER to postgres;