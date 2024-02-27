package fr.k0bus.restrictionmanager.utils;

import fr.k0bus.restrictionmanager.RestrictionManager;
import fr.k0bus.restrictionmanager.restrictions.Restriction;
import fr.k0bus.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class RMUtils {
    public static HashMap<String, Restriction> loadRestrictions(RestrictionManager plugin) {
        HashMap<String, Restriction> protectionHashMap = new HashMap<>();
        Reflections reflections = new Reflections("fr.k0bus.restrictionmanager.restrictions");
        Set<Class<? extends Restriction>> classes = reflections.getSubTypesOf(Restriction.class);
        for (Class<? extends Restriction> aClass : classes) {
            try {
                Restriction protection = (Restriction) aClass.getConstructors()[0].newInstance(plugin);
                protectionHashMap.put(protection.getId(), protection);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return protectionHashMap;
    }

    public static void sendMessage(CommandSender messageTo, String text)
    {
        messageTo.sendMessage(parse(StringUtils.parse(RestrictionManager.API.getLang().getString(text))));
    }
    public static void sendRawMessage(CommandSender messageTo, String text)
    {
        messageTo.sendMessage(parse(StringUtils.parse(text)));
    }

    public static String parse(String string)
    {
        return string.replace("{TAG}", RestrictionManager.API.TAG);
    }
    public static boolean inList(ItemStack itemStack, List<String> strings)
    {
        return inList(itemStack.getType(), strings);
    }
    public static boolean inList(Block block, List<String> strings)
    {
        return inList(block.getType(), strings);
    }
    public static boolean inList(Material material, List<String> strings)
    {
        return inList(material.name().toLowerCase(), strings);
    }

    public static boolean inList(String search, List<String> list) {
        for(String s : list)
        {
            s = s.toLowerCase();
            if(s.equals("*")) return true;
            if(s.isEmpty()) continue;
            if(s.startsWith("*") && s.endsWith("*"))
                if(search.contains(s.substring(1, s.length() -1)))
                    return true;
            if(s.startsWith("*"))
                if(search.endsWith(s.substring(1)))
                    return true;
            if(s.endsWith("*"))
                if(search.startsWith(s.substring(0, s.length() -1)))
                    return true;
            if(s.equals(search))
                return true;
            if(s.startsWith("#"))
            {
                Set<Material> set = RestrictionManager.API.getTagMap().get(s.substring(1).toUpperCase());
                if(set != null)
                {
                    if(set.contains(Material.valueOf(search))) return true;
                }
                else
                {
                    RestrictionManager.API.getInstance().getLogger()
                            .log(Level.WARNING, "Unable to find " + s + " tags");
                }
            }
        }
        return false;
    }
}
