<%@ page language="java" 
         import="java.io.*,
                 java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.awsd.mail.bean.*"
         isThreadSafe="false"%>

<%if(session.getAttribute("APPLICANT") == null){
  request.setAttribute("msg", "Login is required to apply for a specific position.");
%>
    <jsp:forward page="applicant_login.jsp" />
<%}else if(request.getParameter("list_id") == null){%>
  <script type="text/javascript">
    document.location.href='/employment/teachingpositions.jsp';
  </script>
<%}else if(session.getAttribute("APPLIED") != null){
    session.setAttribute("APPLIED", null);
%>
  <script type="text/javascript">
    self.close();
  </script>
<%}else{
  ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  
  SubListBean list = SubListManager.getSubListBean(Integer.parseInt(request.getParameter("list_id")));
  
   try
   {
    ApplicantProfileManager.applyForPosition(profile, list);
      
      EmailBean email = new EmailBean();
      email.setTo(new String[]{profile.getEmail()});
      email.setSubject("Application Received");
      email.setBody("You application has been received for substitute list \"" + list.getTitle()
          + "\" Please do not reply to this message.\n\n" 
          + "\n\nMember Services");
      email.setFrom("employment@nlesd.ca");
      email.send();
    }
    catch(JobOpportunityException e){}
  %>
    <script type="text/javascript">
      document.location.href='/employment/view_sub_list.jsp?list_id=<%=request.getParameter("list_id")%>&applied=true';
    </script>
<%
    session.setAttribute("APPLIED", new Boolean(true));
  }
%>
