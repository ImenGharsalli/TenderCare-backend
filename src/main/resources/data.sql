/**insert Job objects**/
insert into Job (jobfunction, description, location, postdate, expirationdate, estimatedbudget, imageid) values ('Pet care','Dog walking','Berlin',{ts '2017-11-20 00:00:00.000'},{ts '2017-12-20 00:00:00.000'},10,'1');
insert into Job (jobfunction, description, location, postdate, expirationdate, estimatedbudget, imageid) values ('Adult & senior care','Personal care','Berlin',{ts '2017-11-23 00:00:00.000'},{ts '2017-12-23 00:00:00.000'},17,'2');
insert into Job (jobfunction, description, location, postdate, expirationdate, estimatedbudget, imageid) values ('Child care','Out-of-home care','Berlin',{ts '2017-11-24 00:00:00.000'},{ts '2017-12-24 00:00:00.000'},12,'3');
insert into Job (jobfunction, description, location, postdate, expirationdate, estimatedbudget, imageid) values ('Child care','In-home care','Berlin',{ts '2017-11-25 00:00:00.000'},{ts '2017-12-25 00:00:00.000'},13,'4');
/**insert CareService objects**/
insert into Careservice (jobFucntion, overview, experienceLevel, location, availability, minPrice, maxPrice) values ('Child care','Out-of-home care','3 years','Berlin',6,12,15);
insert into Careservice (jobFucntion, overview, experienceLevel, location, availability, minPrice, maxPrice) values ('Child care','In-home care','7 years','Berlin',7,13,17);
insert into Careservice (jobFucntion, overview, experienceLevel, location, availability, minPrice, maxPrice) values ('Adult & senior care','Personal care','2 years','Berlin',5,10,18);
insert into Careservice (jobFucntion, overview, experienceLevel, location, availability, minPrice, maxPrice) values ('Pet care','Grooming','1 year','Berlin',12,14,18);
/**insert User objects**/
insert into User (firstName, lastName, email, location, password, description, imageId, accountName) values('Meredith', 'Grey', 'mgrey@gmail.com', 'Berlin', '123', '','MeredithGrey', 'MeredithGrey');
insert into User (firstName, lastName, email, location, password, description, imageId, accountName) values('Cristina', 'Yang', 'cyang@gmail.com', 'Berlin', '123', '','CristinaYang', 'CristinaYang');
insert into User (firstName, lastName, email, location, password, description, imageId, accountName) values('Neal', 'Caffery', 'ncaffery@gmail.com', 'Berlin', '123', '','NealCaffery', 'NealCaffery');
insert into User (firstName, lastName, email, location, password, description, imageId, accountName) values('Peter','Burke','pburke@gmail.com','Berlin','123','','PeterBurke','PeterBurke');
insert into User (firstName, lastName, email, location, password, description, imageId, accountName) values('Alex','Karev','akarev@gmail.com','Berlin','123','','AlexKarev','AlexKarev');
