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
    
   	<script src="includes/js/Chart.min.js"></script>
    
        
	
	
    <div class="claimHeaderText">Claim Details Report for ${schoolYear}</div>
	
	<br/><div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<br/>
	<div id="printJob">
	
 <form name="add_claim_item_form">
      
      
       				<b>Select Year: </b>
                        <select name="year" id="year">
                          <%
                            Calendar cal = Calendar.getInstance();
                            for(int i=0; i < 5; i++,cal.add(Calendar.YEAR, -1))
                            {
                                out.println("<option value='" + cal.get(Calendar.YEAR) +"'" 
                                  +  ((cal.get(Calendar.YEAR) == year)?" SELECTED":"") + ">"+cal.get(Calendar.YEAR)+"</option>");
                            }
                          %>
                        </select>        
      
      		<br/><br/>
             <table id="claims-table" width="100%" class="claimsTable">
              
              <tr>
                    <td colspan=6 align="center">                    
                    <canvas id="myChart" height="300" width="400"></canvas>                      
                                     
                    </td>
                    </tr>
              
              
              
              
                   <tr class="listHeader">
                      <td class="listdata" width="18%">Month</td>
                      <td class="listdata" width="15%">KMs</td>
                      <td class="listdata" width="20%">KMs Amount</td>
                      <td class="listdata" width="20%">Meals Amount</td>
                      <td class="listdata" width="15%">Lodging Amount</td>
                      <td class="listdata" width="12%">Other Amount</td>
                    </tr>
                    
                    <%for(YearlyClaimsDetailReportItem item : report){%>
                      <%if(item.getPersonnelId() != cur_id){
                        if(cur_id > 0){%>
                        <tr style="padding-bottom:15px; border-bottom: solid 2px #007F01;border-top:1px solid #007F01;"> 
                          <td style="font-weight:bold;background-color: #E3F1E6;" width="18%">TOTALS:</td>
                          <td style="font-weight:bold;" width="15%"><%=kms_df.format(cur_km_total)%> kms</td>
                          <td style="font-weight:bold;" width="20%"><%=curr_df.format(cur_km_total_amount)%></td>                           
                          <td style="font-weight:bold;" width="20%"><%=curr_df.format(cur_meals_total)%></td>
                          <td style="font-weight:bold;" width="15%"><%=curr_df.format(cur_lodging_total)%></td>
                          <td style="font-weight:bold;" width="12%"><%=curr_df.format(cur_other_total)%></td>
                        </tr>
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
                          <td style="background-color: #E3F1E6;border-bottom: solid 1px #007F01;padding-top:10px;" colspan="6">
                          	<%=
                          			"<span style='font-weight:bold;font-size:12px;'>" + item.getPersonnelLastname() 
                          			+ ", " + item.getPersonnelFirstname() + "</span>"                           			
                          			+ "<br>" + item.getPersonnelStreetAddress() 
                          			+ ", " + item.getPersonnelCommunity() 
                          			+ ", " + item.getPersonnelProvince()
                          			+ " &middot; " + item.getPersonnelPostalcode()
                          			+ " &middot; Tel: " + item.getPersonnelPhone1() 
                          	%>
                          </td>
                        </tr>
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
                      
                      <tr style='background-color:<%=(zebra++%2!=0)?"#F0F0F0":"#FFFFFF"%>;'> 
                        <td width="18%"><%=item.getMonth()%></td>
                        <td width="15%"><%=kms_df.format(item.getTotalKms())%></td>
                        <td width="20%"><%=curr_df.format(item.getTotalKmsAmount())%></td>
                        <td width="20%"><%=curr_df.format(item.getTotalMeals())%></td>
                        <td width="15%"><%=curr_df.format(item.getTotalLodging())%></td>
                        <td width="12%"><%=curr_df.format(item.getTotalOther())%></td>
                      </tr>
                    <%}
                    %>
                    <%if(cur_id > 0){%>
                       <tr style="padding-bottom:15px; border-bottom: solid 2px #007F01;border-top:1px solid #007F01;"> 
                         <td style="font-weight:bold;background-color: #E3F1E6;" width="18%">TOTALS:</td>
                        <td style="font-weight:bold;" width="15%"><%=kms_df.format(cur_km_total)%> kms</td>
                        <td style="font-weight:bold;" width="20%"><%=curr_df.format(cur_km_total_amount)%></td>                        
                        <td style="font-weight:bold;" width="20%"><%=curr_df.format(cur_meals_total)%></td>
                        <td style="font-weight:bold;" width="15%"><%=curr_df.format(cur_lodging_total)%></td>
                        <td style="font-weight:bold;" width="12%"><%=curr_df.format(cur_other_total)%></td>
                      </tr>
                    <%}%>
                    <tr style="padding-bottom:10px;">
                      <td style="font-weight:bold;color:#FF0000;" width="18%">Overall Totals:</td>
                      <td style="font-weight:bold;color:#FF0000;" width="15%"><%=kms_df.format(overall_km_total)%> kms</td>
                      <td style="font-weight:bold;color:#FF0000;" width="20%"><%=curr_df.format(overall_km_total_amount)%></td>
                      <td style="font-weight:bold;color:#FF0000;" width="20%"><%=curr_df.format(overall_meals_total)%></td>
                      <td style="font-weight:bold;color:#FF0000;" width="15%"><%=curr_df.format(overall_lodging_total)%></td>
                      <td style="font-weight:bold;color:#FF0000;" width="12%"><%=curr_df.format(overall_other_total)%></td>
                    </tr>
                    <tr style="padding-bottom:10px;border:solid 1px #e0e0e0; background-color:#f0f0f0;">
                    	<td style="font-weight:bold;color:#FF0000;">Overall Expendure:</td>
                    	<td colspan='5' style="font-weight:bold;color:#FF0000; ">
                    		<%=curr_df.format(overall_km_total_amount + overall_meals_total + overall_lodging_total + overall_other_total)%>
                    	</td>
                    </tr>
                    
                    
                 
            </table>
        
    </form>
 
 
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

