package layout;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ase.ben.lab2.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;



/**
 * Created by Ben on 2/3/2016.
 */
public class weather extends AppCompatActivity {

    public TextView temp;
    public TextView condition;
    public TextView weatherPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);


        temp = (TextView) findViewById(R.id.temperature);
        condition = (TextView) findViewById(R.id.location);
        weatherPage = (TextView) findViewById(R.id.weatherPage);}


         public void main(String[] args) {
        try {
            URL url = new URL("http://weather.yahooapis.com/forecastrss?w=2430683");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            parse(con.getInputStream());
            // Give output for the command line
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String ns = null;

    public void parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("item")) {
                        eventType = parser.next();
                        if (parser.getName().equals("title")) {
                            eventType = parser.next();
                            weatherPage.setText(parser.getText());
                        }
                    } else if (parser.getName().equals("yweather:condition")) {
                        int x = parser.getAttributeCount();
                        for (int i = 0; i < x; i++) {
                            if (parser.getAttributeName(i).equals("text")) {
                                condition.setText(parser.getText());
                            } else if (parser.getAttributeName(i).equals("temp")) {
                                temp.setText(parser.getText());
                            }
                        }
                    }
                }
            }
            eventType = parser.next();
        } finally {
            in.close();
        }
    }

}

