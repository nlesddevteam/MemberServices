<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*, 
                java.util.*,com.awsd.security.*, 
                java.text.*,com.awsd.personnel.*,com.awsd.school.*"
       isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>
       
<esd:SecurityCheck permissions='PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST' />

<%
  User usr = (User) session.getAttribute("usr");
  PPGP ppgp = null;
  PPGPGoal goal = null;
  PPGPTask task = null;
  Personnel p = null;
%>
<html>
  <head>
    <title>PGP Program Specialist Summary</title>
    <link rel="stylesheet" href="css/summary.css">
    <STYLE TYPE="text/css">
      br.pagebreak {page-break-before: always}
    </STYLE> 
  </head>

  <body topmargin="10" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" onload="window.print();">

  <%
    for(School s : new Schools()) {
      p = s.getSchoolPrincipal();
      
      if(p == null)
        continue;
  %>  <table align="center" width="95%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td width="234" valign="top" align="left">
            <img src="images/progspeclpsummary.png"><BR>
          </td>
          <td width="*" valign="middle" align="left">
            <table>
              <tr>
                <td>
                  <b>School:</b>
                </td>
                <td>
                  <%=s.getSchoolName()%>
                </td>
              </tr>
              
              <tr>
                <td>
                  <b>Principal:</b>
                </td>
                <td>
                  <%=p.getFullName()%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
  
      <table align="center" width="95%" cellpadding="5" cellspacing="1" border="0">
        <tr>
          <td width="20%" valign="middle" align="center">
            <span class="title">Teacher Name</span><BR>
          </td>
          <td width="25%" valign="middle" align="center">
            <span class="title">PD Requested</span><BR>
          </td>
          <td width="20%"  valign="middle" align="center">
            <span class="title">School Support</span><BR>
          </td>
          <td width="20%"  valign="middle" align="center">
            <span class="title">District Support</span><BR>
          </td>
          <td width="15%" valign="middle" align="center">
            <span class="title">Completion Date</span><BR>
          </td>
        </tr>
        <%
        	for(Personnel teacher : PersonnelDB.getPersonnelList(s)) {
                            ppgp = teacher.getPPGP();
        %>  <tr>
              <td  colspan="5" valign="middle" align="left">
                <span class="title"><%=teacher.getFullName()%></span>
              </td>
            </tr>
            <%if(ppgp==null){%>
              <tr>
                <td width="20%" valign="middle" align="center">
                  <span class="text" align="center"><B><font color="#FF0000">NO PPGP SUBMITTED.</font></B></span><BR>
                </td>
                <td colspan="4" valign="middle">
                  <span class="text">&nbsp;</span><BR>
                </td>
              </tr>
            <%}else{ 
                for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
                  goal = entry.getValue();
            %>    <tr>
                    <td colspan="5"  valign="top">
                      <table>
                        <tr>
                          <td>
                            <span class="title">Goal:&nbsp;</span>
                          </td>
                          <td colspan="4">
                            <span class="text"><%=goal.getPPGPGoalDescription()%></span>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <%for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()) {
                      task = g_entry.getValue();
                  %>  <tr>
                        <td width="20%"  valign="middle" align="center">
                          <span class="text">&nbsp;</span><BR>
                        </td>
                        <td width="25%"  valign="middle">
                          <span class="text"><%=task.getDescription()%></span><BR>
                        </td>
                        <td width="20%"  valign="middle">
                          <span class="text"><%=task.getSchoolSupport()%></span><BR>
                        </td>
                        <td width="20%"  valign="middle">
                          <span class="text"><%=task.getDistrictSupport()%></span><BR>
                        </td>
                        <td width="15%"  valign="middle" align="center">
                          <span class="text"><%=task.getCompletionDate()%></span><BR>
                        </td>
                      </tr>
                  <%}
                }
              }%>
              <tr>
                <td  colspan="5" bgcolor="#E1E1E1" valign="middle" align="left">
                  &nbsp;
                </td>
              </tr>
          <%}%>
      </table>
  
      <table height="5" width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td width="100%" valign="bottom" bgcolor="#FFCC00">
            <img src="images/spacer.gif" width="1" height="5"><BR>
          </td>
        </tr>
        <tr><td width="100%"><br class="pagebreak"></td></tr>
      </table>
    <%}%>  
  </body>
</html>