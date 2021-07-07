package com.esdnl.personnel.v2.model.sds.bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class EmployeePositionBean {

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

    public enum PositionType {

	UNKNOWN, LEAVE, REGULAR, REPLACEMENT;

	public static PositionType get(String pt) {

	    PositionType tmp = UNKNOWN;

	    for (PositionType tpt : values()) {
		if (StringUtils.equalsIgnoreCase(tpt.name(), pt)) {
		    tmp = tpt;
		    break;
		}
	    }

	    return tmp;
	}
    }

    public enum TenureType {

	UNKNOWN(false), CASU(false), TEMP(false), TERM(false), TENP1(true), TENP2(true), TENP3(true), TENTM(true),
	PR1TM(true), PR2TM(true), PR3TM(true), PROB1(true), PROB2(true), PROB3(true), PERM(true), TENUR(true);

	private boolean permType;

	private TenureType(boolean permType) {

	    this.permType = permType;
	}

	public boolean isPermType() {

	    return this.permType;
	}

	public static TenureType get(String tenure) {

	    TenureType tmp = UNKNOWN;

	    for (TenureType tt : values()) {
		if (tt.name().equalsIgnoreCase(tenure)) {
		    tmp = tt;
		    break;
		}
	    }

	    return tmp;
	}

	public static List<TenureType> getPermList() {

	    return Arrays.stream(values()).filter(t -> t.isPermType()).collect(Collectors.toList());
	}

	public static List<TenureType> getTermList() {

	    return Arrays.stream(TenureType.values()).filter(t -> !t.isPermType()).collect(Collectors.toList());
	}

	public static boolean isPerm(TenureType tt) {

	    return getPermList().contains(tt);
	}

	public static boolean isTerm(TenureType tt) {

	    return getTermList().contains(tt);
	}
    }

    public enum PositionCode {

	UNKNOWN("UNKNOWN", false, false), TLA("Teaching & Learning Assistant", false, true),
	TLA_5("Substitute teaching & Learning", false, true), TLAJP("TLA Jordan Principle Third Par", false, true);

	private String value;
	private boolean tlaPosition;
	private boolean teacherPosition;

	private PositionCode(String value, boolean teacherPosition, boolean tlaPosition) {

	    this.value = value;
	    this.teacherPosition = teacherPosition;
	    this.tlaPosition = tlaPosition;
	}

	public String getValue() {

	    return this.value;
	}

	public boolean isTlaPosition() {

	    return tlaPosition;
	}

	public boolean isTeacherPosition() {

	    return teacherPosition;
	}

	public static PositionCode get(String value) {

	    PositionCode tmp = UNKNOWN;

	    for (PositionCode pc : values()) {
		if (pc.getValue().equalsIgnoreCase(value)) {
		    tmp = pc;
		    break;
		}
	    }

	    return tmp;
	}

	public static List<PositionCode> getTLAList() {

	    return Arrays.stream(PositionCode.values()).filter(c -> c.isTlaPosition()).collect(Collectors.toList());
	}

	public static boolean isTLA(PositionCode pc) {

	    return getTLAList().contains(pc);
	}
    }

    public enum ActivityCode {

	NO_ACTIVITY("NO-ACTIVITY", "** No Activity **"), ACCOM("ACCOM", "Accomodation"),
	ADMIN_TENU("ADMIN TENU", "New Admin Tenure"), ADMIN_PROB("ADMIN-PROB", "Serving Admin Probation"),
	ADOPT("ADOPT", "Adoption - Unpaid"), ART_49_08("ART 49.08", "20 teaching, elig benefits/pen"),
	BOUNDARY_C("BOUNDARY C", "Regional Boundary Changes"), BUMP("BUMP", "Bumped into new Position"),
	BUMPED("BUMPED", "Bumped FROM a position"), CANCELLED("CANCELLED", "Another new contract accepted"),
	CASCAL("CASCAL", "Casual Call In"), CASTERM("CASTERM", "Casual in Term Position"),
	CONTRACTED("CONTRACTED", "contract term employee"), COVID_19N("COVID-19N", "New Allocations due to Covid"),
	COVID_19P("COVID-19P", "Perm Increase in Allocation"), COVID_19T("COVID-19T", "Term Increase in Allocation"),
	DATECHANGE("DATECHANGE", "Change in date"), DECEASED("DECEASED", "DECEASED"),
	DECREASE("DECREASE", "Position % Decrease"), DEFER("DEFER", "Deferred Salary Leave"),
	DEFERRED("DEFERRED", "Deferred Salary Leave"), DEPT_PROB("DEPT-PROB", "Department Head Probation"),
	DISPLACED("DISPLACED", "Displaced by senior employee"), DIST_POOL("DIST. POOL", "District Pool"),
	EASEBACK("EASEBACK", "EASEBACK SCHEDULE"), EEL("EEL", "WHSCC Extended Earnings Loss"),
	EMERG_SUPP("EMERG.SUPP", "Emergency Supply Teacher"), ERROR("ERROR", "Posted in Error"),
	EXALLOC("EXALLOC", "Extra Allocation to school"), FAMILY_ACC("FAMILY ACC", "Family Accommodation"),
	HR_DECREAS("HR DECREAS", "Hours decreased"), HR_INCRE("HR INCRE", "Increase in hours"),
	I_O_D("I.O.D.", "Injury on duty - Paid"), INCREASE("INCREASE", "Permanent Increase"),
	INCREASETM("INCREASETM", "TERM INCREASE"), IOD("IOD", "Injury on Duty"),
	JOBCODE("JOBCODE", "Change in Job Code"), JURY_DUTY("JURY DUTY", "JURY DUTY"),
	LOCATIONCH("LOCATIONCH", "Change in Location"), LTD("LTD", "Long Term Disability"),
	MAT("MAT", "Maternity - Unpaid"), MAT_PDSL("MAT-PDSL", "Switch from mat to paid sick"),
	MED_ACCOM("MED.ACCOM", "Medical Accommodation"), MERGE("MERGE", "Merger 2013"),
	MUTUAL_EX("MUTUAL.EX", "Mutual Exchange (Teachers) 46.01"), NEW_PROB("NEW PROB", "NEW PROBATION 2"),
	NEW_PROB1("NEW PROB1", "NEW POSITION PROBATION 1"), NEW_TENURE("NEW TENURE", "NEWLY TENURED IN SEPT"),
	NEWHIRE("NEWHIRE", "Newly Hired Employee"), NEWPOS("NEWPOS", "New Position"),
	NEWSUB("NEWSUB", "New Sub Position"), ON_LAYOFF("ON LAYOFF", "IN LAYOFF POOL"),
	PAID_LEAVE("PAID LEAVE", "Paid leave"), PARENTAL("PARENTAL", "Parental Leave"), PAT("PAT", "Paternity"),
	PD_ED("PD. ED.", "Education - Paid"), PD_S_L("PD. S.L.", "Long Term Sick - Paid"),
	PD_SUSP("PD. SUSP.", "Paid Suspension"), PERM("PERM", "Moved from Casual to Permanent"),
	PERM_TR_DC("PERM TR/DC", "Permanent Transfer w/ decrease"),
	PERM_TR_IN("PERM TR/IN", "Perm Transfer with Increase"), PERM_INC("PERM.INC.", "Permanent Increase"),
	PERM_TRANS("PERM.TRANS", "Permanent Transfer"), PROB_ADMIN("PROB ADMIN", "Probationary Administratior"),
	PSNEWTENUR("PSNEWTENUR", "New Program Specialist Tenure"), R_AND_R("R & R", "Redundant & Reassigned"),
	REASSIGNED("REASSIGNED", "REASSIGNED"), RECALL("RECALL", "Recall from Layoff Pool"),
	RECLASS("RECLASS", "Reclassified/Classified"), REDUCTION("REDUCTION", "Permanent Reduction (Hours)"),
	REDUNDANT("REDUNDANT", "Position Redundant"), REGHOUR("REGHOUR", "Return to regular hours"),
	REPL("REPL", "Replacement"), RESIGNED("RESIGNED", "RESIGNED"), RETIRED("RETIRED", "Retired From Position"),
	RETURN("RETURN", "RETURN"), RTN_REASSI("RTN-REASSI", "Return and Reassigned"),
	RTNREGULAR("RTNREGULAR", "Return to Regular Position"), SECOND("SECOND", "Secondment"),
	SETTLE("SETTLE", "Grievance Settlement"), SUCCESSFUL("SUCCESSFUL", "successful applicant"),
	TEMP("TEMP", "Temporary Appointment"), TERM("TERM", "Term - Extra Allocation"),
	TERM_INC("TERM INC.", "Term Increase"), TERMINC("TERM.INC.", "Term Increase"),
	TLA_SUB("TLA SUB", "New TLA Substitute"), TR("TR", "Terminated"), TRSE("TRSE", "Transfer - External"),
	TRSI("TRSI", "Transfer - Internal"), UNPD_ED("UNPD. ED.", "Education - Unpaid"),
	UNPD_S_L("UNPD. S.L.", "Long Term Sick - Unpaid"), UNPD_LEAVE("UNPD.LEAVE", "Long Term - Unpaid"),
	UNPD_SUSP("UNPD.SUSP.", "Unpaid Suspension"), VACANTPROB("VACANTPROB", "Vacant-Prob Period served"),
	VACANTTM("VACANTTM", "Vacant Term"), WHSCC("WHSCC", "Worker's Compensation");

	private String code;
	private String desc;

	private ActivityCode(String code, String desc) {
	    this.code = code;
	    this.desc = desc;
	}

	public String getCode() {
	    return code;
	}

	public String getDesc() {
	    return desc;
	}

	public static ActivityCode get(String value) {
	    ActivityCode code = ActivityCode.NO_ACTIVITY;

	    if (StringUtils.isNotBlank(value)) {
		for (ActivityCode ac : ActivityCode.values()) {
		    if (StringUtils.equalsIgnoreCase(code.getCode(), value.trim())) {
			code = ac;
			break;
		    }
		}
	    }

	    return code;
	}
    }

    private EmployeeBean employee;
    private String schoolYear;
    private String name;
    private String empId;
    private String position;
    private PositionType positionType;
    private PositionCode positionCode;
    private ActivityCode activityCode;
    private Date startDate;
    private Date endDate;
    private String sin;
    private String location;
    private TenureType tenure;
    private double fteHours;

    public EmployeePositionBean() {

	this.employee = null;
	this.schoolYear = "";
	this.name = "";
	this.empId = "";
	this.position = "";
	this.positionType = PositionType.UNKNOWN;
	this.positionCode = PositionCode.UNKNOWN;
	this.activityCode = ActivityCode.NO_ACTIVITY;
	this.startDate = null;
	this.endDate = null;
	this.sin = "";
	this.location = "";
	this.tenure = TenureType.UNKNOWN;
	this.fteHours = 0.0;
    }

    public EmployeeBean getEmployee() {

	return employee;
    }

    public void setEmployee(EmployeeBean employee) {

	this.employee = employee;
    }

    public String getSchoolYear() {

	return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {

	this.schoolYear = schoolYear;
    }

    public String getName() {

	return name;
    }

    public void setName(String name) {

	this.name = name;
    }

    public String getEmpId() {

	return empId;
    }

    public void setEmpId(String empId) {

	this.empId = empId;
    }

    public String getPosition() {

	return position;
    }

    public void setPosition(String position) {

	this.position = position;
    }

    public boolean isSubstitute() {

	return StringUtils.isNotBlank(this.position) && StringUtils.contains(this.position.toLowerCase(), "substitute");
    }

    public PositionType getPositionType() {

	return positionType;
    }

    public void setPositionType(PositionType positionType) {

	this.positionType = positionType;
    }

    public boolean isLeave() {

	return EmployeePositionBean.PositionType.LEAVE.equals(getPositionType());
    }

    public boolean isReplacement() {

	return EmployeePositionBean.PositionType.REPLACEMENT.equals(getPositionType());
    }

    public boolean isRegular() {

	return EmployeePositionBean.PositionType.REGULAR.equals(getPositionType());
    }

    public PositionCode getPositionCode() {

	return positionCode;
    }

    public void setPositionCode(PositionCode positionCode) {

	this.positionCode = positionCode;
    }

    public ActivityCode getActivityCode() {
	return activityCode;
    }

    public void setActivityCode(ActivityCode activityCode) {
	this.activityCode = activityCode;
    }

    public Date getStartDate() {

	return startDate;
    }

    public String getStartDateFormatted() {

	return this.startDate != null ? sdf.format(this.startDate) : "UNKNOWN";
    }

    public void setStartDate(Date startDate) {

	this.startDate = startDate;
    }

    public Date getEndDate() {

	return endDate;
    }

    public String getEndDateFormatted() {

	return this.endDate != null ? sdf.format(this.endDate) : "UNKNOWN";
    }

    public void setEndDate(Date endDate) {

	this.endDate = endDate;
    }

    public String getSin() {

	return sin;
    }

    public void setSin(String sin) {

	this.sin = sin;
    }

    public String getLocation() {

	return location;
    }

    public void setLocation(String location) {

	this.location = location;
    }

    public TenureType getTenure() {

	return tenure;
    }

    public void setTenure(TenureType tenure) {

	this.tenure = tenure;
    }

    public boolean isPerm() {

	return TenureType.isPerm(this.tenure);
    }

    public boolean isTerm() {

	return TenureType.isTerm(this.tenure);
    }

    public double getFteHours() {

	return fteHours;
    }

    public void setFteHours(double fteHours) {

	this.fteHours = fteHours;
    }

    public boolean isError() {

	return (this.startDate == null) // || (this.endDate == null) -- open ended contracts do exist and are valid
		|| (this.endDate != null && this.startDate != null && this.endDate.before(this.startDate))
		|| (this.startDate != null && !com.esdnl.personnel.v2.utils.StringUtils.getSchoolYear(this.startDate)
			.equals(this.schoolYear));
    }

    @Override
    public String toString() {

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	String tmp = schoolYear + ": " + location + " - " + position + " " + fteHours
		+ (fteHours <= 1 ? " FTE " : " Hrs ") + "(" + tenure.name() + ")";

	if (this.startDate != null) {
	    tmp += " started " + sdf.format(this.startDate);
	}

	if (this.endDate != null) {
	    tmp += " ending " + sdf.format(this.endDate);
	}

	tmp += " (" + (isLeave() ? "<b>" : "") + this.getPositionType() + (isLeave() ? "</b>" : "") + ")";

	return tmp;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((empId == null) ? 0 : empId.hashCode());
	result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
	long temp;
	temp = Double.doubleToLongBits(fteHours);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((location == null) ? 0 : location.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((position == null) ? 0 : position.hashCode());
	result = prime * result + ((positionType == null) ? 0 : positionType.hashCode());
	result = prime * result + ((schoolYear == null) ? 0 : schoolYear.hashCode());
	result = prime * result + ((sin == null) ? 0 : sin.hashCode());
	result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
	result = prime * result + ((tenure == null) ? 0 : tenure.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EmployeePositionBean other = (EmployeePositionBean) obj;
	if (empId == null) {
	    if (other.empId != null)
		return false;
	} else if (!empId.equals(other.empId))
	    return false;
	if (endDate == null) {
	    if (other.endDate != null)
		return false;
	} else if (!endDate.equals(other.endDate))
	    return false;
	if (Double.doubleToLongBits(fteHours) != Double.doubleToLongBits(other.fteHours))
	    return false;
	if (location == null) {
	    if (other.location != null)
		return false;
	} else if (!location.equals(other.location))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (position == null) {
	    if (other.position != null)
		return false;
	} else if (!position.equals(other.position))
	    return false;
	if (positionType != other.positionType)
	    return false;
	if (schoolYear == null) {
	    if (other.schoolYear != null)
		return false;
	} else if (!schoolYear.equals(other.schoolYear))
	    return false;
	if (sin == null) {
	    if (other.sin != null)
		return false;
	} else if (!sin.equals(other.sin))
	    return false;
	if (startDate == null) {
	    if (other.startDate != null)
		return false;
	} else if (!startDate.equals(other.startDate))
	    return false;
	if (tenure == null) {
	    if (other.tenure != null)
		return false;
	} else if (!tenure.equals(other.tenure))
	    return false;
	return true;
    }

}
