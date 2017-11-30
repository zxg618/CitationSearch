package citationsearch.entity;

import static citationsearch.constants.Constants.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Patent extends Entity {
	public static final String TABLE = "dbo.unsw_bs_patent";
	
	/**
	 * publication number got from WIPO content and EPO
	 */
	protected String publicationNumber = "";
	protected int companyId = 0;
	
	/**
	 * FK of tls211_PAT_PUBLN.PAT_PUBLN_ID
	 */
	protected int patPublnId = DUMP_PAT_ID;
	protected Date publnDate = null;
	
	protected String applnNum = "";
	protected String priorityNum = "";
	
	protected String prefix = "";
	protected String postfix = "";
	protected Date appFilingDate = null;
	protected Date appEarliestDate = null;
	protected int docdbFamId = 0;
	
	public Date getAppEarliestDate() {
		return appEarliestDate;
	}
	
	public String getAppEarliestDateString() {
		if (this.appEarliestDate == null) {
			return "1900-01-01";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.appEarliestDate);
	}
	
	public void setAppEarliestDateString(String dateString) {
		try {
			this.appEarliestDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAppEarliestDateBySqlDate(java.sql.Date date) {
		this.appEarliestDate = new Date(date.getTime());
	}
	
	public void setAppEarliestDate(Date appEarliestDate) {
		this.appEarliestDate = appEarliestDate;
	}
	
	
	public int getDocdbFamId() {
		return docdbFamId;
	}
	public void setDocdbFamId(int docdbFamId) {
		this.docdbFamId = docdbFamId;
	}
	
	protected int citationTotal = 0;
	
	public Date getPublnDate() {
		return publnDate;
	}
	public void setPublnDate(Date publnDate) {
		this.publnDate = publnDate;
	}
	
	public String getPublnDateString() {
		if (this.publnDate == null) {
			return "1900-01-01";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.publnDate);
	}
	
	public void setPublnDateString(String dateString) {
		try {
			this.publnDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPublnDateBySqlDate(java.sql.Date date) {
		this.publnDate = new Date(date.getTime());
	}
	
	public String getPriorityNum() {
		return priorityNum;
	}
	public void setPriorityNum(String priorityNum) {
		this.priorityNum = priorityNum;
	}
	public String getApplnNum() {
		return applnNum;
	}
	public void setApplnNum(String applnNum) {
		this.applnNum = applnNum;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPostfix() {
		return postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	public Date getAppFilingDate() {
		return appFilingDate;
	}
	
	public String getApplnDateString() {
		if (this.appFilingDate == null) {
			return "1900-01-01";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.appFilingDate);
	}
	
	public void setAppFilingDateString(String dateString) {
		try {
			this.appFilingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAppFilingDateBySqlDate(java.sql.Date date) {
		this.appFilingDate = new Date(date.getTime());
	}
	
	public void setAppFilingDate(Date applnDate) {
		this.appFilingDate = applnDate;
	}
	public String getPublicationNumber() {
		return publicationNumber;
	}
	public void setPublicationNumber(String publicationNumber) {
		this.publicationNumber = publicationNumber;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getPatPublnId() {
		return patPublnId;
	}
	public void setPatPublnId(int patPublnId) {
		this.patPublnId = patPublnId;
	}
	public int getCitationTotal() {
		return citationTotal;
	}
	public void setCitationTotal(int citationTotal) {
		this.citationTotal = citationTotal;
	}
	
	
}
