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

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

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
    		
    <script src="includes/js/Chart.min.js"></script>		 
	
	
	<div class="claimHeaderText">Kilometer Usage Report for ${schoolYear}</div>
	
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
                    
                     <tr class="listHeader">
                      <td class="listdata" width="18%">Month</td>
                      <td class="listdata" width="18%">KMs</td>
                      <td class="listdata" width="18%">Total</td>
                    </tr>
                    
                  
                    
                    <%for(YearlyKmDetailReportItem item : report){%>
                    
                    
			                    <%if(item.getPersonnelId() != cur_id){
			                    	
			                        if(cur_id > 0){%>
			                         
			                        <tr style="padding-bottom:15px; border-bottom: solid 2px #007F01;border-top:1px solid #007F01;">
			                           	<td style="font-weight:bold;background-color: #E3F1E6;" width="18%">TOTALS:</td>
			                          	<td style="font-weight:bold;" width="15%"><%=kms_df.format(cur_km_total)%> kms </td>
			                          	<td style="font-weight:bold;" width="10%"><%=curr_df.format(cur_total)%></td>
			                        </tr>
			                      <%}
			                        cur_id = item.getPersonnelId();
			                        zebra = 0;
			                        
			                        cur_km_total = item.getTotalKms();
			                        cur_total = item.getTotalAmount();
			                        overall_km_total += item.getTotalKms();
			                        overall_total += item.getTotalAmount();
			                      %>
			                      
			                       
			                      
			                        <tr>
			                          <td style="background-color: #E3F1E6;border-bottom: solid 1px #007F01;padding-top:10px;" colspan="3">
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
                        cur_total += item.getTotalAmount();
                        
                        overall_km_total += item.getTotalKms();
                        overall_total += item.getTotalAmount();
                      }%>
                      
                      <tr style='background-color:<%=(zebra++%2!=0)?"#F0F0F0":"#FFFFFF"%>;'> 
                        <td width="15%"><%=item.getMonth()%></td>
                        <td width="15%"><%=kms_df.format(item.getTotalKms())%></td>
                        <td width="10%"><%=curr_df.format(item.getTotalAmount())%></td>
                      
                      </tr>
                       
                     
                    <%}%>
                    
                                     
                    
                    <%if(cur_id > 0){%>
                    
                    
                   
                    
                    
                      <tr style="padding-bottom:15px; border-bottom: solid 2px #007F01;border-top:1px solid #007F01;"> 
                        <td style="font-weight:bold;background-color: #E3F1E6;" width="18%">TOTALS:</td>
                        <td class="travelTotal" style="font-weight:bold;" width="15%"><%=kms_df.format(cur_km_total)%> kms</td>
                        <td style="font-weight:bold;" width="10%"><%=curr_df.format(cur_total)%></td>
                      </tr>
                    <%}%>
                   
                    
              
                   
                    
                    <tr style="padding-bottom:10px;">
                      <td style="font-weight:bold;color:#FF0000;" width="15%">Overall Totals:</td>
                      <td style="font-weight:bold;color:#FF0000;" width="15%"><%=kms_df.format(overall_km_total)%> kms</td>
                      <td style="font-weight:bold;color:#FF0000;" width="10%"><%=curr_df.format(overall_total)%></td>
                    </tr>
					
								
					
                  </table>
                
    </form>


   </div> 

   
   
  