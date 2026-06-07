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
        Files.writeString(filePath, gson.toJson(state));
    }

    // Carica un GameState dal file JSON con l'id indicato.
    @Override
    public GameState load(String saveId) throws IOException {
        Path filePath = saveDirectory.resolve(saveId + ".json");
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File di salvataggio non trovato: " + saveId);
        }
        return gson.fromJson(Files.readString(filePath), GameState.class);
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
}