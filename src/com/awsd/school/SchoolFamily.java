package com.awsd.school;

import com.awsd.personnel.*;


public class SchoolFamily 
{
  private int family_id;
  private int ps_id;
  private String family_name;

  private Personnel ps = null;
  private Schools schools = null;

  public SchoolFamily()
  {
    this(-1, -1);
  }
  
  public SchoolFamily(int family_id, int ps_id)
  {
    this.family_id = family_id;
    this.ps_id = ps_id;
    if (ps_id  <= 0)
    {
      try
      {
        this.ps = PersonnelDB.getPersonnel(ps_id);
        this.family_name = ps.getFullNameReverse() + "'s School Family";
      }
      catch(PersonnelException e)
      {
        this.ps_id = -1;
        this.family_name = "UNASSIGNED";
        this.ps = null;
      }
    }
    else
    {
      this.ps = null;
      this.family_name = "UNASSIGNED";
    }
  }

  public SchoolFamily(int family_id, int ps_id, String family_name)
  {
    this.family_id = family_id;
    this.ps_id = ps_id;
    this.family_name = family_name;
    this.ps = null;
  }

  public SchoolFamily(int family_id, Personnel ps)
  {
    this.family_id = family_id;
    this.ps_id = ps.getPersonnelID();
    this.ps = ps;
    family_name = ps.getFullNameReverse() + "'s School Family";
  }

  public SchoolFamily(int family_id, Personnel ps, String family_name)
  {
    this.family_id = family_id;
    this.ps_id = ps.getPersonnelID();
    this.ps = ps;
    this.family_name = family_name;
  }

  public SchoolFamily(int ps_id)
  {
    this.family_id = -1;
    this.ps_id = ps_id;

    if (ps_id  <= 0)
    {
      try
      {
        this.ps = PersonnelDB.getPersonnel(ps_id);
        this.family_name = ps.getFullNameReverse() + "'s School Family";
      }
      catch(PersonnelException e)
      {
        this.ps_id = -1;
        this.family_name = "UNASSIGNED";
        this.ps = null;
      }
    }
    else
    {
      this.ps = null;
      this.family_name = "UNASSIGNED";
    }
  }

  public SchoolFamily(Personnel ps)
  {
    this.family_id = -1;
    this.ps_id = ps.getPersonnelID();
    this.family_name =  ps.getFullNameReverse() + "'s School Family";
    this.ps = ps;
  }

  public SchoolFamily(int ps_id, String family_name)
  {
    this.family_id = -1;
    this.ps_id = ps_id;
    this.family_name = family_name;
    this.ps = null;
  }

  public SchoolFamily(Personnel ps, String family_name)
  {
    this.family_id = -1;
    this.ps  = ps;
    this.ps_id = ps.getPersonnelID();
    this.family_name = family_name;
  }

  public int getSchoolFamilyID()
  {
    return this.family_id;
  }

  public void setSchoolFamilyID(int family_id)
  {
    this.family_id = family_id;
  }

  public String getSchoolFamilyName()
  {
    return this.family_name;
  }

  public void setSchoolFamilyName(String family_name)
  {
    this.family_name = family_name;
  }

  public Personnel getProgramSpecialist()
  {
    Personnel tmp = null;

    if(ps != null)
    {
      tmp = ps;
    }
    else if(this.ps_id > 0)
    {
      try
      {
        tmp = PersonnelDB.getPersonnel(this.ps_id);
      }
      catch(PersonnelException e)
      {
        tmp = null;
      }
    }
    return tmp;
  }

  public int getProgramSpecialistID()
  {
    return this.ps_id;
  }

  public void setProgramSpecialistID(int ps_id)
  {
    this.ps_id = ps_id;
  }

  public Schools getSchoolFamilySchools() throws SchoolException
  {
    if(schools == null)
    {
      schools = new Schools(this);
    }

    return schools;
  }
}