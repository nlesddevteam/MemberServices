package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;

public class ApplicantViewPositionOfferAuthorization extends TagSupport {
	
	private static final long serialVersionUID = -3027186461257262996L;

	public int doStartTag() throws JspException
  {
    ApplicantProfileBean  profile = null;
    TeacherRecommendationBean rec = null;
    try
    {
      profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);
      rec = (TeacherRecommendationBean) pageContext.getAttribute("RECOMMENDATION_BEAN", PageContext.REQUEST_SCOPE);
      
      if(!profile.getSIN().equalsIgnoreCase(rec.getCandidateId()))
      {
        this.pageContext.setAttribute("msg", "Secure Resource! Login Required.", PageContext.REQUEST_SCOPE);
        this.pageContext.forward("/Personnel/applicant_login.jsp");
      }
    }
    catch(SecurityException e)
    {
      throw new JspException(e.getMessage());
    }
    catch(ServletException e)
    {
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}
