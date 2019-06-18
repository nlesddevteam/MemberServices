package com.esdnl.payadvice.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.UploadFile;
import com.esdnl.payadvice.bean.NLESDPayrollDocumentBean;
import com.esdnl.payadvice.constants.NLESDPayrollDocumentType;
import com.esdnl.payadvice.dao.NLESDPayrollDocumentManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.tsdoc.bean.TSDocException;

public class AddNLESDPayrollDocumentRequestHandler extends RequestHandlerImpl {

	public AddNLESDPayrollDocumentRequestHandler() {
		this.requiredPermissions= new String[] {
			"PAY-ADVICE-ADMIN"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		Integer fileGroup = 0;

		try {
				if (form.hasValue("op", "confirm")) {
					if (validate_form()) {
						try {
								NLESDPayrollDocumentBean payrolldoc = new NLESDPayrollDocumentBean();
								NLESDPayrollDocumentBean mappingdoc = new NLESDPayrollDocumentBean();
								NLESDPayrollDocumentBean otherdoc = new NLESDPayrollDocumentBean();
		
								boolean validfiles = false;
		
								if (form.uploadFileExists("payroll-file")) {
									UploadFile file = (UploadFile) form.getUploadFiles().get("payroll-file");
									payrolldoc.setOriginalFileName(file.getFileName());
									payrolldoc.setFilename(save_file("payroll-file", NLESDPayrollDocumentBean.DOCUMENT_BASEPATH));
									payrolldoc.setUploadedBy(usr.getPersonnel().getFullNameReverse());
									payrolldoc.setNotes(form.get("payroll-notes"));
									payrolldoc.setType(NLESDPayrollDocumentType.PAYROLL_DATA);
									payrolldoc.setFileGroup(fileGroup);
									fileGroup = NLESDPayrollDocumentManager.addNLESDPayrollDocumentBean(payrolldoc);
		
									validfiles = true;
								}
		
								if (form.uploadFileExists("mapping-file")) {
									UploadFile file = (UploadFile) form.getUploadFiles().get("mapping-file");
									mappingdoc.setOriginalFileName(file.getFileName());
									mappingdoc.setFilename(save_file("mapping-file", NLESDPayrollDocumentBean.DOCUMENT_BASEPATH));
									mappingdoc.setUploadedBy(usr.getPersonnel().getFullNameReverse());
									mappingdoc.setNotes(form.get("mapping-notes"));
									mappingdoc.setType(NLESDPayrollDocumentType.EMPLOYEE_MAPPING);
									mappingdoc.setFileGroup(fileGroup);
									NLESDPayrollDocumentManager.addNLESDPayrollDocumentBean(mappingdoc);
		
									validfiles = true;
								}
		
								if (form.uploadFileExists("other-file")) {
									UploadFile file = (UploadFile) form.getUploadFiles().get("other-file");
									otherdoc.setOriginalFileName(file.getFileName());
									otherdoc.setFilename(save_file("other-file", NLESDPayrollDocumentBean.DOCUMENT_BASEPATH));
									otherdoc.setUploadedBy(usr.getPersonnel().getFullNameReverse());
									otherdoc.setNotes(form.get("other-notes"));
									otherdoc.setType(NLESDPayrollDocumentType.OTHER);
									otherdoc.setFileGroup(fileGroup);
									NLESDPayrollDocumentManager.addNLESDPayrollDocumentBean(otherdoc);
		
									validfiles = true;
								}
		
								if (validfiles) {
									request.setAttribute("msg", "File(s) uploaded successfully.");
								}
								else {
									request.setAttribute("msg", "Please add file(s) to upload.");
								}
		
								path = "upload_files.jsp";
						}
						catch (TSDocException e) {
							request.setAttribute("msg", e.getMessage());
							path = "upload_files.jsp";
						}
					}
					else {
						request.setAttribute("msg", validator.getErrorString());
						path = "upload_files.jsp";
					}
				}
				else {
					path = "upload_files.jsp";
				}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "upload_files.jsp";
		}

		return path;
	}
}
