package org.eclipse.leshan.client.demo;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

public class FireSensorHandler extends BaseInstanceEnabler {

	private Boolean _sensorState;
	private String _applicationType = "Fire Sensor";

	public FireSensorHandler() {
		_sensorState = false;
	}

	@Override
	public ReadResponse read(int resourceid) {
		switch (resourceid) {
		case 5700:
			return ReadResponse.success(resourceid, _sensorState?1:0);
		case 5750:
			return ReadResponse.success(resourceid, getApplicationType());
		default:
			return super.read(resourceid);
		}
	}

	public void toggleFireSensorState() {
		_sensorState = !_sensorState;
	}
	
	private String getApplicationType(){
		return GlobalUtils.ENDPOINT + " " + _applicationType; 
	}
}
