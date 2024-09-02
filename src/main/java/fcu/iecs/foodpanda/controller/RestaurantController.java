package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Restaurant;
import fcu.iecs.foodpanda.service.RestaurantService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
