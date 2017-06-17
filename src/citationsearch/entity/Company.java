package citationsearch.entity;

public class Company extends Entity {
	/**
	 * Chinese name from excel file
	 */
	protected String chineseName = "";
	protected String englishName = "";
	
	//link to source file, use csv i.e. 1,2,3,4
	protected int sourceExcelLineNumbers = 0;
	
	protected String sourceFileIds = "";
	
	public String getSourceFileIds() {
		return sourceFileIds;
	}

	public void setSourceFileIds(String sourceFileIds) {
		this.sourceFileIds = sourceFileIds;
	}

	public int getSourceExcelLineNumbers() {
		return sourceExcelLineNumbers;
	}

	public void setSourceExcelLineNumbers(int sourceExcelLineNumbers) {
		this.sourceExcelLineNumbers = sourceExcelLineNumbers;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	/**
	 * removed common keywords
	 */
	protected String searchKeyword = "";
	protected int patentsTotal = 0;
	protected int citationTotal = 0;
	
	public String getChineseName() {
		return chineseName;
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	
	public int getPatentsTotal() {
		return patentsTotal;
	}
	
	public int getCitationTotal() {
		return citationTotal;
	}
	
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	public void setPatentsTotal(int patentsTotal) {
		this.patentsTotal = patentsTotal;
	}
	
	public void setCitationTotal(int citationTotal) {
		this.citationTotal = citationTotal;
	}
}
