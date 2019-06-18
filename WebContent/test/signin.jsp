<%@ page language="java" isThreadSafe="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Member Services 3.0 Login</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <style type="text/css">@import 'css/style.css';</style>
  <script language="JavaScript" src="js/common.js"></script>
  <script language="Javascript" src="js/browser_detect.js"></script>
  <script language="Javascript" src="js/jquery-1.9.1.min.js"></script>
  
    <script language="Javascript">   
      function toggle(target)
      {
        obj=(document.all) ? document.all[target] : document.getElementById(target);
        obj.style.display=(obj.style.display=='none') ? 'inline' : 'none';
      }
      
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
      
      function forgotPwd()
      {
        if(document.forms[0].user.value == "")
        {
          alert("Please enter your user id.");
        }
        else
        {
          openWindow('SecretQuestion', 'UserAdmin/askSecretQuestion.html?op=ASK&uid='+document.forms[0].user.value, 620, 200, 0);
        }
      }
      
      function browserCheck()
      {
        if(!browser.isIE && !browser.isFirefox){
        	$('#tbl_signin').hide("slow");
        	$('#msgtxt').html('Currently only Internet Explorer and Firebox are supported by Member Services.');
        }
        else
        	$('#user').focus();
      }
      
      $('document').ready(function () {
    		breakout();
    		browserCheck();

    		$('#btn_signin').click(function(){
    			if(validateSignin(document.signin))
        	{
    	    	$('#btn_signin').attr('disabled', 'disabled');
        		toggle('msg');
        		document.signin.submit();
        	}
    			else
        			return false;
    		});
			});
      
    
    </script>
</head>
<body bgcolor="#BF6200" >
	<table width="500" border="0" cellspacing="0" cellpadding="0" align="center" style="background-color: White; border: 2px solid #00407A;">
		<tr><td><div align="center"><img src="images/msheader.png" width="495" vspace="2" border="0"></div>
		</td></tr>
		<tr><td>
  				<form action="/MemberServices/memberSignIn.html" id='signin' name="signin" method="post">
					<table width="400" border="0" cellspacing="2" cellpadding="2" align="center">
						<tr><td>
							<span class="loginheader">USER ID:</span> <input tabindex="1" type="text" name="user" id="user" onKeyPress="return submitenter(this,event);" OnFocus="this.style.background = '#F4f4f4';" OnBlur="this.style.background = '#FFFFFF';" class="inputfield">
							<br>
							<span class="loginheader">PASSWORD:</span> <input tabindex="2" type="password" name="pass" onKeyPress="return submitenter(this,event);" OnFocus="this.style.background = '#F4f4f4';" OnBlur="this.style.background = '#FFFFFF';" class="inputfield">
						</td><td>
							<div align="right">
							<input type='image'
                      		id = 'btn_signin'
                      		name='btn_signin'
                      		src="/MemberServices/test/images/signin-off.png"
                      		onMouseover="src='/MemberServices/images/signin-on.png'"
                      		onMouseout="src='/MemberServices/images/signin-off.png'"
                      		style="cursor: hand;"
                      		border="0" 
                      		alt="Sign In"> 
                      		</div>
                      	</td></tr>
                      	<tr><td colspan="2">
                      		<a href="" onclick="forgotPwd(); return false;" style="font-family:Verdana, Arial, Helvetica, sans-serif; font-size: 9px; color:#003399; text-decoration: none;">
                       		Did you forget your password? Click here. </a>
                       	</td></tr>                 	           	
                       	
					
                      	<tr>
                        <td colspan="2" align="center">                                              
                        <img name="processing" src="/MemberServices/images/spacer.gif">
                        </td>
                      </tr>
                      
                      <tr id="msg">
                        <td colspan="2" align="center" id="msgtxt" style='font-family:Verdana, Arial, Helvetica, sans-serif; font-size: 11px; font-weight:bold;color:#FF6600;'>
                          <% 
                          	if(request.getAttribute("msg") != null)
                              out.println(request.getAttribute("msg"));
                          	else if(request.getParameter("msg") != null) 
                              out.println(request.getParameter("msg"));
                          	else
                          		out.println("&nbsp;");
                          %>
                        </td>
                      </tr>
                    </table>	
                 </form>						
	</td></tr>
	<tr><td>
	<div align="center"><img src="images/msfooter.png" width="479" border="0"></div>
	</td></tr>
	<tr bgcolor="#00407A"><td><div align="center"><span class="copyright">Member Services 3.0 &copy; 2013 Eastern School District &middot; All Rights Reserved</span></div>
	</td></tr>
	</table>									
				

</body>
</html>
