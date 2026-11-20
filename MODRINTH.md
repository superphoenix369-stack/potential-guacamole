# Wearable Relics

**Wear your Totem of Undying and your Beacon around your neck.**

Wearable Relics turns two of Minecraft's most powerful items into amulets. You wear them in accessory slots, so they stay active without taking up your offhand or an inventory slot.

## Totem Amulet
Wear it in the **Necklace** slot. When you would die, it saves you exactly like a Totem of Undying: you get Regeneration II, Absorption II, Fire Resistance and the totem animation.

- A Totem held in your hand is always used first, so the amulet's charge isn't wasted.
- Once used, the amulet turns back into an **Empty Amulet** that stays in the slot. Add a new Totem at a Smithing Table to recharge it.
- Like a vanilla Totem, it doesn't protect you from the void or `/kill`.

## Beacon Amulet
Wear it in the **Pendant** slot to get one beacon effect, wherever you are.

- The effect only works while you're **wearing** it. Holding it or carrying it in your inventory does nothing.
- Only the wearer gets the effect.
- It starts at **level I**. Add a **Nether Star** at a Smithing Table to upgrade it to **level II**. Level II amulets shimmer.
- You can change the effect at any time at a Smithing Table by using a different gem or metal. Changing the effect keeps the amulet's level.

You can wear both amulets at the same time, and both show on your character.

## Crafting

**Empty Amulet** (crafting table)
```
Chain  .     Chain
Chain  .     Chain
.     Gold   .
```
Any chain works, iron or copper.

**Everything else is made at a Smithing Table** (left slot / middle slot / right slot):

| Make | Left slot | Middle slot | Right slot |
|---|---|---|---|
| Totem Amulet | *(empty)* | Empty Amulet | Totem of Undying |
| Beacon Amulet | Beacon | Empty Amulet | Material (see below) |
| Change effect | *(empty)* | Beacon Amulet | Material (see below) |
| Upgrade to level II | *(empty)* | Beacon Amulet (level I) | Nether Star |

| Material | Effect |
|---|---|
| Emerald | Speed |
| Gold Ingot | Haste |
| Iron Ingot | Resistance |
| Amethyst Shard | Jump Boost |
| Netherite Ingot | Strength |
| Diamond | Regeneration |

## Requirements
- Minecraft **26.3**, Fabric Loader
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Trinkets Updated](https://modrinth.com/mod/trinkets-updated) (adds the Necklace and Pendant slots)

Install on **both the client and the server**.

## For pack makers
All recipes are normal data-pack JSON in `data/wearablerelics/recipe/`, so you can change which material gives which effect. The effect is stored as an item component (`wearablerelics:beacon_effect`, any effect ID), so a data pack can even add effects beyond the six beacon ones.
