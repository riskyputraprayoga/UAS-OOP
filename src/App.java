import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
    
public class App extends Application {
    Stage windowStage;
    TableView<kamera> table;
    TableView<kamera> tableView = new TableView<kamera>();
    TextField idInput, brandInput, typeInput, hargaInput, kode_kameraInput;

    @Override
    public void start(Stage stage) {

        // Menampilkan nama window
        windowStage = stage;
        windowStage.setTitle("DataBase - Kamera");
       
        //Menampilkan tabel
        TableColumn<kamera, String> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<kamera, String> columnBrand = new TableColumn<>("Brand");
        columnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<kamera, String> columnType = new TableColumn<>("Type");
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<kamera, String> columnHarga = new TableColumn<>("Harga");
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));

        TableColumn<kamera, String> columnKode_kamera = new TableColumn<>("Kode Kamera");
        columnKode_kamera.setCellValueFactory(new PropertyValueFactory<>("kode_kamera"));


        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnBrand);
        tableView.getColumns().add(columnType);
        tableView.getColumns().add(columnHarga);
        tableView.getColumns().add(columnKode_kamera);


        //Input id
        idInput = new TextField();
        idInput.setPromptText("id");
        idInput.setMinWidth(10);

        //Input brand
        brandInput = new TextField();
        brandInput.setPromptText("Brand");
        brandInput.setMinWidth(20);

        //Input Type
        typeInput = new TextField();
        typeInput.setPromptText("Type");
        typeInput.setMinWidth(20);

        //Input harga
        hargaInput = new TextField();
        hargaInput.setPromptText("Harga");
        hargaInput.setMinWidth(20);

        //Input kode Kamera
        kode_kameraInput = new TextField();
        kode_kameraInput.setPromptText("Kode Kamera");
        kode_kameraInput.setMinWidth(20);

        //Button
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editButtonClicked());
        
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, brandInput, typeInput, hargaInput, kode_kameraInput, editButton, updateButton, deleteButton);

        String url = "jdbc:mysql://localhost:3306/kamera";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet record = stmt.executeQuery("select*from daftar_kamera");

            while (record.next()) {
                tableView.getItems().add(new kamera(record.getInt("id"), record.getString("brand"), record.getString("type"), record.getString("harga"), record.getString("kode_kamera")));
            }
        }
        catch (SQLException e) {
            System.out.print("koneksi gagal");
        }
        

        VBox vbox = new VBox(tableView);
        vbox.getChildren().addAll(hBox);

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.show();

    }




    //Update Button Clicked
    private void updateButtonClicked(){

        Database db = new Database();
                try {
                    Statement state = db.conn.createStatement();
                    String sql = "insert into daftar_kamera SET brand='%s', type='%s', harga='%s', kode_kamera='%s'";
                    sql = String.format(sql, brandInput.getText(), typeInput.getText(), hargaInput.getText(), kode_kameraInput.getText());
                    state.execute(sql);
                    // idInput.clear();
                    brandInput.clear();
                    typeInput.clear();
                    hargaInput.clear();
                    kode_kameraInput.clear();
                    loadData();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            
        };


    //Edit Button Clicked
    private void editButtonClicked(){

        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update daftar_kamera set brand = '%s',  type = '%s', harga = '%s' WHERE kode_kamera ='%s'";
            sql = String.format(sql, brandInput.getText(), typeInput.getText(),hargaInput.getText(), kode_kameraInput.getText());
            state.execute(sql);
            brandInput.clear();
            typeInput.clear();
            hargaInput.clear();
            kode_kameraInput.clear();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Delete button Clicked
    private void deleteButtonClicked(){

        Database db = new Database();
        try{
            Statement state = db.conn.createStatement();
            String sql = "delete from daftar_kamera where kode_kamera ='%s';";
            sql = String.format(sql, kode_kameraInput.getText());
            state.execute(sql);
            kode_kameraInput.clear();
            loadData();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    

    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from daftar_kamera");
            tableView.getItems().clear();
            while(rs.next()){
                tableView.getItems().add(new kamera(rs.getInt("id"), rs.getString("brand"), rs.getString("type"), rs.getString("harga"), rs.getString("kode_kamera")));
            }
            
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}