create table statuses
(
    id         bigserial primary key,
    status     varchar(255)
);
insert into statuses (status)
values ('Not confirmed'),
('Confirmed')
('Paid'),
('Delivered');

create table order_statuses
(
    id         bigserial primary key,
    order_id   bigint references orders (id),
    status_id     bigint references statuses (id)
);
