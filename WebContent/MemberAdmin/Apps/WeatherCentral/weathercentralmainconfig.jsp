<%@ page contentType="text/html; charset=iso-8859-1" language="java"
    session="true"
    import="java.sql.*,
            java.util.*,
            java.text.*,com.awsd.security.*,
            com.awsd.weather.*,com.awsd.servlet.*"
     isThreadSafe="false"%>

<%!
  User usr = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}%>

<html>
<head>
<title></title>
<style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>

<script language="JavaScript">
	function toggle(target)
		{
			obj=(document.all) ? document.all[target] : document.getElementById(target);
			obj.style.display=(obj.style.display=='none') ? 'inline' : 'none';
		}
		
	function toggleHeight(target2)
		{
			obj=(parent.document.all) ? parent.document.all[target2] : parent.document.getElementById(target2);
			obj.style.height=(obj.style.height=='26px') ? '208px' : '26px';
			
		}
</script>
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >	
		<tr>
			<td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">Members Admin</span><BR>
						</td>
						<td width="50" align="right" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../home.jsp';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><BR>
						</td>
					</tr>											
				</table>										
			</td>
		</tr>		
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">				
				<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
					<tr>
						<td width="100%" align="left" valign="top">								
							<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
								<tr>
									<td width="100%" align="left" valign="top">
										<span class="boldBlack11pxLower">Weather Central - Configuration</span><BR><BR>
										<hr noshade="noshade" size="1" width="100%">
                    <form name="wc_config_form" action="weatherCentralConfig.html" method="post">
                      <table align="center" width="300" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                          <td width="100" align="left" valign="middle">
                            <span class="boldBlack10pxTitle">Summer Flag:</span>&nbsp;&nbsp;
                          </td>
                          <td align="left" valign="middle" width="100">
                            <select name="summer_flag">
                              <option value="true" <%=ControllerServlet.WEATHER_CENTRAL_SUMMER_FLAG?"SELECTED":""%>>ON</option>
                              <option value="false" <%=!ControllerServlet.WEATHER_CENTRAL_SUMMER_FLAG?"SELECTED":""%>>OFF</option>
                            </select>
                          </td>
                          <td align="left" valign="middle">
                            <input type="submit" value="Submit">
                          </td>
                        </tr>
                      </table>
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
</body>
</html>
