package citationsearch.mapper;

import static citationsearch.constants.Constants.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import citationsearch.entity.Citation;
import citationsearch.entity.Company;
import citationsearch.entity.CompanyApplicant;
import citationsearch.entity.Patent;

public class PatentMapper extends Mapper {
	protected HashSet<Integer> personIds = null;
	
	public PatentMapper() {
		this.personIds = new HashSet<Integer>();
	}
	
	public Patent get(int id) {
		Patent patent = new Patent();
		
		this.query = "select * from " + Patent.TABLE + " where id = " + id;
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				patent.setID(rs.getInt("id"));
				patent.setPublicationNumber(rs.getString("publication_number"));
				patent.setCompanyId(rs.getInt("company_id"));
				patent.setPatPublnId(rs.getInt("pat_publn_id"));
				patent.setPublnDateBySqlDate(rs.getDate("publication_date"));
				patent.setApplnNum(rs.getString("application_number"));
				patent.setPriorityNum(rs.getString("priority_number"));
				patent.setPrefix(rs.getString("prefix"));
				patent.setPostfix(rs.getString("postfix"));
				patent.setAppFilingDateBySqlDate(rs.getDate("filing_date"));
				patent.setAppEarliestDateBySqlDate(rs.getDate("EARLIEST_FILING_DATE"));
				patent.setDocdbFamId(rs.getInt("docdb_family_id"));
				patent.setCitationTotal(rs.getInt("citations_total"));
				return patent;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Patent[] getPatentsByCompanyId(int companyId) {
		ArrayList<Patent> patentList = new ArrayList<Patent>();
		
		this.query = "select * from " + Patent.TABLE + " where company_id = " + companyId;
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Patent pat = new Patent();
				pat.setID(rs.getInt("id"));
				pat.setPublicationNumber(rs.getString("publication_number"));
				pat.setCompanyId(rs.getInt("company_id"));
				pat.setPatPublnId(rs.getInt("pat_publn_id"));
				pat.setPublnDateBySqlDate(rs.getDate("publication_date"));
				pat.setApplnNum(rs.getString("application_number"));
				pat.setPriorityNum(rs.getString("priority_number"));
				pat.setPrefix(rs.getString("prefix"));
				pat.setPostfix(rs.getString("postfix"));
				pat.setAppFilingDateBySqlDate(rs.getDate("filing_date"));
				pat.setAppEarliestDateBySqlDate(rs.getDate("earliest_filing_date"));
				pat.setDocdbFamId(rs.getInt("docdb_family_id"));
				pat.setCitationTotal(rs.getInt("citations_total"));
				patentList.add(pat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return patentList.toArray(new Patent[0]);
	}

	public int getPatentIdByPublicationNumber(String pubNum) {
		this.query = "select id from " + Patent.TABLE + " where publication_number = \'" + pubNum + "\'";
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int create(Patent patent) {
		this.query = "insert into " + Patent.TABLE + " (publication_number, company_id, pat_publn_id, publication_date, application_number, priority_number, prefix, postfix, filing_date, earliest_filing_date, docdb_family_id, citations_total) values ("
				+ "\'" + patent.getPublicationNumber()
				+ "\', "
				+ patent.getCompanyId()
				+ ", "
				+ patent.getPatPublnId()
				+ ", \'"
				+ patent.getPublnDateString()
				+ "\', \'"
				+ patent.getApplnNum()
				+ "\', \'"
				+ patent.getPriorityNum()
				+ "\', \'"
				+ patent.getPrefix()
				+ "\', \'"
				+ patent.getPostfix()
				+ "\', \'"
				+ patent.getApplnDateString()
				+ "\', \'"
				+ patent.getAppEarliestDateString()
				+ "\', "
				+ patent.getDocdbFamId()
				+ ", "
				+ patent.getCitationTotal()
				+ ")";
		
		int result = this.executeOtherQuery();
		
		return result;
	}
	
	public void getAllRelatedPatents(Company company, Patent patent) {
		int appId = 0;
		int patPubId = patent.getPatPublnId();
		ResultSet rs = null;
		int personId = 0;
		String personName = "";
		CompanyMapper cm = new CompanyMapper();
		String personQuery1 = "";
		String personQuery2 = "";
		ArrayList<Patent> patentList = new ArrayList<Patent>();
		int i = 0;
		int continueFlag = 0;
		java.sql.Date pubDate = null;
		
		System.out.println("Processing patent id........... " + patent.getID());
		
		if (patPubId == DUMP_PAT_ID) {
			this.query = "select appln_id, pat_publn_id, publn_date from dbo.tls211_pat_publn where publn_nr = \'" + patent.getPublicationNumber() + "\' and publn_auth in " + COUNTRY_CODE_LIST;
			rs = this.executeGetQuery();
			
			//System.out.println("Searching patent id " + patent.getID() + " with publication number [" + patent.getPublicationNumber() + "] in EPO DB.");
			
			try {
				if (rs.next()) {
					appId = rs.getInt("appln_id");
					patPubId = rs.getInt("pat_publn_id");
					pubDate = rs.getDate("publn_date");
					//System.out.println("Result found with patPubId = [" + patPubId + "].");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (patPubId == 0) {
			//System.out.println("Patent id " + patent.getID() + " with publication number [" + patent.getPublicationNumber() + "] exists in WIPO ONLY.");
			return;
		}
		else {
			
			//System.out.println("Patent id " + patent.getID() + " with publication number [" + patent.getPublicationNumber() + "] already linked to EPO DB");
			
			this.query = "select appln_id, publn_date from dbo.tls211_pat_publn where pat_publn_id = " + patPubId;
			rs = this.executeGetQuery();
			try {
				if (rs.next()) {
					appId = rs.getInt("appln_id");
					pubDate = rs.getDate("publn_date");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (appId == 0 || patPubId == 0) {
			//System.out.println("Result not found.");
			patent.setPatPublnId(0);
			this.save(patent);
			return;
		}

		patent.setPatPublnId(patPubId);
		patent.setPublnDateBySqlDate(pubDate);
		
		this.query = "select appln_auth, appln_nr, appln_nr_epodoc, appln_kind, appln_filing_date, earliest_filing_date, docdb_family_id from tls201_appln where appln_id = " + appId;
		
		rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				patent.setApplnNum(rs.getString("appln_nr"));
				patent.setPriorityNum(rs.getString("appln_nr_epodoc"));
				patent.setPrefix(rs.getString("appln_auth"));
				patent.setPostfix(rs.getString("appln_kind"));
				patent.setAppFilingDateBySqlDate(rs.getDate("appln_filing_date"));
				patent.setAppEarliestDate(rs.getDate("earliest_filing_date"));
				patent.setDocdbFamId(rs.getInt("docdb_family_id"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.save(patent);
		
		ArrayList<String> personIds = new ArrayList<String>();
		//go through left route
		this.query = "select person_id, person_name from tls206_person where person_id in ("
				+ "select person_id from tls207_pers_appln where appln_id = " + appId
				+ " and APPLT_SEQ_NR = 1 and invt_seq_nr = 0) and psn_sector = \'COMPANY\' and person_ctry_code in " + COUNTRY_CODE_LIST + " "
				//+ "and person_id not in (select person_id from " + Company.TRANS_TABLE + " where company_id = " + company.getID() + ")"
				;
		
		rs = this.executeGetQuery();
		
		//personQuery1 = this.query.replaceAll(", person_name", "");
		//personQuery1 = personQuery1.replace("and person_id not in (select person_id from tls010_company_applnt)", "");
		
		try {
			while (rs.next()) {
				continueFlag++;
				personId = rs.getInt("person_id");
				if (cm.isValidPerson(company, personId)) {
					personIds.add(Integer.toString(personId));
					personName = rs.getString("person_name");
					CompanyApplicant compApplnt = new CompanyApplicant();
					compApplnt.setCompanyId(company.getID());
					compApplnt.setPersonId(personId);
					compApplnt.setCompanyName(personName);
					cm.createCompantApplicant(compApplnt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//go through right route
		this.query = "select person_id, person_name from tls206_person where person_id in ("
				+ "select person_id from tls227_pers_publn where pat_publn_id = " + patPubId
				+ " and APPLT_SEQ_NR = 1 and invt_seq_nr = 0) and psn_sector = \'COMPANY\' and person_ctry_code in " + COUNTRY_CODE_LIST + " "
				//+ "and person_id not in (select person_id from " + Company.TRANS_TABLE + ")"
				;
		
		rs = this.executeGetQuery();
		
		//personQuery2 = this.query.replaceAll(", person_name", "");
		//personQuery2 = personQuery2.replace("and person_id not in (select person_id from tls010_company_applnt)", "");
		
		try {
			while (rs.next()) {
				continueFlag++;
				personId = rs.getInt("person_id");
				personName = rs.getString("person_name");
				if (cm.isValidPerson(company, personId)) {
					personIds.add(Integer.toString(personId));
					CompanyApplicant compApplnt = new CompanyApplicant();
					compApplnt.setCompanyId(company.getID());
					compApplnt.setPersonId(personId);
					compApplnt.setCompanyName(personName);
					cm.createCompantApplicant(compApplnt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Set<String> hs = new HashSet<>();
		hs.addAll(personIds);
		personIds.clear();
		personIds.addAll(hs);
		System.out.println("Left and Right route return these person ids " + String.join(",", personIds.toArray(new String[0])));
		
		if (continueFlag <= 0 || personIds.size() == 0) {
			cm.close();
			return;
		}
		
		personQuery1 = String.join(",", personIds.toArray(new String[0]));
		personQuery2 = String.join(",", personIds.toArray(new String[0]));
		
		this.query = "select person2.* from tls206_person as person1 join tls206_person as person2 on person1.person_id in (" + personQuery1 + ") and person2.person_name = person1.person_name";
		rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				personId = rs.getInt("person_id");
				personName = rs.getString("person_name");
				personIds.add(Integer.toString(personId));
				CompanyApplicant compApplnt = new CompanyApplicant();
				compApplnt.setCompanyId(company.getID());
				compApplnt.setPersonId(personId);
				compApplnt.setCompanyName(personName);
				cm.createCompantApplicant(compApplnt);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		hs = new HashSet<>();
		hs.addAll(personIds);
		personIds.clear();
		personIds.addAll(hs);
		System.out.println("Name exact match return these person ids " + String.join(",", personIds.toArray(new String[0])));
		
		personQuery1 = String.join(",", personIds.toArray(new String[0]));
		personQuery2 = String.join(",", personIds.toArray(new String[0]));
		
		this.query = "select * from tls211_pat_publn where appln_id in (select appln_id from tls207_pers_appln where person_id in "
				+ "(" + personQuery1 + ") and APPLT_SEQ_NR = 1 and invt_seq_nr = 0"
				+ ")"
				+ " or pat_publn_id in (select pat_publn_id from tls227_pers_publn where person_id in "
				+ "(" + personQuery2 + ") and APPLT_SEQ_NR = 1 and invt_seq_nr = 0"
				+ ")";
		
		rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Patent tmpPatent = new Patent();
				tmpPatent.setPublicationNumber(rs.getString("publn_nr"));
				tmpPatent.setPublnDateBySqlDate(rs.getDate("publn_date"));
				tmpPatent.setCompanyId(company.getID());
				tmpPatent.setPatPublnId(rs.getInt("pat_publn_id"));
				patentList.add(tmpPatent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("There are [" + patentList.size() + "] patents for company id " + company.getID());
		
		for (i = 0; i < patentList.size(); i++) {
			Patent tmpPatent = patentList.get(i);
			patPubId = tmpPatent.getPatPublnId();
			
			this.query = "select appln.appln_auth, appln.appln_nr, appln.appln_nr_epodoc, appln.appln_kind, appln.appln_filing_date, appln.earliest_filing_date, docdb_family_id from tls201_appln appln "
					+ "join tls211_pat_publn publn on appln.appln_id = publn.appln_id and publn.pat_publn_id = " + patPubId;
			rs = this.executeGetQuery();
			try {
				if (rs.next()) {
					tmpPatent.setApplnNum(rs.getString("appln_nr"));
					tmpPatent.setPriorityNum(rs.getString("appln_nr_epodoc"));
					tmpPatent.setPrefix(rs.getString("appln_auth"));
					tmpPatent.setPostfix(rs.getString("appln_kind"));
					tmpPatent.setAppFilingDateBySqlDate(rs.getDate("appln_filing_date"));
					tmpPatent.setAppEarliestDateBySqlDate(rs.getDate("earliest_filing_date"));
					tmpPatent.setDocdbFamId(rs.getInt("docdb_family_id"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.save(tmpPatent);
		}
		
		cm.close();
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		super.delete(id);
	}

	public int save(Patent patent) {
		int id = this.getPatentIdByPublicationNumber(patent.getPublicationNumber());
		patent.setID(id);

		if (patent.getID() == 0) {
			return this.create(patent);
		} else {
			this.query = "update " + Patent.TABLE + " set "
					+ "publication_number = \'" + patent.getPublicationNumber() + "\', "
					+ "company_id = " + patent.getCompanyId() + ", "
					+ "pat_publn_id = " + patent.getPatPublnId() + ", "
					+ "publication_date = \'" + patent.getPublnDateString() + "\', "
					+ "application_number = \'" + patent.getApplnNum() + "\', "
					+ "priority_number = \'" + patent.getPriorityNum() + "\', "
					+ "prefix = \'" + patent.getPrefix() + "\', "
					+ "postfix = \'" + patent.getPostfix() + "\', "
					+ "filing_date = \'" + patent.getApplnDateString() + "\', "
					+ "earliest_filing_date = \'" + patent.getAppEarliestDateString() + "\', "
					+ "docdb_family_id = " + patent.getDocdbFamId() + ", "
					+ "citations_total = " + patent.getCitationTotal() + " "
					+ "where id = " + patent.getID();
			return this.executeOtherQuery();
		}
	}
	
	public int getTotalCitationsByCompanyId(int companyId) {
		int totalCitations = 0;
		
		this.query = "select count(distinct citing_application_docdb_family_id) as total from " + Citation.TABLE + " where company_id =" + companyId;
		
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				totalCitations += rs.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalCitations;
	}
	
	public int getTotalNumberOfPatents() {
		int total = 0;
		this.query = "select count(*) as total from " + Patent.TABLE;
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				total = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public Patent[] getPatentsByIDRange(int idStart, int idEnd) {
		ArrayList<Patent> patents = new ArrayList<Patent>();
		
		this.query = "select * from " + Patent.TABLE + " where id >=" + idStart + " and id < " + idEnd + " and pat_publn_id > 0";
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Patent tmpPatent = new Patent();
				tmpPatent.setID(rs.getInt("id"));
				tmpPatent.setPublicationNumber(rs.getString("publication_number"));
				tmpPatent.setCompanyId(rs.getInt("company_id"));
				tmpPatent.setPatPublnId(rs.getInt("pat_publn_id"));
				tmpPatent.setPublnDateBySqlDate(rs.getDate("publication_date"));
				tmpPatent.setApplnNum(rs.getString("application_number"));
				tmpPatent.setPriorityNum(rs.getString("priority_number"));
				tmpPatent.setPrefix(rs.getString("prefix"));
				tmpPatent.setPostfix(rs.getString("postfix"));
				tmpPatent.setAppFilingDateBySqlDate(rs.getDate("filing_date"));
				tmpPatent.setAppEarliestDateBySqlDate(rs.getDate("earliest_filing_date"));
				tmpPatent.setDocdbFamId(rs.getInt("docdb_family_id"));
				tmpPatent.setCitationTotal(rs.getInt("citations_total"));
				patents.add(tmpPatent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patents.toArray(new Patent[0]);
	}
	
	public int countPatentsByCompanyId(int companyId) {
		int total = 0;
		
		this.query = "select count(distinct docdb_family_id) as total from unsw_bs_patent where company_id = " + companyId;
		
		ResultSet rs = this.executeGetQuery(); 
		
		try {
			if (rs.next()) {
				total = rs.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return total;
	}
	
}
