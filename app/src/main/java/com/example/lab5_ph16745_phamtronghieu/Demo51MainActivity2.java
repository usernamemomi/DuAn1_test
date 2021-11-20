package com.example.lab5_ph16745_phamtronghieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//PH16745
//PHẠM TRỌNG HIẾU
public class Demo51MainActivity2 extends AppCompatActivity {
ListView listView;
ArrayAdapter<String> arrayAdapter;
List<String> arrTitle = new ArrayList<>();
List<String> arrLink = new ArrayList<>();
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo51_main2);
        listView = findViewById(R.id.demo51lv);
        //gọi hàm kết nối đến server
        new RSSHauTruong().execute("https://ngoisao.net/rss/hau-truong.rss");
        //đưa lên listview
        arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , arrTitle);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //lấy về link sau khi click
                String link = arrLink.get(position);
                intent = new Intent(Demo51MainActivity2.this , Demo52Detail.class);
                intent.putExtra("linkWeb" , link);
                startActivity(intent);
            }
        });
    }
    //Định nghĩa lớp kết nối với server
    public  class RSSHauTruong extends AsyncTask<String , Void , String>{
//1  : lấy dữ liệu từ server
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            //bắt đầu sử lý
            //1 : khai báo đường dẫn đọc dữ liệu
            try {
                URL url = new URL(strings[0]);//Lấy về đường dẫn đầu tiên
                //kết nối với server : url.openConnection
                //tạo lượng đọc : InputStreamReader
                InputStreamReader reader = new InputStreamReader(url.openConnection().getInputStream());
                //tạo bộ đệm
                BufferedReader bufferedReader = new BufferedReader(reader);
                //đọc theo dòng
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);//đưa dòng đọc được vào content
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //kết thúc sử lý
            return content.toString();
        }
// trả kết quả cho người dùng
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLParser xmlParser = new XMLParser();
            try {
                //tạo 1 tài liệu từ s
                Document document = xmlParser.getDocument(s);
                //lấy về tất cả các node có tên là item
                NodeList nodeList = document.getElementsByTagName("item");
                //lấy về link và title
                String link = "" , title = "";
                for(int i = 0 ; i < nodeList.getLength() ; i++){
                    //lấy về từng thành phần
                    Element element = (Element)nodeList.item(i);
                    //lấy giá trị của từng thành phần
                    title = xmlParser.getValue(element , "title");
                    arrTitle.add(title);
                    link = xmlParser.getValue(element , "link");
                    arrLink.add(link);
                }
                arrayAdapter.notifyDataSetChanged();//cập nhập adapter
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
}