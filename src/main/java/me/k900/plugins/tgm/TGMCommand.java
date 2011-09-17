package me.k900.plugins.tgm;

import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.config.Configuration;

public class TGMCommand extends PlayerListener implements CommandExecutor {
    private Server server;
    private Configuration config;

    public TGMCommand(Server server, Configuration config) {
        this.server = server;
        this.config = config;
    }

    public GameMode getGM(Object o) {
        if (o instanceof Player) {
            return ((Player) o).getGameMode();
        }
        return GameMode.SURVIVAL;
    }

    public void setGM(Object o, GameMode g) {
        if (o instanceof Player) {
            ((Player) o).setGameMode(g);
        }
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Object target = null;
        String gm = null;
        if (commandSender.isOp() || config.getBoolean("config.allow-all", false)) {
            if (strings.length == 0) {
                target = commandSender;
                gm = "swap";
            }
            if (strings.length == 1) {
                if (strings[0].equals("c")) {
                    target = commandSender;
                    gm = "creative";
                } else if (strings[0].equals("s")) {
                    target = commandSender;
                    gm = "survival";
                } else {
                    Player p = server.getPlayer(strings[0]);
                    if (p != null) {
                        target = p;
                        gm = "swap";
                    }
                }
            }
            if (strings.length == 2) {
                Player p = server.getPlayer(strings[0]);
                if (p != null) {
                    target = p;
                }
                if (strings[1].equals("c")) {
                    gm = "creative";
                }
                if (strings[1].equals("s")) {
                    gm = "survival";
                }
            }
            if ((target != null) && (gm != null)) {
                if (gm.equals("swap")) {
                    swapGM(target);
                    return true;
                } else if (gm.equals("creative")) {
                    setGM(target, GameMode.CREATIVE);
                    return true;
                } else if (gm.equals("survival")) {
                    setGM(target, GameMode.SURVIVAL);
                    return true;
                }
            }
        }
        return false;
    }

    private void swapGM(Object o) {
        if (getGM(o) == GameMode.SURVIVAL) {
            setGM(o, GameMode.CREATIVE);
        } else {
            setGM(o, GameMode.SURVIVAL);
        }
    }

    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        World w = event.getTo().getWorld();
        String val = config.getString("worlds." + w.getName(), "none");
        if (val.equals("c")) {
            event.getPlayer().setGameMode(GameMode.CREATIVE);
        }
        if (val.equals("s")) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }
}
