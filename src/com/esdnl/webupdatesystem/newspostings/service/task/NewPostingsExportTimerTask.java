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
					//postings = NewsPostingsManager.getNewsPostingsByCat(type.getValue(),npsettings.get(type.getValue()).intValue(), 1);
					postings = NewsPostingsManager.getNewsPostingsByCat(type.getValue(),8, 1);
				}
				catch (NewsPostingsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				np_tmp = new File(nlesd_rootbasepath + "about/generated/newspostings_" + type.getValue() + ".tmp");
				if (np_tmp.exists()) {
					if (np_tmp.delete()) {
						System.err.println("<<<<< " + np_tmp.getName() + " NEWS FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + np_tmp.getName() + " NEWS FILE COULD NOT BE DELETED >>>>>");
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

				

//Write format for Important Notice	(Type 5)
							
				if (type.getValue() == 5 && counter == 1) {								
								writer.println("<div class=\"importantNoticeBox\" style='margin-bottom:3px;'>");
								writer.println("<div class=\"importantNoticeHeader\"><span class='blink-me' style='float:left;font-size:20px;padding:3px;'><i class='fas fa-exclamation-triangle'></i></span><span class='blink-me' style='float:right;font-size:20px;padding:3px;'><i class='fas fa-exclamation-triangle'></i></span>IMPORTANT NOTICE</div>");
								writer.println("<div class=\"importantNoticeTitle\"><i class=\"fas fa-exclamation-circle\"></i>&nbsp;" + np.getNewsTitle() + "&nbsp;<i class=\"fas fa-exclamation-circle\"></i></div>");
								writer.println("<div class=\"importantNoticeDate\">" + sdf.format(np.getNewsDate()) + "</div>");
								if (np.getNewsLocation() != null) {
									if (!(StringUtils.isEmpty(np.getNewsLocation().getLocationDescription()))) {
										writer.println("<div class=\"importantNoticeLocation\">" + np.getNewsLocation().getLocationDescription() + "</div>");
									}
								}
								writer.println("<div class=\"frontPageNewsItemBody\">");
								if (!(StringUtils.isEmpty(np.getNewsPhoto()))) {
									writer.println("<img src='/includes/files/news/img/" + np.getNewsPhoto() + "' class='frontPageNewsPhoto thumbnail' border='0' title='" + np.getNewsPhotoCaption() + "' />");
								}
								writer.println(np.getNewsDescription());
								writer.println("</div>");
								
								if (!(StringUtils.isEmpty(np.getNewsDocumentation()))) {
									writer.println("<a href='/includes/files/news/doc/" + np.getNewsDocumentation() + "' title='View Attachment' class='btn btn-xs btn-warning'>");
									writer.println("Attachment");
									writer.println("</a>");
								}
								if (!(StringUtils.isEmpty(np.getNewsExternalLinkTitle()))) {
									writer.println("<a href='" + np.getNewsExternalLink() + "' title='View External Link" + np.getNewsExternalLinkTitle() + "' class='btn btn-xs btn-warning'>");
									writer.println("External Link");
									writer.println("</a>");
								}
								for (NewsPostingFileBean npfb : np.getOtherNewsFiles()) {
									writer.println(
											"<a href='/includes/files/news/doc/" + npfb.getNfDoc() + "' title='" + npfb.getNfTitle() + "' class='btn btn-xs btn-primary'>");
									if (npfb.getNfTitle().toLowerCase().contains("form")) {
										writer.println("Form");
									}
									else if (npfb.getNfTitle().toLowerCase().contains("presentation")) {
										writer.println("Presentation");
									}
									else {
										writer.println("Document");
									}
									writer.println("</a>");
								}

								writer.println("</div>");

							} else {

//Write Format for other News Postings (Type 1,2,3,4,6)

if (counter < 4) {					
			writer.println("<div class='card bg-light frontPageNewsCard' style='margin-bottom:8px;'>");			
			writer.println("<div class='card-header frontPageNewsCardHeader'>");
			writer.println("<div class='frontPageNewsItemDate'>");
			writer.print(sdf.format(np.getNewsDate()));			
			
			if (np.getNewsLocation() != null) {
				if (!(StringUtils.isEmpty(np.getNewsLocation().getLocationDescription()))) {
					writer.println(" - " + np.getNewsLocation().getLocationDescription());
				}
			}
			
			writer.println("</div>");
			writer.println("<div class=\"frontPageNewsItemTitle\"><a onclick=\"loadingData();\" href='/about/news.jsp?Id="+ np.getId() +"' title=\"Read Full Story\">"+ np.getNewsTitle() + "</a></div>");
			writer.println("</div>");
			
			
								
								writer.println("<div class='card-body frontPageNewsCardBody'>");
								if (!(StringUtils.isEmpty(np.getNewsPhoto()))) {
									writer.println("<img src='/includes/files/news/img/" + np.getNewsPhoto() + "' class='frontPageNewsPhoto thumbnail' border='0' title='" + np.getNewsPhotoCaption() + "'  />");
								}
								
								writer.println("<div class=\"frontPageNewsItemBody \">");
								
								if (np.getNewsDescription().length() > 150) {
									writer.println(np.getNewsDescription().substring(0, 150).replaceAll("\\<.*?\\>", ""));
									writer.println("...");
								}
								else {
									writer.println(np.getNewsDescription().replaceAll("\\<.*?\\>", ""));
								}
								writer.println("</div>");
								writer.println("</div>");
								
								
								
								writer.println("<div class=\"card-footer frontPageNewsItemFooter\">");
								writer.println("<a href='/about/news.jsp?Id="+ np.getId() +"' class='btn btn-xs btn-light' title='Read Full Story'>");
								writer.println("<i class=\"fab fa-readme\"></i> Full Story");
								writer.println("</a>");
								
								if (!(StringUtils.isEmpty(np.getNewsDocumentation()))) {
									writer.println("<a href='/includes/files/news/doc/" + np.getNewsDocumentation() + "' class='btn btn-xs btn-light' title='View Attachment'>");
									writer.println("<i class=\"fas fa-paperclip\"></i> Attachment");
									writer.println("</a>");
								}
								if (!(StringUtils.isEmpty(np.getNewsExternalLinkTitle()))) {
									writer.println("<a href='" + np.getNewsExternalLink() + "' class='btn btn-xs btn-light' title='View External Link" + np.getNewsExternalLinkTitle() + "'>");
									writer.println("<i class=\"fas fa-link\"></i> External Link");
									writer.println("</a>");
								}
								for (NewsPostingFileBean npfb : np.getOtherNewsFiles()) {
									writer.println(
											"<a href='/includes/files/news/doc/" + npfb.getNfDoc() + "' class='btn btn-xs btn-light' title='" + npfb.getNfTitle() + "'>");
									if (npfb.getNfTitle().toLowerCase().contains("form")) {
										writer.println("<i class=\"far fa-clipboard\"></i> Form");
									}
									else if (npfb.getNfTitle().toLowerCase().contains("presentation")) {
										writer.println("<i class=\"far fa-file-powerpoint\"></i> Presentation");
									}
									else {
										writer.println("<i class=\"far fa-file-alt\"></i> Document");
									}
									writer.println("</a>");
								}
								writer.println("</div>");				
								writer.println("</div>");
								
								writer.println("<div class='frontPageItemSubBlockSmallScreen'>");
								writer.println("<div class='frontPageNewsItemSubBlock'>");
								writer.println("<div class='frontPageNewsItemDate'>");
								writer.println(sdf.format(np.getNewsDate()));
								writer.println("</div>");
								writer.println("<div class=\"frontPageNewsItemSubTitle\"><a onclick=\"loadingData();\" href='/about/news.jsp?Id="+ np.getId() +"' title=\"Read Full Story\">"+ np.getNewsTitle() + "</a></div></div>");
								writer.println("<hr>");
								writer.println("</div>");
								
} else {
	
	
	writer.println("<div class='frontPageNewsItemSubBlock'>");
	
	//if (!(StringUtils.isEmpty(np.getNewsPhoto()))) {
	//	writer.println("<img src='/includes/files/news/img/" + np.getNewsPhoto() + "' class='frontPageSubNewsPhoto thumbnail' border='0' title='" + np.getNewsPhotoCaption() + "'  />");
	//}
	writer.println("<div class='frontPageNewsItemDate'>");
	writer.println(sdf.format(np.getNewsDate()));
	writer.println("</div>");
	writer.println("<div class=\"frontPageNewsItemSubTitle\"><a onclick=\"loadingData();\" href='/about/news.jsp?Id="+ np.getId() +"' title=\"Read Full Story\">"+ np.getNewsTitle() + "</a></div></div>");
	writer.println("<hr>");
	
	//if(counter==6) {
	//	writer.println("<div style='margin-bottom:5px;'><a onclick=\"loadingData();\" href=\"/about/newslist.jsp\" class=\"btn btn-sm btn-light\" title=\"Complete News Archive\">More News</a></div>");
	//}
	
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
						writer.println("<div class='announcement-item'><div class='item-body'>There are no " + type.getDescription() + " items to display at this time. Please check back again at a later time. </div></div>");
					}

				}
				writer.flush();
				writer.close();
				np_real = new File(nlesd_rootbasepath + "about/generated/newspostings_" + type.getValue() + ".html");
				if (np_real.exists()) {
					np_real.delete();
					System.err.println("<<<<<< NEWS EXISTING " + np_real.getName() + " FILE DELETED >>>>>>");
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