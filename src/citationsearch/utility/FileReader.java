package citationsearch.utility;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
//import java.util.HashMap;

//import citationsearch.entity.Company;

public class FileReader extends Reader {

	public FileReader(String location) {
		super(location);
	}

	public String[] getAllLines() {
		File file = new File(this.location);
		String line = "";
		//HashMap<String, Company> map = new HashMap<>();
		ArrayList<String> lines = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new java.io.FileReader(file));
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines.toArray(new String[0]);
	}
}
