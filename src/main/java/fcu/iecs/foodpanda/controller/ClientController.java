package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Client;
import fcu.iecs.foodpanda.service.ClientService;
import java.util.List;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

  @Autowired
  ClientService clientService;

  //實作取得所有客戶資料
  @GetMapping("")
  public List<Client> getAllClient(){
    return clientService.getAllClint();
  }
}
