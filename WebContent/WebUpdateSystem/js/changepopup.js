	function OpenPopUp(pid)
	{

					//{
						//show box
						$(document).ready(function() {
							$('.fancybox').fancybox();
						});

	}
	function checkfields()
	{
		isvalid=true;
		var ptitle = $.trim($('#other_policy_title').val());
		var pfile = $.trim($('#other_policy_file').val());
		if(ptitle == "")
			{
			alert("Please enter value for Policy Title");
			isvalid=false;
			}
		if(pfile == "")
		{
			alert("Please select Policy file");
			isvalid=false;
		}
		
		if(!pfile.match(/pdf$/i))
		{
			alert("Only PDF files can be uploaded");
			isvalid=false;
		}
			
		
		return isvalid;
		
		
		
	}
    function closewindow()
    {
    	//blank fields
    	$.fancybox.close(); 
    }
    function ajaxRequestInfo(pid,eid)
    {
    	var isvalid=false;
    	$.ajax(
     			{
     				type: "POST",  
     				url: "getNLESDEmployeePayAdviceInfo.html",
     				data: {
     					empid: eid,payid:pid
     				}, 
     				success: function(xml){
     					$(xml).find('EMPLOYEE').each(function(){
     						if($(this).find("MESSAGE").text() == "NO ERROR")
     								{
     									var empname = $(this).find("EMPNAME").text();
    	                   				$("#empname").text(empname);
                        				var empemail = $(this).find("EMAIL").text();
    	                   				$("#empemail").text(empemail);
                        				var payperiod = $(this).find("PPERIOD").text();
    	                   				$("#payperiod").text(payperiod);
                        				$("#hidEID").val(eid);
                        				$("#hidPID").val(pid);    	                   				
										isvalid=true;
    	                   				
     								}else{
     									alert($(this).find("ERROR").text());
     									isvalid=false;
     								}

     					});     					
     				},
     				  error: function(xhr, textStatus, error){
     				      alert(xhr.statusText);
     				      alert(textStatus);
     				      alert(error);
     				  },
     				dataType: "text",
     				async: false
     			}
     		);
    	return isvalid;
}
    function sendinfo()
    {
    	
    	var test=checkfields();
    	
    	if(ajaxSendInfo())
    	{
    		$.fancybox.close();
    	}
    }
    function ajaxSendInfo()
    {
    	var isvalid=false;
		var ptitle = $.trim($('#other_policy_title').val());
		var pfile = $('#other_policy_file');
		var pid = $.trim($('#id').val());
		//var ufile= pfile.files[0];
		//var ufile = $('#other_policy_title').prop('files');
		var ufile = $('#other_policy_file')[0].files[0];
		//poltitle: ptitle,polid:pid,polfile: ufile 
		var requestd = new FormData();
		requestd.append('polid',pid);
		requestd.append('poltitle',ptitle);
		requestd.append('polfile',ufile);
		//mimeType:"multipart/form-data",
		
        $.ajax({
            url: "addOtherPolicyFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	
					var i=1;
					cleartable();
 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("PFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("PFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1;
									isvalid=true;
	                   				
 								}else{
 									alert($(this).find("MESSAGE").text()+ "1");
 									
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }
    function cleartable()
    {
    	//$('#showlists td').parent().remove();
    	//$("#showlists").remove("tr:gt(0)");
    	$("#showlists").find("tr:gt(0)").remove();
    }
	function checkblogfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_blog_title').val());
		var bfile = $.trim($('#other_blog_file').val());
		if(btitle == "")
			{
			alert("Please enter value for Blog Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Blog file");
			isvalid=false;
		}
		return isvalid;
	}
    function sendbloginfo()
    {
    	var test=checkblogfields();
    	
    	if(ajaxSendBlogInfo())
    	{
    		$.fancybox.close();
    	}
    }
    function ajaxSendBlogInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_blog_title').val());
		var bfile = $('#other_blog_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_blog_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('blogid',bid);
		requestd.append('blogtitle',btitle);
		requestd.append('blogfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherBlogFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	
					var i=1;
					cleartable();
 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("BFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("BFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1;
									isvalid=true;
	                   				
 								}else{
 									alert($(this).find("MESSAGE").text()+ "1");
 									
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }
    function sendmminfo()
    {
    	var test=checkmmfields();
    	
    	if(ajaxSendMMInfo())
    	{
    		$.fancybox.close();
    	}
    }
	function checkmmfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_mm_title').val());
		var bfile = $.trim($('#other_mm_file').val());
		if(btitle == "")
			{
			alert("Please enter value for Meeting Minutes Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Meeting Minutes file");
			isvalid=false;
		}
		return isvalid;
	}
    function ajaxSendMMInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_mm_title').val());
		var bfile = $('#other_mm_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_mm_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('mmid',bid);
		requestd.append('mmtitle',btitle);
		requestd.append('mmfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherMMFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	
					var i=1;
					cleartable();
 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("MMFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("MMFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1; 
									isvalid=true;
	                   				
 								}else{
 									alert($(this).find("MESSAGE").text()+ "1");
 									
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }    
    function sendmhinfo()
    {
    	var test=checkmhfields();
    	
    	if(ajaxSendMHInfo())
    	{
    		$.fancybox.close();
    	}
    }
	function checkmhfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_mh_title').val());
		var bfile = $.trim($('#other_mh_file').val());
		if(btitle == "")
			{
			alert("Please enter value for Meeting Highlights Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Meeting Highlights file");
			isvalid=false;
		}
		return isvalid;
	}
    function ajaxSendMHInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_mh_title').val());
		var bfile = $('#other_mh_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_mh_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('mhid',bid);
		requestd.append('mhtitle',btitle);
		requestd.append('mhfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherMHFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	
					var i=1;
					cleartable();
 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("MHFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("MHFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1; 
									isvalid=true;
	                   				
 								}else{
 									alert($(this).find("MESSAGE").text()+ "1");
 									
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }
    function sendnewsinfo()
    {
    	var test=checknewsfields();
    	
    	if(ajaxSendNewsInfo())
    	{
    		$.fancybox.close();
    	}
    }
	function checknewsfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_news_title').val());
		var bfile = $.trim($('#other_news_file').val());
		if(btitle == "")
			{
			alert("Please enter value for News Postings Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select News Postings file");
			isvalid=false;
		}
		return isvalid;
	}
    function ajaxSendNewsInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_news_title').val());
		var bfile = $('#other_news_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_news_file')[0].files[0];
		var requestd = new FormData();
		requestd.append('npid',bid);
		requestd.append('nptitle',btitle);
		requestd.append('npfile',ufile);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherNewsFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	
					var i=1;
					cleartable();
 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("NPFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("NPFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1; 
									isvalid=true;
	                   				
 								}else{
 									alert($(this).find("MESSAGE").text()+ "1");
 									
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }
    function sendtendersinfo()
    {
    	var test=checktendersfields();
    	if(test){
    		if(ajaxSendTendersInfo())
        	{
        		$.fancybox.close();
        	}
    	}else{
    		return false;
    	}
    	
    }
	function checktendersfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_tenders_title').val());
		var bfile = $.trim($('#other_tenders_file').val());
		var bdate = $.trim($('#addendum_date').val());
		if(btitle == "")
			{
			alert("Please enter value for Tenders Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Tenders file");
			isvalid=false;
		}
		if(bdate == "")
		{
			alert("Please select Addendum Date");
			isvalid=false;
		}
		return isvalid;
	}
    function ajaxSendTendersInfo()
    {
    	var isvalid=false;
		var btitle = $.trim($('#other_tenders_title').val());
		var bfile = $('#other_tenders_file');
		var bid = $.trim($('#id').val());
		var ufile = $('#other_tenders_file')[0].files[0];
		var bdate= $.trim($('#addendum_date').val());
		var requestd = new FormData();
		requestd.append('tid',bid);
		requestd.append('ttitle',btitle);
		requestd.append('tfile',ufile);
		requestd.append('tdate',bdate);
		//mimeType:"multipart/form-data",
		$.ajax({
            url: "addOtherTendersFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	var i=1;
					cleartable();

 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
									
									$('#details_success_message').html("SUCCESS: Tender has been updated!").css("display","block").delay(6000).fadeOut();
 									//now we add each one to the table
									newrow += "<td>" + $(this).find("TFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("TFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDENDUMDATE").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1; 
									isvalid=true;
	                   				
 								}else{
 									//alert($(this).find("MESSAGE").text()+ "1");
 									$("#details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(6000).fadeOut();			
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }
    function ajaxSendProgramInfo()
    {
    	var isvalid=false;
		var ptitle = $.trim($('#other_program_title').val());
		var pfile = $('#other_program_file');
		var pid = $.trim($('#id').val());
		//var ufile= pfile.files[0];
		//var ufile = $('#other_policy_title').prop('files');
		var ufile = $('#other_program_file')[0].files[0];
		//poltitle: ptitle,polid:pid,polfile: ufile 
		var requestd = new FormData();
		requestd.append('programid',pid);
		requestd.append('programtitle',ptitle);
		requestd.append('programfile',ufile);
		//mimeType:"multipart/form-data",
		
        $.ajax({
            url: "addOtherProgramFile.html",
            type: 'POST',
            data:  requestd,
            contentType: false,
            cache: false,
            processData:false,
            success: function(xml)
            {
            	
					var i=1;
					cleartable();
 					$(xml).find('FILES').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "SUCCESS")
 								{
 									
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("ID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("ID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("PFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("PFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDEDBY").text() + "</td>";
									newrow += "<td>" + $(this).find("DATEADDED").text() + "</td>";
									newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("ID").text() + ");'>Delete</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1;
									isvalid=true;
	                   				
 								}else{
 									alert($(this).find("MESSAGE").text()+ "1");
 									
 								}
						});
            },
            error: function(jqXHR, textStatus, errorThrown) 
            {
            		alert("error");
            },
				dataType: "text",
 				async: false
       });

    	return isvalid;
    }
    function sendprograminfo()
    {
    	
    	var test=checkprogramsfields();
    	
    	if(ajaxSendProgramInfo())
    	{
    		$.fancybox.close();
    	}
    }
	function checkprogramsfields()
	{
		isvalid=true;
		var btitle = $.trim($('#other_program_title').val());
		var bfile = $.trim($('#other_program_file').val());
		if(btitle == "")
			{
			alert("Please enter value for Programs Title");
			isvalid=false;
			}
		if(bfile == "")
		{
			alert("Please select Programs file");
			isvalid=false;
		}
		return isvalid;
	}    