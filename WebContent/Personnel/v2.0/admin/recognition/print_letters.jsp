<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.util.*,
                  com.esdnl.personnel.v2.site.constant.*,
                  com.esdnl.personnel.v2.model.sds.bean.*,
                  com.esdnl.personnel.v2.model.recognition.bean.*,
                  com.esdnl.personnel.v2.model.recognition.constant.*,
                  com.awsd.security.*"  
          isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  User usr = (User) session.getAttribute("usr");
  
  RecognitionRequestBean req = (RecognitionRequestBean)session.getAttribute("RECOGNITION_REQUEST");
  DecimalFormat df = new DecimalFormat("R000000");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - Personnel Package (v2.0)</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import '/MemberServices/Personnel/v2.0/css/home.css';</style>
  <STYLE TYPE="text/css">
      br.pagebreak {page-break-before: always}
    </STYLE> 
</head>
<body style="margin-top:100px;margin-left:40px;margin-right:120px;margin-bottom:30px;" onload="self.print();">

  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="8.5in" cellpadding="0" cellspacing="0" border="0" align="center">
    <tr>
      <td>   
        <table width="8.5in" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="8.5in" align="left" valign="top">
              <table width="8.5in" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="8.5in" align="left" valign="top">		
                    <table width="8.5in" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td colspan="2" class="content">
                          <!-- CONTENT BEGINS HERE -->
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr style="padding-top:10px;">
                              <td width="100%">
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                      <td width="100%" class="content" valign="top">
                                        <%
                                          if(req != null)
                                          {
                                            String[] letters = req.process();
                                            
                                            for(int i=0; i < letters.length; i++)
                                              out.println(StringUtils.encodeHTML(letters[i]) + "<br class='pagebreak'>");
                                          }
                                        %>
                                      </td>
                                    </tr>
                                  </table>
                              </td>
                            </tr>
                          </table>
                          <!-- CONTENT ENDS HERE -->
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
</body>
</html>
