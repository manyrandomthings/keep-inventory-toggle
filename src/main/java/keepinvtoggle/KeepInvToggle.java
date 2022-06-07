package keepinvtoggle;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import java.io.File;

public class KeepInvToggle implements DedicatedServerModInitializer {
  @Override
  public void onInitializeServer() {
    // set whitelist file to keep_inventory_whitelist.json
    KeepInventoryWhitelist.create(new File("keep_inventory_whitelist.json"));
    // register /keepinventory command
    CommandRegistrationCallback.EVENT.register(KeepInventoryCommand::register);
  }
}
