<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.school.*,com.esdnl.util.*,
                  com.esdnl.photocopier.bean.*,
                  com.esdnl.photocopier.dao.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/photocopiers.tld" prefix="copier" %>

<%
  PhotocopierBean pc = null;
  
  if(request.getParameter("id") != null)
    pc = PhotocopierManager.getPhotocopierBean(Integer.parseInt(request.getParameter("id")));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Eastern School District - Member Services - Personnel-Package</title>
    <style type="text/css">@import 'css/photocopiers.css';</style>
    <script type="text/javascript">
      function managePurchased()
      {
        var obj = document.forms[0].purchased;
        var obj_val = obj.options[obj.selectedIndex].value;
                
        if(obj_val)
        {
          var row = document.getElementById("lease_term_row");
          if(row)
          {
            if(obj_val == "0")
              row.style.display='inline';
            else
              row.style.display='none';
          }
        }
      }
      
      function manageServiceContract()
      {
        var obj = document.forms[0].service_contract;
        var obj_val = obj.options[obj.selectedIndex].value;
                
        if(obj_val)
        {
          var row = document.getElementById("service_contract_row");
          if(row)
          {
            if(obj_val == "1")
              row.style.display='inline';
            else
              row.style.display='none';
          }
        }
      }
      
      function check_number(obj)
      {
        if(obj.value.match(/^\d+$/) == null)
        {
          alert('Invalid NUMBER format.');
          //obj.focus();
          //obj.select();
        }
      }
      
      function check_year(obj)
      {
        if(obj.value.match(/^\d{4}$/) == null)
        {
          alert('Invalid YEAR format.');
          //obj.focus();
          //obj.select();
        }
      }
      
      
      function check_date(obj)
      {
        if(obj.value.match(/^\d{2}\/\d{2}\/\d{2}/) == null)
        {
          alert('Invalid DATE format.');
          //obj.focus();
          //obj.select();
        }
      }
      
    </script>
  </head>
  
  <body onload="managePurchased();manageServiceContract();">
    <!--check view page permissions-->
    <esd:SecurityCheck permissions="PHOTOCOPIER-VIEW,PHOTOCOPIER-ADMIN-VIEW" />
    
    <table cellpadding="0" cellspacing="0" width="800" align="center" border="0">
      <tr>
        <td width="800">
          <img src="images/header.gif" width="800"><br>
        </td>
      </tr>
      <tr>
        <td width="800" style="border-bottom:solid 5px #FFCC00;">
          <table cellpadding="0" cellspacing="0" width="800">
            <tr>
              <!-- LEFT SIDE MENU -->
              <td id="side_menu" width="150" valign="top" style="background-color:#F6F6F6; padding-top:10px;padding-left:5px;border-right:solid 1px #c4c4c4;">
                <table width="150" cellpadding="0" cellspacing="0">
                  <tr><td width="100%"><a class="homeSideNavLink" href="view_copiers.jsp">> View Copiers</a></td></tr>
                  <tr><td width="100%"><a class="homeSideNavLink" href="add_copier.jsp">> Add Copier</a></td></tr>
                  <tr><td width="100%"><a class="homeSideNavLink" href="request_service.jsp">> Request Service</a></td></tr>
                </table>
              </td>
              
              <!-- MAIN CONTENT -->
              <td id="main_content" width="650" style="border-left:solid 1px #333333;padding-left:5px;padding-top:10px;padding-bottom:10px;">
                <form name="add_copier_form" action="addCopier.html" method="POST">
                  <%if(pc != null){%>
                    <input type="hidden" name="id" value="<%=pc.getId()%>">
                  <%}%>
                  <table cellspacing="0" cellpadding="0" width="650">
                    <tr>
                      <td class="displayPageTitle" colspan="2"><%=(pc !=null)?"Update":"Add"%> Photocopier</td>
                    </tr>
                    <%if(request.getAttribute("msg") != null){%>
                      <tr style="padding-top:10px;">
                        <td class="messageText" colspan="2" align="center"><%=(String)request.getAttribute("msg")%></td>
                      </tr>
                    <%}%>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Brand</td>
                      <td width="*"><copier:PhotocopierBrands id="brand" cls="requiredInputBox" style="width:250px;" value='<%=(pc != null)?Integer.toString(pc.getBrand().getId()):""%>' /></td>
                    </tr>
                    <tr style="padding-top:5px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Model #</td>
                      <td width="*"><input type="text" id="model_num" name="model_num" class="requiredInputBox" style="width:250px;" value="<%=(pc != null)?pc.getModelNumber():""%>"></td>
                    </tr>
                    <tr style="padding-top:5px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Year Acquired</td>
                      <td width="*"><input type="text" id="year_acquired" name="year_acquired" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?Integer.toString(pc.getYearAcquired()):""%>" onblur="check_year(this);"></td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Acquired From</td>
                      <td width="*"><copier:PhotocopierSuppliers id="acquired_from" cls="requiredInputBox" style="width:250px;" value='<%=(pc != null)?Integer.toString(pc.getAcquiredFrom().getId()):""%>' /></td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Purchased?</td>
                      <td width="*"><copier:YesNo id="purchased" cls="requiredInputBox" style="width:50px;" value='<%=(pc != null)?(pc.isPurchased()?"YES":"NO"):"YES"%>' onchange="managePurchased();" /></td>
                    </tr>
                    <tr id="lease_term_row" style="padding-top:10px; display:none;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Lease Term (months)</td>
                      <td width="*"><input type="text" id="lease_term" name="lease_term" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?Integer.toString(pc.getLeaseTerm()):""%>" onblur="check_number(this);"></td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Has Service Contract?</td>
                      <td width="*"><copier:YesNo id="service_contract" cls="requiredInputBox" style="width:50px;" value='<%=(pc != null)?(pc.hasServiceContract()?"YES":"NO"):"NO"%>' onchange="manageServiceContract();" /></td>
                    </tr>
                    <tr id="service_contract_row" style="display:none;">
                      <td colspan="2">
                        <table cellpadding=="0" cellspacing="0" width="100%" border="0">
                          <tr style="padding-top:10px;">
                            <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Contract Start Date (mm/dd/yyyy)</td>
                            <td width="*"><input type="text" id="contract_start_date" name="contract_start_date" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?(new SimpleDateFormat("MM/dd/yyyy")).format(pc.getContractStartDate()):""%>" onblur="check_date(this);"></td>
                          </tr>
                          <tr style="padding-top:10px;">
                            <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Contract Term (months)</td>
                            <td width="*"><input type="text" id="contract_term" name="contract_term" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?Integer.toString(pc.getContractTerm()):""%>" onblur="check_number(this);"></td>
                          </tr>
                          <tr style="padding-top:10px;">
                            <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Serviced By</td>
                            <td width="*"><copier:PhotocopierSuppliers id="serviced_by" cls="requiredInputBox" style="width:250px;" value='<%=(pc != null)?Integer.toString(pc.getServicedBy().getId()):""%>' /></td>
                          </tr>
                          <tr style="padding-top:10px;">
                            <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Average Service Time (days)</td>
                            <td width="*"><input type="text" id="average_service_time" name="average_service_time" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?Double.toString(pc.getAverageServiceTime()):""%>"  onblur="check_number(this);"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150"># Copies</td>
                      <td width="*"><input type="text" id="num_copies" name="num_copies" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?Integer.toString(pc.getNumberCopies()):""%>" onblur="check_number(this);"></td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Effective Date (mm/dd/yyyy)</td>
                      <td width="*"><input type="text" id="effective_date" name="effective_date" class="requiredInputBox" style="width:75px;" value="<%=(pc != null)?(new SimpleDateFormat("MM/dd/yyyy")).format(pc.getEffectiveDate()):""%>" onblur="check_date(this);"></td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="right" style="padding-right:3px;" width="150">Other Comments</td>
                      <td width="*">
                        <textarea id="other_comments" name="other_comments" class="requiredInputBox" style="width:350px;height:50px;"><%if(pc != null)pc.getOtherComments();%></textarea>
                      </td>
                    </tr>
                    <tr style="padding-top:10px;">
                      <td class="displayHeaderTitle" align="center" style="padding-right:3px;" colspan="2">
                        <input type="submit" value="<%=(pc != null)?"Update":"Add"%>">
                      </td>
                    </tr>
                  </table>
                </form>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>