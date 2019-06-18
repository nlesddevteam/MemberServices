<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.school.*,
                  com.esdnl.sacs.dao.*,
                  com.esdnl.sacs.model.bean.*" %>
                  
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/sacs.tld" prefix="sacs" %>

<%
  User usr = (User) session.getAttribute("usr");
%>

<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Eastern School District - Safe and Caring Schools Initiatives</title>
    <link href="css/safeschools.css" rel="stylesheet" type="text/css">
  </head>
  
  <body>
    
    <esd:SecurityCheck permissions="SACS-VIEW" />
    
    <table width="800" border="0" cellspacing="0" cellpadding="0" align="center" style="border: thin solid #00407A; background-color: White;">
      <tr>
        <td>
          <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" style="font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; font-size: 10px; color: White; background-color: #00407A;">
            <tr>
              <td><div align="left" class="toptext">Welcome <%=usr.getPersonnel().getFirstName()%></div></td>
              <td><div align="right" class="toptext"><a href="index.jsp" class="topmenu">Home</a> | <a href="index.jsp" class="topmenu">Help</a>&nbsp;</div></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr style="background: url(images/topbackgrnd.gif);">
        <td>
          <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
              <td><img src="images/safeandcaringschools.gif" alt="" width="394" height="85" border="0"></td>
              <td><img src="images/esdnl.gif" alt="" width="155" height="85" border="0" align="right"></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td>
          <table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="mainbody">
            <tr>
              <td>
                <!-- Content goes here -->
                
                <sacs:SummaryTable />
                
                <!-- End content-->
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr bgcolor="#00407A">
        <td colspan="2"><div align="center" class="copyright">&copy; 2008 Eastern School District. All Rights Reserved.</div></td>
      </tr>
    </table>
  </body>
  
</html>
