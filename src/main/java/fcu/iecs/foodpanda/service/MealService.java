package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Meal;
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
public class MealService {
  @Autowired
  DatabaseService dbService;
  //使用ResponseEntity的方式回應
  //實作從db取得所有菜單資料的方法
  public ResponseEntity<List<Meal>> getAllMeal() {
    List<Meal> meals = new ArrayList<>();
    String sql = "SELECT * FROM meal";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        Meal meal = new Meal();
        meal.setMeal_id(rs.getString("meal_id"));
        meal.setMeal_name(rs.getString("meal_name"));
        meal.setUnit_price(rs.getInt("unit_price"));
        meal.setRestaurant_id(rs.getString("restaurant_id"));
        meals.add(meal);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(meals, HttpStatus.OK);
  }

  //實作搜尋菜單的方法id
  public ResponseEntity<Meal> getMealByMealId(String meal_id) {
    Meal meal = new Meal();
    String sql = "SELECT * FROM meal WHERE meal_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, meal_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          meal.setMeal_id(rs.getString("meal_id"));
          meal.setMeal_name(rs.getString("meal_name"));
          meal.setUnit_price(rs.getInt("unit_price"));
          meal.setRestaurant_id(rs.getString("restaurant_id"));
        }else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(meal, HttpStatus.OK);
  }

  //實作搜尋菜單的方法name
  public ResponseEntity<List<Meal>> getMealsByMealName(String mealName) {
    List<Meal> meals = new ArrayList<>();
    String sql = "SELECT * FROM meal WHERE meal_name LIKE ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + mealName + "%");
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          Meal meal = new Meal();
          meal.setMeal_id(rs.getString("meal_id"));
          meal.setMeal_name(rs.getString("meal_name"));
          meal.setUnit_price(rs.getInt("unit_price"));
          meal.setRestaurant_id(rs.getString("restaurant_id"));
          meals.add(meal);
        }
      }
      if (meals.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(meals, HttpStatus.OK);
  }

  //實作新增菜單
  public ResponseEntity<Meal> addMeal(Meal meal) {
    String sql = "INSERT INTO meal (meal_id, meal_name, unit_price, restaurant_id) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, meal.getMeal_id()); // 手動設定的 ID
      pstmt.setString(2, meal.getMeal_name());
      pstmt.setInt(3, meal.getUnit_price());
      pstmt.setString(4, meal.getRestaurant_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(meal, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改菜單資料
  public ResponseEntity<Meal> updateMeal(Meal meal) {
    String sql = "UPDATE meal SET meal_name = ?, unit_price = ?, restaurant_id = ? WHERE meal_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, meal.getMeal_name());
      pstmt.setInt(2, meal.getUnit_price());
      pstmt.setString(3, meal.getRestaurant_id());
      pstmt.setString(4, meal.getMeal_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(meal, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除菜單
  public ResponseEntity<Void> deleteMeal(String meal_id) {
    String sql = "DELETE FROM meal WHERE meal_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, meal_id);
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
