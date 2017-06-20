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
	
	protected String applnNum = "";
	protected String priorityNum = "";
	
	protected String prefix = "";
	protected String postfix = "";
	protected Date applnDate = null;
	
	protected int citationTotal = 0;
	
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
	public Date getApplnDate() {
		return applnDate;
	}
	
	public String getApplnDateString() {
		if (this.applnDate == null) {
			return "1900-01-01";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.applnDate);
	}
	
	public void setApplnDateString(String dateString) {
		try {
			this.applnDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setApplnDateBySqlDate(java.sql.Date date) {
		this.applnDate = new Date(date.getTime());
	}
	
	public void setApplnDate(Date applnDate) {
		this.applnDate = applnDate;
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
