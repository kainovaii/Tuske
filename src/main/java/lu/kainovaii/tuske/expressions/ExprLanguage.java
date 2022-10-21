package lu.kainovaii.tuske.expressions;

import lu.kainovaii.tuske.Main;
import lu.kainovaii.tuske.util.Registry;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public class ExprLanguage extends SimplePropertyExpression<Player, String>{
	static {
		Registry.newProperty(ExprLanguage.class, "(locale|language)", "player");
	}
	
	static final boolean isSpigot = Main.isSpigot();

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	@Nullable
	public String convert(Player p) {
		return (p != null)? (isSpigot) ? p.spigot().getLocale() : getLanguage(p) : null;
	}

	@Override
	protected String getPropertyName() {
		return "langague";
	}
	public String getLanguage(Player p){
		try {
			Object ep = p.getClass().getDeclaredMethod("getHandle").invoke(p, (Object[]) null);
			Field f = ep.getClass().getDeclaredField("locale");
			f.setAccessible(true);
			String language = (String) f.get(ep);
			return language;
		} catch (Exception e) {
			return null;
		}
	}
}
