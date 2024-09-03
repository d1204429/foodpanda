package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  DatabaseService dbService;

  public List<Client> getAllClint(){
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM client";
    try(Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()){
      while (rs.next()){
        Client client = new Client();
        client.setClient_id(rs.getString("client_id"));
        client.setName(rs.getString("name"));
        client.setTel(rs.getString("tel"));
        client.setEmail(rs.getString("email"));
        client.setAddress(rs.getString("address"));
        clients.add(client);
      }

    }catch (SQLException exception){
      exception.printStackTrace();
    }
    return clients;
  }

  public List<Client> getClientsByClientName(String clientname){
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM client WHERE name like ?";
    try(Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
      pstmt.setString(1,"%"+clientname+"%");{
        try(ResultSet rs = pstmt.executeQuery()){
          while (rs.next()){
            Client client = new Client();
            client.setClient_id(rs.getString("client_id"));
            client.setName(rs.getString("name"));
            client.setTel(rs.getString("tel"));
            client.setEmail(rs.getString("email"));
            client.setAddress(rs.getString("address"));
            clients.add(client);
          }
        }
      }
    }catch (SQLException exception){
      exception.printStackTrace();
    }
    return clients;
  }
}
