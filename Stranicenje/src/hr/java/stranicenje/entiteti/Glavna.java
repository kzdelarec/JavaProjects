package hr.java.stranicenje.entiteti;

import java.util.ArrayList;
import java.util.List;

public class Glavna {

	public static void main(String[] args) {
		List<Integer> test = new ArrayList<>();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		System.out.println(test);
		
		test.remove(2);
		System.out.println(test);
		test.add(3);
		System.out.println(test);
	}

}
