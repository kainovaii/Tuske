package lu.kainovaii.tuske.expressions.customenchantments;

import javax.annotation.Nullable;

import lu.kainovaii.tuske.util.Registry;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import lu.kainovaii.tuske.manager.customenchantment.CEnchant;
import lu.kainovaii.tuske.manager.customenchantment.CustomEnchantment;
import lu.kainovaii.tuske.manager.customenchantment.EnchantConfig;

public class ExprLoreName extends SimplePropertyExpression<CEnchant, String>{
	static {
		Registry.newProperty(ExprLoreName.class, "lore name", "customenchantment");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	@Nullable
	public String convert(CEnchant ce) {
		return ce.getEnchant().getName();
	}

	@Override
	protected String getPropertyName() {
		return "lore name";
	}
	
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		CEnchant ce = getExpr().getSingle(e);
		if (ce != null && delta != null){
			CustomEnchantment cc = CustomEnchantment.getByID((String)delta[0]);
			if (cc == null)
				cc = CustomEnchantment.getByName((String)delta[0]);
			if (cc != null && !cc.equalsById(ce.getEnchant()))
				return;
			ce.getEnchant().setName((String)delta[0]);
			EnchantConfig.y.set("Enchantments." + ce.getEnchant().getId() + ".Name", ((String)delta[0]));
			EnchantConfig.save(); 
		}
	}

	
	@SuppressWarnings("unchecked")
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}


}
