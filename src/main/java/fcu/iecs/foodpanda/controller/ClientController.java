package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Client;
import fcu.iecs.foodpanda.service.ClientService;
import java.util.List;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

  @Autowired
  ClientService clientService;

  //實作取得所有客戶資料
  @GetMapping("")
  public ResponseEntity<List<Client>> getAllClient(){
    return clientService.getAllClint();
  }

  //實作搜尋客戶的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Client> getClientByClientId(@PathVariable String id){
    return clientService.getClientByClientId(id);
  }

  //實作搜尋客戶名子的辦法%name%
  @GetMapping("/name/{keyword}")
  public ResponseEntity<List<Client>> serachClients(@PathVariable String keyword){
    return clientService.getClientsByClientName(keyword);
  }

  //實作新增客戶
  @PostMapping("")
  public ResponseEntity<Client> addClient(@RequestBody Client client){
    return clientService.addClient(client);
  }

  //實作修改客戶資料
  @PostMapping("/{id}")
  public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client client){
    if (!id.equals(client.getClient_id())) {
      return ResponseEntity.badRequest().build();
    }
    return clientService.updateClient(client);
  }

  //實作刪除客戶
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable String id){
    return clientService.deleteClient(id);
  }

}
