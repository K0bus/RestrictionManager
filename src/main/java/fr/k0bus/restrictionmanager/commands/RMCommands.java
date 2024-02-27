package fr.k0bus.restrictionmanager.commands;

import fr.k0bus.commands.Command;
import fr.k0bus.restrictionmanager.RestrictionManager;
import fr.k0bus.restrictionmanager.commands.cm2.ReloadSubCommand;
import fr.k0bus.restrictionmanager.utils.RMUtils;
import org.bukkit.command.CommandSender;

public class RMCommands extends Command {
    public RMCommands() {
        super("rm", "rm.admin");
        addSubCommands(new ReloadSubCommand());
        setUsage("/rm <argument>");
        setDescription("All command added to RestrictionManager");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        RMUtils.sendRawMessage(sender, RestrictionManager.API.TAG + " RestrictionManager loaded in the server !");
    }
}
