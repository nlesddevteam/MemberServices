package com.awsd.travel;

import com.awsd.personnel.pd.*;

import java.util.*;


public class PDTravelClaim extends TravelClaim implements Claim
{
  private int pd_id;
  
  private PersonnelPD pd_ref = null;
  
  public PDTravelClaim( int claim_id, 
                        int personnel_id, 
                        int cur_status, 
                        Date created_date, 
                        Date submit_Date, 
                        String fiscal_year, 
                        int fiscal_month, 
                        int supervisor_id, 
                        String sds_gl_acc, 
                        boolean sds_paid_tchr_payroll, 
                        Date approved_date, 
                        Date paid_date, 
                        Date exported_date,
                        int pd_id  )
  {
    super(claim_id, personnel_id, cur_status, created_date, submit_Date, 
          fiscal_year, fiscal_month, supervisor_id, sds_gl_acc, sds_paid_tchr_payroll, 
          approved_date, paid_date, exported_date);
    
    this.pd_id = pd_id;
  }
  
  public PersonnelPD getPD() throws PDException
  {
    if(pd_ref == null)
    {
      if(this.pd_id > 0)
        pd_ref = PersonnelPDDB.getPD(this.pd_id);
    }
    
    return pd_ref;
  }
}