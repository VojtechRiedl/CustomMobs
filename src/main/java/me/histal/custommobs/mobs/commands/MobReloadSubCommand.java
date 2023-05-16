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


        if(!(sender instanceof Player)){
            plugin.getConfigurationManager().reload();
            sender.sendMessage("Successfully reloaded");
            return;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("custommobs.reload")){
            player.sendMessage("§cYou don't have permission to use this command");
            return;
        }
        plugin.getConfigurationManager().reload();
        player.sendMessage("§aSuccessfully reloaded");

    }
}
