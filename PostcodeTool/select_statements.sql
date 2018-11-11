select * from tbl_postcode;
alter table tbl_polygon add geometry_type varchar(64);

select * from tbl_postcode where map_id=101;
select * from tbl_polygon;
select * from tbl_postcode_polygons where map_id=101;
select * from tbl_ring;
select * from tbl_polygon_rings;
select * from tbl_coordinates;

select a.postcode,a.polygon_id,b.geometry_type from tbl_postcode_polygons a join tbl_polygon b 
on a.polygon_id=b.id;

select a.polygon_id,a.ring_id,b.ring_type 
from tbl_polygon_rings a join tbl_ring b on a.ring_id=b.id

select ring_id,order_num,x,y from tbl_coordinates where ring_id=6304 order by order_num;

select a.postcode,a.name from tbl_postcode a join tbl_area_postcodes b on a.postcode=b.postcode where b.area_id=1;

select * from tbl_area_postcodes where area_id=2;

select * from tbl_map;

delete from tbl_postcode where map_id=1;
delete from tbl_postcode_polygons where map_id=1;

select count(*) from tbl_postcode_polygons where map_id=101;
select count(*) from tbl_postcode_polygons where map_id=1;

select count(*) from tbl_postcode_polygons a join tbl_polygon_rings b on a.polygon_id=b.polygon_id where a.map_id=1;
select count(*) from tbl_postcode_polygons a join tbl_polygon_rings b on a.polygon_id=b.polygon_id where a.map_id=101;

select count(*) from tbl_coordinates;

select count(*) from tbl_coordinates where ring_id in 
(select ring_id from tbl_postcode_polygons a join tbl_polygon_rings b on a.polygon_id=b.polygon_id where a.map_id=101);

select map_id,postcode,name from tbl_postcode where postcode='74940';
select * from TBL_POSTCODE_POLYGONS where postcode='74940';
select * from tbl_polygon_rings where polygon_id=7852;
select * from tbl_coordinates where ring_id=7974;

select * from tbl_area_postcodes;

