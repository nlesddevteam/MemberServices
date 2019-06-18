package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractHistoryBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractHistoryManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
public class AwardContractAjaxRequestHandler extends RequestHandlerImpl {
	public AwardContractAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer cid =Integer.parseInt(form.get("cid"));
			Integer contractorid = Integer.parseInt(form.get("conid"));
			String snotes = form.get("snotes");
			BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(contractorid);
			BussingContractorSystemContractBean conBean = BussingContractorSystemContractManager.getBussingContractorSystemContractById(cid);
			//update contractor status
			BussingContractorSystemContractHistoryBean hbean = new BussingContractorSystemContractHistoryBean();
			hbean.setContractId(cid);
			hbean.setContractorId(contractorid);
			hbean.setContractStatus(85);
			hbean.setStatusBy(usr.getPersonnel().getFullNameReverse());
			hbean.setStatusNotes(snotes);
			BussingContractorSystemContractHistoryManager.addBussingContractorSystemContractHistory(hbean);
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTAWARDED);
			atbean.setEntryId(hbean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTHISTORY);
			atbean.setEntryNotes("Contract: " + conBean.getContractName() + " awarded to " + bcbean.getCompany() + "(" + bcbean.getLastName() + "," + bcbean.getFirstName()
					+ ") by " + usr.getPersonnel().getFullNameReverse() );
			atbean.setContractorId(bcbean.getId());
			AuditTrailManager.addAuditTrail(atbean);
			//send awarded message 
			// 2 email contractor
			EmailBean email = new EmailBean();
			email.setTo(bcbean.getEmail());
			email.setFrom("bussingcontractorsystem@nlesd.ca");
			email.setSubject("NLESD Bussing Contractor System Contract Awarded");
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("contractname", conBean.getContractName());
			email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/award_contract.vm", model));
			//email.send();
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTOR>");
			sb.append("<CONTRACTORSTATUS>");
			sb.append("<MESSAGE>STATUSUPDATED</MESSAGE>");
			sb.append("</CONTRACTORSTATUS>");
			sb.append("</CONTRACTOR>");
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
			sb.append("<CONTRACTOR>");
			sb.append("<CONTRACTORSTATUS>");
			sb.append("<MESSAGE>ERROR SETTING CONTRACTOR STATUS</MESSAGE>");
			sb.append("</CONTRACTORSTATUS>");
			sb.append("</CONTRACTOR>");
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
