package io.github.saltytt.grouptracker.core;

import io.github.saltytt.grouptracker.commands.*;
import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class Bot {

    public static JDA groupTracker;
    String token;
    static CommandHandler commandHandler;
    static ReactionHandler reactionHandler;

    public Bot(String key) {
        this.token = key;
        try { start(); }
        catch (LoginException | InterruptedException | RateLimitedException e) { e.printStackTrace(); }
    }

    private void start() throws LoginException, RateLimitedException, InterruptedException {
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
                .registerCommand(new ForceCommand());
        reactionHandler = new ReactionHandler();
        DistrictManager.standard.refreshDistricts();
        GroupManager.standard.init();

    }
}
