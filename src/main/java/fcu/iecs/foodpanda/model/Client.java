package fcu.iecs.foodpanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
  private String client_id;
  private String name;
  private String tel;
  private String email;
  private String address;
}
