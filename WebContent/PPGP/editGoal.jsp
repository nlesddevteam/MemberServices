<%@ page language="java"
        session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*"
        isThreadSafe="false"%>

<%
  User usr = null;
  PPGPGoal goal = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("PPGP-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

  goal = (PPGPGoal) request.getAttribute("PPGP_GOAL");
%>

<html>
<head>
<title>Draft Goals</title>

<link rel="stylesheet" href="css/growthplan.css">

</head>

<body topmargin="15" bottommargin="0" leftmargin="15" rightmargin="0" marginwidth="0" marginheight="0">
<form name="EditGoal" action="modifyGrowthPlanGoal.html" method="POST">
<input type="hidden" name="gid" value="<%=goal.getPPGPGoalID()%>">
<center>
<table width="490" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="490" valign="top" align="left" colspan="2">
<img src="images/edit_goal.gif"><BR>
</td>
</tr>
<tr>
<td width="490" valign="top" align="left" colspan="2">
<textarea name="goal" rows="5" cols="70" style="font-family: Arial, Helvetica, sans-serif; font-size: 12px;font-weight:bold;"><%=goal.getPPGPGoalDescription()%></textarea><BR><BR>
</td>
</tr>
</table>
<table width="490" cellpadding="0" cellspacing="0" border="0">
<tr>
<td align="center" valign="bottom">
<img src="images/submit_01.gif"
  onmouseover="src='images/submit_02.gif';"
  onmouseout="src='images/submit_01.gif';"
  onclick="document.EditGoal.submit();self.opener.location.reload();self.close();">&nbsp;
  <img src="images/cancel_01.gif"
  onmouseover="src='images/cancel_02.gif';"
  onmouseout="src='images/cancel_01.gif';"
  onclick="self.close();"><BR>
</td>
</tr>
</table>
</center>
</form>
</body>
</html>