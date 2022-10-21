package lu.kainovaii.tuske.events;

import lu.kainovaii.tuske.listeners.PlayerMovesCheck;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class EvtPlayerStartsMove extends SkriptEvent{

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "player starts moving";
	}

	@Override
	public boolean check(Event ev) {		
		return true;
	}

	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		PlayerMovesCheck.setLoaded(true);
		return true;
	}

}
