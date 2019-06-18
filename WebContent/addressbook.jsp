<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*,
                  com.awsd.security.addressbook.*,
                  java.util.*"%>

<%!
  AddressBook addr = null;
  Address a = null;
  Iterator iter = null;
%>
<%
  addr = (AddressBook) request.getAttribute("AddressBook");
  iter = addr.iterator();
%>
<html>
<head>
<title>AWSB Address Book</title>

<link rel="stylesheet" href="css/growthplan.css">
<script language="javascript" src="js/common.js"></script>

</head>

<body topmargin="15" bottommargin="0" leftmargin="15" rightmargin="0" marginwidth="0" marginheight="0">
<form name="addressbook" action="viewAddressBook.html" method="post">
<center>
<table width="250" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="250" valign="top" align="center" colspan="2">
<img src="images/addressbook_title.gif"><BR>
</td>
</tr>
<tr>
  <td>
    <table width="95%">
      <tr>
        <td valign="middle">
          <B>Search:</B>
        </td>
        <td valign="middle">
          <input type="text" name="searchtxt">
        </td>
        <td valign="middle">
          <img src="images/search_01.jpg"
            onmouseover="src='images/search_02.jpg';"
            onmouseout="src='images/search_01.jpg';"
            onmousedown="src='images/search_03.jpg';"
            onmouseup="src='images/search_02.jpg';"
            onclick="document.addressbook.submit();"><br>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
<td width="250" valign="top" align="center" colspan="2">
<SELECT name="emailaddr" size="18" width="200" onchange="emailSelect(); self.close();">
<!--<table border="1">-->
<% while(iter.hasNext())
  {
    a = (Address) iter.next();
%> <!--<tr>
    <td><%=a.getFullName()%></td>
    <td><%=a.getEmailAddress()%></td>
   </tr>-->
    <option value="<%=a.getEmailAddress()%>"><%=a.getFullName()%></option>
<% } %>
<!--</table>-->
    <option>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </option>
</SELECT>
</td>
</tr>
<!--
<tr>
<td width="250" valign="top" align="center" colspan="2">
<br>
<img src="images/select_01.jpg"
    onmouseover="src='images/select_02.jpg';"
    onmouseout="src='images/select_01.jpg';"
    onmousedown="src='images/select_03.jpg';"
    onmouseup="src='images/select_02.jpg';"
    onclick="emailSelect(); self.close();"><BR>   
</td>
</tr>
-->
</table>
</form>
</body>
</html>