package com.shopkeeper.lam.database;

import com.shopkeeper.lam.models.*;
import com.shopkeeper.mediaone.database.DbAdapterCache;
import com.shopkeeper.mediaone.database.ReadOnlyDbAdapterCache;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PersonDbSet {
    private Connection conn;
    private ReadOnlyDbAdapterCache readOnlyCache;
    private ObservableList<Person> list;
    public PersonDbSet(Connection conn, ReadOnlyDbAdapterCache cache, ObservableList<Person> people){
        this.conn = conn;
        this.list = people;
        this.readOnlyCache = cache;
    }
    public boolean createTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE people (");
        sqlBuilder.append( "personId      INTEGER  PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append( "name          TEXT     NOT NULL,");
        sqlBuilder.append( "dateOfBirth   TEXT     NOT NULL,");
        sqlBuilder.append( "description   TEXT     NOT NULL,");
        sqlBuilder.append( "job           TEXT     NOT NULL");
        sqlBuilder.append( ");");
        String sql = sqlBuilder.toString();
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //Indexes
            stmt.execute("CREATE UNIQUE INDEX idx_people_personId ON people(personId);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void load() throws Exception {
        String sql = "SELECT personId, name, dateOfBirth, description,job FROM people";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        Person person;
        while (rs.next()) {
            person = new Person();
            person.setPersonId(rs.getInt("personId"));
            person.setName(rs.getString("name"));
            person.setDateOfBirth(LocalDate.parse(rs.getString("dateOfBirth")));
            person.setDescription(rs.getString("description"));
            person.setJob(JobOfPerson.valueOf(rs.getString("job")));

            list.add(person);
        }
        rs.close();
        stmt.close();
    }
    //Auto set id for staff after it was inserted
    //Return true if success, otherwise return false
    public boolean insert(Person person){
        if (person.getPersonId() != 0) return false;
        String sql = "INSERT INTO people(name, dateOfBirth, description, job) VALUES(?,DATE(?),?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getDateOfBirth().toString());
            pstmt.setString(3, person.getDescription());
            pstmt.setString(4, person.getJob().toString());
            int affected = pstmt.executeUpdate();
            if (affected == 0) throw new Exception("Creating person failed, no rows affected.");
            //Auto set ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setPersonId(generatedKeys.getInt(1));
            } else throw new Exception("Creating person failed, no ID obtained.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        list.add(person);
        return true;
    }
    //Return true if success, otherwise return false
    public boolean update(Person person){
        if(!list.contains(person))
        {
            System.err.println("person is not in DbAdapter's cache");
            return false;
        }

        String sql = "UPDATE people SET name=?,dateOfBirth=DATE(?),description=?,job=? WHERE personId=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getDateOfBirth().toString());
            pstmt.setString(3, person.getDescription());
            pstmt.setString(4, person.getJob().toString());
            pstmt.setInt(5, person.getPersonId());
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                throw new Exception("Person (ID = " + person.getPersonId() + ") does not exist in \"people\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    //Return true if success, otherwise return false
    public boolean deletePerson(Person person){
        if(person.countTimesToBeReferenced() != 0) {
            System.err.println("Something have referenced to this person.");
            return false;
        }
        int index = list.indexOf(person);
        if(index < 0){
            System.err.println("person is not in DbAdapter's cache");
            return false;
        }

        String sql = "DELETE FROM people WHERE personId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, person.getPersonId());
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                throw new Exception("Person (ID = " + person.getPersonId() + ") does not exist in \"people\" table.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        //When Success
        list.remove(index, index + 1);
        return true;
    }


}
