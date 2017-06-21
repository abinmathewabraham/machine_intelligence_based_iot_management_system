package org.eclipse.leshan.client.demo;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class SprinklerHandler extends BaseInstanceEnabler {

	private String _applicationType = "Sprinkler control Actuator";
	private Boolean _state;

	public SprinklerHandler() {
		_state = false;
	}
	
	@Override
	public ReadResponse read(int resourceid) {
		switch (resourceid) {
		case 5750:
			return ReadResponse.success(resourceid,getApplicationType());
		case 5850:
			return ReadResponse.success(resourceid, _state);
		default:
			return super.read(resourceid);
		}
	}

	@Override
	public WriteResponse write(int resourceid, LwM2mResource value) {
		switch (resourceid) {
		case 5850:
			_state = (Boolean)value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();
		default:
			return super.write(resourceid, value);
		}

	}
	
	public void toggleSprinklerState() {
		_state = !_state;
	}
	
	private String getApplicationType(){
		return GlobalUtils.ENDPOINT + " " + _applicationType; 
	}

}
