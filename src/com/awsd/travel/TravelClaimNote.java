package com.awsd.travel;

import com.awsd.personnel.*;

import java.util.*;


public class TravelClaimNote 
{
  private int note_id;
  private int personnel_id;
  private Date note_date;
  private String note;
  
  public TravelClaimNote(int note_id, int personnel_id, Date note_date, String note)
  {
    this.note_id = note_id;
    this.personnel_id = personnel_id;
    this.note_date = note_date;
    this.note = note;
  }

  public TravelClaimNote(Personnel p, String note)
  {
    this(-1, p.getPersonnelID(), null, note);
  }

  public int getNoteID()
  {
    return this.note_id;
  }

  public Personnel getPersonnel() throws PersonnelException
  {
    Personnel tmp = null;

    if(this.personnel_id > 0)
      tmp = PersonnelDB.getPersonnel(this.personnel_id);
    else
      tmp = null;

    return tmp;
  }

  public Date getNoteDate()
  {
    return this.note_date;
  }

  public String getNote()
  {
    return this.note;
  }
}