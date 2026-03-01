package net.breadwinners.createlaundry.mixins;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiGraphics.class)
public abstract class TestMixin {

    @Shadow
    public abstract int guiWidth();
    @ModifyVariable(
            method = "renderTooltipInternal",
            at = @At("HEAD"),
            argsOnly = true
    )
    private List<ClientTooltipComponent> modifyTooltipComponents(
            List<ClientTooltipComponent> original
    ) {

        //

        List<ClientTooltipComponent> components = new ArrayList<>(original);

//        components.add(0,
//                ClientTooltipComponent.create(
//                        Component.literal("This is a test for mixins.")
//                                .getVisualOrderText()
//                )
//        );

        return components;
    }
}
