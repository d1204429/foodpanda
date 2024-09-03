package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Restaurant;
import fcu.iecs.foodpanda.service.RestaurantService;
import java.sql.SQLException;
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

import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:5173")  // 允許特定來源的跨域請求

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

  @Autowired
  RestaurantService restaurantService;

  //實作取得所有餐廳資料
  @GetMapping("")
  public ResponseEntity<List<Restaurant>>getAllRestaurant(){
    return restaurantService.getAllRestaurant();
  }

  //實作搜尋餐廳的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> getRestaurantByRestaurantId(@PathVariable String id){
    return restaurantService.getRestaurantByRestaurantId(id);
  }

  //實作搜尋餐廳名子的辦法%name%
  @GetMapping("/name/{keyword}")
  public ResponseEntity<List<Restaurant>> searchRestaurants(@PathVariable String keyword){
    return restaurantService.getRestaurantsByRestaurantName(keyword);
  }

  //實作新增餐廳
  @PostMapping("")
  public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
    return restaurantService.addRestaurant(restaurant);
  }

  //實作修改餐廳資料
  @PutMapping("/{id}")
  public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant) {
    if (!id.equals(restaurant.getRestaurant_id())) {
      return ResponseEntity.badRequest().build();
    }
    return restaurantService.updateRestaurant(restaurant);
  }

  //實作刪除餐廳
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
    return restaurantService.deleteRestaurant(id);
  }

}
