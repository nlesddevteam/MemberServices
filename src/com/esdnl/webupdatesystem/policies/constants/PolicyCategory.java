package com.esdnl.webupdatesystem.policies.constants;

import java.util.HashMap;
import java.util.Map;

public class PolicyCategory {
		private int value;
		private String desc;
		public static final PolicyCategory POLICY_BOARD= new PolicyCategory(1, "BOARD");
		public static final PolicyCategory POLICY_FINANCE_ADMINISTRATION= new PolicyCategory(2, "FINANCE AND ADMINISTRATION");
		public static final PolicyCategory POLICY_DEPARTMENT_OF_EDUCATION= new PolicyCategory(3, "DEPARTMENT OF EDUCATION");
		public static final PolicyCategory POLICY_HUMAN_RESOURCES= new PolicyCategory(4, "HUMAN RESOURCES");
		public static final PolicyCategory POLICY_OPERATIONS = new PolicyCategory(5, "OPERATIONS");
		public static final PolicyCategory POLICY_PROGRAMS = new PolicyCategory(6, "PROGRAMS");
		
		public static final PolicyCategory[] ALL = new PolicyCategory[] {
			POLICY_BOARD , POLICY_FINANCE_ADMINISTRATION,POLICY_DEPARTMENT_OF_EDUCATION,POLICY_HUMAN_RESOURCES,POLICY_OPERATIONS,POLICY_PROGRAMS
		};
		private PolicyCategory(int value, String desc) {
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
			if (!(o instanceof PolicyCategory))
				return false;
			else
				return (this.getValue() == ((PolicyCategory) o).getValue());
		}
		public static PolicyCategory get(int value) {
			PolicyCategory tmp = null;
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
		public static Map<Integer,String> getCategoriesList()
		{
			Map<Integer,String> categorylist = new HashMap<Integer,String>();
			for(PolicyCategory t : PolicyCategory.ALL)
			{
				categorylist.put(t.getValue(),t.getDescription());
			}
			return categorylist;
		}
	}

