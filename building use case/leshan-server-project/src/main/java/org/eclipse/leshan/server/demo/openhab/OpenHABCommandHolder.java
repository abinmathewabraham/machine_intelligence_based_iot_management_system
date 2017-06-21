package org.eclipse.leshan.server.demo.openhab;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OpenHABCommandHolder {

	private String _commandJson;
	private CommandVO _cmdVO;
		
	public OpenHABCommandHolder(String commandJson) {
		_commandJson = commandJson;
		Gson gsonHandler = new Gson();
		Type type = new TypeToken<CommandVO>() {
		}.getType();
		_cmdVO = gsonHandler.fromJson(_commandJson, type);
	}

	public OpenHABInterfaceCommands getCommand() {
		return _cmdVO.command;
	}

	public Boolean isClientSpecificCommand() {
		return (_cmdVO.leshanObjInfoVO != null);
	}

	public String getClientName() {
		return _cmdVO.leshanObjInfoVO.name;
	}

	public String getClientRegId() {
		return _cmdVO.leshanObjInfoVO.regId;
	}

	public LeshanClientObjectInfoVO getClientObjectVO() {
		return _cmdVO.leshanObjInfoVO;
	}
	
	public ArrayList<Object> getExtraParams(){
		return _cmdVO.extraParams;
	}
}

class CommandVO {
	OpenHABInterfaceCommands command;
	LeshanClientObjectInfoVO leshanObjInfoVO;
	ArrayList<Object> extraParams;
}
