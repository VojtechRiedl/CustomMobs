package me.histal.custommobs.mobs.commands;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.SubCommand;
import me.histal.custommobs.mobs.MobBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomMobsCommand implements TabExecutor {

    List<SubCommand> subCommands;

    CustomMobs plugin;

    public CustomMobsCommand() {
        this.plugin = CustomMobs.getInstance();
        this.subCommands = new ArrayList<>();
        subCommands.add(new MobAddSubCommand());
        subCommands.add(new MobReloadSubCommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            sender.sendMessage("§cUsage: /custommobs <add|reload>");
            return true;
        }

        for(SubCommand subCommand : subCommands) {
            if(subCommand.getName().equalsIgnoreCase(args[0])) {
                subCommand.execute(sender, args);
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            return subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());
        }
        else if(args.length == 2 && args[0].equalsIgnoreCase("add")){
            return Stream.of(MobBuilder.getPossibleTypes()).map(Enum::toString).collect(Collectors.toList());
        }
        return null;
    }
}
