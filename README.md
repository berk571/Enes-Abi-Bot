### Invite to Server
To invite an already working bot hosted by me by visiting:
https://discordapp.com/oauth2/authorize?client_id=247869756609265664&scope=bot&permissions=0


Write `!trump` (or any other command listed below) in any channel to trigger the bot. It will join the Voicechannel of the author and play trump quotes.

#### Commands
|Command|Action|
|:---|:---|
|!trump, !clinton, !merkel|Invoke the bot with corresponding set of audio|
|**Options**|**Action**
|-h, --help|Show this message|
|-c \<channel>|Send the bot to a specified channel|
|-f \<pattern>|Specify sound file to use. Wildcard: *|
|--sounds|List all available sound files|
|--stats|Print a short summary of statistics|
|--leave|Force-leave the current channel|
|-r \<text\>|Contact the creator (bugs, feedback, etc)|

#### Examples

Play 'let-em-talk.mp3' by using wildcards:  
`!trump -f:*let-em*mp3` or  
`!trump -f:let*talk*`

#####
Send the bot to a voice-channel named 'General' and play 'let-em-talk.mp3':  
`!trump -c:General -f:let-em-talk.mp3`

### Installation
If you want to host your own instance, follow below instructions.

Get a copy of the bot:   
`git clone https://github.com/Blogshot/Trump-Bot.git`

Then, just execute the .jar file in the bin/-directory with a token-parameter. The audio-folder must be in the working directory.
```
#!/bin/bash  

# get token from https://discordapp.com/developers/applications/me
cd /root/discord-bots/Trump-Bot/ && java -jar bin/Trump-Bot.jar --token=<token here> &
```

##### Logging
The log can be found in the working directory of the .jar file.
