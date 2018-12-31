package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /** Utility function to parse the sandwich JSON object
     * Returns a Sandwich object which is used while populating the Detail activity layout */
    public static Sandwich parseSandwichJson(String json) {
        try {
            // Create JSONObject from the json string
            JSONObject sandwich = new JSONObject(json);
            // Get Name JSONObject
            JSONObject name = sandwich.getJSONObject("name");
            // Get mainName String
            String mainName = name.getString("mainName");
            // Get JSONArray for alsoKnownAs
            JSONArray alsoKnownAsJson = name.getJSONArray("alsoKnownAs");
            // Get placeOfOrigin String
            String placeOfOrigin = sandwich.getString("placeOfOrigin");
            // Get description String
            String description = sandwich.getString("description");
            // Get image String
            String image = sandwich.getString("image");
            // Get JSONArray for ingredients
            JSONArray ingredientsJson = sandwich.getJSONArray("ingredients");
            // Create temporary list for alsoKnownAs sandwich name
            List<String> alsoKnownAs = new ArrayList<String>();
            // Create temporary list for ingredients
            List<String> ingredients = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJson.getString(i));
            }
            for (int i = 0; i < ingredientsJson.length(); i++) {
                ingredients.add(ingredientsJson.getString(i));
            }
            // Return a new Sandwich object
            return  new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
