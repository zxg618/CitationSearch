package citationsearch;

import static citationsearch.constants.Constants.*;
import citationsearch.service.CitationSearchService;

public class Main
{	
	public static void main(String[] args) {	
		CitationSearchService service = new CitationSearchService();
		service.setFilePath(FILE_PATH);
		service.displayAllCompanyNames();
		service.setApiUrl(WIPO_RESULT_URL);
		service.displayTotalNumberOfPatentsOnWIPO();
//		service.runStatistics();
//		service.generateOutputExcelFiles();
		//service.searchAllCitationsByEnglishName();
	}
}
