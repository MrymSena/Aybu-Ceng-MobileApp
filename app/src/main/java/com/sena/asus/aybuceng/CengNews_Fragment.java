package com.sena.asus.aybuceng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Asus on 10.05.2017.
 */
public class CengNews_Fragment extends Fragment {

        private static final String TAG="Ceng News";
        private TextView textNews;

        private ListView listNews;
        private ProgressDialog mProgressDialog;
        ArrayList<String> news;
        ArrayList<String>links;
        private ArrayAdapter<String> adapter;
        private String buttonLink;
        Button tümHaberler;
        String url="http://ybu.edu.tr/muhendislik/bilgisayar/";
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.cengnews_fragment, container, false);
            news= new ArrayList<>();
            links=new ArrayList<>();

            tümHaberler=(Button)view.findViewById(R.id.tumHaberler);
            listNews=(ListView)view.findViewById(R.id.listNews);
            listNews.setAdapter(adapter);
            new News().execute();
            //textNews = (TextView) view.findViewById(R.id.cengNews_text);
          //  textNews.setText("news");

            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    //listAnn.getItemAtPosition(index).toString()
                    try {
                        String subUrl = links.get(position).toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(subUrl));
                        startActivity(i);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"Link adresi yoktur",Toast.LENGTH_LONG).show();
                    }
                }
            });
            tümHaberler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String subUrl = buttonLink;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(subUrl));
                        startActivity(i);
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"Link adresi yoktur",Toast.LENGTH_LONG).show();
                    }
                }
            });

            return view;
        }

    public class News extends AsyncTask<Void,Void,Void> {
       String words = "";

     @Override
       protected void onPreExecute() {
           super.onPreExecute();
           mProgressDialog = ProgressDialog.show(getActivity(), "", "Loading...");
       }

       @Override
       protected Void doInBackground(Void... params) {
           try {

               Document doc = Jsoup.connect("http://ybu.edu.tr/muhendislik/bilgisayar/").get();
               Element element = doc.select("div.cnContent").first();
               Elements items = element.select("div.cncItem");
               Elements elinks=items.select("a[href]");

               Element elementbutton = doc.select("div.cnButton").first();
               Elements buttonlinks =elementbutton.select("a[href]");
               Element buttonlink =buttonlinks.get(0);
               buttonLink=buttonlink.attr("abs:href");

               for (int i = 0; i < items.size(); i++) { //first row is the col names so skip it.
                   Element row = items.get(i);
                   Element link= elinks.get(i);
                   //    Elements cols = row.select("span");

                   news.add(row.text());
                   links.add(link.attr("abs:href"));

               }

            //   words = element.text();
           } catch (Exception e) {
               e.printStackTrace();
           }
           return null;
       }

       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
        //   textNews.setText(words);
           adapter = new ArrayAdapter<>(
                   getActivity().getApplicationContext(),
                   R.layout.list_layout,
                   R.id.content,
                   news
           );

           listNews.setAdapter(adapter);

       //    Log.i("anns", "anss=" + listAnn);

           mProgressDialog.dismiss();

       }

   }
}
