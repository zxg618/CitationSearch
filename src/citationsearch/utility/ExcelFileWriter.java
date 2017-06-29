package citationsearch.utility;

import static citationsearch.constants.Constants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import citationsearch.constants.PatentTypeEnum;
import citationsearch.entity.Citation;
import citationsearch.entity.Company;
import citationsearch.entity.CompanyApplicant;
import citationsearch.entity.Patent;
import citationsearch.mapper.CitationMapper;
import citationsearch.mapper.CompanyMapper;
import citationsearch.mapper.PatentMapper;

public class ExcelFileWriter {
	protected Company[] companies = null;
	protected CompanyMapper companyMapper = null;
	protected Patent[] patents = null;
	protected PatentMapper patentMapper = null;
	protected Citation[] citations = null;
	protected CitationMapper citationMapper = null;
	
	
	
	public ExcelFileWriter() {
		this.companyMapper = new CompanyMapper();
		this.patentMapper = new PatentMapper();
		this.citationMapper = new CitationMapper();
	}



	public void generateOutputFiles() {
		System.out.print("Writing to " + COMPANY_FILENAME + "....");
		this.generateCompanyFile();
		System.out.println("Done.");
		System.out.print("Writing to " + PATENT_FILENAME + "....");
		this.generatePatentFile();
		System.out.println("Done.");
		System.out.print("Writing to " + CITATION_FILENAME + "....");
		this.generateCitationFile();
		System.out.println("Done.");
		System.out.print("Writing to " + COMPANY_TRANSLATION_FILENAME + "....");
		this.generateTranslationFile();
		System.out.println("Done.");
	}
	
	protected void addAllSourceFileIdsToCompany(Company[] companies) {
		ExcelFileReader reader = new ExcelFileReader(FILE_PATH);
		
		File file = new File(FILE_PATH);
		String searchKeyword = "";
		
		try {
		    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		    
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;
		    int i = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 0; c < cols; c++) {
		            	if (c != 2) {
		            		continue;
		            	}
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                	String companyName = cell.toString().trim();
		                	if (companyName.length() > 0) {
		                		searchKeyword = reader.removeUselessChars(companyName).trim();
		                		//remove special html character &nbsp;
		                		searchKeyword = searchKeyword.replaceAll("\u00A0", "");
		                		
		                		//get source file id
		                		cell = row.getCell((short)(c - 2));
		                		String sourceFileId = cell.toString().trim();
		                		double sourceFileLineNumn = Double.parseDouble(sourceFileId);
		                		sourceFileId = Integer.toString((int)sourceFileLineNumn);
		                		
		                		for (i = 0; i < companies.length; i++) {
		                			if (searchKeyword.equals(companies[i].getSearchKeyword())) {
		                				if (companies[i].getSourceFileIds().length() == 0) {
		                					companies[i].setSourceFileIds(sourceFileId);
		                				} else {
		                					companies[i].setSourceFileIds(companies[i].getSourceFileIds() + ", " + sourceFileId);
		                				}
		                			}
		                		}
		           
		                	}
		                }
		            }
		        }
		    }
		    
		    wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void generateCompanyFile() {
		this.companies = companyMapper.getAllCompanys();
		this.addAllSourceFileIdsToCompany(this.companies);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		XSSFRow row = null;
		XSSFCell cell = null;
		FileOutputStream fileOut = null;
		int rowCount = this.companies.length;
		int i = 0;
		
		//write column titles
		row = sheet.createRow((short)0);
		cell = row.createCell(0);
		cell.setCellValue(COMPANY_COLUMN1);
		cell = row.createCell(1);
		cell.setCellValue(COMPANY_COLUMN2);
		cell = row.createCell(2);
		cell.setCellValue(COMPANY_COLUMN3);
		cell = row.createCell(3);
		cell.setCellValue(COMPANY_COLUMN4);
		cell = row.createCell(4);
		cell.setCellValue(COMPANY_COLUMN5);
		cell = row.createCell(5);
		cell.setCellValue(COMPANY_COLUMN6);
		
		for (i = 0; i < rowCount; i++) {
			row = sheet.createRow((short)(i + 1));
			cell = row.createCell(0);
		    cell.setCellValue(this.companies[i].getID());
		    cell = row.createCell(1);
		    cell.setCellValue(this.companies[i].getSourceFileIds());
		    cell = row.createCell(2);
		    cell.setCellValue(this.companies[i].getChineseName());
		    cell = row.createCell(3);
		    cell.setCellValue(this.companies[i].getEnglishName());
		    cell = row.createCell(4);
		    cell.setCellValue(this.companies[i].getPatentsTotal());
		    cell = row.createCell(5);
		    cell.setCellValue(this.companies[i].getCitationTotal());
		}
		
		for (i = 0; i < COMPANY_COLUMNS; i++) {
			sheet.autoSizeColumn(i);	
		}
		
		try {
			fileOut = new FileOutputStream(COMPANY_FILENAME);
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void generatePatentFile() {
		int start = 0;
		int end = 1000;
		int increment = 1000;
		int i = 0;
		int j = 0;
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		XSSFRow row = null;
		XSSFCell cell = null;
		FileOutputStream fileOut = null;
		Patent[] patents = null;
		int rowCount = this.patentMapper.getTotalNumberOfPatents();
		String typeKey = "";
		
		//write column titles
		row = sheet.createRow((short)0);
		cell = row.createCell(0);
		cell.setCellValue(PATENT_COLUMN1);
		cell = row.createCell(1);
		cell.setCellValue(PATENT_COLUMN2);
		cell = row.createCell(2);
		cell.setCellValue(PATENT_COLUMN3);
		cell = row.createCell(3);
		cell.setCellValue(PATENT_COLUMN4);
		cell = row.createCell(4);
		cell.setCellValue(PATENT_COLUMN5);
		cell = row.createCell(5);
		cell.setCellValue(PATENT_COLUMN6);
		cell = row.createCell(6);
		cell.setCellValue(PATENT_COLUMN7);
		cell = row.createCell(7);
		cell.setCellValue(PATENT_COLUMN8);
		cell = row.createCell(8);
		cell.setCellValue(PATENT_COLUMN9);
		cell = row.createCell(9);
		cell.setCellValue(PATENT_COLUMN10);
		cell = row.createCell(10);
		cell.setCellValue(PATENT_COLUMN11);
		cell = row.createCell(11);
		cell.setCellValue(PATENT_COLUMN12);
		cell = row.createCell(12);
		cell.setCellValue(PATENT_COLUMN13);
		cell = row.createCell(13);
		cell.setCellValue(PATENT_COLUMN14);
		
		while (start <= rowCount) {
			patents = this.patentMapper.getPatentsByIDRange(start, end);
			j = 0;
			for (i = start; i < start + patents.length; i++) {
				row = sheet.createRow((short)(i + 1));
				cell = row.createCell(0);
				cell.setCellValue(patents[j].getID());
				cell = row.createCell(1);
				cell.setCellValue(patents[j].getCompanyId());
				cell = row.createCell(2);
				cell.setCellValue(patents[j].getPatPublnId());				
				cell = row.createCell(3);
				cell.setCellValue(patents[j].getPublicationNumber());
				cell = row.createCell(4);
				cell.setCellValue(patents[j].getPublnDateString());
				cell = row.createCell(5);
				cell.setCellValue(patents[j].getApplnNum());
				cell = row.createCell(6);
				cell.setCellValue(patents[j].getDocdbFamId());
				cell = row.createCell(7);
				cell.setCellValue(patents[j].getPriorityNum());
				cell = row.createCell(8);
				String applnNum = patents[j].getApplnNum();
				if (applnNum.length() <= 0) {
					typeKey = "N/A";
				} else {
					if (applnNum.length() > 8) {
						typeKey = applnNum.substring(4, 5);
					} else {
						typeKey = applnNum.substring(2, 3);
					}
				}
				cell.setCellValue(PatentTypeEnum.getValue(typeKey) + "(" + typeKey + ")");
				cell = row.createCell(9);
				cell.setCellValue(patents[j].getPrefix());
				cell = row.createCell(10);
				cell.setCellValue(patents[j].getPostfix());
				cell = row.createCell(11);
				String dateString = patents[j].getApplnDateString();
				if (dateString.matches("1900-01-01")) {
					dateString = "";
				}
				cell.setCellValue(dateString);
				cell = row.createCell(12);
				cell.setCellValue(patents[j].getAppEarliestDateString());
				cell = row.createCell(13);
				cell.setCellValue(patents[j].getCitationTotal());
				j++;
			}
			start += increment;
			end += increment;
		}
		
		for (i = 0; i < PATENT_COLUMNS; i++) {
			sheet.autoSizeColumn(i);	
		}
		
		try {
			fileOut = new FileOutputStream(PATENT_FILENAME);
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void generateCitationFile() {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		XSSFRow row = null;
		XSSFCell cell = null;
		FileOutputStream fileOut = null;
		Citation[] citations = null;
		int rowCount = this.citationMapper.getTotalNumberOfCitations();
		int start = 0;
		int end = 1000;
		int increment = 1000;
		int i = 0;
		int j = 0;
		
		//write column titles
		row = sheet.createRow((short)0);
		cell = row.createCell(0);
		cell.setCellValue(CITATION_COLUMN1);
		cell = row.createCell(1);
		cell.setCellValue(CITATION_COLUMN2);
		cell = row.createCell(2);
		cell.setCellValue(CITATION_COLUMN3);
		cell = row.createCell(3);
		cell.setCellValue(CITATION_COLUMN4);
		cell = row.createCell(4);
		cell.setCellValue(CITATION_COLUMN5);
		cell = row.createCell(5);
		cell.setCellValue(CITATION_COLUMN6);
		cell = row.createCell(6);
		cell.setCellValue(CITATION_COLUMN7);
		cell = row.createCell(7);
		cell.setCellValue(CITATION_COLUMN8);
		cell = row.createCell(8);
		cell.setCellValue(CITATION_COLUMN9);
		cell = row.createCell(9);
		cell.setCellValue(CITATION_COLUMN10);
		cell = row.createCell(10);
		cell.setCellValue(CITATION_COLUMN11);
		cell = row.createCell(11);
		cell.setCellValue(CITATION_COLUMN12);
		cell = row.createCell(12);
		cell.setCellValue(CITATION_COLUMN13);
		cell = row.createCell(13);
		cell.setCellValue(CITATION_COLUMN14);
		cell = row.createCell(14);
		cell.setCellValue(CITATION_COLUMN15);
		
		while (start <= rowCount) {
			citations = this.citationMapper.getCitationsByIDRange(start, end);
			j = 0;
			for (i = start; i < start + citations.length; i++) {
				row = sheet.createRow((short)(i + 1));
				cell = row.createCell(0);
				cell.setCellValue(citations[j].getID());
				cell = row.createCell(1);
				cell.setCellValue(citations[j].getPatentId());
				cell = row.createCell(2);
				cell.setCellValue(citations[j].getCompanyId());
				cell = row.createCell(3);
				cell.setCellValue(citations[j].getCitnId());
				cell = row.createCell(4);
				cell.setCellValue(citations[j].getCitingPatentId());
				cell = row.createCell(5);
				cell.setCellValue(citations[j].getCitingPublnNum());
				cell = row.createCell(6);
				cell.setCellValue(citations[j].getPublnDateString());
				cell = row.createCell(7);
				cell.setCellValue(citations[j].getCitingAppNum());
				cell = row.createCell(8);
				cell.setCellValue(citations[j].getCitingAppDocdbFamilyId());
				cell = row.createCell(9);
				cell.setCellValue(citations[j].getPriorityNumber());
				cell = row.createCell(10);
				cell.setCellValue(citations[j].getCitingApplnType());
				cell = row.createCell(11);
				cell.setCellValue(citations[j].getPrefix());
				cell = row.createCell(12);
				cell.setCellValue(citations[j].getPostfix());
				cell = row.createCell(13);
				cell.setCellValue(citations[j].getApplnDateString());
				cell = row.createCell(14);
				cell.setCellValue(citations[j].getEarliestFilingDateString());
				j++;
			}
			start += increment;
			end += increment;
		}
		
		for (i = 0; i < CITATION_COLUMNS; i++) {
			sheet.autoSizeColumn(i);	
		}
		
		try {
			fileOut = new FileOutputStream(CITATION_FILENAME);
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void generateTranslationFile() {
		this.companies = companyMapper.getAllCompanys();
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		XSSFRow row = null;
		XSSFCell cell = null;
		FileOutputStream fileOut = null;
		int i = 0;
		int j = 0;
		int companyId = 0;
		int currentLine = 0;
		CompanyApplicant[] translations = null;
		
		//write column titles
		row = sheet.createRow((short)currentLine);
		cell = row.createCell(0);
		cell.setCellValue(COMPANY_TRANS_COLUMN1);
		cell = row.createCell(1);
		cell.setCellValue(COMPANY_TRANS_COLUMN2);
		cell = row.createCell(2);
		cell.setCellValue(COMPANY_TRANS_COLUMN3);
		cell = row.createCell(3);
		cell.setCellValue(COMPANY_TRANS_COLUMN4);
		cell = row.createCell(4);
		cell.setCellValue(COMPANY_TRANS_COLUMN5);
		
		for (i = 0; i < companies.length; i++) {
			companyId = companies[i].getID();
			translations = this.companyMapper.getTranslationsByCompanyId(companyId);
			j = 0;
			for (j = 0; j < translations.length; j++) {
				currentLine++;
				row = sheet.createRow((short)currentLine);
				cell = row.createCell(0);
				cell.setCellValue(translations[j].getID());
				cell = row.createCell(1);
				cell.setCellValue(translations[j].getCompanyId());
				cell = row.createCell(2);
				cell.setCellValue(translations[j].getPersonId());
				cell = row.createCell(3);
				cell.setCellValue(companies[i].getChineseName());
				cell = row.createCell(4);
				cell.setCellValue(translations[j].getCompanyName());
			}
		}
		
		for (i = 0; i < COMPANY_TRANS_COLUMNS; i++) {
			sheet.autoSizeColumn(i);	
		}
		
		try {
			fileOut = new FileOutputStream(COMPANY_TRANSLATION_FILENAME);
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
