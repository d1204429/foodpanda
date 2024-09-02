package fcu.iecs.foodpanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
  private String meal_id;
  private String meal_name;
  private int unit_price;
  private String restaurant_id;
}
