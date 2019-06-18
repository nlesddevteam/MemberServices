package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentBean;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentPreviewBean;
import com.esdnl.payadvice.bean.NLESDSubWorkHistoryPreviewBean;
import com.esdnl.payadvice.constants.NLESDPayrollDocumentType;
import com.esdnl.payadvice.dao.NLESDPayrollDocumentManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ProcessNLESDPayrollDocumentRequestHandler extends RequestHandlerImpl {

	public ProcessNLESDPayrollDocumentRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		String testing = form.get("strfileids");

		boolean payroll = false;
		boolean mapping = false;
		boolean history = false;

		NLESDPayrollDocumentPreviewBean paydoc = null;
		NLESDSubWorkHistoryPreviewBean hisdoc = null;

		if (testing.length() > 0) {
			String[] ids = form.get("strfileids").split(",");

			for (String s : ids) {

				try {
					ArrayList<NLESDPayrollDocumentBean> doclist = NLESDPayrollDocumentManager.getNLESDPayrollDocumentByID(Integer.parseInt(s));

						for (NLESDPayrollDocumentBean doc : doclist) {
							if (doc.getType() == NLESDPayrollDocumentType.PAYROLL_DATA) {
								request.setAttribute("payroll_file", doc);
	
								paydoc = NLESDPayrollDocumentManager.getNumberOfPayrollRecords(doc.getFilename(), "G_EMP_DATA");
								request.setAttribute("payrollrecords", paydoc);
	
								payroll = true;
							}
							else if (doc.getType() == NLESDPayrollDocumentType.EMPLOYEE_MAPPING) {
								request.setAttribute("mapping_file", doc);
								request.setAttribute("mappingcount",
										NLESDPayrollDocumentManager.getNumberOfLinesMappingFile(doc.getFilename()));
	
								mapping = true;
							}
							else {
								request.setAttribute("other_file", doc);
	
								hisdoc = NLESDPayrollDocumentManager.getNumberOfWorkHistoryRecords(doc.getFilename());
								request.setAttribute("workhistoryrecords", hisdoc);
	
								history = true;
							}
					}
				}
				catch (NumberFormatException e) {
						e.printStackTrace();
				}
				catch (NLESDPayAdviceException e) {
						e.printStackTrace();
				}
			}
		}

		if ((!mapping) || (!payroll) || (!history)) {
			request.setAttribute("msg", "Please select mapping, payroll and history file(s)");
		}

		path = "process_payroll_files.jsp";

		return path;
	}

}
