<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.util.*,
                  com.esdnl.personnel.v2.site.constant.*,
                  com.esdnl.personnel.v2.model.recognition.bean.*,
                  com.esdnl.personnel.v2.model.recognition.constant.*,
                  com.esdnl.personnel.v2.model.sds.bean.*,
                  com.awsd.security.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  User usr = (User) session.getAttribute("usr");
  RecognitionRequestBean rbean = (RecognitionRequestBean) session.getAttribute("RECOGNITION_REQUEST");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - Personnel Package (v2.0)</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import '/MemberServices/Personnel/v2.0/css/home.css';</style>
  
  <script type='text/javascript'>
    function openWindow(id,url,w,h, scroll)
    {
      var winl = (screen.width-w)/2;
      var wint = (screen.height - h - 25 )/2;

      window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
  }

  </script>
  
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
                          <img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="3" height="1"><span class="displayBoxTitle" >Recognition Request > Add Request</span>
                        </td>
                        <td width="*" height="24" align="right" valin="middle" style="background-color: #EBEBEB;">
                          <img src="/MemberServices/Personnel/v2.0/images/help_icon.gif" style="cursor: help;" alt="Administration Welcome."><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/minimize_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><img src="/MemberServices/Personnel/v2.0/images/close_icon.gif"><img src="/MemberServices/Personnel/v2.0/images/spacer.gif" width="2" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2" class="content">
                          <!-- CONTENT BEGINS HERE -->
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td width="100%" class="subtitle">Add Recognition Request</td>
                            </tr>
                            <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){%>
                              <tr style="padding-top:5px;padding-bottom:5px;">
                                <td width="100%" align="center" style='color:#FF0000;font-weight:bold;'><%=(String)request.getAttribute("msg")%></td>
                              </tr>
                            <%}%>
                            <tr>
                              <td width="100%">
                                <form name="frmAddRequest" action="addRequest.html" method='post'>
                                  <input type="hidden" name="op" id="op" value="PRESUBMIT">
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                      <td width="35%" class="Label">Request Class:</td>
                                      <td width="*" align="left"><personnel:RecognitionRequestClass id='request_class' value='<%=(rbean != null)?rbean.getType():null%>' /></td>
                                    </tr>
                                    <tr style="padding-top:5px;">
                                      <td width="35%" class="Label">Employee(s):</td>
                                      <td width="*" align="left"><input type="button" value="add" onclick="openWindow('ADD_ENTITY', 'addEntity.html', 390, 350, 1);"></td>
                                    </tr>
                                    <%
                                      if((rbean != null)&&(rbean.getEntities().length > 0))
                                      {
                                        Object[] entities= rbean.getEntities();
                                        
                                        out.println("<tr style=\"padding-top:5px;\">");
                                        out.println("<td colspan=\"2\" align=\"left\" style=\"padding-left:25px;\">");
                                        out.println("<ul>");
                                        for(int i=0; i < entities.length; i++)
                                        {
                                          if(entities[i] instanceof EmployeeBean)
                                            out.println("<li>" + ((EmployeeBean)entities[i]).getFullnameReverse() + "</li>");
                                        }
                                        out.println("</ul>");
                                        out.println("</td>");
                                        out.println("</tr>");
                                      }
                                    %>
                                    <tr style="padding-top:5px;">
                                      <td colspan="2" class="Label">Recognition Description:</td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="left"><textarea name="desc" id="desc" cols="60" rows="10"><%=((rbean != null) && !StringUtils.isEmpty(rbean.getDescription()))?rbean.getDescription():""%></textarea></td>
                                    </tr>
                                    <tr style="padding-top:5px;">
                                      <td colspan="2" align="center"><input type="button" value="Submit" onclick="document.forms[0].op.value='SUBMIT';document.forms[0].submit();"></td>
                                    </tr>
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
