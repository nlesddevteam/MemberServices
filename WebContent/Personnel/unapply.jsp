<%@ page language="java" 
         import="com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.awsd.mail.bean.*"
         isThreadSafe="false"%>

<%if(session.getAttribute("APPLICANT") == null){
  request.setAttribute("msg", "Login is required to apply for a specific position.");
%>
    <jsp:forward page="applicant_login.jsp" />
<%}else if(request.getParameter("comp_num") == null){%>
    <script type="text/javascript">
      document.location.href='https://www.nlesd.ca/employment/teachingpositions.jsp';
    </script>
<%}else{
    ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  
    JobOpportunityBean opp = JobOpportunityManager.getJobOpportunityBean(request.getParameter("comp_num"));
   
    if((profile != null) && (opp != null))
    {  
      ApplicantProfileManager.withdrawApplicantion(profile, opp);
      
      
      try
      {
        EmailBean email = new EmailBean();
        email.setTo(profile.getEmail());
        email.setSubject("NLESD: Application Withdrawn");
        email.setBody("You application has been withdrawn from competition # " + opp.getCompetitionNumber()
            + "Please do not reply to this message.\n\n" 
            + "\n\nMember Services");
        email.setFrom("employment@nlesd.ca");
        email.send();
      }
      catch(Exception e){}
    %>
      <script type="text/javascript">
        document.location.href='https://www.nlesd.ca/employment/view_job_post.jsp?comp_num=<%=opp.getCompetitionNumber()%>';
      </script>
  <%}
    session.setAttribute("APPLIED", new Boolean(true));
  }
%>
