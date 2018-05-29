USE [patstat2016b]

CREATE TABLE [dbo].[unsw_bs_PAT_stats](
	id int NOT NULL IDENTITY(1,1) primary key,
    company_id int null DEFAULT null,
	source_company_id int null DEFAULT null,
    deal_date date,
	CN_PAT_AT_DEAL_ALL int null DEFAULT null,
	CN_PAT_AT_DEAL_U int null DEFAULT null,
	CN_PAT_AT_DEAL_I int null DEFAULT null,
	CN_PAT_AT_DEAL_A int null DEFAULT null,

	CN_PAT_3Y_AFTER_DEAL_ALL int null DEFAULT null,
	CN_PAT_3Y_AFTER_DEAL_U int null DEFAULT null,
	CN_PAT_3Y_AFTER_DEAL_I int null DEFAULT null,
	CN_PAT_3Y_AFTER_DEAL_A int null DEFAULT null,

	CN_PAT_5Y_AFTER_DEAL_ALL int null DEFAULT null,
	CN_PAT_5Y_AFTER_DEAL_U int null DEFAULT null,
	CN_PAT_5Y_AFTER_DEAL_I int null DEFAULT null,
	CN_PAT_5Y_AFTER_DEAL_A int null DEFAULT null,

	FR_PAT_AT_DEAL_ALL int null DEFAULT null,
	FR_PAT_AT_DEAL_U int null DEFAULT null,
	FR_PAT_AT_DEAL_I int null DEFAULT null,
	FR_PAT_AT_DEAL_A int null DEFAULT null,

	FR_PAT_3Y_AFTER_DEAL_ALL int null DEFAULT null,
	FR_PAT_3Y_AFTER_DEAL_U int null DEFAULT null,
	FR_PAT_3Y_AFTER_DEAL_I int null DEFAULT null,
	FR_PAT_3Y_AFTER_DEAL_A int null DEFAULT null,

	FR_PAT_5Y_AFTER_DEAL_ALL int null DEFAULT null,
	FR_PAT_5Y_AFTER_DEAL_U int null DEFAULT null,
	FR_PAT_5Y_AFTER_DEAL_I int null DEFAULT null,
	FR_PAT_5Y_AFTER_DEAL_A int null DEFAULT null,

	constraint fk5_PAT_STATS_companyID_TO_Company_id foreign key (company_id) references unsw_bs_company (id)
);
