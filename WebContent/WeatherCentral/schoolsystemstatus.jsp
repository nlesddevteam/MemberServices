<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,com.awsd.security.*,
                 com.awsd.weather.*,com.awsd.school.*,com.awsd.personnel.*"
         isThreadSafe="false"%>

<%!
  SchoolSystems systems = null;
  SchoolSystem sys = null;
  ClosureStatus stat = null;
  School school = null;
  
  Iterator sys_iter = null;
  Iterator sch_iter = null;

  private String cssClass(int id)
  {
    String css;
    
    switch(id)
    {
      case 4:
      case 7:
      case 10:
      case 11:
      case 21:
      case 22:
      case 62:
      case 84:
      case 102:
      case 123:
        css = "weatherCentralStatusClosed";
        break;
      case 6:
      case 5:
      case 42:
      case 63:
        css = "weatherCentralStatusDelayed";
        break;
      case 9:
        css = "weatherCentralStatusOpen";
        break;
      default:
        css ="weatherCentralStatusOpen";
    }

    return css;
  }
%>
<%
  Calendar start_cal = Calendar.getInstance();
  Calendar end_cal = null;

  System.out.println("ALL VIEW LOADING[" + request.getRemoteAddr() + "]....");
  
  systems = new SchoolSystems();
  sys_iter = systems.iterator();

  end_cal = Calendar.getInstance();

  System.out.println("....GOT DATA[" + request.getRemoteAddr() + "] (" + (end_cal.getTimeInMillis() - start_cal.getTimeInMillis()) + ")");
%>


<html>
<head>
<title>Eastern School District - School Status Central</title>
<link rel="stylesheet" href="http://www.esdnl.ca/includes/weathercentral.css">
<link rel="stylesheet" href="http://www.esdnl.ca/includes/memberservices-new.css">
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" style="border:1px solid #C1CDD8;">
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="top">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td width="100%" align="left" valign="top">
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <%while(sys_iter.hasNext()) {
                        sys = (SchoolSystem) sys_iter.next();
                      %>
                        <tr height="25" style="background-color: #DBEAF5;">
                          <td colspan="2" align="left" valign="middle">
                            &nbsp;<span class='weatherCentralSchoolSystem'><%=sys.getSchoolSystemName()%></span>
                          </td>
                        </tr>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" colspan="4" style="background-color: #C1CDD8;">
                            <img src="http://www.esdnl.ca/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>
                        <%sch_iter = null;
                          sch_iter = sys.getSchoolSystemSchools().iterator();
                          while((sch_iter != null) && sch_iter.hasNext()){
                            school = (School) sch_iter.next();
                            stat = school.getSchoolClosureStatus();
                        %><tr>
                            <td width="50%" align="left" valign="top">
                              &nbsp;<span class="normalGrey10pxText"><%=school.getSchoolName()%></span>
                            </td>
                            <td width="*" align="left" valign="top">
                            <%if(stat.getSchoolClosureNote() != null){%>
                              <span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription() + "<BR>[Note:&nbsp;"+stat.getSchoolClosureNote()+"]"%></span>
                            <%} else {%>
                              <span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription()%></span>
                            <%}%>
                            </td>
                          </tr>
                        <%}
                      }%>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
