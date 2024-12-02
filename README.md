# **EpicQuest RPG Plugin**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)  
**EpicQuest RPG** is a feature-packed Minecraft plugin designed to deliver an engaging RPG experience to players. With customizable classes, quests, custom items, economy integration, and boss battles, this plugin is perfect for any server looking to offer a unique adventure.

---

## **Features**

### 🎭 **Custom Classes and Skills**
- Choose from multiple classes like **Warrior**, **Mage**, or **Archer**.
- Each class has unique stats (health, mana, damage) and abilities.
- Abilities are triggered via custom events and cooldown systems.

### 📜 **Quest System**
- Add depth to gameplay with a **fully customizable quest system**.
- Support for objectives like killing mobs, gathering resources, or exploring locations.
- Admins can easily define new quests in a configuration file.
- Track active and completed quests via a user-friendly GUI.

### ⚔️ **Custom Items and Recipes**
- Create unique items with custom lore, effects, and abilities.
- Add custom crafting recipes to expand the in-game economy.
- Items can trigger special effects like lightning strikes or area-of-effect spells.

### 💰 **Economy System**
- Integrated with **Vault API** for server-wide economy compatibility.
- Option to use a custom economy with your own currency (e.g., "Gold").
- Rewards for quests, boss battles, and player trading.

### 👹 **Boss Battles**
- Design challenging bosses with special abilities and unique loot tables.
- Custom mechanics like area-wide explosions, minion summoning, and timed phases.
- Reward players with rare items or currency.

### 🗄️ **Persistent Data**
- Player progress, quest states, and balances are stored reliably using SQL or MySQL or Mongo.
- Admins can manage and back up player data with ease.

---

## **Installation**

### Prerequisites
- A **Minecraft server** running **Spigot** or a compatible fork (e.g., Paper).
- Java 17+ (required for modern Minecraft versions).
- **[VaultUnlocked](https://modrinth.com/plugin/vaultunlocked) or [Vault](https://www.spigotmc.org/resources/vault.34315/) Plugin** (VaultUnlocked is recommended it's a updated version of normal vault).

### Steps
1. Download the latest release of EpicQuest RPG from the [Releases](https://github.com/5RoD/EpicQuestsRPG/releases) page.
2. Place the `.jar` file in your server’s `/plugins` directory.
3. Start your server to generate the default configuration files.
4. Configure the plugin by editing the files in the `/plugins/EpicQuestRPG/` directory.
5. Reload or restart the server to apply your changes.

---

## **Configuration**

### Core Configuration (`config.yml`)
The `config.yml` file contains general plugin settings, such as:
```yaml
general:
  language: "en_US"
  enable-economy: true
  max-quests-per-player: 5

economy:
  currency-name: "Gold"
  starting-balance: 100
```

### Quest Configuration (`quests.yml`)
Define quests and their rewards in `quests.yml`:
```yaml
quests:
  - id: "zombie-slayer"
    name: "Zombie Slayer"
    objectives:
      - "kill 10 zombies"
    rewards:
      experience: 50
      items:
        - "minecraft:iron_sword"
      currency: 20
```


---

## **Commands: Subject to change none are implemented**

| Command            | Description                          | Permission             |
|--------------------|--------------------------------------|-----------------------|
| `/class`           | Opens the class selection menu.      | `epicquest.class`     |
| `/quests`          | Displays the player’s active quests. | `epicquest.quests`    |
| `/adminquest`      | Manage quests and objectives.        | `epicquest.admin`     |


---

## **Contributing**

### Ways to Contribute
- **Report Bugs:** Open an issue in the [Issues](https://github.com/5RoD/EpicQuestsRPG/issues) section.
- **Submit Pull Requests:** Fork the repo, make your changes, and submit a PR.
- **Suggest Features:** Share your ideas in the Discussions tab.

### Development Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/5RoD/EpicQuestsRPG.git
   ```
2. Open the project in IntelliJ IDEA.
3. Build the project using **Maven**:
   ```bash
   mvn clean install
   ```
4. Test your changes on a local Spigot server.

---

## **License**
This project is licensed under the **MIT License**.  
See the [LICENSE](https://github.com/5RoD/EpicQuestsRPG/blob/main/LICENSE) file for details.

---

## **Support**
If you encounter issues or have questions, feel free to:
- Open a GitHub issue.
- Join our community on Discord (link coming soon!).
- Reach out via email at `5RoD@gravemc.net`.

---

## **Roadmap**
### Current Features
- [x] Mysql System


### Planned Features
- [ ] Additional boss mechanics.
- [ ] In-game quest creation GUI.
- [ ] Discord bot integration for quest progress.
- [ ] Mongo and SQL System
- [ ] Fully working Config System
- [ ] Economy Integration
- [ ] Custom Items and Recipes
- [ ] etc, etc.

---

## **Screenshots**
*i'll add them later.*  
(Example: a class selection GUI, a quest progress GUI, or a boss fight in action.)

---

