package com.awsd.efile.equestion;

import com.awsd.efile.*;
import com.awsd.personnel.*;
import com.awsd.school.*;

import java.util.*;


public class Question 
{
  private int question_id;
  private int question_type;
  private int personnel_id;
  private Date submit_date;
  private String stem;
  private int subject_id;
  private int grade_id;
  private int course_id;
  private String unit_number;
  private String correct_answer;
  private QuestionOptions options = null;

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
  
  public Question(int question_id, int question_type, int personnel_id,
    Date submit_date, String stem, int subject_id, int grade_id, int course_id,
    String unit_number, String correct_answer)
  {
    this.question_id = question_id;
    this.question_type = question_type;
    this.personnel_id = personnel_id;
    this.submit_date = submit_date;
    this.stem = stem;
    this.subject_id = subject_id;
    this.grade_id = grade_id;
    this.course_id = course_id;
    this.unit_number = unit_number;
    this.correct_answer = correct_answer;
    
    options = null;
  }

  public Question(int question_type, int personnel_id,
    Date submit_date, String stem, int subject_id, int grade_id, int course_id,
    String unit_number, String correct_answer)
  {
    this(-1, question_type, personnel_id, submit_date, stem, subject_id, 
      grade_id, course_id, unit_number, correct_answer);
  }

  public Question(int question_type, int personnel_id,
    String stem, int subject_id, int grade_id, int course_id,
    String unit_number, String correct_answer)
  {
    this(-1, question_type, personnel_id, null, stem, subject_id, 
      grade_id, course_id, unit_number, correct_answer);
  }

  public Question(int question_type, int personnel_id,
    String stem, int subject_id, int grade_id, int course_id,
    String unit_number, QuestionOptions options)
  {
    this(-1, question_type, personnel_id, null, stem, subject_id, 
      grade_id, course_id, unit_number, null);

    this.options = options;
  }

  public Question(int question_type, int personnel_id,
    String stem, int subject_id, int grade_id, int course_id,
    String unit_number)
  {
    this(-1, question_type, personnel_id, null, stem, subject_id, 
      grade_id, course_id, unit_number, null);
  }

  public int getQuestionID()
  {
    return this.question_id;
  }

  public void setQuestionID(int question_id)
  {
    this.question_id = question_id;
  }

  public int getQuestionTypeID()
  {
    return this.question_type;
  }

  public void setQuestionType(int question_type)
  {
    this.question_type = question_type;
  }

  public int getSubmittedByID()
  {
    return this.personnel_id;
  }

  public void setSubmittedByID(int personnel_id)
  {
    this.personnel_id = personnel_id;
  }

  public Date getSubmitDate()
  {
    return this.submit_date;
  }

  public void setSubmitDate(Date submit_date)
  {
    this.submit_date = submit_date;
  }

  public String getStem()
  {
    return this.stem;
  }

  public void setStem(String stem)
  {
    this.stem = stem;
  }

  public int getGradeID()
  {
    return this.grade_id;
  }

  public void setGradeID(int grade_id)
  {
    this.grade_id = grade_id;
  }

  public int getSubjectID()
  {
    return this.subject_id;
  }

  public void setSubjectID()
  {
    this.subject_id = subject_id;
  }

  public int getCourseID()
  {
    return this.course_id;
  }

  public void setCourseID(int course_id)
  {
    this.course_id = course_id;
  }

  public String getUnitNumber()
  {
    return this.unit_number;
  }

  public void setUnitNumber(String unit_number)
  {
    this.unit_number = unit_number;
  }

  public String getCorrectAnswer()
  {
    return this.correct_answer;
  }

  public void setCorrectAnswer()
  {
    this.correct_answer = correct_answer;
  }

  public QuestionOptions getOptions() throws QuestionException
  {
    if (this.options == null)
    {
      this.options = new QuestionOptions(this);
    }

    return this.options;
  }

  public void setQuestionOptions(QuestionOptions options)
  {
    this.options = options;
  }

  public QuestionType getQuestionType() throws EFileException
  {
    QuestionType dt = (QuestionType)typesMap.get(new Integer(this.question_type));

    if(dt == null)
    {
      throw new EFileException("Could not find document type");
    }

    return dt;
  }

  public Course getCourse() throws EFileException
  {
    Course c = null;

    if(course_id > 0)
    {
      c = (Course)coursesMap.get(new Integer(course_id));

      if(c == null)
      {
        throw new EFileException("Could not find course");
      }
    }
  
    return c;
  }
  
  public Grade getGrade() throws EFileException
  {
    Grade g = (Grade)gradesMap.get(new Integer(grade_id));

    if(g == null)
    {
      throw new EFileException("Could not find grade");
    }
    
    return g;
  }

  public Subject getSubject() throws EFileException
  {
    Subject s = (Subject)subjectsMap.get(new Integer(subject_id));

    if(s == null)
    {
      throw new EFileException("Could not find subject");
    }
    
    return s;
  }

  public Personnel getPersonnel() throws EFileException
  {
    Personnel p = null;

    if(personnel_id > 0)
    {
      try
      {
        p = PersonnelDB.getPersonnel(this.personnel_id);
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

  private static void loadTypesMap() throws QuestionException
  {
    Vector types = null;
    QuestionType t = null;
    Iterator iter = null;

    typesMap = new HashMap(7);
    types = QuestionDB.getQuestionTypes();
    iter = types.iterator();

    while(iter.hasNext())
    {
      t = (QuestionType)iter.next();
      typesMap.put(new Integer(t.getTypeID()), t);
    }
  }
}