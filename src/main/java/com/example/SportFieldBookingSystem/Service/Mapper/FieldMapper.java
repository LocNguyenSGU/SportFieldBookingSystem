package com.example.SportFieldBookingSystem.Service.Mapper;

import javax.swing.tree.RowMapper;
import com.example.SportFieldBookingSystem.Entity.Field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldMapper implements RowMapper<Field> {
    public static final String BASE_SQL //
            = "Select fi.field_id fi.field_code fi.field_ From Bank_Account ba ";

    @Override
    public BankAccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("Id");
        String fullName = rs.getString("Full_Name");
        double balance = rs.getDouble("Balance");

        return new BankAccountInfo(id, fullName, balance);
    }
}
