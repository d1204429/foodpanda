package fcu.iecs.foodpanda.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private String order_id;
  private String client_id;
  private String restaurant_id;
  private Date order_time;
  private int delivery_fee;
  private String driver_id;
  private String order_status_id;
}
