package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentBean;
import com.esdnl.payadvice.dao.NLESDPayrollDocumentManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class DeleteNLESDPayrollDocumentRequestHandler extends RequestHandlerImpl {
	public DeleteNLESDPayrollDocumentRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
				String fileName=form.get("fn");
				Integer fileId=Integer.parseInt(form.get("fid"));
				//get list of files to delete from server directory
				ArrayList<NLESDPayrollDocumentBean> doclist = NLESDPayrollDocumentManager.getNLESDPayrollDocumentByID(fileId);
				for(NLESDPayrollDocumentBean doc : doclist){
					delete_file(NLESDPayrollDocumentBean.DOCUMENT_BASEPATH, doc.getFilename());
				}
				
				NLESDPayrollDocumentManager.deleteNLESDPayrollDocumentBean(fileName,fileId,usr.getPersonnel().getFullNameReverse());
				request.setAttribute("msg", "Document has been deleted");
				request.setAttribute("documents", NLESDPayrollDocumentManager.getNLESDPayrollDocumentBeans());
				path = "unprocessed_files.jsp";
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = "unprocessed_files.jsp";
		}
		return path;
	}
}
