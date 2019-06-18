function initRequest() 
{
  var xmlHttp = null;
  
  try
  {    
    // Firefox, Opera 8.0+, Safari    
    xmlHttp=new XMLHttpRequest();    
  }
  catch (e)
  {    
    // Internet Explorer    
    try
    {      
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");      
    }
    catch (e)
    {      
      try
      {        
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");        
      }
      catch (e)
      {        
        alert("Your browser does not support AJAX!");
      }      
    }    
  }
  
  return xmlHttp;
}


function onKeyTypeSelected(keytype, kid)
{
  if(keytype.value == -1)
  {
    return;
  }
  
  if(keytype.name == 'supervisor_keytype')
  {
  	toggleTableRow('supervisor_loading', 'inline');
  	toggleTableRow('supervisor_row', 'none');
  }
  else if(keytype.name == 'user_keytype')
  {
  	toggleTableRow('user_loading', 'inline');
  	toggleTableRow('user_row', 'none');
  }
  
  var xmlHttp = initRequest();
  
  if(xmlHttp)
  {
    xmlHttp.onreadystatechange = function()
    {
      parseKeyTypeSelection(xmlHttp, keytype, kid);
    }
    xmlHttp.open("GET","addSupervisorRule.html?op=KEYTYPE_SELECTED&kt="+keytype.value,true);
    xmlHttp.send(null);
  }
  else
  	alert('xmlHttp not initialized.');
}

function parseKeyTypeSelection(xmlHttp, keytype, kid)
{
  if(xmlHttp.readyState==4)
  {
    var xmlDoc=xmlHttp.responseXML.documentElement;
    
    var r = null;
    var r_c = null;
    var r_str = null;
    var loading = null;
    
    if(keytype.name == 'supervisor_keytype')
    {
    	r = document.getElementById('supervisor_row');
    	r_c = document.getElementById('supervisor_row_content');
    	loading = document.getElementById('supervisor_loading');
    	
    	r_str = "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr>";
	    if(keytype.value == 1)
	    {
	    	r_str = r_str + "<td align='left'><b>Select Supervisor Role:</b>";
	    	r_str = r_str + "<br/>" + generateSelectElement(xmlDoc.getElementsByTagName("ROLE"), "supervisor_key", kid) + "</td>";
	    }
	    else if(keytype.value == 2)
	    {
	    	r_str = r_str + "<td align='left'><b>Select the Supervisor:</b>";
	    	r_str = r_str + "<br/>" + generateSelectElement(xmlDoc.getElementsByTagName("PERSONNEL"), "supervisor_key", kid) + "</td>";
	    }
	    r_str = r_str + "</tr></table>";
	  }
	  else if(keytype.name == 'user_keytype')
	  {
	  	r = document.getElementById('user_row');
	  	r_c = document.getElementById('user_row_content');
	  	loading = document.getElementById('user_loading');
    	
    	r_str = "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr>";
	    if(keytype.value == 1)
	    {
	    	r_str = r_str + "<td align='left'><b>Select User Role:</b>";
	    	r_str = r_str + "<br/>" + generateSelectElement(xmlDoc.getElementsByTagName("ROLE"), "user_key", kid) + "</td>";
	    }
	    else if(keytype.value == 2)
	    {
	    	r_str = r_str + "<td align='left'><b>Select the User:</b>";
	    	r_str = r_str + "<br/>" + generateSelectElement(xmlDoc.getElementsByTagName("PERSONNEL"), "user_key", kid) + "</td>";
	    }
	    r_str = r_str + "</tr></table>";
	  }
	  
    r_c.innerHTML = r_str;
     
    toggleTableRow(r.id, 'inline');
    toggleTableRow(loading.id, 'none');
  }
}



function generateSelectElement(beans, ele_name, kid)
{
	var ele = "<SELECT name='" + ele_name + "' class='requiredinput gensel' style='width:200px;'" + " id='" + ele_name + "'>";
	  
  if(beans.length > 0)
    for(var i = 0; i < beans.length; i++)
    	ele = ele + "<OPTION value='" +beans[i].childNodes[0].childNodes[0].nodeValue+ "'" 
    	+ ((beans[i].childNodes[0].childNodes[0].nodeValue == kid) ? ' SELECTED' : '') + ">" +beans[i].childNodes[1].childNodes[0].nodeValue+ "</OPTION>";
 
  ele = ele + "</SELECT>";
  
  return ele;
}


