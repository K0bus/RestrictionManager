package fr.k0bus.restrictionmanager.commands.cm2;

import fr.k0bus.commands.SubCommands;
import fr.k0bus.restrictionmanager.RestrictionManager;
import fr.k0bus.restrictionmanager.utils.RMUtils;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends SubCommands {
    public ReloadSubCommand() {
        super("reload", "cm2.admin.reload");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        RestrictionManager.API.reloadSettings();
        RMUtils.sendRawMessage(sender, RestrictionManager.API.TAG + " &2Settings & Language reloaded !");
    }
}
