package com.awsd.efile.equestion.handler;

import com.awsd.efile.equestion.*;
import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;


public class ImportExamQuestionRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    UploadFile file = null;
    File f = null;
    MultipartFormDataRequest mrequest = null;
    int qtype=-1, qsub=-1, qgrade=-1, qcourse=-1;
    String qstem, unitnum, correct_answer;
    String correct_options[] = null;
    boolean opt_sel = false;
    Grade g = null;
    Question question = null;
    QuestionOptions options = null;
    String option_text;
    

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    try
    {
      if (MultipartFormDataRequest.isMultipartFormData(request))
      {
        mrequest = new MultipartFormDataRequest(request);

        if(mrequest.getParameter("questionType") != null)
        {
          qtype = Integer.parseInt(mrequest.getParameter("questionType"));
        }
        else
        {
          throw new QuestionException("Question Type required.");
        }

        if(mrequest.getParameter("questionStem") != null)
        {
          qstem = mrequest.getParameter("questionStem");
        }
        else
        {
          throw new QuestionException("Question Stem required.");
        }

        if(mrequest.getParameter("questionUnitNumber") != null)
        {
          unitnum = mrequest.getParameter("questionUnitNumber");
        }
        else
        {
          throw new QuestionException("Question Unit Number required.");
        }

        if(mrequest.getParameter("questionSubject") != null)
        {
          qsub = Integer.parseInt(mrequest.getParameter("questionSubject"));
        }
        else
        {
          throw new QuestionException("Question Subject required.");
        }

        if(mrequest.getParameter("gradeLevel") != null)
        {
          qgrade = Integer.parseInt(mrequest.getParameter("gradeLevel"));
        }
        else
        {
          throw new QuestionException("Grade level required.");
        }

        if(mrequest.getParameter("documentCourse") != null)
        {
          qcourse = Integer.parseInt(mrequest.getParameter("documentCourse"));
        }
        else
        {
          g = GradeDB.getGrade(qgrade);
          if(g.getGradeName().equalsIgnoreCase("LEVEL I") 
            || g.getGradeName().equalsIgnoreCase("LEVEL II")
            || g.getGradeName().equalsIgnoreCase("LEVEL III"))
          {
            throw new QuestionException(g.getGradeName() + " requires a course ID.");
          }
          else
          {
            qcourse = -1;
          }
        }

        switch (qtype)
        {
          case 1://MCSA
          case 2: //MCMA
            correct_options = mrequest.getParameterValues("correct_option");
            if(correct_options.length < 1)
            {
              throw new QuestionException("Correct option(s) not choosen.");
            }
            else
            {
              options = new QuestionOptions();
              
              for(int i=1; i <= 9; i++)
              {
                option_text = mrequest.getParameter("option_text_"+i);
                //System.err.println("i: " + option_text);
                
                if((option_text != null) && (!option_text.trim().equals("")))
                {
                  opt_sel = false;

                  for(int j=0; j < correct_options.length; j++)
                  {
                    if(Integer.parseInt(correct_options[j]) == i)
                    {
                      opt_sel = true;
                      break;
                    }
                  }
                  options.add(new QuestionOption(option_text.trim(), opt_sel));
                }
              }
              question = new Question(qtype, usr.getPersonnel().getPersonnelID(), 
                qstem, qsub, qgrade, qcourse, unitnum, options);

              //System.err.println("Options: " + question.getOptions().size());
            }
            break;
          case 3: //true/false
            correct_answer = mrequest.getParameter("correct_answer");
            if((correct_answer == null)||(correct_answer.equals("")))
            {
              throw new QuestionException("Correct Answer not provided.");
            }
            else
            {
              question = new Question(qtype, usr.getPersonnel().getPersonnelID(), 
                qstem, qsub, qgrade, qcourse, unitnum, correct_answer);
            }
            break;
          case 4: //fill-ins
            correct_answer = mrequest.getParameter("fillin_correct_answer");
            if((correct_answer == null)||(correct_answer.equals("")))
            {
              throw new QuestionException("Correct Answer not provided.");
            }
            else
            {
              question = new Question(qtype, usr.getPersonnel().getPersonnelID(), 
                qstem, qsub, qgrade, qcourse, unitnum, correct_answer);
            }
            break;
          case 5: //short answer
          case 6: //eassy
            question = new Question(qtype, usr.getPersonnel().getPersonnelID(), 
                qstem, qsub, qgrade, qcourse, unitnum);
            break;
        }
        
        Hashtable files = mrequest.getFiles();
        if ( (files != null) || (!files.isEmpty()) )
        {
          file = (UploadFile) files.get("uploadfile");
        }
        
          
        QuestionDB.addQuestion(question);
          
        
        request.setAttribute("msg", "Question imported successfully.");
      }
    }
    catch(UploadException e)
    {
      System.err.println(e);
      throw new QuestionException(e.toString());
    }

    
    path = "importQuestion.jsp";
    
    return path;
  }
}