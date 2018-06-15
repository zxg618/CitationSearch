package citationsearch.utility;

import static citationsearch.constants.Constants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import citationsearch.entity.*;
import citationsearch.mapper.PatStatsMapper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import citationsearch.constants.PatentTypeEnum;
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

	public void generatePatStatsOutputExcelFiles() {
		System.out.print("Writing to " + PAT_STATS_FILENAME + "....");

		PatStatsMapper psm = new PatStatsMapper();
		List<PatStats> psList = psm.getAllPatStats();

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		XSSFRow row = null;
		XSSFCell cell = null;
		FileOutputStream fileOut = null;
		int rowCount = psList.size();
		int i = 0;

		//write column titles
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(PAT_STATS_COLUMN_1);
		cell = row.createCell(1);
		cell.setCellValue(PAT_STATS_COLUMN_2);
		cell = row.createCell(2);
		cell.setCellValue(PAT_STATS_COLUMN_3);
		cell = row.createCell(3);
		cell.setCellValue(PAT_STATS_COLUMN_4);
		cell = row.createCell(4);
		cell.setCellValue(PAT_STATS_COLUMN_5);
		cell = row.createCell(5);
		cell.setCellValue(PAT_STATS_COLUMN_6);
		cell = row.createCell(6);
		cell.setCellValue(PAT_STATS_COLUMN_7);
		cell = row.createCell(7);
		cell.setCellValue(PAT_STATS_COLUMN_8);
		cell = row.createCell(8);
		cell.setCellValue(PAT_STATS_COLUMN_9);
		cell = row.createCell(9);
		cell.setCellValue(PAT_STATS_COLUMN_10);
		cell = row.createCell(10);
		cell.setCellValue(PAT_STATS_COLUMN_11);
		cell = row.createCell(11);
		cell.setCellValue(PAT_STATS_COLUMN_12);
		cell = row.createCell(12);
		cell.setCellValue(PAT_STATS_COLUMN_13);
		cell = row.createCell(13);
		cell.setCellValue(PAT_STATS_COLUMN_14);
		cell = row.createCell(14);
		cell.setCellValue(PAT_STATS_COLUMN_15);
		cell = row.createCell(15);
		cell.setCellValue(PAT_STATS_COLUMN_16);
		cell = row.createCell(16);
		cell.setCellValue(PAT_STATS_COLUMN_17);
		cell = row.createCell(17);
		cell.setCellValue(PAT_STATS_COLUMN_18);
		cell = row.createCell(18);
		cell.setCellValue(PAT_STATS_COLUMN_19);
		cell = row.createCell(19);
		cell.setCellValue(PAT_STATS_COLUMN_20);
		cell = row.createCell(20);
		cell.setCellValue(PAT_STATS_COLUMN_21);
		cell = row.createCell(21);
		cell.setCellValue(PAT_STATS_COLUMN_22);
		cell = row.createCell(22);
		cell.setCellValue(PAT_STATS_COLUMN_23);
		cell = row.createCell(23);
		cell.setCellValue(PAT_STATS_COLUMN_24);
		cell = row.createCell(24);
		cell.setCellValue(PAT_STATS_COLUMN_25);
		cell = row.createCell(25);
		cell.setCellValue(PAT_STATS_COLUMN_26);
		cell = row.createCell(26);
		cell.setCellValue(PAT_STATS_COLUMN_27);
		cell = row.createCell(27);
		cell.setCellValue(PAT_STATS_COLUMN_28);

		for (i = 0; i < rowCount; i++) {
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(psList.get(i).getID());
			cell = row.createCell(1);
			cell.setCellValue(psList.get(i).getCompanyId());
			cell = row.createCell(2);
			cell.setCellValue(psList.get(i).getSourceCompanyId());
			cell = row.createCell(3);
			cell.setCellValue(psList.get(i).getDealDate().toString());

			cell = row.createCell(4);
			cell.setCellValue(psList.get(i).getCN_PAT_AT_DEAL_ALL());
			cell = row.createCell(5);
			cell.setCellValue(psList.get(i).getCN_PAT_AT_DEAL_U());
			cell = row.createCell(6);
			cell.setCellValue(psList.get(i).getCN_PAT_AT_DEAL_I());
			cell = row.createCell(7);
			cell.setCellValue(psList.get(i).getCN_PAT_AT_DEAL_A());
			cell = row.createCell(8);
			cell.setCellValue(psList.get(i).getCN_PAT_3Y_AFTER_DEAL_ALL());
			cell = row.createCell(9);
			cell.setCellValue(psList.get(i).getCN_PAT_3Y_AFTER_DEAL_U());
			cell = row.createCell(10);
			cell.setCellValue(psList.get(i).getCN_PAT_3Y_AFTER_DEAL_I());
			cell = row.createCell(11);
			cell.setCellValue(psList.get(i).getCN_PAT_3Y_AFTER_DEAL_A());
			cell = row.createCell(12);
			cell.setCellValue(psList.get(i).getCN_PAT_5Y_AFTER_DEAL_ALL());
			cell = row.createCell(13);
			cell.setCellValue(psList.get(i).getCN_PAT_5Y_AFTER_DEAL_U());
			cell = row.createCell(14);
			cell.setCellValue(psList.get(i).getCN_PAT_5Y_AFTER_DEAL_I());
			cell = row.createCell(15);
			cell.setCellValue(psList.get(i).getCN_PAT_5Y_AFTER_DEAL_A());
			cell = row.createCell(16);
			cell.setCellValue(psList.get(i).getFR_PAT_AT_DEAL_ALL());
			cell = row.createCell(17);
			cell.setCellValue(psList.get(i).getFR_PAT_AT_DEAL_U());
			cell = row.createCell(18);
			cell.setCellValue(psList.get(i).getFR_PAT_AT_DEAL_I());
			cell = row.createCell(19);
			cell.setCellValue(psList.get(i).getFR_PAT_AT_DEAL_A());
			cell = row.createCell(20);
			cell.setCellValue(psList.get(i).getFR_PAT_3Y_AFTER_DEAL_ALL());
			cell = row.createCell(21);
			cell.setCellValue(psList.get(i).getFR_PAT_3Y_AFTER_DEAL_U());
			cell = row.createCell(22);
			cell.setCellValue(psList.get(i).getFR_PAT_3Y_AFTER_DEAL_I());
			cell = row.createCell(23);
			cell.setCellValue(psList.get(i).getFR_PAT_3Y_AFTER_DEAL_A());
			cell = row.createCell(24);
			cell.setCellValue(psList.get(i).getFR_PAT_5Y_AFTER_DEAL_ALL());
			cell = row.createCell(25);
			cell.setCellValue(psList.get(i).getFR_PAT_5Y_AFTER_DEAL_U());
			cell = row.createCell(26);
			cell.setCellValue(psList.get(i).getFR_PAT_5Y_AFTER_DEAL_I());
			cell = row.createCell(27);
			cell.setCellValue(psList.get(i).getFR_PAT_5Y_AFTER_DEAL_A());
		}

		for (i = 0; i < PAT_STATS_COLUMNS; i++) {
			sheet.autoSizeColumn(i);
		}

		try {
			fileOut = new FileOutputStream(PAT_STATS_FILENAME);
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		                cell = row.getCell(c);
		                if(cell != null) {
		                	String companyName = cell.toString().trim();
		                	if (companyName.length() > 0) {
		                		searchKeyword = reader.removeUselessChars(companyName).trim();
		                		//remove special html character &nbsp;
		                		searchKeyword = searchKeyword.replaceAll("\u00A0", "");
		                		
		                		//get source file id
		                		cell = row.getCell(c - 2);
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

	/**
	 * Slightly updated to server addDealDate()
	 *
	 * @param companies
	 */
	public void addAllSourceFileIdsToCompanyByFan(Company[] companies) {
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
				if (row != null) {
					XSSFCell idCell = row.getCell(0);
					XSSFCell nameCell = row.getCell(2);
					XSSFCell dealDateCell = row.getCell(6);

					if (nameCell != null) {
						String companyName = nameCell.toString().trim();
						if (companyName.length() > 0) {
							searchKeyword = reader.removeUselessChars(companyName).trim();
							//remove special html character &nbsp;
							searchKeyword = searchKeyword.replaceAll("\u00A0", "");

							//get source file id
							String sourceFileId = idCell.toString().trim();
							double sourceFileLineNumn = Double.parseDouble(sourceFileId);
							sourceFileId = Integer.toString((int) sourceFileLineNumn);

							//get dealDateCell
							String dealDateStr = dealDateCell.toString().trim();

							for (i = 0; i < companies.length; i++) {
								if (searchKeyword.equals(companies[i].getSearchKeyword())) {
									CompanyDealDate dealDate = new CompanyDealDate();

									dealDate.setCompanyId(companies[i].getID());
									dealDate.setSourceCompanyId((int) sourceFileLineNumn);
									dealDate.setDealDateFromString(dealDateStr);

									companies[i].addDealDate(dealDate);
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
		row = sheet.createRow(0);
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
			row = sheet.createRow(i + 1);
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
		row = sheet.createRow(0);
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
				row = sheet.createRow(i + 1);
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
		row = sheet.createRow(0);
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
				row = sheet.createRow(i + 1);
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
		row = sheet.createRow(currentLine);
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
				row = sheet.createRow(currentLine);
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
