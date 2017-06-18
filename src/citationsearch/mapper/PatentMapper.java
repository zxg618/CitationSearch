package citationsearch.mapper;

import static citationsearch.constants.Constants.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

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
		
		this.query = "select * from dbo.tls002_patent where id = " + id;
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				patent.setID(rs.getInt("id"));
				patent.setPublicationNumber(rs.getString("publication_number"));
				patent.setCompanyId(rs.getInt("company_id"));
				patent.setPatPublnId(rs.getInt("pat_publn_id"));
				patent.setApplnNum(rs.getString("application_number"));
				patent.setPrefix(rs.getString("prefix"));
				patent.setPostfix(rs.getString("postfix"));
				patent.setApplnDateBySqlDate(rs.getDate("application_date"));
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
		
		this.query = "select * from dbo.tls002_patent where company_id = " + companyId;
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Patent pat = new Patent();
				pat.setID(rs.getInt("id"));
				pat.setPublicationNumber(rs.getString("publication_number"));
				pat.setCompanyId(rs.getInt("company_id"));
				pat.setPatPublnId(rs.getInt("pat_publn_id"));
				pat.setApplnNum(rs.getString("application_number"));
				pat.setPrefix(rs.getString("prefix"));
				pat.setPostfix(rs.getString("postfix"));
				pat.setApplnDateBySqlDate(rs.getDate("application_date"));
				pat.setCitationTotal(rs.getInt("citations_total"));
				patentList.add(pat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return patentList.toArray(new Patent[0]);
	}

	public int getPatentIdByPublicationNumber(String pubNum) {
		this.query = "select id from dbo.tls002_patent where publication_number = \'" + pubNum + "\'";
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
		this.query = "insert into dbo.tls002_patent (publication_number, company_id, pat_publn_id, application_number, prefix, postfix, application_date, citations_total) values ("
				+ "\'" + patent.getPublicationNumber()
				+ "\', "
				+ patent.getCompanyId()
				+ ", "
				+ patent.getPatPublnId()
				+ ", \'"
				+ patent.getApplnNum()
				+ "\', \'"
				+ patent.getPrefix()
				+ "\', \'"
				+ patent.getPostfix()
				+ "\', \'"
				+ patent.getApplnDateString()
				+ "\', "
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
		
		
		if (patPubId == DUMP_PAT_ID) {
			this.query = "select appln_id, pat_publn_id from dbo.tls211_pat_publn where publn_nr = \'" + patent.getPublicationNumber() + "\'";
			rs = this.executeGetQuery();
			
			//System.out.println("Searching patent id " + patent.getID() + " with publication number [" + patent.getPublicationNumber() + "] in EPO DB.");
			
			try {
				if (rs.next()) {
					appId = rs.getInt("appln_id");
					patPubId = rs.getInt("pat_publn_id");
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
			
			this.query = "select appln_id from dbo.tls211_pat_publn where pat_publn_id = " + patPubId;
			rs = this.executeGetQuery();
			try {
				if (rs.next()) {
					appId = rs.getInt("appln_id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (appId == 0 || patPubId == 0) {
			//System.out.println("Result not found.");
			patent.setPatPublnId(0);
			patent.setApplnDateString(patent.getApplnDateString());
			this.save(patent);
			return;
		}

		patent.setPatPublnId(patPubId);
		
		this.query = "select appln_auth, appln_nr, appln_kind, appln_filing_date from tls201_appln where appln_id = " + appId;
		rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				patent.setApplnNum(rs.getString("appln_nr"));
				patent.setPrefix(rs.getString("appln_auth"));
				patent.setPostfix(rs.getString("appln_kind"));
				patent.setApplnDateBySqlDate(rs.getDate("appln_filing_date"));
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
				+ ") and psn_sector = \'COMPANY\' and person_ctry_code in " + COUNTRY_CODE_LIST + " "
				+ "and person_id not in (select person_id from tls010_company_applnt)"
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
				+ ") and psn_sector = \'COMPANY\' and person_ctry_code in " + COUNTRY_CODE_LIST + " "
				+ "and person_id not in (select person_id from tls010_company_applnt)"
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
		
		
		if (continueFlag <= 0 || personIds.size() == 0) {
			cm.close();
			return;
		}
		
		personQuery1 = String.join(",", personIds.toArray(new String[0]));
		personQuery2 = String.join(",", personIds.toArray(new String[0]));
		
		this.query = "select * from tls211_pat_publn where appln_id in (select appln_id from tls207_pers_appln where person_id in "
				+ "(" + personQuery1 + ")"
				+ ")"
				+ " or pat_publn_id in (select pat_publn_id from tls227_pers_publn where person_id in "
				+ "(" + personQuery2 + ")"
				+ ")";
		
		rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Patent tmpPatent = new Patent();
				tmpPatent.setPublicationNumber(rs.getString("publn_nr"));
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
			
			this.query = "select appln.appln_auth, appln.appln_nr, appln.appln_kind, appln.appln_filing_date from tls201_appln appln "
					+ "join tls211_pat_publn publn on appln.appln_id = publn.appln_id and publn.pat_publn_id = " + patPubId;
			rs = this.executeGetQuery();
			try {
				if (rs.next()) {
					tmpPatent.setApplnNum(rs.getString("appln_nr"));
					tmpPatent.setPrefix(rs.getString("appln_auth"));
					tmpPatent.setPostfix(rs.getString("appln_kind"));
					tmpPatent.setApplnDateBySqlDate(rs.getDate("appln_filing_date"));
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
			this.query = "update tls002_patent set "
					+ "publication_number = \'" + patent.getPublicationNumber() + "\', "
					+ "company_id = " + patent.getCompanyId() + ", "
					+ "pat_publn_id = " + patent.getPatPublnId() + ", "
					+ "application_number = \'" + patent.getApplnNum() + "\', "
					+ "prefix = \'" + patent.getPrefix() + "\', "
					+ "postfix = \'" + patent.getPostfix() + "\', "
					+ "application_date = \'" + patent.getApplnDateString() + "\', "
					+ "citations_total = " + patent.getCitationTotal() + " "
					+ "where id = " + patent.getID();
			return this.executeOtherQuery();
		}
	}
	
	public int getTotalCitationsByCompanyId(int companyId) {
		int totalCitations = 0;
		
		this.query = "select count(*) as total from tls003_citation where company_id =" + companyId;
		
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
		this.query = "select count(*) as total from tls002_patent";
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
		
		this.query = "select * from tls002_patent where id >=" + idStart + " and id < " + idEnd;
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Patent tmpPatent = new Patent();
				tmpPatent.setID(rs.getInt("id"));
				tmpPatent.setPublicationNumber(rs.getString("publication_number"));
				tmpPatent.setCompanyId(rs.getInt("company_id"));
				tmpPatent.setPatPublnId(rs.getInt("pat_publn_id"));
				tmpPatent.setApplnNum(rs.getString("application_number"));
				tmpPatent.setPrefix(rs.getString("prefix"));
				tmpPatent.setPostfix(rs.getString("postfix"));
				tmpPatent.setApplnDateBySqlDate(rs.getDate("application_date"));
				tmpPatent.setCitationTotal(rs.getInt("citations_total"));
				patents.add(tmpPatent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patents.toArray(new Patent[0]);
	}
	
}
