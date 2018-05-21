USE [patstat2016b]

DROP TABLE unsw_bs_company_applnt;
DROP TABLE unsw_bs_citation;
DROP TABLE unsw_bs_patent;
DROP TABLE unsw_bs_company;

delete from tls211_pat_publn where pat_publn_id = 111;
delete from tls201_appln where appln_id = 201;


CREATE TABLE [dbo].[unsw_bs_company](
	id int NOT NULL IDENTITY(1,1) primary key,
    chinese_name nvarchar(400) COLLATE Chinese_PRC_BIN not null,
    search_keyword nvarchar(400) COLLATE Chinese_PRC_BIN not null,
	english_name varchar(500) not null,
	source_file_id int not null,
    patents_total int NOT NULL DEFAULT 0,
	citations_total int NOT NULL DEFAULT 0,
);

CREATE TABLE [dbo].[unsw_bs_patent](
	id int NOT NULL IDENTITY(1,1) primary key,
    publication_number varchar(30) not null,
    company_id int not null DEFAULT 0,
    pat_publn_id int NOT NULL DEFAULT 0,
    publication_date date null,
    application_number varchar(30) not null,
    priority_number varchar(30) not null,
    prefix varchar(10) not null,
    postfix varchar(10) not null,
    filing_date date null,
    earliest_filing_date date not null,
    docdb_family_id int not null DEFAULT 0,
	citations_total int NOT NULL DEFAULT 0,
	constraint fk_ra_company_id foreign key (company_id) references unsw_bs_company (id),
	constraint fk_epo_patent_id foreign key (pat_publn_id) references tls211_pat_publn (pat_publn_id)
);

CREATE TABLE [dbo].[unsw_bs_citation](
	id int NOT NULL IDENTITY(1,1) primary key,
	patent_id int not null DEFAULT 0,
    company_id int not null DEFAULT 0,
	citing_patent_id int not null DEFAULT 0,
    citing_publication_number varchar(30) not null,
    citing_publication_date date null,
    citing_application_number varchar(30) not null,
    citing_priority_number varchar(30) not null,
    citing_prefix varchar(10) not null,
    citing_postfix varchar(10) not null,
    citing_filing_date date null,
    citing_earliest_filing_date date null,
    citing_application_docdb_family_id int not null DEFAULT 0,
    citn_id int NOT NULL DEFAULT 0,
	constraint fk_ra_patent_id foreign key (patent_id) references unsw_bs_patent (id),
	constraint fk2_ra_company_id foreign key (company_id) references unsw_bs_company (id),
	constraint fk2_epo_patent_id foreign key (citing_patent_id) references tls211_pat_publn (pat_publn_id)
);

CREATE TABLE [dbo].[unsw_bs_company_applnt](
	id int NOT NULL IDENTITY(1,1) primary key,
    company_id int not null DEFAULT 0,
	person_id int not null DEFAULT 0,
	company_name varchar(500) not null,
	constraint fk3_ra_company_id foreign key (company_id) references unsw_bs_company (id),
	constraint fk_epo_person_id foreign key (person_id) references tls206_person (person_id)
);

CREATE NONCLUSTERED INDEX [unsw_bs_company_searchkey] ON [dbo].[unsw_bs_company] 
(
	[search_keyword] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]


CREATE NONCLUSTERED INDEX [unsw_bs_patent_all] ON [dbo].[unsw_bs_patent] 
(
	[publication_number] ASC,
	[company_id] ASC,
	[pat_publn_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]


CREATE NONCLUSTERED INDEX [unsw_bs_citation_all] ON [dbo].[unsw_bs_citation] 
(
	[patent_id] ASC,
	[company_id] ASC,
	[citing_patent_id] ASC,
	[citn_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]


CREATE NONCLUSTERED INDEX [unsw_bs_company_applnt_all] ON [dbo].[unsw_bs_company_applnt] 
(
	[company_id] ASC,
	[person_id] ASC,
	[company_name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = OFF) ON [PRIMARY]

INSERT INTO [dbo].[tls201_appln]
           ([appln_id]
           ,[appln_auth]
           ,[appln_nr]
           ,[appln_kind]
           ,[appln_filing_date]
           ,[appln_filing_year]
           ,[appln_nr_epodoc]
           ,[appln_nr_original]
           ,[ipr_type]
           ,[internat_appln_id]
           ,[int_phase]
           ,[reg_phase]
           ,[nat_phase]
           ,[earliest_filing_date]
           ,[earliest_filing_year]
           ,[earliest_filing_id]
           ,[earliest_publn_date]
           ,[earliest_publn_year]
           ,[earliest_pat_publn_id]
           ,[granted]
           ,[docdb_family_id]
           ,[inpadoc_family_id]
           ,[docdb_family_size]
           ,[nb_citing_docdb_fam]
           ,[nb_applicants]
           ,[nb_inventors])
     VALUES
           (201
           ,'XX'
           ,'XXXXXXXX'
           ,'X'
           ,'1900-01-01'
           ,1
           ,'XXXXXXXXXX'
           ,'XXXXXXXXXX'
           ,'X'
           ,201
           ,'X'
           ,'X'
           ,'X'
           ,'1900-01-01'
           ,1
           ,201
           ,'1900-01-01'
           ,1
           ,201
           ,1
           ,1
           ,1
           ,1
           ,1
           ,1
           ,1)

INSERT INTO [dbo].[tls211_pat_publn]
           ([pat_publn_id]
           ,[publn_auth]
           ,[publn_nr]
           ,[publn_nr_original]
           ,[publn_kind]
           ,[appln_id]
           ,[publn_date]
           ,[publn_lg]
           ,[publn_first_grant]
           ,[publn_claims])
     VALUES
           (111
           ,'CN'
           ,'xxxxxxxxx'
           ,'xxxxxxxxx'
           ,'NA'
           ,201
           ,'1900-01-01'
           ,'X'
           ,1
           ,0)