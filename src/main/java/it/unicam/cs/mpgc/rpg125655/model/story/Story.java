package it.unicam.cs.mpgc.rpg125655.model.story;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Story {

    private final String id;
    private final String title;
    private final String startNodeId;
    private final Map<String, StoryNode> nodes;

    public Story(String id, String title, String startNodeId,
                 Map<String, StoryNode> nodes) {
        this.id          = id;
        this.title       = title;
        this.startNodeId = startNodeId;
        this.nodes       = Collections.unmodifiableMap(new HashMap<>(nodes));
    }

    public String getId()           { return id; }
    public String getTitle()        { return title; }
    public String getStartNodeId()  { return startNodeId; }

    public StoryNode getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public StoryNode getStartNode() {
        return nodes.get(startNodeId);
    }

    public Map<String, StoryNode> getAllNodes() {
        return nodes;
    }
}