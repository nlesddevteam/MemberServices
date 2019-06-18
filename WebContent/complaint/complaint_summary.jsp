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
</head>
<body bgcolor="#ffffff">
  <a name="top"></a>
  <table width="760" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 1px #B4B4B4;">
    <tr>
      <td colspan="9" id="indexPhoto" align="left" valign="top" style="background-image:url('images/index_02.jpg');">
        <table width="100%" height="156" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="100%" height="50%" align="right" valign="top" style="padding-right: 10px;">
              <span style="color:#FFFFFF;font-weight:bold;"><%=new SimpleDateFormat("EEEEEEEEE, MMMM dd, yyyy").format(Calendar.getInstance().getTime())%></span>
            </td>
          </tr>
          <tr>
            <td width="100%" height="50%" align="right" valign="bottom" style="padding: 0 10px 16px 0;">&nbsp;						
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="9" valign="top" bgcolor="#FFFFFF">
        <table width="760" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2" valign="top"><img src="images/ttls-underline.gif" width="564" height="1"></td>
          </tr>
          <tr>
            <td colspan="2" valign="top">
              <table width="505" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td valign="top">
                    <br><br>
                    <p align="center"><img src="images/header.gif"><br><br><i><font size="5">Online Complaint Form</font><BR>(Secure)</i></p>

                    <%if(request.getAttribute("msg") != null){%>
                      <div style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                        <table cellpadding="0" cellspacing="0">
                          <tr>
                            <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                          </tr>
                        </table>
                      </div>
                    <%}%>
                    
                    <form method="POST" action="addComplaint.html" onsubmit="return FrontPage_Form1_Validator(this)" name="theForm" ENCTYPE="multipart/form-data">
                      <p>Please note that items in marked with red asterix (<span style="color: #FF0000;">*</span>) next to them are required fields.</p>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Your Name</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">First Name<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getFirstName()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Middle Inital</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getMiddleInitial())?complaint.getMiddleInitial():""%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Last Name<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getLastName()%></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Your address</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">line 1<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getAddress1()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">line 2</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getAddress2())?complaint.getAddress2():""%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">line 3</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getAddress3())?complaint.getAddress3():""%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">City<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getCity()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Province<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getProvince()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Country<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getCountry()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Postal Code<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getPostalCode()%></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Your Email Address</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">Email Address<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getEmail()%></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Preferred Telephone Contact Number</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">Area Code<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getAreaCode()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Phone Number<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getPhoneNumber()%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">What kind of number is this? <span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><%=complaint.getPhoneType()%></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Contact Considerations</font></u></b></legend>
                        <p>The District Office hours are Monday - Friday, 8:30am - 4:30pm.</p>
                        <table  cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">What is the best time to contact you?</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getContactTime())?complaint.getContactTime():"Not specified."%></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" width="40%">Special contact restrictions</td><td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getContactRestrictions())?complaint.getContactRestrictions():"Not specified."%></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Complaint Details</font></u></b></legend>
                        <table cellpadding="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">1. Which department, agency or organization is your complaint about? <span style="color: #FF0000;">*</span></td>
                            <td style="vertical-align: top;"><%=complaint.getComplaintType()%></td>
                          </tr>
                          <%if(complaint.getComplaintType().equals(ComplaintType.COMPLAINT_SCHOOL)){%>
                            <tr>
                              <td style="vertical-align: top;" colspan="2">
                                <table cellpadding="6">
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">1b. Which school is your complaint about? <span style="color: #FF0000;">*</span></td>
                                    <td style="vertical-align: top;"><%=(complaint.getSchool() != null)?complaint.getSchool().getSchoolName():""%></td>
                                  </tr>
                                  <tr>
                                    <td style="vertical-align: top;" width="40%">1c. Have you contacted the school administrator to discuss this issue?<span style="color: #FF0000;">*</span></td>
                                    <td style="vertical-align: top;">
                                      <%=complaint.isAdminContacted()?"YES":"NO"%>
                                    </td>
                                  </tr>
                                  <%if(complaint.isAdminContacted()){%>
                                    <tr>
                                      <td style="vertical-align: top;" width="40%">1d. Please provide contact dates.<span style="color: #FF0000;">*</span></td>
                                      <td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getAdminContactDates())?complaint.getAdminContactDates():""%></td>
                                    </tr> 
                                  <%}%>
                                </table>
                              </td>
                            </tr>
                          <%}%>
                          <tr>
                            <td style="vertical-align: top;" width="40%">2. Summarize your complaint. List any steps you have taken to try to resolve it and relevant dates.<span style="color: #FF0000;">*</span></td>
                            <td style="vertical-align: top;"><%=complaint.getComplaintSummary().replaceAll("\n", "<BR>")%></td>
                          </tr>      
                          <tr>
                            <td style="vertical-align: top;" width="40%">3. If you consider the matter urgent, explain why.</td>
                            <td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getUrgency())?complaint.getUrgency():"Not specified."%></td>
                          </tr>
                        </table>
                      </fieldset>
                      
                      <fieldset>
                        <legend><b><u><font color="#333333">Supporting Documentation</font></u></b></legend>
                        <p>If you have supporting documentation such as letter, picture, etc; you may upload them here. To submit multiple documents zip all documents together and then upload the corresponding zip file.</p>
                        <table cellpadding="6">
                          <tr>
                            <td style="vertical-align: top;" width="40%">Documents?</td>
                            <td style="vertical-align: top;"><%=!StringUtils.isEmpty(complaint.getSupportingDocumentation())?"<a href='documentation/"+complaint.getSupportingDocumentation()+"'>Download</a>":"None"%></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                    </form>
                    <p align="left">&nbsp; </p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="9" valign="top" align="center" class="btmtxt" style="background-color:#B4B4B4;" >
        Copyright 2007 Eastern School District.All Rights Reserved.
      </td>
    </tr>
  </table>
</body>
</html>
