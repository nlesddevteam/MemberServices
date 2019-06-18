package com.esdnl.roeweb;

import java.io.*;
import java.util.*;
import java.text.*;

public class RoeWebTest
{
  public static void main(String[] args)
  {
    final int MAX_RECORDS = 911;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
    Date st = null;
    Date ft = null;
    String demo_line = null;
    String s_demo_line = null;
    String roe_line = null;
    BufferedReader br_demo = null;
    BufferedReader br_roe = null;
    IRoe roe = null;
    RoeFileLine rfl = null;
    
    int roe_cnt = 0;
    int file_cnt = 0;
    int classCodeExceptionCount = 0;
    
    //first pay period in the year, all roe file checked against this date.
    Calendar cal_first = Calendar.getInstance();
    cal_first.clear();
    cal_first.set(2009, Calendar.JANUARY, 9);
    Date first_pay_period_processed = cal_first.getTime();
    
    //last pay period in the year, all roe file checked against this date
    Calendar cal_last = Calendar.getInstance();
    cal_last.clear();
    cal_last.set(2009, Calendar.APRIL, 17);
    Date last_pay_period_processed = cal_last.getTime();
    
    boolean submit_draft = true;
    
    boolean term_replacement = false;
    
    RoeWebXmlFile xml = null;
    
    //based directory
    String file_path = "C:/Users/Chris.ESDHQ/Desktop/roedata/";
    
    //files to be processed
    String demo_filename = "Y2009.ADDD400.MAY01.TXT";
    String roe_filename = "Y2009.ROED400.MAY01.TXT";
    
    //output file name
    String base_filename = "JC_0508" ;
    
    //add draft/production to output file name;
    base_filename += "_" + (submit_draft?"DRAFT":"PRODUCTION");
    
    //add timestamp to output file name
    base_filename += "_" + (new SimpleDateFormat("dd_MM_yyyy")).format(Calendar.getInstance().getTime()) + "_";
    
    //used to look for a specific set of sins. reads file containing list of sins
    String sins_filename = "sins.txt";
    File sins_file = null;
    BufferedReader br_sins = null;
    
    //used to look for a specific set of sins based on class code
    HashMap class_codes = new HashMap();
    //class_codes.put("02", null);
    //class_codes.put("03", null);
    //class_codes.put("04", null);
    //class_codes.put("05", null);
    //class_codes.put("06", null);
    class_codes.put("07", null);
    //class_codes.put("08", null);
    
    
    //include null class code recorded in the acceptable sins list.
    boolean includeNullClassCode = false;
    
    //include sin when records contain accepted and not accepted class codes
    boolean includeClassCodeExceptions = false;
    
    //process only sins with null class codes.
    boolean nullClassCodesOnly = false;
    
    //list of sins we know we want to process
    HashMap sins = new HashMap();
    
    //set of sin exceptions that we know we DO NOT want to process. reads file containing list of sins
    String exceptions_filename = null;
    File exceptions_file = null;
    BufferedReader br_exceptions = null;
    HashMap exceptions = new HashMap();
    
    //used to track demo info for already processed roes
    String submitted_filename = "PROCESSED.dat";
    File submitted_file = null;
    BufferedReader br_submitted = null;
    PrintWriter pw_submitted = null;
    HashMap submitted_roes = new HashMap();
    
    try
    {
      //initialize exceptions hashmap for sins we DO NOT want to process
      //exceptions_file should contain just a list of sins
      if(exceptions_filename != null)
      {
        exceptions_file = new File(file_path + exceptions_filename);
        if((exceptions_file != null) && exceptions_file.exists())
        {
          br_exceptions = new BufferedReader(new FileReader(exceptions_file));
          while((s_demo_line = br_exceptions.readLine()) != null)
          {
            s_demo_line = s_demo_line.trim();
            
            if(!exceptions.containsKey(s_demo_line))
              exceptions.put(s_demo_line, null);
          }
          br_exceptions.close();
        }
      }
      
      //initialize sins hashmap for sins we want to process
      //sins_file should contain just a list of sins
      if(sins_filename != null)
      {
        sins_file = new File(file_path + sins_filename);
        if((sins_file != null) && sins_file.exists())
        {
          br_sins = new BufferedReader(new FileReader(sins_file));
          while((s_demo_line = br_sins.readLine()) != null)
          {
            s_demo_line = s_demo_line.trim();
            
            if(!sins.containsKey(s_demo_line))
              sins.put(s_demo_line, null);
          }
          br_sins.close();
        }
      }
      
      //initialize sins for class codes we want to pick up
      if((class_codes != null)&&(class_codes.size() > 0))
      {
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
            
          if((rfl.getJobCode() != null) && class_codes.containsKey(rfl.getJobCode()) && !sins.containsKey(rfl.getSIN()))
            sins.put(rfl.getSIN(), null);
          else if(includeNullClassCode && (rfl.getJobCode() == null) && !sins.containsKey(rfl.getSIN()))
            sins.put(rfl.getSIN(), null);
        }
          
        br_roe.close();
      }
      
      
      
      System.out.println("Number of Specific SINs to be processed: " + sins.size());
      
      //initialize sins that already have a roe submitted, submitted file should contain demographic data.
      if(submitted_filename != null)
      {
        DemographicFileLine dfl = null;
        
        submitted_file = new File(file_path + submitted_filename);
        if((submitted_file != null) && submitted_file.exists())
        {
          br_submitted = new BufferedReader(new FileReader(submitted_file));
          while((s_demo_line = br_submitted.readLine()) != null)
          {
            if(s_demo_line.length() < DemographicFileLine.MIN_LINE_LENGTH)
              continue;
              
            dfl = new DemographicFileLine(s_demo_line);
            if(!submitted_roes.containsKey(dfl.getSIN()))
              submitted_roes.put(dfl.getSIN(), null);
          }
          br_submitted.close();
        }
      }
      
      System.out.println("Number of Roes Previously processed: " + submitted_roes.size());
      
      System.out.println("Starting........");
      st = Calendar.getInstance().getTime();
      
      xml = new RoeWebXmlFile(file_path + base_filename + (++file_cnt) + ".BLK", submit_draft);
      xml.open();
      
      br_demo = new BufferedReader(
                                new FileReader(
                                  new File(file_path + demo_filename)
                                )
                             );
      
      //do we want to track processed roes?
      if(submitted_file != null)
      {
        pw_submitted = new PrintWriter(new FileWriter(submitted_file, true));
        pw_submitted.println(sdf.format(Calendar.getInstance().getTime()));
      }
                             
      while((demo_line = br_demo.readLine()) != null)
      {
        if(demo_line.length() < DemographicFileLine.MIN_LINE_LENGTH)
          continue;
          
        if(!term_replacement)
        {
          roe = new Roe();
          ((Roe)roe).setDemographicFileLine(new DemographicFileLine(demo_line));
        }
        else
          roe = new TermReplacementRoe(demo_line);
          
        //check if it is one of our exceptions
        if(exceptions.containsKey(roe.getSIN()))
          continue;
          
        //check is roe is already processed
        if(submitted_roes.containsKey(roe.getSIN()))
          continue;
        
        //looks for a specific set of sins
        if((sins.size() > 0) && !sins.containsKey(roe.getSIN()))
          continue;
        
        
        //**********************************************
        //*** WE HAVE AN ROE TO PROCESS AT THIS POINT **
        //**********************************************
        
        
        //max records per file
        if(((++roe_cnt) % MAX_RECORDS) == 0)
        {
          xml.close();
          
          xml = new RoeWebXmlFile(file_path + base_filename + (++file_cnt) + ".BLK", submit_draft);
          xml.open();
        }
        
        System.out.print("Processing ROE Data for " + roe.getSIN() + "... ");
        
        if(!term_replacement)
        {
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
            
            if((first_pay_period_processed != null)&& (rfl.getPayPeriodEndingDate().compareTo(first_pay_period_processed) < 0))
              continue;
            //do not include records after the last pay period being processed
            if((last_pay_period_processed != null) && (rfl.getPayPeriodEndingDate().compareTo(last_pay_period_processed) > 0))
              continue;
              
            if(rfl.getSIN().equals(roe.getSIN()))
            {
              if(nullClassCodesOnly && (rfl.getJobCode() != null) && !rfl.getJobCode().trim().equals(""))
              {
                ((Roe)roe).clearRoeFileLines();
                break;
              }
              else if(!includeClassCodeExceptions && (class_codes != null)
                && (class_codes.size() > 0) && (rfl.getJobCode() != null)  
                && !class_codes.containsKey(rfl.getJobCode()))
              {
                ((Roe)roe).clearRoeFileLines();
                classCodeExceptionCount++;
                break;
              }
              //else if(!includeNullClassCode && rfl.getJobCode() == null)
              //	continue;
            
              ((Roe)roe).addRoeFileLine(rfl);
            }
          }
          
          br_roe.close();
        }
        
        if(roe.isValid())
        {
          xml.addRoe(roe);
          
          //write to the processed file so not re-processed in future runs.
          if(pw_submitted != null)
            pw_submitted.println(demo_line);
        }
        else
        {
          System.out.print("ZERO EARNINGS, NO ROE GENERATED.....");
          roe_cnt--;
        }
        
        System.out.println("DONE");
      }
      
      xml.close();
      
      br_demo.close();
      
      if(pw_submitted != null)
        pw_submitted.close();
        
      ft = Calendar.getInstance().getTime();
      System.out.println("Finished......\n");
      
      System.out.println("Start Time: " + st );
      System.out.println("Finish Time: " + ft );
      
      System.out.println("# ROEs Processed: " + roe_cnt);
      if(!includeClassCodeExceptions)
        System.out.println("# Class Code Exception Count: " + classCodeExceptionCount);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}