package com.lcrc.af.gw;

import com.lcrc.af.datatypes.AFEnum;

public class GWMessageStatusType {
	public final static int UNSENT = 1;
	public final static int INFLIGHT = 2;
	public final static int  STATUS3 = 3;
	public final static int  STATUS4  = 4;

	private static AFEnum s_GWMessageStatusType = new AFEnum("GWMessageStatusType")
	.addEntry(UNSENT, "Unsent")
	.addEntry(INFLIGHT, "Inflight")
//	.addEntry(STATUS3, "Status3")
//	.addEntry(STATUS4, "Status4")
	;
	public static AFEnum getEnum(){
		return s_GWMessageStatusType ;
	}
	
}
