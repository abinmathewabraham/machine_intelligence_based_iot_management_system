package leshanclient;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.types.Command;

public interface ILeshanClientObjectHandler {
    void initialize();

    void setLeshanClientObjectInfo(String name, String regId, int typeId, int instanceId);

    void handleChannelCommands(ChannelUID channelUID, Command command);

    void dispose();
}
