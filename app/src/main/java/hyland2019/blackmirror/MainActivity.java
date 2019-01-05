package hyland2019.blackmirror;

import android.content.Intent;
import android.graphics.Color;
import android.media.Rating;
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
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static hyland2019.blackmirror.Util.hex2Byte;

public class MainActivity extends AppCompatActivity implements SendListener{
    private TextView txtReceive;
    private NfcAdapter nfcAdapter;
    private Intent intent;
    private ReaderCB cb;
    private RatingBar yourBar;
    private RatingBar theirBar;
    private Button rteSend;
    private TextView r8txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb = new ReaderCB();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.GRAY);
        HostApdu.registerListener(this);
        txtReceive = findViewById(R.id.txtRecieve);
        theirBar = findViewById(R.id.theirBar);
        rteSend = findViewById(R.id.sendRating);
        r8txt = findViewById(R.id.ratetext);
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
                        rteSend.setVisibility(View.VISIBLE);
                        theirBar.setVisibility(View.VISIBLE);
                        r8txt.setVisibility(View.VISIBLE);
                    }
                });
                dep.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            send(null);
        }
    }

    public void send(View v){
        nfcAdapter.disableReaderMode(this);
        intent = new Intent(this, HostApdu.class);
        startService(intent);
    }

    @Override
    public void hasSent(String res) {
        stopService(intent);
        nfcAdapter.enableReaderMode(this, cb, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    public void sendRating(View v){
        //sends the rating
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        theirBar.setVisibility(View.INVISIBLE);
        rteSend.setVisibility(View.INVISIBLE);
        r8txt.setVisibility(View.INVISIBLE);
    }

}
