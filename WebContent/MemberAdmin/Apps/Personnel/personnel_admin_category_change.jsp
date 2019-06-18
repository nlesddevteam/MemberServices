<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.personnel.*,
                java.text.*"%>
<%!User usr = null;
  Personnel p = null;
  PersonnelCategories categories = null;
  PersonnelCategory cat = null;
  Iterator iter = null;%>
<%
	usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%
	}
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}

  p = (Personnel) request.getAttribute("Personnel");
  categories = new PersonnelCategories();
  iter = categories.iterator();
%>
<html>
  <head>
    <title>Members Admin - Personnel Administration Change</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<form name="change" action="/MemberServices/MemberAdmin/Apps/Personnel/personnelAdminCategoryChange.html?update&pid=<%=p.getPersonnelID()%>" method="post">
<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="100%" valign="top" bgcolor="#333333">
      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
    </td>
  </tr>
  <!--
  <tr>
    <td width="100%">
      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="25"><BR>
    </td>
  </tr>
  -->
  <tr>
      <td width="100%" valign="top">
          <center>
            <table width="70%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="40"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">Personnel Administration - Category Change</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table align="center" width="100%" cellpadding="5" cellspacing="0" border="0">
                    <tr>
                      <td  valign="middle" >
                      <span class="title"><font style="font-weight:bold;">Name:</font></span><BR>
                      </td>
                      <td><%=p.getFullName()%></td>
                    </tr>
                    <tr>
                      <td  width="30%" valign="middle" >
                      <span class="title"><font style="font-weight:bold;">Category</font></span>
                      </td>
                      <td>
                      <SELECT name="category">
                        <option value="-1">PLEASE SELECT PERSONNEL CATEGORY</option>
                        <%
                        	while(iter.hasNext())
                                                  {
                                                    cat = (PersonnelCategory) iter.next();
                        %>
                          <option value="<%=cat.getPersonnelCategoryID()%>" <%=(p.getPersonnelCategory().getPersonnelCategoryID()==cat.getPersonnelCategoryID())?"SELECTED":""%>><%=cat.getPersonnelCategoryName() %></option>
                        <% } %>
                      </SELECT>
                      <BR>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
                  <hr noshade color="#333333" size="1" width="100%" align="right">
                  
                  <table width="100%">
                    <tr>
                      <td align="left" width="75%">
                        <img name="processing" src="/MemberServices/MemberAdmin/images/spacer.gif" align="left">
                        <font color="#FF0000">
                          <b>
                            <% if(request.getAttribute("msg") != null) { %>
                              <%= request.getAttribute("msg") %>
                            <% } %>
                          </b>
                        </font>
                      </td>
                      <td align="right" width="25%">
                        <img name="add" src="/MemberServices/MemberAdmin/images/update_01.jpg" 
                            onmouseover="src='/MemberServices/MemberAdmin/images/update_02.jpg'" 
                            onmouseout="src='/MemberServices/MemberAdmin/images/update_01.jpg'" 
                            onmousedown="src='/MemberServices/MemberAdmin/images/update_03.jpg'" 
                            onmouseup="src='/MemberServices/MemberAdmin/images/update_02.jpg'" 
                            onclick="document.change.submit();"><BR>
                      </td>
                    </tr>
                  </table>
                  <hr noshade color="#333333" size="1" width="100%" align="right"> 
                </td>
              </tr>
            </table>
          </center>
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>