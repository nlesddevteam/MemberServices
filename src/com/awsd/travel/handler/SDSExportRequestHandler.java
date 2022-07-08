package com.awsd.travel.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.travel.PDTravelClaim;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimSummary;
import com.esdnl.servlet.RequestHandlerImpl;

public class SDSExportRequestHandler extends RequestHandlerImpl {

	public SDSExportRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-SDS-EXPORT"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		SimpleDateFormat sdf = null;
		SimpleDateFormat sds = null;
		SimpleDateFormat sds_desc = null;
		DecimalFormat inv_df = null;
		DecimalFormat df = null;
		Date sd = null;
		Iterator iter = null;
		Vector claims = null;
		TravelClaim claim = null;
		TravelClaimSummary summary = null;
		File exp_dat_dir = null;
		File exp_dat_file = null;
		File exp_tchr_dat_file = null;
		PrintWriter writer = null;
		PrintWriter tchr_writer = null;
		double gst = 0.0, total = 0.0;
		String gl_prt_1 = "", gl_prt_2 = "", gl_prt_3 = "", desc = "", vennum = "";

		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {
				if ((request.getParameter("start_date") != null) && !request.getParameter("start_date").equals("")) {
					sdf = new SimpleDateFormat("dd/MM/yyyy");

					try {
						sd = sdf.parse(request.getParameter("start_date"));
						System.err.println(sd);
					}
					catch (ParseException e) {
						System.err.println(e);
						sd = null;
					}

					if (sd != null) {
						if (request.getParameter("include_exported") != null)
							claims = TravelClaimDB.getPaidClaims(sd);
						else
							claims = TravelClaimDB.getPaidClaimsNotExported(sd);

						try {
							
							if (request.getParameter("dataTableExcel") != null) {
							
							//Just export the data for printing and excel if ticked. No Data file for SDS saved.
								request.setAttribute("msgok", "SUCCESS: Export Excel successfull.");
								request.setAttribute("RESULT", "SUCCESS");
								request.setAttribute("TRAVELCLAIMS", claims);
								request.setAttribute("EXCELCLAIMS", claims);
								path = "data_export_table.jsp";	
								
							} else {
							
							//For now, keep the SDS data export dat file in case they need it.
								
							iter = claims.iterator();

							sds = new SimpleDateFormat("yyyyMMdd");
							inv_df = new DecimalFormat("TC00000");
							df = new DecimalFormat("0.00");
							sds_desc = new SimpleDateFormat("MMMM yyyy");

							exp_dat_dir = new File(session.getServletContext().getRealPath("/") + "Travel/Export/");
							if (!exp_dat_dir.exists())
								exp_dat_dir.mkdirs();

							exp_dat_file = new File(exp_dat_dir.getPath() + "/"
									+ (new SimpleDateFormat("dd_MM_yy")).format(Calendar.getInstance().getTime()) + ".dat");
							if (exp_dat_file.exists())
								exp_dat_file.delete();

							exp_tchr_dat_file = new File(exp_dat_dir.getPath() + "/"
									+ (new SimpleDateFormat("dd_MM_yy")).format(Calendar.getInstance().getTime()) + "_tchr.dat");
							if (exp_tchr_dat_file.exists())
								exp_tchr_dat_file.delete();

							writer = new PrintWriter(new FileWriter(exp_dat_file), true);
							tchr_writer = new PrintWriter(new FileWriter(exp_tchr_dat_file), true);
							while (iter.hasNext()) {
								claim = (TravelClaim) iter.next();
								summary = claim.getSummaryTotals();
								vennum = claim.getPersonnel().getSDSInfo().getVendorNumber().replaceAll("-", "");
								if (!claim.isPaidThroughTeacherPayroll()) {
									total = summary.getSummaryTotal();
									gst = (total / 1.15) * 0.05;

									if (claim instanceof PDTravelClaim) {
										desc = summary.getKMSSummary() + " kms - "
												+ ((PDTravelClaim) claim).getPD().getTitle().trim().substring(0,
														(((PDTravelClaim) claim).getPD().getTitle().trim().length() > 32) ? 32
																: ((PDTravelClaim) claim).getPD().getTitle().trim().length())
												+ "...";
									}
									else {
										desc = summary.getKMSSummary() + " kms - " + Utils.getMonthString(claim.getFiscalMonth()) + " "
												+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
									}
								}
								else {
									total = summary.getSummaryTotal() - summary.getKMSTotal();
									gst = (total / 1.15) * 0.05;
									desc = "Meals/Other - " + Utils.getMonthString(claim.getFiscalMonth()) + " "
											+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());

									tchr_writer.println(
											vennum + ", " + df.format((summary.getSummaryTotal() - summary.getMealSummary())));
								}

								gl_prt_1 = claim.getSDSGLAccountCode().substring(0, 1);
								gl_prt_2 = claim.getSDSGLAccountCode().substring(1, 14);
								gl_prt_3 = delete_leading_zeros(claim.getSDSGLAccountCode().substring(14));

								if (total > 0) {
									writer.println(sds.format(claim.getPaidDate()) + "," + vennum + ","
											+ inv_df.format(claim.getClaimID()) + "," + df.format(gst) + "," + df.format(total) + ","
											+ gl_prt_1 + "," + gl_prt_2 + "," + gl_prt_3 + "," + desc);
								}
							}

							writer.flush();
							writer.close();
							tchr_writer.flush();
							tchr_writer.close();

							request.setAttribute("msgok", "SUCCESS: Export successfull.");
							request.setAttribute("RESULT", "SUCCESS");
							request.setAttribute("TRAVELCLAIMS", claims);
							path = "sds_export_report.jsp";
						}
						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							request.setAttribute("msgerr", "ERROR: Could NOT export paid claims.");
							request.setAttribute("RESULT", "FAILED");
							path = "export_claims.jsp";
						}
						
					}
					else {
						request.setAttribute("msgerr", "ERROR: Could NOT export paid claims (START DATE IS NULL).");
						request.setAttribute("RESULT", "FAILED");
					}
				}
				else {
					request.setAttribute("msgerr", "ERROR: Starting date is required.");
					path = "export_claims.jsp";
				}
			}
			else {
				request.setAttribute("msgerr", "ERROR: Invalid option.");
				path = "export_claims.jsp";
			}
		}
		else
			path = "export_claims.jsp";

		return path;
	}

	private String delete_leading_zeros(String str) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '0')
				continue;
			else
				sb.append(str.charAt(i));
		}

		if (sb.length() == 0)
			sb.append("0");

		return sb.toString();
	}
}