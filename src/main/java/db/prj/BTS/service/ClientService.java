package db.prj.BTS.service;

import db.prj.BTS.domain.Client;

import db.prj.BTS.repository.ClientRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*This class handle all business related to client service*/
@Service
public class ClientService {

    private static final Logger logger = LogManager.getLogger(ClientService.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TraderService traderService;


    public List<Client> getAllClient() {


        return (List<Client>) clientRepository.findAll();
    }

    /*Create query for making select query on client table based on the input filter.
    * Trader id criteria is added because each trader should only search his/her own clients*/
    public List<Client> retrieveClient(Client filter) {
        logger.debug("Start to search client based on the filter");

        List<Client> clients = clientRepository.findAll(new Specification<Client>() {

            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                // If designation is specified in filter, add equal where clause
//                if (filter.getDesignation() != null) {
//                    predicates.add(cb.equal(root.get("designation"), filter.getDesignation()));
//                }

                // If firstName is specified in filter, add contains (lile)
                // filter to where clause with ignore case
                if (filter.getFirstname() != null && !filter.getFirstname().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("firstname")),
                            "%" + filter.getFirstname().toLowerCase() + "%"));
                }

                // If lastName is specified in filter, add contains (lile)
                // filter to where clause with ignore case
                if (filter.getLastname() != null && !filter.getLastname().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("lastname")),
                            "%" + filter.getLastname().toLowerCase() + "%"));
                }

                if (filter.getPhone_num() != null && !filter.getPhone_num().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("phone_num")),
                            "%" + filter.getPhone_num().toLowerCase() + "%"));
                }

                if (filter.getCellphone_num() != null && !filter.getCellphone_num().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("cellphone_num")),
                            "%" + filter.getCellphone_num().toLowerCase() + "%"));
                }

                if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("email")),
                            "%" + filter.getEmail().toLowerCase() + "%"));
                }

                if (filter.getStreet() != null && !filter.getStreet().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("street")),
                            "%" + filter.getStreet().toLowerCase() + "%"));
                }

                if (filter.getState() != null && !filter.getState().isEmpty()) {
                    predicates.add(cb.equal(root.get("state"), filter.getState()));
                }

                if (filter.getCity() != null && !filter.getCity().isEmpty()) {
                    predicates.add(cb.equal(root.get("city"), filter.getCity()));
                }

                if (filter.getZipcode() != null && !filter.getZipcode().isEmpty()) {
                    predicates.add(cb.equal(root.get("zipcode"), filter.getZipcode()));
                }

                if (filter.getFiat_currency() != null) {
                    predicates.add(cb.equal(root.get("fiat_currency"), filter.getFiat_currency()));
                }

                if (filter.getBitcoin_bal() != null) {
                    predicates.add(cb.equal(root.get("bitcoin_bal"), filter.getBitcoin_bal()));
                }

                if (filter.getLevel() != null) {
                    predicates.add(cb.equal(root.get("level"), filter.getLevel()));
                }

                predicates.add(cb.equal(root.get("trader"), traderService.getTrader()));


                return cb.and(predicates.toArray(new Predicate[0]));
            }
        });
        logger.debug("End to search client based on the filter");
        return clients;
    }


    public Client getClientByUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        Optional<Client> client = clientRepository.findById(userDetail.getUsername());
        return client.get();


    }

    public List<Client> getClientByID(Integer id) {
        List<Client> client = clientRepository.findByClientId(id);
        return client;

    }

    /*return client who is currently login*/
    public Client getClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        Optional<Client> client = clientRepository.findById(userDetail.getUsername());
        return client.get();
    }


}
