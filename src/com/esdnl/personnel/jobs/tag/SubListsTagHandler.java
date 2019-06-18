package com.esdnl.personnel.jobs.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.school.SchoolException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class SubListsTagHandler extends TagSupport {

	private static final long serialVersionUID = -251485408509480825L;
	private SubstituteListConstant type;

	public void setType(SubstituteListConstant type) {

		this.type = type;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		TreeMap<String, ArrayList<SubListBean>> listMap = null;
		Collection<RegionBean> regions = null;
		String bg_color = "";
		String bd_color = "";
		int jcnt = 0;
		int collapseNum = 0; //for Bootstrap Collapse id
		int subListNum = 0;
		

		try {
			out = pageContext.getOut();
			//regions = RegionManager.getRegionBeans();

			listMap = SubListManager.getSubListBeans(this.type);

			
			//Modified Code for PP 2019
			
			if ((listMap != null) && (listMap.size() > 0)) {
				for (Map.Entry<String, ArrayList<SubListBean>> entry : listMap.entrySet()) {
					out.append("<div class=\"panel-group\" style=\"padding-top:5px;\"><div class=\"panel panel-success\">");
					out.println("<div class=\"panel-heading\"><b>" + type.getDescription() + " - " + entry.getKey()
							+ "</b></div>");
					out.append("<div class=\"panel-body\">");
					out.append("<div class=\"panel-group\" id=\"accordion\">");
					
					for (SchoolZoneBean zone : SchoolZoneService.getSchoolZoneBeans()) {
						StringBuffer buf = new StringBuffer();
						collapseNum ++;
						subListNum = 0;
						buf.append("<div class=\"panel panel-default\">");
						buf.append("<div class=\"panel-heading\"><h4 class=\"panel-title\"><a data-toggle=\"collapse\" data-parent=\"#accordion\" href='#collapse"+collapseNum+"' style=\"font-size:14px;text-transform:Capitalize;color:DimGrey;\">" + zone.getZoneName()
								+ " Region Lists  (<span id='listCount"+collapseNum+"'>0</span>)</a></h4></div>");						
						buf.append("<div id='collapse" + collapseNum + "' class=\"panel-collapse collapse\"><div class=\"panel-body\">");
						regions = RegionManager.getRegionBeans(zone);
                        
						if (regions != null && regions.size() > 0) {
							for (RegionBean region : regions) {								
								buf.append("<span style=\"font-size:14px;color:DimGrey;font-weight:bold;text-transform:Capitalize;\">" + region.getName() + "</span>");
								buf.append("<div class=\"table-responsive\"><table class='table table-striped table-condensed' style='font-size:12px;'>");
								buf.append("<thead><tr><td colspan=\"2\" style=\"border-bottom:1px solid grey;\"></td></tr><tr><th style=\"font-weight:bold;width:90%;\">List Title</th><th style=\"font-weight:bold;width:10%;\">Options</th></tr></thead><tbody>");
								jcnt = 0;
								for (SubListBean list : entry.getValue()) {
									if (list.getRegion().getId() != region.getId())
										continue;
									buf.append("<tr id='"+ list.getId() +"'>");					
									buf.append("<td>" + list.getTitle() + "</td>");
									buf.append("<td><a class='btn btn-warning btn-xs' href='view_sub_list.jsp?list_id="	+ list.getId() + "'>View List</a></td></tr>");
									jcnt++;
									subListNum++;
								}
								buf.append("<script>$('#listCount"+collapseNum+"').html("+subListNum+");</script>");
								if (jcnt <= 0)
									buf.append("\t<tr><td colspan='2' style='color:Red;'>No lists available at this time.</td></tr>");

								buf.append("<tr><td colspan=\"2\" style=\"border-top:1px solid grey;\"></td></tr></tbody></table>");
								buf.append("</div>");
							}
							
						}
						buf.append("</div></div></div>");
						
						out.println(buf.toString());
					}
					
					out.append("</div></div></div></div>");
					
				}
			}
			else {
				out.println("<div class=\"panel-group\" style=\"padding-top:5px;\"><div class=\"panel panel-danger\">");
				out.println("<div class=\"panel-heading\"><b>Substitue Teacher Lists</b></div>");
				out.println("<div class=\"panel-body\">No lists curently available.</div>");
				out.println("</div></div>");
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			/* OLD OLD CODE. ORIGINAL BELOW THIS BLOCK
			 * 
			if ((listMap != null) && (listMap.size() > 0)) {
				Map.Entry[] entries = (Map.Entry[]) listMap.entrySet().toArray(
						new Map.Entry[0]);

				for (int k = 0; k < entries.length; k++) {
					out.println("<h4 class='subTitle'>" + (String) entries[k].getKey()
							+ "</h4>");
					for (int j = 0; j < regions.length; j++) {
						StringBuffer buf = new StringBuffer();

						buf.append("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
						buf.append("\t<TR><TD colspan='2' style='color:#FF0000;' class='displayHeaderTitle'>"
								+ regions[j].getName() + "</TD></TR>");
						buf.append("\t<TR><TD class='displayHeaderTitle'>List Title</TD><TD>&nbsp;</TD></TR>");

						jcnt = 0;

						lists = (SubListBean[]) ((ArrayList) entries[k].getValue()).toArray(new SubListBean[0]);

						for (int i = 0; i < lists.length; i++) {
							if (lists[i].getRegion().getId() != regions[j].getId())
								continue;

							bg_color = (((jcnt++ % 2) == 0) ? "#E0E0E0" : "#FFFFFF");
							bd_color = "#C0C0C0";

							buf.append("<TR id='"
									+ lists[i].getId()
									+ "' style='padding-top:3px;padding-bottom:3px;background-color:"
									+ bg_color + ";' "
									+ "onmouseover=\"toggleTableRowHighlight('"
									+ lists[i].getId() + "', '#FFCC00');\""
									+ "onmouseout=\"toggleTableRowHighlight('" + lists[i].getId()
									+ "', '" + bg_color + "');\">");
							buf.append("\t<TD style='border-top:solid 1px " + bd_color
									+ ";border-bottom:solid 1px " + bd_color
									+ ";' class='displayText'>" + lists[i].getTitle() + "</TD>");

							buf.append("\t<TD align='right' style='border-top:solid 1px "
									+ bd_color
									+ ";border-bottom:solid 1px "
									+ bd_color
									+ ";' class='displayText'><A style='color:#FF0000;font-weight:bold;text-decoration:none;' href='view_sub_list.jsp?list_id="
									+ lists[i].getId() + "'>View</A></TD></TR>");
						}

						buf.append("</TABLE>");
						buf.append("<BR><BR>");

						if (jcnt > 0)
							out.println(buf.toString());
					}
				}
			}
			else {
				out.println("<TABLE width='75%' cellpadding='3' cellspacing='0' align='center'>");
				out.println("<TR><TD colspan='2' class='displayText'>No lists available at this time. Thank you.</TD></TR>");
				out.println("</TABLE>");
			}
			*/
			
			
			/* ORIGINAL CODE
			if ((listMap != null) && (listMap.size() > 0)) {
				for (Map.Entry<String, ArrayList<SubListBean>> entry : listMap.entrySet()) {
					out.append("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
					out.println("<tr><td class='displayPageTitle'>" + type.getDescription() + " - " + entry.getKey()
							+ "</td></tr>");
					out.append("</TABLE><br/>");

					for (SchoolZoneBean zone : SchoolZoneService.getSchoolZoneBeans()) {
						StringBuffer buf = new StringBuffer();

						buf.append("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
						buf.append("\t<TR><TD class='displayPageTitle' style='text-transform:capitalize;'>" + zone.getZoneName()
								+ " Region</TD></TR>");
						buf.append("</TABLE><br/>");

						regions = RegionManager.getRegionBeans(zone);

						if (regions != null && regions.size() > 0) {
							for (RegionBean region : regions) {
								buf.append("<TABLE width='98%' cellpadding='3' cellspacing='0' align='center'>");
								buf.append("\t<TR><TD colspan='2' class='displayHeaderTitle' style='color:#FF0000;text-transform:capitalize;border:none;' >"
										+ region.getName() + "</TD></TR>");
								buf.append("\t<TR><TD class='displayHeaderTitle'>List Title</TD><TD>&nbsp;</TD></TR>");

								jcnt = 0;

								for (SubListBean list : entry.getValue()) {
									if (list.getRegion().getId() != region.getId())
										continue;

									bg_color = (((jcnt++ % 2) == 0) ? "#E0E0E0" : "#FFFFFF");

									bd_color = "#C0C0C0";

									buf.append("<TR id='" + list.getId()
											+ "' style='padding-top:3px;padding-bottom:3px;background-color:" + bg_color + ";' "
											+ "onmouseover=\"toggleTableRowHighlight('" + list.getId() + "', '#FFCC00');\""
											+ "onmouseout=\"toggleTableRowHighlight('" + list.getId() + "', '" + bg_color + "');\">");
									buf.append("\t<TD style='border-top:solid 1px " + bd_color + ";border-bottom:solid 1px " + bd_color
											+ ";' class='displayText'>" + list.getTitle() + "</TD>");

									buf.append("\t<TD align='right' style='border-top:solid 1px "
											+ bd_color
											+ ";border-bottom:solid 1px "
											+ bd_color
											+ ";' class='displayText'><A style='color:#FF0000;font-weight:bold;text-decoration:none;' href='view_sub_list.jsp?list_id="
											+ list.getId() + "'>View</A></TD></TR>");
								}
								if (jcnt <= 0)
									buf.append("\t<TR><TD colspan='2' class='displayText'>No lists available at this time. Thank you.</TD></TR>");

								buf.append("</TABLE>");
								buf.append("<BR><BR>");
							}
						}
						else {

						}
						out.println(buf.toString());
					}
				}
			}
			else {
				out.println("<TABLE width='100%' cellpadding='3' cellspacing='0' align='center'>");
				out.println("\t<TR><TD class='displayText'>No lists available at this time. Thank you.</TD></TR>");
				out.println("</TABLE>");
			}
 */
			// System.err.println("TAG FINISHED");
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (RegionException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (SchoolException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}