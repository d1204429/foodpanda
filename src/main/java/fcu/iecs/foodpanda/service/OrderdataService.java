package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Orderdata;
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
public class OrderdataService {

  @Autowired
  DatabaseService dbService;

  //使用ResponseEntity的方式回應
  //實作從db取得所有訂單資料的方法
  public ResponseEntity<List<Orderdata>> getAllOrder() {
    List<Orderdata> orders = new ArrayList<>();
    String sql = "SELECT * FROM orderdata";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        Orderdata order = new Orderdata();
        order.setOrder_id(rs.getString("order_id"));
        order.setClient_id(rs.getString("client_id"));
        order.setRestaurant_id(rs.getString("restaurant_id"));
        order.setOrder_time(rs.getDate("order_time"));
        order.setDelivery_fee(rs.getInt("delivery_fee"));
        order.setDriver_id(rs.getString("driver_id"));
        order.setOrder_status_id(rs.getString("order_status_id"));
        orders.add(order);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  //實作搜尋餐廳的方法order_id
  public ResponseEntity<Orderdata> getOrderByOrderId(String order_id) {
    Orderdata order = new Orderdata();
    String sql = "SELECT * FROM orderdata WHERE order_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          order.setOrder_id(rs.getString("order_id"));
          order.setClient_id(rs.getString("client_id"));
          order.setRestaurant_id(rs.getString("restaurant_id"));
          order.setOrder_time(rs.getDate("order_time"));
          order.setDelivery_fee(rs.getInt("delivery_fee"));
          order.setDriver_id(rs.getString("driver_id"));
          order.setOrder_status_id(rs.getString("order_status_id"));
        }else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  //實作搜尋餐廳的方法client_id
  public ResponseEntity<List<Orderdata>> getOrderByClientId(String client_id) {
    List<Orderdata> orders = new ArrayList<>();
    String sql = "SELECT * FROM orderdata WHERE client_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, client_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          Orderdata order = new Orderdata();
          order.setOrder_id(rs.getString("order_id"));
          order.setClient_id(rs.getString("client_id"));
          order.setRestaurant_id(rs.getString("restaurant_id"));
          order.setOrder_time(rs.getDate("order_time"));
          order.setDelivery_fee(rs.getInt("delivery_fee"));
          order.setDriver_id(rs.getString("driver_id"));
          order.setOrder_status_id(rs.getString("order_status_id"));
          orders.add(order);
        }else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    }
    catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  //實作搜尋餐廳的方法restaurant_id
  public ResponseEntity<List<Orderdata>> getOrderByRestaurantId(String restaurant_id) {
    List<Orderdata> orders = new ArrayList<>();
    String sql = "SELECT * FROM orderdata WHERE restaurant_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, restaurant_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          Orderdata order = new Orderdata();
          order.setOrder_id(rs.getString("order_id"));
          order.setClient_id(rs.getString("client_id"));
          order.setRestaurant_id(rs.getString("restaurant_id"));
          order.setOrder_time(rs.getDate("order_time"));
          order.setDelivery_fee(rs.getInt("delivery_fee"));
          order.setDriver_id(rs.getString("driver_id"));
          order.setOrder_status_id(rs.getString("order_status_id"));
          orders.add(order);
        }else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    }
    catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  //實作新增訂單
  public ResponseEntity<Orderdata> addOrder(Orderdata order) {
    String sql = "INSERT INTO orderdata (order_id, client_id, restaurant_id, order_time, delivery_fee, driver_id, order_status_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order.getOrder_id()); // 手動設定的 ID
      pstmt.setString(2, order.getClient_id());
      pstmt.setString(3, order.getRestaurant_id());
      pstmt.setDate(4, order.getOrder_time());
      pstmt.setInt(5, order.getDelivery_fee());
      pstmt.setString(6, order.getDriver_id());
      pstmt.setString(7, order.getOrder_status_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(order, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改訂單資料
  public ResponseEntity<Orderdata> updateOrder(Orderdata order) {
    String sql = "UPDATE orderdata SET client_id = ?, restaurant_id = ?, order_time = ?, delivery_fee = ?, driver_id = ?, order_status_id = ? WHERE order_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order.getClient_id());
      pstmt.setString(2, order.getRestaurant_id());
      pstmt.setDate(3, order.getOrder_time());
      pstmt.setInt(4, order.getDelivery_fee());
      pstmt.setString(5, order.getDriver_id());
      pstmt.setString(6, order.getOrder_status_id());
      pstmt.setString(7, order.getOrder_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(order, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除訂單
  public ResponseEntity<Void> deleteOrder(String order_id) {
    String sql = "DELETE FROM orderdata WHERE order_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_id);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



}
