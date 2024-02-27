package fr.k0bus.restrictionmanager.restrictions;

import fr.k0bus.restrictionmanager.RestrictionManager;
import fr.k0bus.restrictionmanager.utils.RMUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public abstract class Restriction implements Listener {

    private final String id;
    private boolean enabled = true;
    private final RestrictionManager plugin;

    public Restriction(RestrictionManager plugin)
    {
        id = this.getClass().getSimpleName().replace("Restriction", "").toLowerCase();
        this.plugin = plugin;
        if(isCompatible())
        {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            plugin.getLogger().log(Level.INFO, "Protection '" + id + "' loaded from class (" + this.getClass().getSimpleName() + ")");
        }
        else
        {
            plugin.getLogger().log(Level.INFO, "Protection '" + id + "' unloaded for incompatibility");
        }
    }

    public boolean hasPermission(LivingEntity player)
    {
        return player.hasPermission(getPermission());
    }

    public boolean hasPermission(LivingEntity player, String permission)
    {
        return player.hasPermission(getPermission() + "." + permission);
    }

    public String getPermission()
    {
        return "creativemanager." + id;
    }


    public String getId() {
        return id;
    }

    public RestrictionManager getPlugin() {
        return plugin;
    }

    public boolean isDisabled() {
        return !enabled || !isCompatible();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCompatible() {
        return true;
    }

    public void sendPermissionMessage(CommandSender toMessage)
    {
        RMUtils.sendMessage(toMessage, "permission." + getId());
    }
    public void sendPermissionMessage(CommandSender toMessage, String custom)
    {
        RMUtils.sendMessage(toMessage, "permission." + getId() + "." + custom);
    }
}
