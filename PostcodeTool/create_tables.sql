create table tbl_postcode(
	postcode varchar(5) not null,
	name varchar(64),
	primary key(postcode)
);


create table tbl_ring(
	id int not null generated always as identity (start with 1 increment by 1),
	ring_type int not null,
	primary key(id)
);

create table tbl_coordinates(
	ring_id int not null constraint fk_ring references tbl_ring on delete cascade on update restrict,
	order_num int not null,
	x double not null,
	y double not null,
	primary key(ring_id, order_num)

);

create table tbl_polygon(
	id int not null generated always as identity (start with 1 increment by 1),
	primary key(id)
);

create table tbl_polygon_rings(
	polygon_id int not null constraint fk_polygon references tbl_polygon on delete cascade on update restrict,
	ring_id int not null constraint fk_ring2 references tbl_ring on delete cascade on update restrict,
	primary key(polygon_id,ring_id)
);

create table tbl_postcode_polygons(
	postcode varchar(5) not null constraint fk_postcode references tbl_postcode on delete cascade on update restrict,
	polygon_id int not null,
	primary key(postcode,polygon_id)
);

