
<%@ page language="java"
         import="com.awsd.security.*"%>

<%
  User usr = (User) session.getAttribute("usr");
  boolean isPrincipal = usr.checkRole("PRINCIPAL") || usr.checkRole("PRINCIPAL REPRESENTATIVE");
%>      
 <script type="text/javascript">
personnelmenu.init({
	mainmenuid: "principalMenu", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'personnelmenu', //class added to menu's outer DIV	
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})
</script>

                        




<div id="principalMenu" class="personnelmenu">
<ul>
<li><a href="admin_index.jsp"><img src="images/v2/home.png" width="15" height="15" alt="" border="0" align="left" />&nbsp;HOME</a></li>
 <%if(isPrincipal){%>
<li><a href="#"><img src="images/v2/addapplic.png" width="16" height="15" alt="" border="0" align="left" />&nbsp;Reference(s)</a>
 	<ul>
 	
  	<li><a href="addTeacherReference.html" target="_blank">Add Applicant Reference</a></li>
  	<%if(usr.checkRole("PRINCIPAL")){%>
  	<li><a href="principalCompletedReferences.html">Completed Reference(s)</a></li>
  	<%}%>
  	</ul>
</li>

<li><a href="#"><img src="images/v2/pp.png" width="16" height="16" alt="" border="0" align="left" />&nbsp;Position Planning</a></li>
<li><a href="principalJobShortlists.html"><img src="images/v2/jshortlist.png" width="15" height="15" alt="" border="0" align="left" />&nbsp;Job Short List(s)</a></li>
<%}%>
 <%if(isPrincipal || usr.checkRole("TEACHER SUB LIST ACCESS")){%> 
<li><a href="principalSubListShortlists.html"><img src="images/v2/sublistshort.png" width="18" height="15" alt="" border="0" align="left" />&nbsp;Sub List Short List(s)</a></li>
 <%}%>
 <%if(isPrincipal || usr.checkRole("SA CALL IN LIST ACCESS")){%>
<li><a href="availability_list.jsp"><img src="images/v2/stdasssublst.png" width="16" height="15" alt="" border="0" align="left" />&nbsp;Student Assistant Sub List</a></li>
<%}%>
</ul>
</div>
