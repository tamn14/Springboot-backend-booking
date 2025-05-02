package NCT.com.Booking.Service.ServiceInterface;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QrService {
    public byte[] generateQRCodeToFile(String json , int width, int height)  throws WriterException , IOException;
}
