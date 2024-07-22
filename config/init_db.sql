CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name TEXT     NOT NULL
);

alter table resume
    owner to postgres;

CREATE TABLE contact
(
    id          SERIAL
        CONSTRAINT contact_pk pRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES resume
            ON UPDATE RESTRICT ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL

);

alter table contact
    owner to postgres;

CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);
