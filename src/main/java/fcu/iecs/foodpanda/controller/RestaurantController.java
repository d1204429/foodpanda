package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Restaurant;
import fcu.iecs.foodpanda.service.RestaurantService;
import java.util.List;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("")
  public List<Restaurant>getAllRestaurant(){
    return restaurantService.getAllRestaurant();
  }

  @PostMapping("")
  public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
    Restaurant addedRestaurant = restaurantService.addRestaurant(restaurant);
    return new ResponseEntity<>(addedRestaurant, HttpStatus.CREATED);
  }

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

}
