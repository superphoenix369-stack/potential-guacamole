# Wearable Relics (Fabric 26.3)

By Zac Supinger. Turns a Totem of Undying or a Beacon into a wearable amulet, worn in Trinkets slots.
See `MODRINTH.md` for the player-facing description (paste it into the Modrinth page).

## Building with GitHub
1. Create a new GitHub repository and upload **everything in this folder**, including the hidden `.github` folder and the `gradle` folder.
2. GitHub builds the mod automatically on every push (the **Actions** tab). The first build takes about 5 minutes.
3. Open the finished run and download **Wearable-Relics-jar** under *Artifacts*. Inside the zip is `wearable-relics-1.0.0+26.3.jar`, which is the file you upload to Modrinth.

To build locally instead, install Java 25 and run `./gradlew build`. The jar ends up in `build/libs/`.

## Modrinth upload settings
- Loader: **Fabric**. Game version: **26.3**. Environment: **client and server**.
- Dependencies: Fabric API (required), Trinkets Updated (required).

## Project layout
| Path | What it is |
|---|---|
| `src/main/java/.../WearableRelics.java` | Mod entry point and creative menu entries |
| `src/main/java/.../TotemAmuletHandler.java` | Saves the wearer from death and empties the amulet |
| `src/main/java/.../item/BeaconAmuletItem.java` | Applies the effect while worn and removes it when taken off |
| `src/main/resources/data/wearablerelics/recipe/` | All recipes (crafting and smithing) |
| `src/main/resources/data/trinkets/` | Adds the Necklace and Pendant slots to players |
| `src/main/resources/assets/wearablerelics/trinkets/` | How each amulet is drawn on the player's chest |

## Things to test in-game first
1. Craft an Empty Amulet, then make a Totem Amulet at the Smithing Table with the left slot empty.
2. Wear it, then die (for example, jump off a tall tower in Survival). You should survive, see the totem animation, and the slot should now hold an Empty Amulet.
3. Make a Beacon Amulet with each material and check the tooltip and the effect icon. Take it off and the effect should disappear.
4. Upgrade it with a Nether Star and check that the effect becomes level II and the amulet shimmers.
5. Wear both and check that both necklaces show on your character in third person (F5).
