import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

public class kamera {
    private int id;
    private String brand = null;
    private String type = null;
    private String harga = null;
    private String kode_kamera = null;
    public Object conn; 

    public kamera(int inputId, String inputBrand, String inputType, String inputHarga, String inputKode_kamera) {
        this.id = inputId;
        this.brand = inputBrand;
        this.type = inputType;
        this.harga = inputHarga;
        this.kode_kamera = inputKode_kamera;
    }



    public int getId(){
        return id;
    }

    public String getBrand(){
        return brand;
    }

    public String getType(){
        return type;
    }

    public String getHarga(){
        return harga;
    }
    public String getKode_kamera(){
        return kode_kamera;
    }

    public void setId(String text) {
    }


    public void setHarga() {
    }


    public void setHarga(String text) {
    }


    public void setHarga(double parseDouble) {
    }


    public void setBrand(String text) {
    }


    public void setType(String text) {
    }


    public void setKode_kamera(String text) {
    }



}