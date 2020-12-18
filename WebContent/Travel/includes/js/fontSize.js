$(document).ready(function() {  
  			$('#incfont').click(function(){    
					curSize= parseInt($('#contentBlock').css('font-size')) + 2; 
 					if(curSize<=18) 
        				$('#contentBlock').css('font-size', curSize); 
        	});  
			
  			$('#decfont').click(function(){ 
        			curSize= parseInt($('#contentBlock').css('font-size')) - 2; 
  					if(curSize>=10) 
        				$('#contentBlock').css('font-size', curSize); 
        	});  
  			
  			
  			
 		}); 