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
</head>
<body bgcolor="#ffffff">
  <%if(request.getAttribute("RELOAD_PARENT") != null){%>
    <script type="text/javascript">
      opener.document.location.reload();
      self.close();
    </script>
  <%}%>
  <esd:SecurityCheck permissions="COMPLAINT-MONITOR,COMPLAINT-ADMIN,COMPLAINT-VIEW" />
  <a name="top"></a>
  <table width="450" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #B4B4B4;">
    <tr>
      <td valign="top" bgcolor="#FFFFFF" width="100%">
        <table width="450" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top" style="padding:5px;" width="100%">
              <table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td valign="top" width="100%">
                    <table width="100%" cellpadding="0" cellspacing="0" border="0" valign="top">
                      <tr>
                        <td style="border-bottom:solid 1px #000000;" width="100%"><img src="images/header2.gif"><br></td>
                      </tr>
                      <tr>
                        <td width="100%"><img src="images/spacer.gif" height="5"></td>
                      </tr>
                      <%if((complaint==null)&&(request.getAttribute("msg") != null)){%>
                        <tr>
                          <td width="100%">
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
                        <td valign="top" style="padding:5px;" width="100%">
                            <form method="POST" action="addComplaintDocument.html" name="theForm"  ENCTYPE="multipart/form-data">
                              <%if(complaint != null) {%>
                                <input type="hidden" name="id" value="<%=complaint.getId()%>">
                                <input type="hidden" name="CONFIRMED" value="true">
                                
                                <%if(request.getAttribute("msg") != null){%>
                                  <p align="center" style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </p>
                                <%}%>
                                
                                <fieldset><legend><font style="color:#00407A;font-weight:bold;font-size:20px;FONT-FAMILY: T, 'Times New Roman', Times, serif;"><i>Add Document</i></font></legend>
                                  <table cellspacing="6" width="100%">
                                    <tr>
                                      <TD style="vertical-align: top;" width="150" align="right" style="font-weight:bold;">Title:</TD>
                                      <TD width="*"><input type="textbox" name="doc_title" id='doc_title' class='requiredInputBox' size="40"></TD>
                                    </tr>
                                    <tr>
                                      <TD style="vertical-align: top;" width="150" align="right" style="font-weight:bold;">Document:</td>
                                      <td style="vertical-align: top;"><input type="file" name="doc_file" size="40" class='requiredInputBox'></td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="center">
                                        <input type="submit" value="Add Document" style="font-size:10px;">
                                      </td>
                                    </tr>
                                  </table>
                                </fieldset>
                              <%}else{%>
                                <p align="center" style="padding-top:25px;">Choose a complaint to view from the list on the left.</p>
                              <%}%>
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
    <tr>
      <td valign="top" align="center" class="btmtxt" style="background-color:#B4B4B4;" >
        Copyright 2007 Eastern School District.All Rights Reserved.
      </td>
    </tr>
  </table>
</body>
</html>