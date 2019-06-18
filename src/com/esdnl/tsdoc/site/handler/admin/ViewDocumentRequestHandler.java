package com.esdnl.tsdoc.site.handler.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.AuditActionBean;
import com.esdnl.tsdoc.bean.TrusteeDocumentAuditBean;
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;
import com.esdnl.tsdoc.manager.TrusteeDocumentAuditManager;
import com.esdnl.tsdoc.manager.TrusteeDocumentManager;

public class ViewDocumentRequestHandler extends RequestHandlerImpl {

	public ViewDocumentRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("doc-id")
		});

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				if (form.hasValue("op", "clean-up")) {
					File tmpdir = new File(session.getServletContext().getRealPath("/") + "tsdoc/" + form.get("tmpdir"));

					if (tmpdir.exists()) {
						for (File f : tmpdir.listFiles())
							System.out.println("CLEAN UP: " + f + " deleted? " + f.delete());

						tmpdir.delete();
					}
					else
						System.out.println("CLEAN UP: " + tmpdir + " does not exits.");

				}
				else {
					TrusteeDocumentBean doc = TrusteeDocumentManager.getTrusteeDocumentBean(form.getInt("doc-id"));

					request.setAttribute("doc", doc);

					TrusteeDocumentAuditBean audit = new TrusteeDocumentAuditBean();
					audit.setAction(AuditActionBean.READ);
					audit.setAuditDate(Calendar.getInstance().getTime());
					audit.setDocument(doc);
					audit.setPersonnel(usr.getPersonnel());
					TrusteeDocumentAuditManager.addTrusteeDocumentAuditBean(audit);

					try {
						File pdffile = new File(session.getServletContext().getRealPath("/") + "WEB-INF/trustee-documents/"
								+ doc.getFilename());
						File tmpfile = new File(session.getServletContext().getRealPath("/") + "tsdoc/"
								+ Calendar.getInstance().getTime().getTime() + "/" + doc.getFilename());

						request.setAttribute("tmpdir", tmpfile.getParentFile().getName());

						if (!tmpfile.getParentFile().exists())
							tmpfile.getParentFile().mkdirs();

						FileCopyUtils.copy(new FileInputStream(pdffile), new FileOutputStream(tmpfile));

						path = "view_document.jsp";
					}
					catch (FileNotFoundException e) {
						e.printStackTrace();

						path = "index.jsp";
					}
					catch (IOException e) {
						e.printStackTrace();

						path = "index.jsp";
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);

			path = "index.jsp";
		}

		return path;
	}
}
