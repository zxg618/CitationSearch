USE [patstat2016b]

CREATE TABLE [dbo].[unsw_bs_CIT_stats](
	id int NOT NULL IDENTITY(1,1) primary key,
    company_id int null DEFAULT null,
	source_company_id int null DEFAULT null,
    deal_date date,
	CN_CIT_AT_DEAL_ON_PAT_AT_DEAL int null DEFAULT null,
	FR_CIT_AT_DEAL_ON_PAT_AT_DEAL int null DEFAULT null,
	CN_CIT_AT_2016_ON_PAT_AT_DEAL int null DEFAULT null,
	FR_CIT_AT_2016_ON_PAT_AT_DEAL int null DEFAULT null,
	CN_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL int null DEFAULT null,
	FR_CIT_AT_2016_ON_PAT_3Y_AFTER_DEAL int null DEFAULT null,
	CN_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL int null DEFAULT null,
	FR_CIT_AT_2016_ON_PAT_5Y_AFTER_DEAL int null DEFAULT null,

	constraint fk6_CIT_STATS_companyID_TO_Company_id foreign key (company_id) references unsw_bs_company (id)
);
