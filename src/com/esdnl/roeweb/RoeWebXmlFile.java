package com.esdnl.roeweb;

import java.io.*;
import java.util.*;
import java.text.*;

import com.esdnl.util.*;

public class RoeWebXmlFile 
{
  private final String DATE_FORMAT = "ddMMyyyy";
  private final String DECIMAL_FORMAT = "0.00";
  private final String INTEGER_FORMAT = "0";
  
  private PrintWriter pw;
  private File xml_file;
  private SimpleDateFormat sdf = null;
  private DecimalFormat df = null;
  private DecimalFormat intf = null;
  private boolean draft;
  
  private boolean includeStatutoryHolidayPay = true;
  
  public RoeWebXmlFile(String path, boolean draft)
  {
    this.pw = null;
    this.xml_file = new File(path);
    this.sdf = new SimpleDateFormat(this.DATE_FORMAT);
    this.df = new DecimalFormat(this.DECIMAL_FORMAT);
    this.intf = new DecimalFormat(this.INTEGER_FORMAT);
    this.draft = draft;
  }
  
  //creates xml_file and writes xml roe header
  public void open() throws IOException
  {
    pw = new PrintWriter(new FileWriter(xml_file));
    
    pw.println("<ROEHEADER Application=\"RoeWeb\" FileVersion=\"1.00\">");
  }
  
  //closes xml_file and write ending tag
  public void close() throws IOException
  {
    pw.println("</ROEHEADER>");
    pw.close();
  }
  
  public void addRoe(IRoe roe) throws IOException
  {
    if(roe instanceof Roe)
      addRoe((Roe)roe);
    else if(roe instanceof TermReplacementRoe)
      addRoe((TermReplacementRoe)roe);
  }
  
  public void addRoe(Roe roe) throws IOException
  {
  	TreeMap hpay = null;
    //RoeFileLine[] lines = roe.getRoeFileLines();
    RoeFileLine line = null;
    
    if(pw == null)
      open();
    
    if(includeStatutoryHolidayPay)
    	hpay = getStatutoryHolidayPay(roe);
    
    pw.println("\t<Roe PrintingLanguage=\"E\" Issue=" + (draft ? "\"D\"" : "\"S\"") + ">");
    
    // CRA Buisness Number
    pw.println("\t\t<B5>856840871RP0003</B5>");
    
    //pay period type
    pw.println("\t\t<B6>B</B6>");
    
    //SIN of employee for whom the ROE will be issued
    pw.println("\t\t<B8>" + roe.getSIN() + "</B8>");
    
    //Employmee Information
    pw.println("\t\t<B9>");
    
      //First name
      pw.println("\t\t\t<FN>" + roe.getDemographicFileLine().getFirstName() + "</FN>");
      
      //First name
      pw.println("\t\t\t<LN>" + roe.getDemographicFileLine().getLastName() + "</LN>");
      
      //Address line 1
      pw.println("\t\t\t<A1>" + roe.getDemographicFileLine().getAddressLine1() + "</A1>");
      
      //Address line 2
      if(!StringUtils.isEmpty(roe.getDemographicFileLine().getAddressLine2()))
        pw.println("\t\t\t<A2>" + roe.getDemographicFileLine().getAddressLine2() + "</A2>");
      
      //Address line 3
      if(!StringUtils.isEmpty(roe.getDemographicFileLine().getAddressLine3()))
        pw.println("\t\t\t<A3>" + roe.getDemographicFileLine().getAddressLine3() + ", " + roe.getDemographicFileLine().getPostalCode() + "</A3>");
    
    pw.println("\t\t</B9>");
    
    //first day worked
    pw.println("\t\t<B10>" + sdf.format(roe.getFirstDayWorked()) + "</B10>");
    
    //last day worked
    pw.println("\t\t<B11>" + sdf.format(roe.getLastDayWorked(hpay)) + "</B11>");
    
    //Final pay period end date
    pw.println("\t\t<B12>" + sdf.format(roe.getFinalPayPeriodEndDate()) + "</B12>");
    
    //Employee Occupation
    pw.println("\t\t<B13>" + roe.getDemographicFileLine().getOccupation() + "</B13>");
    
    //total insureable hours
    pw.println("\t\t<B15A>" + intf.format(Math.ceil(roe.getTotalInsurableHours())) + "</B15A>");
    
    //total insureable earnings
    pw.println("\t\t<B15B>" + df.format(roe.getTotalInsurableEarnings()) + "</B15B>");
    
    //Insurable earning info
    pw.println("\t\t<B15C>");
      
    Date d = null;
    int pp_cnt = 0;
    boolean start = false;
    
    for(Iterator iter = roe.getRoeFileLinesIterator(); iter.hasNext();)
    {
      line = (RoeFileLine)(((Map.Entry) iter.next()).getValue());
      
      //remove ending zero earnings rows
      if(!start && (line.getTotalBiWeeklyInsurableEarning() <= 0))
        continue;
      else
        start = true;
      
      if(line.getPayPeriodDate().equals(d))
        continue;
      else
        d = line.getPayPeriodDate();
      
      //Pay Period
      pw.println("\t\t\t<PP nbr=\"" + (++pp_cnt) + "\">");
        //Pay Period Earnings
        pw.println("\t\t\t\t<AMT>" + df.format(line.getTotalBiWeeklyInsurableEarning()) + "</AMT>");
        
      pw.println("\t\t\t</PP>");
    }
      
    pw.println("\t\t</B15C>");
    
    //Contact Information
    pw.println("\t\t<B16>");
    
      //reason for issuing ROE
      pw.println("\t\t\t<CD>A</CD>");
      
      //first name contact person
      pw.println("\t\t\t<FN>Peggy</FN>");
      
      //last name contact person
      pw.println("\t\t\t<LN>Didham</LN>");
      
      //area code contact person
      pw.println("\t\t\t<AC>709</AC>");
      
      //phone number contact person
      pw.println("\t\t\t<TEL>7582355</TEL>");
      
    pw.println("\t\t</B16>");
    	
    if((hpay != null) && (hpay.size() > 0)){
    		//Statutory Holiday Pay
    		pw.println("\t\t<B17B>");
    		StatutoryPay pay = null;
    		int i=0;
    		for(Iterator iter = hpay.entrySet().iterator(); iter.hasNext();){
    			pay = (StatutoryPay)((Map.Entry)iter.next()).getValue();
    			pw.println("\t\t\t<SH nbr=\"" + (++i) + "\">");
    			pw.println("\t\t\t\t<DT>" + pay.getDate() + "</DT>");
    			pw.println("\t\t\t\t<AMT>" + df.format(pay.getAmount()) + "</AMT>");
    			pw.println("\t\t\t</SH>");
    		}
    		pw.println("\t\t</B17B>");
    }
    	
    pw.println("\t</Roe>");
  }
  
  public void addRoe(TermReplacementRoe roe) throws IOException
  {
    if(pw == null)
      open();
    
    pw.println("\t<Roe PrintingLanguage=\"E\" Issue=" + (draft ? "\"D\"" : "\"S\"") + ">");
    
    // CRA Buisness Number
    pw.println("\t\t<B5>856840871RP0003</B5>");
    
    //pay period type
    pw.println("\t\t<B6>W</B6>");
    
    //SIN of employee for whom the ROE will be issued
    pw.println("\t\t<B8>" + roe.getSIN() + "</B8>");
    
    //Employmee Information
    pw.println("\t\t<B9>");
    
      //First name
      pw.println("\t\t\t<FN>" + roe.getFirstName() + "</FN>");
      
      //First name
      pw.println("\t\t\t<LN>" + roe.getLastName() + "</LN>");
      
      //Address line 1
      pw.println("\t\t\t<A1>" + roe.getAddress1() + "</A1>");
      
      //Address line 2
      if(!StringUtils.isEmpty(roe.getAddress2()))
        pw.println("\t\t\t<A2>" + roe.getAddress2() + "</A2>");
      
      //Address line 3
      if(!StringUtils.isEmpty(roe.getCity()))
        pw.println("\t\t\t<A3>" + roe.getCity() + ", " + roe.getPostalCode() + "</A3>");
    
    pw.println("\t\t</B9>");
    
    //first day worked
    pw.println("\t\t<B10>02092005</B10>");
    
    //last day worked
    pw.println("\t\t<B11>23062006</B11>");
    
    //Final pay period end date
    pw.println("\t\t<B12>23062006</B12>");
    
    //Employee Occupation
    pw.println("\t\t<B13>Teacher</B13>");
    
    //total insureable hours
    pw.println("\t\t<B15A>" + intf.format(Math.ceil(Double.parseDouble(roe.getHours()))) + "</B15A>");
    
    //total insureable earnings
    pw.println("\t\t<B15B>" + df.format(Double.parseDouble(roe.getSalary())) + "</B15B>");
    
    //Insurable earning info
    pw.println("\t\t<B15C>");
      
    for(int i=1; i <= 43; i++)
    {
      //Pay Period
      pw.println("\t\t\t<PP nbr=\"" + i + "\">");
        //Pay Period Earnings
        if(i < 43)
          pw.println("\t\t\t\t<AMT>" + df.format(Double.parseDouble(roe.getBlock42())) + "</AMT>");
        else
          pw.println("\t\t\t\t<AMT>" + df.format(Double.parseDouble(roe.getBlock43())) + "</AMT>");
        
      pw.println("\t\t\t</PP>");
    }
      
    pw.println("\t\t</B15C>");
    
    //Contact Information
    pw.println("\t\t<B16>");
    
      //reason for issuing ROE
      pw.println("\t\t\t<CD>A</CD>");
      
      //first name contact person
      pw.println("\t\t\t<FN>Sheila</FN>");
      
      //last name contact person
      pw.println("\t\t\t<LN>Evely</LN>");
      
      //area code contact person
      pw.println("\t\t\t<AC>709</AC>");
      
      //phone number contact person
      pw.println("\t\t\t<TEL>7574622</TEL>");
      
    pw.println("\t\t</B16>");
    
    pw.println("\t</Roe>");
  }
  
  
  
  private TreeMap getStatutoryHolidayPay(Roe roe){
  	RoeFileLine line = null;
  	Date[] holidays = new Date[1];
  	StatutoryPay pay = null;
  	
  	Calendar cal = Calendar.getInstance();
  	cal.clear();
  	
  	//Christmas Eve
  	//cal.set(2009, Calendar.DECEMBER, 24, 0, 0, 0);
  	//holidays[0] = cal.getTime();
  	//Christmas Day
  	//cal.set(2008, Calendar.DECEMBER, 25, 0, 0, 0);
  	//holidays[1] = cal.getTime();
  	//Boxing Day
  	//cal.set(2008, Calendar.DECEMBER, 26, 0, 0, 0);
  	//holidays[2] = cal.getTime();
  	
  	cal.set(2009, Calendar.APRIL, 10, 0, 0, 0);
  	holidays[0] = cal.getTime();
  	
  	TreeMap map = new TreeMap();
  	
  	for(Iterator iter = roe.getRoeFileLinesIterator(); iter.hasNext();){
      line = (RoeFileLine)(((Map.Entry) iter.next()).getValue());
      for(int h=0; h < holidays.length; h++){
      	pay = line.getStatutoryHolidayPay(holidays[h]);
      	if(pay != null)
      		map.put(holidays[h], pay);
      }
    }
  	return map;
  }
}