package net.breadwinners.createlaundry.utils;

import com.mojang.serialization.Codec;
import net.breadwinners.createlaundry.LaundryMod;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class LaundryStorage {
    // attachments deffered
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, LaundryMod.MODID);

    // main method registar for eventbus
    public static void registar(IEventBus modEventBus)
    {
        ATTACHMENTS.register(modEventBus);
    }
    // Data ------------------------------------------------------------------------------

    // Default = 0.0f
    // Name = dirvalue
    // 0 is most clean - 1 is most dirty
    public static final Supplier<AttachmentType<Float>> DIRTYVALUE =
            ATTACHMENTS.register("dirtvalue",
                    () -> AttachmentType.<Float>builder(() -> 0.0f)
                            .serialize(Codec.FLOAT)
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
}
