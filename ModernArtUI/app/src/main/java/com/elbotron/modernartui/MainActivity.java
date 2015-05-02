package com.elbotron.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends ActionBarActivity {

    private DialogFragment mDialog;
    private SeekBar mSeekBar;
    private LinearLayout lRed;
    private LinearLayout lPink;
    private LinearLayout lViolet;
    private LinearLayout lOrange;
    private String TAG = "MESSAGE: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lRed = (LinearLayout) findViewById(R.id.red);
        lPink = (LinearLayout) findViewById(R.id.pink);
        lViolet = (LinearLayout) findViewById(R.id.violet);
        lOrange = (LinearLayout) findViewById(R.id.orange);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;
            float proportion = 0;

            @Override
            public void onProgressChanged(SeekBar mSeekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                proportion = (float) progressValue;
                Log.i(TAG, "proportion is " + proportion);
                updateColors(proportion);
            }

            @Override
            public void onStartTrackingTouch(SeekBar mSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

    }

    private void updateColors(float proportion) {

        int red = getResources().getColor(R.color.red);
        Log.i(TAG, "red is " + red);
        int pink = getResources().getColor(R.color.pink);
        Log.i(TAG, "pink is " + pink);
        int orange = getResources().getColor(R.color.orange);
        Log.i(TAG, "orange is " + orange);
        int violet = getResources().getColor(R.color.violet);
        Log.i(TAG, "violet is " + violet);

        int antiRed = getResources().getColor(R.color.anti_red);
        int antiPink = getResources().getColor(R.color.anti_pink);
        int antiOrange = getResources().getColor(R.color.anti_orange);
        int antiViolet = getResources().getColor(R.color.anti_violet);

        float mProportion = proportion / 40;

        lRed.setBackgroundColor(interpolateColor(red, antiRed, mProportion));
        lPink.setBackgroundColor(interpolateColor(pink, antiPink, mProportion));
        lOrange.setBackgroundColor(interpolateColor(orange, antiOrange, mProportion));
        lViolet.setBackgroundColor(interpolateColor(violet, antiViolet, mProportion));

    }

    private float interpolate(float color, float antiColor, float proportion) {
        return (color + ((antiColor - color) * proportion));
    }

    private int interpolateColor(int color, int antiColor, float proportion) {
        float[] hsva = new float[3];
        float[] hsvb = new float[3];
        Color.colorToHSV(color, hsva);
        Color.colorToHSV(antiColor, hsvb);
        for (int i = 0; i < 3; i++) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }
        return Color.HSVToColor(hsvb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.information) {
            //open alertdialog
            mDialog = AlertDialogFragment.newInstance();
            mDialog.show(getFragmentManager(), "Alert");
        }

        return super.onOptionsItemSelected(item);
    }

    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(getString(R.string.alert_message))

                            // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                            // Set up No Button
                    .setNegativeButton(getString(R.string.no_dialog),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                //close dialog and return
                                }
                            })

                            // Set up Yes Button
                    .setPositiveButton(getString(R.string.yes_dialog),
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    //open moma website
                                    String url = getString(R.string.url);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);
                                }
                            }).create();
        }
    }
}
