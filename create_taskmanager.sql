drop schema if exists task_manager;

create schema task_manager;

use task_manager;

create table Teachers (
	ID_teacher int not null auto_increment primary key,
    Nome varchar (50) not null
);

create table Subjects (
	ID_subject int not null auto_increment primary key,
    ID_teacher int not null,
    Nome varchar (32) not null unique,
    foreign key (ID_teacher) references Teachers (ID_teacher)
);

create table TaskTypes (
	ID_type int not null auto_increment primary key,
    Nome varchar (16) not null
);

create table Tasks (
	ID_task int not null auto_increment primary key,
    ID_type int not null,
    ID_subject int not null,
    Descript varchar (50) not null,
    TaskDate date not null,
    foreign key (ID_type) references TaskTypes (ID_type),
    foreign key (ID_subject) references Subjects (ID_subject)
);

insert into TaskTypes values
	(1, "Test"), (2, "Homework"), (3, "Others");

-- Insert models
insert into Teachers values (1, "Romaldo");
insert into Subjects values (1, 1, "OOP");

SELECT * FROM Subjects WHERE Nome = "OOP";