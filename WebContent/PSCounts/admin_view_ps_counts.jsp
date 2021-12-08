<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*,com.awsd.school.*,com.awsd.personnel.*,com.awsd.school.School"%>
<%@ page import='com.nlesd.psimport.bean.*' %>
<%@ page import='com.nlesd.psimport.dao.*' %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%
PSClassInformationBean psbean=null;
String school = null;
if(request.getAttribute("PSDATA") != null){
	psbean = (PSClassInformationBean) request.getAttribute("PSDATA");
	school = (String) request.getAttribute("SCHOOL");
}
%>   


<html>
  <head>
    <title>Member Services - PowerSchool Class Data</title>    
 
  </head>
  <body>
  		<c:set var="school" value='<%= school %>' />
		<c:set var="psinfo" value='<%= psbean %>' />
<!-- TEACHER NAME UPDATE -->

<div class="container-fluid">		
				<div class="row no-print">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">
    Using the following screen you can select a school and view the class sizes that has been calculated from the current PowerSchool data.
        			</div>
  				</div>
 
 <br/>   
  	<div class="panel panel-info">  		
  			<div class="panel-body" style="font-size:12px;">
  				<form action='viewPSCounts.html' method='POST' class="no-print">
  				
  					<div class="row">		  
				  	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
						  	
						    	<select id="selschools" name="selschools" class="form-control">
						    	<option value="0">***** PLEASE SELECT SCHOOL *****</option>
						    		<c:forEach var="s" items="${schools}">
        									<option value="${s.schoolID }">${s.schoolName}</option>
   									</c:forEach>
						    	</select>
						    	<input type="hidden" id="hiddata" value="${psinfo eq null ? 0:1 }">
						  	
					</div>
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
				  	 <input type='submit' value='Get Data' class="btn btn-sm btn-primary"> 
                    &nbsp;  <a href="#" class="btn btn-dark btn-sm" role="button"  title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img src=/includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print</a>
                     &nbsp; <a href="/MemberServices/navigate.jsp" class="btn btn-danger btn-sm">Exit to Member Services</a>
                   </div>
                   </div>
                    </form>    					
 					

<div id="divcounts" style="display:none;">
      
<c:choose> 
										<c:when test="${ not empty psinfo }">
											<div style="clear:both;"></div>	
																				
											<c:set var="knum" value="1"/>
											<c:set var="gr1num" value="1"/>
											<c:set var="gr2num" value="1"/>
											<c:set var="gr3num" value="1"/>
											<c:set var="gr4num" value="1"/>
											<c:set var="gr5num" value="1"/>
											<c:set var="gr6num" value="1"/>
											<c:set var="gr7num" value="1"/>
											<c:set var="gr8num" value="1"/>
											<c:set var="gr9num" value="1"/>
											<c:set var="gr10num" value="1"/>
											<c:set var="totalStd" value="0"/>
											
											<div  style="font-size:16px;color:Green;font-weight:bold;text-align:center;"><%=school %> Class Allocation Details</div><br/>
											
											<div class="container" style='width: 100%; padding:0px;text-align:center;'>
										The following data is from PowerSchool information as entered by the school. Only classes with 1 or more students will be listed.<br/><br/>
										
										<c:choose> 
<c:when test="${ not empty psinfo }">

<c:if test="${ psinfo.scBean ne null}">
												
												<table class="table table-sm table-condensed table-bordered" style="margin:0 auto;font-size:12px;max-width:1024px;width:100%;text-align:center;">
																								<thead class="thead-dark">		
												<tr>
<c:if test="${ psinfo.scBean.studentsK gt -1}">
<th>K</th>
</c:if>
<c:if test="${ psinfo.scBean.students1 gt -1}">
<th>Grade 1</th>
</c:if>
<c:if test="${ psinfo.scBean.students2 gt -1}">
<th>Grade 2</th>
</c:if>
<c:if test="${ psinfo.scBean.students3 gt -1}">
<th>Grade 3</th>
</c:if>
<c:if test="${ psinfo.scBean.students4 gt -1}">
<th>Grade 4</th>
</c:if>
<c:if test="${ psinfo.scBean.students5 gt -1}">
<th>Grade 5</th>
</c:if>
<c:if test="${ psinfo.scBean.students6 gt -1}">
<th>Grade 6</th>
</c:if>
<c:if test="${ psinfo.scBean.students7 gt -1}">
<th>Grade 7</th>
</c:if>
<c:if test="${ psinfo.scBean.students8 gt -1}">
<th>Grade 8</th>
</c:if>
<c:if test="${ psinfo.scBean.students9 gt -1}">
<th>Grade 9</th>
</c:if>
<c:if test="${ psinfo.scBean.students10 gt -1}">
<th>Level 1</th>
</c:if>
<c:if test="${ psinfo.scBean.students11 gt -1}">
<th>Level 2</th>
</c:if>
<c:if test="${ psinfo.scBean.students12 gt -1}">
<th>Level 3</th>
</c:if>
<c:if test="${ psinfo.scBean.students13 gt -1}">
<th>Level 4</th>
</c:if>
<c:if test="${ psinfo.scBean.students14 gt -1}">
<th>Level 5</th>
</c:if>
<th>TOTAL</th>
</tr>
</thead>
<tbody>
<tr>
<c:if test="${ psinfo.scBean.studentsK gt -1}">
<td>${ psinfo.scBean.studentsK}</td>
<c:set var="totalStd" value="${ psinfo.scBean.studentsK + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students1 gt -1}">
<td>${ psinfo.scBean.students1}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students1 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students2 gt -1}">
<td>${ psinfo.scBean.students2}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students2 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students3 gt -1}">
<td>${ psinfo.scBean.students3}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students3 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students4 gt -1}">
<td>${ psinfo.scBean.students4}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students4 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students5 gt -1}">
<td>${ psinfo.scBean.students5}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students5 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students6 gt -1}">
<td>${ psinfo.scBean.students6}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students6 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students7 gt -1}">
<td>${ psinfo.scBean.students7}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students7 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students8 gt -1}">
<td>${ psinfo.scBean.students8}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students8 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students9 gt -1}">
<td>${ psinfo.scBean.students9}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students9 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students10 gt -1}">
<td>${ psinfo.scBean.students10}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students10 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students11 gt -1}">
<td>${ psinfo.scBean.students11}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students11 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students12 gt -1}">
<td>${ psinfo.scBean.students12}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students12 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students13 gt -1}">
<td>${ psinfo.scBean.students13}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students13 + totalStd}"/>
</c:if>
<c:if test="${ psinfo.scBean.students14 gt -1}">
<td>${ psinfo.scBean.students14}</td>
<c:set var="totalStd" value="${ psinfo.scBean.students14 + totalStd}"/>
</c:if>
<td>${totalStd}</td>
</tr>
</tbody>
</table>
</c:if>
</c:when>
</c:choose>
										<br/>&nbsp;<br/>
										
											<c:if test="${ not psinfo.kClass.isEmpty()}">
											
												<table class="table table-sm table-condensed table-striped table-bordered" style="text-align:center;margin:0 auto;font-size:12px;max-width:600px;width:100%;">
												<thead class="thead-dark">																													
												<tr style="font-weight:bold;color:White;">
												<th width="20%">GRADE(S)</th>
												<th width="20%">POWER SCHOOL SECTION</th>
												<th width="20%"># STUDENTS</th>												
												</tr>
												</thead>
												<tbody>
													<c:forEach var="entry" items="${psinfo.kClass}">	
													<c:if test="${entry.value.numberOfStudents gt '0'}">							
														<tr>														    		
														  	 	<td>${entry.value.gradesString eq '0'?'K':entry.value.gradesString}</td>
																<td>${entry.value.sectionNumber}</td>
																<td>${entry.value.numberOfStudents}</td>
														</tr>	
														</c:if>													
													</c:forEach>
													</tbody>													
													</table>
															
												</c:if>
												<br/>&nbsp;<br/>
													
												<c:if test="${ not psinfo.hClass.isEmpty()}">
												
													<table class="table table-sm table-condensed table-striped table-bordered" style="text-align:center;margin:0 auto;font-size:12px;max-width:800px;width:100%;">
												<thead class="thead-dark">																						
												<tr>														
																<th>LEVELS</th>																
																<th> <15 STDS </th>
																<th> 15-19 </th>
																<th> 20-24 </th>
																<th> 25-29 </th>
																<th> 30-34 </th>
																<th> >35 </th>																
														</tr>
														</thead>
														<tbody>
														<c:forEach var="entry" items="${psinfo.hClass}">
														<c:if test="${entry.value.gradeLevel le '13'}">
															<tr>
																	<td> I, II, III, IV <!-- ${entry.value.gradeLevel}--> </td>											
																	<td> ${entry.value.lessThan15} </td>
																	<td> ${entry.value.between1520} </td>
																	<td> ${entry.value.between2025} </td>
																	<td> ${entry.value.between2530} </td>
																	<td> ${entry.value.between3035} </td>
																	<td> ${entry.value.greaterThan35} </td>
															</tr>																
															</c:if>													
													</c:forEach>
													</tbody>													
													</table>
												</c:if>
												</div>
										</c:when>
										<c:otherwise>
											<p>Please contact the School District for further details.</p>
										</c:otherwise>
									</c:choose>					
  					
</div>  					
  					
  					
  					
  				
  			</div>
	</div>
<script>
$('document').ready(function(){
	if($("#hiddata").val() == "1"){
		$("#divcounts").show();
	}else{
		$("#divcounts").hide();
	}
});
	
</script>	

   
    
 
    </div>
  </body>
</html>
