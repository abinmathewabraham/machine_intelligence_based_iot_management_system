package org.eclipse.leshan.server.demo.openhab;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.demo.servlet.json.ResponseSerializer;
import org.eclipse.leshan.server.registration.Registration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OpenHabInterfaceThread extends Thread {
	final int openHabInterfacePortNo = 24856;
	ServerSocket openHabInterfaceSocket;
	LeshanServer lwServer;
	Gson gsonHandler;

	public OpenHabInterfaceThread(LeshanServer lwServer) throws IOException {
		this.lwServer = lwServer;
		openHabInterfaceSocket = new ServerSocket(openHabInterfacePortNo);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeHierarchyAdapter(LwM2mResponse.class, new ResponseSerializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

		gsonHandler = gsonBuilder.create();
		// serverSocket.setSoTimeout(10000);
	}

	public void run() {
		while (true) {
			try {
				Socket server = openHabInterfaceSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				String commandJson = in.readUTF();
				OpenHABCommandHolder cmdHolder = new OpenHABCommandHolder(commandJson);

				if (cmdHolder.getCommand() == OpenHABInterfaceCommands.GET_CLIENTS) {
					DataOutputStream out = new DataOutputStream(server.getOutputStream());
					Iterator<Registration> clients = lwServer.getRegistrationService().getAllRegistrations();
					ArrayList<LeshanClientVO> listForJson = new ArrayList<>();
					LeshanClientVO temp;
					Registration c;
					
					while (clients.hasNext()) {
						c = clients.next();
						temp = new LeshanClientVO(c.getEndpoint(),c.getId(),c.getObjectLinks());
						listForJson.add(temp);
					}
//					for (Registration c : clients) {
//						LinkObjectTest[] test = c.getObjectLinks();
//						String[] tempInfo = c.getObjectLinks()[1].getUrl().split("/");
//						// temp = new
//						// LeshanClientVO(Integer.parseInt(tempInfo[1]),
//						// c.getEndpoint(), c.getRegistrationId(),
//						// Integer.parseInt(tempInfo[2]));
//						temp = new LeshanClientVO(c.getEndpoint(), c.getRegistrationId(), c.getObjectLinks());
//						listForJson.add(temp);
//					}

					Type type = new TypeToken<ArrayList<LeshanClientVO>>() {
					}.getType();
					String responseJson = gsonHandler.toJson(listForJson, type);
					out.writeUTF(responseJson);

					type = null;
					temp = null;
					while (!listForJson.isEmpty()) {
						listForJson.remove(0);
					}
					listForJson = null;
				} else if (cmdHolder.isClientSpecificCommand()) {
					String response = "";
					DataOutputStream out = new DataOutputStream(server.getOutputStream());
					switch (cmdHolder.getCommand()) {
					case READ_RESOURCE:
						response = readResource(cmdHolder.getClientObjectVO(), cmdHolder.getExtraParams());
						break;
					case EXECUTE:
						response = execute(cmdHolder.getClientObjectVO(), cmdHolder.getExtraParams());
						break;
					case WRITE:
						response = write(cmdHolder.getClientObjectVO(), cmdHolder.getExtraParams());
						break;

					default:

					}
					out.writeUTF(response);

				}
				cmdHolder = null;
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	private String readResource(LeshanClientObjectInfoVO leshanClientObjectVO, ArrayList<Object> extraParams) {
		String result = "";

		Registration client = lwServer.getRegistrationService().getByEndpoint(leshanClientObjectVO.name);
		if (client != null) {
			String target = "/" + leshanClientObjectVO.typeId + "/" + leshanClientObjectVO.instanceId + "/"
					+ extraParams.get(0);
			ContentFormat contentFormat = ContentFormat.JSON;
			ReadRequest request = new ReadRequest(contentFormat, target);
			LwM2mResponse cResponse = null;
			try {
				cResponse = lwServer.send(client, request, 5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (cResponse != null) {
				result = gsonHandler.toJson(cResponse);
				result = result.substring(0, result.length() - 1);
				result += ",resourceId:" + extraParams.get(0) + "}";
			}
		}
		return result;
	}

	private String execute(LeshanClientObjectInfoVO leshanClientObjectVO, ArrayList<Object> extraParams) {
		String result = "";
		Registration client = lwServer.getRegistrationService().getByEndpoint(leshanClientObjectVO.name);
		if (client != null) {
			String target = "/" + leshanClientObjectVO.typeId + "/" + leshanClientObjectVO.instanceId + "/"
					+ extraParams.get(0);
			ExecuteRequest request = new ExecuteRequest(target, "");
			LwM2mResponse cResponse = null;
			try {
				cResponse = lwServer.send(client, request, 5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (cResponse != null) {
				result = gsonHandler.toJson(cResponse);
				result = result.substring(0, result.length() - 1);
				result += ",resourceId:" + extraParams.get(0) + "}";
			}
		}
		return result;
	}

	private String write(LeshanClientObjectInfoVO leshanClientObjectVO, ArrayList<Object> extraParams) {
		String result = "";
		Registration client = lwServer.getRegistrationService().getByEndpoint(leshanClientObjectVO.name);
		if (client != null) {
			WriteRequest request = new WriteRequest(leshanClientObjectVO.typeId, leshanClientObjectVO.instanceId,
					Integer.parseInt((String) extraParams.get(0)), (String) extraParams.get(1));
			LwM2mResponse cResponse = null;
			try {
				cResponse = lwServer.send(client, request, 5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (cResponse != null) {
				result = gsonHandler.toJson(cResponse);
				result = result.substring(0, result.length() - 1);
				result += ",resourceId:" + extraParams.get(0) + "}";
			}
		}
		return result;
	}
}
