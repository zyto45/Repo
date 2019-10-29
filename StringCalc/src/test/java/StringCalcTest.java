import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.BeforeClass;
import org.junit.Test;

public class StringCalcTest {
	
	private static List<String> longParm;
	private static int sumResult = 0;
	
	@BeforeClass
    public static void onceExecutedBeforeAll() {
        longParm = new ArrayList<String>();
        Random r = new Random();
        
        for (int i=0; i < 10 + r.nextInt(100); i++) {
        	int num = r.nextInt(100);
        	sumResult += num;
        	longParm.add(Integer.toString(num));
        	sumResult = Math.min(sumResult, 1000);
        }
        
    }
	
	@Test (expected = Test.None.class)
    public void addAllGood() throws Exception {
		assertEquals(8, StringCalc.add("1,3,4"));
    }
    
    @Test (expected = Test.None.class)
    public void addOneParmNaN() throws Exception {
		assertEquals(5, StringCalc.add("1,xxx,4"));
    }
    
    @Test (expected = Test.None.class)
    public void addAllNaN() throws Exception {
		assertEquals(0, StringCalc.add("xxx,yyy,zzz"));
    }
    
    @Test (expected = Exception.class)
    public void addNegativeParm() throws Exception {
		StringCalc.add("1,4,-2,-44,88");
    }
    
    @Test (expected = Exception.class)
    public void addDelimAndNewLineFirst() throws Exception {
		StringCalc.add("1,4,\r\n6");
    }
    
    @Test (expected = Exception.class)
    public void addDelimAndNewLineLast() throws Exception {
		StringCalc.add("1,4\r\n,6");
    }
    
    @Test (expected = Test.None.class)
    public void addManyNumbersBigResult() throws Exception {
    	String input = String.join(",", longParm);
    	input += ",1000";
		assertEquals(1000, StringCalc.add(input));
    }
    
    @Test (expected = Test.None.class)
    public void addManyNumbers() throws Exception {
    	String input = String.join(",", longParm);
		assertEquals(sumResult, StringCalc.add(input));
    }
    
    @Test (expected = Test.None.class)
    public void addManyNumbersCustomDelim() throws Exception {
    	String input = String.join(";", longParm);
    	input = "//;\r\n" + input;
		assertEquals(sumResult, StringCalc.add(input));
    }
    
    @Test (expected = Test.None.class)
    public void addManyNumbersCustomLongDelim() throws Exception {
    	String input = String.join("*****", longParm);
    	input = "//*****\r\n" + input;
		assertEquals(sumResult, StringCalc.add(input));
    }
    
    @Test (expected = Test.None.class)
    public void addManyNumbersManyCustomLongDelim() throws Exception {
    	Random r = new Random();
    	AtomicReference<String> inputA = new AtomicReference<>();
    	inputA.set("");
    	String[] delimiters = {"\r\n", "xxx", "delim", ":"};
    	longParm.forEach(el -> {
    		inputA.set(inputA.get() + el + delimiters[r.nextInt(delimiters.length)]);
    	});
    	
    	String input = "//[xxx][delim][:]\r\n" + inputA.get() + "5";
		assertEquals(Math.min(sumResult+5, 1000), StringCalc.add(input));
    }
}
