	  function validateform(frm) 
	  {
	 	var tbl = document.getElementById('DS');
    	var lastRow = tbl.rows.length - 1;
    	var cntrow;
   	 for (cntrow=1; cntrow<=lastRow;cntrow++) 
	 {	
	 	var ARow = document.getElementById('DSQ' + cntrow);
		if (ARow.value.length <= 0) 
		{		
	 		document.getElementById('DSQ' + cntrow).style.backgroundColor="#FFFF66";
	  		document.getElementById('DSQ' + cntrow).style.borderColor='#FF6A6A';
			document.getElementById('DSQ' + cntrow).focus();
	  		alert("All fields of the Student Support Services District Profile MUST be complete. If you have no result for a particular field, please enter 0. Empty fields are highlighted in yellow and outlined in red. Please review and try resubmitting your document.");
			return false;
		}
		
		}
		}  
		
	function validateSSform(frm) 
	  {
	  
	 	var tbl = document.getElementById('SS');
    	var lastRow = tbl.rows.length - 1;
    	var cntrow;
   	 for (cntrow=1; cntrow<=lastRow;cntrow++) 
	 {	
	 	var ARow = document.getElementById('SSQ' + cntrow);
		if (ARow.value.length <= 0) 
		{		
	 		document.getElementById('SSQ' + cntrow).style.backgroundColor="#FFFF66";
	  		document.getElementById('SSQ' + cntrow).style.borderColor='#FF6A6A';
			document.getElementById('SSQ' + cntrow).focus();
	  		alert("All fields of the Student Support Services School Profile MUST be complete. If you have no result for a particular field, please enter 0. Empty fields are highlighted in yellow and outlined in red. Please review and try resubmitting your document.");
			return false;
		}
		
		
		}
		}  
		
		
		function RsetFrm() {
		document.getElementById('ECodeHelp').style.display = 'none';
		document.getElementById('PCatHelp').style.display = 'none';
		document.getElementById('PCat').style.display = 'none';
		document.getElementById('PCriteria').style.display = 'none';
		document.getElementById('PCourse').style.display = 'none';
		document.getElementById('NewStudent').style.display = 'none';
		}

function PCatSelect(id) {
       var e = document.getElementById(id);
	    var f = document.getElementById('SSQ13');
	   if(f.value == "" || f.value == "None"){
       e.style.display = 'none';
    }
	else {
	 e.style.display = 'block';
	 } }
		
function toggle_visibility(id) {
       var e = document.getElementById(id);
	    
       if(e.style.display == 'block')
          e.style.display = 'none';
       else
          e.style.display = 'block';
    }

 function updateTxt(field,toField){
var field = document.getElementById(field);
var toField = document.getElementById(toField);
toField.innerHTML=field.value;
}

		



