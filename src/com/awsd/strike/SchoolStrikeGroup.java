package com.awsd.strike;

import com.awsd.personnel.*;
import com.awsd.school.*;


public class SchoolStrikeGroup 
{
  private int gid;
  private int pid;
  private Personnel coordinator = null;
  
  public SchoolStrikeGroup(int gid, int pid) throws PersonnelException
  {
    this.gid = gid;
    this.pid = pid;
    
    if(this.pid != -1)
    {
      this.coordinator = PersonnelDB.getPersonnel(this.pid);
    }
    else
    {
      throw new PersonnelException("SCHOOL GROUP COORDINATOR ID IS INVALID");
    }
  }

  public SchoolStrikeGroup(int gid, Personnel coordinator)
  {
    this.gid = gid;
    this.pid = coordinator.getPersonnelID();
    this.coordinator = coordinator;
  }

  public int getGroupID()
  {
    return this.gid;
  }

  public int getGroupCoordinatorID()
  {
    return this.pid;
  }

  public Personnel getGroupCoordinator() throws PersonnelException
  {
    if(coordinator == null)
    {
      if(pid != -1)
      {
        coordinator = PersonnelDB.getPersonnel(this.pid);
      }
      else
      {
        throw new PersonnelException("SCHOOL GROUP COORDINATOR ID IS INVALID");
      }
    }

    return coordinator;
  }

  public Schools getSchoolStrikeGroupSchools() throws SchoolException
  {
    return new Schools(this);
  }
}