package keepinvtoggle.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import keepinvtoggle.KeepInventoryWhitelist;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity_KeepInventoryToggleMixin extends LivingEntity {
    protected PlayerEntity_KeepInventoryToggleMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean deathWhitelistCheck(GameRules gamerules, GameRules.Key<GameRules.BooleanRule> gameruleType) {
        return gamerules.getBoolean(gameruleType) || KeepInventoryWhitelist.contains(((PlayerEntity) (Object) this).getGameProfile());
    }

    @Redirect(method = "getCurrentExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean experienceDropCheck(GameRules gamerules, GameRules.Key<GameRules.BooleanRule> gameruleType) {
        return gamerules.getBoolean(gameruleType) || KeepInventoryWhitelist.contains(((PlayerEntity) (Object) this).getGameProfile());
    }
}
