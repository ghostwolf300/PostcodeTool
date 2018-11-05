select * from tbl_postcode;
alter table tbl_polygon add geometry_type varchar(64);

select * from tbl_postcode;
select * from tbl_polygon;
select * from tbl_postcode_polygons;
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




