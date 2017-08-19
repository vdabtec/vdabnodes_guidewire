package com.lcrc.af.gw;

import com.lcrc.af.datatypes.AFEnum;

public class GWMajorVersion {
	public final static int UNKNOWN = 0;
	public final static int VERS7 = 7;
	public final static int VERS8 = 8;
	public final static int VERS9 = 9;
	private static AFEnum s_EnumGWMajorVersion = new AFEnum("GWMajorVersion")
	.addEntry(GWMajorVersion.VERS7, "V7")
	.addEntry(GWMajorVersion.VERS8, "V8")
	.addEntry(GWMajorVersion.VERS9, "V9");
	public static AFEnum getEnum(){
		return s_EnumGWMajorVersion ;
	}
}
