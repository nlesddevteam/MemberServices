package com.esdnl.roeweb;

import java.io.*;
import java.util.*;
import java.text.*;

public class FormatROEWebDownload 
{

  public static void main(String[] args) throws Exception
  {
    
    HashMap exceptions = new HashMap();
    
    HashMap lines = new HashMap();
    String line = null;
    String data[] = null;

    int exception_cnt = 0;
    
    BufferedReader br = null;
    ROEWebDownloadLine rline = null;
    
    File dir = new File("C:\\Documents and Settings\\Chris.ESDHQ\\Desktop\\roedata\\unformated");
    File csv[] = dir.listFiles(new FilenameFilter(){
      public boolean accept(File dir, String name)
      {
        return name.endsWith(".csv");
      }
    });
    
    
    for(int i=0; i < csv.length; i++)
    {
      System.out.println("Processing " + csv[i].getPath());
      
      
      br = new BufferedReader(new FileReader(csv[i]));
      
      line = br.readLine();  //first line is headers
      while((line = br.readLine()) != null)
      {
        System.out.println(line);
        data = line.split(",");
  
        try
        {
          rline = new ROEWebDownloadLine(data);
          if(!lines.containsKey(rline.getSin()))
          {
            lines.put(rline.getSin(), rline);
          }
          else
          {
            if(rline.getDateIssued().after(((ROEWebDownloadLine)lines.get(rline.getSin())).getDateIssued()))
              lines.put(rline.getSin(), rline);
          }
          
          //pw.println(data[1].trim() + "," + reformatDate(data[2]) + "," + reformatDate(data[0]));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          System.out.println(">>>>>>>> PREVIOUS LINE NOT PROCESSED <<<<<<<<<<<");
        }
        //pw.flush();
      }
      
      br.close();
    }
    
    PrintWriter pw = new PrintWriter( new FileWriter("C:\\Documents and Settings\\Chris.ESDHQ\\Desktop\\roedata\\ROE_JUNE_2007.csv"));
    System.out.println("Writing to file: " + lines.size());
    pw.println("SIN,LastDayForWhichPaid,DateIssued");
    Iterator iter = lines.entrySet().iterator();
    while(iter.hasNext())
    {
      pw.println((ROEWebDownloadLine)(((Map.Entry)iter.next()).getValue()));
      pw.flush();
    }
    pw.close();
  }
}