# HeadDrops

HeadDrops is a server plugin made for Spigot servers running versions 1.8.8 - 1.18.2.  The plugin alters the player experience by allowing players on the server to drop their heads when they die.

## Building from Source

To build the project, you will need:

- Git
- A compatible JDK for the server version you are running
- Maven (with environment variables set)

1. Clone the repository

   ```bash
   git clone https://github.com/evancolewright/HeadDrops.git
   ```

2. Change to the repository directory

   ```bash
   cd HeadDrops
   ```

3. Generate the .jar

   ```bash
   mvn package
   ```

4. Move the generated .jar (located in PWD/output directory) to your server/plugins directory

5. Start your server

## API

Since v1.5, you are able to hook into various events that are emitted by the plugin and change internal behavior from within your own project.

1. **HeadDropEvent** - Triggered whenever a player dies and drops their head.
The following example shows how you can alter the head that is dropped using this event.

```Java
@EventHandler
public void onHeadDropped(HeadDropEvent event)
{
    // Get the head that is supposed to be dropped
    ItemStack originalHead = event.getHeadDrop();
	
    // Implement your changes... For example, maybe it's Halloween, and you want all head drops to be a pumpkin head
    ItemStack newHead = new ItemStack(Material.PUMPKIN);
    if (originalHead.hasItemMeta()) 
        newHead.setItemMeta(headItem.getItemMeta())
    event.setHeadDrop(newHead);    
}
```

2. **HeadBreakEvent** - Triggered whenever a player breaks a head that has been placed in the world

   *Example coming soon...*

3. **HeadPlaceEvent** - Triggered whenever a player placed a head from their inventory in the world

   *Example coming soon...*

## Additional Information

For more information on this plugin, please refer to the [official plugin page](https://www.spigotmc.org/resources/headdrops-1-8-1-18.15964/).

## License

MIT

