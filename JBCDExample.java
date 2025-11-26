import java.sql.*;
import java.util.Scanner;

public class JBCDExample {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String url      = "jdbc:sqlite:identifier.MyDB";   //database specific url.
        String user     = "";
        String password = "";
        String answer;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("1.create|2.Update|3.Delete");
            System.out.print("Enter your choice:");
            int choice = sc.nextInt();
            sc.nextLine();
            String bookName;
            String authors;
            int years;
            int page;
            switch (choice){
                case 1:
                    System.out.print("Enter book name: ");
                    bookName = sc.nextLine();
                    System.out.print("Enter author name: ");
                    authors = sc.nextLine();
                    System.out.print("Enter year: ");
                    years = sc.nextInt();
                    System.out.print("Enter pages: ");
                    page = sc.nextInt();
                    sc.nextLine();
                    insertBooksWithPrepare(url, bookName, authors, years, page);
                    break;
                case 2:

                    System.out.print("Enter book name: ");
                    bookName = sc.nextLine();
                    System.out.print("Enter author name: ");
                    authors = sc.nextLine();
                    System.out.print("Enter year: ");
                    years = sc.nextInt();
                    System.out.print("Enter pages: ");
                    page = sc.nextInt();
                    sc.nextLine();
                    updateRow(url, bookName, authors, years, page);
                    break;
                case 3:
                    System.out.print("Enter id: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    deleteRow(url, id);
                    break;
            }
            System.out.print("Do you want to continue? y/n:");
            answer = sc.nextLine();

        }while(answer.equals("y"));
        sc.close();
        System.out.println("Thank you for using our program!");

    }

    private static void showBooks(String url)  {
        try(Connection connection = DriverManager.getConnection(url)){
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM books";
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString("title")+'\t');
                        System.out.print(resultSet.getString("author")+'\t');
                        System.out.print(resultSet.getString("year")+'\t');
                        System.out.print(resultSet.getString("pages")+'\t');
                        System.out.println();

                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }



    }

    private static void createTable(String url,String user,String password) throws ClassNotFoundException {
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()){
                String sql =
                        """
                        CREATE TABLE books (
                           id     INTEGER PRIMARY KEY AUTOINCREMENT,
                           title  VARCHAR(100),
                           author VARCHAR(50),
                           year   INTEGER,
                           pages  INTEGER
                        );   
                        """;
                statement.executeUpdate(sql);
                System.out.println("Create table successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertBooksWithPrepare(String url,String book,String authors,int years,int page) {
        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement statement = connection.createStatement()) {
                 String sql =
                                """ 
                                    INSERT INTO books(title,author,year,pages)
                                    VALUES (?, ?, ?, ?);
                                    
                                """;
                try (PreparedStatement ps = connection.prepareStatement(sql)) {

                    ps.setString(1, book);
                    ps.setString(2, authors);
                    ps.setInt(3, years);
                    ps.setInt(4, page);

                    int rows = ps.executeUpdate();
                    System.out.println("Insert value successfully, rows affected = " + rows);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertMultiple(String url) {
        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement statement = connection.createStatement()) {
                String sql =
                        """
                           INSERT INTO books(title,author,year,pages)
                           VALUES ('harry 4','j.k',2006,126);
                         """;
                statement.executeUpdate(sql);
                System.out.println("Insert value successfully");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private static void deleteRow(String url, int id){
            try(Connection connection = DriverManager.getConnection(url)){
                try(Statement statement = connection.createStatement()) {
                    String sql =
                            """
                                    DELETE FROM books WHERE id=?;
                                    """;

                    try (PreparedStatement ps = connection.prepareStatement(sql)) {
                        ps.setInt(1,id);
                        int rows = ps.executeUpdate();
                        System.out.println("Delete value successfully, rows affected = " + rows);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

        }catch (SQLException e){
                e.printStackTrace();
            }
    }

    private static void updateRow(String url,String book,String authors,int years,int page){
        try(Connection connection = DriverManager.getConnection(url)){
            try(Statement statement = connection.createStatement()){
                String sql =
                        """
                        UPDATE books SET author=?,year=?,pages=? WHERE title=?;
                        """;
                try(PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, authors);
                    ps.setInt(2, years);
                    ps.setInt(3, page);
                    ps.setString(4, book);
                    int rows = ps.executeUpdate();
                    System.out.println("Update value successfully, rows affected = " + rows);
                }catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}