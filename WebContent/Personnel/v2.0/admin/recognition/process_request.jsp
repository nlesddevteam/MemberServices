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
  TemplateBean template = (TemplateBean)session.getAttribute("TEMPLATE");
  DecimalFormat df = new DecimalFormat("R000000");
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
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="3" height="1"><span class="displayBoxTitle" >Recognition Request > View Request</span>
                        </td>
                        <td width="*" height="24" align="right" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/help_icon.gif" style="cursor: help;" alt="Administration Welcome."><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/minimize_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/close_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" class="content">
                          <!-- CONTENT BEGINS HERE -->
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <!--
                            <tr>
                              <td width="100%" class="subtitle">Recognition Requests Pending Approval</td>
                            </tr>
                            -->
                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){%>
                              <tr style="padding-top:5px;padding-bottom:5px;">
                                <td width="100%" align="center" style='color:#FF0000;font-weight:bold;'><%=(String)request.getAttribute("msg")%></td>
                              </tr>
                            <%}%>
                            <tr style="padding-top:10px;">
                              <td width="100%">
                                <form name="processRequestForm" id="processRequestForm" action="processRequest.html">
                                  <input type="HIDDEN" name="op" value="RETRIEVE_TEMPLATE">
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                      <td width="35%" class="Label">Request Id:</td>
                                      <td width="*" align="left" class="content"><%=df.format(req.getId())%></td>
                                    </tr>
                                    <tr style="padding-top:5px;">
                                      <td width="35%" class="Label" valign="top">Employee(s):</td>
                                      <td  width="*" class="content" valign="top">
                                        <%
                                          Object[] entities = req.getEntities();
                                          for(int i=0; i < entities.length; i++)
                                          {
                                            if(entities[i] instanceof EmployeeBean)
                                              out.println(((EmployeeBean)entities[i]).getFullnameReverse() + "<BR>");
                                          }
                                        %> 
                                      </td>
                                    </tr>
                                    <tr style="padding-top:5px;">
                                      <td width="35%" class="Label" valign="top">Recognition Description:</td>
                                      <td width="*" class="content" valign="top"><%=req.getDescription()%></td>
                                    </tr>
                                    <tr style="padding-top:5px;">
                                      <td width="35%" class="Label" valign="top">Template:</td>
                                      <td width="*" class="content" valign="top">
                                        <personnel:Templates id='template_id' value='<%=template%>' />
                                        <input type="button" value="get" onclick="document.forms[0].op.value='RETRIEVE_TEMPLATE';document.forms[0].submit();">
                                      </td>
                                    </tr>
                                    <%if(template != null){%>
                                      <tr style="padding-top:5px;">
                                        <td colspan="2" class="Label" valign="top">Letter Content:</td>
                                      </tr>
                                      <tr style="padding-top:2px;">
                                        <td colspan="2" class="content" valign="top">
                                          <textarea id="letter" name="letter" style="width:100%;height:300px;"><%=template.getText()%></textarea>
                                        </td>
                                      </tr>
                                      <tr style="padding-top:2px;">
                                        <td colspan="2" class="content" valign="top" align="middle">
                                          <input type="button" value="Preview Letters" onclick="document.forms[0].op.value='PREVIEW_LETTERS';document.forms[0].submit();">
                                        </td>
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
