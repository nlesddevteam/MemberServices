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
	        //$checkbox.prop('checked', true);
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
	        //var checkedItems = {}, 
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