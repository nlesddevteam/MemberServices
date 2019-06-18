	if (document.images) {
	 
	b1_on = new Image();
	b1_on.src = "includes/images/cpanel-on.gif";
	b1_off = new Image();
	b1_off.src = "includes/images/cpanel-off.gif";
	b2_on = new Image();
	b2_on.src = "includes/images/search-on.gif";
	b2_off = new Image();
	b2_off.src = "includes/images/search-off.gif";		
	b3_on = new Image();
	b3_on.src = "includes/images/print-on.gif";
	b3_off = new Image();
	b3_off.src = "includes/images/print-off.gif";		
	b4_on = new Image();
	b4_on.src = "includes/images/help-on.gif";
	b4_off = new Image();
	b4_off.src = "includes/images/help-off.gif";			
	b5_on = new Image();
	b5_on.src = "includes/images/logout-on.gif";
	b5_off = new Image();
	b5_off.src = "includes/images/logout-off.gif";		
	
	b10_on = new Image();
	b10_on.src = "includes/images/users-on.gif";
	b10_off = new Image();
	b10_off.src = "includes/images/users-off.gif";
	b11_on = new Image();
	b11_on.src = "includes/images/reports-on.gif";
	b11_off = new Image();
	b11_off.src = "includes/images/reports-off.gif";
	b12_on = new Image();
	b12_on.src = "includes/images/maint-on.gif";
	b12_off = new Image();
	b12_off.src = "includes/images/maint-off.gif";
	b13_on = new Image();
	b13_on.src = "includes/images/schools-on.gif";
	b13_off = new Image();
	b13_off.src = "includes/images/schools-off.gif";
		
	
			
						} 
function dsgo(imgName) { 
        if (document.images) { 
            document[imgName].src = eval(imgName + "_on.src"); 
        } 
} 
function dsleave(imgName) { 
        if (document.images) { 
            document[imgName].src = eval(imgName + "_off.src"); 
        } 
} 
