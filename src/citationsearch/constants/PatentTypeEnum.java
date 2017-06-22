package citationsearch.constants;

/*
Types of patent right (t)
1 - patent for invention
1 - patent for invention
2 - patent for utility model
3 - patent for design
8* - PCT application (patent) entering national phase in China
9* - PCT application (utility model) entering national phase in China
(* types 8 and 9 are only used in application numbers)
 */

public final class PatentTypeEnum {
	public static final String KEY1 = "1";
	public static final String KEY2 = "2";
	public static final String KEY3 = "3";
	public static final String KEY4 = "8";
	public static final String KEY5 = "9";
	
	public static final String VALUE1 = "patent for invention";
	public static final String VALUE2 = "patent for utility model";
	public static final String VALUE3 = "patent for design";
	public static final String VALUE4 = "PCT application (patent) entering national phase in China";
	public static final String VALUE5 = "PCT application (utility model) entering national phase in China";
	
	public static final String DEFAULT_VALUE = "Undefined type";
	
	public static String getValue(String key) {
		switch (key) {
			case KEY1:
				return VALUE1;
			case KEY2:
				return VALUE2;
			case KEY3:
				return VALUE3;
			case KEY4:
				return VALUE4;
			case KEY5:
				return VALUE5;
			default:
				return DEFAULT_VALUE;
		}
	}
}
