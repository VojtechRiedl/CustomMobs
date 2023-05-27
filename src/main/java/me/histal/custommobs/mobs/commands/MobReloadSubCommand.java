package me.histal.custommobs.mobs.commands;

import me.histal.custommobs.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobReloadSubCommand implements SubCommand {


    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getSyntax() {
        return "§e/custommobs reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1){
            sender.sendMessage("§aUsage §8-> " + getSyntax());
        }

        // If the sender is not a player, reload the config
        if(!(sender instanceof Player)){
            plugin.getConfigurationManager().reload();
            sender.sendMessage("Successfully reloaded");
            return;
        }

        Player player = (Player) sender;
        // If the player does not have permission to reload the config, send them a message
        if(!player.hasPermission("custommobs.reload")){
            player.sendMessage("§cYou don't have permission to use this command");
            return;
        }
        // Reload the config
        plugin.getConfigurationManager().reload();
        player.sendMessage("§aSuccessfully reloaded");

    }
}
