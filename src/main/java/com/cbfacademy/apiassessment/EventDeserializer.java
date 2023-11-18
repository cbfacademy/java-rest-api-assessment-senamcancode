package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.FinTechClasses.Event;
import com.cbfacademy.apiassessment.FinTechClasses.EventClasses.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EventDeserializer implements JsonDeserializer<Event> {
    //Used to allow events to be deserialised
    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String eventName = jsonObject.get("eventName").getAsString();

        switch (eventName){
            case "Cybersecurity Leak":
                return new CybersecurityLeak("Cybersecurity Leak");
            case "Economic Boom":
               return new EconomicBoom("Economic Boom");
            case "Economic Downturn":
                return new EconomicDownturn("Economic Downturn");
            case "Social media viral event":
                return new SocialMediaViral("Social media viral event");
            case "No Event":
                return new NoEvent("No Event");
            default:
                throw new JsonParseException("Unknown event type " + eventName);

        }
    }
}
