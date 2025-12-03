# BaseModEx
BaseModEx ("Ex" stands for "extension") is a collection of various miscellaneous fixes for BaseMod as well as an extension of its API.

### Requires:
- Steam version of [Slay the Spire](https://store.steampowered.com/app/646570/Slay_the_Spire/)
- [ModTheSpire](https://steamcommunity.com/sharedfiles/filedetails/?id=1605060445)
- [BaseMod](https://steamcommunity.com/sharedfiles/filedetails/?id=1605833019)

## Features
Check out the [wiki](https://github.com/arqante/BaseModEx/wiki) for the list of features.

## Building
### Requirements:
- Java 8+
- Maven v3.9.0+
- Lombok v1.18.42+
- [ModTheSpire](https://github.com/kiooeht/ModTheSpire) v3.30.3+
- [BaseMod](https://github.com/daviscook477/BaseMod) v5.56.0+

### How to build:
1. Clone this repo to a location of your choice.
2. Inside `pom.xml`, set the `steam.path` property to the location of Steam's `steamapps` folder on your computer. By default, it's set to `C:\Program Files (x86)\Steam\steamapps`.
3. Subscribe to [ModTheSpire](https://steamcommunity.com/sharedfiles/filedetails/?id=1605060445) and [BaseMod](https://steamcommunity.com/sharedfiles/filedetails/?id=1605833019) on Steam Workshop if you haven't done so already.
4. If you're building with an IDE, make sure you've enabled annotation processing. For IntelliJ IDEA users, go to `Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors` and tick the `Enable annotation processing` checkmark.
5. Run `mvn package` from the project's folder. The output `.jar` should be inside the `..\steamapps\common\SlayTheSpire\mods` directory.
