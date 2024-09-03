package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Client;
import fcu.iecs.foodpanda.model.Restaurant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  DatabaseService dbService;

  //實作從db取得所有客戶資料的方法
  public ResponseEntity<List<Client>> getAllClint() {
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM client";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(
        sql); ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        Client client = new Client();
        client.setClient_id(rs.getString("client_id"));
        client.setName(rs.getString("name"));
        client.setTel(rs.getString("tel"));
        client.setEmail(rs.getString("email"));
        client.setAddress(rs.getString("address"));
        clients.add(client);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(clients, HttpStatus.OK);
  }

  //實作搜尋客戶的方法id
  public ResponseEntity<Client> getClientByClientId(String client_id) {
    Client client = new Client();
    String sql = "SELECT * FROM client WHERE client_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, client_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          client.setClient_id(rs.getString("client_id"));
          client.setName(rs.getString("name"));
          client.setTel(rs.getString("tel"));
          client.setEmail(rs.getString("email"));
          client.setAddress(rs.getString("address"));
          return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  //實作搜尋客戶名子的辦法%name%
  public ResponseEntity<List<Client>> getClientsByClientName(String clientName) {
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT * FROM client WHERE name LIKE ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, "%" + clientName + "%");

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          Client client = new Client();
          client.setClient_id(rs.getString("client_id"));
          client.setName(rs.getString("name"));
          client.setTel(rs.getString("tel"));
          client.setEmail(rs.getString("email"));
          client.setAddress(rs.getString("address"));
          clients.add(client);
        }
      }

      // 檢查是否有結果
      if (clients.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(clients, HttpStatus.OK);
  }

  //實作新增客戶
  public ResponseEntity<Client> addClient(Client client) {
    String sql = "INSERT INTO client (client_id, name, tel, email, address) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(
        sql)) {
      pstmt.setString(1, client.getClient_id());
      pstmt.setString(2, client.getName());
      pstmt.setString(3, client.getTel());
      pstmt.setString(4, client.getEmail());
      pstmt.setString(5, client.getAddress());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // 成功新增餐廳後返回
      return new ResponseEntity<>(client, HttpStatus.CREATED);
    }  catch (SQLException exception) {
      exception.printStackTrace();
      // 其他 SQL 錯誤，返回 500 Internal Server Error
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改客戶資料
  public ResponseEntity<Client> updateClient(Client client) {
    String sql = "UPDATE client SET name = ?, tel = ?, email = ?, address = ? WHERE client_id = ?";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(
        sql)) {
      pstmt.setString(1, client.getName());
      pstmt.setString(2, client.getTel());
      pstmt.setString(3, client.getEmail());
      pstmt.setString(4, client.getAddress());
      pstmt.setString(5, client.getClient_id());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      // 成功修改客戶後返回
      return new ResponseEntity<>(client, HttpStatus.OK);

    } catch (SQLException exception) {
      exception.printStackTrace();
      // 其他 SQL 錯誤，返回 500 Internal Server Error
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除客戶
  public ResponseEntity<Void> deleteClient(String clientId) {
    String sql = "DELETE FROM client WHERE client_id = ?";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(
        sql)) {
      pstmt.setString(1, clientId);

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      // 成功刪除客戶後返回
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (SQLException exception) {
      exception.printStackTrace();
      // 其他 SQL 錯誤，返回 500 Internal Server Error
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}

