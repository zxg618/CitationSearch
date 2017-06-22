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

select * from tls201_appln where appln_id in (416613766, 445941994, 423126132, 439476677);