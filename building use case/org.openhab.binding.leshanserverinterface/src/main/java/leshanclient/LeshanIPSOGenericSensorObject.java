package leshanclient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.builder.ChannelBuilder;
import org.eclipse.smarthome.core.thing.binding.builder.ThingBuilder;
import org.eclipse.smarthome.core.types.Command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import custominterfaces.ICallbacksForServerResponse;
import custominterfaces.ICallbacksToThingHandler;
import leshanservercommands.LeshanServerCommands;
import leshanservermanagement.ChannelMap;
import leshanservermanagement.LeshanServerResponseObj;
import leshanservermanagement.ServerCommandsHandler;

public class LeshanIPSOGenericSensorObject implements ILeshanClientObjectHandler, ICallbacksForServerResponse {

    private LeshanClientObjectInfoVO _ipsoGenSensorVO;
    private ICallbacksToThingHandler _thingHandler;

    private ChannelUID _readApplicationTypeChannelUID;
    private ChannelUID _readApplicationTypeResponseChannelUID;
    private ChannelUID _readValueChannelUID;
    private ChannelUID _readValueResponseChannelUID;

    private Gson _gsonHandler;
    private Type _gsonType;

    private Channel _readApplicationTypeChannel;
    private Channel _readApplicationTypeResponseChannel;
    private Channel _readValueChannel;
    private Channel _readValueResponseChannel;

    public LeshanIPSOGenericSensorObject(ICallbacksToThingHandler thingHandler) {
        _thingHandler = thingHandler;
        _gsonHandler = new Gson();
        _gsonType = new TypeToken<LeshanServerResponseObj>() {
        }.getType();
    }

    @Override
    public void setLeshanClientObjectInfo(String name, String regId, int typeId, int instanceId) {
        _ipsoGenSensorVO = new LeshanClientObjectInfoVO(name, regId, typeId, instanceId);
    }

    @Override
    public void initialize() {
        Thing thing = _thingHandler.getCurrentThing();
        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();

        _readApplicationTypeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readApplicationType", "_"));
        _readApplicationTypeResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readApplicationTypeResponse", "_"));
        _readValueChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readValue", "_"));
        _readValueResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readValueResponse", "_"));

        ChannelMap.getInstance().addChannelDetails(_readApplicationTypeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readApplicationTypeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readValueChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readValueResponseChannelUID, this);

        _readApplicationTypeChannel = ChannelBuilder.create(_readApplicationTypeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Application Type")).build();
        _readApplicationTypeResponseChannel = ChannelBuilder.create(_readApplicationTypeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Application Type")).build();
        _readValueChannel = ChannelBuilder.create(_readValueChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Value")).build();
        _readValueResponseChannel = ChannelBuilder.create(_readValueResponseChannelUID, "String")
                .withLabel(getClientPrefix("Value")).build();

        thingBuilder.withChannel(_readApplicationTypeChannel);
        thingBuilder.withChannel(_readApplicationTypeResponseChannel);
        thingBuilder.withChannel(_readValueChannel);
        thingBuilder.withChannel(_readValueResponseChannel);

        _thingHandler.updateCurrentThing(thingBuilder.build());
    }

    @Override
    public void handleChannelCommands(ChannelUID channelUID, Command command) {
        if (channelUID == _readApplicationTypeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5750");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _ipsoGenSensorVO,
                        this, extraParams);
            }
        } else if (channelUID == _readValueChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5700");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _ipsoGenSensorVO,
                        this, extraParams);
            }
        }
    }

    @Override
    public void responseFromServer(String responseJson) {
        LeshanServerResponseObj responseObj = _gsonHandler.fromJson(responseJson, _gsonType);
        if (responseObj != null) {
            if (responseObj.status.equals("CONTENT")) {
                switch (responseObj.resourceId) {
                    case 5750:
                        _thingHandler.callbackForUpdatingChannel(_readApplicationTypeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readApplicationTypeChannelUID, OnOffType.OFF);
                        break;
                    case 5700:
                        _thingHandler.callbackForUpdatingChannel(_readValueResponseChannelUID,
                                new StringType(responseObj.getContentValue().equals("1.0") ? "ON" : "OFF"));
                        _thingHandler.callbackForUpdatingChannel(_readValueChannelUID, OnOffType.OFF);
                        break;
                }
            } else if (responseObj.status.equals("CHANGED")) {

            } else {
                switch (responseObj.resourceId) {
                    case 5750:
                        _thingHandler.callbackForUpdatingChannel(_readApplicationTypeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readApplicationTypeChannelUID, OnOffType.OFF);
                        break;
                    case 5700:
                        _thingHandler.callbackForUpdatingChannel(_readValueResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readValueChannelUID, OnOffType.OFF);
                        break;
                }
            }
        } else {

        }

    }

    private String getClientPrefix(String label) {
        return _ipsoGenSensorVO.name + ":IPSOGenericSensor:" + _ipsoGenSensorVO.instanceId + ":" + label;
    }

    private String getClientPrefix(String label, String seperator) {
        return _ipsoGenSensorVO.name + seperator + "IPSOGenericSensor" + seperator + _ipsoGenSensorVO.instanceId
                + seperator + label;
    }

    @Override
    public void dispose() {
        Thing thing = _thingHandler.getCurrentThing();
        List<Channel> channels = new ArrayList<Channel>(thing.getChannels());

        channels.remove(_readApplicationTypeChannel);
        channels.remove(_readApplicationTypeResponseChannel);
        channels.remove(_readValueChannel);
        channels.remove(_readValueResponseChannel);

        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();
        thingBuilder.withChannels(channels);
        _thingHandler.updateCurrentThing(thingBuilder.build());

        ChannelMap.getInstance().removeChannelDetails(_readApplicationTypeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readApplicationTypeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readValueChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readValueResponseChannelUID);

        _readApplicationTypeChannel = null;
        _readApplicationTypeResponseChannel = null;
        _readApplicationTypeChannelUID = null;
        _readApplicationTypeResponseChannelUID = null;

        _readValueChannel = null;
        _readValueResponseChannel = null;
        _readValueChannelUID = null;
        _readValueResponseChannelUID = null;

        _gsonHandler = null;
        _gsonType = null;
        _thingHandler = null;
    }

}
