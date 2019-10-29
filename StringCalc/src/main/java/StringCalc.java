import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringCalc {
	
	private static final String defDelim = ",";
	private static final String delimToken = "//";
	private static final String negException = "Negatives not allowed: ";
	private static final String delimException = "2 tokens next to each other.";
	private static final Logger logger = LogManager.getLogger(StringCalc.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println(add("//[,][****]\r\n10,2,3\r\n6****5"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int add(String summands) throws Exception {
		
		int result = 0;
		List<Integer> numbers = parseNumbers(summands);
		
		List<Integer> negNumbers = numbers.stream()
			.filter(num -> num < 0)
			.collect(Collectors.toList());
		
		if (negNumbers.size() > 0) {
			logger.error("Got negative numbers!");
			throw new Exception(negException + negNumbers);
		}
		
		result = numbers.stream()
                .mapToInt(i -> i.intValue()).sum();
		
		return result > 1000 ? 1000 : result;
	}
	
	private static String parseDelim(String s) {
		List<String> delimiters = new ArrayList<String>();
		
		String[] tokens = s.split("\\]");
		for (String t : tokens) {
			//ignore not correctly opened elements, make sure to escape entire expression
			if (t.startsWith("[")) {
				delimiters.add("\\Q" + t.substring(1) + "\\E");
			} else {
				delimiters.add("\\Q" + t + "\\E");
			}
		}
		
		logger.debug("Parsed delimiters: " + String.join("|", delimiters));
		
		return String.join("|", delimiters);
	}
	
	private static ArrayList<Integer> parseNumbers(String input) throws Exception {
		String[] lines = null;
		String delim = defDelim;
		lines = input.split(System.lineSeparator());
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		for (String line : lines) {
			if (line.startsWith(delimToken)) {
				delim = parseDelim(lines[0].substring(delimToken.length()));
			} else {
				//If starts or ends with a token -> 2 tokens next to each other, not correct
				if (line.matches("^("+delim+").*") || line.matches(".*("+delim+")$")) {
					logger.error("2 tokens next to each other.");
					throw new Exception(delimException);
				}
				for (String token : line.split(delim)) {
					try {
						numbers.add(Integer.parseInt(token));
					} catch (NumberFormatException e) {
						logger.debug("NaN ignored");
					}
				}
			}
		}
		
		return numbers;
	}

}
