package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SculkSickness.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item",() -> new Item(new Item.Properties()));

    public static void init() {
        ITEMS.register();
    }
}
