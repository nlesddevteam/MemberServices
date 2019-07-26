package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.bean.FileTypeBean;
import com.nlesd.bcs.dao.FileHistoryManager;
import com.nlesd.bcs.dao.FileTypeManager;

public class ContractorDeleteFileAjaxRequestHandler extends BCSApplicationRequestHandlerImpl {
	public ContractorDeleteFileAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String smessage="SUCCESS";
		BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
	    String deletetype="";
	    try {
	    Integer did = form.getInt("did");
        Integer dtype = form.getInt("dtype");
        String fileName = form.get("filename");
        //get the file type object to use
        FileTypeBean ftb = FileTypeManager.getFIleTypeById(dtype);
        if(ftb.getFileCategory().equals("BCS_CONTRACTOR_EMPLOYEE")) {
        	deletetype="E";
        }else {
        	deletetype="V";
        }
        //now we save the current object to history table
        FileHistoryBean fhb = new FileHistoryBean();
        fhb.setFileName(fileName);
        fhb.setFileAction("DELETED");
        fhb.setActionBy(bcbean.getContractorName());
        fhb.setParentObjectId(did);
        fhb.setParentObjectType(ftb.getId());
        FileHistoryManager.addFileHistory(fhb);
        //now we update the record to show the file delete
        FileHistoryManager.deleteFile(ftb, did);
        
	    }catch(Exception e) {
	    	smessage=e.getMessage();
	    }
        
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<FILES>");
		sb.append("<FILE>");
		sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
		sb.append("<DTYPE>" + deletetype + "</DTYPE>");
		sb.append("</FILE>");
		sb.append("</FILES>");
		xml = sb.toString().replaceAll("&", "&amp;");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
		
         return null;
	}
}