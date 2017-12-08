package com.lcrc.af.gw;

import com.lcrc.af.datatypes.AFEnum;

public class GWMessageStatusType {
	public final static int UNSENT = 1;
	public final static int INFLIGHT = 2;
	public final static int  ERROR = 3;
	public final static int  RETRYABLE  = 4;

	private static AFEnum s_GWMessageStatusType = new AFEnum("GWMessageStatusType")
	.addEntry(UNSENT, "Unsent")
	.addEntry(INFLIGHT, "Inflight")
	.addEntry(ERROR, "Error")
	.addEntry(RETRYABLE, "Retryable")
	;
	public static AFEnum getEnum(){
		return s_GWMessageStatusType ;
	}
	
}
