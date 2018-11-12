package db.prj.BTS.view;

import db.prj.BTS.domain.Client;
import db.prj.BTS.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

@Named
public class ClientView {

    @Autowired
    ClientService clientService;


    public List<Client> getAllClients() {

        return clientService.getAllClient();
    }

    public List<Client> searchClients(Client filter) {

        return clientService.retrieveClient(filter);
    }


}
