package db.prj.BTS.view;

import db.prj.BTS.domain.Client;
import db.prj.BTS.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

@Named
public class TraderView {
    @Autowired
    TraderService traderService;

    public List<Client> getAllClientsByTrader() {
        return traderService.getClientList();
    }
}
