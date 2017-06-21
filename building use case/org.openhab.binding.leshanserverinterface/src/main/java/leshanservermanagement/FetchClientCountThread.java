package leshanservermanagement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import custominterfaces.ICallbacksForServerResponse;
import custominterfaces.ICallbacksToThingHandler;
import leshanclient.ILeshanClientObjectHandler;
import leshanclient.LeshanClientObjectHandlerFactory;
import leshanclient.LeshanClientVO;
import leshanclient.LeshanObjectVO;
import leshanservercommands.LeshanServerCommands;

public class FetchClientCountThread extends Thread implements ICallbacksForServerResponse {
    String host;
    int port;
    ICallbacksToThingHandler thingHandler;

    public FetchClientCountThread(String host, int port, ICallbacksToThingHandler thingHandler) {
        this.host = host;
        this.port = port;
        this.thingHandler = thingHandler;
    }

    @Override
    public void run() {
        ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.GET_CLIENTS, null, this);
    }

    @Override
    public void responseFromServer(String responseJson) {
        Gson gsonHandler = new Gson();
        Type type = new TypeToken<ArrayList<LeshanClientVO>>() {
        }.getType();
        ArrayList<LeshanClientVO> clientlist = gsonHandler.fromJson(responseJson, type);

        Iterator<Entry<String, ArrayList<ILeshanClientObjectHandler>>> iterator = ClientMap.getInstance().getIterator();
        Boolean flag;
        Entry<String, ArrayList<ILeshanClientObjectHandler>> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            flag = false;
            for (LeshanClientVO c : clientlist) {
                if (c.regId.equals(entry.getKey())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ArrayList<ILeshanClientObjectHandler> clientObjList = entry.getValue();// .dispose();
                for (ILeshanClientObjectHandler temp : clientObjList) {
                    temp.dispose();
                }
                iterator.remove();
            }
        }

        for (LeshanClientVO c : clientlist) {
            if (ClientMap.getInstance().isClientPresent(c.regId)) {
                continue;
            } else {
                ILeshanClientObjectHandler clientObjHandler = null;
                ArrayList<ILeshanClientObjectHandler> clientObjList = new ArrayList<>();
                for (LeshanObjectVO leshanObj : c.leshanObjList) {
                    clientObjHandler = LeshanClientObjectHandlerFactory.getInstance().createHandler(c.name, c.regId,
                            leshanObj, thingHandler);
                    if (clientObjHandler == null) {
                        continue;
                    }
                    clientObjList.add(clientObjHandler);
                    clientObjHandler.initialize();
                }
                ClientMap.getInstance().addClientDetails(c.regId, clientObjList);
            }

        }
        thingHandler.callbackForUpdatingStaticChannelState(OnOffType.OFF,
                new StringType(Integer.toString(clientlist.size())));

    }
}