# DamageSet
Spigot plugin for creating custom armor sets that buff damage.

# Command
**aliases:** 'ds', 'dmgset', 'damageset'

**description:** Used to give a damage set to a player.

**usage:** ds give {player} [helmet/chestplate/leggings/boots]

The player field is mandatory. You can optionally specify a singular armor piece, and if you do not then you will give the player the full set.

**permission:** damageset.give

# Config
**item-display-names:** There should be a string for helmet, chestplate, leggings, and boots.
This will be the display name of the pieces of the damage set armor.
```
item-display-names:
  helmet: '&8[&4Damage&8] &cHelmet'
  chestplate: '&8[&4Damage&8] &cChestplate'
  leggings: '&8[&4Damage&8] &cLeggings'
  boots: '&8[&4Damage&8] &cBoots'
```
**item-lore:** This is the lore section of the damage set armor. You must specify the lore
as a list of strings, each representing a line of a multiline lore/description.
```
item-lore:
  - '&7Deal &c$damage-buff-percent$% &7more damage when'
  - '&7this set is fully equipped.'
```
**damage-buff-percent:** This is the percent which the damage set will boost your damage by
when you have the full armor set equipped. The example below would be a 5% increase in damage
output.
```
damage-buff-percent: 5
```
**default-item-enchantments:** When you give a player a damage set item, these are the
enchantments each piece will have when they are received. You must specify a list of
enchantments for helmet, chestplate, leggings, and boots. The enchantment should follow
the following notation:
```
default-item-enchantments:
  [helmet/chestplate/leggings/boots]:
    - [ENCHANTMENT NAME IN ALL CAPS] [ENCHANTMENT LEVEL]
```
Here is an example:
```
default-item-enchantments:
  helmet:
    - PROTECTION_ENVIRONMENTAL 4
    - DURABILITY 3
  chestplate:
    - PROTECTION_ENVIRONMENTAL 4
    - DURABILITY 3
  leggings:
    - PROTECTION_ENVIRONMENTAL 4
    - DURABILITY 3
  boots:
    - PROTECTION_ENVIRONMENTAL 4
    - DURABILITY 3
    - DEPTH_STRIDER 3
```
**Important:** The enchantment section should not be deleted from the config.
If you wish for items to have no enchantments, copy the example below.
```
default-item-enchantments:
  helmet: []
  chestplate: []
  leggings: []
  boots: []
```
**equip-message:** This is the message a player will receive upon equipping
a full damage set.

**unequip-message:** This is the message a player will receive upon losing
their damage set buff.
```
equip-message: '&7You have equipped the &cDamage Set&7.'
unequip-message: '&7You have unequipped the &cDamage Set&7.'
```
