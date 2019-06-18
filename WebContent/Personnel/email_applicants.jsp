<%@ page language="java"
          import="com.awsd.security.*,
          				com.awsd.mail.bean.*,
          				com.esdnl.personnel.jobs.bean.*,
          				java.util.*"
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	User usr = (User) session.getAttribute("usr");

	
	ApplicantProfileBean[] applicants = null;
	
	if((request.getParameter("type") != null) && request.getParameter("type").equals("sublist"))
		applicants = (ApplicantProfileBean[]) session.getAttribute("SUBLIST_APPLICANTS");
	else
		applicants = (ApplicantProfileBean[]) session.getAttribute("JOB_APPLICANTS");
	
	String msg = "";
	
	if((request.getParameter("sent") != null) && (request.getParameter("sent").equalsIgnoreCase("1"))){
		
    String to[]=null, cc[]=null, bcc[]=null;
    String subject="", message="";
		
		try
    {  
      if(request.getParameter("to") != null)
      {
        to = (usr.getPersonnel().getEmailAddress()+ ";" +request.getParameter("to")).split(";");
        System.err.println("TO: " + request.getParameter("to"));
      }
      else
      	to = new String[]{usr.getPersonnel().getEmailAddress()};

      if((request.getParameter("cc") != null) && (!request.getParameter("cc").equals("")))
      {
        cc = request.getParameter("cc").split(";");
        System.err.println("CC: " + request.getParameter("cc"));
      }
      else
      {
        cc = null;
      }

      if((request.getParameter("bcc") != null) && (!request.getParameter("bcc").equals("")))
      {
        bcc = request.getParameter("bcc").split(";");
        System.err.println("BCC: " + request.getParameter("bcc"));
      }
      else
      {
        bcc = null;
      }

      if(request.getParameter("subject") != null)
      {
        subject = request.getParameter("subject");
      }
    
      if(request.getParameter("message") != null)
      {
        message = request.getParameter("message");
      }
      if(request.getParameter("bcc").length() < 3500){
	      EmailBean email = new EmailBean();
	      email.setTo(to);
	      email.setCC(cc);
	      email.setBCC(bcc);
	      email.setSubject(subject);
	      email.setBody(message);
	      email.setFrom(usr.getPersonnel().getEmailAddress());
	      email.send();
      }
      else{
      	String bcc1[] = new String[bcc.length / 2];
      	String bcc2[] = new String[bcc.length - bcc1.length];
      	
      	System.arraycopy(bcc, 0, bcc1, 0, bcc1.length);
      	System.arraycopy(bcc, bcc1.length, bcc2, 0, bcc2.length);
      	
      	EmailBean email = new EmailBean();
	      email.setTo(to);
	      email.setCC(cc);
	      email.setBCC(bcc1);
	      email.setSubject(subject);
	      email.setBody(message);
	      email.setFrom(usr.getPersonnel().getEmailAddress());
	      email.send();
	      
	      email.setBCC(bcc2);
	      email.send();
      }
      
      msg = "Email sent successfully.";
      
    }
    catch(Exception e)
    {
      System.err.println(e);
      e.printStackTrace(System.err);
      msg = "Could not send email.";
    }
		
	}
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>
      Newfoundland &amp; Labrador English School District - Applicant Email
    </title>

    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript">
      function confirm_send(){
      	return confirm('Are you ready to send this email?');
      }
      
      function show_msg(){
      	var str = '<%= msg %>';
      	if(str)
      		alert(str);
      }
    </script>
  </head>
  <body bgcolor="#FFFFFF" marginheight="0" marginwidth="0" topmargin="0" leftmargin="0" rightmargin="0" onload="show_msg();">
  	<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
    
    <form name="email" action="" method="post" onsubmit="return confirm_send();">
    
      <input type="hidden" name="sent" value="1">
      
      <table  align="center" cellpadding="1" cellspacing="0" border="0" width="255">
      <tr>
      <td bgcolor="#4682B4">
      <table  width="255" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td bgcolor="#f4f4f4" width="55">
            From:
          </td>
          <td bgcolor="#FFFFFF">
            <input type="text" name="from" size="70" value="<%=usr.getPersonnel().getEmailAddress()%>" readonly="readonly">
          </td>
        </tr>
        <tr>
          <td bgcolor="#f4f4f4" width="55">
            To:
          </td>
          <td bgcolor="#FFFFFF">
            <textarea name="to" cols="53" rows="5"></textarea>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" width="55">
            Cc:
          </td>
          <td  bgcolor="#FFFFFF">
            <textarea name="cc" cols="53" rows="5"></textarea>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" width="55">
            Bcc:
          </td>
          <td bgcolor="#FFFFFF">
            <textarea name="bcc" cols="53" rows="5"><%
            		for(int i=0; i < applicants.length; i++)
                	out.print(applicants[i].getEmail().replaceAll(",", ";") + ";");
            %></textarea>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" width="55">
            Subject:
          </td>
          <td bgcolor="#FFFFFF">
            <input type="text" name="subject" size="71" value='Newfoundland and Labrador English School District Employment Opportunity System'>
          </td>
        </tr>
        <tr>
          <td  bgcolor="#f4f4f4" colspan="2">
            Message:
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <textarea name="message" cols="60" rows="15"></textarea>
          </td>
        </tr>
        <tr>
          <td valign="middle" colspan="2" align="right">
            <input type="submit" value="Send" />
          </td>
        </tr>
      </table>
      </td>
      </tr>
      </table>
    </form>
  </body>
</html>
