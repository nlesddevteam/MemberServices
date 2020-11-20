package com.esdnl.payadvice.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.efile.DocumentException;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.payadvice.worker.NLESDPayAdvicePayStubProcessWorker;
import com.esdnl.servlet.RequestHandlerImpl;

public class CloseNLESDPayAdvicePayPeriodRequestHandler   extends RequestHandlerImpl{
	public static final String DOCUMENT_BASEPATH = "WEB-INF/uploads/payadvice/output/";
	public CloseNLESDPayAdvicePayPeriodRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String paygroupid = form.get("id");
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String sresult ="";
		Date finishtime = null;
			try {
					Date starttime = new Date();
					NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(Integer.parseInt(paygroupid));
					String foldername=pgbean.getPayBgDt().replace("/", "") + pgbean.getPayEndDt().replace("/", "");
					String dirname=ControllerServlet.CONTEXT_BASE_PATH + NLESDPayAdvicePayStubProcessWorker.DOCUMENT_BASEPATH + foldername;
					//now we find the directory
			        //delete physical files.
			        File doc_dir = new File(dirname+ "/stubs");
			        if(doc_dir.exists()){
			          File files[] = doc_dir.listFiles();
	
			          boolean deleted;
			          for(int i=0; i < files.length; i++){
			            deleted = files[i].delete();
			            if(!deleted) {
			              throw new DocumentException("deleteDocument: Could not delete physical file [" + files[i].getAbsolutePath()+"]");
			            }
			          }
			          //now delete the two folders
			          doc_dir = new File(dirname+ "/stubs");
				      if(doc_dir.exists()){
				        	doc_dir.delete();
				      }
				      //now delete the two folders
				      doc_dir = new File(dirname);
					  if(doc_dir.exists()){
					       	doc_dir.delete();
					  }
					  finishtime = new Date();
					  NLESDPayAdvicePayrollProcessManager.closeNLESDPayAdvicePayPeriod(Integer.parseInt(paygroupid), starttime, finishtime, usr.getPersonnel().getFullNameReverse());
				      sresult = "NO ERROR";
			        }
			        else
			        {
			          sresult = "Payroll Directory does not exist.";
			          finishtime = new Date();
			        }
			        // generate XML to show confirmation of closing
					SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
					sb.append("<PAYPERIOD>");
					sb.append("<CLOSEDSTATUS>");
					sb.append("<MESSAGE>" + sresult  + "</MESSAGE>");
					sb.append("<START>" + dt.format(starttime) + "</START>");
					sb.append("<FINISH>" + dt.format(finishtime) + "</FINISH>");
					sb.append("<USER>" +  usr.getPersonnel().getFullNameReverse() + "</USER>");
					sb.append("</CLOSEDSTATUS>");
					sb.append("</PAYPERIOD>");
					xml = sb.toString().replaceAll("&", "&amp;");
					System.out.println(xml);
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
			}
			catch (Exception e) {
				e.printStackTrace();
				sb.append("<PAYPERIOD>");
				sb.append("<CLOSEDSTATUS>");
				sb.append("<MESSAGE>" + e.getMessage()  + "</MESSAGE>");
				sb.append("</CLOSEDSTATUS>");
				sb.append("</PAYPERIOD>");
				xml = sb.toString().replaceAll("&", "&amp;");
				System.out.println(xml);
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;
				return path;
			}
		return path;
		}
	
}
