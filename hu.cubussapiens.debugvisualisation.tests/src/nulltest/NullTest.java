package nulltest;

import java.util.Map;

public class NullTest {

	Map<String, String> nullMap = null;
	
	public static void main(String[] args) {
		NullTest test = new NullTest();
		System.out.println(test.nullMap);
	}
}
