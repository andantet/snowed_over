package com.ninni.snowed_over.mixin;

import com.ninni.snowed_over.client.screen.ReindeerScreenHandler;
import com.ninni.snowed_over.entity.ReindeerEntity;
import com.ninni.snowed_over.network.SnowedOverPacketIdentifiers;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Shadow public abstract void closeHandledScreen();

    @Shadow protected abstract void incrementScreenHandlerSyncId();

    @Shadow protected abstract void onScreenHandlerOpened(ScreenHandler screenHandler);

    @Shadow private int screenHandlerSyncId;

    @Inject(at = @At("HEAD"), method = "openHorseInventory", cancellable = true)
    private void openHorseInventory(HorseBaseEntity horse, Inventory inventory, CallbackInfo ci) {
        ServerPlayerEntity $this = (ServerPlayerEntity) (Object) this;
        if (horse instanceof ReindeerEntity reindeer) {
            ci.cancel();
            if ($this.currentScreenHandler != $this.playerScreenHandler) {
                this.closeHandledScreen();
            }
            this.incrementScreenHandlerSyncId();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(reindeer.getId());
            buf.writeInt(inventory.size());
            buf.writeInt(this.screenHandlerSyncId);
            ServerPlayNetworking.send($this, SnowedOverPacketIdentifiers.OPEN_REINDEER_SCREEN, buf);
            $this.currentScreenHandler = new ReindeerScreenHandler(this.screenHandlerSyncId, $this.getInventory(), inventory, reindeer);
            this.onScreenHandlerOpened($this.currentScreenHandler);
        }
    }

}
