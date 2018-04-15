package A;


import Main.Main;
import org.bukkit.*;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventorySave implements Listener {
    public static BukkitTask task;

    @EventHandler
    public void close(InventoryCloseEvent event) {
        File f = new File(Main.getInstance().getDataFolder() + File.separator + "A/config.yml");
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
        int count = fc.getInt("Inventory" + ".Items-Number");
        List<ItemStack> items = new ArrayList<>();
        if (event.getInventory().getName().equals(ChatColor.GREEN + "Items")) {
            for (ItemStack stack : event.getInventory()) {
                if (stack != null) {
                    items.add(stack);
                }
            }
            fc.set("Falling-Block" + ".Inventory", items);
            try {
                fc.save(f);
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }
    public static void entityCheck(Entity entity){
        task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println(entity.getLocation());
                if(entity.isOnGround()){
                if(entity instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (armorStand.getHelmet().getType() ==  Material.CYAN_SHULKER_BOX) {
                        Location location = entity.getLocation();
                        location.getBlock().setType(Material.CYAN_SHULKER_BOX);
                        entity.remove();
                        if(Material.CYAN_SHULKER_BOX == Material.CYAN_SHULKER_BOX) {
                            ShulkerBox shulkerBox = (ShulkerBox) location.getBlock().getState();
                            File f = new File(Main.getInstance().getDataFolder() + File.separator + "A/config.yml");
                            YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
                            for (ItemStack stack : (List<ItemStack>) fc.getList("Falling-Block" + ".Inventory")) {
                                shulkerBox.getInventory().addItem(stack);
                            }
                        }else if(Material.CYAN_SHULKER_BOX == Material.CHEST){

                        }
                        Bukkit.getScheduler().cancelTask(task.getTaskId());
                    }
                }
                }
            }
            },0L,20);
       }

    }