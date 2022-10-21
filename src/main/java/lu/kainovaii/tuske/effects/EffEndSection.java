package lu.kainovaii.tuske.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.NoDoc;
import ch.njol.skript.lang.*;
import ch.njol.util.Kleenean;
import lu.kainovaii.tuske.util.LazyEffectSection;
import lu.kainovaii.tuske.util.Registry;
import org.bukkit.event.Event;

/**
 * Used for {@link LazyEffectSection} to know the end of a section
 * @author Tuke_Nuke on 28/06/2017
 */
@NoDoc
@Name("Internal usage only, please don't be curious unless your are @Snow-Pyon")
public class EffEndSection extends Effect {
	static {
		Registry.newEffect(EffEndSection.class, "$ end section");
	}

	@Override
	protected void execute(Event event) {

	}

	@Override
	public String toString(Event event, boolean b) {
		return "$ end section";
	}

	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		LazyEffectSection.removeCurrentSection();
		return true;
	}
}
