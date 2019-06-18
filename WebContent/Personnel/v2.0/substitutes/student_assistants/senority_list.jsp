<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.v2.model.sds.constant.PositionConstant,
                  com.esdnl.personnel.v2.model.sds.constant.LocationConstant,
                  com.esdnl.personnel.v2.site.constant.*,
                  com.esdnl.personnel.v2.utils.*,
                  com.awsd.security.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  User usr = (User) session.getAttribute("usr");
  
  String group = request.getParameter("group");
  if(group == null)
    group = "A";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - Personnel Package (v2.0)</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import '/MemberServices/Personnel/v2.0/css/home.css';</style>
  
</head>
<body>

  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="760" cellpadding="0" cellspacing="0" border="0" style="border:solid 1px #FF3E00;" align="center">
    <tr>
      <td>   
        <jsp:include page="../../includes/top_nav.jsp" flush="true">
          <jsp:param name="activeTab" value='<%=TabConstants.TAB_SUBSTITUTES%>'/>
          <jsp:param name="activeSubTab" value='<%=TabConstants.TAB_SUBSTITUTES_STUDENT_ASSISTANT%>'/>
        </jsp:include>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="../../includes/side_nav.jsp" flush="true"> 
                    <jsp:param name="activeTab" value='<%=TabConstants.TAB_SUBSTITUTES%>'/>
                    <jsp:param name="activeSubTab" value='<%=TabConstants.TAB_SUBSTITUTES_STUDENT_ASSISTANT%>'/>
                  </jsp:include>
                  <td width="551" align="left" valign="top" style="padding-bottom:10px;">		
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="15"><BR>
                    <table width="540" cellpadding="0" cellspacing="0" border="0" class="box">
                      <tr class="header">
                        <td width="240" height="24" align="left" valign="middle" class="title">
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="3" height="1"><span class="displayBoxTitle">Tasks &gt; Senority List</span>
                        </td>
                        <td width="*" height="24" align="right" valign="middle">
                          <img src="/MemberServices/Personnel/v2.0/images/help_icon.gif" style="cursor: help;" alt="Administration Welcome."><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/minimize_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/close_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" style="padding:10px;" class="content">
                          <!-- CONTENT BEGINS HERE -->
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td width="100%" class="subtitle">Senority List</td>
                            </tr>
                            <tr>
                              <td width="100%">
                                <personnel:EmployeeCards type='<%=PositionConstant.CASUAL_STUDENT_ASSISTANT%>' 
                                                         schoolYear='<%=StringUtils.getSchoolYear(Calendar.getInstance().getTime())%>'
                                                         location='<%=LocationConstant.SUBSTITUTE_EASTERN_REGION%>'
                                                         bySenority='true' />
                              </td>
                            </tr>
                          </table>
                          <!-- CONTENT ENDS HERE -->
                        </td>
                      </tr>
                    </table>
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="30"><BR>
                  </td>						
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td style="border-top:solid 1px #FF3E00;">
        <jsp:include page="../../includes/footer.jsp" flush="true" />
      </td>
    </tr>
  </table>
</body>
</html>
