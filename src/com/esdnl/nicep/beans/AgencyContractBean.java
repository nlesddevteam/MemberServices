package com.esdnl.nicep.beans;

import java.text.*;
import java.util.*;
import com.esdnl.nicep.constants.*;

public class AgencyContractBean 
{
  private int AgencyId;
  private int contractId;
  private Date effectiveDate;
  private Date endDate;
  private String filename;
  private AgencyContractType contract_type;
  private double contract_type_value;
  
  private final String DATE_FORMAT = "dd/MM/yyyy";

  public AgencyContractBean()
  {
    AgencyId = 0;
    contractId = 0;
    effectiveDate = null;
    endDate = null;
    filename = null;
    contract_type = null;
    contract_type_value = 0;
  }

  public int getAgencyId()
  {
    return AgencyId;
  }

  public void setAgencyId(int newAgencyId)
  {
    AgencyId = newAgencyId;
  }

  public int getContractId()
  {
    return contractId;
  }

  public void setContractId(int newContractId)
  {
    contractId = newContractId;
  }

  public Date getEffectiveDate()
  {
    return effectiveDate;
  }
  
  public String getFormatedEffectiveDate()
  {
    return (new SimpleDateFormat(DATE_FORMAT)).format(getEffectiveDate());
  }

  public void setEffectiveDate(Date newEffectiveDate)
  {
    effectiveDate = newEffectiveDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
  
  public String getFormatedEndDate()
  {
    return (new SimpleDateFormat(DATE_FORMAT)).format(getEndDate());
  }

  public void setEndDate(Date newEndDate)
  {
    endDate = newEndDate;
  }

  public String getFilename()
  {
    return filename;
  }

  public void setFilename(String newFilename)
  {
    filename = newFilename;
  }
  
  public AgencyContractType getContractType()
  {
    return this.contract_type;
  }
  
  public void setContractType(AgencyContractType contract_type)
  {
    this.contract_type = contract_type;
  }
  
  public double getContractTypeValue()
  {
    return this.contract_type_value;
  }
  
  public String getFormattedContractTypeValue()
  {
    return (new DecimalFormat("$#,##0.00")).format(getContractTypeValue());
  }
  
  public void setContractTypeValue(double contract_type_value)
  {
    this.contract_type_value = contract_type_value;
  }
}