

package Main;
import A.InventorySave;
import A.cmd;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private static Main p;
    @Override
    public void onEnable() {
        p = this;
        register();
        File f = new File(Main.getInstance().getDataFolder() + "config.yml");
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            saveResource("config.yml", false);
        }


    }
    @Override
    public void onDisable() {
        p=null;

    }
    public static Main getInstance(){
        return p;
    }
    public void register(){
        String version = Bukkit.getServer().getVersion();
        if(version.contains("1.12") || version.contains("1.11")){
            getCommand("meteor").setExecutor(new A.cmd());
            Bukkit.getPluginManager().registerEvents(new A.InventorySave(), this);
            A.task.repeatingTask();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Your mc version is supported and the plugin was successfully enabled!");

        }else if(version.contains("1.8")){
            getCommand("meteor").setExecutor(new B.cmd());
            Bukkit.getPluginManager().registerEvents(new B.InventorySave(), this);
            B.task.repeatingTask();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Your mc version is supported and the plugin was successfully enabled!");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Your mc version is not compatible with this plugin!");
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
    }
}
