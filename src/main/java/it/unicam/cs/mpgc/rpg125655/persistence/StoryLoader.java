package it.unicam.cs.mpgc.rpg125655.persistence;

import it.unicam.cs.mpgc.rpg125655.model.story.Story;

import java.io.IOException;

public interface StoryLoader {

    Story load() throws IOException;
}