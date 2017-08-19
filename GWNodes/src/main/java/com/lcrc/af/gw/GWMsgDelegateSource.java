package com.lcrc.af.gw;

import com.lcrc.af.polledsource.PolledServiceSource;


public class GWMsgDelegateSource extends PolledServiceSource {
	// CONSTRUCTORS 
	public GWMsgDelegateSource(){	
		super(new GWMsgDelegateService());	
	}	
}
