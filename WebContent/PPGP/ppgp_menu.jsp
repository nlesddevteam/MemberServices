<%@ page language="java"
         session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*,
                 java.text.*,
                 java.util.*"
        isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>

<esd:SecurityCheck permissions='PPGP-VIEW' />

<%
	User usr = (User) session.getAttribute("usr");
  HashMap<String, PPGP> ppgps = PPGPDB.getPPGPMap(usr.getPersonnel());
%>

<html>
  <head>
    <title>Professional Learning Plan</title>
    <link rel="stylesheet" href="css/growthplan.css">
    <style type="text/css">
			hr{
					width:95%;
			    border: 0;
			    height:1px;
			    color: #000000;
			    background-color: #000000;
			}
    </style>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">

    <table style="width:100%;
                  background-image: url(images/ppgp_menu_bg.jpg); 
                  background-position: top left; 
                  background-repeat: repeat-y;
                  border-right: solid 1px #003366;" 
           cellpadding="0" cellspacing="0" border="0" valign="top" height="100%">
      <tr>
        <td width="100%" valign="top" align="center">
          <table cellpadding="0" cellspacing="0" border="0" valign="top">
            <tr>
              <td width="100%" valign="top" align="center">
                <span style="font-size:10px;font-weight:bold;color:Navy;">Learning Plan Archive</span><br>
              </td>
            </tr>
            <%if(ppgps.size() < 1){%>
              <tr>
                <td width="100%" valign="top" align='center' class="menuitem">
                  <a href='viewGrowthPlan.html?sy=<%=PPGP.getCurrentGrowthPlanYear()%>' style='color:#FF0000;' target="ppgpmain">Create <%=PPGP.getCurrentGrowthPlanYear()%> PLP</a>
                </td>
              </tr>
            <%}else{
            		if(ppgps.containsKey(PPGP.getCurrentGrowthPlanYear())){
            			if(!ppgps.containsKey(PPGP.getNextGrowthPlanYear())){
	            			PPGP ppgp = ppgps.get(PPGP.getCurrentGrowthPlanYear());
	            			if(ppgp.isSelfReflectionComplete()) {
	            				out.println("<tr><td width='100%' valign='top' align='center' class='menuitem'>"
	            						+ "<a href='viewGrowthPlan.html?sy=" + PPGP.getNextGrowthPlanYear() 
	            						+ "' style='color:#FF0000;' target='ppgpmain'>Create " + PPGP.getNextGrowthPlanYear() + " PLP</a>"
	            						+ "</td></tr>");
	            			}
	            			else {
	            				out.println("<tr><td width='100%' valign='top' align='center' style='padding:3px;color:red;font-weight:bold;'>"
	            						+ "You will be able to create your " + PPGP.getNextGrowthPlanYear() + " PLP after you complete "
	            						+ "the self reflections in your " + ppgp.getSchoolYear() + " PLP<hr /></td></tr>");
	            			}
            			}
            		}
            		else {
            			if(ppgps.containsKey(PPGP.getPreviousGrowthPlanYear())){
            				PPGP ppgp = ppgps.get(PPGP.getPreviousGrowthPlanYear());
            				if(ppgp.isSelfReflectionComplete()) {
              				out.println("<tr><td width='100%' valign='top' align='center' class='menuitem'>"
              						+ "<a href='viewGrowthPlan.html?sy=" + PPGP.getCurrentGrowthPlanYear() 
              						+ "' style='color:#FF0000;' target='ppgpmain'>Create " + PPGP.getCurrentGrowthPlanYear() + " PLP</a>"
              						+ "</td></tr>");
              			}
              			else {
              				out.println("<tr><td width='100%' valign='top' align='center' style='padding:3px;color:red;font-weight:bold;'>"
              						+ "You will be able to create your " + PPGP.getCurrentGrowthPlanYear() + " PLP after you complete "
              						+ "the self reflections in your " + ppgp.getSchoolYear() + " PLP<br/><hr /></td></tr>");
              			}
            			}
            			else {
            				out.println("<tr><td width='100%' valign='top' align='center' class='menuitem'>"
            						+ "<a href='viewGrowthPlan.html?sy=" + PPGP.getCurrentGrowthPlanYear() 
            						+ "' style='color:#FF0000;' target='ppgpmain'>Create " + PPGP.getCurrentGrowthPlanYear() + " PLP</a>"
            						+ "</td></tr>");
            			}
            		}
            		
            		ArrayList<PPGP> sorted = new ArrayList<PPGP>();
            		sorted.addAll(ppgps.values());
            		
            		Collections.sort(sorted, new SchoolYearComparator());  			
            		
                for(PPGP ppgp : sorted) {%>
                  <tr>
                    <td class="menuitem" width="100%" valign="top" align="center">
                      <a href="viewGrowthPlanSummary.html?ppgpid=<%= ppgp.getPPGPID()%>" target="ppgpmain"><%= ppgp.getSchoolYear()%></a>
                    </td>
                  </tr>
            <%  }
              }%>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>