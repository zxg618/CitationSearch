USE [patstat2016b]
GO
IF  EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[tls201_appln]') AND name = N'IX_tls201_docdb_family_id')
DROP INDEX [IX_tls201_docdb_family_id] ON [dbo].[tls201_appln] WITH ( ONLINE = OFF )
GO

CREATE NONCLUSTERED INDEX [IX_tls201_docdb_family_id] ON [dbo].[tls201_appln] 
(
	[docdb_family_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

