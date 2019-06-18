package com.esdnl.roeweb;

import java.io.*;
import java.util.*;
import java.text.*;

public class EarningsTest
{
  public static void main(String[] args)
  {
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    Date st = null;
    Date ft = null;
    String demo_line = null;
    String roe_line = null;
    BufferedReader br_demo = null;
    BufferedReader br_roe = null;
    RoeFileLine rfl = null;
    
   
    
    //based directory
    String file_path = "C:/Users/Chris.ESDHQ/Desktop/roedata/";
    
    //files to be processed
    String demo_filename = "ADDD400.TXT";
    String roe_filename = "ROED400.TXT";
    
    //output file name
    String base_filename = "EARNINGS" ;
      
    //add timestamp to output file name
    base_filename += "_" + (new SimpleDateFormat("dd_MM_yyyy")).format(Calendar.getInstance().getTime()) + "_";
    

    PrintWriter pw = null;
    try
    {
      
      
      System.out.println("Starting........");
      st = Calendar.getInstance().getTime();
      
      
      pw = new PrintWriter((file_path + base_filename + ".TXT"));
      
      br_demo = new BufferedReader(
                                new FileReader(
                                  new File(file_path + demo_filename)
                                )
                             );
      
      
      DemographicFileLine demo = null;
      Calendar cal = Calendar.getInstance();
      
      while((demo_line = br_demo.readLine()) != null)
      {
        if(demo_line.length() < DemographicFileLine.MIN_LINE_LENGTH)
          continue;
        
        //**********************************************
        //*** WE HAVE AN ROE TO PROCESS AT THIS POINT **
        //**********************************************
        
        demo = new DemographicFileLine(demo_line);
        
        pw.println(demo);
        
        System.out.print("Processing ROE Data for " + demo.getSIN() + "... ");
        
        
          br_roe = new BufferedReader(
                                  new FileReader(
                                    new File(file_path + roe_filename)
                                  )
                               );
                               
          while((roe_line = br_roe.readLine()) != null)
          {
            if(roe_line.length() < RoeFileLine.MIN_LINE_LENGTH)
              continue;
              
            rfl = new RoeFileLine(roe_line);
              
            if(rfl.getSIN().equals(demo.getSIN()))
            {
            	pw.print(rfl.getSIN());
            	
            	cal.setTime(rfl.getWeekEndingDate());
            	cal.add(Calendar.DATE, -5);
              pw.print("\t" + sdf2.format(cal.getTime()));
              
              cal.setTime(rfl.getWeekEndingDate());
            	cal.add(Calendar.DATE, 1);
              pw.print("\t" + sdf2.format(cal.getTime()));
              
              pw.println("\t" + rfl.getWeeklyGross());
            }
          }
          
          br_roe.close();
       
        
        pw.println("*********");
        pw.println();
        pw.println();
        System.out.println("DONE");
    	}  
      
      br_demo.close();
      pw.close();
      
      
        
      ft = Calendar.getInstance().getTime();
      System.out.println("Finished......\n");
      
      System.out.println("Start Time: " + st );
      System.out.println("Finish Time: " + ft );
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}