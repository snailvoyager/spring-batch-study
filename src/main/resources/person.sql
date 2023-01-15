create table person(
    id bigint primary key auto_increment,
    name varchar(255),
    age varchar(255),
    address varchar(255)
);

insert into person (name, age, address)
values ('홍길동','33','서울');
insert into person (name, age, address)
values ('유재석','40','인천');
insert into person (name, age, address)
values ('강호동','30','부산');