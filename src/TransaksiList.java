/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TIARA
 */
public class TransaksiList {
    int id_transaksi, id_barang, idRiwayat_hutang, id_pembeli, jumlah_pembayaran;
    String tgl_pembayaran;

    public TransaksiList(int id_transaksi, int id_barang, int idRiwayat_hutang, int id_pembeli, int jumlah_pembayaran, String tgl_pembayaran) {
        this.id_transaksi = id_transaksi;
        this.id_barang = id_barang;
        this.idRiwayat_hutang = idRiwayat_hutang;
        this.id_pembeli = id_pembeli;
        this.jumlah_pembayaran = jumlah_pembayaran;
        this.tgl_pembayaran = tgl_pembayaran;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
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

    public int getId_pembeli() {
        return id_pembeli;
    }

    public void setId_pembeli(int id_pembeli) {
        this.id_pembeli = id_pembeli;
    }

    public int getJumlah_pembayaran() {
        return jumlah_pembayaran;
    }

    public void setJumlah_pembayaran(int jumlah_pembayaran) {
        this.jumlah_pembayaran = jumlah_pembayaran;
    }

    public String getTgl_pembayaran() {
        return tgl_pembayaran;
    }

    public void setTgl_pembayaran(String tgl_pembayaran) {
        this.tgl_pembayaran = tgl_pembayaran;
    }
    
    
    
}
