package leshanservermanagement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import custominterfaces.ICallbacksForServerResponse;
import leshanclient.LeshanClientObjectInfoVO;
import leshanservercommands.LeshanCommandSerializer;
import leshanservercommands.LeshanServerCommands;

public class ServerRESTAPICallHandler {

    private static ServerRESTAPICallHandler _currentInstance;
    private static Boolean _isInitialized = false;

    private String _host;
    private int _port;

    private LinkedList<APICallQueueElement> _queue;
    private Boolean _requestUnderProcess;

    private ServerRESTAPICallHandler() {
        _queue = new LinkedList<>();
        _requestUnderProcess = false;
    }

    public static ServerRESTAPICallHandler getInstance() {
        if (!_isInitialized) {
            _currentInstance = new ServerRESTAPICallHandler();
            _isInitialized = true;
        }
        return _currentInstance;
    }

    public void setServerParams(String host, int port) {
        _host = host;
        _port = port;
        _host = "127.0.0.1";
        _port = 45456;
    }

    public void sendCommands(LeshanServerCommands cmd, LeshanClientObjectInfoVO deviceVO,
            ICallbacksForServerResponse requester) {
        APICallQueueElement element = new APICallQueueElement(cmd, deviceVO, requester, null);
        _queue.add(element);
        if (!_requestUnderProcess) {
            initiateRequestSending();
        }
    }

    public void sendCommands(LeshanServerCommands cmd, LeshanClientObjectInfoVO deviceVO,
            ICallbacksForServerResponse requester, ArrayList<Object> params) {
        APICallQueueElement element = new APICallQueueElement(cmd, deviceVO, requester, params);
        _queue.add(element);
        if (!_requestUnderProcess) {
            initiateRequestSending();
        }
    }

    private void initiateRequestSending() {

        _requestUnderProcess = true;
        while (_queue.size() != 0) {
            try {

                Socket tempSocket = new Socket(_host, _port);
                OutputStream outToServer = tempSocket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                APICallQueueElement commandElement = _queue.removeFirst();

                switch (commandElement.command) {
                    case GET_CLIENTS:
                        break;
                    case READ_RESOURCE:
                        break;
                    case WRITE:
                        break;
                    case EXECUTE:
                        break;
                }
                LeshanCommandSerializer commandSerializer = new LeshanCommandSerializer(commandElement.command,
                        commandElement.leshanObjInfoVO, commandElement.extraParams);
                out.writeUTF(commandSerializer.getJsonString());
                commandSerializer.dispose();
                commandSerializer = null;
                DataInputStream in = new DataInputStream(tempSocket.getInputStream());
                String response = in.readUTF();
                tempSocket.close();
                commandElement.requester.responseFromServer(response);
                commandElement = null;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        _requestUnderProcess = false;

    }
}

class APICallQueueElement {

    LeshanServerCommands command;
    LeshanClientObjectInfoVO leshanObjInfoVO;
    ICallbacksForServerResponse requester;
    ArrayList<Object> extraParams;

    APICallQueueElement(LeshanServerCommands cmd, LeshanClientObjectInfoVO objInfoVO, ICallbacksForServerResponse req,
            ArrayList<Object> params) {
        command = cmd;
        leshanObjInfoVO = objInfoVO;
        requester = req;
        extraParams = params;
    }

}