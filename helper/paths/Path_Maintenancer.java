package eu.mixeration.helper.paths;

import eu.mixeration.helper.Helper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Path_Maintenancer {
    private static Helper plugin;
    private static String path;
    private static String folderpath;
    private static File file;
    private static FileConfiguration config;

    public Path_Maintenancer(Helper plugin, String path, String folder) {
        this.plugin = plugin;
        this.path = path;
        folderpath = folder;
        file = null;
        config = null;
    }

    public static void create() {
        file = new File(plugin.getDataFolder() + File.separator + folderpath, path);
        if (!file.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }

    }

    public static FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }

        return config;
    }

    public static void reloadConfig() {
        if (config == null) {
            file = new File(plugin.getDataFolder() + File.separator + folderpath, path);
        }

        config = YamlConfiguration.loadConfiguration(file);

        try {
            Reader defaultConfigStream = new InputStreamReader(plugin.getResource(path), "UTF8");
            if (defaultConfigStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
                config.setDefaults(defaultConfig);
            }
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        } catch (NullPointerException var3) {
            var3.printStackTrace();
        }

    }

    public static void saveConfig() {
        try {
            config.save(file);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public void saveDefaultConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder() + File.separator + folderpath, path);
        }

        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

    }

    public String getPath() {
        return path;
    }

    public String getFolderpath() {
        return folderpath;
    }
}
