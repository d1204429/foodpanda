package fcu.iecs.foodpanda.service;

import fcu.iecs.foodpanda.model.Operation_status;
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
public class Operation_statusService {

  @Autowired
  DatabaseService dbService;

//使用ResponseEntity的方式回應
//實作從db取得所有營業狀態的方法
  public ResponseEntity<List<Operation_status>> getAllOperation_status() {
    List<Operation_status> operation_statuses = new ArrayList<>();
    String sql = "SELECT * FROM operation_status";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        Operation_status operation_status = new Operation_status();
        operation_status.setOperation_status_id(rs.getString("operation_status_id"));
        operation_status.setOperation_status(rs.getString("operation_status"));
        operation_statuses.add(operation_status);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(operation_statuses, HttpStatus.OK);
  }

  //實作搜尋營業狀態的方法id
  public ResponseEntity<Operation_status> getOperation_statusByOperation_statusId(String operation_status_id) {
    Operation_status operation_status = new Operation_status();
    String sql = "SELECT * FROM operation_status WHERE operation_status_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, operation_status_id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          operation_status.setOperation_status_id(rs.getString("operation_status_id"));
          operation_status.setOperation_status(rs.getString("operation_status"));
        }else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(operation_status, HttpStatus.OK);
  }

  //實作搜尋營業狀態的方法name
  public ResponseEntity<List<Operation_status>> getOperation_statusByOperation_statusName(String operation_statusName) {
    List<Operation_status> operation_statuses = new ArrayList<>();
    String sql = "SELECT * FROM operation_status WHERE operation_status LIKE ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + operation_statusName + "%");
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          Operation_status operation_status = new Operation_status();
          operation_status.setOperation_status_id(rs.getString("operation_status_id"));
          operation_status.setOperation_status(rs.getString("operation_status"));
          operation_statuses.add(operation_status);
        }
      }
      // 如果沒有找到任何餐廳
      if (operation_statuses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(operation_statuses, HttpStatus.OK);
  }


  //實作新增營業狀態
  public ResponseEntity<Operation_status> addOperation_status(Operation_status operation_status) {
    String sql = "INSERT INTO operation_status (operation_status_id, operation_status) VALUES (?, ?)";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, operation_status.getOperation_status_id());
      pstmt.setString(2, operation_status.getOperation_status());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(operation_status, HttpStatus.CREATED);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作修改營業狀態
  public ResponseEntity<Operation_status> updateOperation_status(Operation_status operation_status) {
    String sql = "UPDATE operation_status SET operation_status = ? WHERE operation_status_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, operation_status.getOperation_status());
      pstmt.setString(2, operation_status.getOperation_status_id());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(operation_status, HttpStatus.OK);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //實作刪除營業狀態
  public ResponseEntity<Void> deleteOperation_status(String operation_status_id) {
    String sql = "DELETE FROM operation_status WHERE operation_status_id = ?";
    try (Connection conn = dbService.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, operation_status_id);
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