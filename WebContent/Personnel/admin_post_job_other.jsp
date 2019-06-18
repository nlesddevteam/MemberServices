<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>
         
         
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<% 
  OtherJobOpportunityBean opp = null;
  String deg_str = "";
  String mjr_str = "";
  String min_str = "";
  String trn_str = "";
  if(request.getAttribute("JOB_OPP") != null)
  {
    opp = (OtherJobOpportunityBean) request.getAttribute("JOB_OPP");

  }
  else if(request.getParameter("job_id") != null)
  {
  
    opp = OtherJobOpportunityManager.getOtherJobOpportunityBeanById(Integer.parseInt(request.getParameter("job_id")));
  }
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>	
<style>
.bootstrap-datetimepicker-widget table td.cw{font-size:11px;}
.bootstrap-datetimepicker-widget table {font-size:11px;}
		.tableTitle {font-weight:bold;width:15%;text-transform:uppercase;}
		.tableResult {font-weight:normal;width:85%;}
		.tableTitleL {font-weight:bold;width:15%;text-transform:uppercase;}
		.tableResultL {font-weight:normal;width:35%;}
		.tableTitleR {font-weight:bold;width:15%;text-transform:uppercase;}
		.tableResultR {font-weight:normal;width:35%;}
		input {border:1px solid silver;}



</style>
<script>
$("#loadingSpinner").css("display","none");
</script>
<script type="text/javascript">
    $(function () {
        $('#ad_comp_end_date').datetimepicker({
        	format:'DD/MM/YYYY HH:mm:A'					                	
        	
        });
    });
 </script>
</head>

<body>
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading">
	               	<b>
	               			<%if(opp == null){%>
                              Post Other Job
                            <%}else{%>  
                              Edit Other Job
                            <%}%>	               	
	               	</b><br/>Fields marked with * are required.</div>
      			 	<div class="panel-body">  
					 									<%if(request.getAttribute("msg")!=null){%>
							                              <div class="alert alert-danger" align="center"><%=(String)request.getAttribute("msg")%></div>
							                           <%}%>
					 
					 <form ENCTYPE="multipart/form-data" id="frmPostJob"  action="postJobOther.html" method="post" >
                                  <%if(opp != null){%>
                                  	<%if(opp.getId() > 0){ %>
                                  		<input type="hidden" name="ad_comp_num" value="<%=opp.getId()%>">
                                  		<input type="hidden" name="edit" value="true">
                                  		<input type="hidden" name="originalfile" value="<%=opp.getFilename() %>"/>
                                    <%}%>

                                  <%}%>
                      <div class="table-responsive">            
					     <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr>                        
    							<td class="tableTitleL">POSITION:</td>
    							<td class="tableResultL"><input type="text" name="ad_title" id="ad_title" class="form-control" value="<%=(opp!=null)?opp.getTitle():""%>"></td>
							    <td class="tableTitleR">REGION:</td>
    							<td class="tableResultR"><job:Regions id="region" cls="form-control" value='<%=(opp!=null)?Integer.toString(opp.getRegion().getId()):""%>' /></td>                     
							    </tr>
							    <tr>                        
    							<td class="tableTitleL">TYPE:</td>
    							<td class="tableResultL">
    												<%if(opp!=null){%>
			                                        	<job:PostingType id='posting_type' cls="form-control" value='<%=opp.getPostingType()%>' />
			                                      	<%}else{%>  
			                                        	<job:PostingType id='posting_type' cls="form-control" />
			                                        <%}%>
    							</td>
							    <td class="tableTitleR">Comp. END DATE:</td>
    							<td class="tableResultR"><div style="position: absolute;"><input class="form-control" id="ad_comp_end_date" name="ad_comp_end_date" value="<%=((opp!=null)&&(opp.getEndDate() != null))?(new SimpleDateFormat("dd/MM/yyyy hh:mm:a")).format(opp.getEndDate()):""%>" /></div>
                                </td>                     
							    </tr>
							    <tr>                        
    							<td class="tableTitle">FILE:</td>
    							<td class="tableResult" colspan=3>
    							<%if(opp!=null){%>
    							Current File: <a href='http://www.nlesd.ca/employment/doc/<%=opp.getFilename() %>' target='_blank'><%=opp.getFilename() %></a><br/>
    							<%} %>
    							<input type="file" id="ad_file" name="ad_file" value="" class="form-control">    							
    							</td>							                     
							    </tr>
							    </tbody>
							    </table>
							       
				              
                                  			
				                         
				                       
                          
                          <div align="center"> <%if(opp == null){%>
                              				<input type="submit" class="btn btn-xs btn-primary" value="Submit">
                            					<%}else{%>  
                              				<input type="submit" class="btn btn-xs btn-primary" value="Update">
                           					<%}%> <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
                           </div>
                                       
 					</div>        
 </form>                       
</div></div></div> 

                           
</body>
</html>
