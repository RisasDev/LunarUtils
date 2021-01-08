package dev.risas.lunarutils.manager.type;

import dev.risas.lunarutils.utilities.item.ItemCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public enum WaypointType {

    WHITE("&f&lWhite", -1, 0),
    PURPLE("&5&lPurple", -65281, 2),
    CYAN("&b&lCyan", -16711681, 3),
    YELLOW("&eYellow", -256, 4),
    GREEN("&a&lGreen", -16711936, 5),
    GRAY("&7&lGray", -3355444, 8),
    BLUE("&1&lBlue", -16776961, 11),
    RED("&4&lRed", -65536, 14),
    BLACK("&8&lBlack", -16777216, 15);

    private final String name;
    private final int color;
    private final int data;

    public ItemStack getItem() {
        return new ItemCreator(Material.WOOL)
                .setDurability(this.getData())
                .setName(this.getName() + " Color")
                .setLore("", "&aClick to select this color.")
                .get();
    }
}
