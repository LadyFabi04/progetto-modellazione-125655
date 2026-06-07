package it.unicam.cs.mpgc.rpg125655.persistence;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg125655.model.story.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonStoryLoader implements StoryLoader {

    private final String resourcePath;
    private final Gson gson = new Gson();

    public JsonStoryLoader(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Story load() throws IOException {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IOException("Story file not found: " + resourcePath);
        }

        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            JsonObject root = gson.fromJson(reader, JsonObject.class);

            String id          = root.get("id").getAsString();
            String title       = root.get("title").getAsString();
            String startNodeId = root.get("startNodeId").getAsString();

            Map<String, StoryNode> nodes = new HashMap<>();
            for (JsonElement el : root.getAsJsonArray("nodes")) {
                StoryNode node = parseNode(el.getAsJsonObject());
                nodes.put(node.getId(), node);
            }

            return new Story(id, title, startNodeId, nodes);
        }
    }

    private StoryNode parseNode(JsonObject o) {
        String  id            = o.get("id").getAsString();
        String  title         = o.get("title").getAsString();
        String  narrativeText = o.get("narrativeText").getAsString();
        boolean isEnding      = o.get("isEnding").getAsBoolean();
        String  endingType    = o.has("endingType") && !o.get("endingType").isJsonNull()
                ? o.get("endingType").getAsString() : null;

        List<Choice> choices = new ArrayList<>();
        for (JsonElement el : o.getAsJsonArray("choices")) {
            choices.add(parseChoice(el.getAsJsonObject()));
        }

        return new StoryNode(id, title, narrativeText, choices, isEnding, endingType);
    }

    private Choice parseChoice(JsonObject o) {
        String id           = o.get("id").getAsString();
        String text         = o.get("text").getAsString();
        String targetNodeId = o.get("targetNodeId").getAsString();

        Choice.Requirement req = null;
        if (o.has("requirement") && !o.get("requirement").isJsonNull()) {
            JsonObject r = o.getAsJsonObject("requirement");
            req = new Choice.Requirement(
                    getIntOrNull(r, "minStrength"),
                    getIntOrNull(r, "minIntelligence"),
                    getIntOrNull(r, "minAgility"),
                    getIntOrNull(r, "minCharisma"),
                    getStringOrNull(r, "requiredItemId")
            );
        }

        Choice.Consequence cons = null;
        if (o.has("consequence") && !o.get("consequence").isJsonNull()) {
            JsonObject c = o.getAsJsonObject("consequence");
            int healthDelta = c.get("healthDelta").getAsInt();
            int xpGain      = c.get("xpGain").getAsInt();

            List<String> given   = new ArrayList<>();
            List<String> removed = new ArrayList<>();
            for (JsonElement e : c.getAsJsonArray("itemsGiven"))   given.add(e.getAsString());
            for (JsonElement e : c.getAsJsonArray("itemsRemoved")) removed.add(e.getAsString());

            String narr = getStringOrNull(c, "narrativeOutcome");
            cons = new Choice.Consequence(healthDelta, xpGain, given, removed, narr);
        }

        return new Choice(id, text, targetNodeId, req, cons);
    }

    private Integer getIntOrNull(JsonObject o, String key) {
        return (o.has(key) && !o.get(key).isJsonNull()) ? o.get(key).getAsInt() : null;
    }

    private String getStringOrNull(JsonObject o, String key) {
        return (o.has(key) && !o.get(key).isJsonNull()) ? o.get(key).getAsString() : null;
    }
}