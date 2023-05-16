package me.histal.custommobs.mobs.commands;

import me.histal.custommobs.Utils;
import me.histal.custommobs.mobs.enums.CreateMobResult;
import me.histal.custommobs.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MobAddSubCommand implements SubCommand {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getSyntax() {
        return "§e/custommobs add [entity_type] [health] [display_name]";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cYou must be a player to use this command");
            return;
        }
        Player player = (Player) sender;
        if(args.length < 4){
            player.sendMessage("§aUsage §8-> " + getSyntax());
            return;
        }
        String entityType = args[1];
        Double health = Utils.convertToDouble(args[2]);
        if(health == null){
            player.sendMessage("§cInvalid health");
            return;
        }
        String displayName = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));

        CreateMobResult result = plugin.getMobsManager().createNewMob(displayName, entityType, health);

        if(result == CreateMobResult.SUCCESSFUL_CREATE){
            player.sendMessage("§aSuccessfully created mob");
        } else if (result == CreateMobResult.FAIL_ADDING_TO_FILE) {
            player.sendMessage("§cFailed to add mob to file");
        }else if(result == CreateMobResult.FAILED_BUILD) {
            player.sendMessage("§cFailed to create a mob");
        }

    }
}
