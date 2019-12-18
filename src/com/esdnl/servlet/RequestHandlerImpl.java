package com.esdnl.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.audit.IAuditable;
import com.esdnl.util.FileUtils;
import com.esdnl.util.StringUtils;

import javazoom.upload.UploadFile;

public class RequestHandlerImpl implements RequestHandler, IAuditable {

	// required by all RequestHandlers
	protected String[] requiredPermissions;
	protected String[] requiredRoles;
	protected Form form;
	protected FormValidator validator;
	protected HttpSession session;
	protected User usr;
	protected String path;
	protected File ROOT_DIR;

	public RequestHandlerImpl() {

		requiredPermissions = null;
		requiredRoles = null;
		form = null;
		validator = null;
		session = null;
		usr = null;
		path = null;
		ROOT_DIR = null;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		boolean validated = false;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (requiredPermissions != null) {
				for (int i = 0; (i < requiredPermissions.length); i++) {
					if (usr.checkPermission(requiredPermissions[i])) {
						validated = true;
						break;

					}
				}
			}
			else if (requiredRoles != null) {
				for (int i = 0; (i < requiredRoles.length); i++) {
					if (usr.checkRole(requiredRoles[i])) {
						validated = true;
						break;
					}
				}
			}
			else
				validated = true;
		}

		if (!validated)
			throw new SecurityException("Illegal Access [" + request.getRequestURI() + "] ["
					+ (usr != null ? usr.getLotusUserFullName() : " UNAUTHENICATED USER") + "]");

		form = new Form(request);

		ROOT_DIR = new File(session.getServletContext().getRealPath("/"));

		return null;
	}

	public void auditRequest() {

		return;
	}

	public final boolean validate_form() {

		boolean validated = false;

		if (form == null)
			validated = false;
		else if (validator == null)
			validated = true;
		else
			validated = validator.validate(form);

		return validated;
	}

	public final String save_file(String field_name, String relative_path) throws Exception {

		return save_file(field_name, relative_path, null);
	}

	public final String save_file(String field_name, String relative_path, String filename) throws Exception {

		String fname = "";

		if ((form != null) && form.isMultipart() && (form.getUploadFiles() != null)
				&& (form.getUploadFiles().entrySet().size() > 0)) {
			UploadFile file = (UploadFile) form.getUploadFiles().get(field_name);
			if ((file != null) && !StringUtils.isEmpty(file.getFileName()) && (file.getFileSize() > 0)) {
				File dir = new File(session.getServletContext().getRealPath("/") + (!relative_path.startsWith("/") ? "/" : "")
						+ relative_path + (!relative_path.endsWith("/") ? "/" : ""));

				if (!dir.exists())
					dir.mkdirs();

				if (org.apache.commons.lang.StringUtils.isNotEmpty(filename)) {
					fname = filename;

					if (fname.lastIndexOf(".") < 0) {
						fname += FileUtils.extractExtension(file.getFileName());
					}
				}
				else {
					fname = FileUtils.generateRandomFilename(dir, null, FileUtils.extractExtension(file.getFileName()));
				}

				if (!StringUtils.isEmpty(fname)) {
					ByteArrayInputStream in = new ByteArrayInputStream(file.getData());
					FileOutputStream fos = new FileOutputStream(new File(dir, fname));
					byte[] bytes = new byte[1024];

					for (int read = in.read(bytes); read > 0; read = in.read(bytes)) {
						fos.write(bytes, 0, read);
					}

					fos.close();
					in.close();
				}
				else
					throw new Exception("save_file: no file name provided.");
			}
			else
				throw new Exception("save_file: specified file not found in upload files.");
		}
		else
			throw new Exception("save_file: no upload files.");

		return fname;
	}

	public final boolean delete_file(String relative_path, String filename) {

		File f = new File(session.getServletContext().getRealPath("/") + (!relative_path.startsWith("/") ? "/" : "")
				+ relative_path + (!relative_path.endsWith("/") ? "/" : "") + filename);

		if (f.exists())
			f.delete();

		return !f.exists();
	}
}