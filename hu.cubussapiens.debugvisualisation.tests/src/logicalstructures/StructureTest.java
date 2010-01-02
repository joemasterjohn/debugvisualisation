package logicalstructures;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import debugproba.Valami;

public class StructureTest {
	List<String> strings = Arrays.asList(new String[]{"S1", "S2", "S3", "S4"});
	List<Valami> structures = Arrays.asList(new Valami[]{new Valami(), new Valami()});
	Hashtable<String, List<?>> table = new Hashtable<String, List<?>>();
	
	public StructureTest(){
		table.put("string", strings);
		table.put("structures", structures);
	}

	public static void main(String args[]){
		StructureTest test = new StructureTest();
		System.out.println(test.strings);
		System.out.println(test.structures);
	}
}
