package net.breadwinners.createlaundry.utils;

import com.mojang.serialization.Codec;
import net.breadwinners.createlaundry.LaundryMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class LaundryStorage {
    // attachments deffered
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, LaundryMod.MODID);
    private static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, LaundryMod.MODID);
    // main method registar for eventbus
    public static void registar(IEventBus modEventBus)
    {
        COMPONENTS.register(modEventBus);
        ATTACHMENTS.register(modEventBus);
    }
    // Data ------------------------------------------------------------------------------

    // Default = 0.0f
    // Name = dirvalue
    // 0 is most clean - 1 is most dirty
    public static final Supplier<AttachmentType<Float>> ATT_DIRT_VALUE =
            ATTACHMENTS.register("att_dirt_value",
                    () -> AttachmentType.<Float>builder(() -> 0.0f)
                            .serialize(Codec.FLOAT)
                            .build()
            );

    public static final Supplier<DataComponentType<Float>> CMP_DIRT_VALUE =
            COMPONENTS.register("cmp_dirt_value",
                    () -> DataComponentType.<Float>builder()
                            .persistent(Codec.FLOAT)
                            .networkSynchronized(ByteBufCodecs.FLOAT)
                            .build()
            );

    // FUNCTIONS ------------------------------------------------------------------------

    // Usage: DataStorage.getData(blockEntity, DataStorage.DIRTYVALUE);
    public static <T> T getData(BlockEntity blockEntity, Supplier<AttachmentType<T>> attachmentType)
    {
        return (T) blockEntity.getData(attachmentType);
    }

    // Usage: DataStorage.setData(blockEntity, DataStorage.DIRTYVALUE, value);
    public static <T> void setData(BlockEntity blockEntity, Supplier<AttachmentType<T>> attachmentType, T value)
    {
        blockEntity.setData(attachmentType, value);
        blockEntity.setChanged();
    }

    // Items use DataComponents
    public static <T> T getData(ItemStack stack, Supplier<DataComponentType<T>> type, T defaultValue)
    {
        return stack.getOrDefault(type.get(), defaultValue);
    }

    public static <T> void setData(ItemStack stack, Supplier<DataComponentType<T>> type, T value)
    {
        stack.set(type.get(), value);
    }
}
