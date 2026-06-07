package it.unicam.cs.mpgc.rpg125655.persistence;

import it.unicam.cs.mpgc.rpg125655.model.GameState;

import java.io.IOException;
import java.util.List;

public interface SaveManager {

    void save(GameState state) throws IOException;

    GameState load(String saveId) throws IOException;

    List<String> listSaves() throws IOException;

    void delete(String saveId) throws IOException;
}