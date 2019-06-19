<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		 com.awsd.personnel.profile.*, 
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.sds.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />



<html>
	<head>
		<title>Travel/Expense Claim SDS Export Report</title>
			<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/travel.js"></script>
			<script src="includes/js/jquery.maskedinput.min.js"></script>
    <STYLE TYPE="text/css">
    	@media print {
      	.pagebreak {page-break-after: always}
      }
    </STYLE>
     <script>
        
  		$( document ).ready(function() {
  			$('#loadingSpinner').css("display","none");
  			
		});
  		</script> 
	</head>
	<body>
	<div id="printJob"> 
	                        
	<div class="claimHeaderText">Travel/Expense Claim SDS Export Report</div>
	<div align="right" class="no-print"><a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><img style="padding-right:10px;padding-bottom:2px;" src="includes/img/print-off.png" class="img-swap" title="Print Pages."></a></div>
	
	Time
	<p>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	
    <form name="add_claim_item_form" method="post" >
       <table width="100%">
       
       
        
          <tr>
            <td>
            
            <div class="claimHeaderText">Claimant: <span style="text-transform:capitalize;">Name</span> 
            
            <div style="float:right;">Date</div>
            
            
            </div>
            
            
             		  <b>Address:</b> g<br/>
		              <b>Tel:</b>44444 &nbsp;&middot;&nbsp; <b>Cell:</b> 3444&nbsp;&middot;&nbsp;
		              <b>Fax:</b> 666<br/>
		              <b>Email:</b> 66665<br/>
		            	              
		              <br>
		                  
		              <b>Position:</b> 67657<br/> 
		              <b>School:</b> 8888<br/>                                         
		              <b>Supervisor:</b>jjjj</a>  
					
					<br/>
            
           </td>
          </tr>
         
          <tr>
            <td>
            
            		<div class="claimStatusBlock">
						<div class="claimHeaderText">Claim Status</div>
	                                
	                                		<div class="alert alert-warning" style="margin-top:5px;padding:2px;height:20px;"><b>PRE-SUBMISSION:</b> Claim is in Pre-Submission mode. You have yet to submit this claim for processing.</div>
	                                	
                     </div>
             
             		<div style="clear:both;"></div>   
                    <div class="claimStatusInfoBlock">
                            <b>Teacher Payroll:</b> tttt<br/>
                            <b>GL Account:</b>
                                    	tttt
                            
                     </div>
                            
             
             
             
             
             </td>
            </tr>
            <tr>
              <td>&nbsp;<br/><img src="includes/img/bar.png" width="100%" height=1><br/>&nbsp;</td>
            </tr>
            
            <tr>
              <td>
                      <table width="100%">
                      <tr>
                        <td colspan="6" class="title">Claim Items</td>
                      </tr>
                      <tr>
                         <td width="15%" class="itemsHeader">Date</td>
                         <td width="25%" class="itemsHeader">Dep/Rtn Time</td>
                         <td width="15%" class="itemsHeader">KMs</td>
                         <td width="15%" class="itemsHeader">Meals</td>
                         <td width="15%" class="itemsHeader">Lodging</td>
                         <td width="*" class="itemsHeader">Other</td>
                      </tr>
                       <tr><td colspan="6" style="padding-bottom:2px;border-top: solid 1px #c4c4c4;color:Grey;height:5px;text-transform:none;"></td></tr>
                      
                            <tr>
                              <td width="15%" class="field_content">1</td>
                              <td width="25%" class="field_content">2</td>
                              <td width="15%" class="field_content">3</td>
                              <td width="15%" class="field_content">4</td>
                              <td width="15%" class="field_content">6</td>
                              <td width="*" class="field_content">7</td>
                            </tr>
                        
                          <tr>
                            <td colspan="6" class="field_content" style='padding-bottom:2px;border-bottom: dashed 1px #c4c4c4;color:Grey;text-transform:none;'>88tyuj uyrturtt f fjfgjh</td>
                          </tr>
                         
                          
                          <tr>
                            <td class="total" colspan="2" align="right" valign="middle">Totals:&nbsp;</td>
                            <td class="total" width="15%" valign="middle">66 kms</td>
                            <td class="total" width="10%" valign="middle">44</td>
                            <td class="total" width="15%" valign="middle">33</td>
                            <td class="total" width="15%" valign="middle">3</td>
                          </tr>
                          
                          <tr><td colspan=6>&nbsp;</td></tr>  
                          
                          <tr>
                             <td class="total" colspan="2" align="right" valign="middle"></td>
                            	
                                   <td valign="middle" colspan=3>vbnvbnb
                                  </td>
                                     <td>     nvnvbnvb                                 </td>
                                    
                                 
                          </tr>
                            
                          <tr><td colspan=6>&nbsp;</td></tr>   
                            
                           <tr>
                          <td class="total_label" colspan="5" align="right" valign="middle">Total Due:</td>                         
                          <td class="summary_total" valign="middle">tttt</td>
                        </tr>
                       <tr><td colspan=6>&nbsp;</td></tr> 
                       
                       
                       
                         
                  
                    </table>
                  
                   </td>
                </tr>             
          <tr class='pagebreak'><td width="100%"><br /></td></tr>
       
      </table>
    </form>
    </div>
    <!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>
    
	</body>
</html>
