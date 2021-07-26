/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TIARA
 */
public class ProdukList {
    int id_produk, id_barang, harga_asli, produk_masuk;
    String nama_produk;

    public ProdukList(int id_produk, int id_barang, int harga_asli, int produk_masuk, String nama_produk) {
        this.id_produk = id_produk;
        this.id_barang = id_barang;
        this.harga_asli = harga_asli;
        this.produk_masuk = produk_masuk;
        this.nama_produk = nama_produk;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public int getHarga_asli() {
        return harga_asli;
    }

    public void setHarga_asli(int harga_asli) {
        this.harga_asli = harga_asli;
    }

    public int getProduk_masuk() {
        return produk_masuk;
    }

    public void setProduk_masuk(int produk_masuk) {
        this.produk_masuk = produk_masuk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }
    
}
