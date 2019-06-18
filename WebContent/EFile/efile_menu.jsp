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
<%}%>

<table width="100%" background="images/top_bg.gif" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="300" valign="middle" align="left">
      <img src="images/spacer.gif" width="300" height="1"><BR>
      <img src="images/spacer.gif" width="20" height="1"><span class="header">Logged in as "<%=usr.getPersonnel().getFullNameReverse()%>"</span><BR>
    </td>
    <td width="100%">
    </td>
    <td width="200" valign="top" align="right">
      <img src="images/top_logo.gif"><BR>
    </td>
  </tr>
</table>

<table width="100%" background="images/nav_bg.gif" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="100%" align="left" valign="top">
      <table border="0" cellpadding="0" cellspacing="0" width="500">
        <tr>
          <td>
            <img src="images/nav_search_01.gif" border="0" alt="" style="cursor:hand;"
              onMouseOut="src='images/nav_search_01.gif';"
              onMouseOver="src='images/nav_search_02.gif';"
              onclick="window.location.href='searchEFileResources.html';"><br>
          </td>
          <td>          
            <img src="images/nav_import_01.gif" border="0" alt="" style="cursor:hand;"
              onMouseOut="src='images/nav_import_01.gif';"
              onMouseOver="src='images/nav_import_02.gif';"
              onclick="window.location.href='importDocument.html';"><br>
          </td>
          <td>
            <img src="images/nav_acrobat.gif" border="0" alt="" style="cursor:hand;"
              onclick="window.location.href='http://www.adobe.com/products/acrobat/readstep2.html';"><br>
          </td>
	    <td>
            <img src="images/nav_powerpoint.gif" border="0" alt="" style="cursor:hand;"
              onclick="window.location.href='http://download.microsoft.com/download/powerpoint2000/ppview97/2000/WIN98/EN-US/PPView97.exe';"><br>
          </td>
          <td>
            <img src="images/nav_word.gif" border="0" alt="" style="cursor:hand;"
              onclick="window.location.href='http://download.microsoft.com/download/word2000/wd97vwr/2000/WIN98/EN-US/wd97vwr32.exe';"><br>
          </td>
	    <td>
            <img src="images/nav_wordperfect.gif" border="0" alt="" style="cursor:hand;"
              onclick="window.location.href='http://www.corel.com/servlet/Satellite?pagename=Corel/onlinesurvey/Survey&id=1047021887275&dlid=1047022177437&prot=http';"><br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
