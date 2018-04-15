package A;


import Main.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;

public class cmd implements CommandExecutor {
    public static BukkitTask task;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            File f = new File(Main.getInstance().getDataFolder() + File.separator + "config.yml");
            YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Use /meteor help to see the available commands!");
            }else {
                if (args[0].equalsIgnoreCase("enable")) {
                    if(player.hasPermission("meteor.normal") || player.hasPermission("meteor.op")) {
                        fc.set("Plugin-Enabled", true);
                        try {
                            fc.save(f);
                        } catch (IOException e) {
                            System.out.println("Error while saving the config!");
                            return true;
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");
                    }
                }else if (args[0].equalsIgnoreCase("setItems")) {
                    if(player.hasPermission("metor.medium") ||  player.hasPermission("meteor.op")) {
                        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Items");
                        player.openInventory(inventory);
                    }else{
                        player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");
                    }
                }else if(args[0].equalsIgnoreCase("start")){
                    if(player.hasPermission("metor.medium") ||  player.hasPermission("meteor.op")) {
                        World w = Bukkit.getWorld("world");
                        double x = Math.rint(player.getLocation().getX());
                        double y = Math.rint(player.getLocation().getY());
                        double z = Math.rint(player.getLocation().getZ());
                        Location location = new Location(w, z, y, z);
                        ArmorStand stand = w.spawn(location, ArmorStand.class);
                        Vector vector = new Vector(0, -1, -1);
                        Velocity.setVelocity(stand, vector);
                        stand.setVisible(false);
                        stand.setInvulnerable(true);
                        stand.setHelmet(new ItemStack(Material.CYAN_SHULKER_BOX, 1, (byte) 0));
                        stand.getLocation().getWorld().spigot().playEffect(stand.getLocation(), Effect.FLAME);
                        Bukkit.broadcastMessage(ChatColor.GREEN + "The metor spawned at " + ChatColor.RED + stand.getLocation().getX() + " " + ChatColor.AQUA + stand.getLocation().getY() + " " + ChatColor.YELLOW + stand.getLocation().getZ());
                        InventorySave.entityCheck(stand);
                    }else{
                        player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");
                    }
                }else if(args[0].equalsIgnoreCase("disable")){
                    if(player.hasPermission("meteor.normal") || player.hasPermission("meteor.op")) {
                        fc.set("Plugin-Enabled", false);
                        try {
                            fc.save(f);
                        } catch (IOException e) {
                            System.out.println("Error while saving the config!");
                            return true;
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");
                    }
                }else if(args[0].equalsIgnoreCase("help")){
                    if(player.hasPermission("meteor.base") || player.hasPermission("meteor.op")) {
                        player.sendMessage("---------------------------------------------------");
                        player.sendMessage("                                                   ");
                        player.sendMessage(ChatColor.RED + "                            Meteor Shard                   ");
                        player.sendMessage("                                                   ");
                        player.sendMessage("---------------------------------------------------");
                        player.sendMessage(ChatColor.GOLD + "/meteor enable" + ChatColor.WHITE + " Enable the plugin!");
                        player.sendMessage(ChatColor.GOLD + "/meteor disable" + ChatColor.WHITE + " Disable the plugin!");
                        player.sendMessage(ChatColor.GOLD + "/meteor setItems" + ChatColor.WHITE + " Add items to the meteor inventory!");
                        player.sendMessage(ChatColor.GOLD + "/meteor start" + ChatColor.WHITE + " Force a meteor to spawn in your current location!");
                    }else{
                        player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");
                    }
                }
            }
        }
        return false;
    }
}
