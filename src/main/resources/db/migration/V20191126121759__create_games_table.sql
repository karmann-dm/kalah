create table games (
    id integer primary key,
    first_user integer,
    second_user integer,
    current_turn integer not null,
    status text not null
);