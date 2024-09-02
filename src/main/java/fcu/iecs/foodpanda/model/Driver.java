package fcu.iecs.foodpanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
  private String driver_id;
  private String name;
  private String tel;

}
