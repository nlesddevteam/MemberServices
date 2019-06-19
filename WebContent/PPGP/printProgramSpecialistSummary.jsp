<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*, 
                java.util.*,com.awsd.security.*, 
                java.text.*,com.awsd.personnel.*,com.awsd.school.*"
       isThreadSafe="false"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>
       
<esd:SecurityCheck permissions='PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST' />

<%
  User usr = (User) session.getAttribute("usr");
  PPGP ppgp = null;
  PPGPGoal goal = null;
  PPGPTask task = null;
  Personnel p = null;
  
  String syear="CUR";
  if(request.getAttribute("syear")!=null){
	  syear=request.getAttribute("syear").toString();
  }
  
%>

<c:set var="query" value="${ param.syear }"/>
<c:if test="${empty param.syear}">
	<c:set var="query" value="CUR"/>
</c:if>


<html>
  <head>   
    <title>PGP Program Specialist Summary</title>
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold; font-size:16px;}
.tableResult {font-weight:normal;}
.tableTitleWide {column-span: all;}
.tableTitleL {font-weight:bold;font-size:16px;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;font-size:16px;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>
  </head>

  <body>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading">
	               	<div style="float:right;font-size:36px;font-weight:bold;margin-top:-10px;color:rgba(0, 102, 153, 0.3);">
		               	<c:choose>
						<c:when test="${param.syear eq 'PREV'}">
						<%=PPGP.getPreviousGrowthPlanYear()%>
						</c:when>
						<c:otherwise>
						<%=PPGP.getCurrentGrowthPlanYear()%>
						</c:otherwise>
						</c:choose>
	               	</div>
	               	
	               	Learning Plan Summaries for
	               	<c:choose>
					<c:when test="${param.syear eq 'PREV'}">
					<%=PPGP.getPreviousGrowthPlanYear()%>
					</c:when>
					<c:otherwise>
					<%=PPGP.getCurrentGrowthPlanYear()%>
					</c:otherwise>
					</c:choose>
	               	
	               	</div>
      			 	<div class="panel-body">
      			 	<span id="foundThem">Below are your detailed Learning Plan Summaries, sorted by school/teacher for your list of schools.</span>
      			 	<br/><br/>
  <%
  
  int schoolFound=0;
    for(School s : new Schools()) {
      p = s.getSchoolPrincipal();
      
      if(p == null)
        continue;
  %> 
  
  <%try{    
	     if(s.getSchoolFamily().getProgramSpecialist().getFullNameReverse().equalsIgnoreCase(usr.getPersonnel().getFullNameReverse())){
	     schoolFound=1;
	     %>
                	
  				<table class="table table-condensed table-bordered" style="font-size:12px;">
					<tbody> 
					<tr style="background-color:#000000;color:White;font-size:14px;">
						<th colspan=4><b>SCHOOL:</b> <%=s.getSchoolName()%> &nbsp; <b>PRINCIPAL:</b> <%=p.getFullName()%></th>
					</tr>
				</tbody>
				</table>
				
        <%
        	for(Personnel teacher : PersonnelDB.getPersonnelList(s)) {                        
        %>  
        
        <c:choose>
					<c:when test="${param.syear eq 'PREV'}">
					<% ppgp = teacher.getPreviousPPGP();  %>
					</c:when>
					<c:otherwise>
					<% ppgp = teacher.getPPGP(); %>
					</c:otherwise>
					</c:choose>
        
        
        
        
        <% String position = teacher.getPersonnelCategory().getPersonnelCategoryName().toString();
			
			  		if (position.equalsIgnoreCase("Teacher") || 
					  position.equalsIgnoreCase("Teaching and Learning Assistant") || 
					  position.equalsIgnoreCase("Substitute Teacher") ||	
					  position.equalsIgnoreCase("Guidance Counsellor") ||
					  position.equalsIgnoreCase("Vice Principal") ||
					  position.equalsIgnoreCase("Principal")) {%>
             <table class="table table-condensed table-bordered" style="font-size:12px;">
					<tbody> 
       
             <tr style="background-color:#0066cc;color:White;">			      
			     <td colspan=4><span style="text-transform:capitalize;font-size:14px;">&nbsp;<b><%=teacher.getFullName()%></b> (<%=position%>)</span></td>
			</tr> 
          
            <%if(ppgp==null){%>
            <tr>			      
			     <td colspan=4><div class="alert alert-danger" align="center"><span style="text-transform:Capitalize;font-weight:bold;"><%=teacher.getFullNameReverse()%></span> has not yet submitted a Learning Plan.</div></td>
			</tr>

            <%}else{ 
            	int goalNum=0;
                for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
                  goal = entry.getValue();
                  goalNum++;
            %>   
            <tr style="color:#0066cc;background-color:#e6f2ff;font-size:14px;">
					<td colspan=4><b>GOAL #<%=goalNum%>:</b> <%=goal.getPPGPGoalDescription()%></td>
			</tr>
            
            
            <%int cntrt=0; %>
            
                  <%for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()) {
                      task = g_entry.getValue();
                      cntrt++;
                  %>  
                  
                  										<tr class="warning">
													    <td colspan=4><b>TASK/STRATEGY #<%=cntrt%>:</b> <%=task.getDescription()%></td>	
													    </tr>
													    <tr class="active">
													    <td colspan=4><b>How may technology support the successfully attainment of your goal?</b></td>
													    </tr>
													    <tr>
													    <td colspan=4><%= task.getTechnologySupport() %></td>	
													    </tr>
													    <tr class="active">
													    <td colspan=2 style="text-align:center;font-weight:bold;">RESOURCES/SUPPORT</td>
													    <td colspan=2 style="text-align:center;font-weight:bold;">TECHNOLOGY</td>
													    </tr>
													    <tr class="active">
													    <td width="25%" style="text-align:center;font-weight:bold;">School Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">District Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">School Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">District Support(s)</td>
													   	</tr>
													    <tr>
													    <td><%=task.getSchoolSupport()%></td>
													    <td><%=task.getDistrictSupport()%></td>
													    <td><%=(task.getTechnologySchoolSupport()!=null)?task.getTechnologySchoolSupport():""%></td>
													    <td><%=(task.getTechnologyDistrictSupport()!=null)?task.getTechnologyDistrictSupport():""%></td>
													   	</tr>
													 	<tr>
													 	<td  class="active"><b>COMPLETION DATE:</b></td>
													 	<td colspan=3><%=task.getCompletionDate()%></td>
													 	</tr>
													 	<tr>
													 	<td class="active"><b>SELF EVALUATION:</b></td>
													 	<td colspan=3>
													 	<%=task.getSelfEvaluation() !=null?task.getSelfEvaluation():"<div class='alert alert-danger' align='center'><span style='text-transform:Capitalize;font-weight:bold;'>"+teacher.getFullNameReverse()+"</span> has not yet completed the self evaluation for this Goal. Completion Date may not have yet passed.</div>"%>
													 	</td>
													 	</tr>
                  <%}
                }
              }%>
           
          <%}%>
			  		
			  		</tbody>
					</table>
					<div class="pagebreak"></div>
        	
        	<%}%>
       		<div class="pagebreak"></div>		
  <%}   } catch(Exception e){ %>
  
  <%}%>
  
     
     
    <%}%>  
    
    <%if(schoolFound==0){ %>
    <div class='alert alert-danger' align='center'>Sorry, no schools found with Professional Learning Plans for your viewing. Director of Schools will need to be associated with a Family of Schools for access. Program Specialists will have access to view all schools. You can still Search PLP's using the search feature in the Administration menu above.</div>
   <script>$("#foundThem").hide();</script>
    <%}%>
    
    </div></div></div>
  </body>
</html>