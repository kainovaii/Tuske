package lu.kainovaii.tuske.manager.recipe;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;


public class CustomShapelessRecipe extends ShapelessRecipe implements CustomRecipe {
	
	private ItemStack[] items = null;
	
	public CustomShapelessRecipe(ItemStack r, ItemStack... items) {
		super(r);
		setIngredients(items);
	}
	public CustomShapelessRecipe(ItemStack r, ItemStack[] items, String key) {
		super(r);
		//super(new NamespacedKey(TuSKe.getInstance(), key), r);
		setIngredients(items);
	}
	private void setIngredients(ItemStack... items) {
		for (int x = 0; x < items.length; x++){
			if (items[x] != null){
				if (items[x].getType() != Material.AIR)
					this.addIngredient(items[x].getAmount(), items[x].getData());
			}
		}
		this.items = items;
	}
	@Override
	public List<ItemStack> getIngredientList(){
		return Arrays.asList(items);
	}
	@Override
	public ItemStack[] getIngredients(){
		return items;
	}
}
