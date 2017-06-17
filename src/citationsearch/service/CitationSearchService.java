package citationsearch.service;

import citationsearch.utility.ExcelFileReader;
import citationsearch.utility.ExcelFileWriter;
import citationsearch.utility.WipoDataFileReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import citationsearch.entity.Company;
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
		this.total = this.apiReader.batchRead();
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
		
		
		this.getAllReplatedPatents();
		this.getAllCitations();
		this.countAllCitationsForEachCompany();
	}
	
	protected void getAllReplatedPatents() {
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
			patents = pm.getPatentsByCompanyId(companyId);
			for (j = 0; j < patents.length; j++) {
				pm.getAllRelatedPatents(companies[i], patents[j]);
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
			int totalPatents = 0;
			for (j = 0; j < patents.length; j++) {
				if (patents[j].getPatPublnId() <= 0) {
					continue;
				}
				totalPatents++;
			}
			companies[i].setPatentsTotal(totalPatents);
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
}
