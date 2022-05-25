package aroze.me.spotifythingy.commands;

import aroze.me.spotifythingy.util.ChatUtils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        HttpResponse<JsonNode> response = Unirest.get("https://api.spotify.com/v1/me/player/currently-playing")
                .header("Authorization", "Bearer " + TestAuth.spotifyAuth.get(player.getUniqueId()))
                .asJson();

        boolean isPlaying = response.getBody().getArray().getJSONObject(0).getBoolean("is_playing");

        if (!isPlaying) {
            HttpResponse<JsonNode> playResponse = Unirest.put("https://api.spotify.com/v1/me/player/play")
                    .header("Authorization", "Bearer " + TestAuth.spotifyAuth.get(player.getUniqueId()))
                    .asJson();
            sender.sendMessage(ChatUtils.color("&7&oResumed Spotify playback"));
            return true;
        }

        HttpResponse<JsonNode> pauseResponse = Unirest.put("https://api.spotify.com/v1/me/player/pause")
                .header("Authorization", "Bearer " + TestAuth.spotifyAuth.get(player.getUniqueId()))
                .asJson();
        sender.sendMessage(ChatUtils.color("&7&oAlready playing so paused Spotify playback"));


        return true;
    }
}
