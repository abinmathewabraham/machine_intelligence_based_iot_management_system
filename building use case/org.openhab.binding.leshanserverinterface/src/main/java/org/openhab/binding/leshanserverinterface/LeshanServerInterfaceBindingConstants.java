/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.leshanserverinterface;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link LeshanServerInterfaceBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author abin - Initial contribution
 */
public class LeshanServerInterfaceBindingConstants {

    public static final String BINDING_ID = "leshanserverinterface";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "leshanadapter");

    // List of all Channel ids
    public final static String REFRESH_CMD_CHANNEL = "refreshCmdChannel";
    public final static String REFRESH_RESPONSE_CHANNEL = "refreshResponseChannel";

    // reusable strings
    public final static String HOST = "HOST";
    public final static String PORT = "PORT";

}
