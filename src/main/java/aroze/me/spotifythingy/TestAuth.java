package aroze.me.spotifythingy;

import aroze.me.spotifythingy.util.ChatUtils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TestAuth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        HashMap<Player, String> spotifyAuth = new HashMap<>();

        if (!sender.hasPermission("spotifythingy.admin")) {
            sender.sendMessage(ChatUtils.color("&c⚠ &#ff7f6eYou aren't allowed to do this! smh!"));
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("settoken")) {
                spotifyAuth.put((Player) sender, args[1]);
                sender.sendMessage(ChatUtils.color("&a✔ &#ff7f6eSuccessfully set your auth token!"));
                return true;
            }

            if (args[0].equalsIgnoreCase("play")) {
                HttpResponse<JsonNode> response = Unirest.put("https://api.spotify.com/v1/me/player/play")
                        .basicAuth("Bearer", spotifyAuth.get((Player) sender))
                        .asJson();
                sender.sendMessage(response.getBody().toPrettyString());
                return true;
            }
        }

        String client_id = SpotifyThingy.getInstance().getConfig().getString("Spotify.clientID");
        String client_secret = SpotifyThingy.getInstance().getConfig().getString("Spotify.clientSecret");
        String redirect_uri = SpotifyThingy.getInstance().getConfig().getString("Spotify.redirectURI");

        sender.sendMessage(ChatUtils.color("\n&7&oAttempting to authenticate with Spotify...\n"));
        sender.sendMessage(ChatUtils.color("&cClient ID: &7" + client_id));
        sender.sendMessage(ChatUtils.color("&cClient Secret: &7" + client_secret));
        sender.sendMessage(ChatUtils.color("&cRedirect URI: &7" + redirect_uri));
        sender.sendMessage("\n");

        HttpResponse<JsonNode> response = Unirest.post("https://accounts.spotify.com/api/token")
            .header("accept", "application/json")
            .basicAuth(client_id, client_secret)
            .contentType("application/x-www-form-urlencoded")
            .body("grant_type=client_credentials")
            .asJson();

        sender.sendMessage(response.getBody().toPrettyString());
        String token = response.getBody().getObject().getString("access_token");

        sender.sendMessage(ChatUtils.color("\n&7&oAttempting to get user info...\n"));
        HttpResponse<JsonNode> response2 = Unirest.get("https://api.spotify.com/v1/users/aroze123")
                .header("Authorization", "Bearer " + token)
                .asJson();
        sender.sendMessage(response2.getBody().toPrettyString());

        HttpResponse<JsonNode> response3 = Unirest.get("https://accounts.spotify.com/authorize?")
            .queryString("response_type", "code")
            .queryString("client_id", client_id)
            .queryString("scope", "user-modify-playback-state user-read-playback-state user-read-currently-playing app-remote-control streaming")
            .queryString("redirect_uri", redirect_uri)
            .asJson();

        String url = "https://accounts.spotify.com/authorize";
        url += "?response_type=token";
        url += "&client_id=" + client_id;
        url += "&scope=user-modify-playback-state%20user-read-playback-state%20user-read-currently-playing%20app-remote-control%20streaming";
        url += "&redirect_uri=" + redirect_uri;

        sender.sendMessage(response3.isSuccess() + "");
        sender.sendMessage(response3.getStatusText());
        sender.sendMessage(url);


        return true;
    }
}
