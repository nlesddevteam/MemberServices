<%@ page language="java"  import="java.util.*, java.text.*" %>
<%
  String width = request.getParameter("width");
%> 
<script type="text/javascript">

	$('document').ready(function(){
	$('#btn_search').click(function(){
		document.location.href = 'searchJob.html?term=' + $('#txt_filter').val() +'&type=' + $("#search_type :selected").val();
		return (false);		
	});
		
	$('#txt_filter').keypress(function(e){
		if(e.which == 13)
		$('#btn_search').click();
	});
		$('#txt_filter').focus();
	});
</script>

      		<table width="100%" class="ppsearch" border="0">
				<tr valign="middle">
				<td align="left" width="190"><%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%></td>				
				<td width="365" align="right">
				<%if(request.getAttribute("msg") != null){%>
                <span class = "searchNoticeError"><%=(String)request.getAttribute("msg")%></span>
                <%}%>				
				</td>
				<td  align="right" width="400"> SEARCH: <input id="txt_filter" type="text"
						class="inputSearchBox"> <select id='search_type' class='inputSelectBox'>
						<option value='1' selected="selected">By Competition #</option>
						<option value='2'>By Location</option>
						<option value='3'>By Applicant</option></select></td><td>		
						<script type="text/javascript">
						jQuery(function(){     
						$(".img-swap").hover(          
						function(){this.src = this.src.replace("-off","-on");},          
						function(){this.src = this.src.replace("-on","-off");     
						});});
						</script>
						<a href="#"><img src="images/v2/search-off.png" id='btn_search' alt="Search" height="22" border="0" class="img-swap" /></a>						
					</td>
				</tr>
				<tr><td align="center" colspan="4" width="955"><img alt="Personnel Package 2.0" src="images/v2/top_header.png" width="955"></td></tr>
			</table> 
      