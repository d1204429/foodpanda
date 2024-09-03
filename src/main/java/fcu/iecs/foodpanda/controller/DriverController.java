package fcu.iecs.foodpanda.controller;

import fcu.iecs.foodpanda.model.Driver;
import fcu.iecs.foodpanda.service.DriverService;
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
@RequestMapping("/api/drivers")
public class DriverController {
  @Autowired
  DriverService driverService;

  //實作取得所有駕駛資料
  @GetMapping("")
  public ResponseEntity<List<Driver>> getAllDriver(){
    return driverService.getAllDriver();
  }

  //實作搜尋駕駛的方法id
  @GetMapping("/{id}")
  public ResponseEntity<Driver> getDriverByDriverId(@PathVariable String id){
    return driverService.getDriverByDriverId(id);
  }

  //實作搜尋駕駛名子的辦法%name%
  @GetMapping("/name/{keyword}")
  public ResponseEntity<List<Driver>> searchDrivers(@PathVariable String keyword){
    return driverService.getDriversByDriverName(keyword);
  }

  //實作新增駕駛
  @PostMapping("")
  public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
    return driverService.addDriver(driver);
  }

  //實作修改駕駛資料
  @PutMapping("/{id}")
  public ResponseEntity<Driver> updateDriver(@PathVariable String id, @RequestBody Driver driver) {
    if (!id.equals(driver.getDriver_id())) {
      return ResponseEntity.badRequest().build();
    }
    return driverService.updateDriver(driver);
  }

  //實作刪除駕駛
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDriver(@PathVariable String id) {
    return driverService.deleteDriver(id);
  }
}
