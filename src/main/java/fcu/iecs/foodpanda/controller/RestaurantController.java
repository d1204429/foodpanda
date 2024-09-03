package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Restaurant;
import fcu.iecs.foodpanda.service.RestaurantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

  @Autowired
  RestaurantService restaurantService;

  //實作取得所有餐廳資料
  @GetMapping("")
  public List<Restaurant>getAllRestaurant(){
    return restaurantService.getAllRestaurant();
  }

  //實作搜尋餐廳名子的辦法%name
  @GetMapping("/name/{keyword}")
  public List<Restaurant> searchRestaurants(@PathVariable String keyword){
    return restaurantService.getRestaurantsByRestaurantName(keyword);
  }

  //實作新增餐廳
  @PostMapping("")
  public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
    Restaurant addedRestaurant = restaurantService.addRestaurant(restaurant);
    return new ResponseEntity<>(addedRestaurant, HttpStatus.CREATED);
  }
  //實作修改餐廳資料
  @PutMapping("/{id}")
  public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant) {
    try {
      // 確保路徑變量 ID 與請求體中的 restaurant_id 匹配
      if (!id.equals(restaurant.getRestaurant_id())) {
        return ResponseEntity.badRequest().body(null);
      }

      Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant);
      if (updatedRestaurant != null) {
        return ResponseEntity.ok(updatedRestaurant);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      // 記錄錯誤
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  //實作刪除餐廳
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
    try {
      restaurantService.deleteRestaurant(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      // 記錄錯誤
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
