package citationsearch.utility;

import static citationsearch.constants.Constants.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import citationsearch.entity.Patent;
import citationsearch.mapper.CompanyMapper;
import citationsearch.mapper.PatentMapper;

public class WipoDataFileReader extends Reader {
	protected CompanyMapper cm = null;
	protected PatentMapper pm = null;

	public WipoDataFileReader(String location) {
		super(location);
		this.cm = new CompanyMapper();
		this.pm = new PatentMapper();
	}
	
	public void batchRead(String[] searchKeywords) {
		int total = searchKeywords.length;
		for (int i = 0; i < total; i++) {
			File dataFile = new File(WIPO_DATA_PATH + searchKeywords[i] + DATA_FILE_POSTFIX);
			if (dataFile.length() <= 0) {
				continue;
			}
			System.out.println("Starting reading file " + dataFile.getPath());
			this.read(dataFile, searchKeywords[i]);
		}
		this.cm.close();
		this.pm.close();
	}
	
	protected void read(File dataFile, String searchKeyword) {
		String line = "";
		String[] lineElements = new String[10]; 
		int lineNumber = 1;
		
		int companyId = this.cm.getCompanyByKeyword(searchKeyword);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) {
					lineNumber++;
					continue;
				}
				if (lineNumber == 1 || lineNumber % 12 == 1) {
					lineElements = line.split(" ");
					Patent patent = new Patent();
					patent.setCompanyId(companyId);
					patent.setPublicationNumber(lineElements[1]);
					this.pm.save(patent);
				}
				lineNumber++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getApplicantNameByKeywordPublnNr(String searchKeyword, String publnNr) {
		File dataFile = new File(WIPO_DATA_PATH + searchKeyword + DATA_FILE_POSTFIX);
		String line = "";
		String applicantName = "";
		int lineNumber = 1;
		String[] lineElements = null;
		boolean flag = false;
		
		if (dataFile.length() <= 0) {
			return applicantName;	
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) {
					lineNumber++;
					continue;
				}
				if (lineNumber == 1 || lineNumber % 12 == 1) {
					lineElements = line.split(" ");
					String tmpPubNr = lineElements[1];
					if (publnNr.equals(tmpPubNr)) {
						flag = true;
					}
				}
				
				if (lineNumber % 12 == 8 && flag) {
					return line;
				}
				
				lineNumber++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return applicantName;
		
	}

}
