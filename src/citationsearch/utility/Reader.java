package citationsearch.utility;

public class Reader
{
	protected String location;
	
	public Reader(String location)  {
		this.location = location;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
}
