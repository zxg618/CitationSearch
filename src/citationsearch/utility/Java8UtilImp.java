package citationsearch.utility;

import com.sun.org.apache.xerces.internal.xs.StringList;

public class Java8UtilImp {


	public static String stringJoin(String delim, String[] stringList){
		String result = "";

		if(stringList == null || stringList.length == 0){
			return "";
		}


		boolean isFirst = true;
		for(String s : stringList){
			if(isFirst){
				isFirst = false;
			} else {
				result += delim;
			}
			result += s;
		}

		return result;
	}

}
