package lu.kainovaii.tuske.manager.recipe;

import java.util.HashMap;
import java.util.Map;

import lu.kainovaii.tuske.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomShapedRecipe extends ShapedRecipe implements CustomRecipe {

	private Map<Character, ItemStack> map = new HashMap<>();
	
	public CustomShapedRecipe(ItemStack r, ItemStack[] items, String...shapes){
		super(r);
		setIngredients(items, shapes);
	}
	public CustomShapedRecipe(ItemStack r, ItemStack[] items, String key, String...shapes){
		super(new NamespacedKey(Main.getInstance(), key), r);
		setIngredients(items, shapes);
	}
	public Map<Character, ItemStack> getIngredientsMap(){
		return map.size() == 0 ? super.getIngredientMap() : map;
	}
	@Override
	public ItemStack[] getIngredients(){
		return map.values().toArray(new ItemStack[map.size()]);
	}

	private void setIngredients(ItemStack[] items, String[] shapes) {
		for (int x = 0; x < shapes.length; x++){
			shapes[x] = shapes[x].toLowerCase();
			if (shapes[x] == null || !shapes[x].matches("[a-i\\s]{1,3}"))
				return;
		}
		shape(shapes);
		char c = 'a';

		for (int x = 0;x < items.length; x++){
			ItemStack item = items[x];
			try {
				setIngredient(c, item.getData());
			} catch (Exception e) {
			}
			c++;
		}
		setupShape(shapes, items);
	}
	private void setupShape(String[] shapes, ItemStack[] items) {
		char ch1 = 'a';
		for (String shape : shapes)
			for (char ch2 : shape.toCharArray()) {
				if (ch2 - 'a' < items.length) {
					ItemStack item = ch2 - 'a' >= 0 ?items[ch2 - 'a'] : null;
					if (item != null)
						map.put(ch1, item);
					else
						map.put(ch1, new ItemStack(Material.AIR));
				}
				ch1++;
			}

	}
}
