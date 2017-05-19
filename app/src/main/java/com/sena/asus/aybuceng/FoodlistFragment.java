package com.sena.asus.aybuceng;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Asus on 10.05.2017.
 */
public class FoodlistFragment extends Fragment {
    private static final String TAG="foodlist";
    private TextView text,date;

    private ProgressDialog mProgressDialog;
    private String url ="http://ybu.edu.tr/sks/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.foodlist_fragment,container,false);

        text=(TextView)view.findViewById(R.id.foodText);
        date=(TextView)view.findViewById(R.id.Date);

        //    text.setText("Foodlist");

        new Foodlist().execute();



        return view;
    }



    public class Foodlist extends AsyncTask<Void,Void,Void> {
        ArrayList<String> words = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading...");
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(url).get();

                //  for(int i=0; i<list; i++) {
                Element table = doc.select("table").first();
                Elements rows = table.select("tr");
                //  words = element.text();
                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    words.add(cols.text());

                }
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute (Void aVoid){
                super.onPostExecute(aVoid);
                String list="";
                for (int k=2; k<words.size(); k++) {
                   list=list+words.get(k)+"\n";
                }
                date.setText(words.get(1));
                text.setText(list);
                mProgressDialog.dismiss();

            }
        }

}
