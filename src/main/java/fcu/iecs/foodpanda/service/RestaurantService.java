package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Restaurant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  @Autowired
  DatabaseService dbService;
  public List<Restaurant> getAllRestaurant(){
    List<Restaurant> restaurants = new ArrayList<>();
    //實作從db取得所有餐廳資料的方法
    String sql = "SELECT * FROM restaurant";
    try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
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
    }
      catch(SQLException exception) {
        exception.printStackTrace();
      }
      return restaurants;
      }
  }



