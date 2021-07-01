package name.trifon.beeserver.service.util;

import java.math.BigDecimal;

public class ParseUtil {

	//@Trifon
	public static BigDecimal parseBigDecimalColumn(String[] columns, int indx) {
		BigDecimal result = null;
		if (columns.length > indx && columns[indx] != null && !columns[indx].isEmpty()) {
			String contents = columns[indx];
			contents = contents.replaceAll(" ", "");
			contents = contents.replaceAll("\t", "");
			if (!contents.isEmpty()) {
				result = new BigDecimal(contents);
			}
		}
		return result;
	}

	//@Trifon
	public static BigDecimal parseBigDecimalColumn(String str) {
		BigDecimal result = null;
		if (str != null && !str.isEmpty()) {
			String contents = str.replaceAll(" ", "");
			contents = contents.replaceAll("\t", "");
			if (!contents.isEmpty()) {
				result = new BigDecimal(contents);
			}
		}
		return result;
	}
}
