package com.example.davutcesmeci.satisonayv02;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends Activity {

    /**
     * Product Variables
     */
    int clickCounter = 0;
    int minRandVal = 1;
    int maxRandVal = 3;

    String[] productColors = {"Kırmızı", "Yeşil", "Mavi", "Siyah", "Beyaz", "Sarı"};
    String[] productNames = {"Gömlek", "Etek", "Pantolon", "Ayakkabı"};
    String[] ModelGomlek = {"Kareli", "Klasik", "Slim", "Regular"};
    String[] ModelEtek = {"Piliseli", "Uzun", "Desenli", "Kalem"};
    String[] ModelPantolon = {"Denim", "Kadife", "Slim", "Kanvas"};
    String[] ModelAyak = {"Topuklu", "Spor", "Günlük", "Sandalet"};

    Random rand = new Random();

    Handler h = new Handler();
    int delay = 5 * 1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    /**
     * End of Product Var
     */

    AppInfoAdapter adapter;

    /**
     * Items entered by the user is stored in this ArrayList variable
     */
    ArrayList<AppInfo> app_info = new ArrayList<AppInfo>();
    //AppInfo app_info[] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listApplication = (ListView) findViewById(R.id.listApplication);
        Button b = (Button) findViewById(R.id.btnButton1);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int aSize = adapter.mCheckStates.size();
                /**StringBuilder result = new StringBuilder();*/
                if (aSize > 0)
                {
                    SparseBooleanArray sArr = adapter.mCheckStates;

                    List<AppInfo> deleteList = new ArrayList<AppInfo>();

                    for (int i = 0; i < aSize; i++) {
                        boolean state = sArr.valueAt(i);
                        if (state) {
                            int keyVal = sArr.keyAt(i);
                            adapter.setChecked(keyVal,false);
                            deleteList.add(app_info.get(keyVal));

                            /**result.append(app_info.get(i).applicationName);
                             result.append("\n");*/
                        }
                    }

                    for (int i=0;i<deleteList.size();i++)
                    {
                        app_info.remove(deleteList.get(i));
                    }


                    adapter.notifyDataSetChanged();
                }


                /**Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();*/
            }

        });

        /**
         ApplicationInfo applicationInfo = getApplicationInfo();
         PackageManager pm = getPackageManager();
         List<PackageInfo> pInfo = new ArrayList<PackageInfo>();
         pInfo.addAll(pm.getInstalledPackages(0));
         app_info = new AppInfo[pInfo.size()];

         int counter = 0;
         for(PackageInfo item: pInfo){
         try{

         applicationInfo = pm.getApplicationInfo(item.packageName, 1);

         app_info[counter] = new AppInfo(String.valueOf(pm.getApplicationLabel(applicationInfo)));

         System.out.println(counter);

         }
         catch(Exception e){
         System.out.println(e.getMessage());
         }

         counter++;
         }
         */
        adapter = new AppInfoAdapter(this, R.layout.listview_item_row, app_info);
        listApplication.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        //start handler as activity become visible

        /** Reference to the button of the layout main.xml */
        final Button btn = (Button) findViewById(R.id.btnButton1);

        h.postDelayed(runnable = new Runnable() {

            public void run() {

                if(adapter.data.size()<100)
                {
                    //do something
                    addItems(btn);
                }
                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    /**
     * Setting the event listener for the add button
     */
    public void addItems(View v) {

        Random randomNum = new Random();

        Product product = new Product();
        product.UniqueIdentifier = String.valueOf(++clickCounter);
        product.Barcode = generateString();

        int randValColor = rand.nextInt(5) + minRandVal;
        product.Color = productColors[randValColor];
        int randVal = rand.nextInt(maxRandVal) + minRandVal;
        product.Name = productNames[randVal - 1];
        int randModel = rand.nextInt(maxRandVal) + minRandVal;

        product.Model = ModelGomlek[randModel];
        switch (randVal) {
            case 1:
                product.Model = ModelGomlek[randModel];
                break;
            case 2:
                product.Model = ModelEtek[randModel];
                break;
            case 3:
                product.Model = ModelPantolon[randModel];
                break;
            case 4:
                product.Model = ModelAyak[randModel];
                break;
        }

        String strProduct = product.UniqueIdentifier + " " + product.Name + " " + product.Model + " " + product.Color + "\n" + product.Barcode;

        AppInfo appInfo = new AppInfo();
        appInfo.applicationName = strProduct;
        app_info.add(appInfo);

        adapter.notifyDataSetChanged();
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
