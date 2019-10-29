import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringCalcTest {
	    
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
    
    @Test (expected = Test.None.class)
    public void addManyNumbers() throws Exception {
    	List<Integer> list = 
		assertEquals(0, StringCalc.add("xxx,yyy,zzz"));
    }
}
