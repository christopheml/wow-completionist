create table if not exists pets
(
    creatureId integer unique not null,
    qualityId  integer        not null,
    name       varchar(100)   not null,
    icon       varchar(100)   not null,
    canBattle  boolean        not null default TRUE
);
