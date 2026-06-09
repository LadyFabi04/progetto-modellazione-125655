package it.unicam.cs.mpgc.rpg125655.ui.view;

import it.unicam.cs.mpgc.rpg125655.model.GameState;
import it.unicam.cs.mpgc.rpg125655.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg125655.ui.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

// Schermata del menu principale con le opzioni di avvio del gioco.
public class MainMenuView {

    private final VBox root;

    // Costruisce la schermata del menu principale.
    public MainMenuView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Text titolo = new Text("Chronicles of Fate");
        titolo.getStyleClass().add("title");

        Text sottotitolo = new Text("Un'avventura di scelte e destino");
        sottotitolo.getStyleClass().add("subtitle");

        Button btnNuovaPartita = new Button("Nuova Partita");
        btnNuovaPartita.getStyleClass().add("btn-primary");
        btnNuovaPartita.setOnAction(e -> SceneManager.showCharacterCreation());

        Button btnCarica = new Button("Carica Partita");
        btnCarica.getStyleClass().add("btn-primary");
        btnCarica.setOnAction(e -> caricaPartita());

        Button btnEsci = new Button("Esci");
        btnEsci.getStyleClass().add("btn-secondary");
        btnEsci.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(titolo, sottotitolo, btnNuovaPartita, btnCarica, btnEsci);
    }

    // Tenta di caricare l'ultimo salvataggio disponibile.
    private void caricaPartita() {
        try {
            JsonSaveManager saveManager = new JsonSaveManager();
            List<String> salvataggi = saveManager.listSaves();

            if (salvataggi.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nessun Salvataggio");
                alert.setHeaderText(null);
                alert.setContentText("Non ci sono partite salvate.");
                alert.showAndWait();
                return;
            }

            // Carica l'ultimo salvataggio
            String ultimoSalvataggio = salvataggi.get(salvataggi.size() - 1);
            GameState stato = saveManager.load(ultimoSalvataggio);
            SceneManager.showGameFromSave(stato);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Restituisce il nodo radice della schermata.
    public VBox getRoot() {
        return root;
    }
}