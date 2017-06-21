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

public class ServerCommandsHandler {

    private static ServerCommandsHandler _currentInstance;
    private static Boolean _isInitialized = false;

    private String _host;
    private int _port;

    private LinkedList<CommandQueueElement> _queue;
    private Boolean _requestUnderProcess;

    private ServerCommandsHandler() {
        _queue = new LinkedList<>();
        _requestUnderProcess = false;
    }

    public static ServerCommandsHandler getInstance() {
        if (!_isInitialized) {
            _currentInstance = new ServerCommandsHandler();
            _isInitialized = true;
        }
        return _currentInstance;
    }

    public void setServerParams(String host, int port) {
        _host = host;
        _port = port;
    }

    public void sendCommands(LeshanServerCommands cmd, LeshanClientObjectInfoVO deviceVO,
            ICallbacksForServerResponse requester) {
        CommandQueueElement element = new CommandQueueElement(cmd, deviceVO, requester, null);
        _queue.add(element);
        if (!_requestUnderProcess) {
            initiateRequestSending();
        }
    }

    public void sendCommands(LeshanServerCommands cmd, LeshanClientObjectInfoVO deviceVO,
            ICallbacksForServerResponse requester, ArrayList<Object> params) {
        CommandQueueElement element = new CommandQueueElement(cmd, deviceVO, requester, params);
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
                CommandQueueElement commandElement = _queue.removeFirst();
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

class CommandQueueElement {

    LeshanServerCommands command;
    LeshanClientObjectInfoVO leshanObjInfoVO;
    ICallbacksForServerResponse requester;
    ArrayList<Object> extraParams;

    CommandQueueElement(LeshanServerCommands cmd, LeshanClientObjectInfoVO objInfoVO, ICallbacksForServerResponse req,
            ArrayList<Object> params) {
        command = cmd;
        leshanObjInfoVO = objInfoVO;
        requester = req;
        extraParams = params;
    }

}