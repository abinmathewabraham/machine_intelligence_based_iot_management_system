package custominterfaces;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.builder.ThingBuilder;
import org.eclipse.smarthome.core.types.State;

public interface ICallbacksToThingHandler {
    void callbackForUpdatingStaticChannelState(OnOffType response1, StringType response2);

    ThingBuilder getThingToEdit();

    Thing getCurrentThing();

    void updateCurrentThing(Thing thing);

    void callbackForUpdatingChannel(ChannelUID channelUID, State state);
}
