create table tbl_map(
	id int not null generated always as identity (start with 1 increment by 1),
	name varchar(64) not null,
	min_x double,
	min_y double,
	max_x double,
	max_y double,
	crs varchar(64),
	primary key(id)
);

create table tbl_area(
	id int not null generated always as identity (start with 1 increment by 1),
	map_id int not null,
	name_1 varchar(64) not null,
	name_2 varchar(64),
	name_3 varchar(64),
	name_4 varchar(64),
	primary key(id),
	constraint fk_area_map_id foreign key(map_id) references tbl_map on delete cascade on update restrict
);

create table tbl_collection(
	id int not null generated always as identity (start with 1 increment by 1),
	map_id int not null,
	name varchar(64) not null,
	color_background varchar(10),
	color_line varchar(10),
	transparency double,
	line_thickness double,
	primary key(id),
	constraint fk_collection_map_id foreign key(map_id) references tbl_map on delete cascade on update restrict
);

create table tbl_collection_areas(
	collection_id int not null,
	area_id int not null,
	primary key(collection_id,area_id),
	constraint fk_collection_areas_collection_id foreign key(collection_id) references tbl_collection on delete cascade on update restrict,
	constraint fk_collection_areas_area_id foreign key(area_id) references tbl_area on delete cascade on update restrict
);

create table tbl_polygon(
	id int not null generated always as identity (start with 1 increment by 1),
	area_id int not null,
	primary key(id),
	constraint fk_polygon_area_id foreign key(area_id) references tbl_area on delete cascade on update restrict
);

create table tbl_ring(
	id int not null generated always as identity (start with 1 increment by 1),
	polygon_id int not null,
	ring_type int not null,
	primary key(id),
	constraint fk_ring_polygon_id foreign key(polygon_id) references tbl_polygon on delete cascade on update restrict
);

create table tbl_coordinates(
	ring_id int not null, 
	order_num int not null,
	x double not null,
	y double not null,
	primary key(ring_id,order_num),
	constraint fk_coordinates_ring_id foreign key(ring_id) references tbl_ring on delete cascade on update restrict
);
