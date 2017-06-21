package leshanservermanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import custominterfaces.ICallbacksToThingHandler;
import leshanclient.LeshanClientVO;
import leshanclient.LeshanObjectVO;

public class FetchClientCountThread2 extends Thread {

    String host;
    int port;
    ICallbacksToThingHandler thingHandler;

    public FetchClientCountThread2(String host, int port, ICallbacksToThingHandler thingHandler) {
        this.host = host;
        this.port = port;
        this.thingHandler = thingHandler;
    }

    @Override
    public void run() {
        // String url = host + ":" + port;
        String url = "http://localhost:45456/api/clients?format=JSON";
        try {

            URL leshanServer = new URL(url);
            URLConnection yc = leshanServer.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String responseBody = in.readLine();
            in.close();
            Gson gsonHandler = new Gson();
            Type type = new TypeToken<ArrayList<LeshanServerClientRepVO>>() {
            }.getType();
            ArrayList<LeshanServerClientRepVO> clientListFromServer = gsonHandler.fromJson(responseBody, type);
            ArrayList<LeshanClientVO> clientlist = new ArrayList<>();
            LeshanClientVO temp;
            LeshanObjectVO temp2;
            ArrayList<LeshanObjectVO> temp3;
            String[] temp4;
            for (LeshanServerClientRepVO c : clientListFromServer) {
                temp3 = new ArrayList<>();
                for (ObjectLinkVO o : c.objectLinks) {
                    temp4 = o.url.split("/");
                    if (temp4.length >= 3) {
                        temp2 = new LeshanObjectVO(Integer.parseInt(temp4[1]), Integer.parseInt(temp4[2]));
                        temp3.add(temp2);
                    }
                    temp4 = null;
                }
                temp = new LeshanClientVO(c.endpoint, c.registrationId, temp3);
                clientlist.add(temp);
            }
            System.out.println(responseBody);
            // URLConnection connection = new URL(url).openConnection();
            // InputStream response = new URL(url).openStream();
            // try (Scanner scanner = new Scanner(response)) {
            // String responseBody = scanner.useDelimiter("\\A").next();
            // Gson gsonHandler = new Gson();
            // Type type = new TypeToken<ArrayList<LeshanServerClientRepVO>>() {
            // }.getType();
            // ArrayList<LeshanServerClientRepVO> clientlist = gsonHandler.fromJson(responseBody, type);
            // System.out.println(responseBody);
            // }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class LeshanServerClientRepVO {
    String endpoint;
    String registrationId;
    String registrationDate;
    String lastUpdate;
    String address;
    String lwM2mVersion;
    String lifetime;
    String bindingMode;
    String rootPath;
    ArrayList<ObjectLinkVO> objectLinks;
}

// {"endpoint":"RoomAClient","registrationId":"NMhNuLSHGj","registrationDate":"2016-12-07T12:08:45+05:30","lastUpdate":"2016-12-07T12:08:45+05:30","address":"127.0.0.1:32454","lwM2mVersion":"1.0","lifetime":30,"bindingMode":"U","rootPath":"/","objectLinks":[{"url":"/","attributes":{"rt":"oma.lwm2m"}},{"url":"/1/0","attributes":{}},{"url":"/3300/0","attributes":{}},{"url":"/3306/0","attributes":{}}],"secure":false,"additionalRegistrationAttributes":{}}
class ObjectLinkVO {
    String url;
}