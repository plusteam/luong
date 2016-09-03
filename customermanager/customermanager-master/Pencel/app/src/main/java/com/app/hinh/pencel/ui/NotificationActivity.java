package com.app.hinh.pencel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.app.hinh.pencel.R;
import com.app.hinh.pencel.model.CustomListview;
import com.app.hinh.pencel.model.Customer;
import com.app.hinh.pencel.model.CustomerResponse;
import com.app.hinh.pencel.network.CustomerAPI;
import com.app.hinh.pencel.util.DialogCustomer;
import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;
import com.riontech.calendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hinh1 on 9/1/2016.
 */
public class NotificationActivity extends AppCompatActivity {
    private CustomCalendar customCalendar;
    private ArrayList<Customer> customers;
    private ArrayList<CustomListview> cus;
    private DialogCustomer dialogCustomer;
    private ArrayList<Customer> customerReturn;  //là mảng customer trả về sau khi chheck dialog
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        customCalendar = (CustomCalendar) findViewById(R.id.customCalendar);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        int id = extra.getInt("id");
        Log.d("id account",String.valueOf(id));
        getCustomers(id);
        String[] arr = {"2016-06-10", "2016-06-11", "2016-06-15", "2016-06-16", "2016-06-25"};
        for (int i = 0; i < 5; i++) {
            int eventCount = 10;
            customCalendar.addAnEvent(arr[i], eventCount, getEventDataList(eventCount));
        }

        customers = new ArrayList<>();
        cus = new ArrayList<>();
        customerReturn = new ArrayList<>();
    }



    public ArrayList<EventData> getEventDataList(int count) {
        ArrayList<EventData> eventDataList = new ArrayList();

        for (int i = 0; i < count; i++) {
            EventData dateData = new EventData();
            ArrayList<dataAboutDate> dataAboutDates = new ArrayList();

            dateData.setSection(CalendarUtils.getNAMES()[new Random().nextInt(CalendarUtils.getNAMES().length)]);
            dataAboutDate dataAboutDate = new dataAboutDate();

            int index = new Random().nextInt(CalendarUtils.getEVENTS().length);

            dataAboutDate.setTitle(CalendarUtils.getEVENTS()[index]);
            dataAboutDate.setSubject(CalendarUtils.getEventsDescription()[index]);
            dataAboutDates.add(dataAboutDate);

            dateData.setData(dataAboutDates);
            eventDataList.add(dateData);
        }

        return eventDataList;
    }


    /* @Override
     public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);

         // Checks the orientation of the screen
         if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
             Toast.makeText(NotificationActivity.this, "landscape", Toast.LENGTH_LONG).show();
         } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
             Toast.makeText(this, "portrait", Toast.LENGTH_LONG).show();
         }
     }*/
    private void getCustomers(int id){
        CustomerAPI.getCustomer(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if(response != null){
                    CustomerResponse customerResponse = response.body();
                    List<Customer> customer =  customerResponse.getData();
                    bindData(customer);
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {

            }
        },id );
    }

    private void bindData(List<Customer> customer1){
        this.customers.addAll(customer1);
        for (int i = 0; i < customers.size(); i ++){
            cus.add(new CustomListview(this.customers.get(i).getName().toString(), "", false, null, 0));
        }

    }

    public void showDialog(View view) {
        dialogCustomer = new DialogCustomer(this);

        dialogCustomer.setArrCustomer(cus);

        dialogCustomer.getListCusomer().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(NotificationActivity.this, "" + i, Toast.LENGTH_SHORT).show();
                checkBox = (CheckBox)view.findViewById(R.id.cbMark);
                checkBox.setVisibility(View.VISIBLE);
                if (!checkBox.isChecked()){
                    checkBox.setChecked(true);
                    customerReturn.add(customers.get(i));
                }else {
                    checkBox.setChecked(false);
                    String name = customers.get(i).getName();
                    for(int in = 0; in < customerReturn.size() ; in++){
                        if(name.equals(customerReturn.get(i).getName())){
                            customerReturn.remove(i);
                        }
                    }
                }
            }
        });

        dialogCustomer.getButtonOk().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCustomer.getDialogCustomer().dismiss();
            }
        });

        dialogCustomer.showDialog();

    }
}
