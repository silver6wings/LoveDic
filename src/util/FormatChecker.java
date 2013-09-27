package util;

import java.io.FileReader;
import java.util.Hashtable;
import java.util.Scanner;

public class FormatChecker {

	public static Scanner cin;

	public enum ErrorStatus {
		AllCorrect, 
		FileNotFound, 
		TitleUnknown, 
		TitleDuplicate, 
		TitleNotEnd, 
		TitleMissing,
		NumberIllegal,
		NumberMaxMinError
	}

	public enum ReadStatus {
		NoReading, 
		ReadingTitle, 
		ReadingGirlRanges,

		ReadingScenarioRanges,

		ReadingIntimateMin,
		ReadingIntimateMax,
		ReadingTopicType, 
		ReadingTopicContent
	}

	public static ErrorStatus check(String filePath) {

		StringBuilder tsb = new StringBuilder("");

		// New Scanner
		try {
			cin = new Scanner(new FileReader(filePath));
		} catch (Exception e) {
			return ErrorStatus.FileNotFound;
		}

		// Collect Data
		while (cin.hasNextLine()) {
			String ts = cin.nextLine();
			for (int i = 0; i < ts.length(); i++) {
				char tc = ts.charAt(i);
				if (tc != ' ') {
					tsb.append(tc);
				}
			}
		}
		cin.close();

		// Data ready
		Hashtable<String, Boolean> dicTitle = new Hashtable<String, Boolean>();
		dicTitle.put("心仪对象", false);
		dicTitle.put("约会场景", false);
		dicTitle.put("亲密指数", false);
		dicTitle.put("话题类型", false);
		dicTitle.put("正文内容", false);

		Hashtable<String, ReadStatus> dicTitleStatus = new Hashtable<String, ReadStatus>();
		dicTitleStatus.put("心仪对象", ReadStatus.ReadingGirlRanges);
		dicTitleStatus.put("约会场景", ReadStatus.ReadingScenarioRanges);
		dicTitleStatus.put("亲密指数", ReadStatus.ReadingIntimateMin);
		dicTitleStatus.put("话题类型", ReadStatus.ReadingTopicType);
		dicTitleStatus.put("正文内容", ReadStatus.ReadingTopicContent);

		StringBuilder tTitle = new StringBuilder("");
		StringBuilder tTopicType = new StringBuilder("");
		StringBuilder tTopicContent = new StringBuilder("");
		StringBuilder tNumber = new StringBuilder("");

		// Start analyze
		ReadStatus rs = ReadStatus.NoReading;

		for (int i = 0; i < tsb.length(); i++) {
			char c = tsb.charAt(i);
			// System.out.println(c);

			if (c == '#') {

				if (rs == ReadStatus.ReadingTitle) {
					String strTitle = tTitle.toString();

					if (dicTitle.containsKey(strTitle.toString())) {
						if (!dicTitle.get(strTitle)) {
							dicTitle.put(strTitle.toString(), true);
							rs = dicTitleStatus.get(strTitle);
							System.out.println(strTitle);
						} else {
							System.out.println("TitleDuplicate" + strTitle);
							return ErrorStatus.TitleDuplicate;
						}
					} else {
						System.out.println("TitleUnknown:" + strTitle);
						return ErrorStatus.TitleUnknown;
					}
				} else {
					rs = ReadStatus.ReadingTitle;
					tTitle = new StringBuilder("");
				}

			} else if (c == '(') {

			} else if (c == ')') {

			} else if (c == '{') {

			} else if (c == '}') {

			} else if (c == ':') {

			} else if (c == '-') {
				
			} else {
				
				if (rs == ReadStatus.ReadingTitle) {
					tTitle.append(c);
				}
				
				if (rs == ReadStatus.ReadingIntimateMin){					
					tNumber.append(c);
				}				
				if (rs == ReadStatus.ReadingTopicType) {
					tTopicType.append(c);
				}
				
				if (rs == ReadStatus.ReadingTopicContent) {
					tTopicContent.append(c);
				}
				
			}
		}

		System.out.println(tsb);
		System.out.println(tTopicType);
		System.out.println(tTopicContent);
		
		// Check Title
		{
			if (rs == ReadStatus.ReadingTitle)
				return ErrorStatus.TitleNotEnd;
		}
		// Check All Titles
		{
			boolean hasAllTitles = true;
			for (String key : dicTitle.keySet())
				hasAllTitles = hasAllTitles && dicTitle.get(key);

			if (!hasAllTitles)
				return ErrorStatus.TitleMissing;
		}
		
		return ErrorStatus.AllCorrect;
	}
}
