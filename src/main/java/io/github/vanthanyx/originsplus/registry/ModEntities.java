package io.github.vanthanyx.originsplus.registry;

import io.github.apace100.originsplus.Origins;
import io.github.vanthanyx.originsplus.entity.LunarianPearlEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType LUNARIAN_PEARL;

    static {
        LUNARIAN_PEARL = FabricEntityTypeBuilder.<LunarianPearlEntity>create(SpawnGroup.MISC, (type, world) -> new LunarianPearlEntity(type, world)).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
    }

    public static void register() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Origins.MODID, "lunarian_pearl"), LUNARIAN_PEARL);
    }
}
