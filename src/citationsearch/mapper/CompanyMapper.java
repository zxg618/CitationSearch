package citationsearch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import citationsearch.entity.Company;
import citationsearch.entity.CompanyApplicant;

public class CompanyMapper extends Mapper {
	public Company get(int id) {
		Company company = new Company();
		this.query = "select * from " + Company.TABLE + " where id = " + id;
		ResultSet rs = this.executeGetQuery();
		
		try {
			if (rs.next()) {
				company.setID(id);
				company.setChineseName(rs.getString("chinese_name"));
				company.setSearchKeyword(rs.getString("search_keyword"));
				company.setEnglishName(rs.getString("english_name"));
				company.setSourceExcelLineNumbers(rs.getInt("source_file_id"));
				company.setPatentsTotal(rs.getInt("patents_total"));
				company.setCitationTotal(rs.getInt("citations_total"));
				return company;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getCompanyByKeyword(String searchKeyword) {
		this.query = "select * from " + Company.TABLE + " where search_keyword = N\'" + searchKeyword + "\'";
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
	
	public Company[] getAllCompanys() {
		ArrayList<Company> companies = new ArrayList<Company>();
		
		this.query = "select * from " + Company.TABLE;
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				Company comp = new Company();
				comp.setID(rs.getInt("id"));
				comp.setChineseName(rs.getString("chinese_name"));
				comp.setSearchKeyword(rs.getString("search_keyword"));
				comp.setEnglishName(rs.getString("english_name"));
				comp.setSourceExcelLineNumbers(rs.getInt("source_file_id"));
				comp.setPatentsTotal(rs.getInt("patents_total"));
				comp.setCitationTotal(rs.getInt("citations_total"));
				companies.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies.toArray(new Company[0]);
	}

	public int create(Company company) {
		
		this.query = "INSERT INTO " + Company.TABLE + " (chinese_name, search_keyword, english_name, source_file_id, patents_total, citations_total)"
				+ " VALUES (N\'"
				+ company.getChineseName()
				+ "\', N\'"
				+ company.getSearchKeyword()
				+ "\', "
				+ "\'" + company.getEnglishName() + "\', "
				+ company.getSourceExcelLineNumbers() + ", "
				+ company.getPatentsTotal()
				+ ", "
				+ company.getCitationTotal()
				+ ")";
		int result = this.executeOtherQuery();
		
		return result;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		super.delete(id);
	}
	
	//tls010_company_applnt section
	public int createCompantApplicant(CompanyApplicant compApplnt) {
		this.query = "select id from " + Company.TRANS_TABLE + " where company_id = " + compApplnt.getCompanyId() + " and person_id = " + compApplnt.getPersonId();
		ResultSet rs = this.executeGetQuery();
		try {
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.query = "insert into " + Company.TRANS_TABLE + " (company_id, person_id, company_name)"
				+ " values ("
				+ compApplnt.getCompanyId() + ", "
				+ compApplnt.getPersonId() + ", "
				+ "\'" + compApplnt.getCompanyName().replace("'", "''") + "\'"
				+ ")";
		
		return this.executeOtherQuery();
	}
	
	public CompanyApplicant[] getTranslationsByCompanyId(int companyId) {
		ArrayList<CompanyApplicant> translations = new ArrayList<CompanyApplicant>();
		
		this.query = "select * from " + Company.TRANS_TABLE + " where company_id = " + companyId + " order by id";
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				CompanyApplicant tmpTrans = new CompanyApplicant();
				tmpTrans.setID(rs.getInt("id"));
				tmpTrans.setCompanyId(rs.getInt("company_id"));
				tmpTrans.setPersonId(rs.getInt("person_id"));
				tmpTrans.setCompanyName(rs.getString("company_name"));
				translations.add(tmpTrans);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return translations.toArray(new CompanyApplicant[0]);
	}
	
	//tls010_company_applnt section end
	
	public int save(Company company) {
		int id = this.getCompanyByKeyword(company.getSearchKeyword());
		company.setID(id);
		
		if (id == 0) {
			return this.create(company);
		} else {
			this.query = "update " + Company.TABLE + " set "
					+ "patents_total = " + company.getPatentsTotal() + ", "
					+ "citations_total = " + company.getCitationTotal() + " "
					+ "where id = " + company.getID()
					;
			return this.executeOtherQuery();
		}
	}
	
	public boolean isValidPerson(Company company, int personId) {
		/* can not use this filter as it may lost some correct results
		if (company.getSearchKeyword().matches("中国银行") && personId == 41370098) {
			return false;
		} else if (company.getSearchKeyword().matches("中国银行") && personId == 21257941) {
			return false;
		} else if (company.getSearchKeyword().matches("深圳市奇迹通讯") && personId == 40780075) {
			return false;
		}
		*/
		
		return true;
	}
	
	public CompanyApplicant[] getAllTranslations() {
	ArrayList<CompanyApplicant> translations = new ArrayList<CompanyApplicant>();
		
		this.query = "select * from " + Company.TRANS_TABLE;
		ResultSet rs = this.executeGetQuery();
		
		try {
			while (rs.next()) {
				CompanyApplicant translation = new CompanyApplicant();
				translation.setID(rs.getInt("id"));
				translation.setCompanyId(rs.getInt("company_id"));
				translation.setPersonId(rs.getInt("person_id"));
				translation.setCompanyName(rs.getNString("company_name"));
				translations.add(translation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return translations.toArray(new CompanyApplicant[0]);
	}
	
}
