package keepinvtoggle.mixins;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import keepinvtoggle.KeepInventoryWhitelist;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_KeepInventoryToggleMixin extends PlayerEntity {

    public ServerPlayerEntity_KeepInventoryToggleMixin(World world, BlockPos blockPos, float yaw, GameProfile gameProfile) {
        super(world, blockPos, yaw, gameProfile);
    }

    @Redirect(method = "copyFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean spawnWhitelistCheck(GameRules gamerules, GameRules.Key<GameRules.BooleanRule> gameruleType) {
        return gamerules.getBoolean(gameruleType) || KeepInventoryWhitelist.contains(this.getGameProfile());
    }
}
