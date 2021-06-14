<%@ page contentType="text/html; charset=iso-8859-1" language="java" 
         session="true"
         isThreadSafe="false"
         import="java.sql.*,
                 java.util.*,
                 java.util.regex.*,
                 java.text.*,
                 java.io.*,com.awsd.personnel.*,
                 com.awsd.weather.*,com.awsd.security.*,com.awsd.school.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEATHERCENTRAL-PRINCIPAL-ADMINVIEW,WEATHERCENTRAL-GLOBAL-ADMIN" />


<%
	User usr = (User) session.getAttribute("usr");
	SchoolSystem[] systems = null;
	SchoolSystem sys = null;
	School school = null;
	Iterator<School> sch_iter = null;
	Iterator<ClosureStatus> stat_iter = null;
	ClosureStatuses stats = null;
	ClosureStatus stat = null;
	ClosureStatus sstat = null;
	Personnel prec = null;
	int cnt;

  if(request.getParameter("pid") != null)
    prec = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("pid")));
  else
    prec = usr.getPersonnel();
    
  systems = (SchoolSystem[]) request.getAttribute("SchoolSystems");
    
  stats = new ClosureStatuses();
  cnt = 0;
  
  String today = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
%>


<html>
<head>
<title>School Status Administration</title>
<!-- HAVE TO LOAD THESE HERE AS THEY WILL NOT WORK USING DECORATOR LOAD for date-->
     <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>   

<script>

$('document').ready(function(){
	
	$(".loadPage").show();
	$(".loadingTable").css("display","none");
	$("#loadingSpinner").css("display","none");	
	
});

	function toggle(target) {
		$('#' + target).toggle();
	}
		
  $(function(){
	  $('.txt-status-note').keypress(function(event){
		  var keycode = event.which;
		  
		  if ((keycode == 8)||(keycode == 13)||(keycode == 39)||(keycode == 46)) {
			  return true;
	    }
	    else if($(this).val().length >= 150) {
		  	alert('Note cannot exceed 150 characters.');
		  	event.preventDefault();
	    }
	    else{
	    	return true;
	    }
	  });
	  
	  $( ".datefield" ).datepicker({
		      changeMonth: true,//this option for allowing user to select month
		      changeYear: true, //this option for allowing user to select from year range
		      dateFormat: "dd/mm/yy",
		      maxDate: '+2y',
		      minDate: '-14d'
		    });  
  });
</script>
</head>
<body>
<div class="siteHeaderGreen">School Status Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>
This will take a few moments!
</div>		

<div style="display:none;border:0px" class="loadPage"> 
  
  
  <form name="schoolstatus" method="post" action="updateSchoolClosureStatus.html">
  <input type="hidden" name="apply_all" value="0">
  <%if(request.getParameter("pid") != null){%>
    <input type="hidden" name="pid" value="<%=prec.getPersonnelID()%>">
  <%}%>
	
	
	<div class="alert alert-danger">
	<b>PLEASE NOTE:</b> Live Updates to school status are delayed up to 5 minutes after you submit your change here. 
	Please wait 5 minutes to reload/refresh the NLESD school status website to see your changes.
	</div>
	
  
  
              <%if((systems != null) && (systems.length > 0)){%>
                <%for(int i=0; i < systems.length; i++){%>
  						
  						
  						<b>System Name:</b> <%=systems[i].getSchoolSystemName()%><br/>
  						<b>Current Administrator(s):</b>
  						<span style="text-transform:capitalize;">
  						<%=(systems[i].getSchoolSystemAdmin() != null)?systems[i].getSchoolSystemAdmin().getFullNameReverse():"UNASSIGNED"%>
  							
  							<%
									Personnel[] tmp = systems[i].getSchoolSystemAdminBackup();
									for(int k=0; ((tmp != null)&&( k < tmp.length));k++) {
										if(tmp[k] != null)
											out.println("/" + tmp[k].getFullNameReverse());
								}
  							%>
  						</span>	
  							
  				<br/><br/>
  				Below are the list of school(s) in your school system. Click on the school name to update status details.
  				
  				<br/><br/>
  				<div id="accordionSch">
                <%
                  sch_iter = systems[i].getSchoolSystemSchools().iterator();
                  
                  while(sch_iter.hasNext()){
                  school = (School)sch_iter.next();
                  sstat = school.getSchoolClosureStatus();
                %>
                 <div class="card">			
                  <div class="card-header siteSubHeaderBlue">
                  <a class="card-link card<%=cnt%>" data-toggle="collapse" href="#collapse<%=cnt%>"><span id="icon<%=cnt%>"><i class='fas fa-folder'></i></span> <%=school.getSchoolName()%></a>
                  <div style="float:right">
                  <c:choose>
			               	<c:when test="${sstat.getClosureStatusDescription() eq 'School open'}">
			               	<span style="color:Green;">OPEN</span>
			               	 </c:when>
			               	 <c:otherwise>
			               	 <span style="color:Red;">${sstat.getClosureStatusDescription()}</span>
			               	 </c:otherwise>
							</c:choose>
                  </div>
                  </div>
                  	<div id="collapse<%=cnt%>" class="collapse" data-parent="#accordionSch">
                  	<script>
                  	if(<%=cnt%> == 0 ) {                  		
                  		$("#collapse<%=cnt%>").addClass("show");  
                  		 $("#icon<%=cnt%>").html("<i class='fas fa-folder-open'></i>");
                  	};        
                  	
                  	 $('.card<%=cnt%>').on("click", function(e){       
        				 if( $("#collapse<%=cnt%>").hasClass("show")) {
        	                	$("#icon<%=cnt%>").html("<i class='fas fa-folder'></i>");
        	                } else {                	
        	                	 $("#icon<%=cnt%>").html("<i class='fas fa-folder-open'></i>");
        	                }                	 
        	                	 e.preventDefault();                	 
        	                	});       
                  	
                  	</script>
					 <div class="card-body">
					<b>Current Status:</b> 
			                  <c:choose>
			               	<c:when test="${sstat.getClosureStatusDescription() eq 'School open'}">
			               	<span style="color:Green;">OPEN</span>
			               	 </c:when>
			               	 <c:otherwise>
			               	 <span style="color:Red;">${sstat.getClosureStatusDescription()}</span>
			               	 </c:otherwise>
							</c:choose>
                  
                   <br/>
                   <div class="row container-fluid" style="padding-top:5px;">
					<div class="col-lg-6 col-12"> 
               		<b>New Status:</b>
                    <select class="form-control form-control-sm" name="<%="SCH_" + school.getSchoolID()%>">
                    <%stat_iter = null;
                      stat_iter = stats.iterator();
                      while(stat_iter.hasNext()){
                        stat = (ClosureStatus) stat_iter.next(); 
                    %>  <option value="<%=stat.getClosureStatusID()%>" <%=(sstat.getClosureStatusID()==stat.getClosureStatusID())?"SELECTED":""%>><%=((!(school.getSchoolName().endsWith("Office")))?stat.getClosureStatusDescription():Pattern.compile("School").matcher(stat.getClosureStatusDescription()).replaceAll("Office"))%></option>
                    <%}%>
                    </select>
                    </div>
                    <div class="col-lg-6 col-12">
                    <b>Notice Date:</b>  ( Repeat daily until notice date?  <input type="checkbox" name="<%="REPEAT_" + school.getSchoolID()%>"> )
                    <input class="form-control form-control-sm datefield" type="text" name="<%="START_DATE_" + school.getSchoolID()%>" value="<%=today%>" readonly="readonly"/>
                    </div>
                    </div>
                     <div class="row container-fluid" style="padding-top:5px;">
                      <div class="col-lg-6 col-12">
                    <b>Note:</b> (optional, to be posted to public web site)<br/>
	                  <textarea class='form-control  form-control-sm' name="<%="NOTE_" + school.getSchoolID()%>"><%=(sstat.getSchoolClosureNote()!=null)?sstat.getSchoolClosureNote():""%></textarea>
	            </div>
                     <div class="col-lg-6 col-12">
                    <b>Is this weather related?</b> 
                  <input type="checkbox" id="<%="WEATHER_RELATED_" + school.getSchoolID()%>" 
                  	name="<%="WEATHER_RELATED_" + school.getSchoolID()%>"
                  	onclick="this.checked ? $('#weather-related-closure-panel-<%=school.getSchoolID()%>').show() : $('#weather-related-closure-panel-<%=school.getSchoolID()%>').hide()"
                  	<%= sstat.isWeatherRelated() ? " CHECKED" : "" %>
                  	/>
               <div id='weather-related-closure-panel-<%=school.getSchoolID()%>' style='display:<%=sstat.isWeatherRelated() ? "inline" : "none" %>;'>
	            <b>Weather Related Rationale</b> (<span style="color:Red;">internal use only</span>):
	                  <textarea class='txt-status-note form-control  form-control-sm' id="<%="RATIONALE_" + school.getSchoolID()%>" name="<%="RATIONALE_" + school.getSchoolID()%>" ><%=(sstat.getRationale()!=null)?sstat.getRationale():""%></textarea>
	               </div>
                  </div>
                   </div>
	            <br/><br/>
	               <div align="center"><%=(cnt++==0)?"<a class='btn btn-sm btn-danger' href='javascript:document.schoolstatus.apply_all.value=\"" + systems[i].getSchoolSystemID() + "\";document.schoolstatus.submit();' class='11pxBlueLink'>Apply to All Schools in this System</a>":""%></div>
                
                  </div>
                  </div>
	                   </div>
	                   	<br/>                 
                <%}%>
              
              </div>
           
               <%cnt=0;%>
              <%}%>
              <%} else {
                sys = (SchoolSystem) prec.getSchool().getSchoolSystem();
                school = prec.getSchool();
                sstat = school.getSchoolClosureStatus();%>
                
                
               <b>System Name:</b> <%=sys.getSchoolSystemName()%><br/>
  				<b>Current Administrator(s):</b> 
  				<span style="text-transform:capitalize;">
  				<%=(sys.getSchoolSystemAdmin() != null)?sys.getSchoolSystemAdmin().getFullNameReverse():"UNASSIGNED"%>
  							<% 
  								Personnel[] tmp = sys.getSchoolSystemAdminBackup();
  								for(int i=0; ((tmp != null)&&( i < tmp.length));i++)
  									if(tmp[i] != null)
  										out.println("/" + tmp[i].getFullNameReverse());
  							%>
  				</span>		
  				<br/><br/>	
<div class="card">			
                  <div class="card-header siteSubHeaderBlue"><%=school.getSchoolName()%>
            	 <div style="float:right">
			                  <c:choose>
			               	<c:when test="${sstat.getClosureStatusDescription() eq 'School open'}">
			               	<span style="color:Green;">OPEN</span>
			               	 </c:when>
			               	 <c:otherwise>
			               	 <span style="color:Red;">${sstat.getClosureStatusDescription()}</span>
			               	 </c:otherwise>
							</c:choose>
					</div></div>
							
           <div class="card-body">
           <div class="row container-fluid" style="padding-top:5px;">
					<div class="col-lg-6 col-12">          
           			<b>New Status:</b>
                    <select class="form-control form-control-sm" name="<%="SCH_" + school.getSchoolID()%>">
                    <%stat_iter = stats.iterator();
                      while(stat_iter.hasNext()){
                        stat = (ClosureStatus) stat_iter.next();
                    %>  <option value="<%=stat.getClosureStatusID()%>" <%=(sstat.getClosureStatusID()==stat.getClosureStatusID())?"SELECTED":""%>><%=((!(school.getSchoolName().endsWith("Office")))?stat.getClosureStatusDescription():Pattern.compile("School").matcher(stat.getClosureStatusDescription()).replaceAll("Office"))%></option>
                    <%}%>
                    </select>
                     </div>
                    <div class="col-lg-6 col-12">
                    <b>Notice Date:</b>  ( Repeat daily until notice date?<input type="checkbox" name="<%="REPEAT_" + school.getSchoolID()%>"> )
                    <input class="datefield form-control form-control-sm" type="text" name="<%="START_DATE_" + school.getSchoolID()%>" value="<%=today%>" readonly="readonly">
                   </div>
                    </div>
                     <div class="row container-fluid" style="padding-top:5px;">
                      <div class="col-lg-6 col-12">
                  	<b>Note:</b> (optional, to be posted to public web site.)<br/>
	                  <textarea class='txt-status-note form-control  form-control-sm'' id="<%="NOTE_" + school.getSchoolID()%>" name="<%="NOTE_" + school.getSchoolID()%>" rows="3" cols="50" ><%=(sstat.getSchoolClosureNote()!=null)?sstat.getSchoolClosureNote():""%></textarea>
	                 </div>
                     <div class="col-lg-6 col-12">                  
                  <b>Is this weather related?</b>  <input type="checkbox" id="<%="WEATHER_RELATED_" + school.getSchoolID()%>" 
                  	name="<%="WEATHER_RELATED_" + school.getSchoolID()%>"
                  	onclick="this.checked ? $('#weather-related-closure-panel-<%=school.getSchoolID()%>').show() : $('#weather-related-closure-panel-<%=school.getSchoolID()%>').hide()"
                  	<%= sstat.isWeatherRelated() ? " CHECKED" : "" %>
                  	/>
                         
                  <div id='weather-related-closure-panel-<%=school.getSchoolID()%>' style='display:<%=sstat.isWeatherRelated() ? "inline" : "none" %>;'>
	                <b>Weather Related Rationale</b> (<span style="color:Red;">internal use only</span>):
	                  <textarea class='txt-status-note form-control  form-control-sm'' id="<%="RATIONALE_" + school.getSchoolID()%>" name="<%="RATIONALE_" + school.getSchoolID()%>" rows="3" cols="50" ><%=(sstat.getRationale()!=null)?sstat.getRationale():""%></textarea>
	                 
                  </div>
                  </div>
                   </div>
                  </div>
              </div>
                  
                  
              <%}%>
              
              <br/><br/>
              <div align="center">
              <input class="btn btn-success btn-sm" type="submit" onClick="document.schoolstatus.submit();" />              
              <a class="btn btn-danger btn-sm" href="../navigate.jsp">Cancel</a>
              </div>
           
	</form>
	
	</div>
	
</body>

</html>
