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

create table tbl_area(
	id int not null generated always as identity (start with 1 increment by 1),
	name varchar(64) not null,
	color_background varchar(7),
	color_line varchar(7),
	line_thickness double,
	transparency double,
	primary key(id)
);

drop table tbl_area;

create table tbl_area_postcodes(
	area_id int not null constraint fk_area_postcodes_area_id references tbl_area on delete cascade on update restrict,
	postcode varchar(5) not null constraint fk_area_postcodes_postcode references tbl_postcode on delete cascade on update restrict,
	primary key(area_id,postcode)
);

drop table tbl_area_postcodes;