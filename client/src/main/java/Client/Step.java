package Client;

/**
 * Created by CORE i7 on 08/06/2017.
 */
public class Step {

    private int quantity;

    private long registerDate;


    public Step(int cant, long date){
        quantity=cant;
        registerDate=date;
    }

    public long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(long registerDate) {
        this.registerDate = registerDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
