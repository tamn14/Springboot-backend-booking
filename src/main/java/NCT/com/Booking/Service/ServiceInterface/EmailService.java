package NCT.com.Booking.Service.ServiceInterface;


public interface EmailService {
    public void SendMessage(String from, String to  , byte[] qrBytes) ;
}
