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

public class LeshanLocationObject implements ILeshanClientObjectHandler, ICallbacksForServerResponse {

    private LeshanClientObjectInfoVO _locationVO;

    private ICallbacksToThingHandler _thingHandler;
    private Gson _gsonHandler;
    private Type _gsonType;

    private ChannelUID _readLatitudeChannelUID;
    private ChannelUID _readLatitudeResponseChannelUID;
    private ChannelUID _readLongitudeChannelUID;
    private ChannelUID _readLongitudeResponseChannelUID;
    private ChannelUID _readAltitudeChannelUID;
    private ChannelUID _readAltitudeResponseChannelUID;
    private ChannelUID _readUncertaintyChannelUID;
    private ChannelUID _readUncertaintyResponseChannelUID;
    private ChannelUID _readVelocityChannelUID;
    private ChannelUID _readVelocityResponseChannelUID;
    private ChannelUID _readTimestampChannelUID;
    private ChannelUID _readTimestampResponseChannelUID;

    private Channel _readLatitudeChannel;
    private Channel _readLatitudeResponseChannel;
    private Channel _readLongitudeChannel;
    private Channel _readLongitudeResponseChannel;
    private Channel _readAltitudeChannel;
    private Channel _readAltitudeResponseChannel;
    private Channel _readUncertaintyChannel;
    private Channel _readUncertaintyResponseChannel;
    private Channel _readVelocityChannel;
    private Channel _readVelocityResponseChannel;
    private Channel _readTimestampChannel;
    private Channel _readTimestampResponseChannel;

    public LeshanLocationObject(ICallbacksToThingHandler thingHandler) {
        _thingHandler = thingHandler;
        _gsonHandler = new Gson();
        _gsonType = new TypeToken<LeshanServerResponseObj>() {
        }.getType();
    }

    @Override
    public void initialize() {
        Thing thing = _thingHandler.getCurrentThing();
        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();

        _readLatitudeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readLatitude", "_"));
        _readLatitudeResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readLatitudeResponse", "_"));
        _readLongitudeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readLongitude", "_"));
        _readLongitudeResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readLongitudeResponse", "_"));
        _readAltitudeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readAltitude", "_"));
        _readAltitudeResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readAltitudeResponse", "_"));
        _readUncertaintyChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readUncertainty", "_"));
        _readUncertaintyResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readUncertaintyResponse", "_"));
        _readVelocityChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readVelocity", "_"));
        _readVelocityResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readVelocityResponse", "_"));
        _readTimestampChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readTimestamp", "_"));
        _readTimestampResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readTimestampResponse", "_"));

        ChannelMap.getInstance().addChannelDetails(_readLatitudeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readLatitudeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readLongitudeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readLongitudeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readAltitudeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readAltitudeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readUncertaintyChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readUncertaintyResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readVelocityChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readVelocityResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readTimestampChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readTimestampResponseChannelUID, this);

        _readLatitudeChannel = ChannelBuilder.create(_readLatitudeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Latitude")).build();
        _readLatitudeResponseChannel = ChannelBuilder.create(_readLatitudeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Latitude")).build();
        _readLongitudeChannel = ChannelBuilder.create(_readLongitudeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Longitude")).build();
        _readLongitudeResponseChannel = ChannelBuilder.create(_readLongitudeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Longitude")).build();
        _readAltitudeChannel = ChannelBuilder.create(_readAltitudeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Altitude")).build();
        _readAltitudeResponseChannel = ChannelBuilder.create(_readAltitudeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Altitude")).build();
        _readUncertaintyChannel = ChannelBuilder.create(_readUncertaintyChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Uncertainty")).build();
        _readUncertaintyResponseChannel = ChannelBuilder.create(_readUncertaintyResponseChannelUID, "String")
                .withLabel(getClientPrefix("Uncertainty")).build();
        _readVelocityChannel = ChannelBuilder.create(_readVelocityChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Velocity")).build();
        _readVelocityResponseChannel = ChannelBuilder.create(_readVelocityResponseChannelUID, "String")
                .withLabel(getClientPrefix("Velocity")).build();
        _readTimestampChannel = ChannelBuilder.create(_readTimestampChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Timestamp")).build();
        _readTimestampResponseChannel = ChannelBuilder.create(_readTimestampResponseChannelUID, "String")
                .withLabel(getClientPrefix("Timestamp")).build();

        thingBuilder.withChannel(_readLatitudeChannel);
        thingBuilder.withChannel(_readLatitudeResponseChannel);
        thingBuilder.withChannel(_readLongitudeChannel);
        thingBuilder.withChannel(_readLongitudeResponseChannel);
        thingBuilder.withChannel(_readAltitudeChannel);
        thingBuilder.withChannel(_readAltitudeResponseChannel);
        thingBuilder.withChannel(_readUncertaintyChannel);
        thingBuilder.withChannel(_readUncertaintyResponseChannel);
        thingBuilder.withChannel(_readVelocityChannel);
        thingBuilder.withChannel(_readVelocityResponseChannel);
        thingBuilder.withChannel(_readTimestampChannel);
        thingBuilder.withChannel(_readTimestampResponseChannel);

        _thingHandler.updateCurrentThing(thingBuilder.build());
    }

    private String getClientPrefix(String label) {
        return _locationVO.name + ":Location:" + label;
    }

    private String getClientPrefix(String label, String seperator) {
        return _locationVO.name + seperator + "Location" + seperator + label;
    }

    @Override
    public void handleChannelCommands(ChannelUID channelUID, Command command) {
        if (channelUID == _readLatitudeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("0");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _locationVO, this,
                        extraParams);
            }
        } else if (channelUID == _readLongitudeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("1");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _locationVO, this,
                        extraParams);
            }
        } else if (channelUID == _readAltitudeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("2");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _locationVO, this,
                        extraParams);
            }
        } else if (channelUID == _readUncertaintyChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("3");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _locationVO, this,
                        extraParams);
            }
        } else if (channelUID == _readVelocityChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("4");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _locationVO, this,
                        extraParams);
            }
        } else if (channelUID == _readTimestampChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _locationVO, this,
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
                    case 0:
                        _thingHandler.callbackForUpdatingChannel(_readLatitudeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readLatitudeChannelUID, OnOffType.OFF);
                        break;
                    case 1:
                        _thingHandler.callbackForUpdatingChannel(_readLongitudeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readLongitudeChannelUID, OnOffType.OFF);
                        break;
                    case 2:
                        _thingHandler.callbackForUpdatingChannel(_readAltitudeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readAltitudeChannelUID, OnOffType.OFF);
                        break;
                    case 3:
                        _thingHandler.callbackForUpdatingChannel(_readUncertaintyResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readUncertaintyChannelUID, OnOffType.OFF);
                        break;
                    case 4:
                        _thingHandler.callbackForUpdatingChannel(_readVelocityResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readVelocityChannelUID, OnOffType.OFF);
                        break;
                    case 5:
                        _thingHandler.callbackForUpdatingChannel(_readTimestampResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readTimestampChannelUID, OnOffType.OFF);
                        break;
                }

            } else {
                switch (responseObj.resourceId) {
                    case 0:
                        _thingHandler.callbackForUpdatingChannel(_readLatitudeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readLatitudeChannelUID, OnOffType.OFF);
                        break;
                    case 1:
                        _thingHandler.callbackForUpdatingChannel(_readLongitudeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readLongitudeChannelUID, OnOffType.OFF);
                        break;
                    case 2:
                        _thingHandler.callbackForUpdatingChannel(_readAltitudeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readAltitudeChannelUID, OnOffType.OFF);
                        break;
                    case 3:
                        _thingHandler.callbackForUpdatingChannel(_readUncertaintyResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readUncertaintyChannelUID, OnOffType.OFF);
                        break;
                    case 4:
                        _thingHandler.callbackForUpdatingChannel(_readVelocityResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readVelocityChannelUID, OnOffType.OFF);
                        break;
                    case 5:
                        _thingHandler.callbackForUpdatingChannel(_readTimestampResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readTimestampChannelUID, OnOffType.OFF);
                        break;
                }
            }

        } else {

        }

    }

    @Override
    public void setLeshanClientObjectInfo(String name, String regId, int typeId, int instanceId) {
        _locationVO = new LeshanClientObjectInfoVO(name, regId, typeId, instanceId);
    }

    @Override
    public void dispose() {
        Thing thing = _thingHandler.getCurrentThing();
        List<Channel> channels = new ArrayList<Channel>(thing.getChannels());

        channels.remove(_readLatitudeChannel);
        channels.remove(_readLatitudeResponseChannel);
        channels.remove(_readLongitudeChannel);
        channels.remove(_readLongitudeResponseChannel);
        channels.remove(_readAltitudeChannel);
        channels.remove(_readAltitudeResponseChannel);
        channels.remove(_readUncertaintyChannel);
        channels.remove(_readUncertaintyResponseChannel);
        channels.remove(_readVelocityChannel);
        channels.remove(_readVelocityResponseChannel);
        channels.remove(_readTimestampChannel);
        channels.remove(_readTimestampResponseChannel);

        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();
        thingBuilder.withChannels(channels);
        _thingHandler.updateCurrentThing(thingBuilder.build());

        ChannelMap.getInstance().removeChannelDetails(_readLatitudeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readLatitudeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readLongitudeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readLongitudeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readAltitudeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readAltitudeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readUncertaintyChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readUncertaintyResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readVelocityChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readVelocityResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readTimestampChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readTimestampResponseChannelUID);

        _readLatitudeChannel = null;
        _readLatitudeResponseChannel = null;
        _readLatitudeChannelUID = null;
        _readLatitudeResponseChannelUID = null;

        _readLongitudeChannel = null;
        _readLongitudeResponseChannel = null;
        _readLongitudeChannelUID = null;
        _readLongitudeResponseChannelUID = null;

        _readAltitudeChannel = null;
        _readAltitudeResponseChannel = null;
        _readAltitudeChannelUID = null;
        _readAltitudeResponseChannelUID = null;

        _readUncertaintyChannel = null;
        _readUncertaintyResponseChannel = null;
        _readUncertaintyChannelUID = null;
        _readUncertaintyResponseChannelUID = null;

        _readVelocityChannel = null;
        _readVelocityResponseChannel = null;
        _readVelocityChannelUID = null;
        _readVelocityResponseChannelUID = null;

        _readTimestampChannel = null;
        _readTimestampResponseChannel = null;
        _readTimestampChannelUID = null;
        _readTimestampResponseChannelUID = null;

        _gsonHandler = null;
        _gsonType = null;
        _thingHandler = null;

    }
}
