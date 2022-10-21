package lu.kainovaii.tuske.hooks.legendchat.events;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import br.com.devpaulo.legendchat.api.events.PrivateMessageEvent;
import lu.kainovaii.tuske.util.Registry;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class EvtLegendchat extends SkriptEvent{
	static {
		Registry.newEvent(EvtLegendchat.class, ChatMessageEvent.class, "Legendchat chat", "l[egend]c[hat] chat" );
		Registry.newEvent(EvtLegendchat.class, PrivateMessageEvent.class, "Legendchat tell", "[l[egend]c[hat]] (tell|p[rivate ]m[essage])");
	}

	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "l[egend]c[hat] chat";
	}

	@Override
	public boolean check(Event e) {
		return true;
	}

	@Override
	public boolean init(Literal<?>[] arg, int arg1, ParseResult arg2) {
		return true;
	}

}
