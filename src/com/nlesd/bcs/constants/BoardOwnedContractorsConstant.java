package com.nlesd.bcs.constants;

public class BoardOwnedContractorsConstant
{
	private int value;
	private String desc;
	public static final BoardOwnedContractorsConstant WESTERN= new BoardOwnedContractorsConstant(25, "Western");
	public static final BoardOwnedContractorsConstant CENTRAL= new BoardOwnedContractorsConstant(27, "Central");
	public static final BoardOwnedContractorsConstant LABRADOR= new BoardOwnedContractorsConstant(28, "Labrador");
	
	
	public static final BoardOwnedContractorsConstant[] ALL = new BoardOwnedContractorsConstant[] {
		WESTERN, CENTRAL, LABRADOR
	};
	
	private BoardOwnedContractorsConstant(int value, String desc) {
	
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
	
		if (!(o instanceof BoardOwnedContractorsConstant))
			return false;
		else
			return (this.getValue() == ((BoardOwnedContractorsConstant) o).getValue());
	}
	
	public static BoardOwnedContractorsConstant get(int value) {
	
		BoardOwnedContractorsConstant tmp = null;
	
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