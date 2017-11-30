package citationsearch.service;

import static citationsearch.constants.Constants.*;

import citationsearch.utility.ExcelFileReader;
import citationsearch.utility.ExcelFileWriter;
import citationsearch.utility.FileReader;
import citationsearch.utility.WipoDataFileReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.Map.Entry;

import org.apache.commons.text.similarity.LevenshteinDistance;

import citationsearch.entity.Company;
import citationsearch.entity.CompanyApplicant;
import citationsearch.entity.Patent;
import citationsearch.mapper.CitationMapper;
import citationsearch.mapper.CompanyMapper;
import citationsearch.mapper.PatentMapper;
import citationsearch.utility.ApiReader;


public class CitationSearchService 
{
	protected String filePath = null;
	protected ExcelFileReader fileReader = null;
	protected WipoDataFileReader dataFileReader = null;
	protected String apiUrl = "";
	protected ApiReader apiReader = null;
	protected String[] companies = null;
	protected HashMap<String, String> total = null;
	protected ExcelFileWriter excelFileWriter = null;
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		this.fileReader = new ExcelFileReader(this.filePath);
	}
	
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
		this.apiReader = new ApiReader(this.apiUrl);
		this.dataFileReader = new WipoDataFileReader("");
	}
	
	public void displayAllCitations() {
		//
	}
	
	public void displayAllCompanyNames() {
		this.companies = this.fileReader.read();
		//printStringArray(this.companies);
	}
	
	public void displayTotalNumberOfPatentsOnWIPO() {
		this.apiReader.setSearchKeywords(this.companies);
		//this.total = this.apiReader.batchRead();
		this.dataFileReader.batchRead(this.companies);
	}
	
	protected void printStringArray(String[] stringArray) {
		int i = 0;
		for (i = 0; i < stringArray.length; i++) {
			System.out.println(stringArray[i]);
		}
		
		System.out.println("There are total " + i + " companies.");
	}
	
	protected void printHashMap() {
		 Iterator<Entry<String, String>> iterator = this.total.entrySet().iterator();
	      while(iterator.hasNext()) {
	         Map.Entry<String, String> mentry = iterator.next();
	         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	         System.out.println(mentry.getValue());
	      }
	}
	
	public void runStatistics() {
		//link company to (multiple) person i.e. fill in tls010 ----
		//link patents to tls211 -----
		//link citations to tls212
		//fill in citations_total in tls002_patent
		//fill in patents_total in tls001_company
		//fill in citations_total in tls001_company
		
		
		this.getAllRelatedPatents();
		//this.getAllCitations();
		//this.countAllCitationsForEachCompany();
	}
	
	protected void getAllRelatedPatents() {
		System.out.println("---------------------------------");
		System.out.println("Starting to find all patents from each company.");
		System.out.println("---------------------------------");
		Patent[] patents = null;
		int i = 0;
		int j = 0;
		int companyId = 0;

		CompanyMapper cm = new CompanyMapper();
		PatentMapper pm = new PatentMapper();
		
		Company[] companies = cm.getAllCompanys();
		
		for (i = 0; i < companies.length; i++) {
			companyId = companies[i].getID();
			if (companyId < 3174) {
				continue;
			}
			
			patents = pm.getPatentsByCompanyId(companyId);
			HashSet<String> hs = new HashSet<>();
			for (j = 0; j < patents.length; j++) {
				pm.getAllRelatedPatents(companies[i], patents[j], hs);
				pm.reConnectDB();
			}
		}
		
		cm.close();
		pm.close();
	}
	
	protected void getAllCitations() {
		System.out.println("---------------------------------");
		System.out.println("Starting to find all Citations from each company's each patent.");
		System.out.println("---------------------------------");
		CompanyMapper cm = new CompanyMapper();
		PatentMapper pm = new PatentMapper();
		CitationMapper cim = new CitationMapper();
		int i = 0;
		int j = 0;
		int companyId = 0;
		Patent[] patents = null;
		
		Company[] companies = cm.getAllCompanys();
		
		for (i = 0; i < companies.length; i++) {
			companyId = companies[i].getID();
			patents = pm.getPatentsByCompanyId(companyId);
			for (j = 0; j < patents.length; j++) {
				if (patents[j].getPatPublnId() <= 0) {
					continue;
				}
				cim.getAllCitations(companies[i], patents[j]);
			}
		}
		
		cm.close();
		pm.close();
		cim.close();
	}
	
	protected void countAllCitationsForEachCompany() {
		CompanyMapper cm = new CompanyMapper();
		PatentMapper pm = new PatentMapper();
		int i = 0;
		int j = 0;
		Patent[] patents = null;
		
		Company[] companies = cm.getAllCompanys();
		
		for (i = 0; i < companies.length; i++) {
			patents = pm.getPatentsByCompanyId(companies[i].getID());
			for (j = 0; j < patents.length; j++) {
				if (patents[j].getPatPublnId() <= 0) {
					continue;
				}
			}
			companies[i].setPatentsTotal(pm.countPatentsByCompanyId(companies[i].getID()));
			int total = pm.getTotalCitationsByCompanyId(companies[i].getID());
			companies[i].setCitationTotal(total);
			cm.save(companies[i]);
		}
		
		cm.close();
		pm.close();
	}
	
	public void generateOutputExcelFiles() {
		this.excelFileWriter = new ExcelFileWriter();
		this.excelFileWriter.generateOutputFiles();
	}
	
	
	public void searchAllCitationsByEnglishName() {
		CompanyMapper cm = new CompanyMapper();
		Company[] companies = cm.getAllCompanys();
		int i = 0;
		String companyName = "";
		
		for (i = 0; i < companies.length; i++) {
			companyName = this.removeCommonWords(companies[i].getEnglishName());
			System.out.println(companies[i].getChineseName() + " length is " + companies[i].getChineseName().length());
			System.out.println(companies[i].getID() + " " + companyName);
		}
	}
	
	protected String removeCommonWords(String string) {
		String newString = string.toLowerCase();
		newString = newString.replace("co.", "");
		newString = newString.replace("ltd", "");
		newString = newString.replace(",", "");
		newString = newString.replace(".", "");
		return newString;
	}
	
	public void validateApplicants() {
		CompanyMapper cm = new CompanyMapper();
		Company[] compinies = cm.getAllCompanys();
		int i = 0;
		int j = 0;
		
		//make it 50% of total for now
		int occuranceThreshold = 0;
		
		for (i = 0; i < compinies.length; i++) {
			String originalEnglishName = compinies[i].getEnglishName();
			int companyId = compinies[i].getID();
			int sourceFileId = compinies[i].getSourceExcelLineNumbers();
			String originalName = compinies[i].getChineseName();
			//String searchKeyword = compinies[i].getSearchKeyword();
			CompanyApplicant[] translations = cm.getTranslationsByCompanyId(companyId);
			Map<String, Integer> transMap = new HashMap<String, Integer>();
			
			int totalTranslations = translations.length;
			if (totalTranslations > 5) {
				occuranceThreshold = Math.round(totalTranslations / 10);	
			} else {
				occuranceThreshold = totalTranslations;	
			}
			
			for (j = 0; j < totalTranslations; j++) {
				String translation = translations[j].getCompanyName();
				if (transMap.containsKey(translation)) {
					int val = transMap.get(translation);
					transMap.replace(translation, val);
				} else {
					transMap.put(translation, 1);
				}
			}
			
			for (j = 0; j < totalTranslations; j++) {
				String translation = translations[j].getCompanyName();
				int personid = translations[j].getPersonId();
				String flag = "-------------------";
				int occurance = transMap.get(translation);
				if (occurance >= occuranceThreshold && this.isSimilarStrings(originalEnglishName, translation, 2)) {
					flag = "good";
				} else if (this.isSimilarStrings(originalEnglishName, translation, 3)) {
					flag = "good";
				}
				//format companyId\toriginalEnglishName\tEPO Translation\tflag
				//flag
				String output = Integer.toString(companyId)
						+ "\t" + personid
						+ "\t" + sourceFileId
						+ "\t" + originalName
						+ "\t" + originalEnglishName 
						+ "\t" + translation + "\t" + flag;
				System.out.println(output);
			}
		}
		
		cm.close();
	}
	
	/**
	 * Check if search string matches target string
	 * Goal: half of search string should match
	 * 
	 * @param String s1 search string
	 * @param String s2 target string
	 * @return boolean -1 false and others true
	 */
	protected boolean isSimilarStrings(String s1, String s2, int factor) {
		boolean result = false;
		int threshold = s1.length() / factor;
		LevenshteinDistance ld = new LevenshteinDistance(threshold);
		int diff = ld.apply(s1.toLowerCase(), s2.toLowerCase());
		
		//System.out.println(s1 + " and " + s2 + " diff is " + diff);
		
		if (diff >= 0) {
			result = true;
		}
		
		return result;
	}
	
	public void generatePatentListByPersonIds() {
		ExcelFileReader fr = new ExcelFileReader(PERSON_ID_FILE_PATH);
		CompanyApplicant[] translations = fr.readCompanyPersonPairs();
		int i = 0;
		int j = 0;
		int size = translations.length;
		//System.out.println("There are total: " + size);
		PatentMapper pm = new PatentMapper();
		CompanyMapper cm = new CompanyMapper();
		
		for (i = 0; i < size; i++) {
			int companyId = translations[i].getCompanyId();
			String epoName = translations[i].getCompanyName();
			int personId = translations[i].getPersonId();
			String[] pubNrs = pm.getPubNrByPersonId(personId);
			Company company = cm.get(companyId);
			//System.out.println("There are " + pubNrs.length + " publication numbers from person id " + personId);
			int pubNrLength = pubNrs.length;
			if (pubNrLength > 5) {
				continue;
			}
			int sourceFileId = company.getSourceExcelLineNumbers();
			String originalChineseName = company.getChineseName();
			String originalEnglishName = company.getEnglishName();
			for (j = 0; j < pubNrLength; j++) {
				System.out.println(
							companyId +
							"\t" +
							sourceFileId +
							"\t" +
							personId +
							"\t" +
							originalChineseName +
							"\t" +
							originalEnglishName +
							"\t" +
							epoName +
							"\t" +
							pubNrs[j]
						);
			}
		}
	}
	
	public void findWipoApplicantByPubNr() {
		//this.apiReader.readWipoSimpleSearchUsingPubNr("202894649");
		FileReader fr = new FileReader("./output/" + PERSON_PUBNR_FILE);
		WipoDataFileReader wfr = new WipoDataFileReader("");
		CompanyMapper cm = new CompanyMapper();
		String[] lines = fr.getAllLines();
		String[] elements = null;
		String publnNr = "";
		int i = 0;
		
		Set<String> set = new HashSet<String>();

		//String[] uniqueLines = set.toArray(new String[0]);
		
		//System.out.println(lines.length + " vs " + uniqueLines.length);
		
		for (i = 0; i < lines.length; i++) {
			if (!set.add(lines[i])) {
				continue;
			}
			elements = lines[i].split("\t");
			int companyId = Integer.parseInt(elements[0]);
			publnNr = elements[6];
			Company comp = cm.get(companyId);
			String applicantName = wfr.getApplicantNameByKeywordPublnNr(comp.getSearchKeyword(), publnNr);
			//System.out.println("Search keyword is: " + comp.getSearchKeyword() + " and publication no is: " + publnNr + " and Applicant Name is: " + applicantName);
			System.out.print(lines[i]);
			if (applicantName.length() > 0) {
				System.out.println("\t" + applicantName);
			} else {
				System.out.println();
			}
		}
		
		//System.out.println("There are " + dupCounter + " duplicates in " + lines.length + " lines");
		
	}
	
	public void formatWipoApplicantsFile() {
		FileReader fr = new FileReader("./output/" + PERSON_PUBNR_FILE2);
		String[] lines = fr.getAllLines();
		int total = lines.length;
		int i = 0;
		int j = 0;
		int ct = 0;
		
		HashMap<String, String> map = new HashMap<String, String>();
		HashSet<String> set = new HashSet<String>();
		
		for (i = 0; i < total; i++) {
			String[] elements = lines[i].split("\t");
			if (elements.length < 8) {
				ct++;
				continue;
			}
			
			if (!this.validateString(elements[7])) {
				ct++;
				continue;
			}
			
			System.out.println(elements[6] + "\t" + elements[7]);
			
			String[] keyArray = Arrays.copyOfRange(elements, 0, 6);
			String key = String.join("\t", keyArray);
			
			System.out.println("key was: " + lines[i]);
			System.out.println("Key is: " + key);
			
			if (map.containsKey(key)) {
				String value = map.get(key);
				String[] valueArray = value.split("\\|");
				for (j = 0; j < valueArray.length; j++) {
					set.add(valueArray[j]);
				}
				set.add(elements[7]);
				valueArray = set.toArray(new String[0]);
				value = String.join("|", valueArray);
				map.replace(key, value);
			} else {
				set.clear();
				map.put(key, elements[7]);
			}
			
			//System.out.println(lines[i]);
		}
		
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pair = it.next();
			System.out.println(pair.getKey() + "\t" + pair.getValue());
		}
		
		System.out.println("There are total " + total + " lines and " + ct + " incomplete data and " + map.size() + " validate data");
		//String[] tmpElements = ArrayUtils.removeElement(elements, elements[elements.length - 1]);
		//String tmpLine = String.join("\t", tmpElements);
	}
	
	/* jre 8 version
	protected boolean validateString(String string) {
		 return string.codePoints().anyMatch(
		            codepoint ->
		            Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
	}
	*/
	
	//jre 7 version
	protected boolean validateString(String string) {
		 return string.codePoints().anyMatch(
		            new IntPredicate() {
						@Override
						public boolean test(int codepoint) {
							return Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN;
						}
					});
	}
	
	
	//second half of the code
	//1. find all patents according to human filtered person ids
	//2. find all citations according to patents
	//3. count all patents and citations
	//4. generate output file
	public void processPersonIds() {
		this.findAllPatentsByPersonIds();
		this.getAllCitations();
		this.countAllCitationsForEachCompany();
		this.generateOutputExcelFiles();
	}
	
	protected void findAllPatentsByPersonIds() {
		PatentMapper pm = new PatentMapper();
		CompanyMapper cm = new CompanyMapper();
		ExcelFileReader efr = new ExcelFileReader("");
		//refined file got from professor
		String[] refinedLines = efr.readRefinedPersonsFileHSSF(REFINED_PERSONS_FILE);
		//refined by us
		String[] originalLines = efr.readRefinedPersonsFileXSSF(ORIGINAL_PERSONS_FILE);
		int i = 0;
		//related
		int rCt = 0;
		//not related
		int nrCt = 0;
		//none (unknown flag)
		int ct = 0;
		//flag length > 7 (------------  ones)
		int valid = 0;
		
		for (i = 1; i < originalLines.length; i++) {
//			if (i < 10000) {
//				continue;
//			}
			System.out.println("Processing original line " + i);
			//0 ra company id
			//1 source company name in Chinese
			//2 source company name in English
			//3 epo person id
			//4 epo person name
			//5 flag
			String[] elements = originalLines[i].split("\t");
			//System.out.println("There are total " + elements.length + " columns in line " + i);
			if (elements.length < 6) {
				nrCt++;
				continue;
			}
			String flag = elements[5];
			//System.out.println(flag);
			if (!flag.equalsIgnoreCase("good")) {
				nrCt++;
				continue;
			}
			valid++;
			double rawCompId = Double.parseDouble(elements[0]);
			int sourceCompanyId = (int) rawCompId;
			int personId = Double.valueOf(elements[3]).intValue();
			int companyId = cm.getCompanyIdBySourceFileId(sourceCompanyId);
//			if (companyId % 100 != 1) {
//				continue;
//			}
			cm.saveCompApplntByPersonId(companyId, personId);
			pm.getPatentsByPersonId(personId, companyId);
		}
		
		
		for (i = 1; i < refinedLines.length; i++) {
//			if (i > 0) {
//				break;
//			}
			System.out.println("Processing refined line " + i);
			//System.out.println(lines[i]);
			//index
			//0 ra company id
			//1 person id
			//2 source file id
			//3 original chinese name
			//4 original english name
			//5 person name
			//6 flag
			String[] elements = refinedLines[i].split("\t");
			//System.out.println("Element 7 is " + elements[7]);
			String flag = elements[7];
			//System.out.println("Flag string length is " + flag.length());
			if (flag.equalsIgnoreCase("related")) {
				rCt++;
			} else if (flag.equalsIgnoreCase("not related")) {
				nrCt++;
			} else {
				//System.out.println(lines[i]);
				//System.out.println(flag);
				ct++;
			}
			
			if (flag.length() > 7) {
				//System.out.println(flag + "----------");
				continue;
			}
			
			valid++;
			//System.out.println(flag);
			
			double rawCompId = Double.parseDouble(elements[0]);
			int sourceCompanyId = (int) rawCompId;
			int personId = Double.valueOf(elements[3]).intValue();
			//System.out.println("Element 0 is " + companyId);
			//System.out.println("Element 3 is " + personId);
			int companyId = cm.getCompanyIdBySourceFileId(sourceCompanyId);
//			if (companyId % 100 != 1) {
//				continue;
//			}
			cm.saveCompApplntByPersonId(companyId, personId);
			pm.getPatentsByPersonId(personId, companyId);
			//pm.getPatentsByPersonId(personId, companyId);
			
			//System.out.println(elements[0]);
		}
		
		System.out.println("There are " + rCt + " related");
		System.out.println("There are " + nrCt + " not related");
		System.out.println("There are " + ct + " none");
		System.out.println("There are " + valid + " valid person ids");
		
		pm.close();
		cm.close();
	}
	
}
