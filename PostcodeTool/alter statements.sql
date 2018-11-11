
alter table tbl_coordinates drop constraint fk_coordinates_ring_id;
alter table tbl_coordinates drop constraint fk_coordinates_map_id;
alter table tbl_coordinates drop constraint pk_coordinates;
alter table tbl_coordinates drop column map_id;
alter table tbl_coordinates add constraint fk_coordinates_ring_id foreign key(ring_id) references tbl_ring on delete cascade on update restrict;
alter table tbl_coordinates add constraint pk_coordinates primary key(ring_id,order_num);

alter table tbl_coordinates drop primary key;

alter table tbl_coordinates add column map_id int not null default 101;

select * from tbl_coordinates;

alter table tbl_coordinates add constraint fk_coordinates_ring_id foreign key(ring_id) references tbl_ring on delete cascade on update restrict;
alter table tbl_coordinates add constraint fk_coordinates_map_id foreign key(map_id) references tbl_map on delete cascade on update restrict;
alter table tbl_coordinates add constraint pk_coordinates primary key(ring_id,map_id,order_num);


alter table tbl_postcode drop primary key;
alter table tbl_postcode add column map_id int not null default 101;
alter table tbl_postcode add constraint fk_postcode_map_id foreign key(map_id) references tbl_map on delete cascade on update restrict;
alter table tbl_postcode add constraint pk_postcode primary key(map_id,postcode);

alter table tbl_postcode_polygons drop primary key;
alter table tbl_postcode_polygons drop constraint fk_postcode;
alter table tbl_postcode_polygons add column map_id int not null default 101
alter table tbl_postcode_polygons add constraint fk_postcode_polygons_postcode foreign key(map_id,postcode) references tbl_postcode(map_id,postcode);
alter table tbl_postcode_polygons add constraint pk_postcode_polygons primary key(postcode,map_id,polygon_id);

alter table tbl_area_postcodes drop primary key;
alter table tbl_area_postcodes drop constraint fk_area_postcodes_postcode;
alter table tbl_area_postcodes add column map_id int not null default 101;
alter table tbl_area_postcodes add constraint pk_area_postcodes primary key(area_id,postcode,map_id);

alter table tbl_area add column map_id int not null default 101;
alter table tbl_area add constraint fk_area_map_id foreign key(map_id) references tbl_map on delete cascade on update restrict;
