package citationsearch.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import citationsearch.constants.PatentTypeEnum;

public class Citation extends Entity {
	
	public static final String TABLE = "unsw_bs_citation";
	
	protected int patentId = 0;
	protected int companyId = 0;
	
	/**
	 * FK of TLS212_CITATION.patpubln_id and CITN_ID
	 */
	protected int citingPatentId = 0;
	
	protected String citingPublnNum = "";
	protected Date citingPubDate = null;
	
	protected String citingAppNum = "";
	protected String priorityNumber = "";
	protected String prefix = "";
	protected String postfix = "";
	protected Date priorityDate = null;
	
	protected int citnId = 0;
	
	public Date getCitingPubDate() {
		return this.citingPubDate;
	}
	public void setCitingPubDate(Date citingPubDate) {
		this.citingPubDate = citingPubDate;
	}
	
	public String getPublnDateString() {
		if (this.citingPubDate == null) {
			return "1900-01-01";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.citingPubDate);
	}
	
	public void setPublnDateString(String dateString) {
		try {
			this.citingPubDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPublnDateBySqlDate(java.sql.Date date) {
		this.citingPubDate = new Date(date.getTime());
	}
	
	public String getCitingAppNum() {
		return this.citingAppNum;
	}
	public void setCitingAppNum(String citingAppNum) {
		this.citingAppNum = citingAppNum;
	}
	public String getCitingPublnNum() {
		return citingPublnNum;
	}
	public void setCitingPublnNum(String citingPublnNum) {
		this.citingPublnNum = citingPublnNum;
	}
	public String getPriorityNumber() {
		return this.priorityNumber;
	}
	public void setPriorityNumber(String priorityNumber) {
		this.priorityNumber = priorityNumber;
	}
	public String getPrefix() {
		return this.prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPostfix() {
		return this.postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	public Date getPriorityDate() {
		return priorityDate;
	}
	public void setPriorityDate(Date priorityDate) {
		this.priorityDate = priorityDate;
	}
	
	public String getApplnDateString() {
		if (this.priorityDate == null) {
			return "1900-01-01";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.priorityDate);
	}
	
	public void setApplnDateString(String dateString) {
		try {
			this.priorityDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setApplnDateBySqlDate(java.sql.Date date) {
		this.priorityDate = new Date(date.getTime());
	}
	
	public int getCitingPatentId() {
		return citingPatentId;
	}
	public void setCitingPatentId(int citingPatentId) {
		this.citingPatentId = citingPatentId;
	}
	
	public int getPatentId() {
		return patentId;
	}
	public void setPatentId(int patentId) {
		this.patentId = patentId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCitnId() {
		return citnId;
	}
	public void setCitnId(int citnId) {
		this.citnId = citnId;
	}
	
	public String getCitingApplnType() {
		String typeCode = "";
		String applicationNumber = this.getCitingAppNum();
		if (applicationNumber.length() <= 0) {
			typeCode = "N/A";
		} else {
			if (applicationNumber.length() > 8) {
				typeCode = applicationNumber.substring(4, 5);
			} else {
				typeCode = applicationNumber.substring(2, 3);
			}
		}
		
		return PatentTypeEnum.getValue(typeCode) + "(" + typeCode + ")";
	}
}
