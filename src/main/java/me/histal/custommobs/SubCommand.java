package me.histal.custommobs;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    CustomMobs plugin = CustomMobs.getInstance();

    String getName();

    String getSyntax();

    void execute(CommandSender sender, String[] args);


}
