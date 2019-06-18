package com.nlesd.bcs.constants;

public class DateFieldConstant {
		private int value;
		private String desc;
		private String dateType;
		public static final DateFieldConstant DLEXP = new DateFieldConstant(1, "Driver License Expiry","E");
		public static final DateFieldConstant DARUN = new DateFieldConstant(2, "Driver Abstract Run","E");
		public static final DateFieldConstant FAEXP = new DateFieldConstant(3, "First Aid Expiry","E");
		public static final DateFieldConstant PRCVSQ = new DateFieldConstant(4, "PRC/VSQ","E");
		public static final DateFieldConstant PCC = new DateFieldConstant(5, "Provincial Court Check","E");
		public static final DateFieldConstant CA = new DateFieldConstant(6, "Confidentiality Agreement","E");
		public static final DateFieldConstant REGEXP = new DateFieldConstant(7, "Registration Expiry","V");
		public static final DateFieldConstant INSEXP = new DateFieldConstant(8, "Insurance Expiry","V");
		public static final DateFieldConstant FALLINS = new DateFieldConstant(9, "Fall Inspection","V");
		public static final DateFieldConstant WININS = new DateFieldConstant(10, "Winter Inspection","V");
		public static final DateFieldConstant FALLHEINS = new DateFieldConstant(11, "Fall H.E Inspection","V");
		public static final DateFieldConstant MISCHEINS1 = new DateFieldConstant(12, "Misc H.E Inspection 1","V");
		public static final DateFieldConstant MISCHEINS2 = new DateFieldConstant(13, "Misc H.E Inspection 2","V");
		public static final DateFieldConstant[] ALL = new DateFieldConstant[] {
			DLEXP, DARUN,FAEXP,PRCVSQ,PCC,CA,REGEXP,INSEXP,WININS,FALLHEINS,MISCHEINS1,MISCHEINS2
		};

		private DateFieldConstant(int value, String desc,String datetype) {

			this.value = value;
			this.desc = desc;
			this.dateType=datetype;
		}

		public int getValue() {

			return this.value;
		}

		public String getDescription() {

			return this.desc;
		}

		public String getDateType() {

			return this.dateType;
		}
		
		public boolean equal(Object o) {

			if (!(o instanceof DateFieldConstant))
				return false;
			else
				return (this.getValue() == ((DateFieldConstant) o).getValue());
		}

		public static DateFieldConstant get(int value) {

			DateFieldConstant tmp = null;

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
