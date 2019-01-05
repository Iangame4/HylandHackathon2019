package hyland2019.blackmirror;

import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback, SendListener{
    private TextView txtReceive;
    private Button btnSend;
    private NfcAdapter nfcAdapter;
    private Handler h;
    private Runnable r;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HostApdu.registerListener(this);
        txtReceive = findViewById(R.id.txtRecieve);
        btnSend = findViewById(R.id.btnSend);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    public void send(View v){
        //txtReceive.setText(HostApdu.getRes());
        nfcAdapter.disableReaderMode(this);
        //System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        intent = new Intent(this, HostApdu.class);
        startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNn");
        IsoDep dep = IsoDep.get(tag);
        try {
            dep.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final byte[] res = dep.transceive(Util.hex2Byte("00A4040007A0000002471001"));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtReceive.setText(Util.byte2hex(res));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //nfcAdapter.disableReaderMode(this);
    }

    @Override
    public void hasSent() {
        //System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        stopService(intent);
        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A, null);
        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
    }
}
