package it.unicam.cs.mpgc.rpg125655.ui.view;

import it.unicam.cs.mpgc.rpg125655.ui.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

        Button btnEsci = new Button("Esci");
        btnEsci.getStyleClass().add("btn-secondary");
        btnEsci.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(titolo, sottotitolo, btnNuovaPartita, btnEsci);
    }

    // Restituisce il nodo radice della schermata.
    public VBox getRoot() {
        return root;
    }
}