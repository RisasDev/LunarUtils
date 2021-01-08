package dev.risas.lunarutils.utilities.item;

import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCreator {

    private final ItemStack itemStack;

    public ItemCreator(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemCreator(String material) {
        this.itemStack = new ItemStack(Material.valueOf(material), 1);
    }

    public ItemCreator(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public ItemCreator(Material material, int damage) {
        this.itemStack = new ItemStack(material, 1, (short) damage);
    }

    public ItemCreator(Material material, int amount, int damage) {
        this.itemStack = new ItemStack(material, amount, (short) damage);
    }

    public ItemCreator setName(String name) {
        if (name != null) {
            name = CC.translate(name);
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.setDisplayName(name);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setLore(List<String> lore) {
        if (lore != null) {
            List<String> list = new ArrayList<>();
            lore.forEach(line -> list.add(CC.translate(line)));
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.setLore(list);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setLore(String... lore) {
        if (lore != null) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.setLore(CC.translate(Arrays.asList(lore)));
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemCreator setGlow(boolean enchant) {
        if (enchant) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setGlow(boolean enchant, int level) {
        if (enchant) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, level, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setGlow(boolean enchanted, Enchantment enchant, int level) {
        if (enchanted) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(enchant, level, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setDurability(short dur) {
        this.itemStack.setDurability(dur);
        return this;
    }

    public ItemCreator setDurability(int dur) {
        this.itemStack.setDurability((short) dur);
        return this;
    }

    public ItemCreator setOwner(String owner) {
        if (this.itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta meta = (SkullMeta) this.itemStack.getItemMeta();
            meta.setOwner(owner);
            this.itemStack.setItemMeta(meta);
            return this;
        }

        throw new IllegalArgumentException("setOwner() only applicable for Skull Item");
    }

    public ItemCreator setArmorColor(Color color) {
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
            leatherArmorMeta.setColor(color);
            this.itemStack.setItemMeta(leatherArmorMeta);
        }
        catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("Error set armor color.");
        }
        return this;
    }

    public ItemStack get() {
        return this.itemStack;
    }
}