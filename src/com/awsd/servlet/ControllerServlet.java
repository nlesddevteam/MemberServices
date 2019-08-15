package com.awsd.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.app.Velocity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.awsd.mail.service.PersistentEmailService;
import com.awsd.navigation.handler.MemberServicesLoginRequestHandler;
import com.awsd.navigation.handler.MemberServicesLogoutRequestHandler;
import com.awsd.navigation.handler.ViewMemberServicesLoginRequestHandler;
import com.awsd.security.PersonnelApplicantSecurityException;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.addressbook.handler.ViewAddressBookRequestHandler;
import com.awsd.travel.handler.ViewTravelClaimSystemRequestHandler;
import com.esdnl.audit.IAuditable;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;

public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1378996656423923687L;
	// Data warehouse connection info
	public static String DATA_WAREHOUSE_JDBC_DRIVER = "";
	public static String DATA_WAREHOUSE_JDBC_URL = "";
	public static String DATA_WAREHOUSE_JDBC_USERNAME = "";
	public static String DATA_WAREHOUSE_JDBC_PASSWORD = "";

	// ACCPAC connection info
	public static String ACCPAC_JDBC_DRIVER = "";
	public static String ACCPAC_JDBC_URL = "";
	public static String ACCPAC_JDBC_USERNAME = "";
	public static String ACCPAC_JDBC_PASSWORD = "";
	public static String ACCPAC_EXPORT_STATUS = "OFF";
	public static long ACCPAC_EXPORT_DELAY = 6000;
	public static long ACCPAC_EXPORT_INTERVAL = 14400000;
	public static String ACCPAC_SUMMARY_DETAIL_GENERATION_ONLY = "OFF";

	// Lotus Notes connection info
	public static String LOTUS_SERVER_IP_ADDRESS = "";
	public static String LOTUS_JDBC_DRIVER = "";
	public static String LOTUS_JDBC_URL = "";
	public static String LOTUS_JDBC_USERNAME = "";
	public static String LOTUS_JDBC_PASSWORD = "";
	public static String LOTUS_ADDRESS_BOOK_FILE = "";
	public static String LOTUS_EMAIL_USERNAME = "";
	public static String LOTUS_EMAIL_PASSWORD = "";

	// FIRSTCLASS LDAP CONNECTION INFO
	public static String FIRSTCLASS_LDAP_SERVER_IP_ADDRESS = "";
	public static String FIRSTCLASS_LDAP_SERVER_PORT = "";
	public static String FIRSTCLASS_LDAP_SERVER_BASE_ORGANIZATION = "";
	public static String FIRSTCLASS_LDAP_SERVER_PRINCIPAL = "";
	public static String FIRSTCLASS_LDAP_SERVER_CREDENTIALS = "";

	// EFile config info
	public static String EFILE_TMP_UPLOAD_DIR = "";
	public static String EFILE_CONVERT_INPUT_DIR = "";
	public static String EFILE_CONVERT_OUTPUT_DIR = "";

	// financial config info
	public static String FINANCIAL_FISCAL_YEAR = "";
	public static int FINANCIAL_FISCAL_YEAR_START_DATE = -1;

	// WeatherCentral config info
	public static boolean WEATHER_CENTRAL_SUMMER_FLAG = false;
	public static String WEATHER_CENTRAL_EXPORT_STATUS = "OFF";

	// Persistent Mail Service config info
	public static String PERSISTENT_MAIL_SERVICE_STATUS = "OFF";

	public static final String CONTROLLER_CONFIG_FILE = "/config/MemberServices.xml";

	public static final String VELOCITY_CONFIG_FILE = "/WEB-INF/velocity/velocity.properties";

	//real path to web app
	public static String CONTEXT_BASE_PATH;

	private Map<String, String> handlerHash;

	public void init(ServletConfig config) throws ServletException {

		DocumentBuilderFactory docbf = null;
		DocumentBuilder docb = null;
		Document doc = null;
		NodeList nodes = null;
		NodeList children = null;
		Element element = null;

		try {
			super.init(config);

			CONTEXT_BASE_PATH = config.getServletContext().getRealPath("/");

			// initialize xml parser
			docbf = DocumentBuilderFactory.newInstance();
			docbf.setIgnoringElementContentWhitespace(true);
			docbf.setValidating(false);
			docbf.setNamespaceAware(false);
			docb = docbf.newDocumentBuilder();
			doc = docb.parse(new File(CONTEXT_BASE_PATH + CONTROLLER_CONFIG_FILE));

			// get data warehouse info
			nodes = doc.getElementsByTagName("Data-Warehouse-Config");
			element = (Element) nodes.item(0);
			nodes = element.getChildNodes();
			DATA_WAREHOUSE_JDBC_DRIVER = nodes.item(1).getFirstChild().getNodeValue();
			DATA_WAREHOUSE_JDBC_URL = nodes.item(3).getFirstChild().getNodeValue();
			DATA_WAREHOUSE_JDBC_USERNAME = nodes.item(5).getFirstChild().getNodeValue();
			DATA_WAREHOUSE_JDBC_PASSWORD = nodes.item(7).getFirstChild().getNodeValue();

			// get FIRSTCLASS config info
			nodes = doc.getElementsByTagName("FirstClass-Config");
			element = (Element) nodes.item(0);
			nodes = element.getChildNodes();
			FIRSTCLASS_LDAP_SERVER_IP_ADDRESS = nodes.item(1).getFirstChild().getNodeValue();
			FIRSTCLASS_LDAP_SERVER_PORT = nodes.item(3).getFirstChild().getNodeValue();
			FIRSTCLASS_LDAP_SERVER_BASE_ORGANIZATION = nodes.item(5).getFirstChild().getNodeValue();
			FIRSTCLASS_LDAP_SERVER_PRINCIPAL = nodes.item(7).getFirstChild().getNodeValue();
			FIRSTCLASS_LDAP_SERVER_CREDENTIALS = nodes.item(9).getFirstChild().getNodeValue();

			// get EFile config info
			nodes = doc.getElementsByTagName("EFile-Config");
			element = (Element) nodes.item(0);
			nodes = element.getChildNodes();
			EFILE_TMP_UPLOAD_DIR = nodes.item(1).getFirstChild().getNodeValue();
			EFILE_CONVERT_INPUT_DIR = nodes.item(3).getFirstChild().getNodeValue();
			EFILE_CONVERT_OUTPUT_DIR = nodes.item(5).getFirstChild().getNodeValue();

			// get financial config info
			nodes = doc.getElementsByTagName("Financial-Config");
			element = (Element) nodes.item(0);
			nodes = element.getChildNodes();
			FINANCIAL_FISCAL_YEAR = nodes.item(1).getFirstChild().getNodeValue();
			FINANCIAL_FISCAL_YEAR_START_DATE = Integer.parseInt(nodes.item(3).getFirstChild().getNodeValue());

			// get status central config info
			nodes = doc.getElementsByTagName("StatusCentral-Config");
			element = (Element) nodes.item(0);
			nodes = element.getChildNodes();
			WEATHER_CENTRAL_EXPORT_STATUS = nodes.item(1).getFirstChild().getNodeValue();

			//get persistent mail service config info
			nodes = doc.getElementsByTagName("Persistent-Mail-Service-Config");
			element = (Element) nodes.item(0);
			nodes = element.getChildNodes();
			PERSISTENT_MAIL_SERVICE_STATUS = nodes.item(1).getFirstChild().getNodeValue();

			// load JDBC drivers
			Class.forName(DATA_WAREHOUSE_JDBC_DRIVER);

			handlerHash = new HashMap<String, String>();

			// get all request handlers
			nodes = doc.getElementsByTagName("Request-Handler");

			for (int i = 0; i < nodes.getLength(); i++) {
				element = (Element) nodes.item(i);
				children = element.getChildNodes();

				handlerHash.put(children.item(3).getFirstChild().getNodeValue(),
						children.item(1).getFirstChild().getNodeValue());
			}

			// initialize EFile directories
			File efile_tmp_upload_dir = new File(EFILE_TMP_UPLOAD_DIR);
			File efile_convert_input_dir = new File(EFILE_CONVERT_INPUT_DIR);
			File efile_convert_output_dir = new File(EFILE_CONVERT_OUTPUT_DIR);

			if (!efile_tmp_upload_dir.exists()) {
				efile_tmp_upload_dir.mkdirs();
			}

			if (!efile_convert_input_dir.exists()) {
				efile_convert_input_dir.mkdirs();
			}

			if (!efile_convert_output_dir.exists()) {
				efile_convert_output_dir.mkdirs();
			}

			//start status central export service
			try {
				Class.forName("com.awsd.weather.SchoolSystems");
			}
			catch (NoClassDefFoundError e) {
				System.err.println("COULD NOT FIND com.awsd.weather.SchoolSystems CLASS.");
			}
			catch (ClassNotFoundException e) {
				System.err.println("COULD NOT FIND com.awsd.weather.SchoolSystems CLASS.");
			}

			//start announcement export service
			try {
				Class.forName("com.esdnl.webmaint.esdweb.service.AnnouncementExportService");
			}
			catch (NoClassDefFoundError e) {
				System.err.println("COULD NOT FIND com.esdnl.webmaint.esdweb.service.AnnouncementExportService CLASS.");
			}
			catch (ClassNotFoundException e) {
				System.err.println("COULD NOT FIND com.esdnl.webmaint.esdweb.service.AnnouncementExportService CLASS.");
			}

			//start job opportunity export service
			try {
				Class.forName("com.esdnl.personnel.jobs.service.OpenJobsExportService");
			}
			catch (NoClassDefFoundError e) {
				System.err.println("COULD NOT FIND com.esdnl.personnel.jobs.service.OpenJobsExportService CLASS.");
			}
			catch (ClassNotFoundException e) {
				System.err.println("COULD NOT FIND com.esdnl.personnel.jobs.service.OpenJobsExportService CLASS.");
			}

			// start email service
			if (PERSISTENT_MAIL_SERVICE_STATUS.equalsIgnoreCase("ON")) {
				new PersistentEmailService();
			}

			//initialize Velocity engine
			Properties vprops = new Properties();

			vprops.setProperty("resource.loader", "file");
			vprops.setProperty("file.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			vprops.setProperty("file.resource.loader.path", CONTEXT_BASE_PATH + "/WEB-INF/velocity/templates");
			vprops.setProperty("file.resource.loader.cache", "false");
			vprops.setProperty("file.resource.loader.modificationCheckInterval", "0");
			vprops.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
			vprops.setProperty("runtime.log.logsystem.log4j.category", "velocity");
			vprops.setProperty("runtime.log.logsystem.log4j.logger", "velocity");

			Velocity.init(vprops);
		}
		catch (ParserConfigurationException e) {
			System.err.println("ControllerServlet(init): " + e);
		}
		catch (SAXException e) {
			System.err.println("ControllerServlet(init): " + e);
		}
		catch (IOException e) {
			System.err.println("ControllerServlet(init): " + e);
		}
		catch (ClassNotFoundException e) {
			System.err.println("ControllerServlet(static): " + e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = null;
		RequestHandler rh = null;
		String viewURL = "";
		User usr = null;

		try {
			if (handlerHash.containsKey(request.getServletPath()))
				rh = (RequestHandler) Class.forName((String) handlerHash.get(request.getServletPath())).newInstance();

			if (rh == null) {
				System.err.println("NO HANDLER: " + request.getServletPath());
				response.sendRedirect("/MemberServices/memberServices.html");
			}
			else {
				if (!((rh instanceof MemberServicesLoginRequestHandler) || (rh instanceof ViewMemberServicesLoginRequestHandler)
						|| (rh instanceof MemberServicesLogoutRequestHandler) || (rh instanceof ViewAddressBookRequestHandler)
						|| (rh instanceof ViewTravelClaimSystemRequestHandler) || (rh instanceof LoginNotRequiredRequestHandler)
						|| (rh instanceof PersonnelApplicationRequestHandler))) {
					session = request.getSession(false);
					if ((session != null) && (session.getAttribute("usr") != null)) {
						usr = (User) session.getAttribute("usr");
						viewURL = rh.handleRequest(request, response);
						System.err.println(usr.getUsername() + ": " + viewURL);
					}
					else {
						response.getWriter().println("<HTML><HEAD><TITLE></TITLE></HEAD><BODY>");
						response.getWriter().println(
								"<SCRIPT>self.parent.location.href='/MemberServices/login.html?msg=Secure Resource!<br>Please Login.'</SCRIPT>");
						response.getWriter().println("</BODY></HTML>");
						response.getWriter().flush();
						return;
					}
				}
				else if (rh instanceof PersonnelApplicationRequestHandler) {
					session = request.getSession(false);
					if ((session != null) && (session.getAttribute("APPLICANT") != null)) {
						viewURL = rh.handleRequest(request, response);
						System.err.println(((ApplicantProfileBean) session.getAttribute("APPLICANT")).getEmail() + ": " + viewURL);
					}
					else {
						throw new PersonnelApplicantSecurityException("Illegal Access Attempt [" + request.getRequestURI() + "]");
					}
				}
				else if (rh instanceof MemberServicesLogoutRequestHandler) {
					session = request.getSession(false);
					if ((session != null) && (session.getAttribute("ADMIN_LOGIN_AS") != null)) {
						rh.handleRequest(request, response);

						response.getWriter().println("<HTML><HEAD><TITLE></TITLE></HEAD><BODY>");
						response.getWriter().println(
								"<SCRIPT>top.location.href='/MemberServices/memberservices_frame.jsp';</SCRIPT>");
						response.getWriter().println("</BODY></HTML>");
						response.getWriter().flush();
					}
					else {
						rh.handleRequest(request, response);

						response.getWriter().println("<HTML><HEAD><TITLE></TITLE></HEAD><BODY>");
						response.getWriter().println("<SCRIPT>self.parent.location.href='http://www.nlesd.ca/'</SCRIPT>");
						response.getWriter().println("</BODY></HTML>");
						response.getWriter().flush();
					}

					return;
				}
				else {
					viewURL = rh.handleRequest(request, response);
				}

				if (rh instanceof IAuditable) {
					((IAuditable) rh).auditRequest();
				}

				if (viewURL != null) {
					if (request.getAttribute("REDIRECT") == null)
						request.getRequestDispatcher(viewURL).forward(request, response);
					else
						response.sendRedirect(viewURL);
				}
			}
		}
		catch (PersonnelApplicantSecurityException e) {
			System.err.println(e.getMessage());
			response.sendRedirect("/MemberServices/Personnel/applicantlogin.html");
		}
		catch (SecurityException e) {
			System.err.println(e);
			response.sendRedirect("/MemberServices/memberServices.html");
		}
		catch (ClassNotFoundException e) {
			System.err.println("NO HANDLER: " + request.getServletPath());
			response.sendRedirect("/MemberServices/memberServices.html");
		}
		catch (IllegalAccessException e) {
			System.err.println(e);
			response.sendRedirect("/MemberServices/memberServices.html");
		}
		catch (InstantiationException e) {
			System.err.println(e);
			response.sendRedirect("/MemberServices/memberServices.html");
		}
	}
}