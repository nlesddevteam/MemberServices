package com.esdnl.colaboration.bean;

public class CollaborationException extends Exception{

	private static final long serialVersionUID = -3704096638962514582L;

		public CollaborationException(){
			super();
		}
		
		public CollaborationException(String msg){
			super(msg);
		}
		
		public CollaborationException(String msg, Throwable e){
			super(msg, e);
		}
}
