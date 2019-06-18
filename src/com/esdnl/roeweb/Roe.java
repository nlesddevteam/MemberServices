package com.esdnl.roeweb;

import java.util.*;

public class Roe implements IRoe
{
  private String sin;
  private DemographicFileLine demo_file_line;
  private TreeMap roe_file_lines;
  
  
  public Roe()
  {
    this.sin = null;
    
    this.demo_file_line = null;
    this.roe_file_lines = new TreeMap(new RoeFileLineComparator());
  }
  
  public String getSIN()
  {
    return this.sin;
  }
  
  public void setDemographicFileLine(DemographicFileLine demo_file_line)
  {
    this.demo_file_line = demo_file_line;
    this.sin = demo_file_line.getSIN();
  }
  
  public DemographicFileLine getDemographicFileLine()
  {
    return this.demo_file_line;
  }
  
  public void addRoeFileLine(RoeFileLine roe_file_line)
  {
    if((roe_file_line != null)&&(roe_file_line.getWeekEndingDate() != null))
      this.roe_file_lines.put(roe_file_line.getWeekEndingDate(), roe_file_line);
  }
  
  public void clearRoeFileLines()
  {
    this.roe_file_lines.clear();
  }
  
  public RoeFileLine[] getRoeFileLines()
  {
    return ((RoeFileLine[]) this.roe_file_lines.values().toArray(new RoeFileLine[0]));
  }
  
  public Iterator getRoeFileLinesIterator()
  {
    return this.roe_file_lines.entrySet().iterator();
  }
  
  public Date getFirstDayWorked()
  {
    
    Date firstDayWorked = null;
    Calendar cal = Calendar.getInstance();
    RoeFileLine rfl = null;
    
    Iterator iter = this.getRoeFileLinesIterator();
    while(iter.hasNext())
    {
      rfl =(RoeFileLine)(((Map.Entry) iter.next()).getValue());
    
      if((rfl != null) && (rfl.getWeekEndingDate() != null) && rfl.workedDuringWeek())
      {
        cal.setTime(rfl.getWeekEndingDate());
        
        if(rfl.getMondayHours() > 0)
          cal.add(Calendar.DATE, -4);
        else if(rfl.getTuesdayHours() > 0)
          cal.add(Calendar.DATE, -3);
        else if(rfl.getWednesdayHours() > 0)
          cal.add(Calendar.DATE, -2);
        else if(rfl.getThursdayHours() > 0)
          cal.add(Calendar.DATE, -1);
        
        firstDayWorked = cal.getTime();       
      }
    }
    
    return firstDayWorked;
    
    //return this.demo_file_line.getFirstDayWorked();
  }
  
  public Date getLastDayWorked(TreeMap statutoryPay)
  {
  	Date ldw = null;
  	Date wbd = null;
  	RoeFileLine line = null;
  	
  	if(this.demo_file_line.getLastDayWorked().after(this.getFinalPayPeriodEndDate())){
  		
  		Calendar cal = Calendar.getInstance();
  		cal.clear();
  		
  		line = (RoeFileLine) this.roe_file_lines.get(this.getFinalPayPeriodEndDate());
  		
  		outside:
  		while(line != null){
  			wbd = line.getWeekBeginningDate();
    		cal.setTime(line.getWeekEndingDate());
	  		while(wbd.compareTo(cal.getTime()) <= 0){
	  			if((line.getDailyHours(cal.get(Calendar.DAY_OF_WEEK)) > 0) && !statutoryPay.containsKey(cal.getTime())){
	  				ldw = cal.getTime();
	  				break outside;
	  			}
	  			
	  			cal.add(Calendar.DATE, -1);
	  		}
	  		
	  		cal.setTime(line.getWeekEndingDate());
	  		cal.add(Calendar.DATE, -7);
	  		
	  		line = (RoeFileLine) this.roe_file_lines.get(cal.getTime());
  		}
  	}
  	else
  		ldw = this.demo_file_line.getLastDayWorked();
  	
    return ldw;
  }
  
  public Date getFinalPayPeriodEndDate()
  {
    RoeFileLine line = null;
    Date d = null;
    
    Iterator iter = this.getRoeFileLinesIterator();
    while(iter.hasNext())
    {
      line = ((RoeFileLine)((Map.Entry)iter.next()).getValue());
      if(line.getTotalBiWeeklyInsurableEarning() > 0)
      {
        d = line.getPayPeriodEndingDate();
        break;
      }
    }
    
    return d;
  }
  
  public double getTotalInsurableEarnings()
  {
    RoeFileLine line = null;
    Date d = null;
    int cnt = 0;
    double total = 0;
    boolean start = false;
    
    for(Iterator iter = this.getRoeFileLinesIterator(); (iter.hasNext() && (cnt < 14));)
    {
      line = ((RoeFileLine)((Map.Entry)iter.next()).getValue());
      
      //remove ending zero earnings rows
      if(!start && (line.getTotalBiWeeklyInsurableEarning() <= 0))
        continue;
      else
        start = true;
      
      if(line.getPayPeriodDate().equals(d))
        continue;
      else
        d = line.getPayPeriodDate();
      
      total += line.getTotalBiWeeklyInsurableEarning();
      cnt++;
    }
    
    return total;
  }
  
  public double getTotalInsurableHours()
  {
    RoeFileLine line = null;
    Date d = null;
    double total = 0;
    
    for(Iterator iter = this.getRoeFileLinesIterator(); iter.hasNext();)
    {
      line = ((RoeFileLine)((Map.Entry)iter.next()).getValue());
      
      if(line.getPayPeriodDate().equals(d))
        continue;
      else
        d = line.getPayPeriodDate();
      
      total += line.getTotalBiWeeklyInsurableHours();
    }
    
    return total;
  }
  
  public boolean isValid()
  {
    return hasInsurableEarnings() && hasInsurableHours();
  }
  
  public boolean hasInsurableEarnings()
  {
  	RoeFileLine [] lines = this.getRoeFileLines();
    boolean valid = false;
    
    for(int i=0; i < lines.length; i++)
    {
      if(lines[i].getTotalBiWeeklyInsurableEarning() > 0)
      {
        valid = true;
        break;
      }
    }
    
    return valid;
  }
  
  public boolean hasInsurableHours()
  {
  	RoeFileLine [] lines = this.getRoeFileLines();
    boolean valid = false;
    
    for(int i=0; i < lines.length; i++)
    {
      if(lines[i].workedDuringWeek())
      {
        valid = true;
        break;
      }
    }
    
    return valid;
  }
  
}