package com.esdnl.webupdatesystem.newspostings.service.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Vector;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.util.StringUtils;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingFileBean;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsBean;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsException;
import com.esdnl.webupdatesystem.newspostings.constants.NewsCategory;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsManager;

public class NewPostingsExportTimerTask extends TimerTask {

	//FOR LIVE SERVER
	private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../nlesdweb/WebContent/";

	//FOR LOCAL HOST ONLY   
	//private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../wtpwebapps/NLESDWEB/";	

	public NewPostingsExportTimerTask() {

		super();
		System.err.println("<<<<<< NEWSPOSTINGS EXPORT TIMER STARTED >>>>>");
	}

	public void run() {

		try {
			//exports all announcement types.
			exportNewsPostingsNLESD();
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

	private void exportNewsPostingsNLESD() {

		File np_tmp = null, np_real = null;
		PrintWriter writer = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
		try {
			//retrieve the limits
			TreeMap<Integer, Integer> npsettings = NewsPostingsManager.getNewsPostingsSettings();
			for (NewsCategory type : NewsCategory.ALL) {
				Vector<NewsPostingsBean> postings = null;
				try {
					postings = NewsPostingsManager.getNewsPostingsByCat(type.getValue(),
							npsettings.get(type.getValue()).intValue(), 1);
				}
				catch (NewsPostingsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				np_tmp = new File(nlesd_rootbasepath + "newspostings/newspostings_" + type.getValue() + ".tmp");
				if (np_tmp.exists()) {
					if (np_tmp.delete()) {
						System.err.println("<<<<< " + np_tmp.getName() + " FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + np_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
					}
				}
				if (!np_tmp.getParentFile().exists()) {
					np_tmp.getParentFile().mkdirs();
				}
				try {
					writer = new PrintWriter(new FileWriter(np_tmp), true);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int counter = 1;
				if (postings.size() > 0) {
					for (NewsPostingsBean np : postings) {

						if (counter == 1) {

							if (type.getValue() == 5) {
								//Write format for Important Notice	(Type 5)
								writer.println("<div class='importantNoticeBox siteBodyText'>");
								writer.println("<div class='importantNoticeHeader siteSubHeaders'>IMPORTANT	NOTICE</div>");
								writer.println("<div class='importantNoticeTitle siteSubHeaders'>" + np.getNewsTitle() + "</div>");
								writer.println(
										"<div class='importantNoticeDate siteFootNotes'>" + sdf.format(np.getNewsDate()) + "</div>");
								if (np.getNewsLocation() != null) {
									if (!(StringUtils.isEmpty(np.getNewsLocation().getLocationDescription()))) {
										writer.println("<div class='importantNoticeLocation siteFootNotes'>"
												+ np.getNewsLocation().getLocationDescription() + "</div>");
									}
								}
								writer.println("<div class='newsFPBody siteBodyText'>");
								if (!(StringUtils.isEmpty(np.getNewsPhoto()))) {
									writer.println("<img src='/includes/files/news/img/" + np.getNewsPhoto()
											+ "' class='newsFPIcon' border='0' title='" + np.getNewsPhotoCaption() + "'>");
								}
								writer.println(np.getNewsDescription());
								writer.println("</div>");
								if (!(StringUtils.isEmpty(np.getNewsDocumentation()))) {
									writer.println(
											"<a href='/includes/files/news/doc/" + np.getNewsDocumentation() + "' title='View Attachment'>");
									writer.println(
											"<img src='/includes/img/mini-icons/attachment-off.png' alt='View Attachment' border=0>");
									writer.println("</a>");
								}
								if (!(StringUtils.isEmpty(np.getNewsExternalLinkTitle()))) {
									writer.println("<a href='" + np.getNewsExternalLink() + "' title='View External Link"
											+ np.getNewsExternalLinkTitle() + "'>");
									writer.println("<img src='/includes/img/mini-icons/weblink-off.png' border=0 alt='"
											+ np.getNewsExternalLinkTitle() + "'>");
									writer.println("</a>");
								}
								for (NewsPostingFileBean npfb : np.getOtherNewsFiles()) {
									writer.println(
											"<a href='/includes/files/news/doc/" + npfb.getNfDoc() + "' title='" + npfb.getNfTitle() + "'>");
									if (npfb.getNfTitle().toLowerCase().contains("form")) {
										writer.println(
												"<img src='/includes/img/mini-icons/forms-off.png' alt='" + npfb.getNfTitle() + "' border=0>");
									}
									else if (npfb.getNfTitle().toLowerCase().contains("presentation")) {
										writer.println("<img src='/includes/img/mini-icons/presentation-off.png' alt='" + npfb.getNfTitle()
												+ "' border=0>");
									}
									else {
										writer.println(
												"<img src='/includes/img/mini-icons/doc-off.png' alt='" + npfb.getNfTitle() + "' border=0>");
									}
									writer.println("</a>");
								}

								writer.println("</div>");

							}
							else {
								//Write Format for other News Postings (Type 1,2,3,4,6)

								writer.println("<div class='newsFPDate siteFootNotes'>" + sdf.format(np.getNewsDate()) + "</div>");
								writer.println("<div class='newsFPTitle siteSubHeaders' style='font-weight: bold;'>" + np.getNewsTitle()
										+ "</div>");
								if (np.getNewsLocation() != null) {
									if (!(StringUtils.isEmpty(np.getNewsLocation().getLocationDescription()))) {
										writer.println("<div class='newsFPLocation siteFootNotes'>"
												+ np.getNewsLocation().getLocationDescription() + "</div>");
									}
								}
								writer.println("<div class='newsFPBody siteBodyText'>");
								if (!(StringUtils.isEmpty(np.getNewsPhoto()))) {
									writer.println("<img src='/includes/files/news/img/" + np.getNewsPhoto()
											+ "' class='newsFPIcon' border='0' title='" + np.getNewsPhotoCaption() + "'>");
								}
								writer.println(np.getNewsDescription().substring(0, 274));
								if (np.getNewsDescription().length() > 274) {
									writer.println("...[<a href='/about/news/index.jsp?story=" + np.getNewsTitle() + "&NewsCategory="
											+ type.getValue() + "' class='newsMenu'>more</a>]");
								}
								writer.println("</div>");
								writer.println("<a href='/about/news/index.jsp?story=" + np.getNewsTitle() + "&NewsCategory="
										+ type.getValue() + "' title='Read Full Story'>");
								writer.println(
										"<img src='/includes/img/mini-icons/fullstory-off.png' border=0 alt='Read Full Story'></a>");
								if (!(StringUtils.isEmpty(np.getNewsDocumentation()))) {
									writer.println(
											"<a href='/includes/files/news/doc/" + np.getNewsDocumentation() + "' title='View Attachment'>");
									writer.println(
											"<img src='/includes/img/mini-icons/attachment-off.png' alt='View Attachment' border=0>");
									writer.println("</a>");
								}
								if (!(StringUtils.isEmpty(np.getNewsExternalLinkTitle()))) {
									writer.println("<a href='" + np.getNewsExternalLink() + "' title='View External Link"
											+ np.getNewsExternalLinkTitle() + "'>");
									writer.println("<img src='/includes/img/mini-icons/weblink-off.png' border=0 alt='"
											+ np.getNewsExternalLinkTitle() + "'>");
									writer.println("</a>");
								}
								for (NewsPostingFileBean npfb : np.getOtherNewsFiles()) {
									writer.println(
											"<a href='/includes/files/news/doc/" + npfb.getNfDoc() + "' title='" + npfb.getNfTitle() + "'>");
									if (npfb.getNfTitle().toLowerCase().contains("form")) {
										writer.println(
												"<img src='/includes/img/mini-icons/forms-off.png' alt='" + npfb.getNfTitle() + "' border=0>");
									}
									else if (npfb.getNfTitle().toLowerCase().contains("presentation")) {
										writer.println("<img src='/includes/img/mini-icons/presentation-off.png' alt='" + npfb.getNfTitle()
												+ "' border=0>");
									}
									else {
										writer.println(
												"<img src='/includes/img/mini-icons/doc-off.png' alt='" + npfb.getNfTitle() + "' border=0>");
									}
									writer.println("</a>");
								}
								writer.println("<img src='/includes/img/bar-silver.png' width=100% height=1>");
							}
						}
						else {

							if (type.getValue() == 5) {
								//No other notices allowed, only one important notice
							}
							else {

								writer.println("<div class='newsFPDate siteFootNotes'>" + sdf.format(np.getNewsDate()) + "</div>");
								writer.println("<div class='newsFPTitle siteBodyText'>");
								writer.println("<a href=\"/about/news/index.jsp?story=" + np.getNewsTitle() + "&NewsCategory="
										+ type.getValue() + "\" class='newsMenu'>");
								writer.println(np.getNewsTitle() + "</a>");
								if (np.getNewsLocation() != null) {
									if (!(StringUtils.isEmpty(np.getNewsLocation().getLocationDescription()))) {
										writer.println("<div class='newsFPLocation siteFootNotes'>"
												+ np.getNewsLocation().getLocationDescription() + "</div>");
									}
								}
								writer.println("</div>");
								writer.println("<img src='/includes/img/bar-silver.png' width=100% height=1>");
							}
						}
						counter++;

					}
				}
				else {

					if (type.getValue() == 5) {
						//Make sure Important notice does not display unless there is a message.
						writer.println("<script>$('importantNoticeBox').css('display','none');</script>");

					}
					else {
						//Write infomation if nothing is found.
						writer.println("<div class='announcement-item'><div class='item-body'>There are no " + type.getDescription()
								+ " items to display at this time. Please check back again at a later time. </div></div>");
					}

				}
				writer.flush();
				writer.close();
				np_real = new File(nlesd_rootbasepath + "newspostings/newspostings_" + type.getValue() + ".html");
				if (np_real.exists()) {
					np_real.delete();
					System.err.println("<<<<<< EXISTING " + np_real.getName() + " FILE DELETED >>>>>>");
				}
				np_tmp.renameTo(np_real);
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();

		}

	}
}