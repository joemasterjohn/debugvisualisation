package stringescape;

/**
 * Test for avoiding unnecessary line breaks when a string parameter contains \n value
 *
 */
public class StringTrimmingTest {
	
	class Test{
	
	String value = "ABCD\nEFGHIJKLMNOPQRSTUVWXYZ";
	};
	
	Test testValue;
	
	public StringTrimmingTest(){
		super();
		testValue = new Test();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringTrimmingTest test = new StringTrimmingTest();
		test.testValue.value = "ABCD\nABCD\nEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(test.testValue.value);

	}

}
