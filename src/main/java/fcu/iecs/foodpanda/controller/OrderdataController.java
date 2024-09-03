package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.service.OrderdataService;
import fcu.iecs.foodpanda.model.Orderdata;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orderdata")
public class OrderdataController {
  @Autowired
  OrderdataService orderdataService;
  //實作取得所有訂單資料
  @GetMapping("")
  public ResponseEntity<List<Orderdata>> getAllOrder(){
    return orderdataService.getAllOrder();
  }

  //實作搜尋訂單的方法order_id
  @GetMapping("/order/{id}")
  public ResponseEntity<Orderdata> getOrderByOrderId(@PathVariable String id){
    return orderdataService.getOrderByOrderId(id);
  }

  //實作搜尋訂單的方法client_id
  @GetMapping("/client/{id}")
  public ResponseEntity<List<Orderdata>> getOrderByClientId(@PathVariable String id){
    return orderdataService.getOrderByClientId(id);
  }

  //實作搜尋訂單的方法restaurant_id
  @GetMapping("/restaurant/{id}")
  public ResponseEntity<List<Orderdata>> getOrderByRestaurantId(@PathVariable String id){
    return orderdataService.getOrderByRestaurantId(id);
  }

  //實作新增訂單
  @PostMapping("")
  public ResponseEntity<Orderdata> addOrder(@RequestBody Orderdata order) {
    return orderdataService.addOrder(order);
  }

  //實作修改訂單資料
  @PostMapping("/{id}")
  public ResponseEntity<Orderdata> updateOrder(@RequestBody Orderdata order) {
    return orderdataService.updateOrder(order);
  }

  //實作刪除訂單
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
    return orderdataService.deleteOrder(id);
  }





}
