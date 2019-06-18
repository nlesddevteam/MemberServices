package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import com.esdnl.personnel.jobs.dao.RequestToHireEmailManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
public class SubmitRequestToHireRequestHandler extends RequestHandlerImpl {
	public SubmitRequestToHireRequestHandler() {

		
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			//add new request to hire
			RequestToHireBean rthb = new RequestToHireBean();
			rthb.setJobTitle(form.get("job_title"));
			rthb.setPreviousIncumbent(form.get("previous_incumbent"));
			LocationBean lbean = LocationManager.getLocationBeanByDescription(form.get("location"));
			rthb.setWorkLocation(lbean.getLocationId());
			rthb.setLocationDescription(lbean.getLocationDescription());
			
			SimpleDateFormat inSDF = new SimpleDateFormat("MM/dd/yyyy");
			if (!StringUtils.isEmpty(form.get("date_vacated"))){
				rthb.setDateVacated(inSDF.parse(form.get("date_vacated")));
			}else{
				rthb.setDateVacated(null);
			}
			rthb.setPositionType(form.getInt("position_type"));
			rthb.setPositionSalary(form.get("position_salary"));
			if (!StringUtils.isEmpty(form.get("start_date"))){
				rthb.setStartDate(inSDF.parse(form.get("start_date")));
			}else{
				rthb.setStartDate(null);
			}
			if (!StringUtils.isEmpty(form.get("end_date"))){
				rthb.setEndDate(inSDF.parse(form.get("end_date")));
			}else{
				rthb.setEndDate(null);
			}
			if(form.get("supervisor").equals("SELECT YEAR")){
				rthb.setSupervisor(-1);
			}else{
				rthb.setSupervisor(form.getInt("supervisor"));
			}
			rthb.setDivision(form.getInt("division"));
			rthb.setComments(form.get("comments"));
			rthb.setRequestBy(Integer.toString(usr.getPersonnel().getPersonnelID()));
			rthb.setStatus(RequestToHireStatus.SUBMITTED);
			rthb.setPositionName(form.getInt("position_name"));
			rthb.setPositionTerm(form.getInt("position_term"));
			rthb.setPositionHours(form.get("position_hours"));
			rthb.setRequestType(form.get("request_type"));
			if(form.exists("shift_diff")){
				rthb.setShiftDiff(1);
			}else{
				rthb.setShiftDiff(0);
			}
			if(form.getInt("rid") == -1){
				RequestToHireManager.addRequestToHireBean(rthb);
				request.setAttribute("msg", "Request submitted");
				//send email for approval/submitted
				RequestToHireEmailManager.sendRequestToHireEmail(RequestToHireManager.getRequestToHireById(rthb.getId()));
			}else{
				rthb.setId(form.getInt("rid"));
				RequestToHireManager.updateRequestToHireBean(rthb);
				request.setAttribute("msg", "Request updated");
			}
			
			
			
			
			
			path = "addRequestToHire.html?rid=" + rthb.getId();	
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}
		return path;
	}
}
