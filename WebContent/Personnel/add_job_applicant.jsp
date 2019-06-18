<%@ page language="java"
         import="java.util.*,
                  java.text.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<%
  String comp_num = (String)request.getParameter("comp_num");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'includes/home.css';</style>
  <script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>  
  <script type="text/javascript">
    function add_applicant()
    {
      if(document.forms[0].sin.value != "")
      {
        opener.document.location.href = 'addJobApplicant.html?comp_num=<%=comp_num%>&sin=' + document.forms[0].sin.value ;
        self.close();
      }
      else
      {
        alert('SIN is a required field.');
      }
    }
  </script>
</head>
<body>

  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="350" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700; ">
    <tr>
      <td>   
        <table width="350" cellpadding="0" cellspacing="0" border="0">
          <tr height="79">
            <td width="350" align="left" valign="top" style="background-color:#FFB700;">
              <table width="350" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="154" align="left" valign="top">
                    <img src="images/admin_top_logo.gif"><BR>
                  </td>
                  <td width="150" align="left" valign="middle">		
                    <table cellpadding="0" cellspacing="0">
                      <tr><td align="center" style="padding-bottom:5px;"><span class="displayWhiteDate14px">Add Job Applicant</span></td></tr>
                      <tr><td align="center"><span class="displayWhiteDate"><%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%> </span></td></tr>
                    </table>
                  </td>
                </tr>
              </table>				
            </td>
          </tr>
        </table>
        <table width="350" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="350" align="left" valign="top">
              <table width="350" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="350" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="350" align="left" valign="top" style="padding-top:8px;">
                          <form>
                            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                              <tr>
                                <td width="391" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                              </tr>
                              <tr style="padding-top:5px;">
                                <td align="center">
                                  <table width="85%">
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width="100"><span class="requiredStar">*&nbsp;</span>COMP. NUMBER:</td>
                                      <td align="left" style='color:#FF0000;font-weight:bold;'><%=request.getParameter("comp_num")%></td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width="100"><span class="requiredStar">*&nbsp;</span>SIN:</td>
                                      <td align="left"><input type="text" name="sin" id="sin" style="width:175px;" class="requiredInputBox"></td>
                                    </tr>
                                  </table>
                                </td>
                              </tr>
                              <tr>
                                <td class="displayBoxTitle" align="center" style="padding-top:10px; color:#333333;">
                                    <input type="button" value="Submit" onclick="add_applicant();">
                                    <img src="images/spacer.gif" width="15px;" height="1px">
                                    <input type="button" value="Cancel" onclick="window.close();">
                                </td>
                              </tr>
                            </table>
                          </form>
                        </td>
                      </tr>
                    </table>
                  </td>						
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <jsp:include page="footer.jsp" flush="true" >
    <jsp:param name="width" value="350" />
  </jsp:include>
</body>
</html>
