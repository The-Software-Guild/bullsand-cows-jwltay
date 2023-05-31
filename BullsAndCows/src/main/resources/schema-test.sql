CREATE TABLE IF NOT EXISTS games(id int primary key auto_increment, inProgress boolean default true, answer varchar(4));

create table if not exists rounds(id int primary key auto_increment, gameId int, guess varchar(4), result varchar(6), foreign key (gameId) references games(id));
