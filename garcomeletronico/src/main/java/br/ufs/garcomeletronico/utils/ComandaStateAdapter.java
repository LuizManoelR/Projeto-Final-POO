package br.ufs.garcomeletronico.utils;

import com.google.gson.*;

import br.ufs.garcomeletronico.model.ComandaState;
import br.ufs.garcomeletronico.model.ComandaAberta;
import br.ufs.garcomeletronico.model.ComandaFechada;
import br.ufs.garcomeletronico.model.ComandaLivre;

import java.lang.reflect.Type;

public class ComandaStateAdapter implements JsonSerializer<ComandaState>, JsonDeserializer<ComandaState> {

    @Override
    public JsonElement serialize(ComandaState state, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        // salva o tipo da classe
        obj.addProperty("type", state.getClass().getSimpleName());
        // salva os dados da classe (se houver)
        obj.add("data", context.serialize(state, state.getClass()));
        return obj;
    }

    @Override
    public ComandaState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();
        JsonElement data = obj.get("data");

        try {
            switch (type) {
                case "ComandaLivre":
                    return context.deserialize(data, ComandaLivre.class);
                case "ComandaFechada":
                    return context.deserialize(data, ComandaFechada.class);
                case "ComandaAberta":
                    return context.deserialize(data, ComandaAberta.class);
                default:
                    throw new IllegalArgumentException("Tipo desconhecido: " + type);
            }
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
