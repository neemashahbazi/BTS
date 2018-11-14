package db.prj.BTS.domain;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "u_traders")
public class Trader extends User{
    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL)
    private List<Client> clientList;

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL)
    private List<PaymentTransaction> paymentTransactionList;



    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }
}
