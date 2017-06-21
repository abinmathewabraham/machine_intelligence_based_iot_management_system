package leshanservermanagement;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.types.Command;

import leshanclient.ILeshanClientObjectHandler;

public class HandleChannelCommandsThread extends Thread {

    private ChannelUID _currentChannelUID;
    private Command _currentCommand;

    public HandleChannelCommandsThread(ChannelUID channelUID, Command command) {
        this._currentChannelUID = channelUID;
        this._currentCommand = command;
    }

    @Override
    public void run() {
        ILeshanClientObjectHandler clientHandler = ChannelMap.getInstance().getHandler(_currentChannelUID);
        clientHandler.handleChannelCommands(_currentChannelUID, _currentCommand);
    }
}
