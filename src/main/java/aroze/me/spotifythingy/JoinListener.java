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

            HttpResponse<JsonNode> response = Unirest.get("https://api.spotify.com/v1/me/player/currently-playing")
                    .header("Authorization", "Bearer " + TestAuth.spotifyAuth.get(e.getPlayer().getUniqueId()))
                    .asJson();

            String name = response.getBody().getArray().getJSONObject(0).getJSONObject("item").getString("name");
            double progress = response.getBody().getArray().getJSONObject(0).getInt("progress_ms");
            double duration = response.getBody().getArray().getJSONObject(0).getJSONObject("item").getInt("duration_ms");


            e.getPlayer().sendMessage("name: " + name);
            e.getPlayer().sendMessage("progress: " + progress);
            e.getPlayer().sendMessage("duration: " + duration);
            e.getPlayer().sendMessage("progress%: " + (Double) (progress/duration));


            //progress_ms =

            BukkitTask updateSpotifyBar = Bukkit.getScheduler().runTaskTimer(SpotifyThingy.getInstance(), () -> {

            }, 0, 1);
        }
    }

}
