# BaseModEx
BaseModEx ("Ex" stands for "extension") is a collection of miscellaneous fixes and tweaks for BaseMod as well as an extension of its API.

### Requires:
- Steam version of [Slay the Spire](https://store.steampowered.com/app/646570/Slay_the_Spire/)
- [ModTheSpire](https://steamcommunity.com/sharedfiles/filedetails/?id=1605060445)
- [BaseMod](https://steamcommunity.com/sharedfiles/filedetails/?id=1605833019)

## Features
Check out the [wiki](https://github.com/arqante/BaseModEx/wiki) for the list of features.

## Building
### Requirements:
- Java 8+
- Maven v3.9+
- [ModTheSpire](https://github.com/kiooeht/ModTheSpire) v3.30.3+
- [BaseMod](https://github.com/daviscook477/BaseMod) v5.56.0+

### How to build:
1. Clone this repository to a location of your choice.
2. Inside `pom.xml`, set the `steam.path` property to your `steamapps` folder.
3. Subscribe to [ModTheSpire](https://steamcommunity.com/sharedfiles/filedetails/?id=1605060445) and [BaseMod](https://steamcommunity.com/sharedfiles/filedetails/?id=1605833019) on Steam Workshop if you haven't done so already.
4. Run `mvn package`. The output `.jar` should be inside the game's `mods` folder.
