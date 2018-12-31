package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // TextView to display place of origin information
    private TextView mOriginTextView;
    // TextView to display to description information
    private TextView mDescriptionTextView;
    // TextView to display list of ingredients
    private TextView mIngredientsTextView;
    // TextView to display alsoKnow names
    private TextView mAlsoKnowTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // ImageView to display the sandwich image
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        // Get reference to place of origin TextView
        mOriginTextView = findViewById(R.id.origin_tv);
        // Get reference to description TextView
        mDescriptionTextView = findViewById(R.id.description_tv);
        // Get reference to ingredients TextView
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        // Get reference to also known TextView
        mAlsoKnowTextView = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Get Sandwich json string for the position obtained from the intent
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        // Display a vector drawable for placeholder while the actual sandwich is being loaded
        Picasso.with(this)
                .load(sandwich.getImage()).placeholder(R.drawable.ic_23472)
                .into(ingredientsIv);

        // Set title of the activity to the sandwich name
        setTitle(sandwich.getMainName());
    }

    /** Helper method to show error when Sandwich json is not available
     * Call finish on the activity and displays a toast */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /** Helper method that populates the UI when a sandwich data is found
     * Expects a Sandwich object
     * Appends * to the beginning and a \n at the end of each ingredient string
     * Appends + to the beginning and a \n at the end of each other name string
     * If no other name available then set text to -*/
    private void populateUI(Sandwich sandwich) {
        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());
        for(String ingredient : sandwich.getIngredients()) {
            mIngredientsTextView.append("* " + ingredient + "\n");
        }
        if (sandwich.getAlsoKnownAs().size() > 0) {
            for(String otherName: sandwich.getAlsoKnownAs()) {
                mAlsoKnowTextView.append("+ " + otherName + "\n");
            }
        } else {
            mAlsoKnowTextView.setText("-");
        }
    }
}
