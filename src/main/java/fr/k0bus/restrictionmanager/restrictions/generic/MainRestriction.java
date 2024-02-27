package fr.k0bus.restrictionmanager.restrictions.generic;

import fr.k0bus.restrictionmanager.RestrictionManager;
import fr.k0bus.restrictionmanager.restrictions.Restriction;
import fr.k0bus.restrictionmanager.type.CustomType;
import fr.k0bus.restrictionmanager.type.ListType;
import fr.k0bus.restrictionmanager.utils.RMUtils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MainRestriction extends Restriction {
    public MainRestriction(RestrictionManager plugin) {
        super(plugin);
    }

    @Override
    public String getId() {
        return "restriction";
    }

    public ListType getType(CustomType type)
    {
        return ListType.fromString(
                RestrictionManager.API.getSettings().getString(
                        "restrictions." + type.getId() + ".type"
                )
        );
    }
    public List<String> getList(CustomType type)
    {
        return RestrictionManager.API.getSettings().getStringList(
                "restrictions." + type.getId() + ".list"
        );
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        if(needCancel(event.getPlayer(), event.getBlock().getType().name(), CustomType.PLACE))
        {
            event.setCancelled(true);
            sendPermissionMessage(event.getPlayer(), CustomType.PLACE.getId());
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if(needCancel(event.getPlayer(), event.getBlock().getType().name(), CustomType.BREAK))
        {
            event.setCancelled(true);
            sendPermissionMessage(event.getPlayer(), CustomType.BREAK.getId());
        }
    }
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event)
    {
        if(event.getClickedBlock() != null && needCancel(event.getPlayer(), event.getClickedBlock().getType().name(), CustomType.BLOCK_USE))
        {
            event.setCancelled(true);
            sendPermissionMessage(event.getPlayer(), CustomType.BLOCK_USE.getId());
        } else if (event.getItem() != null && needCancel(event.getPlayer(), event.getItem().getType().name(), CustomType.ITEM_USE)) {
            event.setCancelled(true);
            sendPermissionMessage(event.getPlayer(), CustomType.ITEM_USE.getId());
        }
    }
    @EventHandler
    public void onCreativeInventory(InventoryCreativeEvent event)
    {
        if(needCancel(event.getWhoClicked(), event.getCursor().getType().name(), CustomType.STORE_ITEM))
        {
            event.setCancelled(true);
            event.setCursor(new ItemStack(Material.AIR));
            sendPermissionMessage(event.getWhoClicked(), CustomType.BLOCK_USE.getId());
        }
        if (event.getCurrentItem() != null && needCancel(event.getWhoClicked(), event.getCurrentItem().getType().name(), CustomType.ITEM_USE)) {
            event.setCancelled(true);
            event.setCurrentItem(new ItemStack(Material.AIR));
            sendPermissionMessage(event.getWhoClicked(), CustomType.ITEM_USE.getId());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event)
    {
        if(needCancel(event.getPlayer(), event.getMessage(), CustomType.COMMANDS))
        {
            event.setCancelled(true);
            sendPermissionMessage(event.getPlayer(), CustomType.COMMANDS.getId());
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event)
    {
        if(event.getRecipe() != null)
            if(needCancel(event.getView().getPlayer(), event.getRecipe().getResult().getType().name(), CustomType.CRAFT))
            {
                event.getRecipe().getResult().setType(Material.AIR);
            }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if(needCancel(event.getPlayer(), event.getItemDrop().getType().name(), CustomType.DROP))
        {
            event.setCancelled(true);
            sendPermissionMessage(event.getPlayer(), CustomType.DROP.getId());
        }
    }
    @EventHandler
    public void onPickup(EntityPickupItemEvent event)
    {
        if(needCancel(event.getEntity(), event.getItem().getType().name(), CustomType.PICKUP))
        {
            event.setCancelled(true);
            sendPermissionMessage(event.getEntity(), CustomType.PICKUP.getId());
        }
    }
    private boolean needCancel(LivingEntity player, String s, CustomType type)
    {
        if(isDisabled()) return false;
        if(hasPermission(player, type.getId())) return false;
        if(s == null) return false;
        if(hasPermission(player, type.getId() + "." + s)) return false;
        return RMUtils.inList(s.toLowerCase(), getList(type)) == getType(type).isBlacklistMode();
    }
}
