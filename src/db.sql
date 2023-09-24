create table arrival
(
    emp_Id varchar(10) null,
    time   varchar(10) null,
    date   varchar(20) null,
    constraint arrival_ibfk_1
        foreign key (emp_Id) references employee (Employee_id)
);

create index emp_Id
    on arrival (emp_Id);

create table customer
(
    cust_Id    varchar(10)  not null
        primary key,
    name       varchar(100) null,
    contact_NO varchar(15)  null,
    join_date  varchar(15)  null,
    address    varchar(50)  null,
    constraint contact_N0
        unique (contact_NO)
);

create table medicine
(
    med_Id       varchar(6)   not null
        primary key,
    name         varchar(100) null,
    type         varchar(15)  null,
    qty_on_stock int          null,
    price        int          null
);

create table order_detail
(
    order_id varchar(5) not null,
    med_Id   varchar(6) not null,
    qty      int        null,
    total    int        null,
    primary key (order_id, med_Id),
    constraint order_detail_ibfk_2
        foreign key (med_Id) references medicine (med_Id),
    constraint order_detail_orders_id_fk
        foreign key (order_id) references orders (id)
            on update cascade on delete cascade
);

create index med_Id
    on order_detail (med_Id);

create table orders
(
    id     varchar(5)     not null
        primary key,
    custId varchar(10)    null,
    price  decimal(10, 2) null,
    constraint orders_customer_cust_Id_fk
        foreign key (custId) references customer (cust_Id)
            on update cascade on delete cascade
);

create table payment
(
    pay_Id varchar(6)    not null
        primary key,
    type   varchar(15)   null,
    date   varchar(15)   null,
    total  decimal(7, 2) null
);

create table supple_order_detail
(
    order_id varchar(6)  not null,
    med_Id   varchar(10) not null,
    qty      int         null,
    total    int         null,
    primary key (order_id, med_Id),
    constraint supple_order_detail_supplier_order_id_fk
        foreign key (order_id) references supplier_order (id)
);

create index med_Id
    on supple_order_detail (med_Id);

create table supplier
(
    supplier_Id varchar(4)   not null
        primary key,
    name        varchar(100) null,
    nic         varchar(15)  null,
    address     text         null,
    contact_NO  varchar(15)  null,
    constraint contact_N0
        unique (contact_NO)
);

create table supplier_order
(
    id         varchar(20) not null,
    supplierId varchar(4)  null,
    constraint supplier_order_id_uindex
        unique (id),
    constraint supplier_order_supplier_supplier_Id_fk
        foreign key (supplierId) references supplier (supplier_Id)
);

alter table supplier_order
    add primary key (id);

create table user
(
    user_Id  varchar(6)   not null
        primary key,
    username varchar(100) not null,
    password varchar(15)  null,
    emp_Id   varchar(6)   null,
    constraint password
        unique (password),
    constraint user_ibfk_1
        foreign key (emp_Id) references employee (Employee_id)
);

create index emp_Id
    on user (emp_Id);

