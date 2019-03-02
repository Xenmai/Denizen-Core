package net.aufdemrand.denizencore.events.core;

import net.aufdemrand.denizencore.DenizenCore;
import net.aufdemrand.denizencore.events.ScriptEvent;
import net.aufdemrand.denizencore.objects.Element;
import net.aufdemrand.denizencore.objects.aH;
import net.aufdemrand.denizencore.objects.dObject;
import net.aufdemrand.denizencore.scripts.ScriptEntryData;
import net.aufdemrand.denizencore.scripts.containers.ScriptContainer;
import net.aufdemrand.denizencore.utilities.CoreUtilities;

public class TickScriptEvent extends ScriptEvent {

    // <--[event]
    // @Events
    // tick
    //
    // @Switch every <count>
    //
    // @Regex ^on tick$
    //
    // @Warning This event fires very rapidly and is usually not the most ideal way to handle things.
    //
    // @Triggers every single tick.
    //
    // @Context
    // <context.tick> how many ticks have passed since the server started.
    //
    // -->

    public static TickScriptEvent instance;

    public long ticks = 0;

    public ScriptEntryData data = null;

    @Override
    public void reset() {
        data = DenizenCore.getImplementation().getEmptyScriptEntryData();
        super.reset();
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return data;
    }

    @Override
    public dObject getContext(String name) {
        if (name.equals("tick")) {
            return new Element(ticks);
        }
        return super.getContext(name);
    }

    public TickScriptEvent() {
        instance = this;
    }

    @Override
    public boolean couldMatch(ScriptContainer script, String event) {
        String lower = CoreUtilities.toLowerCase(event);
        return lower.startsWith("tick");
    }

    @Override
    public boolean matches(ScriptPath path) {
        String countString = path.switches.get("every");
        int count = countString == null ? 1 : aH.getIntegerFrom(countString);
        return ticks % count == 0;
    }

    @Override
    public String getName() {
        return "Tick";
    }

    public boolean enabled = false;

    @Override
    public void init() {
        enabled = true;
    }

    @Override
    public void destroy() {
        enabled = false;
    }
}
