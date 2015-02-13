# Clients schema

# --- !Ups

CREATE TABLE clients (
    id SERIAL,
    number varchar(13) NOT NULL,
    public_key_modulus varchar(255) NOT NULL,
    public_key_exponent varchar(255) NOT NULL,
    validation_code varchar(255) NOT NULL,
    validation_code_sent_at timestamp without time zone NOT NULL,
    is_active boolean NOT NULL DEFAULT FALSE,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE clients;