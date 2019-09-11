<%@ page language="java" 
         import="com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.awsd.mail.bean.*,
                 java.util.HashMap"
         isThreadSafe="false"%>

<%if(session.getAttribute("APPLICANT") == null){
  request.setAttribute("msg", "Login is required to apply for a specific position.");
%>
    <jsp:forward page="applicant_login.jsp" />
<%}else if(request.getParameter("comp_num") == null){%>
  <script type="text/javascript">
    document.location.href='/employment/teachingpositions.jsp';
  </script>
<%}else if((session.getAttribute("JOBS-APPLIED-FOR-MAP") != null)
		&& (((HashMap)session.getAttribute("JOBS-APPLIED-FOR-MAP")).containsKey(request.getParameter("comp_num")))){%>
  <script type="text/javascript">
    history.go(-2);
  </script>
<%}else{
  	ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");  
    ApplicantProfileManager.applyForPosition(profile, request.getParameter("comp_num"));
    
    System.out.println(">>> " + profile.getFullNameReverse().toUpperCase()+ " APPLIED FOR JOB #" + request.getParameter("comp_num") + " FROM " + request.getRemoteAddr() + " <<<");
    
    try
    {
    	EmailBean email = new EmailBean();
    	email.setTo(new String[]{profile.getEmail()});
    	email.setSubject("NLESD: Application Received");
    	email.setBody("You application has been received for competition # " + request.getParameter("comp_num")
          + "Please do not reply to this message.\n\n" 
          + "\n\nMember Services");
    	email.setFrom("employment@nlesd.ca");
    	email.send();
    }
    catch(Exception e){}
    
    HashMap applied_for = null;
    if(session.getAttribute("JOBS-APPLIED-FOR-MAP") != null)
    	applied_for = (HashMap) session.getAttribute("JOBS-APPLIED-FOR-MAP");
    else
    	applied_for = new HashMap();
    
    applied_for.put(request.getParameter("comp_num"), null);
    
    session.setAttribute("JOBS-APPLIED-FOR-MAP", applied_for);
  %>
    <script type="text/javascript">
      document.location.href='/employment/view_job_post.jsp?comp_num=<%=request.getParameter("comp_num")%>';
    </script>
<%}%>
