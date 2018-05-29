delete from unsw_bs_citation;
delete from unsw_bs_patent;
delete from unsw_bs_company_applnt;


update unsw_bs_company
set patents_total = 0, citations_total = 0;

--select * from unsw_bs_company where patents_total <> 0 and citations_total <> 0;

DBCC CHECKIDENT ('[unsw_bs_patent]', RESEED, 0);
DBCC CHECKIDENT ('[unsw_bs_citation]', RESEED, 0);
DBCC CHECKIDENT ('[unsw_bs_company_applnt]', RESEED, 0);
DBCC CHECKIDENT ('[unsw_bs_company_deal_date]', RESEED, 0);
DBCC CHECKIDENT ('[unsw_bs_PAT_stats]', RESEED, 0);
