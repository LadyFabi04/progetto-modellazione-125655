package it.unicam.cs.mpgc.rpg125655.ui.view;

import it.unicam.cs.mpgc.rpg125655.model.character.Character;
import it.unicam.cs.mpgc.rpg125655.model.item.Item;
import it.unicam.cs.mpgc.rpg125655.model.story.Choice;
import it.unicam.cs.mpgc.rpg125655.model.story.Story;
import it.unicam.cs.mpgc.rpg125655.model.story.StoryNode;
import it.unicam.cs.mpgc.rpg125655.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg125655.service.ConsequenceApplier;
import it.unicam.cs.mpgc.rpg125655.service.GameEngine;
import it.unicam.cs.mpgc.rpg125655.service.RequirementChecker;
import it.unicam.cs.mpgc.rpg125655.ui.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.util.stream.Collectors;

// Schermata principale di gioco: mostra il testo narrativo e le scelte disponibili.
public class GameView {

    private final VBox root;

    private final GameEngine engine;

    // Area dove vengono mostrate le scelte.
    private final VBox areaScelte;

    // Testo narrativo del nodo corrente.
    private final Text testoNarrativo;

    // Barra della salute.
    private final ProgressBar barraSalute;

    // Etichetta statistiche.
    private final Text labelStats;

    // Etichetta inventario.
    private final Text labelInventario;

    // Immagine contestuale della scena.
    private final ImageView immagineScena;

    // Riferimento al personaggio.
    private final Character personaggio;

    // Costruisce la schermata di gioco con il personaggio e la storia forniti.
    public GameView(Character personaggio, Story storia) {
        this.personaggio = personaggio;

        RequirementChecker checker = new RequirementChecker();
        ConsequenceApplier applier = new ConsequenceApplier();
        JsonSaveManager saveManager = new JsonSaveManager();
        engine = new GameEngine(checker, applier, saveManager);
        engine.startNewGame(personaggio, storia);

        root = new VBox(15);
        root.getStyleClass().add("root");
        root.setPadding(new Insets(30));

        // Barra statistiche in cima
        barraSalute = new ProgressBar(1.0);
        barraSalute.getStyleClass().add("health-bar");
        barraSalute.setPrefWidth(150);

        labelStats = new Text();
        labelStats.getStyleClass().add("stats-label");

        labelInventario = new Text();
        labelInventario.getStyleClass().add("stats-label");

        HBox statsBox = new HBox(20);
        statsBox.getStyleClass().add("stats-bar");
        statsBox.setAlignment(Pos.CENTER_LEFT);

        Text labelSalute = new Text("❤ ");
        labelSalute.getStyleClass().add("stats-label");

        statsBox.getChildren().addAll(labelSalute, barraSalute, labelStats, labelInventario);

        // Contenuto principale: immagine + testo
        immagineScena = new ImageView();
        immagineScena.setFitWidth(280);
        immagineScena.setFitHeight(200);
        immagineScena.setPreserveRatio(true);
        immagineScena.getStyleClass().add("scene-image");

        testoNarrativo = new Text();
        testoNarrativo.getStyleClass().add("narrative");
        testoNarrativo.setWrappingWidth(520);

        TextFlow flussoTesto = new TextFlow(testoNarrativo);
        flussoTesto.getStyleClass().add("narrative-box");
        flussoTesto.setPrefWidth(540);

        ScrollPane scroll = new ScrollPane(flussoTesto);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(220);
        scroll.getStyleClass().add("scroll-pane");

        HBox contenuto = new HBox(20);
        contenuto.setAlignment(Pos.TOP_LEFT);
        contenuto.getChildren().addAll(immagineScena, scroll);

        // Area scelte
        areaScelte = new VBox(8);
        areaScelte.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(statsBox, contenuto, areaScelte);

        aggiornaPagina();
    }

    // Restituisce l'immagine corrispondente al nodo corrente.
    private Image getImageForNode(String nodeId) {
        String imageName;
        if (nodeId.equals("start") || nodeId.startsWith("village") || nodeId.equals("take_medallion") || nodeId.equals("villagers_talk") || nodeId.equals("fountain_secret") || nodeId.equals("old_woman") || nodeId.equals("refuse_prophecy")) {
            imageName = "village.jpg";
        } else if (nodeId.equals("dark_tower_approach") || nodeId.equals("main_gate_fight") || nodeId.equals("disguise_merchant") || nodeId.equals("bribe_guard") || nodeId.equals("request_audience") || nodeId.equals("secondary_door") || nodeId.equals("window_climb")) {
            imageName = "tower.jpg";
        } else if (nodeId.equals("tower_stairs") || nodeId.equals("prison_cell") || nodeId.equals("secret_passage") || nodeId.equals("guard_ambush") || nodeId.equals("crypt_frescoes") || nodeId.equals("open_sarcophagus") || nodeId.equals("read_diary")) {
            imageName = "stairs.jpg";
        } else if (nodeId.equals("red_room") || nodeId.equals("free_fairy")) {
            imageName = "fairy.jpg";
        } else {
            imageName = "summit.jpg";
        }

        try {
            return new Image(getClass().getResourceAsStream("/images/" + imageName));
        } catch (Exception e) {
            return null;
        }
    }

    // Aggiorna il testo, le statistiche e le scelte in base al nodo corrente.
    private void aggiornaPagina() {
        StoryNode nodo = engine.getCurrentNode();
        testoNarrativo.setText(nodo.getNarrativeText());

        // Aggiorna immagine
        Image img = getImageForNode(nodo.getId());
        if (img != null) immagineScena.setImage(img);

        // Aggiorna statistiche
        int salute = personaggio.getStats().getHealth();
        int maxSalute = personaggio.getStats().getMaxHealth();
        int agilita = personaggio.getStats().getAgility();
        int livello = personaggio.getStats().getLevel();
        barraSalute.setProgress((double) salute / maxSalute);
        labelStats.setText(salute + "/" + maxSalute + " HP  ⚡ " + agilita + " AGI  ⚔ Lv." + livello);

        // Aggiorna inventario
        List<Item> inventario = personaggio.getInventory();
        if (inventario.isEmpty()) {
            labelInventario.setText("  🎒 Inventario vuoto");
        } else {
            String oggetti = inventario.stream()
                    .map(Item::getId)
                    .collect(Collectors.joining(", "));
            labelInventario.setText("  🎒 " + oggetti);
        }

        areaScelte.getChildren().clear();

        if (engine.isGameOver()) {
            mostraFine(nodo);
            return;
        }

        List<Choice> scelte = engine.getAvailableChoices();
        for (Choice scelta : scelte) {
            Button btnScelta = new Button(scelta.getText());
            btnScelta.getStyleClass().add("btn-choice");
            btnScelta.setMaxWidth(Double.MAX_VALUE);
            btnScelta.setOnAction(e -> {
                engine.makeChoice(scelta);
                aggiornaPagina();
            });
            areaScelte.getChildren().add(btnScelta);
        }
    }

    // Mostra la schermata di fine con stile diverso in base al tipo di finale.
    private void mostraFine(StoryNode nodo) {
        if (engine.isDead()) {
            Text labelFinale = new Text("✦ Sei Caduto ✦");
            labelFinale.getStyleClass().add("ending-evil");
            Text testoFinale = new Text("Le tue ferite erano troppo gravi. La tua avventura finisce qui, nel buio.");
            testoFinale.getStyleClass().add("subtitle");
            Button btnMenu = new Button("Torna al Menu");
            btnMenu.getStyleClass().add("btn-primary");
            btnMenu.setOnAction(e -> SceneManager.showMainMenu());
            areaScelte.getChildren().addAll(labelFinale, testoFinale, btnMenu);
            return;
        }

        String tipoFinale = nodo.getEndingType();
        Text labelFinale = new Text();
        String styleClass;
        String messaggio;

        switch (tipoFinale != null ? tipoFinale : "") {
            case "good" -> {
                labelFinale.setText("✦ Fine Gloriosa ✦");
                styleClass = "ending-good";
                messaggio = "Hai salvato il mondo. La tua leggenda vivrà per sempre.";
            }
            case "evil" -> {
                labelFinale.setText("✦ Fine Oscura ✦");
                styleClass = "ending-evil";
                messaggio = "Il Vuoto ha trovato il suo campione. Il mondo tremerà.";
            }
            case "sacrifice" -> {
                labelFinale.setText("✦ Il Sacrificio Supremo ✦");
                styleClass = "ending-sacrifice";
                messaggio = "Non tutti gli eroi tornano a casa. Il tuo nome sarà ricordato.";
            }
            case "bittersweet" -> {
                labelFinale.setText("✦ Una Vittoria Amara ✦");
                styleClass = "ending-bittersweet";
                messaggio = "Hai vinto, ma a un prezzo che non dimenticherai mai.";
            }
            default -> {
                labelFinale.setText("✦ Fine ✦");
                styleClass = "ending-good";
                messaggio = "La tua avventura è giunta al termine.";
            }
        }

        labelFinale.getStyleClass().add(styleClass);
        Text testoFinale = new Text(messaggio);
        testoFinale.getStyleClass().add("subtitle");
        Button btnMenu = new Button("Torna al Menu");
        btnMenu.getStyleClass().add("btn-primary");
        btnMenu.setOnAction(e -> SceneManager.showMainMenu());
        areaScelte.getChildren().addAll(labelFinale, testoFinale, btnMenu);
    }

    // Restituisce il nodo radice della schermata.
    public VBox getRoot() {
        return root;
    }
}