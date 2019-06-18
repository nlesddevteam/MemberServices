package com.awsd.mail.util;

import com.awsd.personnel.*;
import java.util.*;

public class EmailUtils {
	
	public static String[] extractEmailList(Personnel[] p){
		ArrayList emails = new ArrayList();
		
		if(p != null){
			for(int i=0; i < p.length; i++)
				emails.add(p[i].getEmailAddress());
		}
		
		return (String[])emails.toArray(new String[0]);
	}

}
