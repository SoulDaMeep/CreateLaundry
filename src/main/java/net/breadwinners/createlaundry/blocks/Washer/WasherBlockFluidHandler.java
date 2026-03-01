package net.breadwinners.createlaundry.blocks.Washer;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.Set;

public class WasherBlockFluidHandler implements IFluidHandler {

    private final FluidTank[] tanks;
    private final Set<String> allowedFluids; // whitelist by fluid ID

    public WasherBlockFluidHandler(Set<String> allowedFluids, FluidTank... tanks) {
        this.tanks = tanks;
        this.allowedFluids = allowedFluids;
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return tanks[tank].getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return tanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        // Only allow if the fluid is in the whitelist
        return stack != null && allowedFluids.contains(stack.getFluid().toString());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (!isFluidValid(0, resource)) { // check first tank (or you can check all tanks)
            return 0;
        }

        int filled = 0;
        for (FluidTank tank : tanks) {
            if (!isFluidValid(0, resource)) continue; // skip invalid fluids
            int f = tank.fill(resource.copy(), action);
            filled += f;
            resource.shrink(f);
            if (resource.isEmpty()) break;
        }

        return filled;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        for (FluidTank tank : tanks) {
            FluidStack drained = tank.drain(resource, action);
            if (!drained.isEmpty())
                return drained;
        }
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        for (FluidTank tank : tanks) {
            FluidStack drained = tank.drain(maxDrain, action);
            if (!drained.isEmpty())
                return drained;
        }
        return FluidStack.EMPTY;
    }
}