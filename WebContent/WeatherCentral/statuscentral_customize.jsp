<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 java.util.regex.*,com.awsd.security.*,
                 com.awsd.weather.*,com.awsd.school.*,com.awsd.personnel.*"
         isThreadSafe="false"%>

<%
  SchoolSystems systems = null;
  SchoolSystem sys = null;
  Schools schools = null;
  School school = null;
  
  Iterator<SchoolSystem> sys_iter = null;
  Iterator<School> sch_iter = null;
  int sys_cnt_left = 10;
  StringBuffer cookie_str = null;
  Cookie cookies[] = null;
  Cookie cookie = null;
  String cookie_val = null;
  String cookie_email = "";
  boolean sysflag = false;
  boolean sflag = false;
  
  systems = new SchoolSystems();
  sys_iter = systems.iterator();

  if(request.getParameter("confirm") != null)
  {
  	boolean valid = false;
  	
    cookie_str = new StringBuffer("|");
    
    while(sys_iter.hasNext()) 
    {
      sys = sys_iter.next();
      sysflag = false;
      
      sch_iter = null;
      sch_iter = sys.getSchoolSystemSchools().iterator();
      while((sch_iter != null) && sch_iter.hasNext())
      {
        school = sch_iter.next();

        if(request.getParameter("sys_" + sys.getSchoolSystemID() + "_sch_" + school.getSchoolID()) != null)
        {
          cookie_str.append(school.getSchoolID() + "|");
          sysflag = true;
        }
      }

      if((request.getParameter("sys_" + sys.getSchoolSystemID()) != null) || sysflag)
      {
        cookie_str.append("SYS-" + sys.getSchoolSystemID() + "|");
      }
    }
    
    //email notifications
    if((request.getParameter("txt_NotificationEmailAddress") != null) 
    		&& (request.getParameter("txt_NotificationEmailAddressConfirm") != null)
    		&& request.getParameter("txt_NotificationEmailAddress").equalsIgnoreCase(request.getParameter("txt_NotificationEmailAddressConfirm"))) {
    	
    	cookie_str.append("EMAIL:" + request.getParameter("txt_NotificationEmailAddress") + "|");
    }
    
    //SMS notifications
    

    cookie = new Cookie("statuscentral_config", cookie_str.toString());
    cookie.setPath("/");
    //cookie.setDomain("esdnl.ca");
    cookie.setMaxAge(31536000); //one year
    response.addCookie(cookie);
    response.sendRedirect("statuscentral_customize.jsp?saved");
  }

  cookies = request.getCookies();
  if(cookies != null)
  {
    for(int i=0; i < cookies.length; i++)
    {
      if(cookies[i].getName().equalsIgnoreCase("statuscentral_config"))
      {
        cookie_val = cookies[i].getValue();
        break;
      }
    }
    
    if(cookie_val != null) {
    	Pattern pattern_cookie_email = Pattern.compile("*\\|EMAIL:([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))\\|*");
    	Matcher matcher_cookie_email = pattern_cookie_email.matcher(cookie_val);
    	if(matcher_cookie_email.matches())
    		cookie_email = matcher_cookie_email.group(1);
    	
    	
    }
  }
  int cnt = 0;
%>

<html>
<head>
<title>Eastern School District - School Status Central</title>
<link rel="stylesheet" href="//www.esdnl.ca/includes/weathercentral.css">
<link rel="stylesheet" href="//www.esdnl.ca/includes/memberservices-new.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js" type="text/javascript"></script>

<script type="text/javascript">
	$('document').ready(function(){
		
		$('#chk_EmailNotification').click(function(){
			$('#EmailNotificationInfo').toggle();
		});
		
		$('#chk_SMSNotification').click(function(){
			$('#SMSNotificationInfo').toggle();
		});
		
		if(<%= cookie_email != "" %>){
			$('#chk_EmailNotification').click();
			
			$('#txt_NotificationEmailAddress').val(<%= cookie_email %>);
			$('#txt_NotificationEmailAddressConfirm').val(<%= cookie_email %>);
		}
	});

  function Set_Cookie( name, value, expires, path, domain, secure ) 
  {
    // set time, it's in milliseconds
    var today = new Date();
    today.setTime( today.getTime() );
    
    /*
    if the expires variable is set, make the correct 
    expires time, the current script below will set 
    it for x number of days, to make it for hours, 
    delete * 24, for minutes, delete * 60 * 24
    */
    if ( expires )
    {
    expires = expires * 1000 * 60 * 60 * 24;
    }
    var expires_date = new Date( today.getTime() + (expires) );
    
    document.cookie = name + "=" +escape( value ) +
    ( ( expires ) ? ";expires=" + expires_date.toGMTString() : "" ) + 
    ( ( path ) ? ";path=" + path : "" ) + 
    ( ( domain ) ? ";domain=" + domain : "" ) +
    ( ( secure ) ? ";secure" : "" );
  }

  function selectSchoolSystemSchools(sys)
  {
    var search = "sys_" + sys + "_sch_";

    var state = document.form_customize.elements['sys_'+sys].checked;
    
    for(var i=0; i < document.form_customize.elements.length; i++)
    {
      if(document.form_customize.elements[i].name.indexOf(search) >=0)
      {
        document.form_customize.elements[i].checked=state;
      }
    }
  }
</script>
</head>
<body>
  <form name="form_customize">
  <input type="hidden" name="confirm" value="true">
  <%if(request.getParameter("saved") != null){%>
    <br>
    <table align="center" width="20%" cellpadding="0" cellspacing="0" border="0" style="padding:2px;border:1px solid #FF0000;"> 
      <tr>
        <td align="center" valign="middle" style="font-size:xx-small;font-weight:bold;color:#FF0000;">configuration saved.</td>
      </tr>
    </table><br>
  <%}%>
	<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" style="border:1px solid #C1CDD8;">
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="top">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td width="100%" align="left" valign="top">
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td valign="top" width="50%">
                        <%=cookie_val %><br/>
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <%while(sys_iter.hasNext()) {
                        sys = (SchoolSystem) sys_iter.next();
                        sysflag = true;
                      %>
                        <tr height="25" style="padding-left:5px; background-color: #DBEAF5;">
                          <td align="left" valign="middle" colspan="2">
                            <input type="checkbox" name="sys_<%=sys.getSchoolSystemID()%>" onclick="selectSchoolSystemSchools('<%=sys.getSchoolSystemID()%>');">&nbsp;<span class='weatherCentralSchoolSystem'><%=sys.getSchoolSystemName()%></span>
                          </td>
                        </tr>
                        <tr>
                          <td width="100%" height="1" align="left" valign="middle" style="background-color: #C1CDD8;" colspan="2">
                            <img src="//www.esdnl.ca/images/spacer.gif" width="1" height="1"><BR>
                          </td>
                        </tr>
                        <%sch_iter = null;
                          schools = sys.getSchoolSystemSchools();
                          cnt = schools.size()/2;
                          if((schools.size() % 2) != 0)
                            cnt++;
                          sch_iter = schools.iterator();
                        %>
                        <tr><td width="50%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <%for(int i=0; ((sch_iter != null) && sch_iter.hasNext() && (i < cnt)); i++){
                            school = (School) sch_iter.next();
                            sflag = false;
                            if((cookie_val != null) && !cookie_val.equals("") && (cookie_val.indexOf("|" + school.getSchoolID() + "|") >= 0))
                            {
                              sflag = true;
                            }
                            else
                            {
                              sysflag = false;
                            }
                          %><tr>
                              <td width="100%" valign="middle" align="left" style="padding-left:25px;">
                                <input type="checkbox" name="sys_<%=sys.getSchoolSystemID()%>_sch_<%=school.getSchoolID()%>" <%=sflag?"CHECKED":""%>>&nbsp;<span class="normalGrey10pxText"><%=school.getSchoolName()%></span>
                              </td>
                            </tr>
                        <%}%>
                        </table></td><td width="50%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <%while((sch_iter != null) && sch_iter.hasNext()){
                            school = (School) sch_iter.next();
                            sflag = false;
                            if((cookie_val != null) && !cookie_val.equals("") && (cookie_val.indexOf("|" + school.getSchoolID() + "|") >= 0))
                            {
                              sflag = true;
                            }
                            else
                            {
                              sysflag = false;
                            }
                          %><tr>
                              <td width="100%" valign="middle" align="left" style="padding-left:25px;">
                                <input type="checkbox" name="sys_<%=sys.getSchoolSystemID()%>_sch_<%=school.getSchoolID()%>" <%=sflag?"CHECKED":""%>>&nbsp;<span class="normalGrey10pxText"><%=school.getSchoolName()%></span>
                              </td>
                            </tr>
                        <%}%>
                        <%if(schools.size() % 2 != 0){%>
                          <td>&nbsp;</td>
                        <%}%>
                        </table></td></tr>
                        <%if(sysflag){%>
                            <script type="text/javascript">document.form_customize.elements['<%="sys_" + sys.getSchoolSystemID()%>'].checked = true;</script>
                        <%}
                      }%>
                      </table></td></tr>
                      <tr height="25" style="padding-left:10px; background-color: #DBEAF5;">
                        <td align="left" valign="middle" colspan="2" style="border-bottom:1px solid #C1CDD8;">
                          <span class='weatherCentralSchoolSystem'>NOTIFICATION OPTIONS</span>
                        </td>
                      </tr>
                      <tr>
                        <td width="100%" height="1" align="left" valign="middle" style="background-color: #C1CDD8;" colspan="2">
                          <img src="//www.esdnl.ca/images/spacer.gif" width="1" height="1"><BR>
                        </td>
                      </tr>
                      <tr>
                      	<td style='padding:5px; padding-left: 23px;' >
                 					<table cellspacing='0' cellpadding='2'>
                 						<tr>
                 							<td><input type='checkbox' id='chk_EmailNotification' name='chk_EmailNotification' /><span style='font-size:10px;font-weight:bold;'>Email Notification</span></td>
                 						</tr>
                 						<tr id='EmailNotificationInfo' style='display:none;'>
                 							<td style='padding-left: 35px;'>
	                 							<table cellspacing='0' cellpadding='2'>
			                 						<tr>
			                 							<td>
			                 								<span class="normalGrey10pxText">Email Address:</span>
			                 							</td>
			                 							<td>
			                 								<input id="txt_NotificationEmailAddress" name="txt_NotificationEmailAddress" type='text' style='width:250px;'  /> 
			                 							</td>
			                 						</tr>
			                 						<tr>
			                 							<td>
			                 								<span class="normalGrey10pxText">Confirm:</span>
			                 							</td>
			                 							<td>
			                 								<input id="txt_NotificationEmailAddressConfirm" name="txt_NotificationEmailAddressConfirm" type='text' style='width:250px;' /> 
			                 							</td>
			                 						</tr>
		                 						</table>
	                 						</td>
                 						</tr>
                 					</table>
                      	</td>
                      </tr>
                      <tr>
                      	<td style='padding:5px; padding-left: 23px;'>
                 					<table cellspacing='0' cellpadding='2'>
                 						<tr>
                 							<td><input type='checkbox' id='chk_SMSNotification' name='chk_SMSNotification' /><span style='font-size:10px;font-weight:bold;'>SMS Notification</span></td>
                 						</tr>
                 						<tr id='SMSNotificationInfo' style='display:none;'>
                 							<td style='padding-left: 35px;'>
	                 							<table cellspacing='0' cellpadding='2'>
			                 						<tr>
			                 							<td>
			                 								<span class="normalGrey10pxText">Cell Phone:</span>
			                 							</td>
			                 							<td>
			                 								<input id="txt_NotificationCellphone" name="txt_NotificationCellphone" type='text' /> 
			                 							</td>
			                 						</tr>
			                 						<tr>
			                 							<td>
			                 								<span class="normalGrey10pxText">Carrier:</span>
			                 							</td>
			                 							<td>
			                 								<select id="lst_NotificationCellphoneCarrier" name='lst_NotificationCellphoneCarrier'>
			                 									<option value="@pcs.rogers.com">Rogers Wireless</option>
			                 									<option value="@fido.ca">Fido</option>
			                 									<option value="@msg.telus.com">Telus</option>
			                 									<option value="@txt.bell.ca">Bell Mobility</option>
			                 									<option value="@msg.koodomobile.com">Kudo Mobile</option>
			                 								</select>
			                 							</td>
			                 						</tr>
		                 						</table>
	                 						</td>
                 						</tr>
                 					</table>
                      	</td>
                      </tr>
                      <tr style="background-color: #DBEAF5;">
                        <td colspan="2" valign="middle" align="right" style="padding:2px;border-top:1px solid #C1CDD8;">
                          <input type="submit" value="submit">
                        </td>
                      </tr>
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
  <%if(request.getParameter("saved") != null){%>
    <br>
    <table align="center" width="20%" cellpadding="0" cellspacing="0" border="0" style="padding:2px;border:1px solid #FF0000;"> 
      <tr>
        <td align="center" valign="middle" style="font-size:xx-small;font-weight:bold;color:#FF0000;">configuration saved.</td>
      </tr>
    </table><br>
  <%}%>
  </form>
</body>
</html>
