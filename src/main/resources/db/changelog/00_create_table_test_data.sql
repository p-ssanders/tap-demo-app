create table test_data (
  id bigserial primary key,
  data varchar(255),
  created_at timestamp default current_timestamp
);