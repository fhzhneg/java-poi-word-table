package cn.edu.cuit.table;

import java.util.Arrays;
import java.util.List;

public class ListShowTwoElement {

	public static void main(String[] args) {

		List<String> strings = Arrays.asList("item 1", "item 2", "item 3", "item 4", "item 5");
		for(int i = 0; i < strings.size(); i += 2) {
//		for(int i = 0; i < strings.size(); i++){
		    String first = strings.get(i);
		    String second = null;
		    if(strings.size() > i + 1){
		        second = strings.get(i + 1);
		    }
		    System.out.println("First [" + first + "] - Second [" + second + "]");
		}
	}

}
