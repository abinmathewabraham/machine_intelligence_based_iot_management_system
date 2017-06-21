package leshanclient;

import custominterfaces.ICallbacksToThingHandler;

public class LeshanClientObjectHandlerFactory {

    private static Boolean _isInitialized = false;
    private static LeshanClientObjectHandlerFactory _currentInstance;

    private LeshanClientObjectHandlerFactory() {

    }

    public static LeshanClientObjectHandlerFactory getInstance() {
        if (!_isInitialized) {
            _currentInstance = new LeshanClientObjectHandlerFactory();
            _isInitialized = true;
        }
        return _currentInstance;
    }

    public ILeshanClientObjectHandler createHandler(String name, String regId, LeshanObjectVO clientObj,
            ICallbacksToThingHandler thingHandler) {

        ILeshanClientObjectHandler clientObjecthandler = null;
        switch (clientObj.typeId) {
            case 0: // LWM2M security
                return null;
            case 1: // LWM2M server
                return null;
            case 2: // LWM2M access control
                return null;
            case 3: // Device
                clientObjecthandler = new LeshanDeviceObject(thingHandler);
                break;
            case 4: // Connectivity Monitoring
                return null;
            case 5: // Firmware Update
                return null;
            case 6: // Location
                clientObjecthandler = new LeshanLocationObject(thingHandler);
                break;
            case 7: // Connectivity Statistics
                return null;
            case 9: // Software Management
                return null;
            case 3200: // IPSO Digital Input
                return null;
            case 3201: // IPSO Digital Output
                return null;
            case 3202: // IPSO Analog Input
                return null;
            case 3203: // IPSO Analog Output
                return null;
            case 3300: // IPSO Generic Sensor
                clientObjecthandler = new LeshanIPSOGenericSensorObject(thingHandler);
                break;
            case 3301: // IPSO Illuminance
                return null;
            case 3302: // IPSO Presence
                return null;
            case 3303: // IPSO Temperature
                return null;
            case 3304: // IPSO Humidity
                return null;
            case 3305: // IPSO Power Measurement
                return null;
            case 3306: // IPSO Actuation
                clientObjecthandler = new LeshanIPSOActuationObject(thingHandler);
                break;
            case 3308: // IPSO Set Point
                clientObjecthandler = new LeshanIPSOSetPointObject(thingHandler);
                break;
            case 3310: // IPSO Load Control
                return null;
            case 3311: // IPSO Light Control
                return null;
            case 3312: // IPSO Power Control
                return null;
            case 3313: // IPSO Accelerometer
                return null;
            case 3314: // IPSO Magnetometer
                return null;
            case 3315: // IPSO Barometer
                return null;
        }

        if (clientObjecthandler != null) {
            clientObjecthandler.setLeshanClientObjectInfo(name, regId, clientObj.typeId, clientObj.instanceId);
        }

        return clientObjecthandler;
    }

}
