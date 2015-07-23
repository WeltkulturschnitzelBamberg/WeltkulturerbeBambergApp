package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.InformationTable;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Michael on 15.07.2015.
 */
public class InformationAsyncTaskLoader extends AsyncTaskLoader {

    public static final int LOADER_ID = 3;

    public InformationAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            writeInformationsToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO Docuemntation
    private void writeInformationsToDatabase() throws IOException, ParseException, JSONException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new InputStreamReader(getContext().getAssets().open("information.json"), "UTF-8"));

        // Get information for each wayoint
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray informationArray = (JSONArray) jsonObject.get("information");
            for (int i = 0; i < informationArray.size(); i++) {
                JSONObject information = (JSONObject) informationArray.get(i);

                long id = 0L;
                String image = null;
                String infoText = "";

                if (information.get("id") instanceof Long) id = (long) information.get("id");
                if (information.get("image") instanceof String) image = (String) information.get("image");
                if (information.get("info-text") instanceof JSONArray) {
                    JSONArray infoTextArray = (JSONArray) information.get("info-text");
                    for (int k = 0; k < infoTextArray.size(); k++) {
                        infoText += (String) infoTextArray.get(k);
                    }
                }

                ContentValues values = new ContentValues();
                values.put(InformationTable.COLUMN_INFORMATION_ID, id);
                values.put(InformationTable.COLUMN_IMAGE, image);
                values.put(InformationTable.COLUMN_INFO_TEXT, infoText);

                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_INFORMATION, values);
            }
        }
    }
}