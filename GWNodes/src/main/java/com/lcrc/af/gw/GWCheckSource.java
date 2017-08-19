package com.lcrc.af.gw;

import com.lcrc.af.polledsource.PolledServiceSource;


public class GWCheckSource extends PolledServiceSource {
	// CONSTRUCTORS 
	public GWCheckSource(){	
		super(new GWCheckService());
	
	}
	
}
