package com.esdnl.photocopier.bean;

import java.util.*;
import com.awsd.school.*;
import com.esdnl.photocopier.dao.*;

public class PhotocopierBean 
{
  private int id;
  private int school_id;
  private int brand_id;
  private String model_num;
  private int year_acquired;
  private int acquired_from;
  private boolean purchased;
  private int lease_term;
  private boolean service_contract;
  private Date contract_start_date;
  private int contract_term;
  private int serviced_by;
  private double average_service_time;
  private int num_copies;
  private Date effective_date;
  private String other_comments;
  
  public PhotocopierBean()
  {
    this.id = 0;
    this.school_id = 0;
    this.brand_id = 0;
    this.model_num = null;
    this.year_acquired = 0;
    this.acquired_from = 0;
    this.purchased = false;
    this.lease_term = 0;
    this.service_contract = false;
    this.contract_start_date = null;
    this.contract_term = 0;
    this.serviced_by = 0;
    this.average_service_time = 0;
    this.num_copies = 0;
    this.effective_date = null;
    this.other_comments = null;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setSchoolId(int school_id)
  {
    this.school_id = school_id;
  }
  
  public School getSchool() throws SchoolException
  {
    if(this.school_id > 0)
      return SchoolDB.getSchool(this.school_id);
    else
      return null;
  }
  
  public void setBrandId(int brand_id)
  {
    this.brand_id = brand_id;
  }
  
  public BrandBean getBrand() throws PhotocopierException
  {
    return BrandManager.getBrandBean(this.brand_id);
  }
  
  public void setModelNumber(String model_num)
  {
    this.model_num = model_num;
  }
  
  public String getModelNumber()
  {
    return this.model_num;
  }
  
  public void setYearAcquired(int year_acquired)
  {
    this.year_acquired = year_acquired;
  }
  
  public int getYearAcquired()
  {
    return this.year_acquired;
  }
  
  public void setAcquiredFrom(int acquired_from)
  {
    this.acquired_from = acquired_from;
  }
  
  public SupplierBean getAcquiredFrom() throws PhotocopierException
  {
    return SupplierManager.getSupplierBean(this.acquired_from);
  }
  
  public void setPurchased(boolean purchased)
  {
    this.purchased = purchased;
  }
  
  public boolean isPurchased()
  {
    return this.purchased;
  }
  
  public void setLeaseTerm(int lease_term)
  {
    this.lease_term = lease_term;
  }
  
  public int getLeaseTerm()
  {
    return this.lease_term;
  }
  
  public void setServiceContract(boolean service_contract)
  {
    this.service_contract = service_contract;
  }
  
  public boolean hasServiceContract()
  {
    return this.service_contract;
  }
  
  public void setContractDate(Date contract_start_date)
  {
    this.contract_start_date = contract_start_date;
  }
  
  public Date getContractStartDate()
  {
    return this.contract_start_date;
  }
  
  public void setContractTerm(int contract_term)
  {
    this.contract_term = contract_term;
  }
  
  public int getContractTerm()
  {
    return this.contract_term;
  }
  
  public void setServicedBy(int serviced_by)
  {
    this.serviced_by = serviced_by;
  }
  
  public SupplierBean getServicedBy() throws PhotocopierException
  {
    return SupplierManager.getSupplierBean(this.serviced_by);
  }
  
  public void setAverageServiceTime(double average_service_time)
  {
    this.average_service_time = average_service_time;
  }
  
  public double getAverageServiceTime()
  {
    return this.average_service_time;
  }
  
  public void setNumberCopies(int num_copies)
  {
    this.num_copies = num_copies;
  }
  
  public int getNumberCopies()
  {
    return this.num_copies;
  }
  
  public void setEffectiveDate(Date effective_date)
  {
    this.effective_date = effective_date;
  }
  
  public Date getEffectiveDate()
  {
    return this.effective_date;
  }
  
  public void setOtherComments(String other_comments)
  {
    this.other_comments = other_comments;
  }
  
  public String getOtherComments()
  {
    return this.other_comments;
  }
}