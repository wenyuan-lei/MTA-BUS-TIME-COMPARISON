package com.wen.android.mtabuscomparison.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wen.android.mtabuscomparison.R;
import com.wen.android.mtabuscomparison.utilities.BusDbHelper;

/**
 * Created by yuan on 4/10/2017.
 */

public class AboutFragment extends Fragment {
    private Button mSendEmail;
    private static final String TAG_EXCEPTION = "exception error";
    private SQLiteDatabase mDb;
    private Cursor mCursor;

    public AboutFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        BusDbHelper dbHelper = new BusDbHelper(getContext());
        mDb = dbHelper.getReadableDatabase();
        mSendEmail = (Button)v.findViewById(R.id.send_email);
        mSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:longying2011@gmail.com"));
                try{
                    startActivity(emailIntent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getContext(),"Sorry, Can't found the Email App",Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        return v;
    }

    /**
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isBusStation = false;
        if (data == null){
            return;
        }
        Place place = PlacePicker.getPlace(getActivity(), data);
        for (int i: place.getPlaceTypes()){
            if (i == Place.TYPE_BUS_STATION){
                isBusStation = true;
                break;
            }
        }
        if (isBusStation){
            //define a projection that specifies which colums from the database you will actually use after this query
            String[] projection = {
                    BusContract.BusEntry.COLUMN_BUS_STOP_CODE,
                    BusContract.BusEntry.COLUMN_BUS_STOP_LAT
            };
            //filter results WHERE 'title' = 'My Title'
            String selection = BusContract.BusEntry.COLUMN_BUS_NAME + " = ?";
            String placeName = place.getName().toString();
            placeName = placeName.replaceAll("\"","");
            placeName = placeName.replaceAll("\\s+","");
            placeName = placeName.replaceAll("/","");
            placeName = placeName.replaceAll("&","");
            placeName = placeName.replaceAll("'","");
            placeName = placeName.toUpperCase();
            String[] selectionArgs = {placeName};
            String sortOrder = BusContract.BusEntry.COLUMN_BUS_STOP_CODE + " DESC";
            mCursor = mDb.query(
                    BusContract.BusEntry.TABLE_NAME_ALL_BUS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
            Double minmnum = 9999999.0;
            String stopCode = null;
            while (mCursor.moveToNext()){
                String stopCodeTemp = mCursor.getString(mCursor.getColumnIndex(BusContract.BusEntry.COLUMN_BUS_STOP_CODE));
                String latitudeStr = mCursor.getString(mCursor.getColumnIndex(BusContract.BusEntry.COLUMN_BUS_STOP_LAT));
                Double latitude1 = Double.parseDouble(latitudeStr);
                Double latitude2 = place.getLatLng().latitude;
                if (Math.abs(minmnum) > Math.abs(latitude1 - latitude2)){
                    minmnum = latitude1 - latitude2;
                    stopCode = stopCodeTemp;
                }


                //Toast.makeText(getActivity(),stopCode,Toast.LENGTH_LONG)
                 //       .show();
            }
            Toast.makeText(getActivity(),stopCode,Toast.LENGTH_LONG)
                    .show();

             String[] stopcodeArray = new String[1];
            //get the bus code from the user input
            stopcodeArray[0] = stopCode;
            if (stopcodeArray[0] == null) {
                return;
            }
            Intent intent = new Intent(getActivity(), SearchResultActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT,stopcodeArray);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),"please select another place",Toast.LENGTH_LONG)
                    .show();
        }
    }
    **/
}
