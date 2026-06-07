package it.unicam.cs.mpgc.rpg125655.model.story;

import java.util.List;

public class StoryNode {

    private final String       id;
    private final String       title;
    private final String       narrativeText;
    private final List<Choice> choices;
    private final boolean      isEnding;
    private final String       endingType;

    public StoryNode(String id, String title, String narrativeText,
                     List<Choice> choices, boolean isEnding, String endingType) {
        this.id            = id;
        this.title         = title;
        this.narrativeText = narrativeText;
        this.choices       = choices != null ? choices : List.of();
        this.isEnding      = isEnding;
        this.endingType    = endingType;
    }

    public String       getId()            { return id; }
    public String       getTitle()         { return title; }
    public String       getNarrativeText() { return narrativeText; }
    public List<Choice> getChoices()       { return choices; }
    public boolean      isEnding()         { return isEnding; }
    public String       getEndingType()    { return endingType; }
}