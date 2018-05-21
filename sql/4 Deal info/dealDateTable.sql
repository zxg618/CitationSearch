USE [patstat2016b]

DROP TABLE unsw_bs_company_deal_date;

CREATE TABLE [dbo].[unsw_bs_company_deal_date](
	id int NOT NULL IDENTITY(1,1) primary key,
    company_id int null DEFAULT null,
    deal_date date,
	constraint fk4_ra_company_id foreign key (company_id) references unsw_bs_company (id)
);

CREATE NONCLUSTERED INDEX [unsw_bs_company_deal_date] ON [dbo].[unsw_bs_company_deal_date]
(
	[deal_date] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]

drop index unsw_bs_company_searchkey2 ON [dbo].[unsw_bs_company];
drop index unsw_bs_company_searchkey3 ON [dbo].[unsw_bs_company];

CREATE NONCLUSTERED INDEX [unsw_bs_company_searchkey2] ON [dbo].[unsw_bs_company]
(
    [english_name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]

CREATE NONCLUSTERED INDEX [unsw_bs_company_searchkey3] ON [dbo].[unsw_bs_company]
(
	[chinese_name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]

