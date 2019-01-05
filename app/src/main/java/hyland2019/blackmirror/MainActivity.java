package hyland2019.blackmirror;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    private TextView txtReceive;
    private Button btnSend;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtReceive = findViewById(R.id.txtRecieve);
        btnSend = findViewById(R.id.btnSend);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.setNdefPushMessageCallback(this, this);
        nfcAdapter.setNdefPushMessage(new NdefMessage(new NdefRecord[]{NdefRecord.createMime("application/vnd.hyland2019/blackmirror", "hello world".getBytes())}), this, this);
    }

    public void send(View v){
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent e){
        String text = "hello world";
        NdefMessage msg = new NdefMessage(new NdefRecord[]{NdefRecord.createMime("application/vnd.hyland2019/blackmirror", text.getBytes())});
        return msg;
    }

    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        txtReceive.setText(new String(msg.getRecords()[0].getPayload()));
    }


}
