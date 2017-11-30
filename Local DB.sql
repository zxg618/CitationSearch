--select * from unsw_bs_patent where id = 110;
--select * from tls211_pat_publn where pat_publn_id = 416613767;
--select * from tls201_appln where appln_id = 416613766;
--select * from tls209_appln_ipc where appln_id = 416613766;

--select top 10 appIpc.* from tls201_appln as appln
--join tls209_appln_ipc as appIpc
--on appln.appln_id = appIpc.appln_id
--and (appIpc.ipc_class_symbol = 'F01M  11/00' or appIpc.ipc_class_symbol = 'F01M  11/03')
--and appIpc.ipc_gener_auth = 'CN'
--and appln.appln_id in (445941994, 423126132, 439476677);

--select appln_id from tls211_pat_publn where publn_nr in ('105003317', '104500167', '104121059');

--select * from tls209_appln_ipc where appln_id = 416613766;
--select * from tls209_appln_ipc where appln_id in (445941994, 423126132, 439476677);

--select * from tls201_appln where appln_id in (416613766, 445941994, 423126132, 439476677);

--select * from tls211_pat_publn where pat_publn_id = 457963336;

--select * from tls228_docdb_fam_citn where docdb_family_id = 50374664

--select * from unsw_bs_company

--select * from tls207_pers_appln where person_id in (19583898, 19583899, 19599018, 19599079, 43921722, 44026432, 46630885, 48856840, 50420031)

--select top 100 * from tls207_pers_appln where applt_seq_nr > 1

--drop table [dbo].[unsw_bs_company_applnt1];
--CREATE TABLE [dbo].[unsw_bs_company_applnt1](
--	id int NOT NULL IDENTITY(1,1) primary key,
--    company_id int not null DEFAULT 0,
--	person_id int not null DEFAULT 0,
--	company_name varchar(500) not null,
--	constraint fk4_ra_company_id foreign key (company_id) references unsw_bs_company (id),
--	constraint fk1_epo_person_id foreign key (person_id) references tls206_person (person_id)
--);


--select * from unsw_bs_patent where company_id = 1
--select appln_id, pat_publn_id, publn_date from dbo.tls211_pat_publn where publn_nr = '101376335' and publn_auth in ('CN', '')

--select * from tls206_person as person1 
--, tls206_person as person2 where person1.person_id in (44143047)
--and person2.person_name = person1.person_name;

--select * from tls206_person where person_name = (select person_name from tls206_person where person_id = 44143047)

--select * from unsw_bs_company_applnt where person_id = 42239199;

--select * from tls207_pers_appln where person_id = 42239199;

--select * from tls211_pat_publn where appln_id = 331345257;


--delete from unsw_bs_citation;
--delete from unsw_bs_patent;


--update unsw_bs_company
--set patents_total = 0, citations_total = 0;

--select * from unsw_bs_company where patents_total <> 0 and citations_total <> 0;

--DBCC CHECKIDENT ('[unsw_bs_patent]', RESEED, 0);
--DBCC CHECKIDENT ('[unsw_bs_citation]', RESEED, 0);
--select count(*) from unsw_bs_patent;
--select count(*) from unsw_bs_citation;

select appln.appln_auth, appln.appln_nr, appln.appln_nr_epodoc, appln.appln_kind, appln.appln_filing_date, appln.earliest_filing_date, docdb_family_id 
from tls201_appln appln join tls211_pat_publn publn on appln.appln_id = publn.appln_id and publn.pat_publn_id = 418067657;

select appln.appln_auth, appln.appln_nr, appln.appln_nr_epodoc, appln.appln_kind, appln.appln_filing_date, appln.earliest_filing_date, docdb_family_id 
from tls201_appln appln join tls211_pat_publn publn on appln.appln_id = publn.appln_id where publn.pat_publn_id = 418067657;

--select * from tls211_pat_publn where pat_publn_id = 266826938;
select appln.appln_auth, appln.appln_nr, appln.appln_nr_epodoc, appln.appln_kind, appln.appln_filing_date, appln.earliest_filing_date, docdb_family_id  from tls201_appln as appln where appln_id IN (select appln_id from tls211_pat_publn where pat_publn_id = 418067657);