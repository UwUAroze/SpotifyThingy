package aroze.me.spotifythingy;

import aroze.me.spotifythingy.util.ChatUtils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

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

        if (TestAuth.spotifyAuth.containsKey(e.getPlayer().getUniqueId())) {

            e.getPlayer().sendMessage("a");

            BukkitTask updateSpotifyBar = Bukkit.getScheduler().runTaskTimer(SpotifyThingy.getInstance(), () -> {

                HttpResponse<JsonNode> response = Unirest.get("https://api.spotify.com/v1/me/player/currently-playing")
                        .header("Authorization", "Bearer " + TestAuth.spotifyAuth.get(e.getPlayer().getUniqueId()))
                        .asJson();

                boolean isPlaying = response.getBody().getArray().getJSONObject(0).getBoolean("is_playing");
                String name = response.getBody().getArray().getJSONObject(0).getJSONObject("item").getString("name");
                double progress = response.getBody().getArray().getJSONObject(0).getInt("progress_ms");
                double duration = response.getBody().getArray().getJSONObject(0).getJSONObject("item").getInt("duration_ms");
                double barProgress = progress / duration;

                if (isPlaying) {
                    spotifyPlayer.setProgress(barProgress);
                    spotifyPlayer.setTitle(ChatUtils.color("&#51e285▶ &#1fb177" + name));
                } else {
                    spotifyPlayer.setTitle(ChatUtils.color("&#51e285⏸ &#1fb177" + name));
                }

            }, 0, 20);
        }
    }

}
