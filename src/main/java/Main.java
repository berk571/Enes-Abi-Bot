import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
  
  private static String token = "";
  
  private static IDiscordClient client;
  
  public enum Politician {
    trump, clinton, merkel
  }
  
  public static IVoiceChannel isBusyInGuild(IGuild guild) {
    
    for (IVoiceChannel voiceChannel : client.getConnectedVoiceChannels()) {
      
      if (voiceChannel.getGuild().getID().equals(guild.getID())) {
        return voiceChannel;
      }
      
    }
    return null;
  }
    
  public static void removeGuildFromList(IGuild guild) {
    
    IVoiceChannel channelToLeave = null;
    
    for (IVoiceChannel voiceChannel : client.getConnectedVoiceChannels()) {
    
      if (voiceChannel.getGuild().getID().equals(guild.getID())) {

        channelToLeave = voiceChannel;
        break;

      }
    
    }
    
    if (channelToLeave != null) {
      channelToLeave.leave();
    }
    
  }
  
  public static void main(String[] args) throws FileNotFoundException {
    
    System.setOut(new PrintStream(new FileOutputStream("trump.log")));
    
    for (String arg : args) {
      if (arg.startsWith("--token=")) {
        token = arg.replace("--token=", "");
      }
    }
    
    try {
      client = getClient(token, true); // Gets the client object
      
      EventDispatcher dispatcher = client.getDispatcher(); // Gets the EventDispatcher instance for this client instance
      
      // Register some listeners
      dispatcher.registerListener(new ChatListener()); // Listener which reacts to commands
      dispatcher.registerListener(new TrackFinishedListener()); // Listener which reacts to finished audio
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  // Join channel and play specified audio
  public static void playAudio(IVoiceChannel voiceChannel, IChannel textChannel, File soundFile) {
    // Join channel
    try {
      voiceChannel.join();
  
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
      
      // feed the player with audio
      AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(voiceChannel.getGuild());
      
      player.queue(audioInputStream);
      
    } catch (MissingPermissionsException e) {
      e.printStackTrace();
      Main.writeMessage(textChannel,
          "I hate to tell you this, but I have no permission to join this channel.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  
  public static void writeMessage(IChannel channel, String message) {
    
    try {
      new MessageBuilder(client)
          .withChannel(channel)
          .withContent(message)
          .build();
    } catch (RateLimitException | MissingPermissionsException | DiscordException e) {
      e.printStackTrace();
    }
    
  }
  
  private static IDiscordClient getClient(String token, boolean login) throws Exception { // Returns an instance of the Discord client
    
    if (Main.client == null) {
      ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
      clientBuilder.withToken(token); // Adds the login info to the builder
      if (login) {
        return clientBuilder.login(); // Creates the client instance and logs the client in
      } else {
        return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
      }
    } else
      return Main.client;
  }
}