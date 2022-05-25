package aroze.me.spotifythingy;

import aroze.me.spotifythingy.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        BossBar spotifyPlayer = Bukkit.createBossBar(
                ChatUtils.color("&#51e285Spotify: &#1fb177Not Connected"),
                BarColor.GREEN,
                BarStyle.SOLID
        );
        spotifyPlayer.setProgress(0);
        spotifyPlayer.addPlayer(e.getPlayer());
    }

}
