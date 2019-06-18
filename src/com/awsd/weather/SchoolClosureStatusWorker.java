package com.awsd.weather;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Vector;

import com.awsd.school.School;
import com.awsd.servlet.ControllerServlet;

public class SchoolClosureStatusWorker extends TimerTask {

	private Vector<SchoolSystem> systems = null;

	private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../../webapps/nlesdweb/ROOT/";

	//private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../ROOT/";

	public SchoolClosureStatusWorker(Vector<SchoolSystem> systems) {

		super();

		synchronized (systems) {
			this.systems = systems;
		}

		System.err.println("<<<<<< SCHOOLSYSTEM TIMER STARTED >>>>>");
	}

	public void setSchoolSystems(Vector<SchoolSystem> systems) {

		synchronized (this.systems) {
			this.systems = systems;
		}
	}

	public void run() {

		Calendar cur = null;

		try {
			synchronized (this.systems) {
				systems.clear();
				systems.addAll(SchoolSystemDB.getSchoolClosureStatuses());
				System.err.println("<<<<<< SCHOOLSYSTEMS RELOADED >>>>>");

				this.writeCustomizeViewFileNLESDWeb();
				this.writeAllViewFileNLESDWeb();

				this.CBC_writeAllViewFile();

				cur = Calendar.getInstance();
				if (((cur.get(Calendar.MONTH) > Calendar.JUNE) && (cur.get(Calendar.MONTH) < Calendar.SEPTEMBER))
						|| ((cur.get(Calendar.MONTH) == Calendar.JUNE) && (cur.get(Calendar.DATE) > 25))) {
					System.err.println("<<<<<< SCHOOLSYSTEMS RELOADER STOPPED FOR SUMMER VACATION >>>>>");
					SchoolSystems.stopTimer();
				}
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	public void writeAllViewFileNLESDWeb() {

		File all_view_tmp = null, all_view_real = null;
		PrintWriter writer = null;
		Vector<SchoolSystem> ss = null;
		ClosureStatus stat = null;

		try {
			synchronized (this.systems) {
				ss = new Vector<SchoolSystem>(systems);
			}

			all_view_tmp = new File(nlesd_rootbasepath + "statuscentral/status_central_all_view.tmp");

			if (!all_view_tmp.getParentFile().exists())
				all_view_tmp.getParentFile().mkdirs();

			if (all_view_tmp.exists()) {
				System.err.println("<<<<< TEMP FILE ALREADY EXIST >>>>>");

				if (all_view_tmp.delete()) {
					System.err.println("<<<<< TEMP FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< TEMP FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(all_view_tmp), true);

			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>Newfoundland &amp; Labrador English School District - School Status Report</title>");
			writer.println("<link rel='stylesheet' href='/statuscentral/css/weathercentral.css'>");
			writer.println("</head>");
			writer.println("<body>");

			boolean isOffice = false;

			for (SchoolSystem sys : ss) {

				writer.println("<div class='school-system'>");

				writer.println("<div class='school-system-name'>" + sys.getSchoolSystemName().replaceAll("'", "&#39;")
						+ "</div>");

				for (School school : sys.getSchoolSystemSchools()) {
					isOffice = (school.getSchoolName().endsWith("Office") || (school.getSchoolID() == 220));

					stat = school.getSchoolClosureStatus();
					writer.println("<div class='school-system-school' zone='"
							+ (school.getZone() != null ? school.getZone().getZoneId() : "") + "'>");

					writer.println("<div class='school-name'>" + school.getSchoolName().replaceAll("'", "&#39;") + "</div>");

					writer.print("<div class='school-status " + cssClass(stat.getClosureStatusID()) + "'>");
					writer.print(!isOffice ? stat.getClosureStatusDescription() : stat.getClosureStatusDescription().replaceAll(
							"School", "Office"));
					writer.println("</div>");

					if (stat.getSchoolClosureNote() != null) {
						writer.print("<div class='school-status-note'><b>Note</b>:&nbsp;"
								+ stat.getSchoolClosureNote().replaceAll("'", "&#39;") + "</div>");
					}
					writer.println("<div class='clear'></div>");
					writer.println("</div>");
				}
				writer.println("</div>");
			}

			writer.println("</body>");
			writer.println("</html>");
			writer.flush();
			writer.close();

			all_view_real = new File(nlesd_rootbasepath + "statuscentral/status_central_all_view.html");

			if (all_view_real.exists()) {
				all_view_real.delete();
				System.err.println("<<<<<< EXISTING ALL VIEW FILE DELETED >>>>>>");
			}
			all_view_tmp.renameTo(all_view_real);
			ss.clear();
			System.err.println("<<<<<< ALL VIEW FILE REGENERATED >>>>>>");
			System.err.flush();
			System.gc();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	public void writeCustomizeViewFileNLESDWeb() {

		File customize_view_tmp = null, customize_view_real = null;
		PrintWriter writer = null;
		Vector<SchoolSystem> ss = null;
		Iterator<SchoolSystem> sys_iter = null;
		Iterator<School> sch_iter = null;
		SchoolSystem system = null;
		School school = null;
		ClosureStatus stat = null;

		try {
			synchronized (this.systems) {
				ss = new Vector<SchoolSystem>(systems);
			}

			customize_view_tmp = new File(nlesd_rootbasepath + "statuscentral/status_central_customize_view.tmp");

			if (!customize_view_tmp.getParentFile().exists())
				customize_view_tmp.getParentFile().mkdirs();

			if (customize_view_tmp.exists()) {
				System.err.println("<<<<< NLESD CUSTOMIZE TEMP FILE ALREADY EXIST >>>>>");

				if (customize_view_tmp.delete()) {
					System.err.println("<<<<< NLESD CUSTOMIZE TEMP FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< NLESD CUSTOMIZE TEMP FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(customize_view_tmp), true);

			sys_iter = ss.iterator();
			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>Newfoundland &amp; Labrador English School District - School Status Report</title>");
			writer.println("<link rel='stylesheet' href='/statuscentral/css/weathercentral.css'>");
			writer.println("<script type=\"text/javascript\" src=\"/statuscentral/js/scroller_base.js\"></script>");
			writer.println("<script type=\"text/javascript\" src=\"/statuscentral/js/scroller.js\"></script>");
			writer.println("<script type=\"text/javascript\" src=\"/statuscentral/js/rounded_corner.js\"></script>");
			writer.println("<script type=\"text/javascript\">");

			writer.println("function getCookie(name) { // use: getCookie(\"name\");");
			writer.println("var bikky = document.cookie;");
			writer.println("var index = bikky.indexOf(name + \"=\");");
			writer.println("if (index == -1) return null;");
			writer.println("index = bikky.indexOf(\"=\", index) + 1; // first character");
			writer.println("var endstr = bikky.indexOf(\";\", index);");
			writer.println("if (endstr == -1) endstr = bikky.length; // last character");
			writer.println("return unescape(bikky.substring(index, endstr));}");

			writer.println("function run_scroller(w, h)");
			writer.println("{");

			writer.println("// Create the first scroller and position it.");
			writer.println("myScroller1.width = w;");
			writer.println("myScroller1.height = h;");
			writer.println("myScroller1.create();");

			writer.println("myScroller1.setzIndex(100);");
			writer.println("myScroller1.show();");

			writer.println("}");

			writer.println("function restart()");
			writer.println("{");
			writer.println("if(!myScroller1.reversed)");
			writer.println("{");
			writer.println("myScroller1.start();");

			writer.println("}");
			writer.println("else");
			writer.println("{");
			writer.println("myScroller1.reverse();");

			writer.println("}");
			writer.println("}");

			writer.println("</script>");

			writer.println("</head>");

			writer.println("<body>");

			writer.println("<div id=\"tempholder\"></div>");
			writer.println("<script type=\"text/javascript\">");
			writer.println("//SET SCROLLER APPEARANCE AND MESSAGES");
			writer.println("var myScroller1 = new Scroller(0, 0, parseInt($('#tempholder').width()), parseInt($('#tempholder').height()), 0, 10, false); //(xpos, ypos, width, height, border, padding, rounded)");
			writer.println("myScroller1.setColors(\"#333333\", \"#F8F8F1\", \"#F8F8F1\"); //(fgcolor, bgcolor, bdcolor)");
			writer.println("myScroller1.setFont(\"Tahoma\", 1);");

			writer.println("var cookie_val = getCookie(\"statuscentral_config\");");

			boolean isOffice = false;

			while (sys_iter.hasNext()) {
				system = (SchoolSystem) sys_iter.next();
				writer.println("if((cookie_val==null) || cookie_val == \"\" || cookie_val == \"|\"){");
				writer.println("myScroller1.addItem(\"<span class='weatherCentralSchoolSystem'>"
						+ system.getSchoolSystemName().replaceAll("'", "&#39;") + "</span>\");");
				writer.println("}else if(cookie_val.indexOf(\"|SYS-" + system.getSchoolSystemID() + "|\") >= 0){");
				writer.println("myScroller1.addItem(\"<span class='weatherCentralSchoolSystem'>"
						+ system.getSchoolSystemName().replaceAll("'", "&#39;") + "</span>\");}");

				sch_iter = null;
				sch_iter = system.getSchoolSystemSchools().iterator();
				while ((sch_iter != null) && sch_iter.hasNext()) {
					school = (School) sch_iter.next();
					stat = school.getSchoolClosureStatus();

					isOffice = (school.getSchoolName().endsWith("Office") || (school.getSchoolID() == 220));

					writer.println("if((cookie_val==null) || cookie_val == \"\" || cookie_val == \"|\"){");
					if ((stat.getSchoolClosureNote() != null) && !stat.getSchoolClosureNote().equalsIgnoreCase("")) {
						writer.println("myScroller1.addItem('<div id=\"status_"
								+ school.getSchoolID()
								+ "\" class=\"floater_off\" onmouseover=\"this.className=\\'floater_on\\'; myScroller1.stop();\" onmouseout=\"this.className=\\'floater_off\\'; restart();\"><span class=\"normalGrey10pxText\">"
								+ school.getSchoolName().replaceAll("'", "&#39;")
								+ "</span><BR><span class=\""
								+ cssClass(stat.getClosureStatusID())
								+ "\">"
								+ (!isOffice ? stat.getClosureStatusDescription() : stat.getClosureStatusDescription().replaceAll(
										"School", "Office")) + "</span><BR><span style=\"font-size:10px;color:#333333;\"><b>Note</b>: "
								+ encodeHTML(stat.getSchoolClosureNote()) + "</span></div>');");
					}
					else {
						writer.println("myScroller1.addItem('<div id=\"status_"
								+ school.getSchoolID()
								+ "\" class=\"floater_off\" onmouseover=\"this.className=\\'floater_on\\'; myScroller1.stop();\" onmouseout=\"this.className=\\'floater_off\\'; restart();\"><span class=\"normalGrey10pxText\">"
								+ school.getSchoolName().replaceAll("'", "&#39;")
								+ "</span><BR><span class=\""
								+ cssClass(stat.getClosureStatusID())
								+ "\">"
								+ (!isOffice ? stat.getClosureStatusDescription() : stat.getClosureStatusDescription().replaceAll(
										"School", "Office")) + "</span></div>');");
					}
					writer.println("}else if(cookie_val.indexOf(\"|" + school.getSchoolID() + "|\") >= 0){");
					if ((stat.getSchoolClosureNote() != null) && !stat.getSchoolClosureNote().equalsIgnoreCase("")) {
						writer.println("myScroller1.addItem('<div id=\"status_"
								+ school.getSchoolID()
								+ "\" class=\"floater_off\" onmouseover=\"this.className=\\'floater_on\\'; myScroller1.stop();\" onmouseout=\"this.className=\\'floater_off\\'; restart();\"><span class=\"normalGrey10pxText\">"
								+ school.getSchoolName().replaceAll("'", "&#39;")
								+ "</span><BR><span class=\""
								+ cssClass(stat.getClosureStatusID())
								+ "\">"
								+ (!isOffice ? stat.getClosureStatusDescription() : stat.getClosureStatusDescription().replaceAll(
										"School", "Office")) + "</span><BR><span style=\"font-size:10px;color:#333333;\"><b>Note</b>: "
								+ encodeHTML(stat.getSchoolClosureNote()) + "</span></div>');}");
					}
					else {
						writer.println("myScroller1.addItem('<div id=\"status_"
								+ school.getSchoolID()
								+ "\" class=\"floater_off\" onmouseover=\"this.className=\\'floater_on\\'; myScroller1.stop();\" onmouseout=\"this.className=\\'floater_off\\'; restart();\"><span class=\"normalGrey10pxText\">"
								+ school.getSchoolName().replaceAll("'", "&#39;")
								+ "</span><BR><span class=\""
								+ cssClass(stat.getClosureStatusID())
								+ "\">"
								+ (!isOffice ? stat.getClosureStatusDescription() : stat.getClosureStatusDescription().replaceAll(
										"School", "Office")) + "</span></div>');}");
					}
				}
			}
			System.gc();

			writer.println("//SET SCROLLER PAUSE");
			writer.println("myScroller1.setPause(1000); //set pause beteen msgs, in milliseconds");
			writer.println("</script>");

			writer.println("</body>");
			writer.println("</html>");
			writer.flush();
			writer.close();

			customize_view_real = new File(nlesd_rootbasepath + "statuscentral/status_central_customize_view.html");
			if (customize_view_real.exists()) {
				customize_view_real.delete();
				System.err.println("<<<<<< NLESD EXISTING CUSTOMIZE VIEW FILE DELETED >>>>>>");
			}
			customize_view_tmp.renameTo(customize_view_real);
			ss.clear();
			System.err.println("<<<<<< NLESD CUSTOMIZE VIEW FILE REGENERATED >>>>>>");
			System.err.flush();
			System.gc();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	public void CBC_writeAllViewFile() {

		File all_view_tmp = null, all_view_real = null;
		PrintWriter writer = null;
		Vector<SchoolSystem> ss = null;
		Iterator<SchoolSystem> sys_iter = null;
		Iterator<School> sch_iter = null;
		SchoolSystem sys = null;
		School school = null;
		ClosureStatus stat = null;
		boolean isOffice = false;

		try {
			synchronized (this.systems) {
				ss = new Vector<SchoolSystem>(systems);
			}

			all_view_tmp = new File(nlesd_rootbasepath + "statuscentral/cbc_status_central_all_view.tmp");

			if (!all_view_tmp.getParentFile().exists())
				all_view_tmp.getParentFile().mkdirs();

			if (all_view_tmp.exists()) {
				System.err.println("<<<<< TEMP FILE ALREADY EXIST >>>>>");

				if (all_view_tmp.delete()) {
					System.err.println("<<<<< TEMP FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< TEMP FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(all_view_tmp), true);

			sys_iter = ss.iterator();
			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>Newfoundland &amp; Labrador English School District - School Status Central</title>");
			writer.println("<link rel='stylesheet' href='//www.cbc.ca/nl/stormcentre/css/esdnl.css'>");
			writer.println(this.getGoogleAnalytics());
			writer.println("</head>");
			writer.println("<body>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0' style='border:1px solid #C1CDD8;'>");
			writer.println("<tr id='bodyContainer'>");
			writer.println("<td width='100%' align='left' valign='top'>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
			writer.println("<tr>");
			writer.println("<td width='100%' align='left' valign='top'>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
			writer.println("<tr>");
			writer.println("<td width='100%' align='left' valign='top'>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
			while (sys_iter.hasNext()) {
				sys = (SchoolSystem) sys_iter.next();

				writer.println("<tr height='25' style='background-color: #DBEAF5;'>");
				writer.println("<td colspan='2' align='left' valign='middle'>");
				writer.println("&nbsp;<span class='weatherCentralSchoolSystem'>" + sys.getSchoolSystemName() + "</span>");
				writer.println("</td>");
				writer.println("</tr>");
				writer.println("<tr>");
				writer.println("<td width='100%' height='1' align='left' valign='middle' colspan='4' style='background-color: #C1CDD8;'>");
				writer.println("<img src='http://www.nlesd.ca/images/spacer.gif' width='1' height='1'><BR>");
				writer.println("</td>");
				writer.println("</tr>");

				sch_iter = null;
				sch_iter = sys.getSchoolSystemSchools().iterator();
				while ((sch_iter != null) && sch_iter.hasNext()) {
					school = (School) sch_iter.next();
					stat = school.getSchoolClosureStatus();

					isOffice = (school.getSchoolName().endsWith("Office") || (school.getSchoolID() == 220));

					writer.println("<tr>");
					writer.println("<td width='50%' align='left' valign='top'>");
					writer.println("&nbsp;<span class='normalGrey10pxText'>" + school.getSchoolName() + "</span>");
					writer.println("</td>");
					writer.println("<td width='*' align='left' valign='top'>");

					writer.print("<span class='" + cssClass(stat.getClosureStatusID()) + "'>");

					writer.print(!isOffice ? stat.getClosureStatusDescription() : stat.getClosureStatusDescription().replaceAll(
							"School", "Office"));
					writer.println("</span>");
					if (stat.getSchoolClosureNote() != null) {
						writer.print("<BR><span style='font-size:11px;color:#333333;'><b>Note</b>:&nbsp;"
								+ stat.getSchoolClosureNote() + "</span>");
					}
					writer.println("</td>");
					writer.println("</tr>");
				}
			}

			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</body>");
			writer.println("</html>");
			writer.flush();
			writer.close();

			all_view_real = new File(nlesd_rootbasepath + "statuscentral/cbc_status_central_all_view.html");

			if (all_view_real.exists()) {
				all_view_real.delete();
				System.err.println("<<<<<< EXISTING CBC VIEW FILE DELETED >>>>>>");
			}
			all_view_tmp.renameTo(all_view_real);
			ss.clear();
			System.err.println("<<<<<< CBC VIEW FILE REGENERATED >>>>>>");
			System.err.flush();
			System.gc();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	public static String cssClass(int id) {

		String css;

		switch (id) {
		case 4:
		case 7:
		case 10:
		case 11:
		case 21:
		case 22:
		case 62:
		case 82:
		case 83:
		case 84:
		case 102:
		case 123:
			css = "weatherCentralStatusClosed";
			break;
		case 6:
		case 5:
		case 42:
		case 63:
			css = "weatherCentralStatusDelayed";
			break;
		case 9:
			css = "weatherCentralStatusOpen";
			break;
		default:
			css = "weatherCentralStatusOpen";
		}

		return css;
	}

	private String encodeHTML(String raw) {

		return raw.replaceAll(new String(new char[] {
			(char) 39
		}), "&#39;") // single quote
		.replaceAll(new String(new char[] {
			(char) 44
		}), "&#44;") // comma
		.replaceAll(new String(new char[] {
			(char) 10
		}), "") // line feed
		.replaceAll(new String(new char[] {
			(char) 13
		}), ""); // cartiage return;
	}

	private String getGoogleAnalytics() {

		StringBuffer buf = new StringBuffer();

		buf.append("<script type='text/javascript'>");
		buf.append("var _gaq = _gaq || [];");
		buf.append("_gaq.push(['_setAccount', 'UA-29467925-1']);");
		buf.append("_gaq.push(['_setDomainName', 'esdnl.ca']);");
		buf.append("_gaq.push(['_trackPageview']);");

		buf.append("(function() {");
		buf.append(" var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;");
		buf.append("ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';");
		buf.append("var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);");
		buf.append("})();");
		buf.append("</script>");

		return buf.toString();
	}
}