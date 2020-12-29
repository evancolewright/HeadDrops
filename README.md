# HeadDrops
HeadDrops is a bare-bones head-dropping plugin for Spigot 1.13 - 1.16.4. It was originally coded by me in early 2016, and has accrued a whopping **31,000** downloads since then.  Many people love the simplicity, and I plan to stick to that.  Nevertheless, it was almost too simplistic. So, I have added a couple more features, as well as recoded the entire thing from scratch.

### Installation
The installation of this plugin is typical to every server plugin.
1. Download the plugin from [SpigotMC](https://www.spigotmc.org/resources/headdrops.15964/)
2. Transport the jar file to your plugin folder
3. Restart your server

### Permissions
Permission | Description
------------ | -------------
headdrops.guarantee | This permission guarantees the drop of a player head
headdrops.immune | This permission prevents the player from dropping a head.  Keep in mind that this overrides the guarantee permission!

### Configuration
The [configuration](https://github.com/evancolewright/HeadDrops/blob/main/src/main/resources/config.yml) file is pretty self-explanatory.  Everything is heavily commented to ensure that you understand exactly what you are doing.  If you have questions, please PM me on [SpigotMC](https://www.spigotmc.org/members/evanthesurfer.97504/).

### API
Although minimal, the plugin does come with a single event that you can hook into.  This event is fired when a player drops their head. An example of that event is below.
```
    @EventHandler
    public void onDrop(PlayerDropHeadEvent event)
    {
        event.getPlayer().sendMessage("Your head dropped, lol.");
    }
```
**To utilize this event, you will need to add the jar as a dependency, or install to your local maven repository (if you are using Maven).**

### Suggestions
If you have suggestions, or would like to contribute, please PM me on [SpigotMC](https://www.spigotmc.org/members/evanthesurfer.97504/) or submit a pull request.  
