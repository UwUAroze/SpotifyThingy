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

        sender.sendMessage(ChatUtils.color("\n&7&oAttempting to authenticate with Spotify...\n"));
        sender.sendMessage(ChatUtils.color("&cClient ID: &7" + client_id));
        sender.sendMessage(ChatUtils.color("&cClient Secret: &7" + client_secret));
        sender.sendMessage("\n");

        HttpResponse<JsonNode> response = Unirest.post("https://accounts.spotify.com/api/token")
            .header("accept", "application/json")
            .basicAuth(client_id, client_secret)
            .contentType("application/x-www-form-urlencoded")
                .body("grant_type=client_credential")
            .asJson();

        sender.sendMessage(response.getBody().toPrettyString());

        return true;
    }
}
