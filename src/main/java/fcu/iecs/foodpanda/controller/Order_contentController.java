package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Operation_status;
import fcu.iecs.foodpanda.model.Order_content;
import fcu.iecs.foodpanda.service.Order_contentService;
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
@RequestMapping("/api/order_contents")
public class Order_contentController {
  @Autowired
  Order_contentService order_contentService;
  //實作取得所有訂單內容
  @GetMapping("")
  public ResponseEntity<List<Order_content>> getAllOrder_content(){
    return order_contentService.getAllOrder_content();
  }

  //實作搜尋訂單內容的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Order_content> getOrder_contentByOrder_contentId(@PathVariable String id){
    return order_contentService.getOrder_contentByOrder_contentId(id);
  }

  //實作新增訂單內容
  @PostMapping("")
  public ResponseEntity<Order_content> addOrder_content(@RequestBody Order_content order_content) {
    return order_contentService.addOrder_content(order_content);
  }

  //實作修改訂單內容
  @PutMapping("/{id}")
  public ResponseEntity<Order_content> updateOrder_content(@PathVariable String id,@RequestBody Order_content order_content) {
    if (!id.equals(order_content.getOrder_content_id())) {
      return ResponseEntity.badRequest().build();
    }
    return order_contentService.updateOrder_content(order_content);
  }

  //實作刪除訂單內容
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder_content(@PathVariable String id) {
    return order_contentService.deleteOrder_content(id);
  }




}
