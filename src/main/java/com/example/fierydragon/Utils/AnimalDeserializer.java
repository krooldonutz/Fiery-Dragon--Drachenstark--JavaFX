package com.example.fierydragon.Utils;

import com.example.fierydragon.Animals.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AnimalDeserializer implements JsonDeserializer<Animal> {
    @Override
    public Animal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String animalType = jsonObject.get("animal").getAsString();

        return switch (animalType) {
            case "LIZARD" -> context.deserialize(json, Lizard.class);
            case "BAT" -> context.deserialize(json, Bat.class);
            case "SPIDER" -> context.deserialize(json, Spider.class);
            case "DRAGON" -> context.deserialize(json, Dragon.class);
            case "DRAGON_PIRATE" -> context.deserialize(json, DragonPirate.class);
            case "REVERSE_DRAGON" -> context.deserialize(json, ReverseDragon.class);
            case "THIEF" -> context.deserialize(json, RobinPig.class);
            default -> throw new JsonParseException("Unknown animal type: " + animalType);
        };
    }
}