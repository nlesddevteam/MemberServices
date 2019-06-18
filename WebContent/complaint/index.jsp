<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.*,
                 java.util.*,
                 com.esdnl.complaint.model.constant.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/complaints.tld" prefix="c" %>

<html>
<head>
  <title>Eastern School District -- How We Help -- Complaint Form</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <link href="css/complaint.css" rel="stylesheet" type="text/css">
  <script Language="JavaScript" Type="text/javascript" src="js/common.js"></script>
</head>
<body bgcolor="#ffffff">
	<script type='text/javascript'>
		window.location.href = 'https://www.nlesd.ca';
	</script>
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
            <td width="100%" height="50%" align="right" valign="bottom" style="padding: 0 10px 5px 0;">&nbsp;
            <a href="http://www.esdnl.ca" style="color:#FFFFFF;"><b>Home</b></a>						
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
                    <p align="center"><img src="images/header.gif"><br><br><i><font size="5">Online Complaint Form</font><BR><font size="3">(Secure)</font></i></p>

                    <p>To submit a complaint to the Eastern School District, use the form below.</p>

                    <p>The 'Send Form" button will submit your form in a secure and confidential web connection.
                    We will notify you that we have received your complaint via email if your email address is provided.</p>
  
                    <p>You must complete all the required information and click "Send Form" at the bottom;  
                    otherwise, the information will be lost.</p>

                    <%if(request.getAttribute("msg") != null){%>
                      <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                        <table cellpadding="0" cellspacing="0">
                          <tr>
                            <td style="font-weight:bold; color:#ff0000;">Field Errors:</td>
                          </tr>
                          <tr>
                            <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
                          </tr>
                        </table>
                      </p>
                    <%}%>
                    
                    <form method="POST" action="addComplaint.html" onsubmit="return FrontPage_Form1_Validator(this)" name="theForm" ENCTYPE="multipart/form-data">
                      <p>Please note that items with a red asterix (<span style="color: #FF0000;">*</span>) are required fields.</p>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Your Name</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">First Name<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="First_Name" size="50" class="requiredInputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Middle Inital</td><td style="vertical-align: top;"><input type="text" name="Middle_Initial" size="10" class="inputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Last Name<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="Last_Name" size="50" class="requiredInputBox"></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Your address</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">line 1<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="Address_Line_1" size="50" class="requiredInputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">line 2</td><td style="vertical-align: top;"><input type="text" name="Address_Line_2" size="50" class="inputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">line 3</td><td style="vertical-align: top;"><input type="text" name="Address_Line_3" size="50" class="inputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">City<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="City" size="50"   class="requiredInputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Province<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><c:StateProvince id='Province' value='NL'  cls="requiredInputBox" /></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Country<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><c:Country id='Country' value='CA'  cls="requiredInputBox" /></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Postal Code<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="PostalCode" size="12" class="requiredInputBox"></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Your Email Address</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">Email Address</td><td style="vertical-align: top;"><input type="text" name="Email_Address" size="50" class="inputBox"></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Preferred Telephone Contact Number</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">Area Code<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="Contact_Phone_Area_Code" size="3" value="709" class="requiredInputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Phone Number<span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><input type="text" name="Contact_Phone" size="15" class="requiredInputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">What kind of number is this? <span style="color: #FF0000;">*</span></td><td style="vertical-align: top;"><c:PhoneCategory id="Contact_Phone_Type" value='<%=PhoneType.HOME%>' /></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Contact Considerations</font></u></b></legend>
                        <p>The District Office hours are Monday - Friday, 8:30am - 4:30pm.</p>
                        <table  cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">What is the best time to contact you?</td><td style="vertical-align: top;"><input type="text" name="Best_Contact_Time" size="50" class="inputBox"></td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">Are there any special contact restrictions?</td><td style="vertical-align: top;"><textarea rows="3" name="Contact_Restrictions" cols="50" rows="3" class="inputBox"></textarea></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Complaint Details</font></u></b></legend>
                        <table cellpadding="6">
                          <tr>
                            <td style="vertical-align: top;">1. Which department, agency or organization is your complaint about? <span style="color: #FF0000;">*</span></td>
                            <td style="vertical-align: top;"><c:ComplaintCategory id="cat_id" onChange="toggleSchoolRow(this, '5');"  cls="requiredInputBox" /></td>
                          </tr>
                          <tr id="school-row" style="display:none;">
                            <td style="vertical-align: top;" colspan="2">
                              <table cellpadding="6">
                                <tr>
                                  <td style="vertical-align: top;">1b. Which school is your complaint about? <span style="color: #FF0000;">*</span></td>
                                  <td style="vertical-align: top;"><c:Schools id="school_id"  cls="requiredInputBox" /></td>
                                </tr>
                                <tr>
                                  <td style="vertical-align: top;">1c. Have you contacted the school administrator to discuss this issue?<span style="color: #FF0000;">*</span></td>
                                  <td style="vertical-align: top;">
                                    <input type="radio" id="school_admin_contacted" name="school_admin_contacted" value="true" onclick="toggleAdminContactedRow(this);">&nbsp;Yes
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="radio" id="school_admin_contacted" name="school_admin_contacted" value="false" onclick="toggleAdminContactedRow(this);" checked>&nbsp;No
                                  </td>
                                </tr>
                                <tr id="admin-contacted-row" style="display:none;">
                                  <td style="vertical-align: top;">1d. Please provide contact dates.<span style="color: #FF0000;">*</span></td>
                                  <td style="vertical-align: top;"><textarea rows="3" name="contact_dates" cols="35" class="requiredInputBox"></textarea></td>
                                </tr> 
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;">2. Summarize your complaint. List any steps you have taken to try to resolve it and relevant dates.<span style="color: #FF0000;">*</span></td>
                            <td style="vertical-align: top;"><textarea rows="10" name="Complaint" cols="50" class="requiredInputBox"></textarea></td>
                          </tr>      
                          <tr>
                            <td style="vertical-align: top;">3. If you consider the matter urgent, explain why.</td>
                            <td style="vertical-align: top;"><textarea rows="3" name="Urgent_Why" cols="50" class="inputBox"></textarea></td>
                          </tr>
                        </table>
                      </fieldset>
                      
                      <fieldset>
                        <legend><b><u><font color="#333333">Supporting Documentation</font></u></b></legend>
                        <p>If you have supporting documentation such as letter, picture, etc, you may upload them here. To submit multiple documents, <a href="zip_instructions.jsp">create a zip file</a> containing all your documents and then upload the zipped file.</p>
                        <table cellpadding="6">
                          <tr>
                            <td style="vertical-align: top;">Documents?</td>
                            <td style="vertical-align: top;"><input type="file" name="supporting_documents" size="40"></td>
                          </tr>
                        </table>
                      </fieldset>
                    
                      <fieldset>
                        <legend><b><u><font color="#333333">Final Steps</font></u></b></legend>
                        <p>You can  print your responses after you click Send Form.</p>
                          <input type="submit" value="Send Form" name="B1">
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
        Copyright 2007 Eastern School District. All Rights Reserved.
      </td>
    </tr>
  </table>
</body>
</html>
