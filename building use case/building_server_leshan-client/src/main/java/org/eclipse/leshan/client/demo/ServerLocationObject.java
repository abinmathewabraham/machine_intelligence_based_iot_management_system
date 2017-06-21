package org.eclipse.leshan.client.demo;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

public class ServerLocationObject extends BaseInstanceEnabler {

	private int _roomIndex;
	private final int NUM_ROOMS = 2;
	private String _applicationType = "Server Location";
	
	public ServerLocationObject() {
		_roomIndex = 0;
	}
	
	@Override
	public ReadResponse read(int resourceid) {
		switch (resourceid) {
		case 5750:
			return ReadResponse.success(resourceid,_applicationType);
		case 5900:
			return ReadResponse.success(resourceid,_roomIndex);
		default:
			return super.read(resourceid);
		}
	}
	
    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
    	switch (resourceid) {
		case 5900:
			_roomIndex = ((int)value.getValue())%NUM_ROOMS;
			fireResourcesChange(resourceid);
            return WriteResponse.success();
		default:
			return super.write(resourceid, value);
		}
    	
    }
	public void changeRoom() {
		_roomIndex = ++_roomIndex%NUM_ROOMS;
	}
}
