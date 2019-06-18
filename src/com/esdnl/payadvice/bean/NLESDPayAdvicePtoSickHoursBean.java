package com.esdnl.payadvice.bean;

import java.io.Serializable;

public class NLESDPayAdvicePtoSickHoursBean implements Serializable {

		private static final long serialVersionUID = -8771605504141293333L;
		private Integer id;
		private double startBal;
		private double earned;
		private double bought;
		private double taken;
		private double sold;
		private double adjustments;
		private double ptoBal;
		private double sickBegBal;
		private double sickEarned;
		private double sickBought;
		private double sickTaken;
		private double sickSold;
		private double sickAdjust;
		private double sickBal;
		private String empNumber;
		private int payGroupId;
		private Integer empInfoId;
		
		public Integer getEmpInfoId() {
			return empInfoId;
		}
		public void setEmpInfoId(Integer empInfoId) {
			this.empInfoId = empInfoId;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public double getStartBal() {
			return startBal;
		}
		public void setStartBal(double startBal) {
			this.startBal = startBal;
		}
		public double getEarned() {
			return earned;
		}
		public void setEarned(double earned) {
			this.earned = earned;
		}
		public double getBought() {
			return bought;
		}
		public void setBought(double bought) {
			this.bought = bought;
		}
		public double getTaken() {
			return taken;
		}
		public void setTaken(double taken) {
			this.taken = taken;
		}
		public double getSold() {
			return sold;
		}
		public void setSold(double sold) {
			this.sold = sold;
		}
		public double getAdjustments() {
			return adjustments;
		}
		public void setAdjustments(double adjustments) {
			this.adjustments = adjustments;
		}
		public double getPtoBal() {
			return ptoBal;
		}
		public void setPtoBal(double ptoBal) {
			this.ptoBal = ptoBal;
		}
		public double getSickBegBal() {
			return sickBegBal;
		}
		public void setSickBegBal(double sickBegBal) {
			this.sickBegBal = sickBegBal;
		}
		public double getSickEarned() {
			return sickEarned;
		}
		public void setSickEarned(double sickEarned) {
			this.sickEarned = sickEarned;
		}
		public double getSickBought() {
			return sickBought;
		}
		public void setSickBought(double sickBought) {
			this.sickBought = sickBought;
		}
		public double getSickTaken() {
			return sickTaken;
		}
		public void setSickTaken(double sickTaken) {
			this.sickTaken = sickTaken;
		}
		public double getSickSold() {
			return sickSold;
		}
		public void setSickSold(double sickSold) {
			this.sickSold = sickSold;
		}
		public double getSickAdjust() {
			return sickAdjust;
		}
		public void setSickAdjust(double sickAdjust) {
			this.sickAdjust = sickAdjust;
		}
		public double getSickBal() {
			return sickBal;
		}
		public void setSickBal(double sickBal) {
			this.sickBal = sickBal;
		}
		public String getEmpNumber() {
			return empNumber;
		}
		public void setEmpNumber(String empNumber) {
			this.empNumber = empNumber;
		}
		public int getPayGroupId() {
			return payGroupId;
		}
		public void setPayGroupId(int payGroupId) {
			this.payGroupId = payGroupId;
		}

}
