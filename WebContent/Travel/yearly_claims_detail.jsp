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
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW-REPORTS" />

<%
  User usr = null;
  ArrayList<YearlyClaimsDetailReportItem> report = null;
  DecimalFormat curr_df = null;
  DecimalFormat chart_df = null;
  DecimalFormat kms_df = null;
  int cur_id = -1;
  double cur_km_total = 0;
  double cur_km_total_amount = 0;
  double cur_meals_total = 0;
  double cur_lodging_total = 0;
  double cur_other_total = 0;
  double overall_km_total = 0;
  double overall_km_total_amount = 0;
  double overall_meals_total = 0;
  double overall_lodging_total = 0;
  double overall_other_total = 0;
  
  int year = 0;
  int zebra = 0;

  if(!StringUtils.isEmpty(request.getParameter("year")))
  {
    report = TravelClaimDB.yearlyClaimsDetailsReport(request.getParameter("year"));
    year = Integer.parseInt(request.getParameter("year"));
  }
  else
  {
    year = Calendar.getInstance().get(Calendar.YEAR);
    report = TravelClaimDB.yearlyClaimsDetailsReport(Integer.toString(year));
  }

  curr_df = new DecimalFormat("$#,##0.00");
  chart_df = new DecimalFormat("###0");
  kms_df = new DecimalFormat("#,##0");
 
%>
<c:set var="schoolYear" value="<%=year%>" />

<c:set var="countClaims" value="0" />
<c:set var="countClaimants" value="0" />			
    <script type="text/javascript">
    	$('document').ready(function() {
    		
   			 $('#loadingSpinner').css("display","none");
            $('#year').change(function(){
            	var schoolyear = $("#year").val();
            	$('#loadingSpinner').css("display","inline");
            	loadMainDivPage("yearly_claims_detail.jsp?year=" + schoolyear);
            	
            });
    	});
    </script> 
     <script>

			
			$(document).ready(function(){           
    		        		      		
    			 $.cookie('backurl', 'yearly_claims_detail.jsp', {expires: 1 });
			   	 
		   		  $("#claims-table").DataTable({
		   		  "order": [[ 0, "asc" ]],
		   		"responsive": true,
		   		  //dom: 'Blfrtip',		  
		   		  "lengthMenu": [[100,250, 500, -1], [100, 250, 500, "All"]]
		   	  
		   	  
		   	  });	
		   	 
		    });
		       </script>

   	<script src="includes/js/Chart.min.js"></script>
   	 <div id="printDATA">
	<div class="siteHeaderBlue">Claim Details Report for ${schoolYear}</div>
	<br/>
	Below is a list of users with their total claims for ${schoolYear} sorted by name. 
	You can easily search using the search box at right below and/or print the data.
	Select a year from the drop down to view data from 2004 to present.
	
	<br/><br/>
	
 <form name="add_claim_item_form">      
      <div class="no-print">
       				<b>Select Year: </b>
                        <select name="year" id="year" class="form-control">
                          <%
                            Calendar cal = Calendar.getInstance();
                          //Get all years from 2003 and up. No records before 2003.
                          int yearTest =  Calendar.getInstance().get(Calendar.YEAR)-2003;
                          %>
                          <script>$("#numYearsNote").text(<%=yearTest%>)</script>
                          <%
                          
                          for(int i=0; i < yearTest; i++,cal.add(Calendar.YEAR, -1))
                            {
                                out.println("<option value='" + cal.get(Calendar.YEAR) +"'" 
                                  +  ((cal.get(Calendar.YEAR) == year)?" SELECTED":"") + ">"+cal.get(Calendar.YEAR)+"</option>");
                            }
                          %>
                        </select>             
      		  <br/>
      		</div> 
      		 <div align="center">         
         				 <a href='#' class="btn btn-sm btn-primary no-print" title='Print this page (pre-formatted)' onclick="jQuery('#printDATA').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print This Page</a><br>
            </div>                            
                   <%   int tableClaimsNum = 0;  %> 
  <br/>
             		<table id="claims-table" class="table table-condensed table-striped table-bordered claimsTable" style="font-size:11px;background-color:White;" width="100%">	
                    <thead>
                     <tr>
                      <th width="20%">NAME</td>
                      <th width="20%">ADDRESS</td>
                      <th width="60%">DETAILS</td>
                    </tr>
                    </thead>
                  <tbody>   
                                                
                    <%for(YearlyClaimsDetailReportItem item : report){
                       	tableClaimsNum++;
                    %>
                      <%if(item.getPersonnelId() != cur_id){
                        if(cur_id > 0){%>
                       <div style="border-top:1px solid silver;float:left;width:20%;font-weight:bold;text-align:right;">TOTALS: &nbsp;</div>
                       <div style="border-top:1px solid silver;float:left;width:25%;"><%=curr_df.format(cur_km_total_amount)%> (<%=kms_df.format(cur_km_total)%> km)</div>                  
                       <div style="border-top:1px solid silver;float:left;width:20%;"><%=curr_df.format(cur_meals_total)%></div>
                       <div style="border-top:1px solid silver;float:left;width:20%;"> <%=curr_df.format(cur_lodging_total)%></div>
                       <div style="border-top:1px solid silver;float:left;width:15%;"><%=curr_df.format(cur_other_total)%></div>
                       <div style="clear:both;"></div>
                       
                      <%}
                        cur_id = item.getPersonnelId();
                        zebra = 0;                        
                        cur_km_total = item.getTotalKms();
                        cur_km_total_amount = item.getTotalKmsAmount();
                        cur_meals_total = item.getTotalMeals();
                        cur_lodging_total = item.getTotalLodging();
                        cur_other_total = item.getTotalOther();                        
                        overall_km_total += item.getTotalKms();
						overall_km_total_amount += item.getTotalKmsAmount();
                        overall_meals_total += item.getTotalMeals();
                        overall_lodging_total += item.getTotalLodging();
                        overall_other_total += item.getTotalOther();
                      %>
                      
                      <tr>
			               <td style="vertical-align: top;"><b><%= item.getPersonnelLastname() %>, <%= item.getPersonnelFirstname() %> </b></td>              			
                           <td style="vertical-align: top;"><%= item.getPersonnelStreetAddress() %><br/>
                          	<%= item.getPersonnelCommunity() %>, 	<%= item.getPersonnelProvince() %><br/><%= item.getPersonnelPostalcode() %><br/>
                          	Tel: <%= item.getPersonnelPhone1() %>
                          	</td>
                          			 
                          	<c:set var="countClaimants" value="${countClaimants + 1}" />
                          
                           <td style="vertical-align: top;">	                        
			                      
			                      <div style="float:left;width:20%;font-weight:bold;"> MONTH(s)</div>
			                      <div style="float:left;width:25%;font-weight:bold;">KM(s)</div>
			                      <div style="float:left;width:20%;font-weight:bold;">MEALS</div>
			                      <div style="float:left;width:20%;font-weight:bold;">LODGING</div>
			                      <div style="float:left;width:15%;font-weight:bold;">OTHER</div>			                      
			                      <div style="clear:both;"></div>
                      
                      <%}else{
                      	cur_km_total += item.getTotalKms();
                        cur_km_total_amount += item.getTotalKmsAmount();
                        cur_meals_total += item.getTotalMeals();
                        cur_lodging_total += item.getTotalLodging();
                        cur_other_total += item.getTotalOther();                        
                        overall_km_total += item.getTotalKms();
                        overall_km_total_amount += item.getTotalKmsAmount();
                        overall_meals_total += item.getTotalMeals();
                        overall_lodging_total += item.getTotalLodging();
                        overall_other_total += item.getTotalOther();
                      }%>
                      
                     
                    <div style="float:left;width:20%;"><%=item.getMonth()%></div>
                    <div style="float:left;width:25%;"><%=curr_df.format(item.getTotalKmsAmount())%> (<%=kms_df.format(item.getTotalKms())%> km)</div>
                    <div style="float:left;width:20%;"><%=curr_df.format(item.getTotalMeals())%></div>
                    <div style="float:left;width:20%;"><%=curr_df.format(item.getTotalLodging())%></div>
                    <div style="float:left;width:15%;"><%=curr_df.format(item.getTotalOther())%></div>
                    <div style="clear:both;"></div>
                      <c:set var="countClaims" value="${countClaims + 1}" />	
                   
                    <%}%>
                   
                    <%if(cur_id > 0){%>
                      <div style="border-top:1px solid silver;float:left;width:20%;font-weight:bold;text-align:right;">TOTALS:&nbsp;</div>
                      <div style="border-top:1px solid silver;float:left;width:25%;"><%=curr_df.format(cur_km_total_amount)%> (<%=kms_df.format(cur_km_total)%> km)</div>
                      <div style="border-top:1px solid silver;float:left;width:20%;"><%=curr_df.format(cur_meals_total)%></div>
                      <div style="border-top:1px solid silver;float:left;width:20%;"><%=curr_df.format(cur_lodging_total)%></div>
                      <div style="border-top:1px solid silver;float:left;width:15%;"><%=curr_df.format(cur_other_total)%></div>
                     <div style="clear:both;"></div> 
                    <%}%>
                    </td></tr>
                  
                    
                    </tbody>
                 
            </table>
            
            <br/><br/>
            
            <table class="table table-condensed table-striped table-bordered" style="font-size:11px;background-color:White;" width="100%">	
            <thead>
            <tr>
            <th width="30%" style="font-weight:bold;">${schoolYear} TOTALS:</th>
           <th width="25%" style="font-weight:bold;">KM(s)</th>
           <th width="15%" style="font-weight:bold;">MEALS</th>
           <th width="15%" style="font-weight:bold;">LODGING</th>
           <th width="15%" style="font-weight:bold;">OTHER</th>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td>&nbsp;</td>
            <td><%=curr_df.format(overall_km_total_amount)%> (<%=kms_df.format(overall_km_total)%> kms)</td>
            <td><%=curr_df.format(overall_meals_total)%></td>
            <td><%=curr_df.format(overall_lodging_total)%></td>
            <td> <%=curr_df.format(overall_other_total)%></td>            
            </tr>
            <tr>
            <td style="font-weight:bold;text-align:right;">OVERALL EXPENDITURE:</td>
            <td colspan=4><%=curr_df.format(overall_km_total_amount + overall_meals_total + overall_lodging_total + overall_other_total)%></td>
            </tr>
             <tr>
            <td style="font-weight:bold;text-align:right;">TOTAL CLAIMANTS:</td>
            <td colspan=4>${countClaimants}</td>
            </tr>
             <tr>
            <td style="font-weight:bold;text-align:right;">TOTAL CLAIMS:</td>
            <td colspan=4> ${countClaims} </td>
            </tr>
            </tbody>
            </table>
            

    </form>
 
 <br/>
                     <div align="center"> 
                    		<canvas id="myChart" height="300" width="400"></canvas>    
					</div>   
					<div align="center">         
         				 <a href='#' class="btn btn-sm btn-primary no-print" title='Print this page (pre-formatted)' onclick="jQuery('#printDATA').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print This Page</a><br>
            		</div>  
 </div>
 
 <script>
 
var ctx = document.getElementById("myChart").getContext('2d');
var myChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: ["Kilometers: <%=curr_df.format(overall_km_total_amount)%>", 
             "Meals: <%=curr_df.format(overall_meals_total)%>", 
             "Lodging: <%=curr_df.format(overall_lodging_total)%>", 
             "Other: <%=curr_df.format(overall_other_total)%>",],
    datasets: [{
      backgroundColor: [
        "#DC143C",
        "#3498db",
        "#6495ED",
        "#90EE90"        
      ],
      data: [ 	<%=chart_df.format(overall_km_total_amount)%>,
      			<%=chart_df.format(overall_meals_total)%>,
      			<%=chart_df.format(overall_lodging_total)%>,
      			<%=chart_df.format(overall_other_total)%>]
    }]
  },
  
  options: {
	  	  
      title: {
         display: true,
         fontSize: 20,
         text: 'Travel Budget Breakdown of Total Expenditures of <%=curr_df.format(overall_km_total_amount + overall_meals_total + overall_lodging_total + overall_other_total)%>'
     },
     legend: {
         display: true,
         position: 'left',

     },
     responsive: false
 }

  
  
});


</script>

