<%@ page language="java"
         session="true"
         import="java.util.*,com.awsd.security.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />


<html>
  <head>
     <title>MemberServices Permissions</title>
   
   <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
	</style>
	<script src="includes/js/categories.js"></script>
	<script>		
		$('document').ready(function(){
		
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});

		</script>
   
  </head>

<body>

<div class="siteHeaderGreen">Viewing  <span style="color:red;">${category.personnelCategoryName }</span> Category</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading
</div>		

<div style="display:none;" class="loadPage"> 
  <b>Unique Identifier: </b>${ category.personnelCategoryID}<input type="hidden" id="hidcatid" value='${ category.personnelCategoryID}'>
  <br/>
  <br/>
  <b>Name: </b>${ category.personnelCategoryName}
  <br/>
  <br/> 
  <b>Description: </b>${ category.personnelCategoryDescription}                    
 
  <hr>

  <div class="siteSubHeaderBlue">Applied Roles of ${category.personnelCategoryName }:</div>
  <br />
   <div style="float: right;margin: 10px 10px 10px 10px"><a onclick="openAddModal()"  class="btn btn-sm btn-primary">Add Role</a><br /></div>
             
				<table class="permissionsTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;" id="tabroles">
				<thead class="thead-dark">
				<tr style="color:Black;font-size:12px;">					
				<th width="80%">ROLE ID</th>
				<th width="20%">OPTIONS</th>					
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${ fn:length(roles) > 1 }">
							<c:forEach var="entry" items="${roles}" varStatus="status">
  								<tr id="tr${status.index}">
                    				<td width="80%">${entry.key}</td>
                        			<td width="20%"><a onclick="openRemoveRole('${entry.key}','${status.index}')" class="btn btn-xs btn-danger">REMOVE</a></td>
                        		</tr> 
							</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan='3'>No Applied Roles</td>
					</c:otherwise>
				</c:choose>
	           </tbody>
              </table>
   <br/><hr>  
 

 		<br />
    <div align="center">              
         
         <a onclick="loadingData();" href="viewCategories.html" class="btn btn-sm btn-danger">Exit  Category</a>
     </div>
     </div>
              		<br />
<div class="modal fade" id="removeCatRoleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Remove Category Role</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row" style="padding-top:5px;">
    		<div>
      			Are you sure you would like to remove the following role from this category?<input type="hidden" id="hidrowid"><input type="hidden" id="hidroleid">
    		</div>
    	</div>
  		<div class="row" style="padding-top:5px;">
    		<div id="spanroleid">
      			
    		</div>
  		</div>
  		</div>
      
      </div>
       
      
      <div class="modal-footer">
      <button type="button" class="btn btn-success btn-sm" onclick="removeCatRole()">Remove Role</button>
      	<button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>
              		<br />
<div class="modal fade" id="addCatRoleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Add Role to Category</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row" style="padding-top:5px;">
    		<div>
      			Please Select Role to Add to Category
    		</div>
    	</div>
  		<div class="row" style="padding-top:5px;">
    		<div>
      			<select id="newroles" class="form-control">
      				<option value=" ">Please select</option>
      				<c:forEach var="entry" items="${rolesdd}">
      					<option value='${entry}'>${entry}</option>
      				</c:forEach>
      			</select>
    		</div>
  		</div>
  		</div>
      
      </div>
       
      
      <div class="modal-footer">
      <button type="button" class="btn btn-success btn-sm" onclick="addRoleToCategory()">Add Role</button>
      	<button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>   
 </body>
 </html>
         