package citationsearch.constants;

public final class Constants {
	public static final String[] KEYWORDS = {"有限", "公司", "股份", "集团", "责任"};
	public static final String WIPO_STRUCTURED_SEARCH = "https://patentscope.wipo.int/search/en/structuredSearch.jsf";
	public static final String WIPO_RESULT_URL = "https://patentscope.wipo.int/search/en/result.jsf";
	public static final String QUERY_STRING_PART1 = "?query=PA:"; //世芯电子&listLengthOption=1653";
	public static final String QUERY_STRING_PART2 = "&listLengthOption=200";
	//public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish.xlsx";
	//public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish_test.xlsx";
	//public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish_test2.xlsx";
	public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish_test2.xlsx";
	public static final String LOG_IN_URL = "https://patentscope.wipo.int/search/en/reg/login.jsf";
	public static final String DATA_FILE_POSTFIX = ".out";
	public static final String PSN_SECTOR_COMPANY = "COMPANY";
	public static final String COUNTRY_CODE_LIST = "('CN', 'HK', 'TW', '')";
	
	//SQL server related
	public static final String SERVER_ADDR = "jdbc:sqlserver://192.168.1.118:1433;user=zxg618;password=19830618;databaseName=patstat2016b";
	public static final String SQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final int KEY_DATE_LENGTH = 8;
	
	/**
	 * Full width chars used in Chinese texts
	 * 
	 * https://en.wikipedia.org/wiki/Halfwidth_and_fullwidth_forms
	 */
	public static final String FULL_WIDTH_SEMICOLON = "\uFF1B";
	public static final String FULL_WIDTH_LEFT_PARENTHESIS = "\uFF08";
	public static final String FULL_WIDTH_RIGHT_PARENTHESIS = "\uFF09";
	
	
	//output section
	public static final String COMPANY_FILENAME = "companies.xlsx";
	public static final String PATENT_FILENAME = "patents.xlsx";
	public static final String CITATION_FILENAME = "citations.xlsx";
	public static final String COMPANY_TRANSLATION_FILENAME = "company_translation.xlsx";
	//id, Chinese name, English name, total patents, total citations
	public static final int COMPANY_COLUMNS = 6;
	public static final String COMPANY_COLUMN1 = "RAP - Company ID";
	public static final String COMPANY_COLUMN2 = "Source Company IDs";
	public static final String COMPANY_COLUMN3 = "Source - Company Name in Chinese";
	public static final String COMPANY_COLUMN4 = "Source - Company Name in English";
	public static final String COMPANY_COLUMN5 = "Total number of Patents";
	public static final String COMPANY_COLUMN6 = "Total number of Citations";
	//id, publication number, company id, EPO patent id, total citations
	public static final int PATENT_COLUMNS = 9;
	public static final String PATENT_COLUMN1 = "RAP - Patent ID";
	public static final String PATENT_COLUMN2 = "RAP - COMPANY ID";
	public static final String PATENT_COLUMN3 = "EPO - Publication Number";
	public static final String PATENT_COLUMN4 = "EPO - Patent ID";
	public static final String PATENT_COLUMN5 = "EPO - Application Type";
	public static final String PATENT_COLUMN6 = "EPO - Application Country";
	public static final String PATENT_COLUMN7 = "EPO - Application Kind";
	public static final String PATENT_COLUMN8 = "EPO - Application Date";
	public static final String PATENT_COLUMN9 = "Number of Citations";
	//id, patent id, company id, EPO citation id
	public static final int CITATION_COLUMNS = 10;
	public static final String CITATION_COLUMN1 = "RAP - Citation ID";
	public static final String CITATION_COLUMN2 = "RAP - Patent ID";
	public static final String CITATION_COLUMN3 = "RAP - Company ID";
	public static final String CITATION_COLUMN4 = "EPO - Citation ID of Citing Patent";
	public static final String CITATION_COLUMN5 = "EPO - Citing patent ID";
	public static final String CITATION_COLUMN6 = "EPO - Citing patent Publication Number";
	public static final String CITATION_COLUMN7 = "EPO - Citing patent Application Type";
	public static final String CITATION_COLUMN8 = "EPO - Citing patent Application Country";
	public static final String CITATION_COLUMN9 = "EPO - Citing patent Application Kind";
	public static final String CITATION_COLUMN10 = "EPO - Citing patent Priority Date";
	//id, company Chinese name, company English name, EPO company id
	public static final int COMPANY_TRANS_COLUMNS = 5;
	public static final String COMPANY_TRANS_COLUMN1 = "RAP - Translation ID";
	public static final String COMPANY_TRANS_COLUMN2 = "RAP Company ID";
	public static final String COMPANY_TRANS_COLUMN3 = "EPO Company ID";
	public static final String COMPANY_TRANS_COLUMN4 = "Source - Company Name in Chinese";
	public static final String COMPANY_TRANS_COLUMN5 = "Source - Company Name in English";
	
}
