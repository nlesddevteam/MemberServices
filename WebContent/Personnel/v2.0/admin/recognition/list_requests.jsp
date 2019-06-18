<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.util.*,
                  com.esdnl.personnel.v2.site.constant.*,
                  com.esdnl.personnel.v2.model.recognition.bean.*,
                  com.esdnl.personnel.v2.model.recognition.constant.*,
                  com.awsd.security.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  User usr = (User) session.getAttribute("usr");
  RecognitionRequestBean[] reqs = (RecognitionRequestBean[])request.getAttribute("REQUESTS");
  DecimalFormat df = new DecimalFormat("R00000");
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  
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

  <esd:SecurityCheck permissions="PERSONNEL-RECOGNITION-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="760" cellpadding="0" cellspacing="0" border="0" style="border:solid 1px #FFB700;" align="center">
    <tr>
      <td>   
        <jsp:include page="../../includes/top_nav.jsp" flush="true">
          <jsp:param name="activeTab" value='<%=TabConstants.TAB_ADMIN%>'/>
          <jsp:param name="activeSubTab" value='<%=TabConstants.TAB_ADMIN_RECOGNITION_REQUEST%>'/>
        </jsp:include>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="../../includes/side_nav.jsp" flush="true">
                    <jsp:param name="activeTab" value='<%=TabConstants.TAB_ADMIN%>'/>
                    <jsp:param name="activeSubTab" value='<%=TabConstants.TAB_ADMIN_RECOGNITION_REQUEST%>'/>
                  </jsp:include>
                  <td width="551" align="left" valign="top">		
                    <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="1" height="15"><BR>
                    <table width="540" cellpadding="0" cellspacing="0" border="0" class="box">
                      <tr class="header">
                        <td width="240" height="24" align="left" valin="middle" class="title">
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="3" height="1"><span class="displayBoxTitle" >Recognition Request > List Requests</span>
                        </td>
                        <td width="*" height="24" align="right" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/help_icon.gif" style="cursor: help;" alt="Administration Welcome."><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/minimize_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/close_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" class="content">
                          <!-- CONTENT BEGINS HERE -->
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <%if(request.getAttribute("TITLE") != null){%>
                              <tr>
                                <td width="100%" class="subtitle"><%=(String)request.getAttribute("TITLE")%></td>
                              </tr>
                            <%}%>
                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){%>
                              <tr style="padding-top:5px;padding-bottom:5px;">
                                <td width="100%" align="center" style='color:#FF0000;font-weight:bold;'><%=(String)request.getAttribute("msg")%></td>
                              </tr>
                            <%}%>
                            <tr style="padding-top:10px;">
                              <td width="100%">
                                <form name="frmAddRequest" action="addRequest.html" method='post'>
                                  <input type="hidden" name="op" id="op" value="">
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                      <td width="15%" class="Label">Request Id:</td>
                                      <td width="15%" align="left" class="Label">Status:</td>
                                      <td width="20%" align="left" class="Label">Action Date:</td>
                                      <td width="20%" align="left" class="Label">Action By:</td>
                                      <td width="*">&nbsp;</td>
                                    </tr>
                                    <%if((reqs != null)&&(reqs.length > 0)){%>
                                      <%for(int i=0; i < reqs.length; i++){%>
                                        <tr style="padding-top:5px;background-color:<%=((i%2)!=0)?"#e0e0e0":"#FFFFFF"%>">
                                          <td width="15%" class="content"><%=df.format(reqs[i].getId())%></td>
                                          <td width="20%" align="left" class="content"><%=reqs[i].getCurrentStatus().getDescription()%></td>
                                          <td width="20%" align="left" class="content"><%=sdf.format(((HistoryBean)reqs[i].getHistory().get(reqs[i].getCurrentStatus())).getActionDate())%></td>
                                          <td width="20%" align="left" class="content"><%=((HistoryBean)reqs[i].getHistory().get(reqs[i].getCurrentStatus())).getActionedByObject().getFullNameReverse()%></td>
                                          <td width="*" align="left"><input type="button" value="view" onclick="document.location.href='viewRequest.html?id=<%=reqs[i].getId()%>';"></td>
                                        </tr>
                                      <%}%>
                                    <%}else{%>
                                      <tr style="padding-top:5px;background-color:#FFFFFF;">
                                          <td colspan="5" class="content">No requests available.</td>
                                      </tr>
                                    <%}%>
                                  </table>
                                </form>
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
      <td style="border-top:solid 1px #FFB700;">
        <jsp:include page="../../includes/footer.jsp" flush="true" />
      </td>
    </tr>
  </table>
</body>
</html>
