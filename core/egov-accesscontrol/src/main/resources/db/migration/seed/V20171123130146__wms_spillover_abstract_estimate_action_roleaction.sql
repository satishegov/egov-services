insert into eg_action(id, name, url, servicecode, queryparams, ordernumber, displayname, enabled, createdby, createddate, lastmodifiedby, lastmodifieddate) values(nextval('SEQ_EG_ACTION'), 'Spillover Abstract Estimate', '/works-estimate/v1/abstractestimates/_create', 'AbstractEstimate', null, 4, 'Spillover Abstract Estimate', true, 1, now(), 1, now());
insert into eg_roleaction(roleCode,actionid,tenantid)values('SUPERUSER', (select id from eg_action where name = 'Spillover Abstract Estimate' and url='/works-estimate/v1/abstractestimates/_create' ), 'default');