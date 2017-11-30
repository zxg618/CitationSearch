package citationsearch.utility;

import static citationsearch.constants.Constants.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ApiReader extends Reader
{
	protected String[] searchKeywords;
	protected String localFile = "";
	
	public ApiReader(String location) {
		super(location);
	}
	
	public void setSearchKeywords(String[] searchKeywords) {
		this.searchKeywords = searchKeywords;
	}

	public HashMap<String, String> batchRead() {
		//"loginForm=loginForm&loginForm%3A
		//email=zxg618%40gmail.com&loginForm%3A
		//password=zxg19830618&loginForm%3Aremember=on&loginForm%3AloginBtn=Login&javax.faces.ViewState=3595332667045381679%3A-4172698219479446334"
		
		
		HashMap<String, String> total = new HashMap<String, String>();
		int length = this.searchKeywords.length;
		
		Map<String, String> loginCookies = null;
		loginCookies = this.logIntoWipo();
		for (int i = 0; i < length; i++) {
			String value = read(this.searchKeywords[i], loginCookies);
			System.out.println((i + 1) + ". " + searchKeywords[i] + " has " + value + " patents.");
			total.put(this.searchKeywords[i], value);
		}
		
		return total;
	}
	
	protected boolean fileExist() {
		File f = new File(this.localFile);
		if(f.exists() && !f.isDirectory() && f.length() > 2) { 
			return true;
		}
		return false;
	}
	
	//login, seems not working, not sure
	protected Map<String, String> logIntoWipo() {
		Map<String, String> loginCookies = null;
		try {
			Connection.Response response = Jsoup.connect(LOG_IN_URL)
					.header("Host", "patentscope.wipo.int")
					.header("Origin", "https://patentscope.wipo.int")
					.header("Referer", "https://patentscope.wipo.int/search/en/reg/login.jsf")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Cache-Control", "max-age=0")
					.header("Connection", "keep-alive")
					.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
					.data(
							"loginForm", "loginForm",
							"loginForm:email", "zxg618@gmail.com",
							"loginForm:password", "zxg19830618",
							"loginForm:remember", "on",
							"loginForm:loginBtn", "Login"
							)
					.method(Method.POST)
					.execute();
			loginCookies = response.cookies();
			System.out.println("Login return status " + response.statusCode());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return loginCookies;
	}
	
	protected String read(String searchKeyword, Map<String, String> cookies) {
		String resultTableContent = "";
		String detailContent = "";
		
		String urlParamters = "";
		
		String total = "";
		this.localFile = WIPO_DATA_PATH + searchKeyword + DATA_FILE_POSTFIX;
		if (this.fileExist()) {
			return "FileExist";
		}
		
		if (searchKeyword.length() > 4) {
			urlParamters = QUERY_STRING_PART1 + searchKeyword + QUERY_STRING_PART2;	
		} else {
			urlParamters = QUERY_STRING_PART1 + "\"" + searchKeyword + "\"" + QUERY_STRING_PART2;
		}
		
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.localFile)));
			//read total number of patents
	        Document document = Jsoup.connect(this.location + urlParamters)
	        		.cookies(cookies)
	        		.maxBodySize(1024*1024*1024)
	        		.get();
	        //System.out.println(document.text());
	        resultTableContent = document.select("#resultListFormTop").text();
	        if (resultTableContent.length() == 0) {
	        	total = "search error";
	        	detailContent = document.select("#resultPanel1").text();
	        	if (detailContent.length() != 0) {
	        		total = "1";
	        		//TODO: get details from details page
	        		bw.write(detailContent);
	        		bw.close();
	        	} else {
	        		System.out.println("Search Error occurred.");
	        	}
	        	return total;
	        } else {
	        	int index1 = resultTableContent.indexOf("of");
		        int index2 = resultTableContent.indexOf("for Criteria");
		        total = resultTableContent.substring(index1 + 2, index2);	
	        }
	        
	        total = total.trim();
	        
	        //read result table context
	        //resultTableContent = document.select("#resultTable").text();
	        //this.saveToDisk(searchKeyword + ".out", resultTableContent);
	        Element table = document.select("#resultTable").get(1);
	        Elements rows = table.select("tr");
	        for (int i = 1; i < rows.size(); i++) {
	            Element row = rows.get(i);
	            Elements cols = row.select("td");
	            for (int j = 0; j < cols.size(); j++) {
	            	Element col = cols.get(j);
	            	bw.write(col.text());
	            	bw.newLine();
	            }
	        }
	        bw.close();
		        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return total.trim();
	}
	
	protected void saveToDisk(String fileName, String text) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
			bw.write(text);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String readWipoSimpleSearchUsingPubNr(String pubNr) {
		String applicantName = "";
		
		try {
			/*
			//read total number of patents
	        Document document = Jsoup.connect(this.location)
	        		//.followRedirects(true)
	        		.data(
							"simpleSearchSearchForm", "simpleSearchSearchForm",
							"simpleSearchSearchForm:j_idt379", "FP",
							"simpleSearchSearchForm:fpSearch", pubNr,
							"simpleSearchSearchForm:j_idt447", "workaround"
							)
					.post();
	        System.out.println(document.text());
	        System.out.println("-----------------------");
	        applicantName = document.select("detailMainForm:NPapplicants").text();
	        System.out.println(applicantName);
	        */
			
			Response response = Jsoup.connect(this.location)
					.followRedirects(false)
					.execute();
			System.out.println(response.url());
			System.out.println(response.header("location"));
			System.out.println(response.cookie("JSESSIONID"));
			//System.out.println(response.cookie("\\_ga"));
			
			Response response2 = Jsoup.connect(this.location)
					.header("Host", "patentscope.wipo.int")
					.header("Origin", "https://patentscope.wipo.int")
					.header("Referer", "https://patentscope.wipo.int/search/en/search.jsf")
					.data(
							"simpleSearchSearchForm", "simpleSearchSearchForm",
							"simpleSearchSearchForm:fpSearch", pubNr
							)
				    .cookie("JSESSIONID", response.cookie("JSESSIONID"))
				    //.cookie("_ga", response.cookie("_ga"))
				    .method(Method.POST)
				    .execute();
			
			System.out.println(response2.url());
			System.out.println(response2.header("location"));
			System.out.println(response2.cookie("JSESSIONID"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return applicantName;
	}
}
