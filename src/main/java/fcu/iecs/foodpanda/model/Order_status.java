package fcu.iecs.foodpanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order_status {
  private String order_status_id;
  private String order_status;
}
