	if (document.images) {
	 
	b1_on = new Image();
	b1_on.src = "../includes/images/env-on.jpg";
	b1_off = new Image();
	b1_off.src = "../includes/images/env-off.jpg";
	b2_on = new Image();
	b2_on.src = "../includes/images/gov-on.jpg";
	b2_off = new Image();
	b2_off.src = "../includes/images/gov-off.jpg";		
	b3_on = new Image();
	b3_on.src = "../includes/images/day-on.jpg";
	b3_off = new Image();
	b3_off.src = "../includes/images/day-off.jpg";		
	b4_on = new Image();
	b4_on.src = "../includes/images/fire-on.jpg";
	b4_off = new Image();
	b4_off.src = "../includes/images/fire-off.jpg";			
	b5_on = new Image();
	b5_on.src = "../includes/images/imp-on.jpg";
	b5_off = new Image();
	b5_off.src = "../includes/images/imp-off.jpg";		
	b6_on = new Image();
	b6_on.src = "../includes/images/rep-on.jpg";
	b6_off = new Image();
	b6_off.src = "../includes/images/rep-off.jpg";		
	
	b7_on = new Image();
	b7_on.src = "../includes/images/addai-on.jpg";
	b7_off = new Image();
	b7_off.src = "../includes/images/addai-off.jpg";		
	
	b8_on = new Image();
	b8_on.src = "../includes/images/deleteai-on.jpg";
	b8_off = new Image();
	b8_off.src = "../includes/images/deleteai-off.jpg";		
	
	b9_on = new Image();
	b9_on.src = "../includes/images/viewreport-on.jpg";
	b9_off = new Image();
	b9_off.src = "../includes/images/viewreport-off.jpg";	
	
	b10_on = new Image();
	b10_on.src = "../includes/images/deleterep-on.jpg";
	b10_off = new Image();
	b10_off.src = "../includes/images/deleterep-off.jpg";		
	
	b11_on = new Image();
	b11_on.src = "../includes/images/listai-on.jpg";
	b11_off = new Image();
	b11_off.src = "../includes/images/listai-off.jpg";
	
	b12_on = new Image();
	b12_on.src = "../includes/images/viewai-on.jpg";
	b12_off = new Image();
	b12_off.src = "../includes/images/viewai-off.jpg";		
	
	b13_on = new Image();
	b13_on.src = "../includes/images/imprep-on.jpg";
	b13_off = new Image();
	b13_off.src = "../includes/images/imprep-off.jpg";		
			
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
