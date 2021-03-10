package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.awsd.security.User;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimItem;
import com.awsd.travel.TravelClaimItemsDB;
import com.awsd.travel.bean.TravelClaimFileBean;
import com.awsd.travel.dao.TravelClaimFileManager;
import com.esdnl.servlet.RequestHandlerImpl;

import javazoom.upload.UploadFile;

public class UpdateTravelClaimItemAjaxRequestHandler extends RequestHandlerImpl {

	public UpdateTravelClaimItemAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		TravelClaimItem item = null;
		SimpleDateFormat sdf = null;
		String op = "";
		int id = -1;
		Integer itemid = -1;
		boolean check = false;
		Calendar date_chk = null;
		boolean iserror = false;
		String errormessage = "";
		try {
			id = form.getInt("id");
			itemid = form.getInt("itemid");
		}
		catch (NumberFormatException e) {
			id = -1;
		}
		if (id < 1) {
			//throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR CLAIM ITEM ADD OPERATION.  >>>>>");
			//send xml back with error
			iserror = true;
			errormessage = "CLAIM ID IS REQUIRED FOR CLAIM ITEM ADD OPERATION";
		}
		else {
			claim = TravelClaimDB.getClaim(id);
			session = request.getSession(false);
			usr = (User) session.getAttribute("usr");
			if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
				iserror = true;
				errormessage = "You do not have permission to add an item to this travel claim";
			}
			else {
				request.setAttribute("TRAVELCLAIM", claim);
				op = form.get("op");
				if (op != null) {
					if (op.equalsIgnoreCase("CONFIRM")) {
						sdf = new SimpleDateFormat("MM/dd/yyyy");
						try {
							item = new TravelClaimItem(sdf.parse(form.get("item_date")), form.get(
									"item_desc"), form.getInt("item_kms"), form.getDouble("item_meals"), form.getDouble("item_lodging"), form.getDouble("item_other")
									, form.get("item_departure_time"), form.get("item_return_time"));
						}
						catch (ParseException e) {
							System.err.println(e);
							iserror = true;
							errormessage = e.getMessage();
							item = null;
						}
						if (item != null) {
							date_chk = Calendar.getInstance();
							date_chk.setTime(item.getItemDate());
							// check item date
							if ((!claim.getFiscalYear().equalsIgnoreCase(Utils.getSchoolYear(date_chk)))
									|| (claim.getFiscalMonth() != date_chk.get(Calendar.MONTH))) {
								iserror = true;
								errormessage = "Claim item date is invalid.<br>Only items for "
										+ Utils.getMonthString(claim.getFiscalMonth()) + " "
										+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + " are valid.";
							}
							else if (Utils.compareCurrentDate(item.getItemDate()) >= 1) {
								iserror = true;
								errormessage = "Claim item date is in the future.<br>Only items on or before "
										+ (new SimpleDateFormat("MMMM dd, yyyy").format((Calendar.getInstance()).getTime()))
										+ " are valid.";
							}
							else if ((item.getItemKMS() == 0) && (item.getItemMeals() == 0) && (item.getItemLodging() == 0)
									&& (item.getItemOther() == 0)) {
								iserror = true;
								errormessage = "All claim item values are zero (0).<br> Please enter one or more values greater then zero (0).";
							}
							else {
								TravelClaimItem item_old = TravelClaimItemsDB.getClaimItem(itemid);
								int iid = TravelClaimItemsDB.editClaimItemAtt(claim, item_old, item);
								TravelClaimFileManager.updateTravelClaimFilesById(iid, item_old.getItemID());
								check=true;
								if(form.getInt("filecount") > 0) {
									String[] fdesc = form.get("filedesc").split(",");
									for(int x=1; x <=form.getInt("filecount");x++) {
										String filename = "file" + x;
										UploadFile f = form.getUploadFile(filename);
										try {
											TravelClaimFileBean tbean = new TravelClaimFileBean();
											tbean.setFilePath(save_file(filename, "/Travel/Attachments/"));
											tbean.setClaimId(claim.getClaimID());
											tbean.setItemId(iid);
											tbean.setFileNotes(fdesc[x-1]);
											TravelClaimFileManager.addTravelClaimFile(tbean);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
								}
								System.out.println(form.get("deletedfiles"));
								//finally we check to see if there are files to delete
								if(form.get("deletedfiles") != null && !form.get("deletedfiles").equals("")) {
									String[] files = form.get("deletedfiles").split(",");
									for(String s : files) {
										TravelClaimFileBean tcbean = TravelClaimFileManager.getTravelClaimFileById(Integer.parseInt(s));
										delete_file(tcbean.getFilePath(),"/Travel/Attachments/");
										TravelClaimFileManager.deleteTravelClaimFileById(tcbean.getId());
									}
								}
								
								if (check) {
									request.setAttribute("RESULT", "SUCCESS");
									request.setAttribute("msg", "Claim item updated successfully.");
								}
								else {
									iserror = true;
									errormessage = "Claim item could not be updated.";
								}
							}
						}
						else {
							iserror = true;
							errormessage = "Claim item could not be updated.";
						}
					}
					else {
						iserror = true;
						errormessage = "Invalid operation.";
					}
				}
			}
		}
		if (iserror) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
			sb.append("</TRAVELCLAIM>");
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</TRAVELCLAIM>");
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}
}