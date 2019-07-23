package dev.seeruk.mc.autoequip

import org.bukkit.Server
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * This file is part of the "mc-autoequip" project.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the LICENSE is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
class Main : JavaPlugin() {
    override fun onEnable() {
        val listener = BreakListener(server)

        server.pluginManager.registerEvents(listener, this)
        server.logger.info("Hello, AutoEquip!")
    }
}

class BreakListener(val server: Server) : Listener {
    @EventHandler
    fun onPlayerItemBreak(evt: PlayerItemBreakEvent) {
        this.server.logger.info("${evt.player.displayName} broke ${evt.brokenItem.type.name}")
    }
}
