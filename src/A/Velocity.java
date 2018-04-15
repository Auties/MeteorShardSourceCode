package A;

import Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;


public class Velocity {
    private static BukkitTask task;
    public static void setVelocity(ArmorStand armorStand,Vector vector) {
          task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(),new BukkitRunnable() {
            @Override
            public void run() {
                if(armorStand.isOnGround()){
                    task.cancel();
                }else{
                    armorStand.setVelocity(vector);
                }
            }
        },1,0);

    }
}
