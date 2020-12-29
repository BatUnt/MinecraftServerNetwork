package org.mcservernetwork.client.task;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mcservernetwork.commons.KeepAliveHandler;
import org.mcservernetwork.client.util.ColorUtils;
import org.mcservernetwork.client.util.SectorLocationUtils;
import org.mcservernetwork.client.util.StringUtils;
import org.mcservernetwork.commons.net.Sector;
import org.mcservernetwork.commons.net.packet.PacketStatus;

public class ActionBarTask implements Runnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location location = player.getLocation();

            double distance = SectorLocationUtils.distance(location);
            Sector nearest = SectorLocationUtils.getNearest(location);
            if (distance > SectorLocationUtils.MAX_DISTANCE || nearest == null) {
                continue;
            }
            String name = StringUtils.capitalizeFirstLetter(nearest.getSectorName());
            PacketStatus status = KeepAliveHandler.get(nearest.getSectorName());
            if (status == null) {
                player.spigot()
                        .sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtils.fixColors("&4\u2716 Sector &4&l" + name + " &4is not responding. \u2716")));
            } else {
                player.spigot()
                        .sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtils.fixColors("&a\u2714 TPS: &a&l" + status.tps + "&a, online: &a&l" + status.players + " &a\u2714")));
            }
        }
    }
}
