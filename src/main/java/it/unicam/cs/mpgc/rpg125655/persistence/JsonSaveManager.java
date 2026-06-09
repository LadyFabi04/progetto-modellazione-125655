package it.unicam.cs.mpgc.rpg125655.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import it.unicam.cs.mpgc.rpg125655.model.GameState;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Implementazione di SaveManager che salva e carica il GameState in formato JSON.
public class JsonSaveManager implements SaveManager {

    private final Gson gson;

    // Cartella in cui vengono salvati i file di salvataggio.
    private final Path saveDirectory;

    public JsonSaveManager() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                                LocalDateTime.parse(json.getAsString()))
                .create();
        this.saveDirectory = Paths.get("saves");
    }

    // Salva il GameState su file JSON.
    @Override
    public void save(GameState state) throws IOException {
        Files.createDirectories(saveDirectory);
        Path filePath = saveDirectory.resolve(state.getSaveId() + ".json");
        // Salva solo i dati essenziali senza la storia completa
        SaveData data = new SaveData(
                state.getSaveId(),
                state.getPlayer().getName(),
                state.getPlayer().getCharacterClass().name(),
                state.getPlayer().getStats().getHealth(),
                state.getPlayer().getStats().getMaxHealth(),
                state.getPlayer().getStats().getAgility(),
                state.getPlayer().getStats().getMagic(),
                state.getPlayer().getStats().getLevel(),
                state.getPlayer().getStats().getExperience(),
                state.getPlayer().getCurrentNodeId()
        );
        Files.writeString(filePath, gson.toJson(data));
    }

    // Carica un GameState dal file JSON con l'id indicato.
    @Override
    public GameState load(String saveId) throws IOException {
        Path filePath = saveDirectory.resolve(saveId + ".json");
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File di salvataggio non trovato: " + saveId);
        }
        SaveData data = gson.fromJson(Files.readString(filePath), SaveData.class);
        return data.toGameState();
    }

    // Restituisce la lista degli id dei salvataggi disponibili.
    @Override
    public List<String> listSaves() throws IOException {
        if (!Files.exists(saveDirectory)) return List.of();
        return Files.list(saveDirectory)
                .filter(p -> p.toString().endsWith(".json"))
                .map(p -> p.getFileName().toString().replace(".json", ""))
                .collect(Collectors.toList());
    }

    // Elimina il file di salvataggio con l'id indicato.
    @Override
    public void delete(String saveId) throws IOException {
        Files.deleteIfExists(saveDirectory.resolve(saveId + ".json"));
    }

    private static class SaveData {
        String saveId;
        String playerName;
        String playerClass;
        int health;
        int maxHealth;
        int agility;
        int magic;
        int level;
        int experience;
        String currentNodeId;

        SaveData(String saveId, String playerName, String playerClass,
                 int health, int maxHealth, int agility, int magic,
                 int level, int experience, String currentNodeId) {
            this.saveId = saveId;
            this.playerName = playerName;
            this.playerClass = playerClass;
            this.health = health;
            this.maxHealth = maxHealth;
            this.agility = agility;
            this.magic = magic;
            this.level = level;
            this.experience = experience;
            this.currentNodeId = currentNodeId;
        }

        // Converte i dati salvati in un GameState utilizzabile.
        GameState toGameState() {
            it.unicam.cs.mpgc.rpg125655.model.character.CharacterClass classe =
                    it.unicam.cs.mpgc.rpg125655.model.character.CharacterClass.valueOf(playerClass);
            it.unicam.cs.mpgc.rpg125655.model.character.Character player =
                    new it.unicam.cs.mpgc.rpg125655.model.character.Character(playerName, classe);
            player.getStats().setHealth(health);
            player.getStats().setMaxHealth(maxHealth);
            player.getStats().setAgility(agility);
            player.getStats().setMagic(magic);
            player.getStats().setLevel(level);
            player.getStats().setExperience(experience);
            player.setCurrentNodeId(currentNodeId);
            return new it.unicam.cs.mpgc.rpg125655.model.GameState(saveId, player, null,
                    java.time.LocalDateTime.now());
        }
    }
}