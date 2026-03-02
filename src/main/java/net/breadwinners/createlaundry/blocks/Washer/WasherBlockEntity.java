package net.breadwinners.createlaundry.blocks.Washer;

import net.breadwinners.createlaundry.init.LaundryModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Set;


public class WasherBlockEntity extends BlockEntity {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    public WasherBlockEntity(BlockPos pos, BlockState state) {
        super(LaundryModBlockEntities.WASHER.get(), pos, state);
    }

    private final ItemStackHandler itemHand = new ItemStackHandler(2);
    private final FluidTank waterTank = new FluidTank(4000);
    private final FluidTank detergentTank = new FluidTank(4000);

    Set<String> allowed = Set.of(
            "minecraft:water",
            "minecraft:lava"
    );

    private final IFluidHandler fluidHandler = new WasherBlockFluidHandler(allowed, waterTank, detergentTank);

    public IItemHandler getItemHandler() {
        return itemHand;
    }

    public IFluidHandler getFluidHandler() {
        return fluidHandler;
    }

    private int waterReq = 500;
    private int detergentReq = 50;

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (level.getGameTime() % 20 != 0) return;

        ItemStack input = itemHand.getStackInSlot(INPUT_SLOT);
        if (input.isEmpty()) return;
        if (waterTank.getFluidAmount() < waterReq) return;
        if (detergentTank.getFluidAmount() < detergentReq) return;

        ItemStack output = itemHand.getStackInSlot(OUTPUT_SLOT);
        if (!output.isEmpty()) return;

        // consume fluids
        waterTank.drain(500, IFluidHandler.FluidAction.EXECUTE);
        detergentTank.drain(50, IFluidHandler.FluidAction.EXECUTE);

        // move item input -> output
        itemHand.extractItem(INPUT_SLOT, 1, false);
        itemHand.setStackInSlot(OUTPUT_SLOT, new ItemStack(input.getItem()));

        setChanged();
    }
}