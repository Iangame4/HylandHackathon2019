package hyland2019.blackmirror;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import static hyland2019.blackmirror.Util.hex2Byte;

public class MainActivity extends AppCompatActivity implements SendListener{
    private TextView txtReceive;
    private Button btnSend;
    private NfcAdapter nfcAdapter;
    private Handler h;
    private Runnable r;
    private Intent intent;
    private int count = 0;
    private ReaderCB cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb = new ReaderCB();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.GRAY);
        HostApdu.registerListener(this);
        txtReceive = findViewById(R.id.txtRecieve);
        btnSend = findViewById(R.id.btnSend);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableReaderMode(this, new ReaderCB(), NfcAdapter.FLAG_READER_NFC_A, null);
    }

    protected class ReaderCB implements NfcAdapter.ReaderCallback{

        @Override
        public void onTagDiscovered(Tag tag) {
            IsoDep dep = IsoDep.get(tag);
            try {
                dep.connect();
                final byte[] res = dep.transceive(hex2Byte("00A4040007A0000002471001"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtReceive.setText(Util.byte2hex(res));
                    }
                });
                dep.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.knowyourstars, menu);
        return true;
    }

    public void send(View v){
        //txtReceive.setText(HostApdu.getRes());
        nfcAdapter.disableReaderMode(this);
        //System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        intent = new Intent(this, HostApdu.class);
        startService(intent);
    }

    @Override
    public void hasSent() {
        //System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        stopService(intent);
        nfcAdapter.enableReaderMode(this, cb, NfcAdapter.FLAG_READER_NFC_A, null);
        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
    }
}
