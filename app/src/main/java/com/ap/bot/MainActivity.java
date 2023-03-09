package com.ap.bot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import com.ap.bot.Adapter.MessageListAdapter;
import com.ap.bot.Service.ChatBotService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MessageListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Button generateMsg = (Button) findViewById(R.id.generateBtn);
        Button stopService = (Button) findViewById(R.id.stopBtn);
        EditText editName = (EditText) findViewById(R.id.edtMessage);
        listView = (ListView) findViewById(R.id.messageList);

        registerServiceStateChangeReceiver();

        adapter = new MessageListAdapter(this,new ArrayList<String>());
        listView.setAdapter(adapter);


      generateMsg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String userName = editName.getText().toString();
              sendMessage(userName);
              editName.getText().clear();
          }
      });

      stopService.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              stopMessage();
          }
      });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    private void stopMessage() {
        Bundle data = new Bundle();
        data.putInt("ChatBot",10);
        Intent intent = new Intent(getApplicationContext(), ChatBotService.class);
        intent.putExtras(data);
        this.startService(intent);

    }

    private void sendMessage(String userName) {
        Bundle data = new Bundle();
        data.putInt("ChatBot",20);
        data.putString("ChatBott",userName);
        Intent intent = new Intent(this, ChatBotService.class);
        Log.d("Chat bot userName",userName);
        intent.putExtras(data);
        this.startService(intent);

    }
    private void registerServiceStateChangeReceiver() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_NEW_MESSAGE);
        intentFilter.addAction(Constants.BROADCAST_STOP);
        this.registerReceiver(mBroadcastReceiver,intentFilter);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // DISPLAY FETCHED BROADCAST INTO MESSAGE
            String action = intent.getAction();
            Bundle data  = intent.getExtras();

            if(Constants.BROADCAST_NEW_MESSAGE.equals(action)){
                String username = data.getString("BroadcastUsername");
                String message = data.getString("BroadcastMessage");
                displayMessage(message);

                Log.d("send button pressed",message);
            }
            else if(Constants.BROADCAST_STOP.equals(action)){
                String message = data.getString("BroadcastNotify");
                displayMessage(message);
                Log.d("Stop button pressed",message);

            }

        }
    };

    private void displayMessage(String message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        listView.setSelection(listView.getCount() -1);
    }

}