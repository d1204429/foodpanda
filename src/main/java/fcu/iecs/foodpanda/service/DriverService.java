package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

  @Autowired
  DatabaseService dbService;

  //使用ResponseEntity的方式回應
  //實作從db取得所有餐廳資料的方法
  public ResponseEntity<List<Driver>> getAllDriver() {
    List<Driver> drivers = new ArrayList<>();
    String sql = "SELECT * FROM driver";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        Driver driver = new Driver();
        driver.setDriver_id(rs.getString("driver_id"));
        driver.setName(rs.getString("name"));
        driver.setTel(rs.getString("tel"));
        drivers.add(driver);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(drivers, HttpStatus.OK);
  }

  //實作搜尋餐廳的方法id
  public ResponseEntity<Driver> getDriverByDriverId(String driver_id) {
    Driver driver = new Driver();
    String sql = "SELECT * FROM driver WHERE driver_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, driver_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          driver.setDriver_id(rs.getString("driver_id"));
          driver.setName(rs.getString("name"));
          driver.setTel(rs.getString("tel"));
        }else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(driver, HttpStatus.OK);
  }

  //實作搜尋餐廳名子的辦法%name%
  public ResponseEntity<List<Driver>> getDriversByDriverName(String driverName) {
    List<Driver> drivers = new ArrayList<>();
    String sql = "SELECT * FROM driver WHERE name LIKE ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + driverName + "%");
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          Driver driver = new Driver();
          driver.setDriver_id(rs.getString("driver_id"));
          driver.setName(rs.getString("name"));
          driver.setTel(rs.getString("tel"));
          drivers.add(driver);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(drivers, HttpStatus.OK);
  }

  //實作新增駕駛到db的方法
  public ResponseEntity<Driver> addDriver(Driver driver) {
    String sql = "INSERT INTO driver (driver_id, name, tel) VALUES (?, ?, ?)";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, driver.getDriver_id()); // 手動設定的 ID
      pstmt.setString(2, driver.getName());
      pstmt.setString(3, driver.getTel());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(driver, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改駕駛資料
  public ResponseEntity<Driver> updateDriver(Driver driver) {
    String sql = "UPDATE driver SET name = ?, tel = ? WHERE driver_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, driver.getName());
      pstmt.setString(2, driver.getTel());
      pstmt.setString(3, driver.getDriver_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(driver, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除駕駛
  public ResponseEntity<Void> deleteDriver(String driverId) {
    String sql = "DELETE FROM driver WHERE driver_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, driverId);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
