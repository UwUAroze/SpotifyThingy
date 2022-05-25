package aroze.me.spotifythingy.commands;

import aroze.me.spotifythingy.util.ChatUtils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkipBackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        HttpResponse<JsonNode> skipResponse = Unirest.post("https://api.spotify.com/v1/me/player/previous")
                .header("Authorization", "Bearer " + TestAuth.spotifyAuth.get(player.getUniqueId()))
                .asJson();
        sender.sendMessage(ChatUtils.color("&7&oSkipped to previous song"));

        return true;
    }
}
