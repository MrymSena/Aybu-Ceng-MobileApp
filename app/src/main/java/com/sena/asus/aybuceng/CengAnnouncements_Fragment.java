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

/**
 * Created by Asus on 10.05.2017.
 */
public class CengAnnouncements_Fragment extends Fragment {

    private static final String TAG = "Ceng Ann";
    private TextView textAnn;
    private ProgressDialog mProgressDialog;
    private String url="http://ybu.edu.tr/muhendislik/bilgisayar/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cengann_fragment, container, false);

        textAnn = (TextView) view.findViewById(R.id.cengAnn_text);
        textAnn.setText("Ann");

        new Announcements().execute();
    /*    mProgressDialog=new ProgressDialog(getActivity());

        Thread t = new Thread(new Runnable() {
            Document doc;
            Element element;

            @Override
            public void run() {
                try {
                    handle.sendMessage(handle.obtainMessage());
                    doc = Jsoup.connect(url).get();
                    element = doc.select("div.caContent").first();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(element.text());
                            mProgressDialog.cancel();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        });
        t.start();*/
        return view;

}
 /*   Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mProgressDialog.setTitle("Ceng Announcemet");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
    };
*/



    public class Announcements extends AsyncTask<Void,Void,Void> {
        String words="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(getActivity(),"","Loading...");

        }

        @Override
        protected Void doInBackground(Void... params){
            try{

                Document doc = Jsoup.connect("http://ybu.edu.tr/muhendislik/bilgisayar/").get();
                Element element = doc.select("div.caContent").first();
                words=element.text();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            textAnn.setText(words);
           mProgressDialog.dismiss();


        }
    }

}
