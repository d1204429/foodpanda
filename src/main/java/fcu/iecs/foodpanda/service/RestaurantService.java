package fcu.iecs.foodpanda.service;

import static java.sql.DriverManager.getConnection;

import fcu.iecs.foodpanda.model.Restaurant;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RestaurantService {

  @Autowired
  DatabaseService dbService;

  //使用ResponseEntity的方式回應
  //實作從db取得所有餐廳資料的方法
  public ResponseEntity<List<Restaurant>> getAllRestaurant() {
    List<Restaurant> restaurants = new ArrayList<>();
    String sql = "SELECT * FROM restaurant";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_id(rs.getString("restaurant_id"));
        restaurant.setRestaurant_name(rs.getString("restaurant_name"));
        restaurant.setTel(rs.getString("tel"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setOperation_start(rs.getTime("operation_start"));
        restaurant.setOperation_end(rs.getTime("operation_end"));
        restaurant.setOperation_status_id(rs.getString("operation_status_id"));
        restaurants.add(restaurant);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }

  //實作搜尋餐廳的方法id
  public ResponseEntity<Restaurant> getRestaurantByRestaurantId(String restaurant_id) {
    Restaurant restaurant = new Restaurant();
    String sql = "SELECT * FROM restaurant WHERE restaurant_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, restaurant_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          restaurant.setRestaurant_id(rs.getString("restaurant_id"));
          restaurant.setRestaurant_name(rs.getString("restaurant_name"));
          restaurant.setTel(rs.getString("tel"));
          restaurant.setAddress(rs.getString("address"));
          restaurant.setOperation_start(rs.getTime("operation_start"));
          restaurant.setOperation_end(rs.getTime("operation_end"));
          restaurant.setOperation_status_id(rs.getString("operation_status_id"));
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }

  //實作搜尋餐廳的辦法%name%
  public ResponseEntity<List<Restaurant>> getRestaurantsByRestaurantName(String restaurantName) {
    List<Restaurant> restaurants = new ArrayList<>();
    String sql = "SELECT * FROM restaurant WHERE restaurant_name LIKE ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, "%" + restaurantName + "%");
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          // 每次循環新建 Restaurant 物件
          Restaurant restaurant = new Restaurant();
          restaurant.setRestaurant_id(rs.getString("restaurant_id"));
          restaurant.setRestaurant_name(rs.getString("restaurant_name"));
          restaurant.setTel(rs.getString("tel"));
          restaurant.setAddress(rs.getString("address"));
          restaurant.setOperation_start(rs.getTime("operation_start"));
          restaurant.setOperation_end(rs.getTime("operation_end"));
          restaurant.setOperation_status_id(rs.getString("operation_status_id"));
          restaurants.add(restaurant);
        }
      }

      // 如果沒有找到任何餐廳
      if (restaurants.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }

  //實作新增餐廳到db的方法
  public ResponseEntity<Restaurant> addRestaurant(Restaurant restaurant) {
    String sql = "INSERT INTO restaurant (restaurant_id, restaurant_name, tel, address, operation_start, operation_end, operation_status_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, restaurant.getRestaurant_id()); // 手動設定的 ID
      pstmt.setString(2, restaurant.getRestaurant_name());
      pstmt.setString(3, restaurant.getTel());
      pstmt.setString(4, restaurant.getAddress());
      pstmt.setTime(5, restaurant.getOperation_start());
      pstmt.setTime(6, restaurant.getOperation_end());
      pstmt.setString(7, restaurant.getOperation_status_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      // 成功新增餐廳後返回
      return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      // 其他 SQL 錯誤，返回 500 Internal Server Error
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改餐廳資料的方法
  public ResponseEntity<Restaurant> updateRestaurant(Restaurant restaurant) {
    String sql = "UPDATE restaurant SET restaurant_name = ?, tel = ?, address = ?, " +
        "operation_start = ?, operation_end = ?, operation_status_id = ? " +
        "WHERE restaurant_id = ?";

    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, restaurant.getRestaurant_name());
      pstmt.setString(2, restaurant.getTel());
      pstmt.setString(3, restaurant.getAddress());
      pstmt.setTime(4, restaurant.getOperation_start());
      pstmt.setTime(5, restaurant.getOperation_end());
      pstmt.setString(6, restaurant.getOperation_status_id());
      pstmt.setString(7, restaurant.getRestaurant_id());

      int affectedRows = pstmt.executeUpdate();

      if (affectedRows == 0) {
        // 返回 404 Not Found，表示沒有找到要更新的記錄
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      // 返回 200 OK 和更新後的餐廳對象
      return new ResponseEntity<>(restaurant, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      // 返回 500 Internal Server Error，表示內部伺服器錯誤
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除餐廳的方法
  public ResponseEntity<Void> deleteRestaurant(String restaurantId) {
    String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, restaurantId);
      int affectedRows = pstmt.executeUpdate();

      if (affectedRows == 0) {
        // 返回 404 Not Found，表示沒有找到要刪除的記錄
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      // 返回 204 No Content，表示成功刪除記錄
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (SQLException exception) {
      exception.printStackTrace();
      // 返回 500 Internal Server Error，表示內部伺服器錯誤
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}





