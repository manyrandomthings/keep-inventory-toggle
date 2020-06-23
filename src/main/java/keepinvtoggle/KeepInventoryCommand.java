package keepinvtoggle;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class KeepInventoryCommand {
  public static LiteralCommandNode<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
    return dispatcher.register(
      CommandManager.literal("keepinventory")
      .then(
        CommandManager.literal("on")
        .executes(KeepInventoryCommand::enableKeepInv) // "/keepinventory on"
      )
      .then(
        CommandManager.literal("off")
        .executes(KeepInventoryCommand::disableKeepInv) // "/keepinventory off"
      )
      .executes(KeepInventoryCommand::keepInvStatus) // "/keepinventory"
    );
  }

  // enables keep inventory for player
  private static int enableKeepInv(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();
    GameProfile player = source.getPlayer().getGameProfile();

    // if not in list, add to list
    if(!KeepInventoryWhitelist.contains(player)) {
      KeepInventoryWhitelist.addPlayer(player);
      source.sendFeedback(new LiteralText("Keep inventory for you is now on"), false);
      return 1;
    }

    // if already in list, tell player
    source.sendFeedback(new LiteralText("You already have keep inventory on!"), false);
    return 0;
  }

  // disables keep inventory for player
  private static int disableKeepInv(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();
    GameProfile player = source.getPlayer().getGameProfile();

    // if in list, remove from list
    if(KeepInventoryWhitelist.contains(player)) {
      KeepInventoryWhitelist.removePlayer(player);
      source.sendFeedback(new LiteralText("Keep inventory for you is now off"), false);
      return 1;
    }

    // if player isn't in list, tell player
    source.sendFeedback(new LiteralText("You already have keep inventory off!"), false);
    return 0;
  }

  // send player their current keep inventory status
  private static int keepInvStatus(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    ServerCommandSource source = ctx.getSource();
    GameProfile player = source.getPlayer().getGameProfile();

    source.sendFeedback(new LiteralText("Keep inventory for you is " + (KeepInventoryWhitelist.contains(player) ? "on" : "off")), false);
    return 1;
  }
}
