package fr.k0bus.restrictionmanager;

import fr.k0bus.config.Lang;
import fr.k0bus.restrictionmanager.file.Settings;
import fr.k0bus.restrictionmanager.restrictions.Restriction;
import fr.k0bus.restrictionmanager.utils.RMUtils;
import fr.k0bus.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.Tag;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

public class RMAPI {
    private final Settings settings;
    private final Lang lang;
    private final HashMap<String, Restriction> restrictions;
    private final RestrictionManager instance;
    public String TAG;
    private final HashMap<String, Set<Material>> tagMap = new HashMap<>();

    public RMAPI(RestrictionManager instance)
    {
        this.instance = instance;
        this.restrictions = RMUtils.loadRestrictions(instance);
        this.settings = new Settings(instance);
        this.lang = new Lang(settings.getLang(), instance);
        this.TAG = StringUtils.parse(settings.getTag());
        loadTags();
    }

    public RestrictionManager getInstance() {
        return instance;
    }

    public HashMap<String, Restriction> getProtections() {
        return restrictions;
    }

    public Lang getLang() {
        return lang;
    }

    public Settings getSettings() {
        return settings;
    }

    public void reloadSettings()
    {
        settings.loadConfig();
        lang.loadConfig();
        this.TAG = StringUtils.parse(settings.getTag());
    }

    private void loadTags()
    {
        try
        {
            Field[] fieldlist = Tag.class.getDeclaredFields();
            for (Field fld : fieldlist) {
                try {
                    Set<Material> set = ((Tag<Material>) fld.get(null)).getValues();
                    tagMap.put(fld.getName(), set);
                }catch (Exception ignored)
                {}
            }
            instance.getLogger().log(Level.INFO, "&2Tag loaded from Spigot ! &7[" + tagMap.size() + "]");
        }
        catch (Exception e)
        {
            instance.getLogger().log(Level.WARNING, "&cThis minecraft version could not use the TAG system.");
        }
    }

    public HashMap<String, Set<Material>> getTagMap() {
        return this.tagMap;
    }
}
