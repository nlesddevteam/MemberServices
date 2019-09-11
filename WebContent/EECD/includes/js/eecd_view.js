	/*******************************************************************************
     * Calls ajax post for adding new area description
     ******************************************************************************/
    function addUpdateTeacherArea(listids) {
    	var savestatus=$('#firstsave').val();
    	$.ajax({
    		url : 'addUpdateTeacherAreaAjax.html',
    		type : 'POST',
    		data : {
    				selectedids : listids,
    				firstsave: savestatus
    		},success : function(xml) {
    			$(xml).find('TAREA').each(
    					function() {
    						// now add the items if any
    						if ($(this).find("MESSAGE").text() == "ADDED") {
    							//set is firstsave to no so they will now update
    							$('#firstsave').val('N');
    							//show save message
    							$('#successMsg').text("Areas have been updated").css("display", "block").delay(5000);
    							$('#successMsgb').text("Areas have been updated").css("display", "block").delay(5000);
    							$(location).attr('href',"viewEECD.html");
    							
    						} else {
    							//show error message
    							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000);    							
    						}

    					});
    		},
    		error : function(xhr, textStatus, error) {    			
    			$('#errorMsg').text(error).css("display", "block").delay(5000).fadeOut();     			
    		},
    		dataType : "text",
    		async : false

    	});

    }	


$(function () {
	$('.list-group.checked-list-box .list-group-item').each(function () {
	    	var teststring=$('#teacherareas').val();
	    	//alert($(this).attr("nocheck"));
	    	var testid1 =":" + $(this).attr("id")+":";
	        if(teststring.indexOf(testid1) >= 0){
		        //if(teststring.indexOf($(this).attr("id")) > 0){
	        		if($(this).attr("nocheck") == "Y"){
		        		return;
		        		
		        	}
	        		
	        		if($(this).attr("qupdated") == "N"){
	        			//show edit questions div
	        			var dname = "#divhref" + $(this).attr("id");
	        			$(dname).show();
	        		}
	        		
	        }

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
	        var teststring=$('#teacherareas').val();
	        
	    	//teacherareas
	        var testid =":" + $widget.attr("id") + ":";
	        if(teststring.indexOf(testid) >= 0){
	        	$checkbox.prop('checked', true);
	        }else{
	        	$checkbox.prop('checked', false);
	        }
	        // Event Handlers
	        $widget.on('click', function () {
	        	var test = $("#editselected").val();
	        	if(test == "Y"){
	        		getAreaQuestions($widget.attr("id"));
	            }else{
	        		$checkbox.prop('checked', !$checkbox.is(':checked'));
		            $checkbox.triggerHandler('change');
		            $("#selectedid").val($widget.attr("id"));
		            if($checkbox.is(':checked')){
		            	getAreaQuestions($widget.attr("id"));
		            	updateDisplay();
		            	
		            }else{
		            	// do nothing it is unchecked
		            	$checkbox.prop('checked', false);
		            	updateDisplay();
		            }
	        	}
		       
		        $checkbox.triggerHandler('change');
		    });
	        $checkbox.on('click', function () {
	        	
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
	        var idlist;
	        $("#check-list-box li.active").each(function(idx, li) {
	        	if(counter == 0){
	        		idlist=$(li).attr("id");
	        	}else{
	        		idlist = idlist + ',' + $(li).attr("id");
	        	}
	            //checkedItems[counter] = $(li).attr("id");
	            counter++;
	        });
	        //$('#display-json').html(JSON.stringify(checkedItems, null, '\t'));
	        //$('#display-json').html(JSON.stringify(idlist, null, '\t'));
	        //now we pass the list to the update ajax
	        addUpdateTeacherArea(idlist);
	    });

	});
/*******************************************************************************
 * Opens dialog for adding to shortlist
 ******************************************************************************/
function openaddquestions() {
	var tstatus=-1;
	var options = {
		"backdrop" : "static",
		"show" : true
	};
	
	$("#maintitlespan").text("Area Questions");
	$("#spantitle1").text("");
	$("#spantitle2").text("");
	$("#spantitle3").text("");
	$('#myModal').modal(options);
	// now we add the onclick event
	$('#btnok').unbind('click').bind('click', function (e) {
		addUpdateTeacherAreaQuestions();
		});
	

}
/*******************************************************************************
 * Calls ajax post for adding new area description
 ******************************************************************************/
function getAreaQuestions(areaid) {
	$("#tquestions").find("tr:gt(1)").remove();
	if(areaid == ""){
		return;
	}
	var showwindow="N";
	$.ajax({
		url : 'getAreaQuestions.html',
		type : 'POST',
		data : {
				aid : areaid 
		},success : function(xml) {
			$(xml).find('TQUESTION').each(
					function() {
						
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							$("#title1").html($(this).find("AREAD").text());
							//if($(this).find("ETEACHER").text() != "null"){
								//$("#title2").html($(this).find("ETEACHER").text());
							//}
							var newrow="";
							newrow = "<tr><td colspan='2'>" + $(this).find("QSORT").text() + ".  " +  $(this).find("QTEXT").text() + "</td></tr>";
							newrow = newrow + "<tr><td colspan='2'><textarea rows='5' cols='60' maxlength='500' id='txtqanswer' name='txtqanswer'>";
							newrow = newrow + $(this).find("ANSWER").text() + "</textarea><input type='hidden' name='txtanswerid' value='" + $(this).find("ANSWERID").text() + "'>";
							newrow = newrow + "<input type='hidden' id='txtqid' name='txtqid' value='" + $(this).find("QID").text() + "'>";
							newrow = newrow + "<input type='hidden' id='txtareaid' name='txtareaid'  value='" + $(this).find("AREAID").text() + "'></td></tr>";
							$("#tquestions tbody").append(newrow);
							showwindow="Y";   							
						} else if ($(this).find("MESSAGE").text() == "NONE") {
							//no questions do not show modal
							  							
						}else{
							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000);  
						}

					});
			if(showwindow == "Y"){
				openaddquestions();
			}
			
		},
		error : function(xhr, textStatus, error) {    			
			$('#errorMsg').text(error).css("display", "block").delay(5000).fadeOut();     			
		},
		dataType : "text",
		async : false

	});

}
/*******************************************************************************
 * Calls ajax post for saving the data on modal
 ******************************************************************************/
function addUpdateTeacherAreaQuestions() {
	var frm = $('#frmquestions');
	$.ajax({
		url : 'addUpdateQuestions.html',
		type : 'POST',
		data : frm.serialize(),
		success : function(xml) {
			$(xml).find('CAREA').each(
					function() {
						// now add the items if any
						if ($(this).find("MESSAGE").text() == "SUCCESS") {
							//isvalid = true;
							$('#successMsg').text("Area Questions Have Been Updated").css("display", "block");
							$('#myModal').modal('hide');
							$('.modal-backdrop').remove();
							var did = "#divhref" + $("#selectedid").val();
							$(did).show();
						} else {
							$('#errorMsg').text($(this).find("MESSAGE").text()).css("display", "block").delay(5000).fadeOut();
						}

					});
		},
		error : function(xhr, textStatus, error) {
			$("#errorMsg").html(error).css("display",
					"block");
		},
		dataType : "text",
		async : false

	});
	
}
function openedit(areaid){
	$("#editselected").val("Y");
}