package com.pavelorekhov;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class App extends ListenerAdapter {

    private static void setUpJira() {
        String username = MiscUtils.getUsernameFromCMD();
        String password = MiscUtils.getPasswordFromCMD();
        JiraUtils.setUsername(username);
        JiraUtils.setPassword(password);
    }

    private static void setUpDiscord() throws Exception {
        new JDABuilder(AccountType.BOT)
                .setToken(DiscordUtils.TOKEN)
                .addEventListener(new App())
                .buildBlocking();
    }

    public static void main(String[] args) throws Exception {
        setUpJira();
        setUpDiscord();
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel messageChannel = event.getChannel();
        String messageString = message.getContentRaw().toLowerCase();
        if (GeneralUtils.DICTIONARY.containsKey(messageString)) {
            messageChannel.sendMessage(GeneralUtils.DICTIONARY.get(messageString).get()).queue();
        } else if (messageString.matches(JiraUtils.issueInfoRegex)) {
            String[] infoArguments = messageString.split("\\s+");
            String info = JiraUtils.getIssueInfo(infoArguments[0], infoArguments[1]);
            messageChannel.sendMessage(info).queue();
        }
    }
}
