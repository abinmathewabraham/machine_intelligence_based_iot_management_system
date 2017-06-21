package leshanservercommands;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import leshanclient.LeshanClientObjectInfoVO;

public class LeshanCommandSerializer {

    private Gson _gsonHandler;
    private Type _type;
    private CommandVO _cmdVO;

    public LeshanCommandSerializer(LeshanServerCommands command, LeshanClientObjectInfoVO deviceVO,
            ArrayList<Object> extraParams) {
        _gsonHandler = new Gson();
        _type = new TypeToken<CommandVO>() {
        }.getType();
        _cmdVO = new CommandVO(command, deviceVO, extraParams);
    }

    public String getJsonString() {
        return _gsonHandler.toJson(_cmdVO, _type);
    }

    public void dispose() {
        _gsonHandler = null;
        _type = null;
        _cmdVO = null;
    }
}

class CommandVO {
    LeshanServerCommands command;
    LeshanClientObjectInfoVO leshanObjInfoVO;
    ArrayList<Object> extraParams;

    public CommandVO(LeshanServerCommands cmd, LeshanClientObjectInfoVO objInfoVo, ArrayList<Object> params) {
        command = cmd;
        leshanObjInfoVO = objInfoVo;
        extraParams = params;
    }
}
