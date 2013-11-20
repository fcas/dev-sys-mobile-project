create table Usuario (login varchar(30) primary key not null, senha varchar(30), nome varchar(60), curso varchar(60), sobre varchar(500));
create table Lugar(id_lugar int primary key not null auto_increment, nome varchar(30));
create table Tarefas(id_tarefa int primary key not null auto_increment, usuario varchar(30) not null, id_lugar int, data varchar(10), horario varchar(10), descricao varchar(200));
create table Comentarios(id_comentario int primary key not null auto_increment, autor varchar(30) not null, comentario varchar(80) not null, id_lugar int);

alter table Tarefas add foreign key(id_lugar) references Lugar(id_lugar); 
alter table Comentarios add foreign key(id_lugar) references Lugar(id_lugar); 
