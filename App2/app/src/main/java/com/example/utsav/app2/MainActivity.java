package com.example.utsav.app2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Messenger messenger;
    boolean isBound = false;
    EditText editText;
    private Button button;
    private boolean isConnected = false;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d("conncected");
            messenger = new Messenger(service);
            isBound = true;
            Toast.makeText(getApplicationContext(), "bound to service", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    // Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClassName("com.example.utsav.gitbranchingmerging", "com.example.utsav.gitbranchingmerging.MessengerSrvice");
        isConnected = bindService(intent, connection, BIND_AUTO_CREATE);

        editText = (EditText) findViewById(R.id.edit_text);
        button = ((Button) findViewById(R.id.button));
        button.setEnabled(isConnected);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound) {
                    Logger.d("1");
                    Bundle bundle = new Bundle();
                    bundle.putString("user", "utsav");
                    Logger.d("1");
                    bundle.putString("message", editText.getText().toString().equals("") ? "HELLO" : editText.getText().toString());
                    Message message = new Message();
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "cudnt connect", Toast.LENGTH_LONG).show();
                        Logger.d("error in sending");
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
