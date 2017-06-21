package org.eclipse.leshan.client.demo;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class ServerActuationHandler extends BaseInstanceEnabler {

	private String _applicationType = "Server Power Actuator";
	private Boolean _state;

	public ServerActuationHandler() {
		_state = true;
	}
	
	@Override
	public ReadResponse read(int resourceid) {
		switch (resourceid) {
		case 5750:
			return ReadResponse.success(resourceid,_applicationType);
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
	
	public void toggleServerActuationState() {
		_state = !_state;
	}

}
