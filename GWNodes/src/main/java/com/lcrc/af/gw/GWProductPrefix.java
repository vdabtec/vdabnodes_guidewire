package com.lcrc.af.gw;

import com.lcrc.af.datatypes.AFEnum;

public class GWProductPrefix {
	public final static int UNKNOWN = 0;
	public final static int CC = 1;
	public final static int BC = 2;
	public final static int PC = 3;
	public final static int AB = 4;
	private static AFEnum s_EnumGWProductPrefix = new AFEnum("GWProductPrefix")
	.addEntry(GWProductPrefix.CC, "CC")
	.addEntry(GWProductPrefix.BC, "BC")	
	.addEntry(GWProductPrefix.PC, "PC")	
	.addEntry(GWProductPrefix.AB, "AB");
	public static AFEnum getEnum(){
		return s_EnumGWProductPrefix ;
	}
}
