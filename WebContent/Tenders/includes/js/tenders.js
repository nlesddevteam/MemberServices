/*tenders javascript */

	function OpenPopUp(pid)
	{

					//{
						//show box
						$(document).ready(function() {
							//$('.fancybox').fancybox();
						});

	}

    function closewindow()
    {
    	//blank fields
    	//$.fancybox.close(); 
    }
   
    function sendinfo()
    {
    	
    	var test=checkfields();
    	
    	if(ajaxSendInfo())
    	{
    		//$.fancybox.close();
    	}
    }
   
    function cleartable()
    {    
    	$("#showlists").find("tr:gt(0)").remove();
    }
	
  
    
	
    
    function sendtendersinfo()
    {
    	var test=checktendersfields();
    	if(test){
    		if(ajaxSendTendersInfo())
        	{
        		//$.fancybox.close();
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
									$('#tender_status').val('6');	
									$('.details_success_message').html("SUCCESS: Tender has been updated!").css("display","block").delay(6000).fadeOut();
 									
									//now we add each one to the table
									newrow += "<td>" + $(this).find("TFTITLE").text() + "</td>";
									newrow += "<td>" + $(this).find("TFDOC").text() + "</td>";
									newrow += "<td>" + $(this).find("ADDENDUMDATE").text() + "</td>";
									newrow += "<td><a class='btn btn-danger btn-xs' onclick='return confirm(\"Are you sure you want to DELETE this document?\");' href='deleteOtherTendersFile.html?id=${p.id}&fid=${p.tfDoc}&tid=${p.tenderId}'>DEL</a></td>";
									//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
									newrow +="</tr>";
									$('table#showlists tr:last').after(newrow);
									i=i+1; 
									isvalid=true;
	                   				
 								}else{
 									//alert($(this).find("MESSAGE").text()+ "1");
 									$(".details_error_message").html($(this).find("MESSAGE").text()).css("display","block").delay(6000).fadeOut();			
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
   