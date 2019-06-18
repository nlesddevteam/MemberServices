package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateCustomReportAjaxRequestHandler extends RequestHandlerImpl{
	public UpdateCustomReportAjaxRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
			super.handleRequest(request, response);
			String message="UPDATED";
			HttpSession session = null;
		    User usr = null;
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
		    int reportid = form.getInt("reportid");
		    BussingContractorSystemCustomReportBean origbean = BussingContractorSystemCustomReportManager.getCustomReportsById(reportid);
		    //built to take multiple tables in future
		    int reporttable=Integer.parseInt(origbean.getReportTables().replace("[","").replace("]", "").split(",")[0]);
		    int[] tables = new int[1];
		    tables[0]=reporttable;
		    //ajax serialize does not send through multiselect fields correctly
		    String selectedfields = form.get("selectedfields");
		    String test[] = selectedfields.split(",");
		    int[] tablesfields = new int[test.length];
		    int xx=0;
		    for(String ss: test){
		    	tablesfields[xx]=Integer.parseInt(ss);
		    	xx++;
		    }
		    int[] fields = form.getIntArray("fieldid");
		    int[] ddvalues = form.getIntArray("selectid");
		    String[] operators = form.getArray("operatorid");
		    String[] ctexts = form.getArray("ctext");  
		    String[] startdates = form.getArray("startdate");
		    String[] enddates = form.getArray("enddate");
		    int[] cids = form.getIntArray("cid");
		    StringBuilder sb = new StringBuilder();
		    StringBuilder sbheader = new StringBuilder();
		    sb.append(BuildCustomReportManager.buildSelectClause(tablesfields,sbheader,tables));
		    sb.append(BuildCustomReportManager.buildFromClause(tables));
		    sb.append(BuildCustomReportManager.buildWhereClause(fields,operators,ctexts,ddvalues,startdates,enddates,usr,tables));
		    //now we save the report
	    	BussingContractorSystemCustomReportBean rbean = new BussingContractorSystemCustomReportBean();
	    	rbean.setReportUser(usr.getPersonnel().getFullNameReverse());
	    	rbean.setReportName(form.get("reportname"));
	    	rbean.setReportTables(Arrays.toString(tables));
	    	rbean.setReportTableFields(Arrays.toString(tablesfields));
	    	rbean.setReportSql(sb.toString());
	    	rbean.setId(origbean.getId());
	    	BussingContractorSystemCustomReportManager.updateBussingContractorCustomReport(rbean);
	    	//now we save the conditions
	    	int x=0;
	    	if(!(cids == null)){
	    	for(int conid : cids){
	    		if(conid == -1){
	    			BussingContractorSystemCustomReportConditionsBean cbean = new BussingContractorSystemCustomReportConditionsBean();
		    		cbean.setFieldId(fields[x]);
		    		cbean.setOperatorId(operators[x]);
		    		cbean.setcText(ctexts[x]);
		    		cbean.setReportId(rbean.getId());
		    		cbean.setSelectId(ddvalues[x]);
		    		cbean.setStartDate(startdates[x]);
		    		cbean.setEndDate(enddates[x]);
		    		BussingContractorSystemCustomReportConditionsManager.addBussingContractorCustomConditionsReportBean(cbean);
	    		}
	    		x++;
	    	}
	    	}
	    	//now we check to see if we need to delete any conditions
	    	if(!(form.get("deletedids") == "")){
	    		String[] deleteids = form.get("deletedids").split(",");
	    		for(String s : deleteids){
	    			BussingContractorSystemCustomReportConditionsManager.deleteBussingContractorCustomConditionsReportBean(Integer.parseInt(s));
	    		}
	    	}
	    	String xml = null;
			StringBuffer sbb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sbb.append("<REPORTS>");
			sbb.append("<REPORT>");
			sbb.append("<MESSAGE>" + message + "</MESSAGE>");
			sbb.append("</REPORT>");
			sbb.append("</REPORTS>");
			xml = sbb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			
			return null;
		}
	
}
