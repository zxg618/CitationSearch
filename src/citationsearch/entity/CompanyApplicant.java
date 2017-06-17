package citationsearch.entity;

public class CompanyApplicant extends Entity {
	protected int companyId = 0;
	
	/**
	 * FK of TLS_206_PERSON.PERSON_ID
	 */
	protected int personId = 0;
	
	protected String companyName = "";
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
