<%@ page language="java"%>
<script type="text/javascript">
personnelmenu.init({
	mainmenuid: "assistantPrincipalMenu", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'personnelmenu', //class added to menu's outer DIV	
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})
</script>
<div id="assistantPrincipalMenu" class="personnelmenu">
<ul>
<li><a href=""><img src="images/v2/home.png" width="15" height="15" alt="" border="0" align="left" />&nbsp;HOME</a></li>
<li><a href="#"><img src="images/v2/addapplic.png" width="16" height="15" alt="" border="0" align="left" />&nbsp;Reference(s)</a>
 	<ul>
  	<li><a href="addTeacherReference.html">Add Applicant Reference</a></li>
  	<li><a href="principalCompletedReferences.html">Completed Reference(s)</a></li>
   </ul>
</li>
<li><a href="principalJobShortlists.html"><img src="images/v2/jshortlist.png" width="15" height="15" alt="" border="0" align="left" />&nbsp;View Job Short List(s)</a></li>
<li><a href="principalSubListShortlists.html"><img src="images/v2/sublistshort.png" width="18" height="15" alt="" border="0" align="left" />&nbsp;View Sub List Short List(s)</a></li>
<li><a href="availability_list.jsp"><img src="images/v2/stdasst.png" width="22" height="15" alt="" border="0" align="left" />&nbsp;Student Assistant List</a></li>
</ul>
</div>