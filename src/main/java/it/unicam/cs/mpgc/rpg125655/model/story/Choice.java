package it.unicam.cs.mpgc.rpg125655.model.story;

import java.util.List;

public class Choice {

    private final String id;
    private final String text;
    private final String targetNodeId;
    private final Requirement requirement;
    private final Consequence consequence;

    public Choice(String id, String text, String targetNodeId,
                  Requirement requirement, Consequence consequence) {
        this.id = id;
        this.text = text;
        this.targetNodeId = targetNodeId;
        this.requirement = requirement;
        this.consequence = consequence;
    }

    public String getId()           { return id; }
    public String getText()         { return text; }
    public String getTargetNodeId() { return targetNodeId; }
    public Requirement getRequirement()   { return requirement; }
    public Consequence getConsequence()   { return consequence; }

    public static class Requirement {
        private final Integer minStrength;
        private final Integer minIntelligence;
        private final Integer minAgility;
        private final Integer minCharisma;
        private final String  requiredItemId;

        public Requirement(Integer minStrength, Integer minIntelligence,
                           Integer minAgility, Integer minCharisma,
                           String requiredItemId) {
            this.minStrength     = minStrength;
            this.minIntelligence = minIntelligence;
            this.minAgility      = minAgility;
            this.minCharisma     = minCharisma;
            this.requiredItemId  = requiredItemId;
        }

        public Integer getMinStrength()     { return minStrength; }
        public Integer getMinIntelligence() { return minIntelligence; }
        public Integer getMinAgility()      { return minAgility; }
        public Integer getMinCharisma()     { return minCharisma; }
        public String  getRequiredItemId()  { return requiredItemId; }
    }

    public static class Consequence {
        private final int          healthDelta;
        private final int          xpGain;
        private final List<String> itemsGiven;
        private final List<String> itemsRemoved;
        private final String       narrativeOutcome;

        public Consequence(int healthDelta, int xpGain,
                           List<String> itemsGiven, List<String> itemsRemoved,
                           String narrativeOutcome) {
            this.healthDelta      = healthDelta;
            this.xpGain           = xpGain;
            this.itemsGiven       = itemsGiven != null ? itemsGiven : List.of();
            this.itemsRemoved     = itemsRemoved != null ? itemsRemoved : List.of();
            this.narrativeOutcome = narrativeOutcome;
        }

        public int          getHealthDelta()      { return healthDelta; }
        public int          getXpGain()           { return xpGain; }
        public List<String> getItemsGiven()       { return itemsGiven; }
        public List<String> getItemsRemoved()     { return itemsRemoved; }
        public String       getNarrativeOutcome() { return narrativeOutcome; }
    }
}