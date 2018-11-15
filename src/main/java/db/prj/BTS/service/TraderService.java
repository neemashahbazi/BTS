package db.prj.BTS.service;

import db.prj.BTS.domain.Client;
import db.prj.BTS.domain.Trader;
import db.prj.BTS.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraderService {

    @Autowired
    TraderRepository traderRepository;

    public List<Client> getClientList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        Optional<Trader> trader = traderRepository.findById(userDetail.getUsername());
        return trader.get().getClientList();

    }

    public Trader getTrader() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        Optional<Trader> trader = traderRepository.findById(userDetail.getUsername());
        return trader.get();

    }

}
