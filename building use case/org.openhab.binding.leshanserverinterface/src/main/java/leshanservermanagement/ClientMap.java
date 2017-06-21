package leshanservermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import leshanclient.ILeshanClientObjectHandler;

public class ClientMap {

    private static Boolean _isInitialized = false;
    private static ClientMap _currentInstance;

    private HashMap<String, ArrayList<ILeshanClientObjectHandler>> _clientHashMap;

    private ClientMap() {
        _clientHashMap = new HashMap<>();
    }

    public static ClientMap getInstance() {
        if (!_isInitialized) {
            _currentInstance = new ClientMap();
            _isInitialized = true;
        }
        return _currentInstance;
    }

    public Boolean isClientPresent(String regId) {
        return (_clientHashMap.get(regId) != null);
    }

    public void removeClientDetails(String regId) {
        _clientHashMap.remove(regId);
    }

    public void addClientDetails(String regId, ArrayList<ILeshanClientObjectHandler> clientHandler) {
        _clientHashMap.put(regId, clientHandler);
    }

    public Iterator<Entry<String, ArrayList<ILeshanClientObjectHandler>>> getIterator() {
        return _clientHashMap.entrySet().iterator();
    }

    public void dispose() {
        _clientHashMap.clear();
        _clientHashMap = null;
        _isInitialized = false;
        _currentInstance = null;
    }
}
