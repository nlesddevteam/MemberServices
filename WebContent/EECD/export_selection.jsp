<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<html>

	<head>
		<title>Education and Early Childhood Development Groups Admin</title>


	<script>
	$(function () {
	    $('.list-group.checked-list-box .list-group-item').each(function () {
	        
	        // Settings
	        var $widget = $(this),
	            $checkbox = $('<input type="checkbox" class="hidden" />'),
	            color = ($widget.data('color') ? $widget.data('color') : "primary"),
	            style = ($widget.data('style') == "button" ? "btn-" : "list-group-item-"),
	            settings = {
	                on: {
	                    icon: 'glyphicon glyphicon-check'
	                },
	                off: {
	                    icon: 'glyphicon glyphicon-unchecked'
	                }
	            };
	            
	        $widget.css('cursor', 'pointer')
	        $widget.append($checkbox);

	        // Event Handlers
	        $widget.on('click', function () {
	            $checkbox.prop('checked', !$checkbox.is(':checked'));
	            $checkbox.triggerHandler('change');
	            updateDisplay();
	        });
	        $checkbox.on('change', function () {
	            updateDisplay();
	        });
	          

	        // Actions
	        function updateDisplay() {
	            var isChecked = $checkbox.is(':checked');

	            // Set the button's state
	            $widget.data('state', (isChecked) ? "on" : "off");

	            // Set the button's icon
	            $widget.find('.state-icon')
	                .removeClass()
	                .addClass('state-icon ' + settings[$widget.data('state')].icon);

	            // Update the button's color
	            if (isChecked) {
	                $widget.addClass(style + color + ' active');
	            } else {
	                $widget.removeClass(style + color + ' active');
	            }
	        }

	        // Initialization
	        function init() {
	            
	            if ($widget.data('checked') == true) {
	                $checkbox.prop('checked', !$checkbox.is(':checked'));
	            }
	            
	            updateDisplay();

	            // Inject the icon if applicable
	            if ($widget.find('.state-icon').length == 0) {
	                $widget.prepend('<span class="state-icon ' + settings[$widget.data('state')].icon + '"></span>');
	            }
	        }
	        init();
	    });
	    
	    $('#get-checked-data').on('click', function(event) {
	        event.preventDefault(); 
	        var counter = 0;
	        var idlist="";
	        $("#check-list-box li.active").each(function(idx, li) {
	        	if(counter == 0){
	        		idlist=$(li).attr("id");
	        	}else{
	        		idlist = idlist + ',' + $(li).attr("id");
	        	}
	            counter++;
	        });
	        //alert(idlist);
	        //$('#display-json').html(JSON.stringify(checkedItems, null, '\t'));
	        $('#idlist').val(idlist);
	        $('#formexport').submit();
	    });
	});
	</script>
<script>
$('document').ready(function(){
$("#loadingSpinner").css("display","none");	
	 
 });
    </script>	

	
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Export Shortlist(s)</b></div>
      			 	<div class="panel-body">
      			 	
      			 	Below is a list of all EECD Working Group Areas with completed shortlist.  To export the shortlist(s), select the export all list checkbox or select the checkboxes next to the list that are to be exported.
					Once all selectons have been made please click the Export Shortlist(s) button.
      			 	
  					<form id="formexport" name="formexport" method="post" action="exportShortlistMulti.html">
  
					<jsp:include page="eecd_menu.jsp"/>
					
					
					<div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
					<div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					
					<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
						
            					<ul id="check-list-box" class="list-group checked-list-box">
                  					<c:forEach items='${ areas }' var='area'>
                  						<li class="list-group-item" id="${ area.id }">${ area.areaDescription }</li>
                  					</c:forEach>
                  					
                  				</ul>
                	</div>
                		<div align="center">
                		<button onclick="" class="btn btn-success btn-xs" id="get-checked-data">Export Selection(s)</button>
                		<a class="btn btn-xs btn-danger" onclick="loadingData()" href="javascript:history.go(-1);">Back</a> 
                		</div>
                		<input type="hidden" id="idlist" name="idlist">
                		</form>
            			
   </div></div></div>
</body>
</html>	
			

			