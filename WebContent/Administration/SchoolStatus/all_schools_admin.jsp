<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         isThreadSafe="false"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 java.io.*,com.awsd.personnel.*,
                 com.awsd.weather.*,com.awsd.security.*,com.awsd.school.*,com.esdnl.school.bean.*,com.awsd.school.dao.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEATHERCENTRAL-GLOBAL-ADMIN" />

<%
	User usr = (User) session.getAttribute("usr");
  School[] schools = null;
  Personnel principal = null;
  int colorcnt = 0;
  ClosureStatus sstat = null;
  
  UserRoles roles = usr.getUserRoles();
  if(roles.containsKey("SENIOR EDUCATION OFFICIER")
      || roles.containsKey("REGIONAL EDUCATION OFFICIER")
      || roles.containsKey("REGIONAL OPERATIONS MANAGER"))
  {
    switch(usr.getPersonnel().getSchool().getSchoolID())
    {
      case 277: //DISTRICT OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(1)).toArray(new School[0]);
        break;
      case 278: //AVALON WEST REGIONAL OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(2)).toArray(new School[0]);
        break;
      case 279: //VISTA REGIONAL OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(3)).toArray(new School[0]);
        break;
      case 280: //BURIN REGIONAL OFFICE
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(4)).toArray(new School[0]);
        break;
      default:
        schools = SchoolDB.getSchools(RegionManager.getRegionBean(usr.getPersonnel().getSchool())).toArray(new School[0]);
    }
  }
  else
    schools = (School[]) SchoolDB.getSchools().toArray(new School[0]);
    
  colorcnt = 0;
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Status Central Global Admin</title>
    <style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>
    <style type="text/css">@import "css/weathercentral.css";</style>
    <style>
      a{
       text-decoration:none;  
       font-weight:bold;
      };
      
      a:hover{
        text-decoration:none;
        font-weight:bold;
        color:#333333;
      };
    </style>
  </head>
  
  <body>
    <form name="schoolstatus" method="post" action="updateSchoolClosureStatus.html">
    <table width="100%" cellpadding="0" cellspacing="0" border="0" >	
      <tr>
        <td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
          <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td width="100%" align="left" valign="middle">
                <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">Weather Central</span><BR>
              </td>
              <td width="50" align="right" valign="middle">
                <img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../home.jsp';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><BR>
              </td>
            </tr>											
          </table>										
        </td>
      </tr>		
      <tr id="bodyContainer">
        <td width="100%" align="center" valign="top">
          <table align="center" width="50%" cellpadding="5" cellspacing="0" border="0" style="padding:5px;">
            <tr>
              <td colspan="3">
                <span class="boldBlack11pxTitle">Status Central Global Admin</span><BR>
                <hr noshade size="1" width="100%">
              </td>
            </tr>
            <tr>
            <td colspan=3 style="background-color:#ffe6e6;border:1px solid red;font-size:12px;padding:4px;"><b>PLEASE NOTE:</b> Live Updates to school status are delayed up to 5 minutes after you submit your change here. Please wait 5 minutes to reload/refresh the NLESD school status website to see your changes.</td>
            </tr>
            <tr><td colspan=3>&nbsp;</td></tr>
            <tr>
              
              <td  width="30%" valign="middle" >
                <span class="boldBlack11pxLower">School Name</span><BR>
              </td>
              <td  valign="middle" width="30%" align="center">
                <span class="boldBlack11pxLower">Current Status</span><BR>
              </td>
              <td  valign="middle" width="*" align="center">
                <span class="boldBlack11pxLower">Note(s)</span><BR>
              </td>
            </tr>
            <% for(int i=0; i < schools.length; i++)
              {
                sstat = schools[i].getSchoolClosureStatus();
                principal = schools[i].getSchoolPrincipal();
            %>  <tr>
                  
                  <td bgcolor="<%=((colorcnt%2)==0)?"#F4F4F4":"#E1E1E1"%>" valign="middle" align="left" style="padding-left:5px;">
                    <span class="normalBlack10pxText"><%=schools[i].getSchoolName()%></span>
                  </td>
                  <td bgcolor="<%=((colorcnt%2)==0)?"#F4F4F4":"#E1E1E1"%>" valign="middle" align="center">
                    <span class="normalBlack10pxText">
                    <% if(principal!=null) { %>
                      <span class="normalBlack10pxText" style="font-size:11px;">
                        <a class="<%=SchoolClosureStatusWorker.cssClass(sstat.getClosureStatusID())%>"
                           href="principaladmin.jsp?pid=<%=principal.getPersonnelID()%>" 
                           title="Click here to view detailed summary"
                           onmouseover="this.innerText='Change Status';"
                           onmouseout="this.innerText='<%=sstat.getClosureStatusDescription()%>';">
                                <%=sstat.getClosureStatusDescription()%>
                        </a>
                      </span>
                    <% } else { %>
                      <FONT COLOR="#FF0000">No Principal On Record.</FONT>
                    <% } %>
                    </span>
                  </td>
                  <td bgcolor="<%=((colorcnt%2)==0)?"#F4F4F4":"#E1E1E1"%>" valign="middle" align="left">
                    <span class="normalBlack10pxText"><%=(sstat.getSchoolClosureNote()!=null)?sstat.getSchoolClosureNote():"&nbsp;"%></span>
                  </td>
                </tr>
            <%   colorcnt++;
                }
            %>
            </table>
            <hr width="60%" color="#333333" size="5">
      	</td>
    	</tr>
    </table>
  	</form>    
  </body>
</html>
