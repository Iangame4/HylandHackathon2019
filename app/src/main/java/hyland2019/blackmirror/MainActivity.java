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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static hyland2019.blackmirror.Util.byte2hex;
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
    private TextView alex;
    private ImageView alix;
    private final String HEX_CONNECT = "00A4040007A0000002471001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb = new ReaderCB();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.GRAY);
        HostApdu.registerListener(this);
        theirBar = findViewById(R.id.theirBar);
        rteSend = findViewById(R.id.sendRating);
        r8txt = findViewById(R.id.ratetext);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        alex = findViewById(R.id.Alex);
        alix = findViewById(R.id.alexpic);
        yourBar = findViewById(R.id.yourBar);
        nfcAdapter.enableReaderMode(this, new ReaderCB(), NfcAdapter.FLAG_READER_NFC_A, null);
        System.out.println("READING READING");
    }

    protected class ReaderCB implements NfcAdapter.ReaderCallback{

        @Override
        public void onTagDiscovered(Tag tag) {
            IsoDep dep = IsoDep.get(tag);
            try {
                dep.connect();
                byte[] res = dep.transceive(hex2Byte(HEX_CONNECT));
                final int id = Integer.parseInt(byte2hex(res), 16);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rteSend.setVisibility(View.VISIBLE);
                        theirBar.setVisibility(View.VISIBLE);
                        r8txt.setVisibility(View.VISIBLE);
                        alex.setVisibility(View.VISIBLE);
                        alix.setVisibility(View.VISIBLE);

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
        System.out.println("SENDING SENDING");
    }

    @Override
    public void hasSent(String res) {
        stopService(intent);
        nfcAdapter.enableReaderMode(this, cb, NfcAdapter.FLAG_READER_NFC_A, null);
        System.out.println("READING READING1");

    }

    //Sends the rating
    public void sendRating(View v){
        theirBar.setVisibility(View.INVISIBLE);
        rteSend.setVisibility(View.INVISIBLE);
        r8txt.setVisibility(View.INVISIBLE);
        alex.setVisibility(View.INVISIBLE);
        alix.setVisibility(View.INVISIBLE);
        yourBar.setNumStars(0);
    }

}
