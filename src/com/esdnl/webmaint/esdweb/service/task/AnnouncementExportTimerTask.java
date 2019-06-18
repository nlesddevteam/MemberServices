package com.esdnl.webmaint.esdweb.service.task;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.TimerTask;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.util.StringUtils;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.AnnouncementSettingsBean;
import com.esdnl.webmaint.esdweb.constants.AnnouncementTypeConstant;
import com.esdnl.webmaint.esdweb.dao.AnnouncementManager;
import com.esdnl.webmaint.esdweb.dao.AnnouncementSettingsManager;

public class AnnouncementExportTimerTask extends TimerTask {

	//private String esdnl_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../ROOT/";
	//private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH
	//		+ "/../../../webapps/nlesdweb/ROOT/announcements/";

	private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../ROOT/";

	public AnnouncementExportTimerTask() {

		super();
		System.err.println("<<<<<< ANNOUNCEMENT EXPORT TIMER STARTED >>>>>");
	}

	public void run() {

		try {
			//exportAnnouncementsESDNL();
			//exportBulletinBoardESDNL();

			//exports all announcement types.
			exportAnnouncementsNLESD();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);

			try {
				(new AlertBean(e)).send();
			}
			catch (EmailException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	private void exportAnnouncementsESDNL() {

		File announcement_tmp = null, announcement_real = null;
		PrintWriter writer = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");

		try {

			AnnouncementBean[] msgs = AnnouncementManager.getFrontPageAnnouncementBeans(AnnouncementTypeConstant.ANNOUNCEMENT.getTypeID());

			announcement_tmp = new File(esdnl_rootbasepath + "announcements_"
					+ AnnouncementTypeConstant.ANNOUNCEMENT.getTypeID() + ".tmp");

			if (announcement_tmp.exists()) {
				System.err.println("<<<<< " + announcement_tmp.getName() + " FILE ALREADY EXIST >>>>>");

				if (announcement_tmp.delete()) {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(announcement_tmp), true);

			for (int i = 0; ((msgs != null) && (i < msgs.length)); i++) {

				writer.println("<table width='405'><tr valign='top'><td>");
				writer.println("<h4 class='newsheader'>" + msgs[i].getHeader() + "</h4>");
				writer.println("<div align='left' class='mainbody'>");
				writer.println("<div style='font-size: 11px; color: #999999;text-align:left;'>" + sdf.format(msgs[i].getDate())
						+ "</div><br>");
				//Does Message have image?
				if (!StringUtils.isEmpty(msgs[i].getImage())) {
					//IMAGES Align table to right of page
					writer.println("<table width='200' align='right'>");
					//out.println("<tr valign='top'><td><div align='center'><img src='/images/" + msgs[i].getImage() + "' align='center' border='0' alt='' width='200'></div></td></tr>");
					writer.println("<tr valign='top'><td style='border: 1px solid Silver; padding: 3px 3px 3px 3px;'><div align='center'><img src='/images/"
							+ msgs[i].getImage() + "' align='center' border='0' alt='' width='200'></div></td></tr>");

					//close the table
					writer.println("</table>");
				}
				writer.println(msgs[i].getBody().replaceAll("\"", "&quot;").replaceAll("\n", "<BR>").replaceAll("<a",
						"<a class='menu'"));
				// Just in case to limit the amount of characters displayed in story
				//out.print(msgs[i].getBody().substring(0,155));

				if (!StringUtils.isEmpty(msgs[i].getImage())) {
					if (!StringUtils.isEmpty(msgs[i].getImageCaption())) {
						writer.println("<p><a href=\"/news/fullstory.jsp?NewsTitle="
								+ msgs[i].getHeader()
								+ "&NewsImage="
								+ msgs[i].getImage()
								+ "&NewsCaption="
								+ msgs[i].getImageCaption()
								+ "&NewsBody="
								+ msgs[i].getBody().replaceAll("\n", "<BR>")
								+ "\" onclick=\"NewWindow(this.href,'mywin','640','450','yes','center');return false\" onfocus=\"this.blur()\" class=\"menu\"><img src='/includes/images/fullstory-off.gif' alt='Open Full Story Page' name='fp2' id='fp2' width='102' height='27' border='0'></a>");
					}
					else {
						writer.println("<p><a href=\"/news/fullstory.jsp?NewsTitle="
								+ msgs[i].getHeader()
								+ "&NewsImage="
								+ msgs[i].getImage()
								+ "&NewsBody="
								+ msgs[i].getBody().replaceAll("\n", "<BR>")
								+ "\" onclick=\"NewWindow(this.href,'mywin','640','450','yes','center');return false\" onfocus=\"this.blur()\" class=\"menu\"><img src='/includes/images/fullstory-off.gif' alt='Open Full Story Page' name='fp2' id='fp2' width='102' height='27' border='0'></a>");

					}
				}
				else {
					writer.println("<p>");
				}

				if (!StringUtils.isEmpty(msgs[i].getFullStoryLink()))
					writer.println("<a href='/pdf/"
							+ msgs[i].getFullStoryLink()
							+ "' class='menu'><img src='/includes/images/seeattachment-off.gif' alt='See Attachment' name='fp1' id='fp1' width='102' height='27' border='0'></a>&nbsp;");

				writer.println("</div>");

				writer.println("</td></tr><tr><td colspan=2>");

				if (i < (msgs.length - 1)) {
					writer.println("<br><img src='/includes/images/redbar.gif' alt='' width='405' height='1' border='0'><p>");
				}
				writer.println("</td></tr></table>");
			}

			writer.flush();
			writer.close();

			announcement_real = new File(esdnl_rootbasepath + "announcements_"
					+ AnnouncementTypeConstant.ANNOUNCEMENT.getTypeID() + ".html");
			if (announcement_real.exists()) {
				announcement_real.delete();
				System.err.println("<<<<<< EXISTING " + announcement_real.getName() + " FILE DELETED >>>>>>");
			}
			announcement_tmp.renameTo(announcement_real);

		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	private void exportBulletinBoardESDNL() {

		File announcement_tmp = null, announcement_real = null;
		PrintWriter writer = null;
		SimpleDateFormat sdfbb = new SimpleDateFormat("MM/d/y");

		try {

			AnnouncementBean[] bboard = AnnouncementManager.getCurrentAnnouncementBeans(AnnouncementTypeConstant.BULLETIN_BOARD.getTypeID());

			announcement_tmp = new File(esdnl_rootbasepath + "announcements_"
					+ AnnouncementTypeConstant.BULLETIN_BOARD.getTypeID() + ".tmp");

			if (announcement_tmp.exists()) {
				System.err.println("<<<<< " + announcement_tmp.getName() + " FILE ALREADY EXIST >>>>>");

				if (announcement_tmp.delete()) {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(announcement_tmp), true);

			if (bboard.length < 1) {
				writer.println("<p><img src='includes/images/bluebar.jpg' alt='' width='170' height='1' border='0'><p>");
			}
			else {
				writer.println("<p><table width='160' border='0' cellspacing='0' cellpadding='0' align='center' style='background: #FFFFFF;'><tr><td><img src='includes/images/bbtop-y.png' alt='' width='160' height='30' border='0'></td></tr><tr><td style='background-image: url(/includes/images/bbback-y.png); background-repeat: repeat-y;'><table width='145' border='0' cellspacing='2' cellpadding='2' align='center'><tr><td><div align='center' style='font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; font-size: 9px; color: #00407A;'>");

				for (int i = 0; ((bboard != null) && (i < bboard.length)); i++) {

					//Does Message have image?
					if (!StringUtils.isEmpty(bboard[i].getImage())) {
						writer.println("<div align='center'><img src='/images/" + bboard[i].getImage()
								+ "' align='center' border='0' alt=''></div>");
					}
					writer.println("<b style='font-size: 11px;'>" + bboard[i].getHeader() + "</b>");
					writer.println("<br><span style='color: Silver;'>Posted: " + sdfbb.format(bboard[i].getDate())
							+ "</span><br>");
					writer.println(bboard[i].getBody().replaceAll("\"", "&quot;").replaceAll("\n", "<BR>").replaceAll("<a",
							"<a class='menu'"));
					if (!StringUtils.isEmpty(bboard[i].getFullStoryLink()))
						writer.println(" (<a href='/pdf/" + bboard[i].getFullStoryLink() + "' class='menu' >More details...</a>)");
					if (i < (bboard.length - 1)) {
						writer.println("<br><img src='/includes/images/bluebar.jpg' alt='' width='100' height='1' vspace='10' border='0'><br>");
					}
				}

				writer.println("</div></td></tr></table></td></tr><tr><td><img src='includes/images/bbbot-y.png' alt='' width='160' height='7' border='0'></td></tr></table><p><img src='includes/images/bluebar.jpg' alt='' width='170' height='1' border='0'><p>");
			}

			writer.flush();
			writer.close();

			announcement_real = new File(esdnl_rootbasepath + "announcements_"
					+ AnnouncementTypeConstant.BULLETIN_BOARD.getTypeID() + ".html");
			if (announcement_real.exists()) {
				announcement_real.delete();
				System.err.println("<<<<<< EXISTING " + announcement_real.getName() + " FILE DELETED >>>>>>");
			}
			announcement_tmp.renameTo(announcement_real);

		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}
	
	*/

	private void exportAnnouncementsNLESD() {

		File announcement_tmp = null, announcement_real = null;
		PrintWriter writer = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
		Collection<AnnouncementBean> msgs = null;
		AnnouncementSettingsBean settings = null;

		try {

			this.exportLastestAnnouncementsNLESD();

			for (AnnouncementTypeConstant type : AnnouncementTypeConstant.ALL) {

				if (type.equals(AnnouncementTypeConstant.BULLETIN_BOARD)) {
					this.exportBulletinBoardNLESD();

					continue;
				}

				settings = AnnouncementSettingsManager.getAnnouncementSettingsBean(type);

				if (settings != null && settings.getExportLimit() > 0) {
					msgs = AnnouncementManager.getCurrentAnnouncementBeans(type.getTypeID(), settings.getExportLimit());
				}
				else {
					msgs = AnnouncementManager.getCurrentAnnouncementBeans(type.getTypeID());
				}

				announcement_tmp = new File(nlesd_rootbasepath + "announcements/announcements_" + type.getTypeID() + ".tmp");

				if (announcement_tmp.exists()) {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE ALREADY EXIST >>>>>");

					if (announcement_tmp.delete()) {
						System.err.println("<<<<< " + announcement_tmp.getName() + " FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + announcement_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
					}
				}

				if (!announcement_tmp.getParentFile().exists()) {
					announcement_tmp.getParentFile().mkdirs();
				}

				writer = new PrintWriter(new FileWriter(announcement_tmp), true);

				if (msgs.size() > 0) {
					for (AnnouncementBean msg : msgs) {
						writer.println("<div class='announcement-item'>");
						writer.println("<div class='item-header'>" + msg.getHeader() + "</div>");
						writer.println("<div class='item-date'>" + sdf.format(msg.getDate()) + "</div>");
						writer.println("<div class='item-body'>");
						//Does Message have image?
						if (!StringUtils.isEmpty(msg.getImage())) {
							writer.println("<img class='item-image' src='/announcements/img/" + msg.getImage() + "' />");
						}
						writer.println(msg.getBody().replaceAll("\"", "&quot;").replaceAll("\n", "<BR />"));

						writer.println("<br /><br />");

						if (!StringUtils.isEmpty(msg.getImage())) {
							if (!StringUtils.isEmpty(msg.getImageCaption())) {
								writer.println("<a href=\"/announcements/fullstory.jsp?NewsTitle="
										+ msg.getHeader()
										+ "&NewsImage="
										+ msg.getImage()
										+ "&NewsCaption="
										+ msg.getImageCaption()
										+ "&NewsBody="
										+ msg.getBody().replaceAll("\n", "<BR />")
										+ "\"><img src='/announcements/img/viewfullstory-off.png' class='img-swap' alt='Open Full Story Page' border='0'></a>");
							}
							else {
								writer.println("<a href=\"/announcements/fullstory.jsp?NewsTitle="
										+ msg.getHeader()
										+ "&NewsImage="
										+ msg.getImage()
										+ "&NewsBody="
										+ msg.getBody().replaceAll("\n", "<BR />")
										+ "\"><img src='/announcements/img/viewfullstory-off.png' class='img-swap' alt='Open Full Story Page' border='0'></a>");
							}
							writer.println("<br />");
						}

						if (!StringUtils.isEmpty(msg.getFullStoryLink())) {
							writer.println("<a href='/announcements/doc/"
									+ msg.getFullStoryLink()
									+ "'><img src='/announcements/img/viewattachment-off.png' class='img-swap' alt='See Attachment' border='0'></a>&nbsp;<br/>");
						}

						writer.println("</div>");

						writer.println("</div>");
					}
				}
				else {
					writer.println("<div class='announcement-item'><div class='item-body'>There are no " + type.getDescription()
							+ " items to display at this time. Please check back again at a later time. </div></div>");
				}

				writer.flush();
				writer.close();

				announcement_real = new File(nlesd_rootbasepath + "announcements/announcements_" + type.getTypeID() + ".html");
				if (announcement_real.exists()) {
					announcement_real.delete();
					System.err.println("<<<<<< EXISTING " + announcement_real.getName() + " FILE DELETED >>>>>>");
				}
				announcement_tmp.renameTo(announcement_real);
			}

		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	private void exportBulletinBoardNLESD() {

		File announcement_tmp = null, announcement_real = null;
		PrintWriter writer = null;
		SimpleDateFormat sdfbb = new SimpleDateFormat("MM/d/y");

		try {

			Collection<AnnouncementBean> bboard = AnnouncementManager.getCurrentAnnouncementBeans(AnnouncementTypeConstant.BULLETIN_BOARD.getTypeID());

			announcement_tmp = new File(nlesd_rootbasepath + "announcements/announcements_"
					+ AnnouncementTypeConstant.BULLETIN_BOARD.getTypeID() + ".tmp");

			if (announcement_tmp.exists()) {
				System.err.println("<<<<< " + announcement_tmp.getName() + " FILE ALREADY EXIST >>>>>");

				if (announcement_tmp.delete()) {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
				}
			}

			if (!announcement_tmp.getParentFile().exists()) {
				announcement_tmp.getParentFile().mkdirs();
			}

			writer = new PrintWriter(new FileWriter(announcement_tmp), true);

			if (bboard.size() > 0) {
				int i = 0;
				for (AnnouncementBean ab : bboard) {

					writer.println("<div class='bulletin-board-item'>");
					//Does Message have image?
					if (!StringUtils.isEmpty(ab.getImage())) {
						writer.println("\t<div align='center' class='item-image'><img src='/announcements/img/" + ab.getImage()
								+ "' align='center' border='0' alt=''/></div>");
					}
					writer.println("\t<div class='item-header'>" + ab.getHeader() + "</div>");
					writer.println("\t<div class='item-date'>Posted: " + sdfbb.format(ab.getDate()) + "</div>");
					writer.println("\t<div class='item-body'>\n"
							+ ab.getBody().replaceAll("\"", "&quot;").replaceAll("\n", "<BR>"));
					if (!StringUtils.isEmpty(ab.getFullStoryLink()))
						writer.println(" (<a href='/announcements/doc/" + ab.getFullStoryLink() + "'>More details...</a>)");
					writer.println("\t</div>");
					writer.println("</div>");
					if (i++ < (bboard.size() - 1)) {
						writer.println("<div class='item-separator'><img src='/includes/images/bluebar.jpg' alt='' width='100' height='1' vspace='10' border='0'></div>");
					}
				}
			}

			writer.flush();
			writer.close();

			announcement_real = new File(nlesd_rootbasepath + "announcements/announcements_"
					+ AnnouncementTypeConstant.BULLETIN_BOARD.getTypeID() + ".html");
			if (announcement_real.exists()) {
				announcement_real.delete();
				System.err.println("<<<<<< EXISTING " + announcement_real.getName() + " FILE DELETED >>>>>>");
			}
			announcement_tmp.renameTo(announcement_real);

		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	private void exportLastestAnnouncementsNLESD() {

		File announcement_tmp = null, announcement_real = null;
		PrintWriter writer = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
		Collection<AnnouncementBean> msgs = null;
		AnnouncementSettingsBean settings = null;

		try {

			settings = AnnouncementSettingsManager.getAnnouncementSettingsBean(AnnouncementTypeConstant.LATEST_ANNOUNCEMENTS);

			if (settings != null && settings.getExportLimit() > 0) {
				msgs = AnnouncementManager.getLatestAnnouncementBeans(settings.getExportLimit());
			}
			else {
				msgs = AnnouncementManager.getLatestAnnouncementBeans(3);
			}

			announcement_tmp = new File(nlesd_rootbasepath + "announcements/announcements_LATEST.tmp");

			if (announcement_tmp.exists()) {
				System.err.println("<<<<< " + announcement_tmp.getName() + " FILE ALREADY EXIST >>>>>");

				if (announcement_tmp.delete()) {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< " + announcement_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
				}
			}

			if (!announcement_tmp.getParentFile().exists()) {
				announcement_tmp.getParentFile().mkdirs();
			}

			writer = new PrintWriter(new FileWriter(announcement_tmp), true);

			for (AnnouncementBean msg : msgs) {
				writer.println("<div class='announcement-item'>");
				writer.println("<div class='item-header'>" + msg.getHeader() + "</div>");
				writer.println("<div class='item-date'>" + sdf.format(msg.getDate()) + "</div>");
				writer.println("<div class='item-body'>");
				//Does Message have image?
				if (!StringUtils.isEmpty(msg.getImage())) {
					writer.println("<img class='item-image' src='/announcements/img/" + msg.getImage() + "' />");
				}
				writer.println(msg.getBody().replaceAll("\"", "&quot;").replaceAll("\n", "<BR />"));

				writer.println("<br /><br />");

				if (!StringUtils.isEmpty(msg.getImage())) {
					if (!StringUtils.isEmpty(msg.getImageCaption())) {
						writer.println("<a href=\"/announcements/fullstory.jsp?NewsTitle="
								+ msg.getHeader()
								+ "&NewsImage="
								+ msg.getImage()
								+ "&NewsCaption="
								+ msg.getImageCaption()
								+ "&NewsBody="
								+ msg.getBody().replaceAll("\n", "<BR />")
								+ "\"><img src='/announcements/img/viewfullstory-off.png' alt='Open Full Story Page' class='img-swap' border='0'></a>");
					}
					else {
						writer.println("<a href=\"/announcements/fullstory.jsp?NewsTitle="
								+ msg.getHeader()
								+ "&NewsImage="
								+ msg.getImage()
								+ "&NewsBody="
								+ msg.getBody().replaceAll("\n", "<BR />")
								+ "\"><img src='/announcements/img/viewfullstory-off.png' alt='Open Full Story Page' class='img-swap' border='0'></a>");

					}
				}

				if (!StringUtils.isEmpty(msg.getFullStoryLink())) {
					writer.println("<a href='/announcements/doc/"
							+ msg.getFullStoryLink()
							+ "'><img src='/announcements/img/viewattachment-off.png' alt='See Attachment' class='img-swap' border='0'></a><br />");
				}

				writer.println("</div>");

				writer.println("</div>");
			}

			writer.flush();
			writer.close();

			announcement_real = new File(nlesd_rootbasepath + "announcements/announcements_LATEST.html");
			if (announcement_real.exists()) {
				announcement_real.delete();
				System.err.println("<<<<<< EXISTING " + announcement_real.getName() + " FILE DELETED >>>>>>");
			}
			announcement_tmp.renameTo(announcement_real);
			System.err.println("<<<<<< EXISTING " + announcement_real.getName() + " FILE DELETED >>>>>>");

		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

}