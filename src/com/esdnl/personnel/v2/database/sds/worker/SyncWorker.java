package com.esdnl.personnel.v2.database.sds.worker;

import com.awsd.personnel.*;
import com.awsd.mail.bean.*;
import java.util.*;
import java.text.*;
import java.io.*;

import com.esdnl.personnel.v2.database.sds.SyncManager;

public class SyncWorker extends Thread{
	
	public void run(){
		
		try{
				EmailBean email = new EmailBean();
				email.setTo(PersonnelDB.getPersonnelByPermission("PERSONNEL-SUBSTITUTES-RELOAD-TABLES"));
				email.setSubject("HR Sync Manager Notification");
				email.setBody("Sync started at " + new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
				email.send();
				
				SyncManager.loadTables();
				
				email = new EmailBean();
				email.setTo(PersonnelDB.getPersonnelByPermission("PERSONNEL-SUBSTITUTES-RELOAD-TABLES"));
				email.setSubject("HR Sync Manager Notification");
				email.setBody("Sync finished at " + new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
				email.send();
		}
		catch(Exception e){
			try{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				
				EmailBean email = new EmailBean();
				email.setTo("chriscrane@esdnl.ca");
				email.setSubject("HR Sync Manager ERROR Notification");
				email.setBody(sw.toString());
				email.send();
				
			}catch(Exception ex){}
		}
	}
	              
}
