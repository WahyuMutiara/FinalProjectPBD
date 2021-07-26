/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TIARA
 */
public class BarangList {
    int id_barang, idRiwayat_hutang, id_transaksi, id_produk, harga_jual, produk_terjual, stok_barang;
    String nama_produk;

    public BarangList(int id_barang, int idRiwayat_hutang, int id_transaksi, int id_produk, int harga_jual, int produk_terjual, int stok_barang, String nama_produk) {
        this.id_barang = id_barang;
        this.idRiwayat_hutang = idRiwayat_hutang;
        this.id_transaksi = id_transaksi;
        this.id_produk = id_produk;
        this.harga_jual = harga_jual;
        this.produk_terjual = produk_terjual;
        this.stok_barang = stok_barang;
        this.nama_produk = nama_produk;
    }

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public int getIdRiwayat_hutang() {
        return idRiwayat_hutang;
    }

    public void setIdRiwayat_hutang(int idRiwayat_hutang) {
        this.idRiwayat_hutang = idRiwayat_hutang;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    public int getProduk_terjual() {
        return produk_terjual;
    }

    public void setProduk_terjual(int produk_terjual) {
        this.produk_terjual = produk_terjual;
    }

    public int getStok_barang() {
        return stok_barang;
    }

    public void setStok_barang(int stok_barang) {
        this.stok_barang = stok_barang;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }
    
}
