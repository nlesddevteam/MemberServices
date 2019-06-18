<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.school.*,com.esdnl.util.*,
                  com.esdnl.photocopier.bean.*,
                  com.esdnl.photocopier.dao.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/photocopiers.tld" prefix="copier" %>

<%
  User usr = (User) session.getAttribute("usr");
  boolean isAdmin = usr.getUserPermissions().containsKey("PHOTOCOPIER-ADMIN-VIEW");
  
  PhotocopierBean[] copiers = null;
  School s = null;
  School tmp = null;
  int cnt = 0;
  int s_cnt = 0;
  
  if(isAdmin)
    copiers = PhotocopierManager.getPhotocopierBeans();
  else
    copiers = PhotocopierManager.getPhotocopierBeans(usr.getPersonnel().getSchool());
    

  //System.out.println(isAdmin);
  //System.out.println(copiers.length);
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Eastern School District - Member Services - Personnel-Package</title>
    <style type="text/css">@import 'css/photocopiers.css';</style>
  </head>
  
  <body>
    <!--check view page permissions-->
    <esd:SecurityCheck permissions="PHOTOCOPIER-VIEW,PHOTOCOPIER-ADMIN-VIEW" />
    
    <table cellpadding="0" cellspacing="0" width="800" align="center" border="0">
      <tr>
        <td width="800">
          <img src="images/header.gif" width="800"><br>
        </td>
      </tr>
      <tr>
        <td width="800" style="border-bottom:solid 5px #FFCC00;">
          <table cellpadding="0" cellspacing="0" width="800">
            <tr>
              <!-- LEFT SIDE MENU -->
              <td id="side_menu" width="150" valign="top" style="background-color:#F6F6F6; padding-top:10px;padding-left:5px;border-right:solid 1px #c4c4c4;">
                <table width="150" cellpadding="0" cellspacing="0">
                  <tr><td width="100%"><a class="homeSideNavLink" href="view_copiers.jsp">> View Copiers</a></td></tr>
                  <tr><td width="100%"><a class="homeSideNavLink" href="add_copier.jsp">> Add Copier</a></td></tr>
                  <!--<tr><td width="100%"><a class="homeSideNavLink" href="request_service.jsp">> Request Service</a></td></tr>-->
                </table>
              </td>
              
              <!-- MAIN CONTENT -->
              <td id="main_content" width="650" style="border-left:solid 1px #333333;padding-left:5px;padding-top:10px;padding-bottom:10px;">
                <table cellspacing="0" cellpadding="0" width="650">
                  <tr>
                    <td class="displayPageTitle" colspan="4"> View Photocopiers</td>
                  </tr>
                  <tr>
                    <td colspan="4" class="displayText" style='padding-top:10px;'>
                      <%if(isAdmin){%>
                        <span id="total_schools" style="padding-right:15px;"></span>
                      <%}%>
                      Total Copiers: <%=copiers.length%>
                    </td>
                  </tr>
                  
                  <%if(request.getAttribute("msg") != null){%>
                    <tr style="padding-top:10px;">
                      <td class="messageText" colspan="4" align="center"><%=(String)request.getAttribute("msg")%></td>
                    </tr>
                  <%}%>
                  
                  <tr style="padding-top:10px;">
                    <td width="225" class="displayHeaderTitle">Brand</td>
                    <td width="225" class="displayHeaderTitle">Model#</td>
                    <td width="100" class="displayHeaderTitle">Year Acquired</td>
                    <td width="*">&nbsp;</td>
                  </tr>
                  <%if((copiers != null) && (copiers.length > 0)){%>
                    <%for(int i=0; i < copiers.length; i++){%>
                      <%if(isAdmin)
                        {
                          tmp = copiers[i].getSchool();
                          if(tmp != null)
                          {
                            if((s == null) || (s.getSchoolID() != tmp.getSchoolID()))
                            {
                              s_cnt++;
                              cnt=-1;
                              if(s != null)
                                out.println("<tr style='padding-bottom:5px;'><td colspan='4' class='displayText' style='background-color:#c4c4c4;'><img src='images/spacer.gif' width='1' height='3'></td></tr>");  
                              s = tmp;
                              out.println("<tr style='padding-top:10px;padding-bottom:5px;'><td colspan='4' class='displayText' style='font-weight:bold;'>"+ s.getSchoolName() +"</td></tr>");
                            }
                          }
                        }
                      %>
                      <tr style='padding-top:5px;padding-bottom:5px;' >
                        <td class="displayText"><%=copiers[i].getBrand()%></td>
                        <td class="displayText"><%=copiers[i].getModelNumber()%></td>
                        <td class="displayText" colspan="2"><%=copiers[i].getYearAcquired()%></td>
                      </tr>
                      <%if(!StringUtils.isEmpty(copiers[i].getOtherComments())){%>
                        <tr style='padding-bottom:5px;' >
                          <td class="displayText" style="font-weight:bold;padding-left:20px;">Other Comments:</td>
                          <td class="displayText" colspan="3"><%=copiers[i].getOtherComments()%></td>
                        </tr>
                      <%}%>
                      <tr style='padding-bottom:5px;' >
                        <td class="displayText" colspan="4" valign="middle" align="right" style="border-bottom:solid 1px #333333;color:#FF0000;">
                          <!--<a href="view_copier.jsp?id=<%=copiers[i].getId()%>">VIEW DETAILS</a>|-->
                          <a href="add_copier.jsp?id=<%=copiers[i].getId()%>">EDIT</a>
                          <!--&nbsp;|&nbsp;<a href="deleteCopier.html?id=<%=copiers[i].getId()%>">DELETE</a>-->
                        </td>
                      </tr>
                    <%}%>
                    <%if(isAdmin){%>
                      <script type="text/javascript">
                        var obj = document.getElementById('total_schools');
                        if(obj)
                          obj.innerText = 'Total Schools: <%=s_cnt%>';
                      </script>
                    <%}%>
                  <%}else{%>
                    <tr><td class="messageText" colspan="4">No photocopiers on file.</td></tr>
                  <%}%>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>