package com.esdnl.util;

public class ArrayUtils {
	
	public static boolean contains(String[] arr, String val){
		boolean check = false;
		
		for(int i=0; i < arr.length; i++){
			if(arr[i].equals(val)){
				check = true;
				break;
			}
		}
		
		return check;
	}

}
