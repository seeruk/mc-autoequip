package dev.seeruk.mc.autoequip.event

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.inventory.ItemStack

/**
 * BreakListener is a listener that listens for the PlayerItemBreakEvent. If an item breaks, and
 * another of the same Material exists in the player's inventory, we'll replace the item in the same
 * hand the broken item was in with that item in the inventory (i.e. move it to the hand).
 */
class BreakListener : Listener {
    @EventHandler
    fun onPlayerItemBreak(evt: PlayerItemBreakEvent) {
        val brokenItem = evt.brokenItem
        val player = evt.player

        val brokeMainHand = player.inventory.itemInMainHand == brokenItem

        if (this.hasItemInInventory(brokenItem, player)) {
            // Find the first item that matches the broken item. It should definitely exist at this
            // point, so it's safe to continue without checking.
            val replacementItem = this.getFirstMatchingItem(brokenItem, player)
            val replacementIndex = player.inventory.indexOf(replacementItem)

            if (brokeMainHand) {
                player.inventory.setItemInMainHand(replacementItem.clone())
            } else {
                player.inventory.setItemInOffHand(replacementItem.clone())
            }

            // Remove the original item, at the index it was found at.
            player.inventory.setItem(replacementIndex, null)
        }

        player.updateInventory()
    }

    /**
     * Find the first item in the contents of the given PlayerInventory that matches the given
     * ItemStack.
     *
     * @param itemStack The item to try to find.
     * @param player The player whose inventory we're looking in.
     * @return The first matching item.
     */
    private fun getFirstMatchingItem(itemStack: ItemStack, player: Player): ItemStack {
        // Make sure we're not replacing the item with the broken item.
        return player.inventory.contents.first { item ->
            item != null && item != itemStack && item.type == itemStack.type
        }
    }

    /**
     * Check if the given ItemStack exists in the contents of the given PlayerInventory.
     *
     * @param itemStack The item to try to find.
     * @param player The player whose inventory we're looking in.
     * @return True if the PlayerInventory's contents contains the given ItemStack.
     */
    private fun hasItemInInventory(itemStack: ItemStack, player: Player): Boolean {
        return player.inventory.contents.any { item ->
            item != null && item != itemStack && item.type == itemStack.type
        }
    }
}
