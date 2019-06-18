package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemEmployeeTrainingBean;
import com.nlesd.bcs.dao.BussingContractorSystemEmployeeTrainingManager;
public class GetEmployeeTrainingByIdAjaxRequestHander extends RequestHandlerImpl {
	public GetEmployeeTrainingByIdAjaxRequestHander() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			int trainingid = form.getInt("tid");
			StringBuilder spath = new StringBuilder();
			spath.append(request.getContextPath());
			spath.append("/BCS/documents/employeedocs/");
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAINING>");
			sb.append("<TITEM>");
			BussingContractorSystemEmployeeTrainingBean ebean = BussingContractorSystemEmployeeTrainingManager.getEmployeeTrainingByTrainingId(trainingid);
			sb.append("<ID>" + ebean.getPk() + "</ID>");
			sb.append("<TRAININGTYPE>" + ebean.getTrainingType() + "</TRAININGTYPE>");
			sb.append("<TRAININGDATE>" + ebean.getTrainingDateFormatted() + "</TRAININGDATE>");
			sb.append("<EXPIRYDATE>" + ebean.getExpiryDateFormatted() + "</EXPIRYDATE>");
			sb.append("<NOTES>" + ebean.getNotes() + "</NOTES>");
			if(ebean.gettDocument() == null){
				sb.append("<TDOCUMENT>NONE</TDOCUMENT>");
			}else{
				sb.append("<TDOCUMENT>" + spath + ebean.gettDocument() + "</TDOCUMENT>");
			}
			
			sb.append("<TRAININGLENGTH>" + ebean.getTrainingLength() + "</TRAININGLENGTH>");
			if(ebean.getProvidedBy() == null){
				sb.append("<PROVIDEDBY></PROVIDEDBY>");
			}else{
				sb.append("<PROVIDEDBY>" + ebean.getProvidedBy() + "</PROVIDEDBY>");
			}
			if(ebean.getLocation() == null){
				sb.append("<LOCATION></LOCATION>");
			}else{
				sb.append("<LOCATION>" + ebean.getLocation() + "</LOCATION>");
			}
			
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</TITEM>");
			sb.append("</TRAINING>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

			
		}
		catch (Exception e) {
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAINING>");
			sb.append("<TITEM>");
			sb.append("<MESSAGE>ERROR GETTING TRAINING</MESSAGE>");
			sb.append("</TITEM>");
			sb.append("</TRAINING>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

		}
		return path;
	}

}