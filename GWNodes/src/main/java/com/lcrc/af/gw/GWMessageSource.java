package com.lcrc.af.gw;

import com.lcrc.af.polledsource.PolledServiceSource;


public class GWMessageSource extends PolledServiceSource {
	// CONSTRUCTORS 
	public GWMessageSource(){	
		super(new GWMessageService());
	
	}
	
}
