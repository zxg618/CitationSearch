package citationsearch;

import static citationsearch.constants.Constants.*;
import citationsearch.service.CitationSearchService;

public class Main
{	
	public static void main(String[] args) {	
		CitationSearchService service = new CitationSearchService();
		service.setFilePath(FILE_PATH);
		
		//get all company details from excel file
		//service.displayAllCompanyNames();
		
		//read wipo api
		//service.setApiUrl(WIPO_RESULT_URL);
		//service.displayTotalNumberOfPatentsOnWIPO();
		
		//get related patents and citations
		//service.runStatistics();
		
		//generate output file
		//service.generateOutputExcelFiles();
		
		//validate person ids
		//service.validateApplicants();
		
		service.generatePatentListByPersonIds();
		
		//not used now
		//service.searchAllCitationsByEnglishName();
	}
}
