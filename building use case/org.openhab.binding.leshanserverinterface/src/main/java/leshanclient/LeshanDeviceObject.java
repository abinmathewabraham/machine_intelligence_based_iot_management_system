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

public class LeshanDeviceObject implements ILeshanClientObjectHandler, ICallbacksForServerResponse {

    private LeshanClientObjectInfoVO _deviceVO;

    private ICallbacksToThingHandler _thingHandler;

    private ChannelUID _readManufacturerChannelUID;
    private ChannelUID _readManufacturerResponseChannelUID;
    private ChannelUID _readModelChannelUID;
    private ChannelUID _readModelResponseChannelUID;
    private ChannelUID _readSerialChannelUID;
    private ChannelUID _readSerialResponseChannelUID;
    private ChannelUID _readFirmwareChannelUID;
    private ChannelUID _readFirmwareResponseChannelUID;
    private ChannelUID _execRebootChannelUID;
    private ChannelUID _execRebootResponseChannelUID;
    private ChannelUID _execFactRstChannelUID;
    private ChannelUID _execFactRstResponseChannelUID;
    private ChannelUID _readAvlPwrSrcChannelUID;
    private ChannelUID _readAvlPwrSrcResponseChannelUID;
    private ChannelUID _readSrcVltChannelUID;
    private ChannelUID _readSrcVltResponseChannelUID;
    private ChannelUID _readSrcCurChannelUID;
    private ChannelUID _readSrcCurResponseChannelUID;
    private ChannelUID _readBtryLvlChannelUID;
    private ChannelUID _readBtryLvlResponseChannelUID;
    private ChannelUID _readMemFreeChannelUID;
    private ChannelUID _readMemFreeResponseChannelUID;
    private ChannelUID _readErrCodeChannelUID;
    private ChannelUID _readErrCodeResponseChannelUID;
    private ChannelUID _execRstErrCodeChannelUID;
    private ChannelUID _execRstErrCodeResponseChannelUID;
    private ChannelUID _readCurTimeChannelUID;
    private ChannelUID _readCurTimeResponseChannelUID;
    private ChannelUID _readUTCOffsetChannelUID;
    private ChannelUID _readUTCOffsetResponseChannelUID;
    private ChannelUID _readTmZoneChannelUID;
    private ChannelUID _readTmZoneResponseChannelUID;
    private ChannelUID _readModesChannelUID;
    private ChannelUID _readModesResponseChannelUID;

    private Gson _gsonHandler;
    private Type _gsonType;

    private Channel _readManufacturerChannel;
    private Channel _readManufacturerResponseChannel;
    private Channel _readModelChannel;
    private Channel _readModelResponseChannel;
    private Channel _readSerialChannel;
    private Channel _readSerialResponseChannel;
    private Channel _readFirmwareChannel;
    private Channel _readFirmwareResponseChannel;
    private Channel _execRebootChannel;
    private Channel _execRebootResponseChannel;
    private Channel _execFactRstChannel;
    private Channel _execFactRstResponseChannel;
    private Channel _readAvlPwrSrcChannel;
    private Channel _readAvlPwrSrcResponseChannel;
    private Channel _readSrcVltChannel;
    private Channel _readSrcVltResponseChannel;
    private Channel _readSrcCurChannel;
    private Channel _readSrcCurResponseChannel;
    private Channel _readBtryLvlChannel;
    private Channel _readBtryLvlResponseChannel;
    private Channel _readMemFreeChannel;
    private Channel _readMemFreeResponseChannel;
    private Channel _readErrCodeChannel;
    private Channel _readErrCodeResponseChannel;
    private Channel _execRstErrCodeChannel;
    private Channel _execRstErrCodeResponseChannel;
    private Channel _readCurTimeChannel;
    private Channel _readCurTimeResponseChannel;
    private Channel _readUTCOffsetChannel;
    private Channel _readUTCOffsetResponseChannel;
    private Channel _readTmZoneChannel;
    private Channel _readTmZoneResponseChannel;
    private Channel _readModesChannel;
    private Channel _readModesResponseChannel;

    public LeshanDeviceObject(ICallbacksToThingHandler thingHandler) {
        _thingHandler = thingHandler;
        _gsonHandler = new Gson();
        _gsonType = new TypeToken<LeshanServerResponseObj>() {
        }.getType();
    }

    @Override
    public void initialize() {
        Thing thing = _thingHandler.getCurrentThing();
        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();

        _readManufacturerChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readManufacturer", "_"));
        _readManufacturerResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readManufacturerResponse", "_"));
        _readModelChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readModel", "_"));
        _readModelResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readModelResponse", "_"));
        _readSerialChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readSerial", "_"));
        _readSerialResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readSerialResponse", "_"));
        _readFirmwareChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readFirmware", "_"));
        _readFirmwareResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readFirmwareResponse", "_"));
        _execRebootChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("execReboot", "_"));
        _execRebootResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("execRebootResponse", "_"));
        _execFactRstChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("execFactRst", "_"));
        _execFactRstResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("execFactRstResponse", "_"));
        _readAvlPwrSrcChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readAvlPwrSrc", "_"));
        _readAvlPwrSrcResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readAvlPwrSrcResponse", "_"));
        _readSrcVltChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readSrcVlt", "_"));
        _readSrcVltResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readSrcVltResponse", "_"));
        _readSrcCurChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readSrcCur", "_"));
        _readSrcCurResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readSrcCurResponse", "_"));
        _readBtryLvlChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readBtryLvl", "_"));
        _readBtryLvlResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readBtryLvlResponse", "_"));
        _readMemFreeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readMemFree", "_"));
        _readMemFreeResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readMemFreeResponse", "_"));
        _readErrCodeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readErrCode", "_"));
        _readErrCodeResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readErrCodeResponse", "_"));
        _execRstErrCodeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("execRstErrCode", "_"));
        _execRstErrCodeResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("execRstErrCodeResponse", "_"));
        _readCurTimeChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readCurTime", "_"));
        _readCurTimeResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readCurTimeResponse", "_"));
        _readUTCOffsetChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readUTCOffset", "_"));
        _readUTCOffsetResponseChannelUID = new ChannelUID(thing.getUID(),
                getClientPrefix("readUTCOffsetResponse", "_"));
        _readTmZoneChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readTmZone", "_"));
        _readTmZoneResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readTmZoneResponse", "_"));
        _readModesChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readModes", "_"));
        _readModesResponseChannelUID = new ChannelUID(thing.getUID(), getClientPrefix("readModesResponse", "_"));

        ChannelMap.getInstance().addChannelDetails(_readManufacturerChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readManufacturerResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readModelChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readModelResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readSerialChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readSerialResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readFirmwareChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readFirmwareResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_execRebootChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_execRebootResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_execFactRstChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_execFactRstResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readAvlPwrSrcChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readAvlPwrSrcResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readSrcVltChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readSrcVltResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readSrcCurChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readSrcCurResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readBtryLvlChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readBtryLvlResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readMemFreeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readMemFreeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readErrCodeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readErrCodeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_execRstErrCodeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_execRstErrCodeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readCurTimeChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readCurTimeResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readUTCOffsetChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readUTCOffsetResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readTmZoneChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readTmZoneResponseChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readModesChannelUID, this);
        ChannelMap.getInstance().addChannelDetails(_readModesResponseChannelUID, this);

        _readManufacturerChannel = ChannelBuilder.create(_readManufacturerChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Manufacturer")).build();
        _readManufacturerResponseChannel = ChannelBuilder.create(_readManufacturerResponseChannelUID, "String")
                .withLabel(getClientPrefix("Manufacturer")).build();
        _readModelChannel = ChannelBuilder.create(_readModelChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Model")).build();
        _readModelResponseChannel = ChannelBuilder.create(_readModelResponseChannelUID, "String")
                .withLabel(getClientPrefix("Model")).build();
        _readSerialChannel = ChannelBuilder.create(_readSerialChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Serial")).build();
        _readSerialResponseChannel = ChannelBuilder.create(_readSerialResponseChannelUID, "String")
                .withLabel(getClientPrefix("Serial")).build();
        _readFirmwareChannel = ChannelBuilder.create(_readFirmwareChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Firmware")).build();
        _readFirmwareResponseChannel = ChannelBuilder.create(_readFirmwareResponseChannelUID, "String")
                .withLabel(getClientPrefix("Firmware")).build();
        _execRebootChannel = ChannelBuilder.create(_execRebootChannelUID, "Switch")
                .withLabel(getClientPrefix("Execute Reboot")).build();
        _execRebootResponseChannel = ChannelBuilder.create(_execRebootResponseChannelUID, "String")
                .withLabel(getClientPrefix("Reboot Response")).build();
        _execFactRstChannel = ChannelBuilder.create(_execFactRstChannelUID, "Switch")
                .withLabel(getClientPrefix("Execute Factory Reset")).build();
        _execFactRstResponseChannel = ChannelBuilder.create(_execFactRstResponseChannelUID, "String")
                .withLabel(getClientPrefix("Factory Reset Response")).build();
        _readAvlPwrSrcChannel = ChannelBuilder.create(_readAvlPwrSrcChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Available Power Sources")).build();
        _readAvlPwrSrcResponseChannel = ChannelBuilder.create(_readAvlPwrSrcResponseChannelUID, "String")
                .withLabel(getClientPrefix("Available Power Sources")).build();
        _readSrcVltChannel = ChannelBuilder.create(_readSrcVltChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Source Voltage")).build();
        _readSrcVltResponseChannel = ChannelBuilder.create(_readSrcVltResponseChannelUID, "String")
                .withLabel(getClientPrefix("Source Voltage")).build();
        _readSrcCurChannel = ChannelBuilder.create(_readSrcCurChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Source Current")).build();
        _readSrcCurResponseChannel = ChannelBuilder.create(_readSrcCurResponseChannelUID, "String")
                .withLabel(getClientPrefix("Source Current")).build();
        _readBtryLvlChannel = ChannelBuilder.create(_readBtryLvlChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Battery Level")).build();
        _readBtryLvlResponseChannel = ChannelBuilder.create(_readBtryLvlResponseChannelUID, "String")
                .withLabel(getClientPrefix("Battery Level")).build();
        _readMemFreeChannel = ChannelBuilder.create(_readMemFreeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Memory Free")).build();
        _readMemFreeResponseChannel = ChannelBuilder.create(_readMemFreeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Memory Free")).build();
        _readErrCodeChannel = ChannelBuilder.create(_readErrCodeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Error Code")).build();
        _readErrCodeResponseChannel = ChannelBuilder.create(_readErrCodeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Error Code")).build();
        _execRstErrCodeChannel = ChannelBuilder.create(_execRstErrCodeChannelUID, "Switch")
                .withLabel(getClientPrefix("Execute Reset Error Code")).build();
        _execRstErrCodeResponseChannel = ChannelBuilder.create(_execRstErrCodeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Reset Error Code Response")).build();
        _readCurTimeChannel = ChannelBuilder.create(_readCurTimeChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Current Time")).build();
        _readCurTimeResponseChannel = ChannelBuilder.create(_readCurTimeResponseChannelUID, "String")
                .withLabel(getClientPrefix("Current Time")).build();
        _readUTCOffsetChannel = ChannelBuilder.create(_readUTCOffsetChannelUID, "Switch")
                .withLabel(getClientPrefix("Read UTC Offset")).build();
        _readUTCOffsetResponseChannel = ChannelBuilder.create(_readUTCOffsetResponseChannelUID, "String")
                .withLabel(getClientPrefix("UTC Offset")).build();
        _readTmZoneChannel = ChannelBuilder.create(_readTmZoneChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Timezone")).build();
        _readTmZoneResponseChannel = ChannelBuilder.create(_readTmZoneResponseChannelUID, "String")
                .withLabel(getClientPrefix("Timezone")).build();
        _readModesChannel = ChannelBuilder.create(_readModesChannelUID, "Switch")
                .withLabel(getClientPrefix("Read Supported Bindings and Modes")).build();
        _readModesResponseChannel = ChannelBuilder.create(_readModesResponseChannelUID, "String")
                .withLabel(getClientPrefix("Supported Bindings and Modes")).build();

        thingBuilder.withChannel(_readManufacturerChannel);
        thingBuilder.withChannel(_readManufacturerResponseChannel);
        thingBuilder.withChannel(_readModelChannel);
        thingBuilder.withChannel(_readModelResponseChannel);
        thingBuilder.withChannel(_readSerialChannel);
        thingBuilder.withChannel(_readSerialResponseChannel);
        thingBuilder.withChannel(_readFirmwareChannel);
        thingBuilder.withChannel(_readFirmwareResponseChannel);
        thingBuilder.withChannel(_execRebootChannel);
        thingBuilder.withChannel(_execRebootResponseChannel);
        thingBuilder.withChannel(_execFactRstChannel);
        thingBuilder.withChannel(_execFactRstResponseChannel);
        thingBuilder.withChannel(_readAvlPwrSrcChannel);
        thingBuilder.withChannel(_readAvlPwrSrcResponseChannel);
        thingBuilder.withChannel(_readSrcVltChannel);
        thingBuilder.withChannel(_readSrcVltResponseChannel);
        thingBuilder.withChannel(_readSrcCurChannel);
        thingBuilder.withChannel(_readSrcCurResponseChannel);
        thingBuilder.withChannel(_readBtryLvlChannel);
        thingBuilder.withChannel(_readBtryLvlResponseChannel);
        thingBuilder.withChannel(_readMemFreeChannel);
        thingBuilder.withChannel(_readMemFreeResponseChannel);
        thingBuilder.withChannel(_readErrCodeChannel);
        thingBuilder.withChannel(_readErrCodeResponseChannel);
        thingBuilder.withChannel(_execRstErrCodeChannel);
        thingBuilder.withChannel(_execRstErrCodeResponseChannel);
        thingBuilder.withChannel(_readCurTimeChannel);
        thingBuilder.withChannel(_readCurTimeResponseChannel);
        thingBuilder.withChannel(_readUTCOffsetChannel);
        thingBuilder.withChannel(_readUTCOffsetResponseChannel);
        thingBuilder.withChannel(_readTmZoneChannel);
        thingBuilder.withChannel(_readTmZoneResponseChannel);
        thingBuilder.withChannel(_readModesChannel);
        thingBuilder.withChannel(_readModesResponseChannel);

        _thingHandler.updateCurrentThing(thingBuilder.build());

    }

    private String getClientPrefix(String label) {
        return _deviceVO.name + ":Device:" + label;
    }

    private String getClientPrefix(String label, String seperator) {
        return _deviceVO.name + seperator + "Device" + seperator + label;
    }

    @Override
    public void handleChannelCommands(ChannelUID channelUID, Command command) {
        if (channelUID == _readManufacturerChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("0");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readModelChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("1");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readSerialChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("2");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readFirmwareChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("3");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _execRebootChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("4");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.EXECUTE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _execFactRstChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("5");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.EXECUTE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readAvlPwrSrcChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("6");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readSrcVltChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("7");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readSrcCurChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("8");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readBtryLvlChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("9");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readMemFreeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("10");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readErrCodeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("11");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _execRstErrCodeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("12");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.EXECUTE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readCurTimeChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("13");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readUTCOffsetChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("14");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readTmZoneChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("15");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readModesChannelUID) {
            if (command == OnOffType.ON) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("16");
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.READ_RESOURCE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readCurTimeResponseChannelUID) {
            if (command instanceof StringType) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("13");
                extraParams.add(((StringType) command).toString());
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.WRITE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readUTCOffsetResponseChannelUID) {
            if (command instanceof StringType) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("14");
                extraParams.add(((StringType) command).toString());
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.WRITE, _deviceVO, this,
                        extraParams);
            }
        } else if (channelUID == _readTmZoneResponseChannelUID) {
            if (command instanceof StringType) {
                ArrayList<Object> extraParams = new ArrayList<>();
                extraParams.add("15");
                extraParams.add(((StringType) command).toString());
                ServerCommandsHandler.getInstance().sendCommands(LeshanServerCommands.WRITE, _deviceVO, this,
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
                        _thingHandler.callbackForUpdatingChannel(_readManufacturerResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readManufacturerChannelUID, OnOffType.OFF);
                        break;
                    case 1:
                        _thingHandler.callbackForUpdatingChannel(_readModelResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readModelChannelUID, OnOffType.OFF);
                        break;
                    case 2:
                        _thingHandler.callbackForUpdatingChannel(_readSerialResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readSerialChannelUID, OnOffType.OFF);
                        break;
                    case 3:
                        _thingHandler.callbackForUpdatingChannel(_readFirmwareResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readFirmwareChannelUID, OnOffType.OFF);
                        break;
                    case 6:
                        _thingHandler.callbackForUpdatingChannel(_readAvlPwrSrcResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readAvlPwrSrcChannelUID, OnOffType.OFF);
                        break;
                    case 7:
                        _thingHandler.callbackForUpdatingChannel(_readSrcVltResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readSrcVltChannelUID, OnOffType.OFF);
                        break;
                    case 8:
                        _thingHandler.callbackForUpdatingChannel(_readSrcCurResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readSrcCurChannelUID, OnOffType.OFF);
                        break;
                    case 9:
                        _thingHandler.callbackForUpdatingChannel(_readBtryLvlResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readBtryLvlChannelUID, OnOffType.OFF);
                        break;
                    case 10:
                        _thingHandler.callbackForUpdatingChannel(_readMemFreeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readMemFreeChannelUID, OnOffType.OFF);
                        break;
                    case 11:
                        _thingHandler.callbackForUpdatingChannel(_readErrCodeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readErrCodeChannelUID, OnOffType.OFF);
                        break;
                    case 13:
                        _thingHandler.callbackForUpdatingChannel(_readCurTimeResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readCurTimeChannelUID, OnOffType.OFF);
                        break;
                    case 14:
                        _thingHandler.callbackForUpdatingChannel(_readUTCOffsetResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readUTCOffsetChannelUID, OnOffType.OFF);
                        break;
                    case 15:
                        _thingHandler.callbackForUpdatingChannel(_readTmZoneResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readTmZoneChannelUID, OnOffType.OFF);
                        break;
                    case 16:
                        _thingHandler.callbackForUpdatingChannel(_readModesResponseChannelUID,
                                new StringType(responseObj.getContentValue()));
                        _thingHandler.callbackForUpdatingChannel(_readModesChannelUID, OnOffType.OFF);
                        break;
                }
            } else if (responseObj.status.equals("CHANGED")) {
                switch (responseObj.resourceId) {
                    case 4:
                        _thingHandler.callbackForUpdatingChannel(_execRebootResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_execRebootChannelUID, OnOffType.OFF);
                        break;
                    case 5:
                        _thingHandler.callbackForUpdatingChannel(_execFactRstResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_execFactRstChannelUID, OnOffType.OFF);
                        break;
                    case 12:
                        _thingHandler.callbackForUpdatingChannel(_execRstErrCodeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_execRstErrCodeChannelUID, OnOffType.OFF);
                        break;
                    case 13:
                        _thingHandler.callbackForUpdatingChannel(_readCurTimeResponseChannelUID,
                                new StringType(responseObj.status));
                        break;
                    case 14:
                        _thingHandler.callbackForUpdatingChannel(_readUTCOffsetResponseChannelUID,
                                new StringType(responseObj.status));
                        break;
                    case 15:
                        _thingHandler.callbackForUpdatingChannel(_readTmZoneResponseChannelUID,
                                new StringType(responseObj.status));
                        break;
                }
            } else {
                switch (responseObj.resourceId) {
                    case 0:
                        _thingHandler.callbackForUpdatingChannel(_readManufacturerResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readManufacturerChannelUID, OnOffType.OFF);
                        break;
                    case 1:
                        _thingHandler.callbackForUpdatingChannel(_readModelResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readModelChannelUID, OnOffType.OFF);
                        break;
                    case 2:
                        _thingHandler.callbackForUpdatingChannel(_readSerialResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readSerialChannelUID, OnOffType.OFF);
                        break;
                    case 3:
                        _thingHandler.callbackForUpdatingChannel(_readFirmwareResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readFirmwareChannelUID, OnOffType.OFF);
                        break;
                    case 4:
                        _thingHandler.callbackForUpdatingChannel(_execRebootResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_execRebootChannelUID, OnOffType.OFF);
                        break;
                    case 5:
                        _thingHandler.callbackForUpdatingChannel(_execFactRstResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_execFactRstChannelUID, OnOffType.OFF);
                        break;
                    case 6:
                        _thingHandler.callbackForUpdatingChannel(_readAvlPwrSrcResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readAvlPwrSrcChannelUID, OnOffType.OFF);
                        break;
                    case 7:
                        _thingHandler.callbackForUpdatingChannel(_readSrcVltResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readSrcVltChannelUID, OnOffType.OFF);
                        break;
                    case 8:
                        _thingHandler.callbackForUpdatingChannel(_readSrcCurResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readSrcCurChannelUID, OnOffType.OFF);
                        break;
                    case 9:
                        _thingHandler.callbackForUpdatingChannel(_readBtryLvlResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readBtryLvlChannelUID, OnOffType.OFF);
                        break;
                    case 10:
                        _thingHandler.callbackForUpdatingChannel(_readMemFreeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readMemFreeChannelUID, OnOffType.OFF);
                        break;
                    case 11:
                        _thingHandler.callbackForUpdatingChannel(_readErrCodeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readErrCodeChannelUID, OnOffType.OFF);
                        break;
                    case 12:
                        _thingHandler.callbackForUpdatingChannel(_execRstErrCodeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_execRstErrCodeChannelUID, OnOffType.OFF);
                        break;
                    case 13:
                        _thingHandler.callbackForUpdatingChannel(_readCurTimeResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readCurTimeChannelUID, OnOffType.OFF);
                        break;
                    case 14:
                        _thingHandler.callbackForUpdatingChannel(_readUTCOffsetResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readUTCOffsetChannelUID, OnOffType.OFF);
                        break;
                    case 15:
                        _thingHandler.callbackForUpdatingChannel(_readTmZoneResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readTmZoneChannelUID, OnOffType.OFF);
                        break;
                    case 16:
                        _thingHandler.callbackForUpdatingChannel(_readModesResponseChannelUID,
                                new StringType(responseObj.status));
                        _thingHandler.callbackForUpdatingChannel(_readModesChannelUID, OnOffType.OFF);
                        break;

                }

            }
        } else {

        }
    }

    @Override
    public void setLeshanClientObjectInfo(String name, String regId, int typeId, int instanceId) {
        _deviceVO = new LeshanClientObjectInfoVO(name, regId, typeId, instanceId);
    }

    @Override
    public void dispose() {
        Thing thing = _thingHandler.getCurrentThing();
        List<Channel> channels = new ArrayList<Channel>(thing.getChannels());
        channels.remove(_readManufacturerChannel);
        channels.remove(_readManufacturerResponseChannel);
        channels.remove(_readModelChannel);
        channels.remove(_readModelResponseChannel);
        channels.remove(_readSerialChannel);
        channels.remove(_readSerialResponseChannel);
        channels.remove(_readFirmwareChannel);
        channels.remove(_readFirmwareResponseChannel);
        channels.remove(_execRebootChannel);
        channels.remove(_execRebootResponseChannel);
        channels.remove(_execFactRstChannel);
        channels.remove(_execFactRstResponseChannel);
        channels.remove(_readAvlPwrSrcChannel);
        channels.remove(_readAvlPwrSrcResponseChannel);
        channels.remove(_readSrcVltChannel);
        channels.remove(_readSrcVltResponseChannel);
        channels.remove(_readSrcCurChannel);
        channels.remove(_readSrcCurResponseChannel);
        channels.remove(_readBtryLvlChannel);
        channels.remove(_readBtryLvlResponseChannel);
        channels.remove(_readMemFreeChannel);
        channels.remove(_readMemFreeResponseChannel);
        channels.remove(_readErrCodeChannel);
        channels.remove(_readErrCodeResponseChannel);
        channels.remove(_execRstErrCodeChannel);
        channels.remove(_execRstErrCodeResponseChannel);
        channels.remove(_readCurTimeChannel);
        channels.remove(_readCurTimeResponseChannel);
        channels.remove(_readUTCOffsetChannel);
        channels.remove(_readUTCOffsetResponseChannel);
        channels.remove(_readTmZoneChannel);
        channels.remove(_readTmZoneResponseChannel);
        channels.remove(_readModesChannel);
        channels.remove(_readModesResponseChannel);

        ThingBuilder thingBuilder = _thingHandler.getThingToEdit();
        thingBuilder.withChannels(channels);
        _thingHandler.updateCurrentThing(thingBuilder.build());

        ChannelMap.getInstance().removeChannelDetails(_readManufacturerChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readManufacturerResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readModelChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readModelResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readSerialChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readSerialResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readFirmwareChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readFirmwareResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_execRebootChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_execRebootResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_execFactRstChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_execFactRstResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readAvlPwrSrcChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readAvlPwrSrcResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readSrcVltChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readSrcVltResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readSrcCurChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readSrcCurResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readBtryLvlChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readBtryLvlResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readMemFreeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readMemFreeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readErrCodeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readErrCodeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_execRstErrCodeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_execRstErrCodeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readCurTimeChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readCurTimeResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readUTCOffsetChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readUTCOffsetResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readTmZoneChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readTmZoneResponseChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readModesChannelUID);
        ChannelMap.getInstance().removeChannelDetails(_readModesResponseChannelUID);

        _readManufacturerChannel = null;
        _readManufacturerResponseChannel = null;
        _readManufacturerChannelUID = null;
        _readManufacturerResponseChannelUID = null;

        _readModelChannel = null;
        _readModelResponseChannel = null;
        _readModelChannelUID = null;
        _readModelResponseChannelUID = null;

        _readSerialChannel = null;
        _readSerialResponseChannel = null;
        _readSerialChannelUID = null;
        _readSerialResponseChannelUID = null;

        _readFirmwareChannel = null;
        _readFirmwareResponseChannel = null;
        _readFirmwareChannelUID = null;
        _readFirmwareResponseChannelUID = null;

        _execRebootChannel = null;
        _execRebootResponseChannel = null;
        _execRebootChannelUID = null;
        _execRebootResponseChannelUID = null;

        _execFactRstChannel = null;
        _execFactRstResponseChannel = null;
        _execFactRstChannelUID = null;
        _execFactRstResponseChannelUID = null;

        _readAvlPwrSrcChannel = null;
        _readAvlPwrSrcResponseChannel = null;
        _readAvlPwrSrcChannelUID = null;
        _readAvlPwrSrcResponseChannelUID = null;

        _readSrcVltChannel = null;
        _readSrcVltResponseChannel = null;
        _readSrcVltChannelUID = null;
        _readSrcVltResponseChannelUID = null;

        _readSrcCurChannel = null;
        _readSrcCurResponseChannel = null;
        _readSrcCurChannelUID = null;
        _readSrcCurResponseChannelUID = null;

        _readBtryLvlChannel = null;
        _readBtryLvlResponseChannel = null;
        _readBtryLvlChannelUID = null;
        _readBtryLvlResponseChannelUID = null;

        _readMemFreeChannel = null;
        _readMemFreeResponseChannel = null;
        _readMemFreeChannelUID = null;
        _readMemFreeResponseChannelUID = null;

        _readErrCodeChannel = null;
        _readErrCodeResponseChannel = null;
        _readErrCodeChannelUID = null;
        _readErrCodeResponseChannelUID = null;

        _execRstErrCodeChannel = null;
        _execRstErrCodeResponseChannel = null;
        _execRstErrCodeChannelUID = null;
        _execRstErrCodeResponseChannelUID = null;

        _readCurTimeChannel = null;
        _readCurTimeResponseChannel = null;
        _readCurTimeChannelUID = null;
        _readCurTimeResponseChannelUID = null;

        _readUTCOffsetChannel = null;
        _readUTCOffsetResponseChannel = null;
        _readUTCOffsetChannelUID = null;
        _readUTCOffsetResponseChannelUID = null;

        _readTmZoneChannel = null;
        _readTmZoneResponseChannel = null;
        _readTmZoneChannelUID = null;
        _readTmZoneResponseChannelUID = null;

        _readModesChannel = null;
        _readModesResponseChannel = null;
        _readModesChannelUID = null;
        _readModesResponseChannelUID = null;

        _gsonHandler = null;
        _gsonType = null;
        _thingHandler = null;
    }

}
