package citationsearch.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import citationsearch.entity.Company;
import citationsearch.entity.CompanyApplicant;
import citationsearch.mapper.CompanyMapper;

import static citationsearch.constants.Constants.*;

public class ExcelFileReader extends Reader
{	
	public ExcelFileReader(String location) {
		super(location);
	}
	
	public String[] read() {
		ArrayList<String> companyNames = new ArrayList<String>();
		
		File file = new File(this.location);
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

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    for(int r = 1; r < rows; r++) {
		    //for(int r = EXCELFILE_START; r < EXCELFILE_END; r++) {
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
		                		searchKeyword = removeUselessChars(companyName).trim();
		                		//remove special html character &nbsp;
		                		searchKeyword = searchKeyword.replaceAll("\u00A0", "");
		                		
		                		//get english name
		                		cell = row.getCell((short)(c + 6));
		                		String englishName = cell.toString().trim().replace("'", "''");
		                		//get source file id
		                		cell = row.getCell((short)(c - 2));
		                		String sourceFileId = cell.toString().trim();
		                		
		                		companyNames.add(searchKeyword);	
		                		this.saveCompany(companyName, searchKeyword, englishName, sourceFileId);
		                	}
		                }
		            }
		        }
		    }
		    
		    wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return companyNames.toArray(new String[0]);

	}
	
	protected void saveCompany(String companyName, String searchKeyword, String englishName, String sourceFileId) {
		Company company = new Company();
		CompanyMapper compMapper = new CompanyMapper();
		company.setChineseName(companyName);
		company.setSearchKeyword(searchKeyword);
		company.setEnglishName(englishName);
		double sourceFileLineNumn = Double.parseDouble(sourceFileId);
		company.setSourceExcelLineNumbers((int)(sourceFileLineNumn));
		compMapper.save(company);
		compMapper.close();
		
	}
	
	/**
	 * Remove/Replace full-width string
	 * Rules:
	 * - semicolon will be replaced with " or "
	 * - parenthesis and text inside will be removed
	 *   * some texts can be kept i.e. 上海, 深圳
	 *   * some texts have been removed already i.e. (集团)
	 *   * some texts should be removed i.e.  (SZ.300365) , (本部)
	 * 
	 * @param String string raw string
	 * @return String
	 */
	public String removeUselessChars(String string) {
		StringBuffer newString = new StringBuffer();
		int i = 0;
		boolean isEnglishName = false;
		
		if (string.indexOf(' ') >= 0) {
			String[] subStrings = string.split(" ");
			newString.append(subStrings[0]);
			if (
					(subStrings[0].charAt(0) >= 'A'
					&& subStrings[0].charAt(0) <= 'Z')
					|| (subStrings[0].charAt(0) >= 'a'
					&& subStrings[0].charAt(0) <= 'z')
					) {
				isEnglishName = true;
			}
			
			for (i = 1; i < subStrings.length; i++) {
				if (
						isEnglishName
						&& subStrings[i].charAt(0) != '('
						&& subStrings[i].charAt(0) != '\uFF08'
						) {
					newString.append(' ');
					newString.append(subStrings[i]);
				} else if (
						!isNotValidChar(subStrings[i].charAt(0))
						&& !Character.isLetter(subStrings[i].charAt(0))
						) {
					newString.append(subStrings[i]);
				} else if (Character.UnicodeScript.of(subStrings[i].charAt(0)) == Character.UnicodeScript.HAN) {
					newString.append(subStrings[i]);
				}
			}
		} else {
			newString.append(string);
		}
		
		removeParas(newString);
		removeUselessCommonWords(newString);
		
		return newString.toString().trim();
	}
	
	protected boolean isNotValidChar(char c) {
		if (Character.isDigit(c) || c == '(' || c == '\uFF08') {
			return true;
		}
		
		return false;
	}
	
	protected void removeParas(StringBuffer stringBuf) {
		int i = 0;
		boolean deleteMode = false;
		
		for (i = 0; i < stringBuf.length(); i++) {
			if (stringBuf.charAt(i) == '(' || stringBuf.charAt(i) == '\uFF08') {
				deleteMode = true;
			}
			
			if (deleteMode) {
				if (stringBuf.charAt(i) == ')' || stringBuf.charAt(i) == '\uFF09') {
					deleteMode = false;
				}
				stringBuf.deleteCharAt(i);
				i--;
			}
		}
	}
	
	protected void removeUselessCommonWords(StringBuffer stringBuf) {
		int i = 0;
		String tmpStr = stringBuf.toString();
		for (i = 0; i < KEYWORDS.length; i++) {
			tmpStr = tmpStr.replace(KEYWORDS[i], "");
		}
		
		tmpStr = tmpStr.replace(";", " or ");
		tmpStr = tmpStr.replace(FULL_WIDTH_SEMICOLON, " or ");
		
		stringBuf.setLength(0);
		stringBuf.append(tmpStr);
	}
	
	public CompanyApplicant[] readCompanyPersonPairs() {
		ArrayList<CompanyApplicant> translations = new ArrayList<CompanyApplicant>();
		File file = new File(this.location);
		
		try {
		    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		    
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    for(int r = 0; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		        	String companyIdString = "";
		        	int companyId = 0;
		        	String personIdString = "";
		        	int personId = 0;
		        	String flag = "------";
		        	String epoName = "";
		        	
		            for(int c = 0; c < cols; c++) {
		            	if (c == 0) {
		            		//company id
		            		cell = row.getCell((short)c);
			                if(cell != null) {
			                	companyIdString = cell.toString().trim();
			                }
		            	}
		            	if (c == 2) {
		            		//person id
		            		cell = row.getCell((short)c);
			                if(cell != null) {
			                	personIdString = cell.toString().trim(); 
			                }
		            	}
		            	
		            	if (c == 6) {
		            		//epo name
		            		cell = row.getCell((short)c);
		            		if(cell != null) {
		            			epoName = cell.toString().trim();	
		            		}
		            	}
		            	
		            	if (c == 7) {
		            		//good flag
		            		cell = row.getCell((short)c);
		            		if(cell != null) {
			            		flag = cell.toString();
			            		//System.out.println("Flag is: " + flag);	
		            		}
		            		
		            	}
		            }
		            if (companyIdString.length() > 0 && personIdString.length() > 0 && epoName.length() > 0 && flag.length() > 4) {
		            	companyId = (int) Double.parseDouble(companyIdString);
		            	personId = (int) Double.parseDouble(personIdString);
		            	CompanyApplicant trans = new CompanyApplicant();
		            	trans.setCompanyId(companyId);
		            	trans.setPersonId(personId);
		            	trans.setCompanyName(epoName);
		            	translations.add(trans);
		            }
		        }
		    }
		    
		    wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return translations.toArray(new CompanyApplicant[0]);
	}
}
