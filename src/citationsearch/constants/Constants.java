package citationsearch.constants;

public final class Constants {
	public static final String[] KEYWORDS = {"有限", "公司", "股份", "集团", "责任"};
	public static final String WIPO_SIMPLE_SEARCH = "https://patentscope.wipo.int/search/en/search.jsf";
	public static final String WIPO_STRUCTURED_SEARCH = "https://patentscope.wipo.int/search/en/structuredSearch.jsf";
	public static final String WIPO_RESULT_URL = "https://patentscope.wipo.int/search/en/result.jsf";
	public static final String QUERY_STRING_PART1 = "?query=PA:"; //世芯电子&listLengthOption=1653";
	public static final String QUERY_STRING_PART2 = "&listLengthOption=2000";
	//public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish.xlsx";
	public static final String WIPO_DATA_PATH = "./WIPO_Data/";
	public static final String PERSON_PUBNR_FILE = "person_pubNr_complete.txt";
	public static final String PERSON_PUBNR_FILE2 = "person_pubNr_complete_done.txt";
	
	public static final String PERSONS = "persons.txt";
	
	//excel file start position >=1
	public static final int EXCELFILE_START = 1;
	//excel file end position
	public static final int EXCELFILE_END = 20;
	
	public static final String FILE_PATH = "patentfirmsmandenglish_test.xlsx";
	//public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish_test2.xlsx";
	//public static final String FILE_PATH = "/Users/zxg/Documents/workspace/CitationSearch/patentfirmsmandenglish_test3.xlsx";
	public static final String PERSON_ID_FILE_PATH = "./persons.xlsx";
	
	public static final String LOG_IN_URL = "https://patentscope.wipo.int/search/en/reg/login.jsf";
	public static final String DATA_FILE_POSTFIX = ".out";
	public static final String PSN_SECTOR_COMPANY = "COMPANY";
	public static final String COUNTRY_CODE_LIST = "('CN', '')";
	
	//SQL server related
	//zxg
//	public static final String SERVER_ADDR = "jdbc:sqlserver://192.168.1.118:1433;user=zxg618;password=19830618;databaseName=patstat2016b";

//	public static final String SERVER_ADDR = "jdbc:sqlserver://14.202.168.100:1433;user=zxg618;password=19830618;databaseName=patstat2016b";

	//Fan Desktop
//	public static final String SERVER_ADDR = "jdbc:sqlserver://192.168.8.100:1433;user=fan;password=fan;databaseName=patstat2016b";


	//RA server
//	public static final String SERVER_ADDR = "jdbc:sqlserver://localhost:1433;user=fansapp;password=abcd1234;databaseName=patstat2016b";

	//Win Vm in Mac
	public static final String SERVER_ADDR = "jdbc:sqlserver://localhost:1433;user=fan;password=fan;databaseName=patstat2016b";

	public static final String SQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final int KEY_DATE_LENGTH = 8;
	
	public static final int DUMP_PAT_ID = 111;
	public static final int DUMP_APPLN_ID = 201;
	
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
	public static final int PATENT_COLUMNS = 13;
	public static final String PATENT_COLUMN1 = "RAP - Patent ID";
	public static final String PATENT_COLUMN2 = "RAP - Company ID";
	public static final String PATENT_COLUMN3 = "EPO - Patent ID";
	public static final String PATENT_COLUMN4 = "EPO - Publication Number";
	public static final String PATENT_COLUMN5 = "EPO - Publication Date";
	public static final String PATENT_COLUMN6 = "EPO - Application Number";
	public static final String PATENT_COLUMN7 = "EPO - Application DOCDB Family ID";
	public static final String PATENT_COLUMN8 = "EPO - Priority Number";
	public static final String PATENT_COLUMN9 = "EPO - Application Type (type digit)";
	public static final String PATENT_COLUMN10 = "EPO - Application Country";
	public static final String PATENT_COLUMN11 = "EPO - Application Kind";
	public static final String PATENT_COLUMN12 = "EPO - Filing Date";
	public static final String PATENT_COLUMN13 = "EPO - Earliest Filing Date";
	public static final String PATENT_COLUMN14 = "Number of Citations";
	//id, patent id, company id, EPO citation id
	public static final int CITATION_COLUMNS = 15;
	public static final String CITATION_COLUMN1 = "RAP - Citation ID";
	public static final String CITATION_COLUMN2 = "RAP - Patent ID";
	public static final String CITATION_COLUMN3 = "RAP - Company ID";
	public static final String CITATION_COLUMN4 = "EPO - Citation ID of Citing Patent";
	public static final String CITATION_COLUMN5 = "EPO - Citing patent ID";
	public static final String CITATION_COLUMN6 = "EPO - Citing patent Publication Number";
	public static final String CITATION_COLUMN7 = "EPO - Citing patent Publication Date";
	public static final String CITATION_COLUMN8 = "EPO - Citing Application Number";
	public static final String CITATION_COLUMN9 = "EPO - Citing Application DOCDB Family ID";
	public static final String CITATION_COLUMN10 = "EPO - Citing Priority Number";
	public static final String CITATION_COLUMN11 = "EPO - Citing patent Application Type (type digit)";
	public static final String CITATION_COLUMN12 = "EPO - Citing patent Application Country";
	public static final String CITATION_COLUMN13 = "EPO - Citing patent Application Kind";
	public static final String CITATION_COLUMN14 = "EPO - Citing patent Filing Date";
	public static final String CITATION_COLUMN15 = "EPO - Citing patent Earliest Filing Date";
	//id, company Chinese name, company English name, EPO company id
	public static final int COMPANY_TRANS_COLUMNS = 5;
	public static final String COMPANY_TRANS_COLUMN1 = "RAP - Translation ID";
	public static final String COMPANY_TRANS_COLUMN2 = "RAP Company ID";
	public static final String COMPANY_TRANS_COLUMN3 = "EPO Company ID";
	public static final String COMPANY_TRANS_COLUMN4 = "Source - Company Name in Chinese";
	public static final String COMPANY_TRANS_COLUMN5 = "RAP - Company Name in English";
	
	public static final String REFINED_PERSONS_FILE = "output/ReversedFulltobecheckedallcompleted.xls";
	public static final String ORIGINAL_PERSONS_FILE = "output/CandidateCompanyNames.xlsx";

	public static final String DEAL_DATE_FILE = "output/fancostogetpatstats.xlsx";
}
