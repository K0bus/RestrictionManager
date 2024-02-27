package fr.k0bus.restrictionmanager;

import fr.k0bus.restrictionmanager.commands.RMCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class RestrictionManager extends JavaPlugin {

    public static RMAPI API;


    @Override
    public void onEnable() {
        // Plugin startup logic
        API = new RMAPI(this);
        new RMCommands().register(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
