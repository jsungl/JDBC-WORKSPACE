--=============================================
--관리자계정
--=============================================
--student계정 생성 및 권한부여
create user student
identified by student
default tablespace users;

grant connect, resource to student;



--=============================================
--STUDENT 계정
--=============================================
set serveroutput on;

create table member(
    member_id varchar2(20),
    password varchar2(20) not null,
    member_name varchar2(100) not null,
    gender char(1),
    age number,
    email varchar2(200),
    phone char(11) not null,
    address varchar2(1000),
    hobby varchar2(100), --농구, 음악감상, 영화
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_member_gender check(gender in('M','F'))
);

insert into member
values(
    'hoggd', '1234', '홍길동', 'M', 33, 'hoggd@naver.com', '01012341234',
    '서울 강남구 테헤란로', '등산,그림,요리', default
);

insert into member
values(
    'sinsa', '1234', '신사임당', 'F', 48, 'sinsa@naver.com', '01013341234',
    '서울 강동구', '음악감상', default
);

insert into member
values(
    'sejong', '1234', '세종', 'M', 65, 'sejong@naver.com', '01044441234',
    '서울 관악구', '농구', default
);

insert into member
values(
    'leess', '1234', '이순신', 'M', 35, 'leess@naver.com', '01013345678',
    '서울 강북구', '무예', default
);

insert into member
values(
    'ygss', '1234', '유관순', 'F', null, null, '01031313131',
    null, null, default
);

select * from member order by enroll_date desc;
desc member;
select * from member where member_name like '%순신%';
--commit;

--drop table member_del;

create table member_del
as
select member.*, 
        sysdate del_date
from member
where 1 = 0;

--alter table member_del
--add constraint pk_member_del_id primary key(member_id);
--
--alter table member_del
--add constraint ck_member_del_gender check(gender in ('M','F'));


select * from member_del;

desc member_del;

--trigger
create or replace trigger trig_member_del
    before
    delete on member
    for each row
begin
    insert into member_del
    values(:old.member_id, :old.password, :old.member_name, :old.gender, :old.age, :old.email, :old.phone, :old.address, 
    :old.hobby, :old.enroll_date, sysdate); 
end;
/





----------------------------------------------------------------------------------------------------------------------------------
-- 상품재고관리 프로그램
----------------------------------------------------------------------------------------------------------------------------------

create table product_stock (
    PRODUCT_ID  VARCHAR2(30) ,
    PRODUCT_NAME  VARCHAR2(30)  NOT NULL,
    PRICE NUMBER(10)  NOT NULL,
    DESCRIPTION VARCHAR2(50),
    STOCK NUMBER DEFAULT 0,
    constraint pk_product_stock_id primary key(product_id)
);

create table product_io (
    IO_NO NUMBER PRIMARY KEY, --seq_product_io_no
    PRODUCT_ID VARCHAR2(30),
    IODATE DATE DEFAULT SYSDATE,
    AMOUNT NUMBER,
    STATUS CHAR(1) CHECK (STATUS IN ('I', 'O')),
    constraint fk_product_io_id foreign key(product_id) references product_stock(product_id)
);

create sequence seq_product_io_no
start with 1000;


--상품입출고테이블에 데이터가 삽입될때마다, 자동으로 재고테이블의 수량이
--변경될 수 있도록 트리거 작성
create or replace trigger trig_product_io
    before
    insert on product_io
    for each row
begin
    --입고할때
    if :new.status = 'I' then
        update product_stock
        set stock = stock + :new.amount
        where product_id = :new.product_id;
    --출고할때
    else
        update product_stock
        set stock = stock - :new.amount
        where product_id = :new.product_id;
     end if;

end;
/


--상품정보를 삭제하면, 해당 입출고 데이터도 삭제되도록 처리하세요
create or replace trigger trig_product_del
    before
    delete on product_stock
    for each row
begin
    delete product_io
    where product_id = :old.product_id;

end;
/

select * from product_stock;
select * from product_io;
--commit;
--delete from product_stock where product_id = 'nb_ss7'; 