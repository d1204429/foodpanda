package fcu.iecs.foodpanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order_content {
  private String order_content_id;
  private String order_id;
  private String meal_id;
  private int quantity;
}
