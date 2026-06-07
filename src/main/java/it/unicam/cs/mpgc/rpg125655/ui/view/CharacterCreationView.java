package it.unicam.cs.mpgc.rpg125655.ui.view;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.character.CharacterClass;
import it.unicam.cs.mpgc.rpg125655.model.story.Story;
import it.unicam.cs.mpgc.rpg125655.persistence.JsonStoryLoader;
import it.unicam.cs.mpgc.rpg125655.ui.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// Schermata di creazione del personaggio: nome e classe.
public class CharacterCreationView {

    private final VBox root;

    // Costruisce la schermata di creazione del personaggio.
    public CharacterCreationView() {
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Text titolo = new Text("Crea il tuo Personaggio");
        titolo.getStyleClass().add("title");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Inserisci il tuo nome...");
        campoNome.getStyleClass().add("text-field");
        campoNome.setMaxWidth(300);

        Text labelClasse = new Text("Scegli la tua classe:");
        labelClasse.getStyleClass().add("subtitle");

        ToggleGroup gruppo = new ToggleGroup();

        RadioButton warrior = new RadioButton("Guerriero — Forza e resistenza");
        RadioButton mage = new RadioButton("Mago — Intelligenza e magia");
        RadioButton rogue = new RadioButton("Ladro — Agilità e furtività");

        warrior.setToggleGroup(gruppo);
        mage.setToggleGroup(gruppo);
        rogue.setToggleGroup(gruppo);
        warrior.setSelected(true);

        warrior.getStyleClass().add("radio-btn");
        mage.getStyleClass().add("radio-btn");
        rogue.getStyleClass().add("radio-btn");

        Button btnAvvia = new Button("Inizia l'Avventura");
        btnAvvia.getStyleClass().add("btn-primary");
        btnAvvia.setOnAction(e -> {
            String nome = campoNome.getText().trim();
            if (nome.isEmpty()) nome = "Eroe";

            CharacterClass classe;
            RadioButton selezionato = (RadioButton) gruppo.getSelectedToggle();
            String testo = selezionato.getText();
            if (testo.startsWith("Mago")) classe = CharacterClass.MAGE;
            else if (testo.startsWith("Ladro")) classe = CharacterClass.ROGUE;
            else classe = CharacterClass.WARRIOR;

            Character personaggio = new Character(nome, classe);

            try {
                JsonStoryLoader loader = new JsonStoryLoader("/data/chronicles_of_fate.json");
                Story storia = loader.load();
                SceneManager.showGame(personaggio, storia);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnIndietro = new Button("Indietro");
        btnIndietro.getStyleClass().add("btn-secondary");
        btnIndietro.setOnAction(e -> SceneManager.showMainMenu());

        root.getChildren().addAll(titolo, campoNome, labelClasse,
                warrior, mage, rogue, btnAvvia, btnIndietro);
    }

    // Restituisce il nodo radice della schermata.
    public VBox getRoot() {
        return root;
    }
}