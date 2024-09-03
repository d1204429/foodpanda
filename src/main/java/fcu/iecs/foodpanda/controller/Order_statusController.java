package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Order_status;
import fcu.iecs.foodpanda.service.Order_statusService;
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
@RequestMapping("/api/order_status")
public class Order_statusController {
  @Autowired
  Order_statusService order_statusService;

  //實作取得所有訂單狀態資料
  @GetMapping("")
  public ResponseEntity<List<Order_status>> getAllOrder_status(){
    return order_statusService.getAllOrder_status();
  }

  //實作搜尋訂單狀態的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Order_status> getOrder_statusByOrder_statusId(@PathVariable String id){
    return order_statusService.getOrder_statusByOrder_statusId(id);
  }

  //實作搜尋訂單狀態名子的辦法%name%
  @GetMapping("/name/{keyword}")
  public ResponseEntity<List<Order_status>> searchOrder_status(@PathVariable String keyword){
    return order_statusService.getOrderStatusesByName(keyword);
  }

  //實作新增訂單狀態
  @PostMapping("")
  public ResponseEntity<Order_status> addOrder_status(@RequestBody Order_status order_status) {
    return order_statusService.addOrder_status(order_status);
  }

  //實作修改訂單狀態資料
  @PutMapping("/{id}")
  public ResponseEntity<Order_status> updateOrder_status(@PathVariable String id, @RequestBody Order_status order_status) {
    if (!id.equals(order_status.getOrder_status_id())) {
      return ResponseEntity.badRequest().build();
    }
    return order_statusService.updateOrder_status(order_status);
  }

  //實作刪除訂單狀態
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder_status(@PathVariable String id) {
    return order_statusService.deleteOrder_status(id);
  }
}
