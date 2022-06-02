create table if not exists USERS
(
    id       integer      not null primary key auto_increment,
    email    varchar(255) not null,
    login    varchar(255) not null,
    name     varchar(255) not null,
    birthday date,
    constraint USER_PK primary key (id)
);

create unique index if not exists USER_EMAIL_UINDEX on USERS (email);
create unique index if not exists USER_LOGIN_UINDEX on USERS (login);

create table if not exists FILMS
(
    id           bigint not null primary key auto_increment,
    name         varchar(255),
    description  varchar(200),
    release_date date,
    duration_sec bigint,
    constraint FILM_PK primary key (id)
);

create table if not exists FRIENDS
(
    user_id   int not null references USERS (id),
    friend_id int not null references USERS (id),

    PRIMARY KEY (user_id, friend_id)
);

create table if not exists LIKES
(
    user_id int references USERS (id),
    film_id int references FILMS (id),

    PRIMARY KEY (user_id, film_id)
);