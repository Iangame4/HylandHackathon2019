package hyland2019.blackmirror;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

import java.nio.ByteBuffer;

import static hyland2019.blackmirror.Util.byte2hex;
import static hyland2019.blackmirror.Util.hex2Byte;

public class HostApdu extends HostApduService {
    private static String res;
    private static int send = 123;
    private static SendListener list;

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle){
        list.hasSent("");
        return hex2Byte(Integer.toHexString(send));
    }

    public static void registerListener(SendListener listener){
        list = listener;
    }

    @Override
    public void onDeactivated(int i) {

    }

    public static String getRes(){
        return res;
    }
}
