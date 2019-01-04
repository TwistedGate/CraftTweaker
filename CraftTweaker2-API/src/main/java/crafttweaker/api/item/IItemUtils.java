package crafttweaker.api.item;

import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntityDefinition;
import stanhebben.zenscript.annotations.*;

/**
 *
 * Class for various Item utils
 * can be called with itemUtils.function
 *
 * @author BloodWorkXGaming
 */
public interface IItemUtils {
    
    /**
     *
     * @param params has to be in this syntax:
     *               [<effect1>, strength, time], [<effect2>, strength, time], ...
     * @return returns the {@link IItemStack} of the potion
     */
    @ZenMethod
    IItemStack createPotion(Object[]... params);
    
    
    /**
     * Gets all Items where the Regex matches the Registry name
     * check in syntax "mod:item:meta"
     * meta is always present
     * @param regex to compare agains
     * @return Array of Items that match
     */
    @ZenMethod
    IItemStack[] getItemsByRegexRegistryName(String regex);
    
    /**
     * Invokes the Bracket Handler Item method
     * @param location What you would write in the BH, e.g. "minecraft:dirt"
     * @param meta The metadata, default to 0
     * @return The same thing the Bracket handler would return.
     */
    @ZenMethod
    IItemStack getItem(String location, @Optional int meta);
    
    /**
     * Gets all Items where the Regex matches the Unlocalized name
     * check in syntax "mod:item:meta"
     * meta is always present
     * @param regex to compare agains
     * @return Array of Items that match
     */
    @ZenMethod
    IItemStack[] getItemsByRegexUnlocalizedName(String regex);
    
    
    /**
     * Creates a Spawnegg of the given entity
     * @param entity
     * @param customNBT NBT that the Item should have, entity gets overwritten by function
     * example: itemUtils.createSpawnEgg(<entity:minecraft:sheep>, {EntityTag:{id:"minecraft:creeper",NoAI:1 as byte,PersistenceRequired:1 as byte}})
     */
    @ZenMethod
    IItemStack createSpawnEgg(IEntityDefinition entity, @Optional IData customNBT);
    
    
}
