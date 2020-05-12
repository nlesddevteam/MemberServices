<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

<html>

	<head>
	<title>MyHRP Applicant Profiling System</title>
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:15%;}
.tableResult {font-weight:normal;width:85%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>	
		<script type="text/javascript">
			$('document').ready(function(){
										
					$('.datefield').datepicker({
						yearRange: "-75:+0", 
						autoSize: true,
						showOn: 'focus',
						showAnim: 'drop',
						dateFormat: 'dd/mm/yy',
						changeMonth: true,
						changeYear: true
					});
					
					
					
					$('#btn-add-offence').click(function(){
						
						
						if($("#txt-offence-date-1").val()=="" || $("#txt-court-location-1").val()=="" || $("#txt-conviction-1").val()=="" ) {
							
							$("#numCODsErr").css('display','block').delay(5000).fadeOut();
							
						return false;
						
						} else {
							
						var cnt = parseInt($('#hdn-offences-listed').val()) + 1;
						
						$("#list-of-offenses tbody")				
						.append('<tr><td rowspan=3 width=5%>'+cnt+'</td><td width=20%><b>Date*:</b></td><td width=75%><input type="text" id="txt-offence-date-'+cnt+'" name="txt_offence_date_'+cnt+'" class="form-control datefield" /></td></tr>')
						.append('<tr><td width=20%><b>Court Location*:</b></td><td width=75%><input type="text" id="txt-court-location-'+cnt+'" name="txt_court_location_'+cnt+'" class="form-control"/></td></tr>')
						.append('<tr><td width=20%><b>Conviction/Charge*:</b></td><td width=75%><input type="text" id="txt-conviction-'+cnt+'" name="txt_conviction_'+cnt+'" class="form-control" /></td></tr>')						
						.append('<tr><td colspan=4 style="border-top:1px solid silver;">&nbsp;</tr>');
					
						
					$('#txt-offence-date-' + cnt).datepicker({
							yearRange: "-65:+0", 
							autoSize: true,
							showOn: 'focus',
							showAnim: 'drop',
							dateFormat: 'dd/mm/yy',
							changeMonth: true,
							changeYear: true
						});
						
						$('#hdn-offences-listed').val(cnt);
						}
					});
			});
		</script>
	</head>
	
	<body>
	<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">10A</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
SECTION 10A: Editing your Teacher/Educator HR Application Profile 
</div>

<br/>Fields marked with * are required.<br/>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>10A. CRIMINAL OFFENSE DECLARATION FORM</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">
	
	                    <form id="frmPostJob" action="applicantRegistration.html?step=10COD" method="post">
	                       <input type='hidden' id='hdn-offences-listed' name='num_offences_listed' value='1' />
	                        <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
                                
                                <tr>
                                  	<td class="tableTitleL">NAME*:</td>         
	                              	<td class="tableResultL">${APPLICANT.fullName}</td>  
	                               	<td class="tableTitleR">DATE OF BIRTH*:</td>         
	                              	<td class="tableResultR">
                         				<c:choose>
                                   			<c:when test="${APPLICANT.DOB ne null}">
                                   				${APPLICANT.DOBFormatted}
                                   			</c:when>
                                   			<c:otherwise>
                                   				<input type='text' id='txt-dob' name='txt_dob' style='padding:2px;' class='form-control datefield' />
                                   			</c:otherwise>	
                                   		</c:choose>
	                              	</td>   				
					            </tr>
					            <tr>
					              	<td class="tableTitleL">POSITION*:</td>         
	                              	<td class="tableResultL"><input type='text' id='txt-position' name='txt_position' class='form-control' style='padding:2px;'/></td>  
	                               	<td class="tableTitleR">LOCATION/SCHOOL:</td>         
	                              	<td class="tableResultR"><input type='text' id='txt-location' name='txt_location' class='form-control' style='padding:2px;'/></td>                       		
					            </tr>                        	
					             <tr>
					             <td colspan=4 style="border-top:1px solid Silver;background-color:#FFFFFF;">&nbsp;</td>
					             </tr>                       
					           </tbody>
					          </table>                       
					           
					          <b>I DECLARE</b>, since the last Criminal Reference Check (CRC) provided to Newfoundland &amp; Labrador English School District, or since the last Criminal Offence Declaration (COD) given by me to the district, that:
                       							<ol style='padding-top:5px;list-style-type: lower-alpha;'>
                       								<li>
                       									I have <b>no</b> convictions under the <i>Criminal Code of Canada</i> up and including the date of this
                       									declaration for which a pardon has not been issued or granted under the <i>Criminal Records Act (Canada)</i>.
                       								</li>
                       								<li>
                       									I have <b>no</b> charges pending under the <i>Criminal Code of Canada</i> up to and including the date
                       									of this declaration.
                       								</li>
                       							</ol>
                       					
                       		
                       		
                       		<b>OR</b><br/><br/>
                       		
                       				
                       				
                       		I have the following convictions/charges for offences under the <i>Criminal Code of Canada</i>
                       		for which a pardon under the <i>Criminal Records Act (Canada)</i> has <b>not</b> been issued or granted.<br/><br/>
                       						
                       		
		<div style="font-size:14px;font-weight:bold;margin-bottom:5px;">List of Offences:</div>
                       						
                       						
                       							<table id="list-of-offenses" class="table table-striped table-condensed" style="font-size:12px;color:Grey;">
                       								<tbody>
                       								    	
                       									<tr><td rowspan=3 width=5%>1.</td><td width=20%><b>Offense Date:</b></td><td width=75%><input type='text' id='txt-offence-date-1' name='txt_offence_date_1' class='form-control datefield' /></td></tr>
                       									<tr><td width=20%><b>Court Location:</b></td><td width=75%><input type='text' id='txt-court-location-1' name='txt_court_location_1' class='form-control'/></td></tr>
                       									<tr><td width=20%><b>Conviction/Charge:</b></td><td width=75%><input type='text' id='txt-conviction-1' name='txt_conviction_1' class='form-control' /></td></tr>
                       								    <tr><td colspan=4 style="border-top:1px solid silver;">&nbsp;</tr>
                       								</tbody>
                       							</table>
                       						
                       					<div class="alert alert-danger" id="numCODsErr" style="text-align:center;display:none;">ERROR: You have not filled out the first offense. You cannot add another offense until you fill out the first.</div>
                   
                  							             							
                  					
                   							<div align="center">
                   							<input id='btn-add-offence' class="btn btn-xs btn-info" type='button' value='Add Offence Listing' /> 
                   							<input type="submit" class="btn btn-xs btn-success" value="Submit Declaration" />
                   							 <a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a></div>
	                                  			
	                                </form>
	                          
	                              
   </div></div></div></div>                           
	                              
   <%if(request.getAttribute("msg")!=null){%>
	<script>$(".msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$(".msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>    	                              
	                              
	</body>
</html>
