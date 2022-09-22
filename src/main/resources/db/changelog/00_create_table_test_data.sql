create table test_data (
  id bigint not null auto_increment,
  data varchar(255) not null,
  created_at timestamp default now(),
  primary key (id)
);