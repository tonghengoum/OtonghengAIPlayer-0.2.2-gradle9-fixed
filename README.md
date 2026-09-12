# O.Tongheng AI Player — v0.2.0

A client-side autonomous Minecraft player agent for **Minecraft 1.21.11 + Fabric**. Designed for single-player, private servers, and authorized test environments.

## Controls
- **Right Shift** — AI Control Center
- **F6** — Start / Stop
- **F7** — Pause / Resume
- **F8** — Emergency Stop

## Implemented core
- Perception and hostile-target selection
- Continuous decision loop: observe → evaluate → act → verify
- Camera steering and approach movement
- Sprinting and emergency retreat behavior
- Attack cooldown-aware PvE combat
- Combat hotbar selection (mace/sword/axe preference)
- Totem detection
- Health/danger monitoring
- Goal manager and progress state
- Short-term AI event memory
- Runtime state machine
- HUD telemetry
- Control-center GUI
- Configurable architecture ready for expansion
- Java 21 / Gradle / Fabric Loom

## Safety boundary
This build does not include anti-cheat bypasses, stealth botting, packet manipulation for cheating, or autonomous attacks against players on public competitive servers. Player-vs-player behavior can be developed as a training/simulation system or for environments where automation is explicitly authorized.

## Build
Use a machine with Java 21 and network access to the Fabric/Minecraft Maven repositories, then run:

```text
./gradlew build
```

The remapped JAR is produced under `build/libs/`.
