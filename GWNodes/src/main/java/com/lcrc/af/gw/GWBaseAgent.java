package com.lcrc.af.gw;

import java.lang.instrument.Instrumentation;

public class GWBaseAgent {
	 public static void premain(String args, Instrumentation inst) {
		inst.addTransformer(new GWActivityTransformer());
	 }
	
}
