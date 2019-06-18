package com.esdnl.personnel.jobs.dao.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

import com.awsd.school.SchoolException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.personnel.jobs.service.OpenJobsExportService;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class OpenJobsWorker extends TimerTask {

	private String rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../ROOT/";

	public OpenJobsWorker() {

		super();
		System.err.println("<<<<<< OPEN JOBS TIMER STARTED >>>>>");
	}

	public void run() {

		try {
			synchronized (OpenJobsExportService.OPEN_JOBS) {
				OpenJobsExportService.OPEN_JOBS.clear();
				OpenJobsExportService.OPEN_JOBS.addAll(JobOpportunityManager.getJobOpportunityBeansVector("OPEN"));
				OpenJobsExportService.OPEN_JOBS.addAll(JobOpportunityManager.getJobOpportunityBeansVector("AWARDED"));
				System.err.println("<<<<<< OPEN JOBS RELOADED >>>>>");

				createJobFiles();

				createSubListFiles();
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	private void createJobFiles() {

		File job_tmp = null, job_real = null;
		PrintWriter writer = null;
		JobOpportunityBean[] jobs = null;
		JobOpportunityAssignmentBean[] ass = null;

		try {
			jobs = (JobOpportunityBean[]) OpenJobsExportService.OPEN_JOBS.toArray(new JobOpportunityBean[0]);

			for (int ii = 0; ii < JobTypeConstant.ALL.length; ii++) {
				job_tmp = new File(rootbasepath + "employment/generated/index" + JobTypeConstant.ALL[ii].getValue() + ".tmp");

				if (!job_tmp.getParentFile().exists()) {
					job_tmp.getParentFile().mkdirs();
				}

				if (job_tmp.exists()) {
					System.err.println("<<<<< " + job_tmp.getName() + " FILE ALREADY EXIST >>>>>");

					if (job_tmp.delete()) {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
					}
				}

				writer = new PrintWriter(new FileWriter(job_tmp), true);

				writer.println("<html>");
				writer.println("<head>");
				writer.println("<title>Newfoundland &amp; Labrador English School District - Employment Opportunities</title>");
				writer.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
				// writer.println("<link rel='stylesheet'
				// href='../../includes/style.css' type='text/css'>");
				writer.println("<link rel='stylesheet' href='/employment/css/employment.css' type='text/css'>");
				writer.println("<script type='text/javascript'>");
				writer.println("\tfunction toggleTableRowHighlight(target, color)");
				writer.println("\t{");
				writer.println("\t\tvar rowSelected = document.getElementById(target);");
				writer.println("\t\trowSelected.style.backgroundColor=color; ");
				writer.println("\t}");
				writer.println("</script>");
				writer.println("</head>");
				writer.println("<body style='background-color:#f0f0f0;'>");
				writer.println("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
				writer.println("\t<TR><TD colspan='4' style='color:#FF0000;' class='displayHeaderTitle'>"
						+ JobTypeConstant.ALL[ii].getDescription() + "</TD></TR>");
				writer.println("\t<TR><TD class='displayHeaderTitle'>Competition #</TD><TD class='displayHeaderTitle'>Position Title</TD><TD class='displayHeaderTitle'>Competition End Date</TD><TD>&nbsp;</TD></TR>");

				int jcnt = 0;
				String bg_color = "";
				String bd_color = "";
				if (jobs.length > 0) {
					for (int i = 0; i < jobs.length; i++) {
						if ((JobTypeConstant.AWARDED.equal(JobTypeConstant.ALL[ii]) && jobs[i].isAwarded())
								|| (!jobs[i].isAwarded() && (jobs[i].getJobType().getValue() == JobTypeConstant.ALL[ii].getValue()))) {

							if (!jobs[i].isCancelled())
								bg_color = (((jcnt++ % 2) == 0) ? "#E0E0E0" : "#FFFFFF");
							else
								bg_color = "#FFFFCC";
							bd_color = ((!jobs[i].isCancelled()) ? "#C0C0C0" : "#FF0000");
							// ass =
							// JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(jobs[i]);
							ass = null;
							ass = (JobOpportunityAssignmentBean[]) jobs[i].toArray(new JobOpportunityAssignmentBean[0]);

							writer.println("\t<TR id='" + jobs[i].getCompetitionNumber()
									+ "' style='padding-top:3px;padding-bottom:3px;background-color:" + bg_color + ";'");
							writer.println("\t\tonmouseover=\"toggleTableRowHighlight('" + jobs[i].getCompetitionNumber()
									+ "', '#FFCC00');\"");
							writer.println("\t\tonmouseout=\"toggleTableRowHighlight('" + jobs[i].getCompetitionNumber() + "', '"
									+ bg_color + "');\">");
							writer.println("\t\t<TD style='border-top:solid " + (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
									+ " ;border-bottom:solid " + (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
									+ ";' class='displayText'>" + jobs[i].getCompetitionNumber() + " </TD>");
							writer.println("\t\t<TD style='border-top:solid " + (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
									+ ";border-bottom:solid " + (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
									+ ";' class='displayText'>");
							for (int j = 0; ((ass != null) && (j < ass.length)); j++)
								writer.println("\t\t\t<b>" + ass[j].getLocationText() + "</b><br>");
							writer.println("\t\t\t" + jobs[i].getPositionTitle());
							writer.println("\t\t</TD>");
							if (!jobs[i].isCancelled()) {
								writer.println("\t\t<TD style='border-top:solid " + (jobs[i].isCancelled() ? "3px " : "1px ")
										+ bd_color + ";border-bottom:solid " + (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
										+ ";' class='displayText'>" + jobs[i].getFormatedCompetitionEndDate() + "</TD>");
								writer.println("\t\t<TD style='border-top:solid " + (jobs[i].isCancelled() ? "3px " : "1px ")
										+ bd_color + ";border-bottom:solid " + (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
										+ " ;' class='displayText'>");
								writer.println("\t\t\t<A style='color:#FF0000;font-weight:bold;text-decoration:none;' href='/employment/view_job_post.jsp?comp_num="
										+ jobs[i].getCompetitionNumber() + "'>View</A>");
								writer.println("\t\t</TD>");
							}
							else {
								writer.println("\t\t<TD align='center' style='border-top:solid "
										+ (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color + ";border-bottom:solid "
										+ (jobs[i].isCancelled() ? "3px " : "1px ") + bd_color
										+ ";color:#FF0000;font-weight:bold;' colspan='2' class='displayText'>POSITION CANCELLED</TD>");
							}
							writer.println("\t</TR>");
						}
					}
					if (jcnt <= 0)
						writer.println("\t<TR><TD colspan='4' class='displayText'>No positions available at this time. Thank you.</TD></TR>");
				}
				else {
					writer.println("\t<TR><TD colspan='4' class='displayText'>No positions available at this time. Thank you.</TD></TR>");
				}
				writer.println("</TABLE>");
				writer.println("</BODY>");
				writer.println("</HTML>");
				writer.flush();
				writer.close();

				job_real = new File(rootbasepath + "employment/generated/index" + JobTypeConstant.ALL[ii].getValue() + ".html");
				if (job_real.exists()) {
					job_real.delete();
					System.err.println("<<<<<< EXISTING " + job_real.getName() + " FILE DELETED >>>>>>");
				}
				job_tmp.renameTo(job_real);
			}
		}
		catch (IOException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	private void createSubListFiles() throws SchoolException {

		File job_tmp = null, job_real = null;
		PrintWriter writer = null;
		TreeMap<String, ArrayList<SubListBean>> listMap = null;
		Collection<RegionBean> regions = null;

		try {

			for (int ii = 0; ii < SubstituteListConstant.ALL.length; ii++) {

				listMap = SubListManager.getSubListBeans(SubstituteListConstant.ALL[ii]);

				job_tmp = new File(rootbasepath + "employment/generated/index_sublist_"
						+ SubstituteListConstant.ALL[ii].getValue() + ".tmp");

				if (!job_tmp.getParentFile().exists()) {
					job_tmp.getParentFile().mkdirs();
				}

				if (job_tmp.exists()) {
					System.err.println("<<<<< " + job_tmp.getName() + " FILE ALREADY EXIST >>>>>");

					if (job_tmp.delete()) {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE DELETED >>>>>");
					}
					else {
						System.err.println("<<<<< " + job_tmp.getName() + " FILE COULD NOT BE DELETED >>>>>");
					}
				}

				writer = new PrintWriter(new FileWriter(job_tmp), true);

				writer.println("<html>");
				writer.println("<head>");
				writer.println("<title>Newfoundland &amp; Labrador English School District - Employment Opportunities</title>");
				writer.println("<link rel='stylesheet' href='/employment/css/employment.css' type='text/css'>");
				writer.println("<script type='text/javascript'>");
				writer.println("\tfunction toggleTableRowHighlight(target, color)");
				writer.println("\t{");
				writer.println("\t\tvar rowSelected = document.getElementById(target);");
				writer.println("\t\trowSelected.style.backgroundColor=color; ");
				writer.println("\t}");
				writer.println("</script>");
				writer.println("</head>");
				writer.println("<body style='background-color:#f0f0f0;'>");

				int jcnt = 0;
				String bg_color = "";
				String bd_color = "";

				if ((listMap != null) && (listMap.size() > 0)) {
					for (Map.Entry<String, ArrayList<SubListBean>> entry : listMap.entrySet()) {
						/*
						writer.println("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
						writer.println("\t<TR><TD class='displayPageTitle'>" + SubstituteListConstant.ALL[ii].getDescription()
								+ " - " + entry.getKey() + "</TD></TR>");
						writer.println("</TABLE>");
						*/

						for (SchoolZoneBean zone : SchoolZoneService.getSchoolZoneBeans()) {
							writer.println("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
							writer.println("\t<TR><TD class='displayPageTitle' style='text-transform:capitalize;'>"
									+ zone.getZoneName() + " Region</TD></TR>");
							writer.println("</TABLE><br/>");

							//writer.println("<h5 style='text-align:center;font-family:arial;text-transform: capitalize;'>"
							//		+ zone.getZoneName() + " Region</h5>");

							regions = RegionManager.getRegionBeans(zone);

							if (regions != null && regions.size() > 0) {
								for (RegionBean region : regions) {
									writer.println("<TABLE width='98%' cellpadding='3' cellspacing='0' align='center'>");
									writer.println("\t<TR><TD colspan='2' style='color:#FF0000;text-transform:capitalize;' class='displayHeaderTitle'>"
											+ region.getName() + "</TD></TR>");
									writer.println("\t<TR><TD class='displayHeaderTitle'>List Title</TD><TD>&nbsp;</TD></TR>");

									jcnt = 0;

									for (SubListBean list : entry.getValue()) {
										if (list.getRegion().getId() != region.getId())
											continue;

										bg_color = (((jcnt++ % 2) == 0) ? "#E0E0E0" : "#FFFFFF");

										bd_color = "#C0C0C0";

										writer.println("\t<TR id='" + list.getId()
												+ "' style='padding-top:3px;padding-bottom:3px;background-color:" + bg_color + ";'");
										writer.println("\t\tonmouseover=\"toggleTableRowHighlight('" + list.getId() + "', '#FFCC00');\"");
										writer.println("\t\tonmouseout=\"toggleTableRowHighlight('" + list.getId() + "', '" + bg_color
												+ "');\">");
										writer.println("\t\t<TD align='left' style='border-top:solid 1px " + bd_color
												+ " ;border-bottom:solid 1px " + bd_color + ";' class='displayText'>" + list.getTitle()
												+ " </TD>");
										writer.println("\t\t<TD align='right' style='border-top:solid 1px " + bd_color
												+ " ;border-bottom:solid 1px " + bd_color + ";' class='displayText'>");
										writer.println("\t\t\t<A style='color:#FF0000;font-weight:bold;text-decoration:none;' href='/employment/view_sub_list.jsp?list_id="
												+ list.getId() + "'>View</A>");
										writer.println("\t\t</TD>");

										writer.println("\t</TR>");
									}
									if (jcnt <= 0)
										writer.println("\t<TR><TD colspan='2' class='displayText'>No lists available at this time. Thank you.</TD></TR>");

									writer.println("</TABLE>");
									writer.println("<BR><BR>");
								}
							}
							else {

							}
						}
					}
				}
				else {
					writer.println("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
					writer.println("\t<TR><TD class='displayText'>No lists available at this time. Thank you.</TD></TR>");
					writer.println("</TABLE>");
				}

				writer.println("</BODY>");
				writer.println("</HTML>");
				writer.flush();
				writer.close();

				job_real = new File(rootbasepath + "employment/generated/index_sublist_"
						+ SubstituteListConstant.ALL[ii].getValue() + ".html");
				if (job_real.exists()) {
					job_real.delete();
					System.err.println("<<<<<< EXISTING " + job_real.getName() + " FILE DELETED >>>>>>");
				}
				job_tmp.renameTo(job_real);
			}
		}
		catch (JobOpportunityException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
		catch (RegionException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
		catch (IOException e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}
}