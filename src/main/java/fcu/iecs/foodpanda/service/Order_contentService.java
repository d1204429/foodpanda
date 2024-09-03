package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Order_content;
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
public class Order_contentService {
  @Autowired
  DatabaseService dbService;

  //實作獲得所有訂單內容
  public ResponseEntity<List<Order_content>> getAllOrder_content() {
    List<Order_content> order_contents = new ArrayList<>();
    String sql = "SELECT * FROM order_content";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        Order_content order_content = new Order_content();
        order_content.setOrder_content_id(rs.getString("order_content_id"));
        order_content.setOrder_id(rs.getString("order_id"));
        order_content.setMeal_id(rs.getString("meal_id"));
        order_content.setQuantity(rs.getInt("quantity"));
        order_contents.add(order_content);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(order_contents, HttpStatus.OK);
  }

  //實作搜尋訂單內容的方法id
  public ResponseEntity<Order_content> getOrder_contentByOrder_contentId(String order_content_id) {
    Order_content order_content = new Order_content();
    String sql = "SELECT * FROM order_content WHERE order_content_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_content_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          order_content.setOrder_content_id(rs.getString("order_content_id"));
          order_content.setOrder_id(rs.getString("order_id"));
          order_content.setMeal_id(rs.getString("meal_id"));
          order_content.setQuantity(rs.getInt("quantity"));
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(order_content, HttpStatus.OK);
  }

  //實作新增訂單內容
  public ResponseEntity<Order_content> addOrder_content(Order_content order_content) {
    String sql = "INSERT INTO order_content (order_content_id, order_id, meal_id, quantity) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_content.getOrder_content_id());
      pstmt.setString(2, order_content.getOrder_id());
      pstmt.setString(3, order_content.getMeal_id());
      pstmt.setInt(4, order_content.getQuantity());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(order_content, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改訂單內容
  public ResponseEntity<Order_content> updateOrder_content(Order_content order_content) {
    String sql = "UPDATE order_content SET order_id = ?, meal_id = ?, quantity = ? WHERE order_content_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, order_content.getOrder_id());
      pstmt.setString(2, order_content.getMeal_id());
      pstmt.setInt(3, order_content.getQuantity());
      pstmt.setString(4, order_content.getOrder_content_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(order_content, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除訂單內容
  public ResponseEntity<Void> deleteOrder_content(String id) {
    String sql = "DELETE FROM order_content WHERE order_content_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, id);
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
