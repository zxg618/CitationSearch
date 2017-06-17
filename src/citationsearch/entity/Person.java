package citationsearch.entity;

public class Person extends Entity {
	protected int personId = 0;
	protected String personName = "";
	protected String psnSector = "";
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPsnSector() {
		return psnSector;
	}
	public void setPsnSector(String psnSector) {
		this.psnSector = psnSector;
	}
	
	
}
