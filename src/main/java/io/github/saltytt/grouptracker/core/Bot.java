package io.github.saltytt.grouptracker.core;

import io.github.saltytt.grouptracker.Main;
import io.github.saltytt.grouptracker.commands.HelpCommand;
import io.github.saltytt.grouptracker.commands.admin.RestartCommand;
import io.github.saltytt.grouptracker.commands.admin.ShutdownCommand;
import io.github.saltytt.grouptracker.commands.districts.DownCommand;
import io.github.saltytt.grouptracker.commands.districts.ForceCommand;
import io.github.saltytt.grouptracker.commands.districts.NotifyCommand;
import io.github.saltytt.grouptracker.commands.groups.*;
import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class Bot {

    public static JDA groupTracker;
    public ArrayList<String> adminList;
    public String prefix;
    String token;
    public static CommandHandler commandHandler;
    static ReactionHandler reactionHandler;

    public Bot(String key, String prefix, ArrayList<String> adminList) {
        this.token = key;
        this.prefix = prefix;
        this.adminList = adminList;
        try { start(); }
        catch (LoginException | InterruptedException e) { e.printStackTrace(); }
    }

    private void start() throws LoginException, InterruptedException {
        groupTracker = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(new MessageListener())
                .buildBlocking();

        commandHandler = new CommandHandler()
                .registerCommand(new AddCommand())
                .registerCommand(new BumpCommand())
                .registerCommand(new ChannelCommand())
                .registerCommand(new CreateCommand())
                .registerCommand(new DisbandCommand())
                .registerCommand(new DisbandAllCommand())
                .registerCommand(new JoinCommand())
                .registerCommand(new LeaveCommand())
                .registerCommand(new RemoveCommand())
                .registerCommand(new PromoteCommand())
                .registerCommand(new ForceCommand())
                .registerCommand(new DownCommand())
                .registerCommand(new ShutdownCommand())
                .registerCommand(new RestartCommand())
                .registerCommand(new NotifyCommand())
                .registerCommand(new HelpCommand());
        reactionHandler = new ReactionHandler();
        DistrictManager.standard.refreshDistricts();
        GroupManager.standard.init();
    }
}
