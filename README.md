# HeadDrops

### Important Note
__As of March 3rd, 2024, this repository has been archived and will not receive more updates.__  I don't have time to put into this project currently, but I encourage anybody that's adamant about fixing it to fork it and make it better! 

HeadDrops is a server plugin made for Spigot servers running versions 1.8.8 - 1.18.2.  The plugin alters the player experience by allowing players on the server to drop their heads when they die.

## Building from Source

To build the project, you will need:

- [Git](https://git-scm.com/)
- A compatible [JDK](https://www.oracle.com/java/technologies/downloads/) for the server version you are running
- [Maven](https://maven.apache.org/) (with environment variables set)

1. Clone the repository

   ```bash
   git clone https://github.com/evancolewright/HeadDrops.git
   ```

2. From the root directory of the cloned source code, run:

   ```bash
   mvn package
   ```

3. Move the generated JAR file (located in /target directory) to your server/plugins directory

4. Start your server

## API

The plugin contains a custom event that you can hook into that allows you alter the default behavior of the plugin from your own plugin.  Below is an example of how you can make all head drops be pumpkins.

```
@EventHandler
public void onHeadDropped(HeadDropEvent event)
{
    // Get the head that is supposed to be dropped
    ItemStack originalHead = event.getHeadDrop();
	
    // Implement your changes... For example, maybe it's Halloween, and you want all head drops to be a pumpkin head
    event.setHeadDrop(new ItemStack(Material.PUMPKIN));    
}
```

## Additional Information

For more information on this plugin, please refer to the [official plugin page](https://www.spigotmc.org/resources/headdrops-1-8-1-18.15964/).

## License

MIT
