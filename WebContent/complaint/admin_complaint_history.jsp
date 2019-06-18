<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.*,
                 java.util.*,
                 com.awsd.school.*,
                 com.esdnl.util.*,
                 com.esdnl.complaint.model.constant.*,
                 com.esdnl.complaint.model.bean.*"
         isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/complaints.tld" prefix="c" %>

<%
  ComplaintBean complaint = (ComplaintBean) request.getAttribute("COMPLAINT_BEAN");
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
%>
<html>
<head>
  <title>Eastern School District -- How We Help -- Complaint Form</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <link href="css/complaint.css" rel="stylesheet" type="text/css">
  <script Language="JavaScript" Type="text/javascript" src="js/common.js"></script>
  <script language="JavaScript" src="js/tree.js"></script>
  <script language="JavaScript" src="js/tree_tpl.js"></script>
</head>
<body bgcolor="#ffffff">
  <esd:SecurityCheck permissions="COMPLAINT-MONITOR,COMPLAINT-ADMIN,COMPLAINT-VIEW" />
  <jsp:include page="side_nav_tree_items.jsp" flush="true"/>
  <a name="top"></a>
  <table width="760" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #B4B4B4;">
    
    <tr>
      <td valign="top" bgcolor="#FFFFFF">
        <table width="760" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top" style="padding:5px;">
              <table width="760" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td valign="top" >
                    <table width="100%" cellpadding="0" cellspacing="0" border="0" valign="top">
                      <tr>
                        <td colspan='2' style="border-bottom:solid 1px #000000;"><img src="images/header2.gif"><br></td>
                      </tr>
                      <tr>
                        <td colspan='2'><img src="images/spacer.gif" height="5"></td>
                      </tr>
                      <%if((complaint==null)&&(request.getAttribute("msg") != null)){%>
                        <tr>
                          <td colspan='2'>
                            <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                                </tr>
                              </table>
                            </p>
                          </td>
                        </tr>
                      <%}%>
                      <tr>
                        <td valign="top" style="background-color:#f0f0f0;border-right:solid 1px #000000;" width="35%">
                          <p style="padding-left:10px;">
                            <script language="JavaScript">
                              new tree (TREE_ITEMS, tree_tpl);
                            </script>
                          </p>
                        </td>
                        <td valign="top" width="*" style="height:450px; padding:5px;">
                          <%if(complaint != null) {%>
                            <form method="POST" action="viewAdminComplaintSummary.html" name="theForm">
                              <input type="hidden" name="id" value="<%=complaint.getId()%>">
                              
                              <%if(request.getAttribute("msg") != null){%>
                                <p align="center" style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                  <table cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                                    </tr>
                                  </table>
                                </p>
                              <%}%>
                      
                              <table cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                  <td width="100%" align="right"><input type="button" value="Back" onclick="document.theForm.submit();"></td>
                                </tr>
                              </table>
                              
                              <fieldset><legend><font style="color:#00407A;font-weight:bold;font-size:20px;FONT-FAMILY: T, 'Times New Roman', Times, serif;"><i>Complaint History</i></font></legend>
                                <table cellspacing="6">
                                  <tr>
                                    <TH style="vertical-align: top;">Action</TH>
                                    <TH style="vertical-align: top;">By Who</TH>
                                    <TH style="vertical-align: top;">To Who</TH>
                                    <TH style="vertical-align: top;">Timestamp</TH>
                                  </tr>
                                  <%for(int i=0; i < complaint.getHistory().length; i++){%>
                                    <tr>
                                      <td><%=complaint.getHistory()[i].getAction()%></td>
                                      <%if(complaint.getHistory()[i].getAction().equals(ComplaintStatus.SUBMITTED)){%>
                                        <td><%=complaint.getFullName()%></td>
                                      <%}else{%>
                                        <td><%=complaint.getHistory()[i].getByWho().getFullName()%></td>
                                      <%}%>
                                      <td><%=(complaint.getHistory()[i].getToWho() != null)?complaint.getHistory()[i].getToWho().getFullName():"&nbsp;"%></td>
                                      <td><%=sdf.format(complaint.getHistory()[i].getHistoryDate())%></td>
                                    </tr>
                                  <%}%>
                                </table>
                              </fieldset>
                              <br>
                              <table cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                  <td width="100%" align="center"><input type="button" value="Back" onclick="document.theForm.submit();"></td>
                                </tr>
                              </table>
                            </form>
                          <%}else{%>
                            <p align="center" style="padding-top:25px;">Choose a complaint to view from the list on the left.</p>
                          <%}%>
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
    <tr>
      <td valign="top" align="center" class="btmtxt" style="background-color:#B4B4B4;" >
        Copyright 2007 Eastern School District.All Rights Reserved.
      </td>
    </tr>
  </table>
</body>
</html>
