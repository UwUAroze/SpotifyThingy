package aroze.me.spotifythingy;

import aroze.me.spotifythingy.util.ChatUtils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.nio.Buffer;

public class TestAuth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("spotifythingy.admin")) {
            sender.sendMessage(ChatUtils.color("&c⚠ &#ff7f6eYou aren't allowed to do this! smh!"));
            return true;
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
            .queryString("scope", "user-read-private user-read-email")
            .queryString("redirect_uri", redirect_uri)
            .asJson();

        sender.sendMessage(response3.isSuccess() + "");
        sender.sendMessage(response3.getStatusText());


        return true;
    }
}
