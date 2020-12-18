<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />
<c:set var="countClaims" value="0" />
<c:set var="countClaimants" value="0" />
<%
  User usr = null;
  ArrayList<YearlyKmDetailReportItem> report = null;
  DecimalFormat curr_df = null;
  DecimalFormat kms_df = null;
  DecimalFormat chart_df = null;
  int cur_id = -1;
  double cur_km_total = 0;
  double cur_total = 0;
  double overall_km_total = 0;
  double overall_total = 0;
  double checkRate =1000;
  

  
  int year = 0;
  int zebra = 0;

  usr = (User) session.getAttribute("usr");
  
  if(!StringUtils.isEmpty(request.getParameter("year")))
  {
    report = TravelClaimDB.yearlykilometerDetailsReport(request.getParameter("year"));
    year = Integer.parseInt(request.getParameter("year"));
  }
  else
  {
    year = Calendar.getInstance().get(Calendar.YEAR);
    report = TravelClaimDB.yearlykilometerDetailsReport(Integer.toString(year));
  }
  chart_df = new DecimalFormat("###0");
  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
%>
<c:set var="schoolYear" value="<%=year%>" />

<script type="text/javascript">
    		$('document').ready(function() {
    		$('#loadingSpinner').css("display","none");
            $('#year').change(function(){
            	var schoolyear = $("#year").val();
            	$('#loadingSpinner').css("display","inline");
            	loadMainDivPage("yearly_kms_detail.jsp?year=" + schoolyear);
            });
    		});
</script>  
 <script>
				$(document).ready(function(){                   		      		
    			 $.cookie('backurl', 'yearly_kms_detail.jsp', {expires: 1 });			   	 
		   		  $("#claims-table").DataTable({
		   		  "order": [[ 0, "asc" ]],		
		   		"responsive": true,
		   		  "lengthMenu": [[100,250, 500, -1], [100, 250, 500, "All"]]	  	  
		   	  
		   	  });			   	 
		    });
  </script>
  <script src="includes/js/Chart.min.js"></script>		 
	<div id="printDATA">
	<div class="siteHeaderBlue">Kilometer Usage Report for ${schoolYear}</div>
	<br/>
	Below is a list of users with Kilometer use for ${schoolYear} sorted by name. 
	You can easily search using the search box at right below and/or export/print the data using options at left.
	Select a year from the dropdown to view data from 2004 to present.	
	<br/><br/>
    <form name="add_claim_item_form">
       <div class="no-print">
      <b>Select Year: </b>
       <select name="year" id="year" class="form-control">
                          <%
                            Calendar cal = Calendar.getInstance();                          
                          	int yearTest =  Calendar.getInstance().get(Calendar.YEAR)-2003;
                          %>
                          <script>$("#numYearsNote").text(<%=yearTest%>)</script>
                          <%
                           for(int i=0; i < yearTest; i++,cal.add(Calendar.YEAR, -1))  {
                                out.println("<option value='" + cal.get(Calendar.YEAR) +"'" 
                                  +  ((cal.get(Calendar.YEAR) == year)?" SELECTED":"") + ">"+cal.get(Calendar.YEAR)+"</option>");
                            } %>
                        </select>
             <br/>
             		<div align="center">         
         				 <a href='#' class="btn btn-sm btn-primary no-print" title='Print this page (pre-formatted)' onclick="jQuery('#printDATA').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print This Page</a><br>
            		</div>   
                       
      	</div>
      
                  <br/>
             		<table id="claims-table" class="table table-condensed table-striped table-bordered claimsTable" style="font-size:11px;background-color:White;" width="100%">	
                    <thead>
                     <tr>
                      <th width="25%">NAME</th>
                      <th width="35%">ADDRESS</th>
                      <th width="40%">DETAILS</th>
                    </tr>
                    </thead>
                  <tbody>                
                
                    
                    <%for(YearlyKmDetailReportItem item : report){%>                    
                    
			                    <%if(item.getPersonnelId() != cur_id){ %>			                    	
			                    	
			                    	 <% if(cur_id > 0){ %>		                        
			                         
			            <div style="border-top:1px solid silver;float:left;width:33%;font-weight:bold;text-align:right;">TOTALS: &nbsp;</div>
                    	<div style="border-top:1px solid silver;float:left;width:33%;"><%=kms_df.format(cur_km_total)%> kms</div>
                    	<div style="border-top:1px solid silver;float:left;width:33%;"><%=curr_df.format(cur_total)%></div>
                    	<div style="clear:both;"></div>                 
			                      
			                     <%} %>
			                    	
			                     <%
			                        cur_id = item.getPersonnelId();
			                        zebra = 0;			                        
			                        cur_km_total = item.getTotalKms();
			                        cur_total = item.getTotalAmount();
			                        overall_km_total += item.getTotalKms();
			                        overall_total += item.getTotalAmount();
			                      %>
			                      
			                       <tr>
			                          <td style="vertical-align: top;"><b><%=item.getPersonnelLastname() %>, 	<%=item.getPersonnelFirstname() %></b></td>
			                           <td style="vertical-align: top;"><%=item.getPersonnelStreetAddress() %><br/>
			                             <%=item.getPersonnelCommunity() %>, 	<%=item.getPersonnelProvince() %> &nbsp;  	<%=item.getPersonnelPostalcode() %><br/>
			                          	Tel: <%=item.getPersonnelPhone1() %>		
			                          </td>		                          
			                          	<c:set var="countClaimants" value="${countClaimants + 1}" />
			                         
			                        <td style="vertical-align: top;">
			                        
			                      
			                      <div style="float:left;width:33%;font-weight:bold;"> MONTH(s)</div>
			                       <div style="float:left;width:33%;font-weight:bold;">KM(s)</div>
			                        <div style="float:left;width:33%;font-weight:bold;">SUBTOTAL</div>
			                      <div style="clear:both;"></div>
			                        
			                         
			                        
                      <% }else{
                        cur_km_total += item.getTotalKms();
                        cur_total += item.getTotalAmount();                        
                        overall_km_total += item.getTotalKms();
                        overall_total += item.getTotalAmount();
                      }%>
                      
                     <div style="float:left;width:33%;"><%=item.getMonth()%></div>
                      <div style="float:left;width:33%;"><%=kms_df.format(item.getTotalKms())%></div>
                      <div style="float:left;width:33%;"><%=curr_df.format(item.getTotalAmount())%></div>
                    <div style="clear:both;"></div>
                       
                     <c:set var="countClaims" value="${countClaims + 1}" />	
                   
                    <%}%>
                  <%if(cur_id > 0){%>
                    	<div style="border-top:1px solid silver;float:left;width:33%;font-weight:bold;text-align:right;">TOTALS: &nbsp;</div>
                    	<div style="border-top:1px solid silver;float:left;width:33%;"><%=kms_df.format(cur_km_total)%> kms</div>
                    	<div style="border-top:1px solid silver;float:left;width:33%;"><%=curr_df.format(cur_total)%></div>
                    	<div style="clear:both;"></div>                    	
                   <%}%>   
                    </td></tr>
                                   
               			
								
					</tbody>
                  </table>
                  
            <div align="center">         
         				 <a href='#' class="btn btn-sm btn-primary no-print" title='Print this page (pre-formatted)' onclick="jQuery('#printDATA').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print This Page</a><br>
            		</div>  
            
            <b>Total Kilometers: </b> <%=kms_df.format(overall_km_total)%> kms<br/>      
            <b>Total Claimed: </b>  <%=curr_df.format(overall_total)%><br/>  
           <b>Total Claimants:</b> ${countClaimants}<br/>  
   <b>Total Claims:</b> ${countClaims}       
    </form>

</div>