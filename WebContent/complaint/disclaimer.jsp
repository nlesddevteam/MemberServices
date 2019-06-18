<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.*,
                 java.util.*,
                 com.esdnl.complaint.model.constant.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/complaints.tld" prefix="c" %>c" %>

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
                    <p align="center"><img src="images/header.gif"><br><br></p>

                    <form method="POST" action=""  name="theForm">
                      
                      <fieldset>
                        <legend><b><u><font color="#333333">Disclaimer</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">
                              <p>
                                I have read and understand both the District Policy and Admin 
                                Regulations on Public Complaints located at <a href='http://www.esdnl.ca/about/policies' target="_blank">www.esdnl.ca/policies</a>
                              </p>
                              <p>
                                I agree that all submitted information is this property of the Eastern School District to file/store, investigate and action in accordance with district policy and relevant legislation.
                              </p>
                            </td>
                          </tr>
                          <tr>
                            <td style="vertical-align: top;" align="center"><input type="checkbox" onclick="window.location='index.jsp';"><b>I agree</b></td>
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
        Copyright 2007 Eastern School District. All Rights Reserved.
      </td>
    </tr>
  </table>
</body>
</html>
