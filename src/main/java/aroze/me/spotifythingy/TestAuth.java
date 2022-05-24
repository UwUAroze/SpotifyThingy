package aroze.me.spotifythingy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestAuth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String client_id = SpotifyThingy.getInstance().getConfig().getString("Spotify.clientID");
        String client_secret = SpotifyThingy.getInstance().getConfig().getString("Spotify.clientSecret");

        sender.sendMessage("Client ID: " + client_id);
        sender.sendMessage("Client Secret: " + client_secret);

        return true;
    }
}
