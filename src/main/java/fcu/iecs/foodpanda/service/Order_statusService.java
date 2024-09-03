package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Order_status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Order_statusService {

  @Autowired
  DatabaseService dbService;

  //使用ResponseEntity的方式回應
//實作取得所有訂單狀態資料的方法
  public ResponseEntity<List<Order_status>> getAllOrder_status() {
    List<Order_status> order_statuses = new ArrayList<>();
    String sql = "SELECT * FROM order_status";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        Order_status order_status = new Order_status();
        order_status.setOrder_status_id(rs.getString("order_status_id"));
        order_status.setOrder_status(rs.getString("order_status"));
        order_statuses.add(order_status);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(order_statuses, HttpStatus.OK);
  }

  //實作搜尋訂單狀態的方法id
  public ResponseEntity<Order_status> getOrder_statusByOrder_statusId(String order_status_id) {
    Order_status order_status = new Order_status();
    String sql = "SELECT * FROM order_status WHERE order_status_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_status_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          order_status.setOrder_status_id(rs.getString("order_status_id"));
          order_status.setOrder_status(rs.getString("order_status"));
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(order_status, HttpStatus.OK);
  }

  //實作搜尋訂單狀態的辦法%name%
  public ResponseEntity<List<Order_status>> getOrderStatusesByName(String orderstatusName) {
    List<Order_status> order_statuses = new ArrayList<>();
    String sql = "SELECT * FROM order_status WHERE order_status LIKE ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + orderstatusName + "%");
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          Order_status order_status = new Order_status();
          order_status.setOrder_status_id(rs.getString("order_status_id"));
          order_status.setOrder_status(rs.getString("order_status"));
          order_statuses.add(order_status);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(order_statuses, HttpStatus.OK);
  }

  //實作新增訂單狀態
  public ResponseEntity<Order_status> addOrder_status(Order_status order_status) {
    String sql = "INSERT INTO order_status (order_status_id, order_status) VALUES (?, ?)";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_status.getOrder_status_id());
      pstmt.setString(2, order_status.getOrder_status());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(order_status, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改訂單狀態資料
  public ResponseEntity<Order_status> updateOrder_status(Order_status order_status) {
    String sql = "UPDATE order_status SET order_status = ? WHERE order_status_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_status.getOrder_status());
      pstmt.setString(2, order_status.getOrder_status_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(order_status, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除訂單狀態
  public ResponseEntity<Void> deleteOrder_status(String order_status_id) {
    String sql = "DELETE FROM order_status WHERE order_status_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_status_id);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



}
