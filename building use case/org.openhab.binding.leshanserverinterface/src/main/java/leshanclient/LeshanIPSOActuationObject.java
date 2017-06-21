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

public class LeshanIPSOActuationObject implements ILeshanClientObjectHandler, ICallbacksForServerResponse {

    private LeshanClientObjectInfoVO _ipsoActuationVO;
    private ICallbacksToThingHandler _thingHandler;

    private ChannelUID _readApplicationTypeChannelUID;
    private ChannelUID _readApplicationTypeResponseChannelUID;
    private ChannelUID _readOnOffStateChannelUID;
    private ChannelUID _readOnOffStateResponseChannelUID;

    private Gson _gsonHandler;
    private Type _gsonType;

    private Channel _readApplicationTypeChannel;
    private Channel _readApplicationTypeResponseChannel;
    private Channel _readOnOffStateChannel;
    private Channel _readOnOffStateResponseChannel;

    public LeshanIPSOActuationObject(ICallbacksToThingHandler thingHandler) {
        _thingHandler = thingHandler;
        _gsonHandler = new Gson();
        _gsonType = new TypeToken<LeshanServerResponseObj>() {
        }.getType();
    }

    @Override
    public void setLeshanClientObjectInfo(String name, String regId, int typeId, int instanceId) {
        _ipsoActuationVO = new LeshanClientObjectInfoVO(name, regId, typeId, instanceId);
    }

    @Override
    public void initialize() {
        Thing thing = _thingHandler.getCurrentThing();
        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();

        _readApplicationTypeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readApplicationType", "_"));
        _readApplicationTypeResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readApplicationTypeResponse", "_"));
        _readOnOffStateChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readOnOffState", "_"));
        _readOnOffStateResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readOnOffStateResponse", "_"));

        ChannelMap.getInstance().addChannelDetails(_readApplicationTypeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readApplicationTypeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readOnOffStateChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readOnOffStateResponseChannelUID, this);

        _readApplicationTypeChannel = ChannelBuilder.create(_readApplicationTypeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Application Type")).build();
        _readApplicationTypeResponseChannel = ChannelBuilder.create(_readApplicationTypeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Application Type")).build();
        _readOnOffStateChannel = ChannelBuilder.create(_readOnOffStateChannelUID, "Switch")
                .withLabel(getClientPrefix("Read On/Off State")).build();
        _readOnOffStateResponseChannel = ChannelBuilder.create(_readOnOffStateResponseChannelUID, "String")
                .withLabel(getClientPrefix("On/Off State")).build();

        thingBuilder.withChannel(_readApplicationTypeChannel);
        thingBuilder.withChannel(_readApplicationTypeResponseChannel);
        thingBuilder.withChannel(_readOnOffStateChannel);
        thingBuilder.withChannel(_readOnOffStateResponseChannel);

        _thingHandler.updateCurrentThing(thingBuilder.build());
    }

    @Override
    public void handleChannelCommands(ChannelUID channelUID, Command command) {
        if (channelUID == _readApplicationTypeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5750");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _ipsoActuationVO,
                        this, extraParams);
            }
        } else if (channelUID == _readOnOffStateChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5850");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _ipsoActuationVO,
                        this, extraParams);
            }
        } else if (channelUID == _readOnOffStateResponseChannelUID) {
            if (command instanceof StringType) {
                if (!command.equals("ON") && !command.equals("OFF")) {
                    return;
                }
                String commandToSend = command.equals("ON") ? "true" : "false";
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5850");
                extraParams.add(commandToSend);
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.WRITE, _ipsoActuationVO, this,
                        extraParams);
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
                    case 5850:
                        _thingHandler.callbackForUpdatingChannel(_readOnOffStateResponseChannelUID,
                                new StringType(responseObj.getContentValue().equals("true") ? "ON" : "OFF"));
                        _thingHandler.callbackForUpdatingChannel(_readOnOffStateChannelUID, OnOffType.OFF);
                        break;
                }
            } else if (responseObj.status.equals("CHANGED")) {
                switch (responseObj.resourceId) {
                    case 5850:
                        _thingHandler.callbackForUpdatingChannel(_readOnOffStateResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readOnOffStateChannelUID, OnOffType.OFF);
                        break;
                }
            } else {
                switch (responseObj.resourceId) {
                    case 5750:
                        _thingHandler.callbackForUpdatingChannel(_readApplicationTypeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readApplicationTypeChannelUID, OnOffType.OFF);
                        break;
                    case 5850:
                        _thingHandler.callbackForUpdatingChannel(_readOnOffStateResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readOnOffStateChannelUID, OnOffType.OFF);
                        break;
                }
            }
        } else {

        }

    }

    private String getClientPrefix(String label) {
        return _ipsoActuationVO.name + ":IPSOActuation:" + _ipsoActuationVO.instanceId + ":" + label;
    }

    private String getClientPrefix(String label, String seperator) {
        return _ipsoActuationVO.name + seperator + "IPSOActuation" + seperator + _ipsoActuationVO.instanceId + seperator
                + label;
    }

    @Override
    public void dispose() {
        Thing thing = _thingHandler.getCurrentThing();
        List<Channel> channels = new ArrayList<Channel>(thing.getChannels());

        channels.remove(_readApplicationTypeChannel);
        channels.remove(_readApplicationTypeResponseChannel);
        channels.remove(_readOnOffStateChannel);
        channels.remove(_readOnOffStateResponseChannel);

        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();
        thingBuilder.withChannels(channels);
        _thingHandler.updateCurrentThing(thingBuilder.build());

        ChannelMap.getInstance().removeChannelDetails(_readApplicationTypeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readApplicationTypeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readOnOffStateChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readOnOffStateResponseChannelUID);

        _readApplicationTypeChannel = null;
        _readApplicationTypeResponseChannel = null;
        _readApplicationTypeChannelUID = null;
        _readApplicationTypeResponseChannelUID = null;

        _readOnOffStateChannel = null;
        _readOnOffStateResponseChannel = null;
        _readOnOffStateChannelUID = null;
        _readOnOffStateResponseChannelUID = null;

        _gsonHandler = null;
        _gsonType = null;
        _thingHandler = null;
    }

}
