package leshanservermanagement;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.smarthome.core.thing.ChannelUID;

import leshanclient.ILeshanClientObjectHandler;

public class ChannelMap {
    private static Boolean _isInitialized = false;
    private static ChannelMap _currentInstance;
    private HashMap<ChannelUID, ILeshanClientObjectHandler> _channelHashMap;

    private ChannelMap() {
        _channelHashMap = new HashMap<>();
    }

    public static ChannelMap getInstance() {
        if (!_isInitialized) {
            _currentInstance = new ChannelMap();
            _isInitialized = true;
        }
        return _currentInstance;
    }

    public Boolean isChannelPresent(ChannelUID channelUID) {
        return (_channelHashMap.get(channelUID) != null);
    }

    public void removeChannelDetails(ChannelUID channelUID) {
        _channelHashMap.remove(channelUID);
    }

    public void addChannelDetails(ChannelUID channelUID, ILeshanClientObjectHandler clientHandler) {
        _channelHashMap.put(channelUID, clientHandler);
    }

    public ILeshanClientObjectHandler getHandler(ChannelUID channelUID) {
        return _channelHashMap.get(channelUID);
    }

    public Collection<ILeshanClientObjectHandler> getAllHandlers() {
        return _channelHashMap.values();
    }

    public void dispose() {
        _channelHashMap.clear();
        _channelHashMap = null;
        _isInitialized = false;
        _currentInstance = null;
    }
}
