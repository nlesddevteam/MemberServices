<%@ page language="java"
        session="true"
         import="com.awsd.security.*"%>

<%!
  User usr = null;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
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
%>
<html>
<head>
  <title>Please Choose. - E-File Repository</title>
  <link rel="stylesheet" href="css/e-file.css">
  <script type="text/javascript">
    function swap_image(id)
    {
      var img = document.getElementById('chooser');

      img.src = 'images/efile_chooser_' + id + '.gif';
    }
  </script>
</head>
<body bgcolor="#333333">
<center>
<table width="500" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="500" valign="top">
<img src="images/choose_logo.gif"><BR>
</td>
</tr>
<tr>
<td width="500" valign="top" align="center">
  <img id="chooser" src="images/efile_chooser_0.gif" usemap="#chooser_map" border="0">
</td>
<tr>
<tr>
<td valign="top" align="center" bgcolor="#333333">
<img src="images/spacer.gif" width="1" height="20"><BR>
</td>
<tr>
<td width="500" height="1" valign="top" align="center" bgcolor="#666666">
<img src="images/spacer.gif" width="1" height="1"><BR>
</td>
</tr>
<td width="500" height="25" align="left" valign="middle">
<span class="smalltext">&copy; Copyright 2005 Eastern School District. All Rights Reserved.</span><BR>
</td>
</tr>
</table>

<map name="chooser_map">
  <area shape="rect" coords="0,0,227,154" href="EDocument/viewEFileRepository.html"
    onmouseover="swap_image(1);" onmouseout="swap_image(0);">
  <area shape="rect" coords="0,177,227,328" href="EQuestion/viewEFileQuestionRepository.html"
    onmouseover="swap_image(2);" onmouseout="swap_image(0);">
  <area shape="rect" coords="244,0,468,156" href=""
    onmouseover="swap_image(3);" onmouseout="swap_image(0);">
  <area shape="rect" coords="243,176,468,328" href=""
    onmouseover="swap_image(4);" onmouseout="swap_image(0);">
</map>
</body>
</html>