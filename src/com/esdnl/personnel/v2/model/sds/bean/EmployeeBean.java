package com.esdnl.personnel.v2.model.sds.bean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.v2.model.availability.bean.EmployeeAvailabilityBean;
import com.esdnl.personnel.v2.model.recognition.bean.IEntity;
import com.esdnl.personnel.v2.model.sds.bean.EmployeePositionBean.ActivityCode;
import com.esdnl.personnel.v2.model.sds.constant.PositionConstant;
import com.esdnl.util.StringUtils;

public class EmployeeBean implements IEntity {

    private String empId;
    private String SIN;
    private String lastName;
    private String firstName;
    private String previousName;
    private String title;
    private String gender;
    private String address1;
    private String address2;
    private String city;
    private String postalCode;
    private String province;
    private String phone;
    private String alternatePhone;
    private String email;
    private String status;
    private Date seniorityDate;
    private Date boardStartDate;
    private String schoolYear;
    private PositionConstant position;
    private String tenur;
    private int tenur_code;
    private String location_prefs;
    private double FTE;
    private String positionDescription;

    private EmployeeAvailabilityBean current_availability;

    private Map<EmployeeSeniorityBean.Union, EmployeeSeniorityBean> seniority;
    private Map<String, List<EmployeePositionBean>> positions;

    private Comparator<EmployeePositionBean> positionComparator = (EmployeePositionBean p1,
	    EmployeePositionBean p2) -> {
	if ((p1.getEndDate() != null) && (p2.getEndDate() != null)
		&& (p1.getEndDate().compareTo(p2.getEndDate()) != 0)) {
	    return p1.getEndDate().compareTo(p2.getEndDate()) * -1;
	} else if ((p1.getStartDate() != null) && (p2.getStartDate() != null)
		&& (p1.getStartDate().compareTo(p2.getStartDate()) != 0)) {
	    return p1.getStartDate().compareTo(p2.getStartDate());
	} else {
	    return p1.getSchoolYear().compareTo(p2.getSchoolYear());
	}
    };

    public EmployeeBean() {

	this.email = null;
	this.SIN = null;
	this.lastName = null;
	this.firstName = null;
	this.previousName = null;
	this.title = null;
	this.gender = null;
	this.address1 = null;
	this.address2 = null;
	this.city = null;
	this.postalCode = null;
	this.province = null;
	this.phone = null;
	this.alternatePhone = null;
	this.email = null;
	this.status = null;
	this.seniorityDate = null;
	this.boardStartDate = null;
	this.schoolYear = null;
	this.position = null;
	this.tenur = null;
	this.tenur_code = 999;
	this.location_prefs = null;
	this.current_availability = null;
	this.seniority = new HashMap<>();
	this.FTE = 0;
	this.positionDescription = null;
	this.positions = new HashMap<>();
    }

    public String getEmpId() {

	return empId;
    }

    public void setEmpId(String newEmpId) {

	empId = newEmpId;
    }

    public String getSIN() {

	return SIN;
    }

    public void setSIN(String newSIN) {

	SIN = newSIN;
    }

    public String getLastName() {

	return lastName;
    }

    public void setLastName(String newLastName) {

	lastName = newLastName;
    }

    public String getFirstName() {

	return firstName;
    }

    public void setFirstName(String newFirstName) {

	firstName = newFirstName;
    }

    public String getPreviousName() {

	return previousName;
    }

    public void setPreviousName(String newPreviousName) {

	previousName = newPreviousName;
    }

    public String getTitle() {

	return title;
    }

    public void setTitle(String newTitle) {

	title = newTitle;
    }

    public String getGender() {

	return gender;
    }

    public void setGender(String newGender) {

	gender = newGender;
    }

    public String getAddress1() {

	return address1;
    }

    public void setAddress1(String newAddress1) {

	address1 = newAddress1;
    }

    public String getAddress2() {

	return address2;
    }

    public void setAddress2(String newAddress2) {

	address2 = newAddress2;
    }

    public String getCity() {

	return city;
    }

    public void setCity(String newCity) {

	city = newCity;
    }

    public String getPostalCode() {

	return postalCode;
    }

    public void setPostalCode(String newPostalCode) {

	postalCode = newPostalCode;
    }

    public String getProvince() {

	return province;
    }

    public void setProvince(String newProvince) {

	province = newProvince;
    }

    public String getPhone() {

	return phone;
    }

    public void setPhone(String newPhone) {

	phone = newPhone;
    }

    public String getAlternatePhone() {

	return alternatePhone;
    }

    public void setAlternatePhone(String newAlternatePhone) {

	alternatePhone = newAlternatePhone;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String newEmail) {

	email = newEmail;
    }

    public String getStatus() {

	return status;
    }

    public void setStatus(String newStatus) {

	status = newStatus;
    }

    public Date getSeniorityDate() {

	return seniorityDate;
    }

    public void setSeniorityDate(Date newSeniorityDate) {

	seniorityDate = newSeniorityDate;
    }

    public Date getBoardStartDate() {

	return boardStartDate;
    }

    public void setBoardStartDate(Date newBoardStartDate) {

	boardStartDate = newBoardStartDate;
    }

    public String getSchoolYear() {

	return schoolYear;
    }

    public void setSchoolYear(String newSchoolYear) {

	schoolYear = newSchoolYear;
    }

    public PositionConstant getPosition() {

	return position;
    }

    public void setPosition(PositionConstant newPosition) {

	position = newPosition;
    }

    public String getTenur() {

	return tenur;
    }

    public void setTenur(String tenur) {

	this.tenur = tenur;
    }

    public int getTenurCode() {

	return tenur_code;
    }

    public void setTenurCode(int newTenurCode) {

	this.tenur_code = newTenurCode;
    }

    public String getLocationPreferences() {

	return this.location_prefs;
    }

    public void setLocationPreferences(String location_prefs) {

	this.location_prefs = location_prefs;
    }

    public String getFullnameReverse() {

	return this.lastName + ((this.previousName != null) ? " (" + this.previousName + ")" : "") + ", "
		+ this.firstName;
    }

    public EmployeeAvailabilityBean getCurrentAvailability() {

	return this.current_availability;
    }

    public void setCurrentAvailability(EmployeeAvailabilityBean current_availability) {

	this.current_availability = current_availability;
    }

    public EmployeeSeniorityBean getSeniority(EmployeeSeniorityBean.Union union) {

	return seniority.get(union);
    }

    public EmployeeSeniorityBean getSeniority() {

	if (seniority.size() < 1)
	    return null;

	return this.seniority.values().stream().filter(s -> !s.getUnion().equals(EmployeeSeniorityBean.Union.NLTA))
		.findFirst().orElse(null);
    }


    public EmployeeSeniorityBean getSenioritySupport() {

	if (seniority.size() < 1)
	    return null;

	return this.seniority.values().stream().findFirst().orElse(null);
    }

    public void addSeniority(EmployeeSeniorityBean seniority) {

	if (seniority != null) {
	    this.seniority.put(seniority.getUnion(), seniority);
	}
    }

    public double getFTE() {

	return FTE;
    }

    public void setFTE(double fTE) {

	FTE = fTE;
    }

    public String getPositionDescription() {

	return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {

	this.positionDescription = positionDescription;
    }

    public void addPosition(EmployeePositionBean position) {

	if (position != null) {
	    if (!positions.containsKey(position.getSchoolYear())) {
		positions.put(position.getSchoolYear(), new ArrayList<>());
		positions.get(position.getSchoolYear()).add(position);
	    } else {
		boolean exists = this.positions.get(position.getSchoolYear()).stream()
			.anyMatch(p -> p.equals(position));

		if (!exists) {
		    positions.get(position.getSchoolYear()).add(position);
		}
	    }
	}
    }

    public Map<String, List<EmployeePositionBean>> getPositions() {

	return this.positions;
    }

    public List<EmployeePositionBean> getPositions(String schoolYear) {

	return this.positions.get(schoolYear);
    }

    public List<EmployeePositionBean> getPositions(LocationBean location) {

	return getPositions().values().stream().flatMap(List::stream)
		.filter(p -> p.getLocation().trim().equalsIgnoreCase(location.getLocationDescription()))
		.sorted(positionComparator).collect(Collectors.toList());
    }

    public List<EmployeePositionBean> getPositions(String schoolYear, LocationBean location) {

	List<EmployeePositionBean> positions = getPositions(schoolYear);

	if (positions != null && positions.size() > 0) {
	    positions = positions.stream()
		    .filter(p -> p.getLocation().trim().equalsIgnoreCase(location.getLocationDescription()))
		    .sorted(positionComparator).collect(Collectors.toList());
	}

	return positions;
    }

    public List<EmployeePositionBean> getCurrentPositions() {

	List<EmployeePositionBean> tmp = getPositions(
		com.esdnl.personnel.v2.utils.StringUtils.getSchoolYear(new Date()));

	if (tmp == null) {
	    tmp = getPositions(com.esdnl.personnel.v2.utils.StringUtils.getPreviousSchoolYear(new Date()));
	}
	// still null - return empty array
	if (tmp == null) {
	    tmp = new ArrayList<>();
	}

	return tmp;
    }

    public List<EmployeePositionBean> getCurrentPositions(LocationBean location) {

	List<EmployeePositionBean> positions = null;

	if (location == null) {
	    positions = getCurrentPositions();
	} else {
	    positions = getCurrentPositions().stream()
		    .filter(p -> p.getLocation().trim().equalsIgnoreCase(location.getLocationDescription()))
		    .sorted(positionComparator).collect(Collectors.toList());
	}

	return positions;
    }

    public List<EmployeePositionBean> getPositions(TeacherAllocationBean allocation) {

	List<EmployeePositionBean> positions = null;

	if (allocation == null) {
	    positions = getCurrentPositions();
	} else if (this.positions.containsKey(allocation.getSchoolYear())) {
	    positions = getPositions(allocation.getSchoolYear(), allocation.getLocation());
	} else {
	    positions = getCurrentPositions(allocation.getLocation());
	}

	if ((positions == null || positions.size() < 1)) {
	    positions = getPositions(allocation.getLocation());
	}

	return positions;
    }

    public List<EmployeePositionBean> getReplacementPositions() {

	return getPositions().values().stream().flatMap(List::stream).filter(p -> p.isTerm() && !p.isSubstitute())
		.sorted(positionComparator).collect(Collectors.toList());
    }

    public boolean hasReplacementPositions() {

	return getReplacementPositions().size() > 0;
    }

    public List<EmployeePositionBean> getPermanentPositions() {

	return getPositions().values().stream().flatMap(List::stream).filter(p -> p.isPerm()).sorted(positionComparator)
		.collect(Collectors.toList());
    }

    public boolean hasPermanentPositions() {

	return getPositions().values().stream().flatMap(List::stream).anyMatch(p -> p.isPerm());
    }

    public boolean isRetired() {

	return getPositions().values().stream().flatMap(List::stream)
		.anyMatch(p -> p.getActivityCode().equals(ActivityCode.RETIRED));
    }

    public boolean isResigned() {

	return getPositions().values().stream().flatMap(List::stream)
		.anyMatch(p -> p.getActivityCode().equals(ActivityCode.RESIGNED));
    }

    public boolean isTerminated() {

	return getPositions().values().stream().flatMap(List::stream)
		.anyMatch(p -> p.getActivityCode().equals(ActivityCode.TR));
    }

    public boolean isPermanent() {
	return hasPermanentPositions() && !isRetired() && !isResigned();
    }

    public boolean is(ApplicantProfileBean profile) {

	return org.apache.commons.lang.StringUtils.equals(this.SIN, profile.getSIN())
		|| (org.apache.commons.lang.StringUtils.isNotBlank(profile.getSIN2())
			&& org.apache.commons.lang.StringUtils.equals(this.SIN, profile.getSIN2()))
		|| (org.apache.commons.lang.StringUtils.isNotBlank(this.email) && org.apache.commons.lang.StringUtils
			.equals(this.email.toLowerCase().trim(), profile.getEmail().toLowerCase().trim()));
    }

    public String toXML() {

	DecimalFormat df = new DecimalFormat("0.00");
	StringBuffer buf = new StringBuffer();

	buf.append("<EMPLOYEE-BEAN employee-id=\"" + this.empId.trim() + "\" first-name=\""
		+ StringUtils.encodeHTML2(this.firstName) + "\" last-name=\"" + StringUtils.encodeHTML2(this.lastName)
		+ "\""
		+ ((this.previousName != null)
			? (" previous-name=\"" + StringUtils.encodeHTML2(this.previousName) + "\"")
			: "")
		+ " fte=\"" + df.format(this.FTE) + "\" tenure=\"" + this.tenur + "\" position-description=\""
		+ StringUtils.encodeHTML2(this.getPositionDescription()) + "\" />");

	return buf.toString();
    }
    public String viewAllSeniorityTeach() {
    	StringBuilder sb = new StringBuilder();
    	NumberFormat nf = new DecimalFormat("0.00");
    	if((this.seniority.size() > 0 )){
    		for (Map.Entry<EmployeeSeniorityBean.Union, EmployeeSeniorityBean> entry : this.seniority.entrySet()) {
    			if(entry.getKey() == EmployeeSeniorityBean.Union.NLTA || entry.getKey() == EmployeeSeniorityBean.Union.NLTA_TLA ) {
    				if(entry.getKey() == EmployeeSeniorityBean.Union.NLTA) {
	    				sb.append("NLTA PROVINCIAL: " + nf.format(entry.getValue().getSeniorityValue1()) + " yrs<br />");
	    				sb.append("OUT OF PROVINCE: " + nf.format(entry.getValue().getSeniorityValue2()) + " yrs<br />");
	    			}else {
	    				sb.append(entry.getKey().getUnionName() + ": " + nf.format(entry.getValue().getSeniorityValue1()) + " yrs<br />");
	    			}
    			}
    			
    		}
    	
		} 
		else {
			sb.append("N/A");
	 	}
    	
    	return sb.toString();
    }
    public String viewAllSenioritySupport() {
    	StringBuilder sb = new StringBuilder();
    	NumberFormat nf = new DecimalFormat("0.00");
    	if((this.seniority.size() > 0 )){
    		sb.append("<table class='table table-striped table-condensed' style='font-size:12px;'>");
    		for (Map.Entry<EmployeeSeniorityBean.Union, EmployeeSeniorityBean> entry : this.seniority.entrySet()) {
    			//support staff emp do not show nlta/tla
    			
    			if(entry.getKey() != EmployeeSeniorityBean.Union.NLTA || entry.getKey() != EmployeeSeniorityBean.Union.NLTA_TLA ) {
    				switch (entry.getKey().getUnionCode()) {
    				case "01":
    				case "03":
    				case "04":
    				case "10":
    				case "11":
    					//check both dates
    					if(entry.getValue().getSeniorityDate1() != null) {
    						sb.append("<tr><td class='tableResult'>" + entry.getKey().getUnionName() +": Seniority Date - " + entry.getValue().getSeniorityDate1Formatted() + "</td></tr>");
    					}else if(entry.getValue().getSeniorityDate2() != null) {
    						sb.append("<tr><td class='tableResult'>" + entry.getKey().getUnionName() +": Seniority Date - " + entry.getValue().getSeniorityDate2Formatted()+ "</td></tr>");
    					}else {
    						sb.append("<tr><td class='tableResult'>" + entry.getKey().getUnionName() +": Seniority Date - N/A</td></tr>");
    					}
    					break;
    				case "02":
    				case "05":
    				case "06":
    				case "07":
    				case "08":
    				case "09":
    						if(entry.getValue().getSeniorityTotal() > 0) {
    							sb.append("<tr><td class='tableResult'>" + entry.getKey().getUnionName() +":  " + nf.format(entry.getValue().getSeniorityTotal())+ " days worked</td></tr>");
    						}else {
    							sb.append("<tr><td class='tableResult'>" + entry.getKey().getUnionName() +":  N/A</td></tr>");
    						}
    				}
    			}
    		}
    		sb.append("</table>");
    	}else {
    		sb.append("<table class='table table-striped table-condensed' style='font-size:12px;'>");
    		sb.append("<tr><td class='tableTitle'>Seniority:</td></tr>");
    		sb.append("<tr><td class='tableResult'>N/A</td></tr>");
    		sb.append("</table>");
    	}
    	System.out.println(sb.toString());
    	return sb.toString();
    }

}