<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW-REPORTS" />

<%
  User usr = null;
  ArrayList<YearlyClaimsDetailReportItem> report = null;
  DecimalFormat curr_df = null;
  DecimalFormat kms_df = null;
  DecimalFormat percent_fm = null;
  int cur_id = -1;
  int year = 0;
  int zebra = 0;
  int graphid = 0;
  int checkYear=0;

  double cur_km_total = 0;
  double cur_km_total_amount = 0;
  double cur_meals_total = 0;
  double cur_lodging_total = 0;
  double cur_other_total = 0;
  double cur_g_total = 0;
  
  
  double overall_km_total_amount = 0;
  double overall_meals_total = 0;
  double overall_lodging_total = 0;
  double overall_other_total = 0;
  double overall_total = 0;
  double overall_total_claims = 0;
  double overall_km_totals = 0;
  
  
  String overall_km_pc = null;
  String overall_meals_pc = null;
  String overall_lodging_pc = null;
  String overall_other_pc = null;
  
  
  List<String> claimName = new ArrayList<String>();
  List<Integer> claimAmount = new ArrayList<Integer>();
    
  
  String cur_employee="";
  int numofrows=20;
  if(!StringUtils.isEmpty(request.getParameter("year")))
  {
	  	year = Integer.parseInt(request.getParameter("year"));
		if(!StringUtils.isEmpty(request.getParameter("numrows"))){
			numofrows=Integer.parseInt(request.getParameter("numrows"));			
			report = TravelClaimDB.yearlyClaimsDetailsTopReport(request.getParameter("year"),Integer.parseInt(request.getParameter("numrows")));
		}else{
			report = TravelClaimDB.yearlyClaimsDetailsTopReport(request.getParameter("year"),20);
		}
  }
  else
  {
    year = Calendar.getInstance().get(Calendar.YEAR);
    report = TravelClaimDB.yearlyClaimsDetailsTopReport(""+year+"",20);
  }

  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
  percent_fm = new DecimalFormat("##0.00");
  checkYear = Calendar.getInstance().get(Calendar.YEAR);
%>



			
    <script type="text/javascript">
    	$('document').ready(function() {
    		
    $('#loadingSpinner').css("display","none");
            $('#year').change(function(){
            	var schoolyear = $("#year").val();
            	//var numofrows = $("#numrows").val();
            	var numofrows = 20;
            	$('#loadingSpinner').css("display","inline");
            	loadMainDivPage("yearly_claims_detail_top.jsp?year=" + schoolyear + "&numrows=" + numofrows);
            	
            });
            $('#numrows').change(function(){
           	var schoolyear = $("#year").val();
         	numofrows = 20;
            	$('#loadingSpinner').css("display","inline");
            	loadMainDivPage("yearly_claims_detail_top.jsp?year=" + schoolyear + "&numrows=" + numofrows);
            	
            });
            
            
            $("#topusage-table").DataTable({	 
				  "order": [ 6, "desc" ],
				  "responsive": true,
					  "lengthChange": false,
					  "pageLength": 20,
				  dom: 'Blfrtip',				
			        buttons: [			        	
			        	//'colvis',
			        	'copy', 
			        	'csv', 
			        	'excel', 
			        	{
			                extend: 'pdfHtml5',
			                footer:true,
			                //orientation: 'landscape',
			                messageTop: 'Top Expenditure Report ',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3,4,5,6 ]
			                }
			            },
			        	{
			                extend: 'print',
			                //orientation: 'landscape',
			                footer:true,
			                messageTop: 'Top Expenditure Report',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3,4,5,6]
			                }
			            }]			           
            
        	});        
            
    	});
    </script> 
    
   
	<script src="includes/js/Chart.min.js"></script>
	
		<div class="siteHeaderBlue">Yearly Claim Details Top Usage Users and Amounts</div>
		
	Below are the top 20 user travel claims (including kms, meals, lodging, and other expenses) for the selected year (default being current). You can review up to <span id="numYearsNote"></span> years ago.

	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	   
	<p>
    <form name="add_claim_item_form">
    
      				<b>Select Year: </b>&nbsp;
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
      				
      				
   </form>
   

   
  <br/><br/>
      <canvas id="myChart" height="200"></canvas>
       	<table id="topusage-table" class="table table-condensed table-striped table-bordered topusageTable" style="font-size:11px;background-color:White;" width="100%">	
				<thead>
					<tr style="text-transform:uppercase;font-weight:bold;">  
                  	  <th width="20%">Name</th>
                      <th width="10%">KMs</th>
                      <th width="13%">KMs Amount</th>
                      <th width="15%">Meals</th>
                      <th width="15%">Lodging</th>
                      <th width="12%">Other</th>
                      <th width="15%">Totals</th>
                    </tr>
               </thead>   
               <tbody>
                    <%
                    int i = 0;
                    for(YearlyClaimsDetailReportItem item : report){
					   %>
                        <tr>
                          <td width="20%"><%=item.getPersonnelFirstname() + " " + item.getPersonnelLastname() %></td>
                          <td width="10%"><%=kms_df.format(item.getTotalKms())%></td>
                          <td width="13%"><%=curr_df.format(item.getTotalKmsAmount())%></td>
                          <td width="15%"><%=curr_df.format(item.getTotalMeals())%></td>
                          <td width="15%"><%=curr_df.format(item.getTotalLodging())%></td>
                          <td width="12%"><%=curr_df.format(item.getTotalOther())%></td>
                          <td width="15%"><%=curr_df.format(item.getTotalClaim())%></td>
                        </tr>                       
                   
                        
                      <% 
                      overall_km_totals += item.getTotalKms();
                      overall_km_total_amount += item.getTotalKmsAmount();
                      overall_meals_total += item.getTotalMeals();
                      overall_lodging_total += item.getTotalLodging();
                      overall_other_total += item.getTotalOther();
                      overall_total_claims += item.getTotalClaim();            
                      
                      claimName.add("&quot;" + item.getPersonnelFirstname() + " " + item.getPersonnelLastname().replace("'","") + "&quot;");
                      claimAmount.add((int)item.getTotalClaim());				
 		             //
                      
                      
                      
                    
                      	
                      	i++;
                      	
					  }%>
                       </tbody>    
                      <tfoot>
                   <tr style="border-top:1px solid grey;border-bottom:1px solid grey;font-weight:bold;padding:2px;">
                   			<td style="text-align:right;">TOTALS:&nbsp;</td>
                          <td><%=kms_df.format(overall_km_totals)%></td>
                          <td><%=curr_df.format(overall_km_total_amount)%></td>
                          <td><%=curr_df.format(overall_meals_total)%></td>
                          <td><%=curr_df.format(overall_lodging_total)%></td>
                          <td><%=curr_df.format(overall_other_total)%></td>
                          <td ><%=curr_df.format(overall_total_claims)%></td>
                    </tr>   
                    </tfoot>                     
                      
                 </table>
            
            
            <%
            
            overall_total = overall_km_total_amount + overall_meals_total + overall_lodging_total + overall_other_total;
            overall_km_pc = percent_fm.format(overall_km_total_amount/overall_total * 100);
            overall_meals_pc = percent_fm.format(overall_meals_total/overall_total * 100);
            overall_lodging_pc = percent_fm.format(overall_lodging_total/overall_total * 100);
            overall_other_pc = percent_fm.format(overall_other_total/overall_total * 100);
            
            
            String claimNames = StringUtils.join(claimName, ","); 
            claimNames=claimNames.replace("&quot;","\'");
            
            
            %>
            
          
            
            <div class="claimHeaderText">Expenditure Breakdown of Top 20 Users (Totals)</div> <br/>
	
	  <canvas id="myBreakdownChart" height="300"></canvas>
            
            
        			
				        
        <script>
        
        $("#claims-table tr:even").not(':first').css("background-color", "#FFFFFF");
	    $("#claims-table tr:odd").css("background-color", "#E3F1E6");
        
        </script>
        
         <script>
 
var ctx = document.getElementById("myChart");

ctx.height = 150;

var randomColorPlugin = {

		beforeUpdate: function(chart) {
	        var backgroundColor = [];
	        var borderColor = [];	       
	        for (var i = 0; i < chart.config.data.datasets[0].data.length; i++) {	          
	            var color = "rgba(" + Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255) + ",";				
	            backgroundColor.push(color + "0.2)");
	            borderColor.push(color + "1)");
	        }
			chart.config.data.datasets[0].backgroundColor = backgroundColor;
	        chart.config.data.datasets[0].borderColor = borderColor;
	    }
	}; 

var ColorPluginPie = {

		beforeUpdate: function(chart) {
	        var backgroundColor = ['#F08080',' #1E90FF','#FFD700','#90EE90'];
	        var borderColor = ['#F08080',' #1E90FF','#FFD700','#90EE90'];	       
	        
			chart.config.data.datasets[0].backgroundColor = backgroundColor;
	        chart.config.data.datasets[0].borderColor = borderColor;
	    }
	}; 



Chart.pluginService.register(randomColorPlugin);



var myChart = new Chart(ctx, {
  type: 'bar',
  options: {
	  legend: {
          display: false,
          labels: {
              fontColor: 'rgb(255, 99, 132)',
              fontSize: 8
          }
      },  
	  
      title: {
          display: true,
          text: 'Top 20 User Travel Expenditures'
      }
  },

  data: {
    labels: [<%=claimNames%>],
    datasets: [{
    	backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255,99,132,1)',
        borderWidth: 1,  
      data:  <%=claimAmount%>
    }]
  }});



var ctx1 = document.getElementById("myBreakdownChart").getContext('2d');

Chart.pluginService.register(ColorPluginPie);
var myBreakdownChart = new Chart(ctx1, {
  type: 'pie',
  data: {
    labels: ["Kilometers: <%=overall_km_pc%>% (<%=curr_df.format(overall_km_total_amount)%>)", 
             "Meals: <%=overall_meals_pc%>% (<%=curr_df.format(overall_meals_total)%>)", 
             "Lodging: <%=overall_lodging_pc%>% (<%=curr_df.format(overall_lodging_total)%>)", 
             "Other: <%=overall_other_pc%>% (<%=curr_df.format(overall_other_total)%>)",],
    datasets: [{
    	backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255,99,132,1)',
        borderWidth: 1,  
      data: [ 	<%=overall_km_total_amount%>,<%=overall_meals_total%>,<%=overall_lodging_total%>,<%=overall_other_total%>]
    }]
  },
  
  options: {
	  	  
      title: {
         display: false,
         fontSize: 20,
         text: 'Your Expenditure Breakdown'
     },
     legend: {
         display: true,
         position: 'bottom',

     },
     responsive: false
 }

  
  
});




</script>
        
        
   
    </div>
