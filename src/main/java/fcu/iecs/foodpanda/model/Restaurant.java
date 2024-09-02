package fcu.iecs.foodpanda.model;

import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

  private String restaurant_id;
  private String restaurant_name;
  private String tel;
  private String address;
  private Time operation_start;
  private Time operation_end;
  private String operation_status_id;
}
