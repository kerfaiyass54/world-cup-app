CREATE TABLE IF NOT EXISTS nation (
                                      id                   BIGSERIAL PRIMARY KEY,
                                      name                 VARCHAR(100) NOT NULL UNIQUE,
    continent            VARCHAR(50)  NOT NULL,
    number_participation INTEGER      NOT NULL DEFAULT 0,
    last_participation   INTEGER
    );

CREATE TABLE IF NOT EXISTS cup (
                                   id     BIGSERIAL PRIMARY KEY,
                                   year   INTEGER      NOT NULL UNIQUE,
                                   host   VARCHAR(100) NOT NULL,
    winner VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS participation (
                                             id        BIGSERIAL PRIMARY KEY,
                                             id_nation BIGINT NOT NULL REFERENCES nation(id),
    id_cup    BIGINT NOT NULL REFERENCES cup(id),
    CONSTRAINT uq_nation_cup UNIQUE (id_nation, id_cup)
    );