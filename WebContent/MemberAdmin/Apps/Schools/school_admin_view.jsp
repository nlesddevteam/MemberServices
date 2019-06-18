<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                java.text.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = null;
	
  School[] schools = null;
  School school = null;
  Personnel principal = null;
  Personnel[] viceprincipal = null;
  int colorcnt = 0;
  String color0, color1, border_color, border_style, bckgrd_color;
  int page_num = 0;

  colorcnt = 0;
  color1="#E5F2FF";
  color0="#FFFFFF";
  bckgrd_color="#FFFFCC";
  border_color="#C4C4C4";
  border_style = "solid";
  
  if(request.getParameter("page") != null)
  {
    page_num = Integer.parseInt(request.getParameter("page"));
  }
  else
  {
    page_num = 0;
  }
    
  if((session.getAttribute("SCHOOL-ARRAYLIST") == null)||(page_num == 0))
  {
    session.setAttribute("SCHOOL-ARRAYLIST", SchoolDB.getSchoolsAlphabetized());
  }
  
  
  schools = (School[])((Vector<School>) (((ArrayList<Vector<School>>)session.getAttribute("SCHOOL-ARRAYLIST")).get(page_num))).toArray(new School[0]);
%>
<html>
  <head>
    <title>Members Admin - School Administration Summary</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet" />
    <style type='text/css'>
    	a { text-transform: capitalize; }
    </style>
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="100%" valign="top" bgcolor="#333333">
      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
    </td>
  </tr>
  <tr>
      <td width="100%" valign="top">
          <center>
            <table width="90%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td align="left"><span class="header1">School Administration Summary</span></td>
                    </tr>
                    
                    <tr style='padding-top: 10px;padding-bottom:10px;'>
                      <td colspan="4">
                        <%for (char i='A'; i <= 'Z'; i++){
                            if((i-'A') != page_num){%>
                              <a href="school_admin_view.jsp?page=<%=i - 'A'%>"><%=i%></a>
                        <%  }else{%>
                            <%=i%>
                        <%}}%>
                      </td>
                    </tr>
                  </table>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <% if(request.getAttribute("msg") != null){%>
                      <table align="center" width="100%" cellpadding="0" cellspacing="0" 
                         style="border:thin dashed #E0E0E0; background-color:#FFFFCC;">
                         <tr>
                          <td width="25" align="left" style="padding-left:5;"><img src="../../images/info-cloud.gif"><br></td>
                          <td class="msg"><%=request.getAttribute("msg")%></td>
                         </tr>
                      </table>
                    <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <%}%>
                  
                  <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" style="border:hidden;">
                    <tr bgcolor="#000066">
                      <td  width="30%" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">School Name</font></span><BR>
                      </td>
                      <td  width="30%" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Principal</font></span><BR>
                      </td>
                      <td  width="30%" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Vice Principal</font></span><BR>
                      </td>
                      <td width="*">&nbsp;</td>
                    </tr>
                    <% for(int ss=0; ss < schools.length; ss++)
                      {
                        school = schools[ss];
                        principal = school.getSchoolPrincipal();
                        //viceprincipal = school.getSchoolVicePrincipal();
                        viceprincipal = school.getAssistantPrincipals();
                    %>  <tr id="container_row_<%=school.getSchoolID()%>" bgcolor="<%=((colorcnt%2)==0)?color0:color1%>">
                          <td  height="100%" valign="top" align="left" style="border:none;">
                            <span class="text"><%=school.getSchoolName()%></span>
                          </td>
                          <td  valign="top" align="left">
                            <% if(principal!=null) { %>
                              <a href="" onclick="top.document.location.href='../../../loginAs.html?pid=<%=principal.getPersonnelID()%>'; return false;"><%=principal.getFullNameReverse()%></a>
                            <% } else { %>
                              <span class="text"><FONT COLOR="#FF0000">No Principal On Record.</FONT></span>
                            <% } %>
                          </td>     
                          <td valign="top" align="left">
                            <% if((viceprincipal!=null) && (viceprincipal.length > 0)) { %>
                              <span class="text"> 
                              	<%
                              		for(int i=0; i < viceprincipal.length; i++){
                              			out.println("<a href='' onclick=\"top.document.location.href='../../../loginAs.html?pid=" + viceprincipal[i].getPersonnelID()+ "'; return false;\">" + viceprincipal[i].getFullNameReverse() + "</A><BR>");
                              		}
                              	%>
                              </span>
                            <% } else { %>
                              <span class="text"><FONT COLOR="#FF0000">No Vice Principal On Record.</FONT></span>
                            <% } %>
                          </td>
                          <td valign="top" align="left">
                            <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
                              <tr>
                                <td align="center" width="50%" valign="middle" alt="Modify School Administration Information">
                                	<a href='schoolAdminChange.html?sid=<%=school.getSchoolID()%>&page=<%=page_num%>'>
                                  	<img src="../../images/modify_off.gif" border='0'
                                       onmouseover="src='../../images/modify_on.gif';"
                                       onmouseout="src='../../images/modify_off.gif';" />
                                  </a>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                    <%   colorcnt++;
                        }
                    %>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
                  <hr noshade color="#333333" size="1" width="100%" align="right">
                </td>
              </tr>
            </table>
          </center>
      </td>
    </tr>
    </table>
  </body>
</html>