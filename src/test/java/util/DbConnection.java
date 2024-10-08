package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DbConnection {


    public static ArrayList<HashMap<String, Object>> fetchData(String dBName,String tableName,String columnName,String id){
        String url = "jdbc:mysql://10.0.10.75:3306/"+dBName+"?useSSL=false";
        String user = "mysql";
        String password = "Bgjs*t*5#GWP3ECz";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();

            String query = "select * from "+tableName+" where "+columnName+"='"+id+"' ORDER BY installment_number";
            System.out.println("Query...."+query);
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>(50);

            while(rs.next()) {
                HashMap<String, Object> row = new HashMap<String, Object>(columnCount);

                for(int i=1; i<=columnCount; i++) {
                    row.put(rsmd.getColumnName(i), rs.getString(i));
                }

                //System.out.println("row...."+row);
                list.add(row);
            }

            //  System.out.println(list);
            con.close();

            return list;

        } catch (SQLException e) {
            System.out.println("Error "+e);
        }
        return null;
    }



}
