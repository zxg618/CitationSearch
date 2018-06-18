package citationsearch;

import static citationsearch.constants.Constants.*;
import citationsearch.service.CitationSearchService;

import javax.sound.midi.SysexMessage;
import java.util.Date;

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
//		service.runStatistics();
		
		//generate output file
//		service.generateOutputExcelFiles();
		
		//validate person ids
		//service.validateApplicants();
		
		//service.generatePatentListByPersonIds();
		
		//service.setApiUrl(WIPO_SIMPLE_SEARCH);
		//service.findWipoApplicantByPubNr();
		//service.formatWipoApplicantsFile();
		
		//second half part
//		service.processPersonIds();
		
		//not used now
		//service.searchAllCitationsByEnglishName();


		//==== one-off operations ====

		//Add 'type' to patent table
//		service.addTypeToPatentTable();
//		service.addDealDate();


		//Compute statistics
//		service.getStats();
//		service.generatePatStatsOutputExcelFiles();
		service.generateCitStatsOutputExcelFiles();
	}
}
