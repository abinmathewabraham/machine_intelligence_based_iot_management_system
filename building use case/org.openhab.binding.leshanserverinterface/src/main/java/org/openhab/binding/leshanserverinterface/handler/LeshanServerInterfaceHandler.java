/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.leshanserverinterface.handler;

import static org.openhab.binding.leshanserverinterface.LeshanServerInterfaceBindingConstants.*;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.builder.ThingBuilder;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import custominterfaces.ICallbacksToThingHandler;
import leshanclient.ILeshanClientObjectHandler;
import leshanservermanagement.ChannelMap;
import leshanservermanagement.ClientMap;
import leshanservermanagement.FetchClientCountThread2;
import leshanservermanagement.HandleChannelCommandsThread;
import leshanservermanagement.ServerCommandsHandler;

/**
 * The {@link LeshanServerInterfaceHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author abin - Initial contribution
 */
public class LeshanServerInterfaceHandler extends BaseThingHandler implements ICallbacksToThingHandler {

    private Logger logger = LoggerFactory.getLogger(LeshanServerInterfaceHandler.class);

    private String host;
    private int port;

    private ChannelUID refreshCmdChannel;
    private ChannelUID refreshCmdResponseChannel;

    public LeshanServerInterfaceHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void thingUpdated(Thing thing) {
        if (refreshCmdChannel == null) {
            refreshCmdChannel = new ChannelUID(getThing().getUID(), REFRESH_CMD_CHANNEL);
        }
        if (refreshCmdResponseChannel == null) {
            refreshCmdResponseChannel = new ChannelUID(getThing().getUID(), REFRESH_RESPONSE_CHANNEL);
        }

        host = (String) getThing().getConfiguration().get(HOST);
        port = Integer.parseInt((String) getThing().getConfiguration().get(PORT));
        ServerCommandsHandler.getInstance().setServerParams(host, port);
        super.thingUpdated(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        String currentChannel = channelUID.getId();
        if (currentChannel.equals(REFRESH_CMD_CHANNEL)) {
            if (command == OnOffType.ON) {
                // FetchClientCountThread fetchClientCountThread = new FetchClientCountThread(host, port, this);
                // fetchClientCountThread.start();
                FetchClientCountThread2 fetchClientCountThread2 = new FetchClientCountThread2(host, port, this);
                fetchClientCountThread2.start();
            }
        } else if (ChannelMap.getInstance().isChannelPresent(channelUID)) {
            HandleChannelCommandsThread handleChannelCommandsThread = new HandleChannelCommandsThread(channelUID,
                    command);
            handleChannelCommandsThread.start();
        } else {

        }

        /*
         * // Note: if communication with thing fails for some reason,
         * // indicate that by setting the status with detail information
         * // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
         * // "Could not control device at IP address x.x.x.x");
         * }
         */
    }

    @Override
    public void initialize() {
        // Long running initialization should be done asynchronously in background.
        host = (String) getThing().getConfiguration().get(HOST);
        port = Integer.parseInt((String) getThing().getConfiguration().get(PORT));
        ServerCommandsHandler.getInstance().setServerParams(host, port);
        refreshCmdChannel = new ChannelUID(getThing().getUID(), REFRESH_CMD_CHANNEL);
        refreshCmdResponseChannel = new ChannelUID(getThing().getUID(), REFRESH_RESPONSE_CHANNEL);
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    @Override
    public void dispose() {
        logger = null;
        refreshCmdChannel = null;
        refreshCmdResponseChannel = null;
        for (ILeshanClientObjectHandler handler : ChannelMap.getInstance().getAllHandlers()) {
            handler.dispose();
        }
        ClientMap.getInstance().dispose();
        ChannelMap.getInstance().dispose();
        super.dispose();
    }

    // used to handle callbacks from FetchClientCountThread
    @Override
    public void callbackForUpdatingStaticChannelState(OnOffType stateForRefreshCommandChannel,
            StringType stateForRefreshCommandResponseChannel) {

        if (stateForRefreshCommandChannel != null) {
            updateState(refreshCmdChannel, stateForRefreshCommandChannel);
        }
        if (stateForRefreshCommandResponseChannel != null) {
            updateState(refreshCmdResponseChannel, stateForRefreshCommandResponseChannel);
        }
    }

    @Override
    public ThingBuilder getThingToEdit() {
        ThingBuilder thingBuilder = editThing();
        if (thingBuilder != null) {
            return thingBuilder;
        }
        return null;
    }

    @Override
    public Thing getCurrentThing() {
        Thing thing = getThing();
        if (thing != null) {
            return thing;
        }
        return null;
    }

    @Override
    public void updateCurrentThing(Thing thing) {
        updateThing(thing);

    }

    @Override
    public void callbackForUpdatingChannel(ChannelUID channelUID, State state) {
        updateState(channelUID, state);

    }
}