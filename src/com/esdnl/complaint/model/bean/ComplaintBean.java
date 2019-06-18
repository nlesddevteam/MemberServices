package com.esdnl.complaint.model.bean;
import com.esdnl.complaint.model.constant.PhoneType;
import com.esdnl.complaint.model.constant.ComplaintType;
import com.esdnl.complaint.model.constant.ComplaintStatus;
import com.awsd.school.School;
import com.awsd.personnel.*;
import java.util.*;
import com.esdnl.util.*;

public class ComplaintBean 
{
  private int id;
  
  private String firstName;
  private String middleInitial;
  private String lastName;
  
  private String address1;
  private String address2;
  private String address3;
  private String city;
  private String province;
  private String country;
  private String postalCode;
  
  private String email;
  
  private String areaCode;
  private String phoneNumber;
  private PhoneType phoneType;
  
  private String contactTime;
  private String contactRestrictions;
  
  private ComplaintType complaintType;
  private School school;
  private boolean adminContacted;
  private String adminContactDates;
  private String complaintSummary;
  private String urgency;
  private String supportingDocumentation;
  
  private Date dateSubmitted;
  
  private ComplaintStatus cur_status;
  
  private ComplaintHistoryBean[] history;
  
  private ComplaintCommentBean[] comments;
  
  private ComplaintDocumentBean[] docs;

  public ComplaintBean()
  {
    id = -1;
    firstName = null;
    middleInitial = null;
    lastName = null;
    address1 = null;
    address2 = null;
    address3 = null;
    city = null;
    province = "NL";
    country = "CA";
    postalCode = null;
    email = null;
    areaCode = "709";
    phoneNumber = null;
    phoneType = PhoneType.HOME;
    contactTime = null;
    contactRestrictions = null;
    complaintType = ComplaintType.COMPLAINT_OTHER;
    school = null;
    adminContacted = false;
    adminContactDates = null;
    complaintSummary = null;
    urgency = null;
    supportingDocumentation = null;
    
    cur_status = ComplaintStatus.SUBMITTED;
    
    history = null;
    
    dateSubmitted = null;
    
    comments = null;
    
    docs = null;
  }
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int newId)
  {
    id = newId;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String newFirstName)
  {
    firstName = newFirstName;
  }

  public String getMiddleInitial()
  {
    return middleInitial;
  }

  public void setMiddleInitial(String newMiddleInitial)
  {
    middleInitial = newMiddleInitial;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String newLastName)
  {
    lastName = newLastName;
  }
  
  public String getFullName()
  {
    return (this.lastName + ", " + this.firstName + (!StringUtils.isEmpty(this.middleInitial)?" " + this.middleInitial:"")).toUpperCase();
  }

  public String getAddress1()
  {
    return address1;
  }

  public void setAddress1(String newAddress1)
  {
    address1 = newAddress1;
  }

  public String getAddress2()
  {
    return address2;
  }

  public void setAddress2(String newAddress2)
  {
    address2 = newAddress2;
  }

  public String getAddress3()
  {
    return address3;
  }

  public void setAddress3(String newAddress3)
  {
    address3 = newAddress3;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String newCity)
  {
    city = newCity;
  }

  public String getProvince()
  {
    return province;
  }

  public void setProvince(String newProvince)
  {
    province = newProvince;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String newCountry)
  {
    country = newCountry;
  }

  public String getPostalCode()
  {
    return postalCode;
  }

  public void setPostalCode(String newPostalCode)
  {
    postalCode = newPostalCode;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String newEmail)
  {
    email = newEmail;
  }

  public String getAreaCode()
  {
    return areaCode;
  }

  public void setAreaCode(String newAreaCode)
  {
    areaCode = newAreaCode;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber(String newPhoneNumber)
  {
    phoneNumber = newPhoneNumber;
  }

  public PhoneType getPhoneType()
  {
    return phoneType;
  }

  public void setPhoneType(PhoneType newPhoneType)
  {
    phoneType = newPhoneType;
  }

  public String getContactTime()
  {
    return contactTime;
  }

  public void setContactTime(String newContactTime)
  {
    contactTime = newContactTime;
  }

  public String getContactRestrictions()
  {
    return contactRestrictions;
  }

  public void setContactRestrictions(String newContactRestrictions)
  {
    contactRestrictions = newContactRestrictions;
  }

  public ComplaintType getComplaintType()
  {
    return complaintType;
  }

  public void setComplaintType(ComplaintType newComplaintType)
  {
    complaintType = newComplaintType;
  }

  public School getSchool()
  {
    return school;
  }

  public void setSchool(School newSchool)
  {
    school = newSchool;
  }

  public boolean isAdminContacted()
  {
    return adminContacted;
  }

  public void setAdminContacted(boolean newAdminContacted)
  {
    adminContacted = newAdminContacted;
  }

  public String getAdminContactDates()
  {
    return adminContactDates;
  }

  public void setAdminContactDates(String newAdminContactDates)
  {
    adminContactDates = newAdminContactDates;
  }

  public String getComplaintSummary()
  {
    return complaintSummary;
  }

  public void setComplaintSummary(String newComplaintSummary)
  {
    complaintSummary = newComplaintSummary;
  }

  public String getUrgency()
  {
    return urgency;
  }

  public void setUrgency(String newUrgency)
  {
    urgency = newUrgency;
  }

  public String getSupportingDocumentation()
  {
    return supportingDocumentation;
  }

  public void setSupportingDocumentation(String newSupportingDocumentation)
  {
    supportingDocumentation = newSupportingDocumentation;
  }

  public ComplaintStatus getCurrentStatus()
  {
    return cur_status;
  }
  
  public void  setCurrentStatuc(ComplaintStatus status)
  {
    this.cur_status = status;
  }
  
  public void setHistory(ComplaintHistoryBean[] history)
  {
    this.history = history;
  }
  
  public ComplaintHistoryBean[] getHistory()
  {
    return this.history;
  }
  
  public Date getDateSubmitted()
  {
    return this.dateSubmitted;
  }
  
  public void setDateSubmitted(Date dateSubmitted)
  {
    this.dateSubmitted = dateSubmitted;
  }
  
  public Personnel getAssignedTo()
  {
    Personnel tmp = null;
    System.out.println("CUR STATUS: " + this.cur_status);
    if(this.cur_status.equals(ComplaintStatus.ASSIGNED))
    {
      System.out.println("HISTORY LENGTH: " + this.history.length);
      for(int i=this.history.length-1; i >= 0; i--)
      {
        System.out.println(history[i]);
        if(history[i].getAction().equals(ComplaintStatus.ASSIGNED))
        {
          System.out.println("FOUND!");
          System.out.println("WHO: " + history[i].getToWho());
          tmp = history[i].getToWho();
          break;
        }
      }
    }
    
    return tmp;
  }
  
  public ComplaintCommentBean[] getComments()
  {
    return this.comments;
  }
  
  public void setComments(ComplaintCommentBean[] comments)
  {
    this.comments = comments;
  }
  
  public ComplaintDocumentBean[] getDocuments()
  {
    return this.docs;
  }
  
  public void setDocuments(ComplaintDocumentBean[] docs)
  {
    this.docs = docs;
  }
}