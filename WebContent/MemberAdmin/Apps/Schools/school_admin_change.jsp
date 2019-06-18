<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*, com.esdnl.util.*,
                java.text.*"%>
<%!User usr = null;
  School school = null;
  Personnel principal = null;
  int principalID;
  HashMap viceprincipal = null;
  //int viceprincipalID;
  Personnel tmp = null;
  DistrictPersonnel personnel = null;
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

  school = (School) request.getAttribute("School");
  principal = school.getSchoolPrincipal();
  if(principal != null)
  {
    principalID = principal.getPersonnelID();
  }
  else
  {
    principalID = -1;
  }
  
  //viceprincipal = school.getSchoolVicePrincipal();
  viceprincipal = school.getAssistantPrincipalsMap();
  
  personnel = new DistrictPersonnel();
  iter = personnel.iterator();
%>
<!DOCTYPE html>
<html>
  <head>
    <title>Members Admin - School Administration Change</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<form name="change" action="/MemberServices/MemberAdmin/Apps/Schools/schoolAdminChange.html?update&sid=<%=school.getSchoolID()%>" method="post">
<input type="hidden" name="page" value="<%=request.getParameter("page")%>" />
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
            <table width="70%" cellpadding="0" cellspacing="0" border="0" align="center">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="40"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">School Administration Change</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table align="center" width="100%" cellpadding="5" cellspacing="0" border="0">
                    <tr>
                      <td  valign="middle" >
                      <span class="title"><font style="font-weight:bold;">School Name:</font></span><BR>
                      </td>
                      <td><%=school.getSchoolName()%></td>
                    </tr>
                    <tr>
                      <td  width="30%" valign="middle" >
                      <span class="title"><font style="font-weight:bold;">Principal</font></span>
                      </td>
                      <td>
                      <SELECT name="principal">
                        <option value="-1">NO PRINCIPAL ASSIGNED TO SCHOOL</option>
                        <% while(iter.hasNext())
                          {
                            tmp = (Personnel) iter.next();
                            if(!tmp.getEmailAddress().endsWith("@nlesd.ca") || !tmp.getUserName().endsWith("@nlesd.ca")) continue;
                        %>
                          <option value="<%=tmp.getPersonnelID()%>" <%=(principalID ==tmp.getPersonnelID())?"SELECTED":""%>><%=StringUtils.encodeHTML2(tmp.getFullName())%>(<%=tmp.getUserName()%>)</option>
                        <% } %>
                      </SELECT>
                      <BR>
                      </td>
                    </tr>
                    <tr>
                      <td  width="30%" valign="top" >
                      <span class="title"><font style="font-weight:bold;">Vice Principal</font></span>
                      </td>
                      <td>
                      <SELECT name="viceprincipal" MULTIPLE style='height:200px;'>
                        <option value="-1" <%=((viceprincipal != null)&&(viceprincipal.size() == 0))?"SELECTED":""%>>NO VICE PRINCIPAL ASSIGNED TO SCHOOL</option>
                        <% 
                        	Personnel[] aps = school.getAssistantPrincipals();
                        	for(int i=0; i < aps.length; i++){
                        		out.println("<option value='" + aps[i].getPersonnelID() + "' SELECTED>" + StringUtils.encodeHTML2(aps[i].getFullName()) + "(" + aps[i].getUserName() + ")</option>");
                        	}
                        	
                        	iter = personnel.iterator(); 
                          while(iter.hasNext()){
                            tmp = (Personnel) iter.next();
                            if(viceprincipal.containsKey(new Integer(tmp.getPersonnelID())) || !tmp.getEmailAddress().endsWith("@nlesd.ca") || !tmp.getUserName().endsWith("@nlesd.ca")) continue;
                            	
                          	out.println("<option value='" + tmp.getPersonnelID() + "'>" + StringUtils.encodeHTML2(tmp.getFullName()) + "(" + tmp.getUserName() + ")</option>");
                        	}
                        %>
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
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>