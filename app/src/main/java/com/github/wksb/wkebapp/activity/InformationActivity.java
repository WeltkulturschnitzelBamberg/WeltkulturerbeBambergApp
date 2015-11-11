package com.github.wksb.wkebapp.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.wksb.wkebapp.CustomHtmlTagHandler;
import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.InformationTable;

/**
 * This activity shows information about a world-heritage.
 * The shown content depends on the world-heritage.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InformationActivity extends AppCompatActivity {


    // Current Information used in this Activity
    private Information mCurrentInformation; // TODO Documentation

    // Definition of the different Views represented inside the Layout
    private ImageView mIv_info_image; // TODO Documentation
    private TextView mTv_info_information; // TODO Documentation

    // Definition of the Tags used in Intents send to this Activity
    public static final String TAG_PACKAGE = QuizActivity.class.getPackage().getName(); // TODO Documentation
    /** This TAG tags the ID of the Information, which is to be loaded, within an Intent send to this Activity*/
    public static final String TAG_INFORMATION_ID = TAG_PACKAGE + "information_id";
    /** FLAG for the Information ID, within an Intent send to this Activity,
     * tagged with the TAG {@link InformationActivity#TAG_INFORMATION_ID}, which indicates a Information with this ID doesn't exist
     * */
    public static final int FLAG_INFORMATION_ID_ERROR = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Initialise the Views
        mIv_info_image = (ImageView) findViewById(R.id.iv_info_image);
        mTv_info_information = (TextView) findViewById(R.id.tv_info_information);

        // Set up the ActionBar
        setUpActionBar();

        // Set up the Information
        setUpInformation();
    }

    private void setUpActionBar() {
        if (getSupportActionBar() == null)return;

        // Use Custom ActionBar Layout and Display BackButton
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

        // Set Custom ActionBar Layout
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
    }

    // TODO Documentation
    private void setUpInformation(){
        mCurrentInformation = getInformationFromID(getInformationIDFromIntent());
        mTv_info_information.setText(Html.fromHtml(mCurrentInformation.getInfoText(), null, new CustomHtmlTagHandler()));
    }

    // TODO Documentation
    private int getInformationIDFromIntent() {
        return getIntent().getIntExtra(TAG_INFORMATION_ID, FLAG_INFORMATION_ID_ERROR);
    }

    // TODO Documentation
    private Information getInformationFromID(int informationID) {
        String[] projection = {InformationTable.COLUMN_INFORMATION_ID, InformationTable.COLUMN_IMAGE, InformationTable.COLUMN_INFO_TEXT};
        String selection = InformationTable.COLUMN_INFORMATION_ID + "=?";
        String[] selectionArgs = {Integer.toString(informationID)};

        Cursor cursor = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_INFORMATION, projection, selection, selectionArgs, null);
        cursor.moveToFirst();

        int image = cursor.getInt(cursor.getColumnIndex(InformationTable.COLUMN_IMAGE));
        String infoText = cursor.getString(cursor.getColumnIndex(InformationTable.COLUMN_INFO_TEXT));

        return new Information(informationID, image, infoText);
    }

    // TODO Documentation
    private class Information {

        private final int id;
        private final int image;
        private final String infoText;

        public Information(int id, int image, String infoText) {
            this.id = id;
            this.image = image;
            this.infoText = infoText;
        }

        public int getId() {
            return id;
        }

        public int getImage() {
            return image;
        }

        public String getInfoText() {
            return infoText;
        }
    }
}