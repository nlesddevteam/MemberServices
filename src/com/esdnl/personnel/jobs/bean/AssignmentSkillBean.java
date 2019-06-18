package com.esdnl.personnel.jobs.bean;

public class AssignmentSkillBean 
{
  private int assign_id;
  private int skill_id;
  
  
  public AssignmentSkillBean(int skill_id)
  {
    this(-1, skill_id);
  }
  
  public AssignmentSkillBean(int assign_id, int skill_id)
  {
    this.assign_id = assign_id;
    this.skill_id = skill_id;
  }
  
  public int getJobOpportunityAssignmentId()
  {
    return this.assign_id;
  }
  
  public void setJobOpportunityAssignmentId(int assign_id)
  {
    this.assign_id = assign_id;
  }
  
  public int getSkillId()
  {
    return this.skill_id;
  }
  
  public void setSkillId(int skill_id)
  {
    this.skill_id = skill_id;
  }
}