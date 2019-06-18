package com.esdnl.personnel.jobs.bean;

public class AssignmentSubjectBean 
{
  private int assign_id;
  private int subject_id;
  
  public AssignmentSubjectBean(int assign_id, int subject_id)
  {
    this.assign_id = assign_id;
    this.subject_id = subject_id;
  }
  
  public AssignmentSubjectBean(int subject_id)
  {
    this(-1, subject_id);
  }
  
  public int getJobOpportunityAssignmentId()
  {
    return this.assign_id;
  }
  
  public void setJobOpportunityAssignmentId(int assign_id)
  {
    this.assign_id = assign_id;
  }
  
  public int getSubjectId()
  {
    return this.subject_id;
  }
  
  public void setSubjectId(int subject_id)
  {
    this.subject_id = subject_id;
  }
}