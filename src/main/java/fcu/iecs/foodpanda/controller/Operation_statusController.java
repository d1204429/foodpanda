package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Operation_status;
import fcu.iecs.foodpanda.service.Operation_statusService;
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
@RequestMapping("/api/operation_status")
public class Operation_statusController {
  @Autowired
  Operation_statusService operation_statusService;

  //實作取得所有營業狀態資料
  @GetMapping("")
  public ResponseEntity<List<Operation_status>> getAllOperation_status(){
    return operation_statusService.getAllOperation_status();
  }

  //實作搜尋營業狀態的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Operation_status> getOperation_statusByOperation_statusId(@PathVariable String id){
    return operation_statusService.getOperation_statusByOperation_statusId(id);
  }

  //實作新增營業狀態
  @PostMapping("")
  public ResponseEntity<Operation_status> addOperation_status(@RequestBody Operation_status operation_status) {
    return operation_statusService.addOperation_status(operation_status);
  }

  //實作搜尋營業狀態名子的辦法%name%
  @GetMapping("/name/{keyword}")
  public ResponseEntity<List<Operation_status>> searchOperation_status(@PathVariable String keyword){
    return operation_statusService.getOperation_statusByOperation_statusName(keyword);
  }

  //實作修改營業狀態資料
  @PutMapping("/{id}")
  public ResponseEntity<Operation_status> updateOperation_status(@PathVariable String id, @RequestBody Operation_status operation_status) {
    if (!id.equals(operation_status.getOperation_status_id())) {
      return ResponseEntity.badRequest().build();
    }
    return operation_statusService.updateOperation_status(operation_status);
  }

  //實作刪除營業狀態
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOperation_status(@PathVariable String id) {
    return operation_statusService.deleteOperation_status(id);
  }

}
