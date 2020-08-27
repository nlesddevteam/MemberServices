package com.nlesd.bcs.constants;

public class EntryTypeConstant {
	private int value;
	private String desc;
	public static final EntryTypeConstant CONTRACTORSUBMITTED= new EntryTypeConstant(1, "New Contractor Submitted");
	public static final EntryTypeConstant CONTRACTORAPPROVED= new EntryTypeConstant(2, "Contractor Approved");
	public static final EntryTypeConstant CONTRACTORSECURITYADDED= new EntryTypeConstant(3, "Contractor Security Record Added");
	public static final EntryTypeConstant CONTRACTORREJECTED= new EntryTypeConstant(4, "Contractor Rejected");
	public static final EntryTypeConstant CONTRACTORCONFIRMED= new EntryTypeConstant(5, "Contractor Confirmed");
	public static final EntryTypeConstant CONTRACTORCHANGEPASSWORD= new EntryTypeConstant(6, "Contractor Changed Password");
	public static final EntryTypeConstant CONTRACTORLOGIN= new EntryTypeConstant(7, "Contractor Login");
	public static final EntryTypeConstant CONTRACTORCONTACTUPDATED= new EntryTypeConstant(8, "Contractor Contact Info Updated");
	public static final EntryTypeConstant CONTRACTORSECUIRTYUPDATED= new EntryTypeConstant(9, "Contractor Security Info Updated");
	public static final EntryTypeConstant CONTRACTORCOMPANYUPDATED= new EntryTypeConstant(10, "Contractor Company Info Updated");
	public static final EntryTypeConstant CONTRACTORVEHICLEADDED= new EntryTypeConstant(11, "Contractor Vehicle Added");
	public static final EntryTypeConstant CONTRACTORVEHICLEUPDATED= new EntryTypeConstant(12, "Contractor Vehicle Updated");
	public static final EntryTypeConstant CONTRACTORVEHICLEDELETED= new EntryTypeConstant(13, "Contractor Vehicle Deleted");
	public static final EntryTypeConstant CONTRACTORVEHICLEDOCADDED= new EntryTypeConstant(14, "Contractor Vehicle Document Added");
	public static final EntryTypeConstant CONTRACTORVEHICLEDOCDELETED= new EntryTypeConstant(15, "Contractor Vehicle Document Deleted");
	public static final EntryTypeConstant CONTRACTOREMPLOYEEADDED= new EntryTypeConstant(16, "Contractor Employee Added");
	public static final EntryTypeConstant CONTRACTOREMPLOYEEDELETED= new EntryTypeConstant(17, "Contractor Employee Deleted");
	public static final EntryTypeConstant CONTRACTOREMPLOYEEUPDATED= new EntryTypeConstant(18, "Contractor Employee Updated");
	public static final EntryTypeConstant CONTRACTOREMPLOYEEAPPROVED= new EntryTypeConstant(19, "Contractor Employee Approved");
	public static final EntryTypeConstant CONTRACTOREMPLOYEEREJECTED= new EntryTypeConstant(20, "Contractor Employee Rejected");
	public static final EntryTypeConstant CONTRACTORVEHICLEAPPROVED= new EntryTypeConstant(21, "Contractor Vehicle Approved");
	public static final EntryTypeConstant CONTRACTORVEHICLEREJECTED= new EntryTypeConstant(22, "Contractor Vehicle Rejected");
	public static final EntryTypeConstant CONTRACTOREMPLOYEESUSPENDED= new EntryTypeConstant(23, "Contractor Employee Suspended");
	public static final EntryTypeConstant CONTRACTORSUSPENDED= new EntryTypeConstant(24, "Contractor Suspended");
	public static final EntryTypeConstant CONTRACTORUNSUSPENDED= new EntryTypeConstant(25, "Contractor Unsuspended");
	public static final EntryTypeConstant CONTRACTORDOCADDED= new EntryTypeConstant(26, "Contractor Document Added");
	public static final EntryTypeConstant CONTRACTORDOCDELETED= new EntryTypeConstant(27, "Contractor Document Deleted");
	public static final EntryTypeConstant SYSTEMDOCADDED= new EntryTypeConstant(28, "System Document Added");
	public static final EntryTypeConstant SYSTEMDOCDELETED= new EntryTypeConstant(29, "System Document Deleted");
	public static final EntryTypeConstant SYSTEMDOCUPDATED= new EntryTypeConstant(30, "System Document Updated");
	public static final EntryTypeConstant ADDNEWCONTRACT= new EntryTypeConstant(31, "Contract Added");
	public static final EntryTypeConstant UPDATECONTRACT= new EntryTypeConstant(32, "Contract Updated");
	public static final EntryTypeConstant DELETECONTRACT= new EntryTypeConstant(33, "Contract Deleted");
	public static final EntryTypeConstant ADDNEWROUTE= new EntryTypeConstant(34, "Route Added");
	public static final EntryTypeConstant UPDATEROUTE= new EntryTypeConstant(35, "Route Updated");
	public static final EntryTypeConstant DELETEROUTE= new EntryTypeConstant(36, "Route Deleted");
	public static final EntryTypeConstant ROUTEADDEDTOCONTRACT= new EntryTypeConstant(37, "Route Added to Contract");
	public static final EntryTypeConstant ROUTEREMOVEDFROMCONTRACT= new EntryTypeConstant(38, "Route Removed from Contract");
	public static final EntryTypeConstant CONTRACTAWARDED= new EntryTypeConstant(39, "Contract Awarded");
	public static final EntryTypeConstant CONTRACTSUSPENDED= new EntryTypeConstant(40, "Contract Suspended");
	public static final EntryTypeConstant CONTRACTCANCELLED= new EntryTypeConstant(41, "Contract Cancelled");
	public static final EntryTypeConstant ADDDRIVERTOROUTE= new EntryTypeConstant(42, "Add Driver to Route");
	public static final EntryTypeConstant ADDVEHICLETOROUTE= new EntryTypeConstant(43, "Add Vehicle to Route");
	public static final EntryTypeConstant REMOVEDRIVERTOROUTE= new EntryTypeConstant(44, "Remove Driver to Route");
	public static final EntryTypeConstant REMOVEVEHICLETOROUTE= new EntryTypeConstant(45, "Remove Vehicle to Route");
	public static final EntryTypeConstant CONTRACTORVEHICLESUSPENDED= new EntryTypeConstant(46, "Contractor Vehicle Suspended");
	public static final EntryTypeConstant EMPLOYEETRAININGADDED= new EntryTypeConstant(47, "Employee Training Added");
	public static final EntryTypeConstant EMPLOYEETRAININGDELETED= new EntryTypeConstant(48, "Employee Training Deleted");
	public static final EntryTypeConstant LETTERONFILEADDED= new EntryTypeConstant(49, "Letter On File Added");
	public static final EntryTypeConstant LETTERONFILEDELETED= new EntryTypeConstant(50, "Letter On File Deleted");
	public static final EntryTypeConstant EMPLOYEETRAININGUPDATED= new EntryTypeConstant(47, "Employee Training Updated");
	public static final EntryTypeConstant CONTRACTUNSUSPENDED= new EntryTypeConstant(48, "Contract Unsuspended");
	public static final EntryTypeConstant EMPLOYEESUBMITTEDFORREVIEW= new EntryTypeConstant(49, "Employee Submitted For Review");
	public static final EntryTypeConstant VEHICLESUBMITTEDFORREVIEW= new EntryTypeConstant(49, "Vehicle Submitted For Review");
	public static final EntryTypeConstant CONTRACTOREMAILADDRESSUPDATED= new EntryTypeConstant(50, "Contractor Email Address Updated");
	public static final EntryTypeConstant CONTRACTOREMPLOYEERESTORED= new EntryTypeConstant(51, "Contractor Employee Restored");
	public static final EntryTypeConstant CONTRACTORVEHICLESTATUSUPDATED= new EntryTypeConstant(52, "Contractor Vehicle Status Updated");
	public static final EntryTypeConstant CONTRACTOREMPLOYEESTATUSUPDATED= new EntryTypeConstant(53, "Contractor Employee Status Updated");
	public static final EntryTypeConstant[] ALL = new EntryTypeConstant[] {
		CONTRACTORSUBMITTED, CONTRACTORAPPROVED, CONTRACTORSECURITYADDED,CONTRACTORREJECTED,CONTRACTORCHANGEPASSWORD,
		CONTRACTORLOGIN,CONTRACTORCONTACTUPDATED,CONTRACTORSECUIRTYUPDATED,CONTRACTORCOMPANYUPDATED,CONTRACTORVEHICLEADDED,CONTRACTORVEHICLEUPDATED,
		CONTRACTORVEHICLEDELETED,CONTRACTORVEHICLEDOCADDED,CONTRACTORVEHICLEDOCDELETED,CONTRACTOREMPLOYEEADDED,CONTRACTOREMPLOYEEDELETED,CONTRACTOREMPLOYEEUPDATED,
		CONTRACTOREMPLOYEEAPPROVED,CONTRACTOREMPLOYEEREJECTED,CONTRACTORVEHICLEAPPROVED,CONTRACTORVEHICLEREJECTED,CONTRACTOREMPLOYEESUSPENDED,CONTRACTORSUSPENDED,
		CONTRACTORUNSUSPENDED,CONTRACTORDOCADDED,CONTRACTORDOCDELETED,SYSTEMDOCADDED,SYSTEMDOCDELETED,SYSTEMDOCUPDATED,ADDNEWCONTRACT,UPDATECONTRACT,DELETECONTRACT,
		ADDNEWROUTE,UPDATEROUTE,DELETEROUTE,ROUTEADDEDTOCONTRACT,ROUTEREMOVEDFROMCONTRACT,CONTRACTAWARDED,CONTRACTSUSPENDED,CONTRACTCANCELLED,ADDDRIVERTOROUTE,
		ADDVEHICLETOROUTE,REMOVEDRIVERTOROUTE,REMOVEVEHICLETOROUTE,CONTRACTORVEHICLESUSPENDED,EMPLOYEETRAININGADDED,EMPLOYEETRAININGDELETED,LETTERONFILEADDED,
		LETTERONFILEDELETED,EMPLOYEETRAININGUPDATED,CONTRACTUNSUSPENDED,EMPLOYEESUBMITTEDFORREVIEW,VEHICLESUBMITTEDFORREVIEW,CONTRACTOREMAILADDRESSUPDATED,
		CONTRACTOREMPLOYEERESTORED,CONTRACTORVEHICLESTATUSUPDATED,CONTRACTOREMPLOYEESTATUSUPDATED
	};

	private EntryTypeConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public boolean equal(Object o) {

		if (!(o instanceof EntryTypeConstant))
			return false;
		else
			return (this.getValue() == ((EntryTypeConstant) o).getValue());
	}

	public static EntryTypeConstant get(int value) {

		EntryTypeConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue() == value) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public String toString() {

		return this.getDescription();
	}

}
