# Backpacks
Spigot plugin that adds backpacks accessible with commands
## Badges:
[![Latest Version](https://img.shields.io/badge/Latest%20Version-2.2.0-brightgreen)](https://github.com/IBMESP/Backpacks/releases/latest)
![Spigot Downloads](https://img.shields.io/spiget/downloads/99840?label=Spigot%20Downloads)
![Spigot Rating](https://img.shields.io/spiget/rating/99840?label=Spigot%20Rating)


[![](https://i.imgur.com/6JVFkxy.png)](https://www.spigotmc.org/resources/backpacks.99840/)
![](https://i.imgur.com/Edv3vWf.png)
- **Three sizes** of backpacks: small, medium and large
- **Create**, **delete** and **see** backpacks of online or offline players
- A **GUI** to create, configure and delete backpacks
- **[Multiple languages]()**

![](https://i.imgur.com/M21j7Lc.png)

![](https://media2.giphy.com/media/3zRrIdt0yYDYmfNJAs/giphy.gif)

![](https://i.imgur.com/XEfeWN8.png)
- **Permission:** bp.admin →
  - **Commands:**
    - /bp delete <player> : Deletes a player backpack
    - /bgr keepbackpack (true/false) : To keep your backpack after dead
- **Permission:** bp.small →
  - **Commands:**
    - /bp create s : Creates a small backpack
- **Permission:** bp.medium →
  - **Commands:**
    - /bp create m : Creates a medium backpack
- **Permission:** bp.large →
  - **Commands:**
    - /bp create l : Creates a large backpack
- **Permission:** bp.see →
  - **Commands:**
    - /bpsee <player> : To see other player backpack
- **Other Commands**
  - /bpmenu : Plugin GUI
  - /bp open : To open your backpack
  - /bp relaod : Reloads bpconfig.yml

![](https://i.imgur.com/oH7B1CQ.png)
<details>
  <summary>Default config.yml</summary>
  
```YAML
name: "&6[Backpacks] "

# This is the config version for reference.
# DO NOT EDIT VALUE.
configVersion: 2

#Available languages
#en_US
#es_ES
#zh_CN
locale: en_US

# This is the en_US.yml version for reference.
# ONLY EDIT ONCE ALL LANGUAGE FILES HAVE BEEN UPDATED.
languageFile: 4

#Paginated create and delete GUIs, adds pages of players to create and delete GUIs
#If using this gives lag just change to false
paginatedGUI: true

#Maximun number of backpacks per player (1-36)
maxBP: 9
  ```
</details>
<details>
<summary>Default en_US.yml</summary>

```YAML
create:
  already: "You already have a backpack"
  perm: "You do not have permission to create a %size% backpack"
  maxbp: "You can not create more backpacks"
  target:
    already: "%player% already has a backpack"
    created: "You created a backpack to "
    create: "%player% created you a %size% backpack"
    perm: "You do not have permission to create other backpacks"
delete:
  confirm: "Write \u0022confirm\u0022"
  notBackpack: "You do not have a backpack"
  deleted: "Your backpack has been deleted"
  target:
    notBackpack: "%player% does not have a backpack"
    deleted: "%player%'s backpack has been deleted"
    perm: "You do not have permission to delete other backpacks"
    deletedBy: "Your backpack has been deleted by %player%"
gui:
  small: "small"
  medium: "medium"
  large: "large"
  title: "&rBackpack Menu"
  browser: "Write the name"
  nextPage: "Next page"
  previousPage: "Previous page"
  size:
    small: "&a&lSmall size"
    medium: "&e&lMedium size"
    large: "&4&lLarge size"
  items:
    create: "Create a backpack"
    delete: "Delete a backpack"
    config: "Config"
    configuration: "&rConfiguration"
    keepBackpack: "Keep Backpack"
    current: "Current: "
    back: "Back"
    search: "Search a player"
    has: "%player% has a backpack"
    hasNot: "%player% does not have a backpack"
    size: "&rSize"
  config:
    gamerule: "Gamerule keepBackpack is now set: %bool%"
    changeSize: "%size% size set to %num% row"
    small: "Small backpack can't be bigger than medium backpack"
    large: "Large backpack can't be smaller than medium backpack"
  create:
    title: "&rPlayers Online (%size%)"
  delete:
    title: "&rPlayers Online (Delete)"
  open:
    small: "Small Backpack"
    medium: "Medium Backpack"
    large: "Large Backpack"
config:
  reloaded: "[Backpacks] Config reloaded!"
  perms: "You do not have permission to use this command"
  exist: "This command doesn't exists"
  open: "Use /bp open to open the backpack"
  help: "Use /bp help to see the commands"
  update: "Backpacks has a new update"
  notUpdate: "Backpacks is up to date"
  title: "&r%player%'s Backpack"
  backpacks: "&rBackpacks"
  ```
</details>

![](https://bstats.org/signatures/bukkit/Backpacks%20-%20by%20Ib.svg)

  
