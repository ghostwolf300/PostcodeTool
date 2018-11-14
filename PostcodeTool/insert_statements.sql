insert into tbl_area(name,color_background,color_line,line_thickness,transparency)
values('Testialue 01','#CCF62E','#000000',1.0,0.25);

insert into tbl_area_postcodes(area_id,postcode) values(1,'41360');
insert into tbl_area_postcodes(area_id,postcode) values(1,'41370');
insert into tbl_area_postcodes(area_id,postcode) values(1,'41390');
insert into tbl_area_postcodes(area_id,postcode) values(1,'41400');
insert into tbl_area_postcodes(area_id,postcode) values(1,'41410');

insert into tbl_map (name,min_x,min_y,max_x,max_y,crs)
values ('Postinumeroalueet 2018 EPSG 4326 (WGS84)',59.45414797,19.08320879,70.09229322,31.58670394,'urn:ogc:def:crs:EPSG::4326');

insert into tbl_map (name,min_x,min_y,max_x,max_y,crs)
values ('Postinumeroalueet 2018 EPSG 3067',61686.15199999977,6605838.889000002,732907.7230000021,7776450.217000008,'urn:ogc:def:crs:EPSG::3067');

update tbl_map set name='Postinumeroalueet 2018 EPSG 4326' where id=1;
