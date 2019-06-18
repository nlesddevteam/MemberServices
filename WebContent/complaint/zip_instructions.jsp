<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.*,
                 java.util.*,
                 com.esdnl.complaint.model.constant.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/complaints.tld" prefix="c" %>

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
                    <p align="center"><img src="images/header.gif"><br><br><i><font size="5">How To Create A .zip File</font></i></p>

                    <form method="POST" action=""  name="theForm">
                      
                      <fieldset>
                        <legend><b><u><font color="#333333">Windows XP</font></u></b></legend>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">
                              <ol>
                                <li>Start Windows Explorer (right-click the Start menu, then choose Explore).</li>
                                <li>Navigate to the folder in which you want to create a .zip file.</li>
                                <li>On the File menu, point to New, and then click Compressed (zipped) Folder. Type a name for the new folder, and then press ENTER.</li> 
                                <li>Using Windows Explorer, drag any files you wish to place into the .zip file</li>
                              </ol>
                            </td>
                          </tr>
                        </table>
                      </fieldset>
                      
                      <fieldset>
                        <legend><b><u><font color="#333333">Using Winzip</font></u></b></legend>
                        <p><b>Winzip</b> is an application that makes .zip files. You can download Winzip for your home PC from <a href='http://www.winzip.com' target="_blank">www.winzip.com</a>.</p>
                        <table cellspacing="6">
                          <tr>
                            <td style="vertical-align: top;">
                              <ol>
                                <li>Start up Winzip.</li>
                                <li>Click "I agree".</li>
                                <li>Click the New button (upper left corner of the Winzip window). You will see a window titled New archive.</li>
                                <li>Select the folder where you wish to store the archive, by using the folder navigation box in the upper portion of the New archive window.</li>
                                <li>Name the archive, by typing a name into the File name textbox in the lower portion of the New archive window.</li> 
                                <li>Click the OK button. This should cause a new window to be displayed, called Add.</li>
                                <li>In the Add window, select those files that you wish to place in the archive, by first using the folder navigation box, and then clicking on those files you wish to add. You can select more than one file by holding down the Ctrl key on the keyboard when you click on each file name.</li>
                                <li>After you have selected all the files to include in the archive, click Add.</li>
                                <li>Exit Winzip.</li>
                              </ol>
                            </td>
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
