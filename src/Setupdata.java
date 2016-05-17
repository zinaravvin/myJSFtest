import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

//import static java.sql.DriverManager.getConnection;

/**
 * Created by 202824 on 3/9/2016.
 */
@ManagedBean(name = "studentdata")
@SessionScoped
public class Setupdata implements Serializable{

    private static final long serialVersionUID = 1L;
    private static ArrayList<StudentBean> studentList;
    String fnameo ;
    String lnameo ;
    String cntryo ;
    @PostConstruct
    private void initStudents() {
        System.out.println("postco");
        studentList = loadStudentArray();
    }

    public void insertStudent(StudentBean stnt){
        String fname =  stnt.getFirstName();
        String lname = stnt.getLastName();
        String cntry = stnt.getCountry();
        System.out.println(" Before insert student = " + fname + lname + cntry);
        if (lname.equals("")  || fname.equals("")){
            stnt.setMessage( "Please enter First Name and Last name");
        }else {
            connDataBaseInsert(fname, lname, cntry);
            loadStudentArray();
            System.out.println("Insert student = " + fname + lname + cntry);
        }
    }


    public void deleteStudent(StudentBean stntdel){
        String fnamed = stntdel.getFirstName();
        String lnamed = stntdel.getLastName();
        String cntryd = stntdel.getCountry();

        connDataBaseDelete(fnamed,lnamed,cntryd);
        loadStudentArray();
        System.out.println("Delete student = " + fnamed + lnamed + cntryd);
    }

    public String updateStudent(StudentBean s){
        this.fnameo = s.getFirstName();
        this.lnameo = s.getLastName();
        this.cntryo = s.getCountry();
        System.out.println("First name old = " + fnameo  );
        s.setEditable(true);

        return null;
    }
        public void connDataBaseUpdate(String firstName, String lastName, String country, int studentid, String fnameo ) {

        PreparedStatement pst3 = null;
         /*   String stm3 = "UPDATE STUDENTS SET STUDENTID = " + "'"+ studentid + "'" +"," + "LASTNAME = " + "'"+ lastName + "'" +"," + "FIRSTNAME = "
                    + "'" + firstName + "'" + ","+ "COUNTRY = " + "'"+ country + "'" + " WHERE " + "FIRSTNAME = " + "'" + fnameo + "'"; */

            String stm3 = "UPDATE STUDENTS SET STUDENTID = ? ,LASTNAME = ?,FIRSTNAME = ?,COUNTRY =? where FIRSTNAME=?";



        try (Connection con = getConnection()) {

            pst3 = con.prepareStatement(stm3);
            pst3.setInt(1,studentid);
            pst3.setString(2,lastName);
            pst3.setString(3,firstName);
            pst3.setString(4,country);
            pst3.setString(5,fnameo);
            System.out.println(stm3);
            pst3.execute();



        } catch (SQLException e) {
            e.printStackTrace();

        }


    }
    public String saveStudent(){

        for (StudentBean s:studentList){
            if (s.editable) {
                System.out.println("First name new " + s.editable + s.getFirstName());
                String fnameu = s.getFirstName();
                String lnameu = s.getLastName();
                String cntryu = s.getCountry();
                connDataBaseUpdate(fnameu, lnameu, cntryu, 3, fnameo);
                s.setEditable(false);
                System.out.println("Updated student = " + fnameu + lnameu + cntryu);
            }
        }
            loadStudentArray();

        return null;
    }



    public void connDataBaseInsert(String firstName, String lastName, String country) {

        PreparedStatement pst1 = null;
        String stm1 = "INSERT INTO STUDENTS (studentid, firstname,lastname,country) VALUES (6,?,?,?)";
        System.out.println(stm1);
        try (Connection con = getConnection()) {

            pst1 = con.prepareStatement(stm1);

            pst1.setString(1,firstName);
            pst1.setString(2,lastName);
            pst1.setString(3,country);
            System.out.println(stm1);
            pst1.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void connDataBaseDelete(String firstName, String lastName, String country) {

        PreparedStatement pstd = null;
      //*  String stm2 = "Delete FROM STUDENTS WHERE " +
       //*         "(lastname ='" +  lastName + "'" + " and firstname ='" + firstName + "' and country = '" + country+"')" ;

        String stm2 = "Delete FROM STUDENTS WHERE lastname =? and firstname = ?and country = ?" ;
        System.out.println(stm2);
        try (Connection con = getConnection()) {

            pstd = con.prepareStatement(stm2);
            pstd.setString(1,lastName);
            pstd.setString(2,firstName);
            pstd.setString(3,country);
            System.out.println(stm2);
            pstd.execute();

        } catch (SQLException e) {

            e.printStackTrace();

        }


    }


    public ArrayList<StudentBean> loadStudentArray(){
        System.out.println("loadStudentArray");
        studentList = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String stm = "Select * from Students";
        try (Connection con = getConnection()){

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while(rs.next()){
                StudentBean student = new StudentBean();

                student.setFirstName(rs.getString(3));
                student.setLastName(rs.getString(2));
                student.setCountry(rs.getString(4));
                System.out.println(stm);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //  System.out.println(studentList.size());
        return studentList;





    }
    public ArrayList<StudentBean> getStudentList() {

        return studentList;
    }

    public Connection getConnection(){
        Connection con = null;
        //* System.out.println("getConnection..");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:1521/xe", "aristo_owner", "ardvo1");

        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return con;
    }
}


//*   public static String basicToString(BasicEvent bse){
//*       String out = bse.getLevel() + ";" + bse.getEtype() + ";" + bse.getxPos() + ";" + bse.getyPos() + ";" + bse.getDate().dateAsString() + ";" + bse.getTime().timeAsString() + ";" + bse.getRlb() + ";" + bse.getSig() + ";" + bse.getReserved();
//*       return out;
