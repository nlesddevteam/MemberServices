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
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td colspan='2' style="border-bottom:solid 1px #000000;"><img src="images/header2.gif"><br></td>
                      </tr>
                      <tr>
                        <td colspan='2'><img src="images/spacer.gif" height="5"></td>
                      </tr>
                      <%if((complaint == null) && (request.getAttribute("msg") != null)){%>
                        <tr>
                          <td colspan='2'>
                            <div style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                                </tr>
                              </table>
                            </div>
                          </td>
                        </tr>
                      <%}%>
                      <tr>
                        <td valign="top" style="background-color:#f0f0f0;border-right:solid 1px #000000;" width="35%">
                          <p style="padding-left:5px;">
                            <script language="JavaScript">
                              new tree (TREE_ITEMS, tree_tpl);
                            </script>
                          </p>
                        </td>
                        <td valign="top" width="*" style="height:450px; padding:5px;">
                          <%if(complaint != null) {%>
                            <form method="POST" action="" name="theForm">
                              <input type="hidden" name="id" value="<%=complaint.getId()%>">
                              
                              <%if(request.getAttribute("msg") != null){%>
                                <div align="center" style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                  <table cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                                    </tr>
                                  </table>
                                </div>
                              <%}%>
                      
                              <fieldset>
                                <legend><font style="color:#00407A;font-weight:bold;font-size:20px;FONT-FAMILY: T, 'Times New Roman', Times, serif;"><i>Administration</i></font></legend>
                                <table cellspacing="0" cellpadding="0" border="0" width="100%" >
                                  <tr>
                                    <td style="font-size:10px;font-weight:bold;color:#FF0000;background-color:#FFFFFF;" width="40%">
                                      CURRENT STATUS: <%=complaint.getCurrentStatus()%>
                                      <%if(complaint.getCurrentStatus().equals(ComplaintStatus.ASSIGNED)){%>
                                        <br><span style="color:#000000;">ASSIGNED TO: </span><span style="color:#000000;font-weight:normal;"><%=complaint.getAssignedTo().getFullName()%></span>
                                      <%}%>
                                    </td>
                                    <td width="*" align="right" >
                                      <table width="95%" cellspacing="0" cellpadding="4" border="0">
                                        <tr>
                                          <td width="100%" align="right">
                                            <table width="100%" cellspacing="0" cellpadding="2">
                                              <tr>
                                                <td width="80%" align="right">
                                                  <%if(!(complaint.getCurrentStatus().equals(ComplaintStatus.RESOLVED)
                                                    ||complaint.getCurrentStatus().equals(ComplaintStatus.NO_RESOLUTION))){%>
                                                    <input type="button" value="Assign" style="font-size:10px;" onclick="openWindow('ASSIGN_COMPLAINT', 'assignComplaint.html?id=<%=complaint.getId()%>', 475, 275, 0);">
                                                  <%}%>
                                                  <input type="button" value="History" style="font-size:10px;" onclick="document.forms[0].action='viewAdminComplaintHistory.html';document.forms[0].submit();"><br>
                                                  
                                                  
                                                </td>
                                                <td width="*" style="border-left:solid 1px #e0e0e0;">
                                                  <%if(!(complaint.getCurrentStatus().equals(ComplaintStatus.REJECTED)
                                                    ||complaint.getCurrentStatus().equals(ComplaintStatus.RESOLVED)
                                                    ||complaint.getCurrentStatus().equals(ComplaintStatus.NO_RESOLUTION))){%>
                                                    <input type="button" value="Reject" style="color:#FF0000;font-size:10px;" onclick="openWindow('REJECT_COMPLAINT', 'rejectComplaint.html?id=<%=complaint.getId()%>', 475, 275, 0);"><BR>
                                                    <input type="button" value="Resolved" style="color:#FF0000;font-size:10px;" onclick="openWindow('RESOLVE_COMPLAINT', 'changeComplaintStatus.html?id=<%=complaint.getId()%>&status=5', 475, 275, 0);"><BR>
                                                    <input type="button" value="No Resolution" style="color:#FF0000;font-size:10px;" onclick="openWindow('NO_RESOLUTION_COMPLAINT', 'changeComplaintStatus.html?id=<%=complaint.getId()%>&status=6', 475, 275, 0);"><BR>
                                                  <%}%>
                                                  <input type="button" value="Delete" style="color:#FF0000;font-size:10px;" onclick="openWindow('DELETE_COMPLAINT', 'deleteComplaint.html?id=<%=complaint.getId()%>', 475, 275, 0);">
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td width="150" style="color:#00407A;font-weight:bold;font-size:14px;FONT-FAMILY: T, 'Times New Roman', Times, serif;">
                                      Comments:
                                    </td>
                                    <td width="*" align="right" style='padding-right:28px;'>
                                      <input type="button" value="Add" style="font-size:10px;" onclick="openWindow('ADD_COMMENT', 'addComplaintComment.html?id=<%=complaint.getId()%>', 475, 375, 0);">
                                    </td>
                                  </tr>
                                  <tr>
                                    <%if((complaint.getComments() == null)||(complaint.getComments().length <=0)){%>
                                      <td colspan="2" style='padding-left:5px;'>None on record.</td>
                                    <%}else{%>
                                      <td colspan="2">
                                        <table width="100%" cellspacing="0" cellpadding="2">
                                          <%for(int i=0; i < complaint.getComments().length; i++){%>
                                            <tr>
                                              <td width="100%" align="left" class="small_text_2" style="padding-left:15px;">
                                                Entered On <%=complaint.getComments()[i].getFormatedSubmittedDate()%> by <%=complaint.getComments()[i].getMadeBy().getFullNameReverse()%>
                                              </td>
                                            </tr>
                                            <tr>
                                              <td width="100%" align="left" style="padding-left:15px;">
                                                <%=StringUtils.encodeHTML(complaint.getComments()[i].getComment())%>
                                              </td>
                                            </tr>
                                            <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="5"></td></tr>
                                          <%}%>
                                        </table>
                                      </td>
                                    <%}%>  
                                  </tr>
                                  <tr>
                                    <td width="150" style="color:#00407A;font-weight:bold;font-size:14px;FONT-FAMILY: T, 'Times New Roman', Times, serif;">
                                      Documents:
                                    </td>
                                    <td width="*" align="right" style='padding-right:28px;'>
                                      <input type="button" value="Add" style="font-size:10px;" onclick="openWindow('ADD_DOCUMENT', 'addComplaintDocument.html?id=<%=complaint.getId()%>', 475, 375, 0);">
                                    </td>
                                  </tr>
                                  <tr>
                                    <%if((complaint.getDocuments() == null)||(complaint.getDocuments().length <=0)){%>
                                      <td colspan="2" style='padding-left:5px;'>None on record.</td>
                                    <%}else{%>
                                      <td colspan="2">
                                        <table width="100%" cellspacing="0" cellpadding="2">
                                          <%for(int i=0; i < complaint.getDocuments().length; i++){%>
                                            <tr>
                                              <td width="100%" align="left" class="small_text_2" style="padding-left:15px;">
                                                Uploaded On <%=complaint.getDocuments()[i].getFormattedUploadDate()%> by <%=complaint.getDocuments()[i].getUploadedBy().getFullNameReverse()%>
                                              </td>
                                            </tr>
                                            <tr>
                                              <td width="100%" align="left" style="padding-left:15px;">
                                                <a href='documentation/<%=complaint.getDocuments()[i].getFilename()%>' target="_blank"><%=complaint.getDocuments()[i].getTitle()%></a>
                                              </td>
                                            </tr>
                                            <tr><td colspan="2"><img src="images/spacer.gif" width="1" height="5"></td></tr>
                                          <%}%>
                                        </table>
                                      </td>
                                    <%}%>  
                                  </tr>
                                </table>
                              </fieldset>
                              <br>
                              <fieldset><legend><font style="color:#00407A;font-weight:bold;font-size:20px;FONT-FAMILY: T, 'Times New Roman', Times, serif;"><i>Complaint Summary</i></font></legend>
                              <fieldset>
                                <legend><b><u><font color="#333333">Name</font></u></b></legend>
                                <table cellspacing="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">First Name:</td><td style="vertical-align: top;"><%=complaint.getFirstName()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Middle Inital:</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getMiddleInitial())?complaint.getMiddleInitial():""%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Last Name:</td><td style="vertical-align: top;"><%=complaint.getLastName()%></td>
                                  </tr>
                                </table>
                              </fieldset>
                            
                              <fieldset>
                                <legend><b><u><font color="#333333">Address</font></u></b></legend>
                                <table cellspacing="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Line 1:</td><td style="vertical-align: top;"><%=complaint.getAddress1()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Line 2:</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getAddress2())?complaint.getAddress2():""%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Line 3:</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getAddress3())?complaint.getAddress3():""%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">City:</td><td style="vertical-align: top;"><%=complaint.getCity()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Province:</td><td style="vertical-align: top;"><%=complaint.getProvince()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Country:</td><td style="vertical-align: top;"><%=complaint.getCountry()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Postal Code:</td><td style="vertical-align: top;"><%=complaint.getPostalCode()%></td>
                                  </tr>
                                </table>
                              </fieldset>
                            
                              <fieldset>
                                <legend><b><u><font color="#333333">Email Address</font></u></b></legend>
                                <table cellspacing="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Email Address:</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getEmail())?complaint.getEmail():"Not Specified"%></td>
                                  </tr>
                                </table>
                              </fieldset>
                            
                              <fieldset>
                                <legend><b><u><font color="#333333">Preferred Telephone Contact Number</font></u></b></legend>
                                <table cellspacing="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Area Code:</td><td style="vertical-align: top;"><%=complaint.getAreaCode()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Phone Number:</td><td style="vertical-align: top;"><%=complaint.getPhoneNumber()%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Phone Type:</td><td style="vertical-align: top;"><%=complaint.getPhoneType()%></td>
                                  </tr>
                                </table>
                              </fieldset>
                            
                              <fieldset>
                                <legend><b><u><font color="#333333">Contact Considerations</font></u></b></legend>
                                <table  cellspacing="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Best Contact Time:</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getContactTime())?complaint.getContactTime():"Not specified."%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Special Contact Restrictions:</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getContactRestrictions())?complaint.getContactRestrictions():"Not specified."%></td>
                                  </tr>
                                </table>
                              </fieldset>
                            
                              <fieldset>
                                <legend><b><u><font color="#333333">Complaint Details</font></u></b></legend>
                                <table cellpadding="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Complaint Category:</td>
                                    <td style="vertical-align: top;"><%=complaint.getComplaintType()%></td>
                                  </tr>
                                  <%if(complaint.getComplaintType().equals(ComplaintType.COMPLAINT_SCHOOL)){%>
                                    <tr>
                                      <td style="vertical-align: top;" colspan="2">
                                        <table cellpadding="6">
                                          <tr>
                                            <td style="vertical-align: top;" width="40%">School:</td>
                                            <td style="vertical-align: top;"><%=(complaint.getSchool() != null)?complaint.getSchool().getSchoolName():""%></td>
                                          </tr>
                                          <tr>
                                            <td style="vertical-align: top;" width="40%">Administrator Contacted?</td>
                                            <td style="vertical-align: top;">
                                              <%=complaint.isAdminContacted()?"YES":"NO"%>
                                            </td>
                                          </tr>
                                          <%if(complaint.isAdminContacted()){%>
                                            <tr>
                                              <td style="vertical-align: top;" width="40%">Administrator Contact dates:</td>
                                              <td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getAdminContactDates())?complaint.getAdminContactDates():""%></td>
                                            </tr> 
                                          <%}%>
                                        </table>
                                      </td>
                                    </tr>
                                  <%}%>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Complaint Summary: </td>
                                    <td style="vertical-align: top;"><%=StringUtils.encodeHTML(complaint.getComplaintSummary())%></td>
                                  </tr>      
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Urgency:</td>
                                    <td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getUrgency())?complaint.getUrgency():"Not specified."%></td>
                                  </tr>
                                </table>
                              </fieldset>
                              
                              <fieldset>
                                <legend><b><u><font color="#333333">Supporting Documentation</font></u></b></legend>
                                <table cellpadding="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">Documents:</td>
                                    <td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getSupportingDocumentation())?"<a href='documentation/"+complaint.getSupportingDocumentation()+"'>Download</a>":"None"%></td>
                                  </tr>
                                </table>
                              </fieldset>
                              </fieldset>
                              
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
