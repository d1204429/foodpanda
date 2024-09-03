package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Meal;
import fcu.iecs.foodpanda.service.MealService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/meals")
public class MealController {
  @Autowired
  MealService mealService;

  //實作取得所有菜單資料
  @GetMapping("")
  public ResponseEntity<List<Meal>> getAllMeal(){
    return mealService.getAllMeal();
  }

  //實作搜尋菜單的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Meal> getMealByMealId(@PathVariable String id){
    return mealService.getMealByMealId(id);
  }

  //實作搜尋菜單名子的辦法%name%
  @GetMapping("/name/{keyword}")
  public ResponseEntity<List<Meal>> searchMeals(@PathVariable String keyword){
    return mealService.getMealsByMealName(keyword);
  }

  //實作新增菜單
  @PostMapping("")
  public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
    return mealService.addMeal(meal);
  }

  //實作修改菜單資料
  @PutMapping("/{id}")
  public ResponseEntity<Meal> updateMeal(@PathVariable String id, @RequestBody Meal meal) {
    if (!id.equals(meal.getMeal_id())) {
      return ResponseEntity.badRequest().build();
    }
    return mealService.updateMeal(meal);
  }

  //實作刪除菜單
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMeal(@PathVariable String id) {
    return mealService.deleteMeal(id);
  }



}
