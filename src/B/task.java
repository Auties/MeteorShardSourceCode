package B;
import Main.Main;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import java.io.File;
import java.util.List;
import java.util.Random;

public class task {
    public static void repeatingTask(){
        File f = new File(Main.getInstance().getDataFolder() + File.separator + "config.yml");
        YamlConfiguration fc  = YamlConfiguration.loadConfiguration(f);
        boolean isActivated = fc.getBoolean("Plugin-Enabled");
        if(isActivated){
                Random spawn = new Random();
                Random coordinate = new Random();
                Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new BukkitRunnable(){
                    public void run() {
                        if(spawn.nextInt(1000) >= 999){

                            World w = Bukkit.getWorld("world");
                            double x = 10000 + (20000 - 10000) * coordinate.nextDouble();
                            double y = 100 + (200 - 100) * coordinate.nextDouble();
                            double z = 10000 + (20000 - 10000) * coordinate.nextDouble();

                            Location location = new Location(w, z, y, z);
                            w.getChunkAt(location).load();
                            ArmorStand stand = w.spawn(location, ArmorStand.class);
                            stand.setVisible(false);
                            Vector vector = new Vector(0, -1, -1);
                            Velocity.setVelocity(stand,vector);

                            stand.setHelmet(new ItemStack(Material.CHEST, 1, (byte) 0));
                            Bukkit.broadcastMessage(ChatColor.GREEN + "The metor spawned at " + ChatColor.RED + stand.getLocation().getX() + " " + ChatColor.AQUA + stand.getLocation().getY() + " " + ChatColor.YELLOW + stand.getLocation().getZ());
                            InventorySave.entityCheck(stand);
                          }
                        }
                    },0,0);
                }
            }
        }
