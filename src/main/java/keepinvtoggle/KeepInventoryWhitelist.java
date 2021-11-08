package keepinvtoggle;

import java.io.File;

import com.mojang.authlib.GameProfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.server.Whitelist;
import net.minecraft.server.WhitelistEntry;

public class KeepInventoryWhitelist {
    private static final Logger LOGGER = LogManager.getLogger("keep-inventory-toggle");
    private static Whitelist keepInventoryWhitelist;

    // create and load whitelist
    public static void create(File file) {
        // uses vanilla whitelist class for handling keep inventory whitelisting
        keepInventoryWhitelist = new Whitelist(file);
        load();
    }

    // load data from whitelist file
    public static void load() {
        try {
            keepInventoryWhitelist.load();
        }
        catch(Exception error) {
            LOGGER.warn("Failed to load keep inventory whitelist: ", error);
        }
    }

    // check if player is in whitelist
    public static boolean contains(GameProfile player) {
        return keepInventoryWhitelist.isAllowed(player);
    }

    // add player to whitelist
    public static void addPlayer(GameProfile player) {
        keepInventoryWhitelist.add(new WhitelistEntry(player));
    }

    // remove player from whitelist
    public static void removePlayer(GameProfile player) {
        keepInventoryWhitelist.remove(player);
    }
}
