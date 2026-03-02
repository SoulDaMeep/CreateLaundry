package net.breadwinners.createlaundry.mixins;

import com.google.common.collect.Maps;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(PartDefinition.class)
public interface PartDefinitionAccessor {
    @Accessor("cubes")
    List<CubeDefinition> cubes();

}
