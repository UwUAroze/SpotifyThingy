package aroze.me.spotifythingy;

import aroze.me.spotifythingy.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestAuth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String client_id = SpotifyThingy.getInstance().getConfig().getString("Spotify.clientID");
        String client_secret = SpotifyThingy.getInstance().getConfig().getString("Spotify.clientSecret");

        sender.sendMessage(ChatUtils.color("\n&7&oAttempting to authenticate with Spotify...\n"));
        sender.sendMessage(ChatUtils.color("&cClient ID: &7" + client_id));
        sender.sendMessage(ChatUtils.color("&cClient Secret: &7" + client_secret));
        sender.sendMessage("\n");

        return true;
    }
}
