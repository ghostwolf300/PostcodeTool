create table tbl_postcode(
	postcode varchar(5) not null,
	name varchar(64),
	primary key(postcode)
);

create table tbl_ring(
	ring_id int not null,
	order_id int not null,
	x double not null,
	y double not null,
	primary key(ring_id, order_id)
);

create table tbl_polygon(
	id int not null generated always as identity (start with 1 increment by 1),
	outer_ring_id int not null,
	primary key(id)
);

create table tbl_polygon_inner_rings(
	polygon_id int not null constraint fk_polygon references tbl_polygon,
	ring_id int not null,
	primary key(polygon_id,ring_id)
);

create table tbl_postcode_polygons(
	postcode varchar(5) not null constraint fk_postcode references tbl_postcode on delete cascade on update restrict,
	polygon_id int not null,
	primary key(postcode,polygon_id)
);

