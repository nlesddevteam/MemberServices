package com.esdnl.personnel.jobs.bean;

import com.esdnl.personnel.jobs.constants.*;

public class AssignmentTrainingMethodBean 
{
  private int assign_id;
  private TrainingMethodConstant trnmthd_id;
  
  public AssignmentTrainingMethodBean(int assign_id, TrainingMethodConstant trnmthd_id)
  {
    this.assign_id = assign_id;
    this.trnmthd_id = trnmthd_id;
  }
  
  public AssignmentTrainingMethodBean(TrainingMethodConstant trnmthd_id)
  {
    this(-1, trnmthd_id);
  }
  
  public int getJobOpportunityAssignmentId()
  {
    return this.assign_id;
  }
  
  public void setJobOpportunityAssignmentId(int assign_id)
  {
    this.assign_id = assign_id;
  }
  
  public TrainingMethodConstant getTrainingMethod()
  {
    return this.trnmthd_id;
  }
  
  public void setTrainingMethod(TrainingMethodConstant trnmthd_id)
  {
    this.trnmthd_id = trnmthd_id;
  }
}