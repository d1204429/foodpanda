package fcu.iecs.foodpanda.service;

import static java.sql.DriverManager.getConnection;

import fcu.iecs.foodpanda.model.Restaurant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  @Autowired
  DatabaseService dbService;

  //實作從db取得所有餐廳資料的方法
  public List<Restaurant> getAllRestaurant() {
    List<Restaurant> restaurants = new ArrayList<>();
    String sql = "SELECT * FROM restaurant";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(
        sql); ResultSet rs = pstmt.executeQuery()) {
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
    }
    return restaurants;
  }

  //實作搜尋餐廳的方法id
  public Restaurant getRestaurantByRestaurantId(String restaurant_id) {
    Restaurant restaurant = new Restaurant();
    String sql = "SELECT * FROM restaurant WHERE restaurant_id = ?";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return restaurant;
  }

  //實作搜尋餐廳的辦法%name%
  public List<Restaurant> getRestaurantsByRestaurantName(String restaurantname){
    List<Restaurant> restaurants = new ArrayList<>();
    String sql = "SELECT * FROM restaurant WHERE restaurant_name like ?";
    try(Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
      pstmt.setString(1, "%" + restaurantname + "%");
      try(ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()){
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
    }catch (SQLException exception){
      exception.printStackTrace();
    }
    return restaurants;
  }


  //實作新增餐廳的到db的方法
  public Restaurant addRestaurant(Restaurant restaurant) {
    String sql = "INSERT INTO restaurant ( restaurant_id, restaurant_name, tel, address, operation_start, operation_end, operation_status_id ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql,
        Statement.RETURN_GENERATED_KEYS)) {

      pstmt.setString(1, restaurant.getRestaurant_id());
      pstmt.setString(2, restaurant.getRestaurant_name());
      pstmt.setString(3, restaurant.getTel());
      pstmt.setString(4, restaurant.getAddress());
      pstmt.setTime(5, restaurant.getOperation_start());
      pstmt.setTime(6, restaurant.getOperation_end());
      pstmt.setString(7, restaurant.getOperation_status_id());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating restaurant failed, no rows affected.");
      }

      // 獲取生成的 restaurant ID
      try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          restaurant.setRestaurant_id(generatedKeys.getString(1)); // 假設 restaurant_id 是字符串類型
        } else {
          throw new SQLException("Creating restaurant failed, no ID obtained.");
        }
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return restaurant;
  }

  //實做更新db餐廳資料的方法
  public Restaurant updateRestaurant(Restaurant restaurant) {
    String sql = "UPDATE D1204429_foodpanda.restaurant SET restaurant_name = ?, tel = ?, address = ?, " +
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
        throw new SQLException("更新餐廳失敗，沒有找到匹配的記錄。");
      }

      return restaurant;
    } catch (SQLException e) {
      // 在實際應用中，你可能想要記錄錯誤或拋出自定義異常
      e.printStackTrace();
      return null;
    }
  }

  //實作刪除餐廳資料的方法
  public void deleteRestaurant(String restaurant_id) {
    String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, restaurant_id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}





