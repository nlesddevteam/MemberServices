<%@ page language ="java" 
         session = "true"
         import = "java.util.*,
         			com.awsd.security.*,                 	
                 	java.io.*,
                 	java.text.*,
         			com.esdnl.survey.bean.*,
         			com.esdnl.survey.dao.*,
         			com.esdnl.util.*"
         isThreadSafe="false"%>    
         
         
         
         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/survey.tld" prefix="survey" %>
<esd:SecurityCheck permissions="SURVEY-ADMIN-VIEW" />

<%
	SurveyBean survey = (SurveyBean) request.getAttribute("SURVEY_BEAN");
	SurveySectionBean section = (SurveySectionBean) request.getAttribute("SURVEY_SECTION_BEAN");
%>


<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>District Survey System Admin</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="/MemberServices/includes/css/jquery-ui-1.10.3.custom.css" >
			<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="/MemberServices/includes/js/jquery-1.9.1.js"></script>
			<script src="/MemberServices/includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>						
			<script src="../includes/js/nlesd.js"></script>
			<script src="/MemberServices/includes/js/backgroundchange.js"></script>		
			
			
		<script>
			var opt_cnt = 0;
			
			function addRowToTable(opt, idx){
				opt_cnt = opt_cnt+1;
				
			  var tbl = document.getElementById('tblQuestionOptions');
			  
			  var rIdx;
			  
			  if(!idx) 
			  	rIdx = tbl.rows.length;
			  else
				  rIdx = idx;
			  
			  var row = tbl.insertRow(rIdx);
			  
			  // left cell - option label
			  var cellLeft = row.insertCell(0);
			  cellLeft.className="label";
			  cellLeft.setAttribute("align", "left");
			  var textNode = document.createTextNode("Option " + String.fromCharCode(64+opt_cnt) + ":");
			  cellLeft.appendChild(textNode);
			  
			  // right cell - option text
			  var cellRight = row.insertCell(1);
			  var el = document.createElement('input');
			  el.type = 'text';
			  el.name = 'option_' + opt_cnt;
			  el.id = 'option_' + opt_cnt;
			  el.className = 'requiredInputBox';
			  el.style.width = '150px';
			  if(opt)
				  el.value = opt;
			  
			  cellRight.appendChild(el);

			  //delete option button
			  el = document.createElement('input');
			  el.type = 'button';
			  el.value = '-';
			  el.id = 'btn_delete_option_' + opt_cnt;
			  el.className = 'delete-option';

			  cellRight.appendChild(el);

			  //insert option button
			  el = document.createElement('input');
			  el.type = 'button';
			  el.value = '+';
			  el.id = 'btn_insert_option_' + opt_cnt;
			  el.className = 'insert-option';

			  cellRight.appendChild(el);

			  document.forms[0].option_count.value=opt_cnt;
			}
			
			function onQuestionTypeSelect(sel) {
				if((sel.selectedIndex == 1) || (sel.selectedIndex == 2) || (sel.selectedIndex == 7)) {
					$('#rowQuestionOptions').show();
					$('#option_count').val(opt_cnt);
				}
				else {
					$('#rowQuestionOptions').hide();
					$('#option_count').val(0);
				}
				
				if(sel.selectedIndex == 7) {
					$('.bullets-question-info').show();
				}
				else {
					$('.bullets-question-info').hide();
				}
			}
			
			function validateQuestion(){
				
				var valid = true;
				
				var row = document.getElementById('msgRow');
				var txt = document.getElementById('msgTxt');
				var msg = '';
				
				var frm = document.forms[0];
				
				if(!validateSelectionMade(frm.section_id)){
					msg = 'Please select a question section.';	
					valid = false;
				}
				else if(!validateSelectionMade(frm.question_type)){
					msg = 'Please select question type.';
					valid = false;
				}
				else if(!validateNotEmpty(frm.question_stem)){
					msg = 'Please enter question stem.';
					valid = false;
				}
				else if(frm.option_count.value > 0){
					var fld = null;
					for(i=1; i <= opt_cnt; i++){
						fld = document.getElementById('option_' + i);
						if(fld && !validateNotEmpty(fld)){
							msg = 'Please enter OPTION ' + String.fromCharCode(64+i) +  ' text.';
							fld.focus();
							valid = false;
							break;
						}
					}
				}
				
				if(!valid){
				txt.innerHTML = msg;
					row.style.display = 'inline';
				}
				
				return valid;
			}

			function reorganizeOptions() {
				//remove existing shift options
				$('.shift-down-option').remove();
				$('.shift-up-option').remove();
				
				$('#tblQuestionOptions').children().children().each(function(idx){
					if(idx > 1) {
						//rename label
						$(this).children()[0].innerHTML = "Option " + String.fromCharCode(65 + idx - 2) + ":";

						//add shift options
						var el;
		
						//shift down option button
						if(idx < $('#tblQuestionOptions').children().children().length-1) { 
						  el = document.createElement('input');
						  el.type = 'button';
						  el.value = String.fromCharCode(0x2C5);
						  el.id = 'btn_shift_down_' + opt_cnt;
						  el.className = 'shift-down-option';
			
						  $(this).children()[1].appendChild(el);
						}
						
						//shift up option button
					  if(idx > 2) {				
						  el = document.createElement('input');
						  el.type = 'button';
						  el.value = String.fromCharCode(0x2C4);
						  el.id = 'btn_shift_up_' + opt_cnt;
						  el.className = 'shift-up-option';
			
						  $(this).children()[1].appendChild(el);
					  }
					}
				});

				$('.shift-down-option').click(function () {
					var tmp = $(this).siblings(':eq(0)').val();
					
					var dIdx = $(this).parent().parent().index() + 1;
					
					$(this).siblings(':eq(0)')
						.val(
							$('#tblQuestionOptions')
							.children()
							.children(':nth-child('+ (dIdx + 1) +')')
							.children(':nth-child(2)')
							.children(':nth-child(1)')
							.val()
						);

					$('#tblQuestionOptions')
							.children()
							.children(':nth-child('+ (dIdx + 1) +')')
							.children(':nth-child(2)')
							.children(':nth-child(1)').val(tmp);
				});

				$('.shift-up-option').click(function () {
					var tmp = $(this).siblings(':eq(0)').val();
					
					var dIdx = $(this).parent().parent().index() - 1;
					
					$(this).siblings(':eq(0)')
						.val(
							$('#tblQuestionOptions')
							.children()
							.children(':nth-child('+ (dIdx + 1) +')')
							.children(':nth-child(2)')
							.children(':nth-child(1)')
							.val()
						);

					$('#tblQuestionOptions')
							.children()
							.children(':nth-child('+ (dIdx + 1) +')')
							.children(':nth-child(2)')
							.children(':nth-child(1)').val(tmp);
				});
				
			}

			$('document').ready(function(){

				$('#question_type').change(function(){
					onQuestionTypeSelect($(this)[0]);
				});

				$('#btnAddOption').click(function(){
					addRowToTable();

					reorganizeOptions();
				});

				$('.delete-option').on("click", 'null', function(){
					$(this).parent().parent().remove();
					
					opt_cnt = opt_cnt - 1;
					
					reorganizeOptions();
				});

				$('.insert-option').on("click", 'null', function(){
					addRowToTable('', $(this).parent().parent().index() + 1);
					
					reorganizeOptions();
				});
				
				$('#frmCreateSectionQuestion').submit(function(){
					return validateQuestion();
				});
				
				onQuestionTypeSelect($('#question_type')[0]);

				<% 
					if(pageContext.getAttribute("options", PageContext.REQUEST_SCOPE) != null){
						for(SurveySectionQuestionOptionBean opt : (ArrayList<SurveySectionQuestionOptionBean>) pageContext.getAttribute("options", PageContext.REQUEST_SCOPE)){
							if(!opt.isOther())
								out.println("addRowToTable('" + opt.getOptionBody() + "');");
							else
								out.println("$('#include_other').attr('checked', 'checked')");
						}
						out.println("reorganizeOptions();");
					}
				%>
				
			});
			
		</script>	
			
			
			
			
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
   
	
	</head>

  <body><br/>
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="../includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
					<div align="center" style="padding-bottom:10px;padding-top:5px;">
						<a href='index.jsp'><img src="../includes/img/survey-list-off.png" class="img-swap" border=0 title="Survey Listings"></a>
						<a href='createSurvey.html'><img src="../includes/img/create-new-off.png" class="img-swap" border=0 title="Create Survey"></a>
						<a href="../../navigate.jsp"><img src="../includes/img/back-off.png" class="img-swap" border=0 title="Back to MemberServices Main Menu"></a>
					</div>	
					
					<div class="headerText">${SURVEY_BEAN.name} - ${question ne null ? "Update" : "Create"} Question</div>
					<p>
					
                	
                	<form id="frmCreateSectionQuestion" action="createSectionQuestion.html" method="post" >
								
									<c:choose>
										<c:when test="${question ne null}">
											<input type="hidden" name="op" value="update" />
											<input type="hidden" name="question_id" value="${question.questionId}" />
										</c:when>
										<c:otherwise>
											<input type="hidden" name="op" value="create" />
										</c:otherwise>
									</c:choose>
									
									<input type="hidden" id='option_count' name="option_count" value='${options ne null ? fn:length(options) : "0"}'/>
									
									<table align="center" width="100%" class="formTable">
										<tr>
                    	<td colspan="2" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                    </tr>
                    
                    <tr id="msgRow" class="messageText" style='display: ${msg ne null ? "inline" : "none"}'>
                    	<td id="msgTxt" align="center">${msg}</td>
                    </tr>
                  
                    <tr>
                    	<td class="label">Section:<span class="requiredStar">*&nbsp;</span><br/>
                    	
                    		<survey:SectionList 
                    			id="section_id" 
                    			survey="${SURVEY_BEAN}" 
                    			value="${SURVEY_SECTION_BEAN}" 
                    			style="width:200px;" 
                    			cls="requiredInputBox" />
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td class="label">Question Type: <span class="requiredStar">*&nbsp;</span><br/>
                    	
                    		<survey:QuestionTypeList 
                    			id="question_type" 
                    			style="width:200px;" 
                    			cls="requiredInputBox" 
                    			value="${question ne null ? question.questionType : null}" />
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td class="label">Is Manditory?<span class="requiredStar">*&nbsp;</span> &nbsp;
                    		<input 
                    			id="question_manditory" 
               					 	name="question_manditory" 
               					 	type="checkbox" 
               					 	class="requiredInputBox" ${question ne null && question.manditory ? "CHECKED" : "" } />
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td class="label" valign="top">Stem: <span class="requiredStar">*&nbsp;</span><br/>
                    		<textarea 
                    			id="question_stem" 
                    			name="question_stem" 
                    			class="requiredInputBox" 
                    			style="width:90%;height:100px;">${question ne null ? question.questionBody : ""}</textarea>
                    	</td>
                    </tr>
                    
                  	<tr class='bullets-question-info'>
               				<td class="label" ># Bullets:<span class="requiredStar">*&nbsp;</span><br/>
               				<input class="requiredInputBox" name='question_bullets' type="text" value="" /></td>
               			</tr>
               			
               			<tr class='bullets-question-info'>
               				<td class="label">Bullet Length:<span class="requiredStar">*&nbsp;</span><br/>
               				<input class="requiredInputBox" name='question_bullet_length' type="text" value="" /></td>
               			</tr>
                    		
                    <tr id='rowQuestionOptions' style='display:none;'>
                    	<td  align='left'>
                    		<table id="tblQuestionOptions" width="95%" cellspacing="2" cellpadding="2" border="0">
	                    		<tr>
			               				<td class="label">Answer Options: <span class="requiredStar">*&nbsp;</span><br/>
			               				<input id='btnAddOption' type="button" value=" + " /></td>
			               			</tr>
			               			
			               			<tr>
                    				<td class="label">Include Other?<br/>
                    					<input 
                    						id="include_other" 
                    						name="include_other" type="checkbox" 
                    						class="requiredInputBox" />
                    				</td>
                    			</tr>
                    		</table>
                    	</td>
                    </tr>
                    
                    
									</table>
									
									<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0">
										<tr>
                    	<td align="center"><input type="submit" value='${question ne null ? "Update Question" : "Create Question"}' /></td>
                    </tr>
									</table>
									
								</form>
                	
                	<p>                	
                	<div align="center"><a href="javascript:history.go(-1)"><img src="../includes/img/backa-off.png" class="img-swap" border=0></a></div>
                	
                	
                	<br/>&nbsp;<br/>
					
					
						
				</div>
			</div>
		</div>

<div style="float:right;padding-right:3px;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0 style="width:100%;"></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2017 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  

    
  </body>

</html>	