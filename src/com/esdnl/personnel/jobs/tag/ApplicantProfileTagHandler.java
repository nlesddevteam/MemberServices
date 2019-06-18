package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.personnel.jobs.constants.*;
import com.esdnl.util.*;

import com.awsd.school.*;

public class ApplicantProfileTagHandler  extends TagSupport
{
  public int doStartTag() throws JspException
  {
    SimpleDateFormat sdf = null;
    JspWriter out = null;
    
    ApplicantProfileBean profile = null;
    
    try
    {    
      out = pageContext.getOut();
      
      profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", pageContext.SESSION_SCOPE);
      

      out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
      
      
      if(profile != null)
      {
          //line 1
          out.println("<TR>");
          out.println("   <TD width='100%'>");
          out.println("     <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("       <TR>");
          //Surname
          out.println("         <TD width='33%'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Surname:</TD>");
          out.println("               <TD class='displayText'>" + profile.getSurname() + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          //First Name
          out.println("         <TD width='33%'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>First Name:</TD>");
          out.println("               <TD class='displayText'>" + profile.getFirstname() + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          //Middle Name
          out.println("         <TD width='*'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Middle Name:</TD>");
          out.println("               <TD class='displayText'>" + ((!StringUtils.isEmpty(profile.getMiddlename()))?profile.getMiddlename():"&nbsp;") + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          out.println("       </TR>");
          out.println("     </TABLE>"); 
          out.println("   </TD>");
          out.println("</TR>");
        
          //line 2
          out.println("<TR>");
          out.println("   <TD width='100%'>");
          out.println("     <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("       <TR>");
          //SIN
          out.println("         <TD width='65%'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Social Insurance Number:</TD>");
          out.println("               <TD class='displayText'>" + profile.getSIN() + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          //Maiden Name
          out.println("         <TD width='*'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Maiden Name:</TD>");
          out.println("               <TD class='displayText'>" + (!StringUtils.isEmpty(profile.getMaidenname())?profile.getMaidenname():"&nbsp;") + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          out.println("       </TR>");
          out.println("     </TABLE>"); 
          out.println("   </TD>");
          out.println("</TR>");
          
          //line 3
          out.println("<TR>");
          out.println("   <TD width='100%'>");
          out.println("     <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("       <TR>");
          out.println("         <TD width='100%'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          //Address1
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Mailing Address:</TD>");
          out.println("               <TD class='displayText'>" + profile.getAddress1() + "</TD>");
          out.println("             </TR>");
          //Address2
          if(!StringUtils.isEmpty(profile.getAddress2()))
          {
            out.println("             <TR>");
            out.println("               <TD class='displayHeaderTitle'>&nbsp;</TD>");
            out.println("               <TD class='displayText'>" + profile.getAddress2() + "</TD>");
            out.println("             </TR>");
          }
          //Province, Country
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>&nbsp;</TD>");
          out.println("               <TD class='displayText'>" + profile.getProvince() + ", " + profile.getCountry() + "</TD>");
          out.println("             </TR>");
          //Postal Code
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>&nbsp;</TD>");
          out.println("               <TD class='displayText'>" + profile.getPostalcode() + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          out.println("       </TR>");
          out.println("     </TABLE>"); 
          out.println("   </TD>");
          out.println("</TR>");
          
          //line 4
          out.println("<TR>");
          out.println("   <TD width='100%'>");
          out.println("     <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("       <TR>");
          //Telephone (Home)
          out.println("         <TD width='33%'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Tele (Home):</TD>");
          out.println("               <TD class='displayText'>" + profile.getHomephone() + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          //Telephone (Work)
          out.println("         <TD width='33%'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Tele (Work):</TD>");
          out.println("               <TD class='displayText'>" + (!StringUtils.isEmpty(profile.getWorkphone())?profile.getWorkphone():"&nbsp;") + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          //Cellphone
          out.println("         <TD width='*'>");
          out.println("           <TABLE width='100%' cellpadding='0' cellspacing='0'>");
          out.println("             <TR>");
          out.println("               <TD class='displayHeaderTitle'>Cell:</TD>");
          out.println("               <TD class='displayText'>" + (!StringUtils.isEmpty(profile.getCellphone())?profile.getCellphone():"&nbsp;") + "</TD>");
          out.println("             </TR>");
          out.println("           </TABLE>");
          out.println("         </TD>");
          out.println("       </TR>");
          out.println("     </TABLE>"); 
          out.println("   </TD>");
          out.println("</TR>");
      }
      else
      {
        out.println("<TR><TD width='100%' class='displayText'>Profile not on record.</TD></TR>");  
      }
      out.println("</TABLE>"); 
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}