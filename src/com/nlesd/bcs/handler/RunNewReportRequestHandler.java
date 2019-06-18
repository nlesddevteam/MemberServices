package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportBean;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportConditionsBean;
import com.nlesd.bcs.dao.BuildCustomReportManager;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportConditionsManager;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportManager;
public class RunNewReportRequestHandler extends RequestHandlerImpl
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
		  super.handleRequest(request, response);
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
	    //get the  report details
	    int[] tables = form.getIntArray("selecttables");
	    int[] tablesfields = form.getIntArray("to");
	    int[] fields = form.getIntArray("fieldid");
	    int[] ddvalues = form.getIntArray("selectid");
	    String[] operators = form.getArray("operatorid");
	    String[] ctexts = form.getArray("ctext");  
	    String[] startdates = form.getArray("startdate");
	    String[] enddates = form.getArray("enddate");
	    StringBuilder sb = new StringBuilder();
	    StringBuilder sbheader = new StringBuilder();
	    sb.append(BuildCustomReportManager.buildSelectClause(tablesfields,sbheader,tables));
	    sb.append(BuildCustomReportManager.buildFromClause(tables));
	    sb.append(BuildCustomReportManager.buildWhereClause(fields,operators,ctexts,ddvalues,startdates,enddates,usr,tables));
	    request.setAttribute("tablebody", BuildCustomReportManager.runReportSql(sb.toString()));
	    request.setAttribute("tableheader",sbheader.toString());
	    //now we save the report if it is to be saved
	    if(form.get("reportname").length() > 0){
	    	BussingContractorSystemCustomReportBean rbean = new BussingContractorSystemCustomReportBean();
	    	rbean.setReportUser(usr.getPersonnel().getFullNameReverse());
	    	rbean.setReportName(form.get("reportname"));
	    	rbean.setReportTables(Arrays.toString(tables));
	    	rbean.setReportTableFields(Arrays.toString(tablesfields));
	    	rbean.setReportSql(sb.toString());
	    	BussingContractorSystemCustomReportManager.addBussingContractorCustomReport(rbean);
	    	//now we save the conditions
	    	int x=0;
	    	if(fields != null){
		    	for(int fieldid : fields){
		    		BussingContractorSystemCustomReportConditionsBean cbean = new BussingContractorSystemCustomReportConditionsBean();
		    		cbean.setFieldId(fieldid);
		    		cbean.setOperatorId(operators[x]);
		    		cbean.setcText(ctexts[x]);
		    		cbean.setReportId(rbean.getId());
		    		cbean.setSelectId(ddvalues[x]);
		    		cbean.setStartDate(startdates[x]);
		    		cbean.setEndDate(enddates[x]);
		    		BussingContractorSystemCustomReportConditionsManager.addBussingContractorCustomConditionsReportBean(cbean);
		    		x++;
		    	}
	    	}
	    }
	    
	    path = "admin_view_report.jsp";
	    return path;
	  }
}
