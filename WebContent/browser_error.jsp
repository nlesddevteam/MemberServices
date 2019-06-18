<%@ page language="java" 
        isThreadSafe="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Member Services Signin - Eastern School District</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <script language="JavaScript" src="js/common.js"></script>
  <script language="Javascript" src="js/browser_detect.js"></script>
  <script language="Javascript" src="js/prototype.js"></script>
    <script>
      <!--
      function breakout()
      {
        if(opener)
        {
          opener.location.href="login.html";
          self.close();
        }
        else if(self != top)
        {
          top.location.href="login.html";
          self.close();
        }
      }
      function browserString()
      {
        document.write('You are using ' + BrowserDetect.browser + ' ' + BrowserDetect.version + ' on ' + BrowserDetect.OS + '.');
      }
      
      function browserErrorMsg()
      {
        document.write('Member Services is standardized on Internet Explorer.<BR> Please use Internet Explorer to login.<BR><BR>Thank you.');
      }
      -->
    </script>
</head>
<body onload="browserCheck()" topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" bgcolor="#BAC2C7" style="background-image: url('/MemberServices/images/bg.jpg'); background-repeat: repeat-x;">
  <script type='text/javascript'>breakout();</script>
	<img src="/MemberServices/images/spacer.gif" width="1" height="28"><BR>
	<table width="426" cellpadding="0" cellspacing="0" border="0" align="center">
    <tr>
      <td valign="top" align="center" style='font-family:arial;color:#333333;font-weight:bold;font-size:9px;'>
        <script>browserString();</script>
      </td>
    </tr>
		<tr>
			<td width="426" align="left" valign="top">
				<table width="426" height="420" cellpadding="0" cellspacing="0" border="0" style="background-image: url('/MemberServices/images/signin_left_bg.gif'); background-repeat: repeat-y;">
					<tr>
						<td width="426" align="left" valign="top">
							<img src="/MemberServices/images/signin_left_top.gif">
						</td>
					</tr>
					<tr> 
						<td width="426" align="center" valign="top" style='font-family:arial;color:#FF0000;font-weight:bold;font-size:11px;'>
              <script>browserString();</script>
              <BR><BR>
              <script>browserErrorMsg();</script>
						</td>
					</tr>
					<tr>
						<td width="426" align="left" valign="bottom" colspan="2" >
							<img src="/MemberServices/images/signin_left_bottom.gif"><BR>
						</td>									
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
