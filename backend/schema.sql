DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS funds;
DROP TABLE IF EXISTS subdivisions;
DROP TABLE IF EXISTS supervisors;
DROP TABLE IF EXISTS types_activities;
DROP TABLE IF EXISTS users;

CREATE TABLE activities (
    id      INTEGER PRIMARY KEY AUTOINCREMENT
                    NOT NULL,
    id_user INTEGER REFERENCES users (id) 
                    NOT NULL,
    id_type INTEGER REFERENCES types_activities (id) 
                    NOT NULL,
    value   REAL    NOT NULL,
    date    TEXT    NOT NULL
);

CREATE TABLE funds (
    id       INTEGER PRIMARY KEY AUTOINCREMENT 
                     NOT NULL,
    username TEXT    UNIQUE
                     NOT NULL,
    password TEXT    NOT NULL,
    email    TEXT    NOT NULL,
    name     TEXT    NOT NULL
);

CREATE TABLE subdivisions (
    id   INTEGER PRIMARY KEY AUTOINCREMENT
                 NOT NULL,
    name TEXT   NOT NULL
);

CREATE TABLE supervisors (
    id             INTEGER PRIMARY KEY AUTOINCREMENT
                           NOT NULL,
    username       TEXT    UNIQUE,
    password       TEXT    NOT NULL,
    email          TEXT    NOT NULL,
    name           TEXT    NOT NULL,
    id_subdivision INTEGER REFERENCES subdivisions (id) 
                          
);

CREATE TABLE types_activities (
    id          INTEGER PRIMARY KEY AUTOINCREMENT
                        NOT NULL,
    name        TEXT    NOT NULL,
    coefficient REAL    NOT NULL
);

CREATE TABLE users(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    username       TEXT    NOT NULL
                           UNIQUE,
    password       TEXT    NOT NULL,
    email          TEXT    NOT NULL,
    name           TEXT    NOT NULL,
    selected_fund  INTEGER REFERENCES funds (id) ON DELETE SET NULL,
    id_subdivision INTEGER REFERENCES subdivisions (id) 
                     
);
