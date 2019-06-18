package com.awsd.efile.edocument;

import com.awsd.efile.*;
import com.awsd.personnel.*;
import com.awsd.school.*;

import java.util.*;


public class Document 
{
  private int id;
  private String desc;
  private int doctype;
  private int sid;
  private int gid;
  private int pid;
  private int cid;
  private Date uploaded;
  private Date lastviewed;
  private String[] keywords = null;

  private static HashMap gradesMap = null;
  private static HashMap coursesMap = null;
  private static HashMap typesMap = null;
  private static HashMap subjectsMap = null;

  static
  {
    try
    {
      loadGradesMap();
      loadCoursesMap();
      loadTypesMap();
      loadSubjectsMap();
    }
    catch(Exception e)
    {
      System.err.println("Document (static block): " + e);
    }
  }
  
  public Document(String desc, int doctype, int sid, int gid, int cid, int pid, Date uploaded, Date lastviewed, String keywords[])
  {
    this.desc = desc;
    this.doctype = doctype;
    this.sid = sid;
    this.gid = gid;
    this.cid = cid;
    this.pid = pid;
    this.uploaded = uploaded;
    this.lastviewed = lastviewed;
    id = -1;
    this.keywords = keywords;
  }

  public Document(int doctype, int sid, int gid, int cid, int pid, String keywords[])
  {
    this.desc = "UNKNOWN";
    this.doctype = doctype;
    this.sid = sid;
    this.gid = gid;
    this.cid = cid;
    this.pid = pid;
    this.uploaded = null;
    this.lastviewed = null;
    id = -1;
    this.keywords = keywords;
  }

  public Document(int doctype, int sid, int gid, int pid, String keywords[])
  {
    this.desc = "UNKNOWN";
    this.doctype = doctype;
    this.sid = sid;
    this.gid = gid;
    this.cid = -1;
    this.pid = pid;
    this.uploaded = null;
    this.lastviewed = null;
    id = -1;
    this.keywords = keywords;
  }

  public Document(int id, String desc, int doctype, int subject, int gid, int cid, int pid, Date uploaded, Date lastviewed, String keywords[])
  {
    this(desc, doctype, subject, gid, cid, pid, uploaded, lastviewed, keywords);
    this.id = id;
  }

  public Document(int id, String desc, int doctype, int subject, int gid, int cid, int pid, Date uploaded)
  {
    this(desc, doctype, subject, gid, cid, pid, uploaded, null, null);
    this.id = id;
  }

  public int getDocumentID()
  {
    return this.id;
  }

  public void setDocumentID(int id)
  {
    this.id = id;
  }

  public String getDocumentDescription()
  {
    return this.desc;
  }

  /*
  public DocumentType getDocumentType() throws EFileException
  {
    DocumentType dt = null;

    if(doctype > 0)
    {
      try
      {
        dt = DocumentTypeDB.getDocumentType(doctype);
      }
      catch(DocumentTypeException e)
      {
        throw new EFileException("Could not find document type");
      }
    }
    else
    {
      dt = null;
    }
    return dt;
  }
  */

  /*
   * No Database query version
   */
  public DocumentType getDocumentType() throws EFileException
  {
    DocumentType dt = (DocumentType)typesMap.get(new Integer(doctype));

    if(dt == null)
    {
      throw new EFileException("Could not find document type");
    }

    return dt;
  }

  /*
  public Subject getSubject() throws EFileException
  {
    Subject s = null;

    if(sid > 0)
    {
      try
      {
        s = SubjectDB.getSubject(sid);
      }
      catch(SubjectException e)
      {
        throw new EFileException("Could not find subject");
      }
    }
    else
    {
      s = null;
    }
    return s;
  }
  */

  /*
   * No database query version
   */

  public Subject getSubject() throws EFileException
  {
    Subject s = (Subject)subjectsMap.get(new Integer(sid));

    if(s == null)
    {
      throw new EFileException("Could not find subject");
    }
    
    return s;
  }
  

  /*
  public Course getCourse() throws EFileException
  {
    Course c = null;

    if(cid > 0)
    {
      try
      {
        c = CourseDB.getCourse(cid);
      }
      catch(CourseException e)
      {
        throw new EFileException("Could not find course");
      }
    }
    else
    {
      c = null;
    }
    return c;
  }
  */

  /*
   * no database query version
   */
  public Course getCourse() throws EFileException
  {
    Course c = null;

    if(cid > 0)
    {
      c = (Course)coursesMap.get(new Integer(cid));

      if(c == null)
      {
        throw new EFileException("Could not find course");
      }
    }
  
    return c;
  }

  /*
  public Grade getGrade() throws EFileException
  {
    Grade s = null;

    if(gid > 0)
    {
      try
      {
        s = GradeDB.getGrade(gid);
      }
      catch(GradeException e)
      {
        throw new EFileException("Could not find grade");
      }
    }
    else
    {
      s = null;
    }
    return s;
  }
  */

  /*
   * no database query version
   */
  public Grade getGrade() throws EFileException
  {
    Grade g = (Grade)gradesMap.get(new Integer(gid));

    if(g == null)
    {
      throw new EFileException("Could not find grade");
    }
    
    return g;
  }

  public Personnel getPersonnel() throws EFileException
  {
    Personnel p = null;

    if(pid > 0)
    {
      try
      {
        p = PersonnelDB.getPersonnel(pid);
      }
      catch(PersonnelException e)
      {
        throw new EFileException("Could not find personnel");
      }
    }
    else
    {
      p = null;
    }
    return p;
  }

  public String[] getKeywords() throws EFileException
  {
    if(keywords == null)
    {
      try
      {
        keywords = DocumentKeywordsDB.getDocumentKeywords(this);
      }
      catch(DocumentKeywordException e)
      {
        throw new EFileException("Could not find personnel" + e);
      }
    }
    
    return keywords;
  }

  public Date getUploadDate()
  {
    return uploaded;
  }

  public Date getLastViewedDate()
  {
    return lastviewed;
  }

  /*
   * IMPLEMENT LATER
  public void setLastViewDate()
  {
    return;
  }

  public void setLastViewedBy()
  {
    return;
  }
   */

   /*
    * UTILITY METHODS..TO BOOST SEARCH PERFORMANCE 
    */
  private static void loadCoursesMap() throws CourseException
  {
    Vector courses = null;
    Course c = null;
    Iterator iter = null;

    coursesMap = new HashMap(150);
    courses = CourseDB.getCourses();
    iter = courses.iterator();

    while(iter.hasNext())
    {
      c = (Course)iter.next();
      coursesMap.put(new Integer(c.getCourseID()), c);
    }
  }

  private static void loadSubjectsMap() throws SubjectException
  {
    Vector subjects = null;
    Subject s = null;
    Iterator iter = null;

    subjectsMap = new HashMap(20);
    subjects = SubjectDB.getSubjects();
    iter = subjects.iterator();

    while(iter.hasNext())
    {
      s = (Subject)iter.next();
      subjectsMap.put(new Integer(s.getSubjectID()), s);
    }
  }

  private static void loadGradesMap() throws GradeException
  {
    Vector grades = null;
    Grade g = null;
    Iterator iter = null;

    gradesMap = new HashMap(15);
    grades = GradeDB.getGrades();
    iter = grades.iterator();

    while(iter.hasNext())
    {
      g = (Grade)iter.next();
      gradesMap.put(new Integer(g.getGradeID()), g);
    }
  }

  private static void loadTypesMap() throws DocumentTypeException
  {
    Vector types = null;
    DocumentType t = null;
    Iterator iter = null;

    typesMap = new HashMap(7);
    types = DocumentTypeDB.getDocumentTypes();
    iter = types.iterator();

    while(iter.hasNext())
    {
      t = (DocumentType)iter.next();
      typesMap.put(new Integer(t.getDocumentTypeID()), t);
    }
  }
}