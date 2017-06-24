package citationsearch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import citationsearch.entity.Citation;
import citationsearch.entity.Company;
import citationsearch.entity.Patent;

public class CitationMapper extends Mapper {
	public void getAllCitations(Company company, Patent patent) {
		PatentMapper pm = new PatentMapper();
		int patPublnId = patent.getPatPublnId();
		int i = 0;
		
		String[] citationIds = this.getAllCitationIdsByPatentId(patPublnId);
		
		System.out.println("Company [" + company.getID() + "] patent [" + patent.getID() + "] has [" + citationIds.length + "] citations.");
		
		for (i = 0; i < citationIds.length; i++) {
			Citation tmpCitation = new Citation();
			tmpCitation.setPatentId(patent.getID());
			tmpCitation.setCompanyId(company.getID());
			String[] tmpCitaionIds = citationIds[i].split(",");
			tmpCitation.setCitingPatentId(Integer.parseInt(tmpCitaionIds[0]));
			tmpCitation.setCitnId(Integer.parseInt(tmpCitaionIds[1]));
			this.getCitingPatentDetails(tmpCitation);
			this.save(tmpCitation);
		}
		
		patent.setCitationTotal(citationIds.length);
		pm.save(patent);
		pm.close();
		
	}
	
	protected void getCitingPatentDetails(Citation cit) {
		int patPublnId = cit.getCitingPatentId();
		this.query = "select publn.publn_nr, publn.publn_date, appln.appln_nr, appln.appln_nr_epodoc, appln.appln_auth, appln.appln_kind, appln.appln_filing_date, appln.docdb_family_id "
				+ "from tls211_pat_publn as publn "
				+ "join tls201_appln as appln on publn.pat_publn_id = " + patPublnId + " and publn.appln_id = appln.appln_id "
				+ "join tls201_appln as appln2 "
				+ "on appln.appln_id = appln2.appln_id and appln.appln_auth = appln2.appln_auth and appln.docdb_family_id = appln2.docdb_family_id";
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				cit.setCitingPublnNum(rs.getString("publn_nr"));
				cit.setPublnDateBySqlDate(rs.getDate("publn_date"));
				cit.setCitingAppNum(rs.getString("appln_nr"));
				cit.setPriorityNumber(rs.getString("appln_nr_epodoc"));
				cit.setPrefix(rs.getString("appln_auth"));
				cit.setPostfix(rs.getString("appln_kind"));
				cit.setApplnDateBySqlDate(rs.getDate("appln_filing_date"));
				cit.setCitingAppDocdbFamilyId(rs.getInt("docdb_family_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[] getAllCitationIdsByPatentId(int patentId) {
		ArrayList<String> citationList = new ArrayList<String>();
		this.query = "select * from tls212_citation where cited_pat_publn_id = " + patentId;
		ResultSet rs = this.executeGetQuery();
		String citationId = "";
		
		try {
			while (rs.next()) {
				citationId = Integer.toString(rs.getInt("pat_publn_id")) + "," + Integer.toString(rs.getInt("citn_id"));
				citationList.add(citationId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return citationList.toArray(new String[0]);
	}
	
	public int save(Citation citation) {
		this.query = "select * from " + Citation.TABLE + " where citing_patent_id = " + citation.getCitingPatentId() + " and citn_id = " + citation.getCitnId();
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.query = "insert into " + Citation.TABLE
				+ " (patent_id, "
				+ "company_id, "
				+ "citing_patent_id, "
				+ "citing_publication_number, "
				+ "citing_publication_date, "
				+ "citing_application_number, "
				+ "citing_priority_number, "
				+ "citing_prefix, "
				+ "citing_postfix, "
				+ "citing_priority_date, "
				+ "citing_application_docdb_family_id, "
				+ "citn_id) values ("
				+ citation.getPatentId() + ", "
				+ citation.getCompanyId() + ", "
				+ citation.getCitingPatentId() + ", "
				+ "\'" + citation.getCitingPublnNum() + "\', "
				+ "\'" + citation.getPublnDateString() + "\', "
				+ "\'" + citation.getCitingAppNum() + "\', "
				+ "\'" + citation.getPriorityNumber() + "\', "
				+ "\'" + citation.getPrefix() + "\', "
				+ "\'" + citation.getPostfix() + "\', "
				+ "\'" + citation.getApplnDateString() + "\', "
				+ citation.getCitingAppDocdbFamilyId() + ", "
				+ citation.getCitnId()
				+ ")";
		
		return this.executeOtherQuery();
	}
	
	public int getTotalNumberOfCitations() {
		int total = 0;
		this.query = "select count (*) as total from " + Citation.TABLE;
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
	
	public Citation[] getCitationsByIDRange(int idStart, int idEnd) {
		ArrayList<Citation> citations = new ArrayList<Citation>();
		this.query = "select * from " + Citation.TABLE + " where id >= " + idStart + " and id < " + idEnd;
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Citation cit = new Citation();
				cit.setID(rs.getInt("id"));
				cit.setPatentId(rs.getInt("patent_id"));
				cit.setCompanyId(rs.getInt("company_id"));
				cit.setCitingPatentId(rs.getInt("citing_patent_id"));
				cit.setCitingPublnNum(rs.getString("citing_publication_number"));
				cit.setPublnDateBySqlDate(rs.getDate("citing_publication_date"));
				cit.setCitingAppNum(rs.getString("citing_application_number"));
				cit.setPriorityNumber(rs.getString("citing_priority_number"));
				cit.setPrefix(rs.getString("citing_prefix"));
				cit.setPostfix(rs.getString("citing_postfix"));
				cit.setApplnDateBySqlDate(rs.getDate("citing_priority_date"));
				cit.setCitingAppDocdbFamilyId(rs.getInt("citing_application_docdb_family_id"));
				cit.setCitnId(rs.getInt("citn_id"));
				citations.add(cit);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return citations.toArray(new Citation[0]);
	}
}
