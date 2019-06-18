package com.esdnl.personnel.v2.site.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.personnel.v2.model.sds.constant.LocationConstant;
import com.esdnl.personnel.v2.model.sds.constant.PositionConstant;
import com.esdnl.util.StringUtils;

public class EmployeeCardsTagHandler extends TagSupport {

	private static final long serialVersionUID = 4595991927044141635L;

	private PositionConstant type = null;
	private String schoolYear = null;
	private String group = null;
	private LocationConstant location = null;
	private boolean bySenority = false;
	private boolean byAvailability = false;
	private boolean showAvailabilityOp = false;
	private Date viewDate = null;
	private int anyOrderCutOff = 0;

	public void setType(PositionConstant type) {

		this.type = type;
	}

	public void setSchoolYear(String schoolYear) {

		this.schoolYear = schoolYear;
	}

	public void setGroup(String group) {

		this.group = group;
	}

	public void setLocation(LocationConstant location) {

		this.location = location;
	}

	public void setBySenority(boolean bySenority) {

		this.bySenority = bySenority;
	}

	public void setByAvailability(boolean byAvailability) {

		this.byAvailability = byAvailability;
	}

	public void setShowAvailabilityOp(boolean showAvailabilityOp) {

		this.showAvailabilityOp = showAvailabilityOp;
	}

	public void setViewDate(Date viewDate) {

		this.viewDate = viewDate;
	}

	public void setAnyOrderCutOff(int anyOrderCutOff) {

		this.anyOrderCutOff = anyOrderCutOff;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		EmployeeBean[] emps = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf_v_date = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
		String card_class = null;
		User usr = null;

		try {
			usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);

			out = pageContext.getOut();

			if (this.bySenority)
				emps = EmployeeManager.getEmployeeBeansBySenority(this.type, this.schoolYear, this.location);
			else if (this.byAvailability) {
				if (this.viewDate != null)
					emps = EmployeeManager.getEmployeeBeansByAvailability(this.type, this.schoolYear, this.location,
							this.viewDate);
				else
					emps = EmployeeManager.getEmployeeBeansByAvailability(this.type, this.schoolYear, this.location,
							Calendar.getInstance().getTime());
			}
			else {
				if (!StringUtils.isEmpty(this.group))
					emps = EmployeeManager.getEmployeeBeans(this.type, this.schoolYear, group);
				else
					emps = EmployeeManager.getEmployeeBeans(this.type, this.schoolYear);
			}

			boolean display_div_line = false;

			out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
			if (emps.length > 0) {
				for (int i = 0; i < emps.length; i++) {
					card_class = (emps[i].getCurrentAvailability().isFutureBooking(viewDate) ? "EmployeeCardFutureBooking"
							: (emps[i].getCurrentAvailability().isBooked() ? "EmployeeCardBooked"
									: (emps[i].getCurrentAvailability().isNotAvailable() ? "EmployeeCardNotAvailable" : "EmployeeCard")));

					out.println("<TR><TD width='100%' style='padding:5px;'>");
					out.println("<TABLE width='100%' cellpadding='0' cellspacing='0' class='" + card_class + "'>");
					out.println("<TR><TD width='100%' class='Header'><TABLE width='100%' cellpadding='0' cellspacing='0'><TR><TD>"
							+ emps[i].getFullnameReverse() + "</TD>");

					if (usr.isAdmin())
						out.println("<TD align='right' style='padding-right:5px;'><a class='delete' href='deleteFutureBookings.html?id="
								+ emps[i].getEmpId().trim()
								+ "&view_date="
								+ sdf_v_date.format(this.viewDate)
								+ "'>Delete Future Bookings</a></TD>");

					out.println("</TR></TABLE></TD></TR>");

					out.println("<TR><TD width='100%' class='Body'>");
					out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
					out.println("<TR>");
					out.println("<TD width='50%' valign='top' style='padding-top:5px;'>");
					out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
					// out.println("<TR><TD width='30%' class='Label'>SIN:</TD><TD width='*' class='Content'>"
					// + emps[i].getSIN() + "</TD></TR>");
					out.println("<TR><TD width='30%' class='Label'>Address:</TD><TD width='*' class='Content'>"
							+ emps[i].getAddress1() + "</TD></TR>");
					if (!StringUtils.isEmpty(emps[i].getAddress2()))
						out.println("<TR><TD width='30%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>"
								+ emps[i].getAddress2() + "</TD></TR>");
					out.println("<TR><TD width='30%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>" + emps[i].getCity()
							+ ", " + emps[i].getProvince() + "</TD></TR>");
					out.println("<TR><TD width='30%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>"
							+ emps[i].getPostalCode() + "</TD></TR>");
					out.println("</TABLE>");
					out.println("</TD>");
					out.println("<TD width='50%' valign='top' style='padding-top:5px;'>");
					out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
					out.println("<TR><TD width='40%' class='Label'>Phone:</TD><TD width='*' class='Content'>"
							+ emps[i].getPhone() + "</TD></TR>");
					if (!StringUtils.isEmpty(emps[i].getAlternatePhone()))
						out.println("<TR><TD width='40%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>"
								+ emps[i].getAlternatePhone() + "</TD></TR>");
					out.println("<TR><TD width='40%' class='Label'>Seniority Date:</TD><TD width='*' class='Content'>"
							+ ((emps[i].getSeniorityDate() != null) ? sdf.format(emps[i].getSeniorityDate())
									: "<SPAN style='color:#FF0000;'>UNKNOWN</SPAN>") + "</TD></TR>");
					out.println("</TABLE>");
					out.println("</TD>");
					out.println("</TR>");
					if (!StringUtils.isEmpty(emps[i].getLocationPreferences())) {
						out.println("<TR>");
						out.println("<TD colspan='2' valign='top' style='padding-top:5px;'>");
						out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
						out.println("<TR><TD width='125' class='Label'>Location Preferences:</TD><TD width='*' class='Content'>"
								+ emps[i].getLocationPreferences() + "</TD></TR>");
						out.println("</TABLE>");
						out.println("</TD>");
						out.println("</TR>");
					}
					out.println("</TABLE>");
					out.println("</TD></TR>");
					out.println("<TR><TD width='100%' class='Operations'>");
					out.println("<TABLE align='right' cellpadding='0' cellspacing='2'>");
					out.println("<TR>");
					if (this.showAvailabilityOp)
						out.println("<TD><a href='employee_availability.jsp?id=" + emps[i].getEmpId().trim()
								+ "'>Availability</a></TD>");
					if (this.byAvailability) {
						if (emps[i].getCurrentAvailability().isAvailable()
								|| emps[i].getCurrentAvailability().isFutureBooking(viewDate)) {
							if (emps[i].getCurrentAvailability().isFutureBooking(viewDate)) {
								out.println("<TD>BOOKED AT: " + emps[i].getCurrentAvailability().getBookedWhere().getSchoolName()
										+ "<BR>" + sdf_v_date.format(emps[i].getCurrentAvailability().getStartDate()) + " TO "
										+ sdf_v_date.format(emps[i].getCurrentAvailability().getEndDate()) + "</TD>");
							}
							out.println("<TD><a href='javascript:openWindow(\"BOOK_EMPLOYEE\", \"empNotAvailable.html?id="
									+ emps[i].getEmpId().trim() + "&view_date=" + sdf_v_date.format(this.viewDate)
									+ "\", 375, 325, 0);'>Not Available</a></TD>");
							out.println("<TD><a href='javascript:openWindow(\"BOOK_EMPLOYEE\", \"empBooked.html?id="
									+ emps[i].getEmpId().trim() + "&view_date=" + sdf_v_date.format(this.viewDate)
									+ "\", 375, 325, 0);'>Booked</a></TD>");
						}
						else if (!emps[i].getCurrentAvailability().isFutureBooking(viewDate)
								&& emps[i].getCurrentAvailability().isBooked()) {
							out.println("<TD>BOOKED AT: " + emps[i].getCurrentAvailability().getBookedWhere().getSchoolName()
									+ "<BR>" + sdf_v_date.format(emps[i].getCurrentAvailability().getStartDate()) + " TO "
									+ sdf_v_date.format(emps[i].getCurrentAvailability().getEndDate()) + "</TD>");
							if (usr.getUserPermissions().containsKey("PERSONNEL_SUBSTITUTES-UNBOOK-ANY")
									|| (usr.getPersonnel().getPersonnelID() == emps[i].getCurrentAvailability().getBookedById()))
								out.println("<TD style='padding-left:10px;'><a href='empUnbooked.html?id=" + emps[i].getSIN().trim()
										+ "&view_date=" + sdf_v_date.format(this.viewDate) + "&start_date="
										+ sdf_v_date.format(emps[i].getCurrentAvailability().getStartDate()) + "&end_date="
										+ sdf_v_date.format(emps[i].getCurrentAvailability().getEndDate()) + "'>Remove Booking</a></TD>");
						}
						else if (emps[i].getCurrentAvailability().isNotAvailable()) {
							out.println("<TD style='text-transform:uppercase;'>REASON:&nbsp;"
									+ emps[i].getCurrentAvailability().getReason() + "</TD>");
							if (usr.getUserPermissions().containsKey("PERSONNEL_SUBSTITUTES-UNBOOK-ANY")
									|| (usr.getPersonnel().getPersonnelID() == emps[i].getCurrentAvailability().getNotAvailableById()))
								out.println("<TD style='padding-left:10px;'><a href='empUnbooked.html?id=" + emps[i].getSIN().trim()
										+ "&view_date=" + sdf_v_date.format(this.viewDate) + "&start_date="
										+ sdf_v_date.format(emps[i].getCurrentAvailability().getStartDate()) + "&end_date="
										+ sdf_v_date.format(emps[i].getCurrentAvailability().getEndDate())
										+ "'>Change Availability</a></TD>");
						}
					}
					out.println("</TR>");
					out.println("</TABLE>");
					out.println("</TD></TR>");
					out.println("</TABLE>");
					out.println("</TD><TR>");

					if (!display_div_line && ((i + 1) < emps.length) && (emps[i + 1].getTenurCode() > this.anyOrderCutOff)) {
						display_div_line = true;
						out.println("<TR><TD width='100%' class='TENURE_CUTOFF_LINE'>");
						out.println(this.type + "s below can be called in any order.");
						out.println("</TD><TR>");
					}
				}
			}
			else {
				out.println("<TR><TD width='100%' style='padding:5px;color:#ff0000;'>No " + type + "s found.");
			}
			out.println("</TABLE>");
		}
		catch (SecurityException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (EmployeeException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}