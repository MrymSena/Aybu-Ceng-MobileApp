package com.sena.asus.aybuceng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class CengAnnouncements_Fragment extends Fragment {


    private TextView textAnn;
    private ProgressDialog mProgressDialog;
    private ListView listAnn;
    private ArrayAdapter<String> adapter;
    ArrayList<String> anns;
    ArrayList<String>links;
    String buttonLink;
    Button tumDuyurular;
    private String url="http://ybu.edu.tr/muhendislik/bilgisayar/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cengann_fragment, container, false);
        anns= new ArrayList<>();
        links=new ArrayList<>();
        textAnn = (TextView) view.findViewById(R.id.cengAnn_text);
    //    textAnn.setText("Ann");
        tumDuyurular=(Button)view.findViewById(R.id.tumDuyurular);
        listAnn=(ListView)view.findViewById(R.id.listAnn);
        listAnn.setAdapter(adapter);

       // listAnn.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());
        new Announcements().execute();


        listAnn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //listAnn.getItemAtPosition(index).toString()

                String subUrl = links.get(position).toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(subUrl));
                startActivity(i);
            }
        });

        tumDuyurular.setOnClickListener(new View.OnClickListener() {
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



    public class Announcements extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(getActivity(),"","Loading...");

        }

        @Override
        protected Void doInBackground(Void... params){
            try{

                Document doc = Jsoup.connect(url).get();
                Element element = doc.select("div.caContent").first();
                Elements items = element.select("div.cncItem");
                Elements elinks=items.select("a[href]");

                Element elementbutton = doc.select("div.caButton").first();
                Elements buttonlinks =elementbutton.select("a[href]");
                Element buttonlink =buttonlinks.get(0);
                buttonLink=buttonlink.attr("abs:href");

                for (int i = 0; i < items.size(); i++) { //first row is the col names so skip it.
                    Element row = items.get(i);
                    Element link= elinks.get(i);
                //    Elements cols = row.select("span");

                    anns.add(row.text());
                    links.add(link.attr("abs:href"));

                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            String list="";
           /* for (int k=0; k<words.size(); k++) {
                list=list+words.get(k)+"\n";
            }*/
            adapter = new ArrayAdapter<>(
                    getActivity().getApplicationContext(),
                    R.layout.list_layout,
                    R.id.content,
                    anns
            );

            listAnn.setAdapter(adapter);

          Log.i("anns", "anss=" + listAnn);
            mProgressDialog.dismiss();


        }
    }

}
