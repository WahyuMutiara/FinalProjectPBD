
import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TIARA
 */
public class MenuUtama extends javax.swing.JFrame {

    /**
     * Creates new form MenuUtama
     */
    PreparedStatement pst = null;
    ResultSet rs = null;
    //disiini copy kan
    CardLayout cardLayout;

    public MenuUtama() {
        initComponents();
        barangList();
        pembeliList();
        produkList();
        riwayathutangList();
        transaksiList();
        getDataPemasukan();
        getDataPelanggan();
        getStokBarang();
        getTotalHutang();
        getDataPenghutang();
        cardLayout = (CardLayout) (pnlCard.getLayout());
        setLocationRelativeTo(null);
        fetchDataTabelBarang();
        fetchDataTabelPembeli();
        fetchDataTabelProduk();
        fetchDataTabelRiwayatHutang();
        fetchDataTransaksi();
    }

    public ArrayList<PembeliList> pembeliList() {
        ArrayList<PembeliList> pembeliList = new ArrayList<>();
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT data_pembeli.id_pembeli,data_pembeli.nama_pembeli,data_pembeli.alamat,data_pembeli.nomor_hp FROM data_pembeli ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            PembeliList pembeli;
            while (rs.next()) {
                pembeli = new PembeliList(rs.getInt("id_pembeli"), rs.getString("nama_pembeli"), rs.getString("alamat"), rs.getString("nomor_hp"));
                pembeliList.add(pembeli);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return pembeliList;
    }

    //
    private void getDataPemasukan() {
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "select sum(jumlah_pembayaran) as total from data_transaksi";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                jml_pelanggan4.setText(rs.getString("total"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void getStokBarang() {
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "select sum(stok_barang) as stok from data_barang";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                jml_pelanggan5.setText(rs.getString("stok"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void getTotalHutang() {
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "select sum(jumlahSisa_hutang) as totalHutang from datariwayat_hutang";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                totalHutang.setText(rs.getString("totalHutang"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void getDataPenghutang() {
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT COUNT(idRiwayat_hutang) as penghutang from datariwayat_hutang";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                totalPenghutang.setText(rs.getString("penghutang"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void getDataPelanggan() {
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT COUNT(id_pembeli) as pembeli from data_pembeli";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                jml_pelanggan1.setText(rs.getString("pembeli"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void fetchDataTabelPembeli() {
        ArrayList<PembeliList> list = pembeliList();
        DefaultTableModel model = (DefaultTableModel) tabelPembeli.getModel();
        Object[] row = new Object[4];
        for (int i = 0; i < list.size(); i++) {

            row[0] = list.get(i).getId_pembeli();
            row[1] = list.get(i).getNama_pembeli();
            row[2] = list.get(i).getAlamat();
            row[3] = list.get(i).getNomor_hp();

            model.addRow(row);
        }
    }

    public ArrayList<BarangList> barangList() {
        ArrayList<BarangList> barangList = new ArrayList<>();
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT data_barang.id_barang,data_barang.idRiwayat_hutang,data_barang.id_produk,data_barang.id_transaksi,data_barang.nama_produk,data_barang.harga_jual,data_barang.produk_terjual,data_barang.stok_barang FROM data_barang ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            BarangList barang;
            while (rs.next()) {
                barang = new BarangList(rs.getInt("id_barang"), rs.getInt("idRiwayat_hutang"), rs.getInt("id_transaksi"), rs.getInt("id_produk"), rs.getInt("harga_jual"), rs.getInt("produk_terjual"), rs.getInt("stok_barang"), rs.getString("nama_produk"));
                barangList.add(barang);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return barangList;
    }

    private void fetchDataTabelBarang() {
        ArrayList<BarangList> list = barangList();
        DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {

            row[0] = list.get(i).getId_barang();
            row[1] = list.get(i).getNama_produk();
            row[2] = list.get(i).getHarga_jual();
            row[3] = list.get(i).getProduk_terjual();
            row[4] = list.get(i).getStok_barang();

            model.addRow(row);
        }
    }

    public ArrayList<ProdukList> produkList() {
        ArrayList<ProdukList> produkList = new ArrayList<>();
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT data_produk.id_produk,data_produk.id_barang,data_produk.nama_produk,data_produk.harga_asli,data_produk.produk_masuk FROM data_produk ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            ProdukList produk;
            while (rs.next()) {
                ////// urutan tidak sama////////
                produk = new ProdukList(rs.getInt("id_produk"), rs.getInt("id_barang"), rs.getInt("harga_asli"), rs.getInt("produk_masuk"), rs.getString("nama_produk"));
                produkList.add(produk);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return produkList;
    }

    private void fetchDataTabelProduk() {
        ArrayList<ProdukList> list = produkList();
        DefaultTableModel model = (DefaultTableModel) tabelProduk.getModel();
        Object[] row = new Object[4];
        for (int i = 0; i < list.size(); i++) {

            row[0] = list.get(i).getId_produk();
            row[1] = list.get(i).getNama_produk();
            row[2] = list.get(i).getHarga_asli();
            row[3] = list.get(i).getProduk_masuk();

            model.addRow(row);
        }
    }

    public ArrayList<RiwayatHutangList> riwayathutangList() {
        ArrayList<RiwayatHutangList> riwayathutangList = new ArrayList<>();
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT datariwayat_hutang.idRiwayat_hutang,datariwayat_hutang.id_barang,datariwayat_hutang.id_pembeli,datariwayat_hutang.jumlah_hutang,datariwayat_hutang.tgl_pembayaran,datariwayat_hutang.status_hutang,datariwayat_hutang.jumlahSisa_hutang FROM datariwayat_hutang ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            RiwayatHutangList riwayathutang;
            while (rs.next()) {
                riwayathutang = new RiwayatHutangList(rs.getInt("idRiwayat_hutang"), rs.getInt("id_barang"), rs.getInt("jumlah_hutang"), rs.getInt("id_pembeli"), rs.getInt("jumlahSisa_hutang"), rs.getString("status_hutang"), rs.getString("tgl_pembayaran"));
                riwayathutangList.add(riwayathutang);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return riwayathutangList;
    }

    private boolean validateInput() throws SQLException {
        boolean isCompleted = false;
        Riwayathutangcoba statushutang = new Riwayathutangcoba();

        if (lunasRadioBtn.isSelected()) {
            statushutang.setStatus_hutang(lunasRadioBtn.getText());
            isCompleted = true;
        } else if (belumlunasRadioBtn.isSelected()) {
            statushutang.setStatus_hutang(belumlunasRadioBtn.getText());
            isCompleted = true;
        } else if (belumbyrRadioBtn.isSelected()) {
            statushutang.setStatus_hutang(belumbyrRadioBtn.getText());
            isCompleted = true;
        }

        return true;
    }

    private void fetchDataTabelRiwayatHutang() {
        ArrayList<RiwayatHutangList> list = riwayathutangList();
        DefaultTableModel model = (DefaultTableModel) tabelRiwayatHutang.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {

            row[0] = list.get(i).getIdRiwayat_hutang();
            row[1] = list.get(i).getJumlah_hutang();
            row[2] = list.get(i).getTgl_pembayaran();
            row[3] = list.get(i).getStatus_hutang();
            row[4] = list.get(i).getJumlahSisa_hutang();

            model.addRow(row);
        }
    }

    public ArrayList<TransaksiList> transaksiList() {
        ArrayList<TransaksiList> transaksiList = new ArrayList<>();
        try {
            Connection con = connectionHelper.getConnection();
            String sql = "SELECT data_transaksi.id_transaksi,data_transaksi.id_barang,data_transaksi.idRiwayat_hutang,data_transaksi.id_pembeli,data_transaksi.jumlah_pembayaran,data_transaksi.tgl_pembayaran FROM data_transaksi ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            TransaksiList riwayathutang;
            while (rs.next()) {
                riwayathutang = new TransaksiList(rs.getInt("id_transaksi"), rs.getInt("id_barang"), rs.getInt("id_pembeli"), rs.getInt("idRiwayat_hutang"), rs.getInt("jumlah_pembayaran"), rs.getString("tgl_pembayaran"));
                transaksiList.add(riwayathutang);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return transaksiList;
    }

    // copy 3
    private void fetchDataTransaksi() {
        ArrayList<TransaksiList> list = transaksiList();
        DefaultTableModel model = (DefaultTableModel) tabelTransaksi.getModel();
        Object[] row = new Object[3];
        for (int i = 0; i < list.size(); i++) {

            row[0] = list.get(i).getId_transaksi();
            row[1] = list.get(i).getJumlah_pembayaran();
            row[2] = list.get(i).getTgl_pembayaran();

            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        dashboardBtn = new javax.swing.JButton();
        pembeliBtn = new javax.swing.JButton();
        barangBtn = new javax.swing.JButton();
        produkBtn = new javax.swing.JButton();
        hutangBtn = new javax.swing.JButton();
        LogoutBtn = new javax.swing.JButton();
        transaksiBtn = new javax.swing.JButton();
        pnlCard = new javax.swing.JPanel();
        pnlCardDashboard = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jml_pelanggan1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jml_pelanggan4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jml_pelanggan5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        totalHutang = new javax.swing.JLabel();
        totalPenghutang = new javax.swing.JLabel();
        pnlCardPembeli = new javax.swing.JPanel();
        rootPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPembeli = new javax.swing.JTable();
        saveBtnPembeli = new javax.swing.JButton();
        deleteBtnPembeli = new javax.swing.JButton();
        updateBtnPembeli = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        namaPembelitxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        noHptxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        IDPembelitxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        alamattxt = new javax.swing.JTextField();
        refreshBtnPembeli = new javax.swing.JButton();
        resetBtnPembeli = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        judulLabel1 = new javax.swing.JLabel();
        pnlCardBarang = new javax.swing.JPanel();
        rootPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBarang = new javax.swing.JTable();
        SaveBtnBarang = new javax.swing.JButton();
        deleteBtnBarang = new javax.swing.JButton();
        updateBtnBarang = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        namaProduktxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        StokBarangtxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jmlbrgkeluartxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        IDBarangtxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        HargaJualtxt = new javax.swing.JTextField();
        refreshBtnBarang = new javax.swing.JButton();
        resetBtnBarang = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        pnlCardProduk = new javax.swing.JPanel();
        rootPanel5 = new javax.swing.JPanel();
        JScrollPane4 = new javax.swing.JScrollPane();
        tabelProduk = new javax.swing.JTable();
        saveBtnProduk = new javax.swing.JButton();
        deleteBtnProduk = new javax.swing.JButton();
        updateBtnProduk = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        namaProduktxtnew = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jmlprodukmasuktxt = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        IDProduktxt = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        hargaAslitxt = new javax.swing.JTextField();
        refreshBtnProduk = new javax.swing.JButton();
        resetBtnProduk = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pnlCardHutang = new javax.swing.JPanel();
        rootPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelRiwayatHutang = new javax.swing.JTable();
        saveBtnHutang = new javax.swing.JButton();
        deleteBtnHutang = new javax.swing.JButton();
        updateBtnHutang = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        Jumlahtxt = new javax.swing.JTextField();
        IDRiwayatHutangtxt = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        sisaHutangtxt = new javax.swing.JTextField();
        refreshBtnHutang = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        resetBtnHutang = new javax.swing.JButton();
        lunasRadioBtn = new javax.swing.JRadioButton();
        belumlunasRadioBtn = new javax.swing.JRadioButton();
        belumbyrRadioBtn = new javax.swing.JRadioButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        pnlCardTransaksi = new javax.swing.JPanel();
        rootPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        saveBtnTransaksi = new javax.swing.JButton();
        deleteBtnTransaksi = new javax.swing.JButton();
        updateBtnTransaksi = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jmlTransaksitxt = new javax.swing.JTextField();
        IDTransaksitxt = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        refreshBtnTransaksi = new javax.swing.JButton();
        resetBtnTransaksi = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setRightComponent(jPanel7);

        jPanel8.setBackground(new java.awt.Color(28, 37, 65));

        dashboardBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        dashboardBtn.setText("Dashboard");
        dashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardBtnActionPerformed(evt);
            }
        });

        pembeliBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        pembeliBtn.setText("Pembeli");
        pembeliBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pembeliBtnActionPerformed(evt);
            }
        });

        barangBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        barangBtn.setText("Barang");
        barangBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangBtnActionPerformed(evt);
            }
        });

        produkBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        produkBtn.setText("Produk");
        produkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produkBtnActionPerformed(evt);
            }
        });

        hutangBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        hutangBtn.setText("Riwayat Hutang");
        hutangBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hutangBtnActionPerformed(evt);
            }
        });

        LogoutBtn.setBackground(new java.awt.Color(204, 0, 0));
        LogoutBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        LogoutBtn.setForeground(new java.awt.Color(255, 255, 255));
        LogoutBtn.setText("LOG OUT");
        LogoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutBtnMouseClicked(evt);
            }
        });
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });

        transaksiBtn.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        transaksiBtn.setText("Transaksi");
        transaksiBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiBtnMouseClicked(evt);
            }
        });
        transaksiBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pembeliBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barangBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(produkBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hutangBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transaksiBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(LogoutBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(dashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(pembeliBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(barangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(produkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(hutangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(transaksiBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(LogoutBtn)
                .addGap(24, 24, 24))
        );

        jSplitPane1.setLeftComponent(jPanel8);

        pnlCard.setLayout(new java.awt.CardLayout());

        pnlCardDashboard.setBackground(new java.awt.Color(197, 216, 209));

        jPanel1.setBackground(new java.awt.Color(244, 237, 234));

        jml_pelanggan1.setBackground(new java.awt.Color(255, 255, 255));
        jml_pelanggan1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(244, 209, 174));

        jLabel39.setFont(new java.awt.Font("Bodoni Bd BT", 0, 18)); // NOI18N
        jLabel39.setText("Jumlah Pelanggan ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jml_pelanggan1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jml_pelanggan1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(244, 237, 234));

        jPanel6.setBackground(new java.awt.Color(244, 209, 174));

        jLabel38.setFont(new java.awt.Font("Bodoni Bd BT", 0, 18)); // NOI18N
        jLabel38.setText("Jumlah Pemasukan ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jml_pelanggan4.setBackground(new java.awt.Color(255, 255, 255));
        jml_pelanggan4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jml_pelanggan4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jml_pelanggan4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(244, 237, 234));

        jPanel9.setBackground(new java.awt.Color(244, 209, 174));

        jLabel36.setFont(new java.awt.Font("Bodoni Bd BT", 0, 18)); // NOI18N
        jLabel36.setText("Stok Barang");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(85, 85, 85))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jml_pelanggan5.setBackground(new java.awt.Color(255, 255, 255));
        jml_pelanggan5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jml_pelanggan5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jml_pelanggan5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(28, 37, 65));

        jLabel37.setFont(new java.awt.Font("Britannic Bold", 0, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Selamat Datang di");

        jLabel40.setFont(new java.awt.Font("Britannic Bold", 0, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("SISTEM INFORMASI PENJUALAN TOKO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel40))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(jLabel37)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(244, 237, 234));

        jLabel42.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        jLabel42.setText("Total Penghutang :");

        jLabel43.setFont(new java.awt.Font("Bodoni Bd BT", 0, 14)); // NOI18N
        jLabel43.setText("Total Hutang :");

        jPanel11.setBackground(new java.awt.Color(244, 209, 174));

        jLabel41.setFont(new java.awt.Font("Bodoni Bd BT", 0, 18)); // NOI18N
        jLabel41.setText("Tagihan Hutang ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        totalHutang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        totalPenghutang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(18, 18, 18)
                        .addComponent(totalHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(18, 18, 18)
                        .addComponent(totalPenghutang, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(totalPenghutang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(totalHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardDashboardLayout = new javax.swing.GroupLayout(pnlCardDashboard);
        pnlCardDashboard.setLayout(pnlCardDashboardLayout);
        pnlCardDashboardLayout.setHorizontalGroup(
            pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        pnlCardDashboardLayout.setVerticalGroup(
            pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCardDashboardLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(113, 113, 113))
        );

        pnlCard.add(pnlCardDashboard, "pnlCard1");

        rootPanel.setBackground(new java.awt.Color(91, 192, 190));
        rootPanel.setPreferredSize(new java.awt.Dimension(597, 340));

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        tabelPembeli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "Nama ", "Alamat", "No HP/WA"
            }
        ));
        tabelPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPembeliMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelPembeli);

        saveBtnPembeli.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        saveBtnPembeli.setText("Save");
        saveBtnPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnPembeliActionPerformed(evt);
            }
        });

        deleteBtnPembeli.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        deleteBtnPembeli.setText("Delete");
        deleteBtnPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnPembeliActionPerformed(evt);
            }
        });

        updateBtnPembeli.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        updateBtnPembeli.setText("Update");
        updateBtnPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnPembeliActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel7.setText("ID Pembeli :");

        namaPembelitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaPembelitxtActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel10.setText("Alamat :");

        noHptxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noHptxtActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel11.setText("No Hp / WA :");

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel12.setText("Nama :");

        refreshBtnPembeli.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        refreshBtnPembeli.setText("Refresh");
        refreshBtnPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnPembeliActionPerformed(evt);
            }
        });

        resetBtnPembeli.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        resetBtnPembeli.setText("Reset");
        resetBtnPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnPembeliActionPerformed(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(58, 80, 107));

        jLabel9.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Data Pembeli");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(343, 343, 343)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(58, 80, 107));

        judulLabel1.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        judulLabel1.setForeground(new java.awt.Color(255, 255, 255));
        judulLabel1.setText("Laporan Pembeli");
        judulLabel1.setPreferredSize(new java.awt.Dimension(100, 50));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(judulLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(judulLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(refreshBtnPembeli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(updateBtnPembeli)
                        .addGap(36, 36, 36)
                        .addComponent(deleteBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(208, 208, 208)
                                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(rootPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(IDPembelitxt, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(rootPanelLayout.createSequentialGroup()
                                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel10))
                                        .addGap(6, 6, 6)
                                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(rootPanelLayout.createSequentialGroup()
                                                .addComponent(resetBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(74, 74, 74)
                                                .addComponent(saveBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(namaPembelitxt, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                            .addComponent(alamattxt)
                                            .addComponent(noHptxt, javax.swing.GroupLayout.Alignment.LEADING)))))
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 68, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(IDPembelitxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(namaPembelitxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(alamattxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(noHptxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardPembeliLayout = new javax.swing.GroupLayout(pnlCardPembeli);
        pnlCardPembeli.setLayout(pnlCardPembeliLayout);
        pnlCardPembeliLayout.setHorizontalGroup(
            pnlCardPembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        pnlCardPembeliLayout.setVerticalGroup(
            pnlCardPembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardPembeli, "pnlCard2");

        rootPanel1.setBackground(new java.awt.Color(78, 165, 217));
        rootPanel1.setPreferredSize(new java.awt.Dimension(597, 340));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tabelBarang.setBackground(new java.awt.Color(204, 204, 204));
        tabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "Nama Produk", "Harga Jual", "Jml Produk Terjual", "Stok Barang"
            }
        ));
        tabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBarang);

        SaveBtnBarang.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        SaveBtnBarang.setText("Save");
        SaveBtnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnBarangActionPerformed(evt);
            }
        });

        deleteBtnBarang.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        deleteBtnBarang.setText("Delete");
        deleteBtnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnBarangActionPerformed(evt);
            }
        });

        updateBtnBarang.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        updateBtnBarang.setText("Update");
        updateBtnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnBarangActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel2.setText("ID Barang :");

        namaProduktxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaProduktxtActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel6.setText("Stok Barang :");

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel4.setText("Harga Jual :");

        jmlbrgkeluartxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmlbrgkeluartxtActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel5.setText("Jumlah Barang Keluar :");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel8.setText("Nama Produk :");

        refreshBtnBarang.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        refreshBtnBarang.setText("Refresh");
        refreshBtnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnBarangActionPerformed(evt);
            }
        });

        resetBtnBarang.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        resetBtnBarang.setText("Reset");
        resetBtnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnBarangActionPerformed(evt);
            }
        });

        jPanel20.setBackground(new java.awt.Color(42, 68, 148));

        jLabel20.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Laporan Barang");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(42, 68, 148));

        jLabel19.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Data Barang");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout rootPanel1Layout = new javax.swing.GroupLayout(rootPanel1);
        rootPanel1.setLayout(rootPanel1Layout);
        rootPanel1Layout.setHorizontalGroup(
            rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(rootPanel1Layout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(rootPanel1Layout.createSequentialGroup()
                        .addComponent(resetBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SaveBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(StokBarangtxt)
                    .addComponent(jmlbrgkeluartxt, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HargaJualtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaProduktxt, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDBarangtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanel1Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(rootPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(refreshBtnBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateBtnBarang)
                .addGap(47, 47, 47)
                .addComponent(deleteBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        rootPanel1Layout.setVerticalGroup(
            rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanel1Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDBarangtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaProduktxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HargaJualtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jmlbrgkeluartxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StokBarangtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtnBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardBarangLayout = new javax.swing.GroupLayout(pnlCardBarang);
        pnlCardBarang.setLayout(pnlCardBarangLayout);
        pnlCardBarangLayout.setHorizontalGroup(
            pnlCardBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        pnlCardBarangLayout.setVerticalGroup(
            pnlCardBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardBarang, "pnlCard3");

        rootPanel5.setBackground(new java.awt.Color(132, 140, 142));
        rootPanel5.setPreferredSize(new java.awt.Dimension(597, 340));

        tabelProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "Nama Produk", "Harga Asli", "Jml Produk Masuk"
            }
        ));
        tabelProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelProdukMouseClicked(evt);
            }
        });
        JScrollPane4.setViewportView(tabelProduk);

        saveBtnProduk.setText("Save");
        saveBtnProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnProdukActionPerformed(evt);
            }
        });

        deleteBtnProduk.setText("Delete");
        deleteBtnProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnProdukActionPerformed(evt);
            }
        });

        updateBtnProduk.setText("Update");
        updateBtnProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnProdukActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel28.setText("ID Produk:");

        namaProduktxtnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaProduktxtnewActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel30.setText("Harga Asli :");

        jmlprodukmasuktxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmlprodukmasuktxtActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel31.setText("Jumlah produk masuk :");

        jLabel32.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel32.setText("Nama Produk:");

        refreshBtnProduk.setText("Refresh");
        refreshBtnProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnProdukActionPerformed(evt);
            }
        });

        resetBtnProduk.setText("Reset");
        resetBtnProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnProdukActionPerformed(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(67, 80, 88));

        jLabel18.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Laporan Pembeli");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(336, 336, 336))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel19.setBackground(new java.awt.Color(67, 80, 88));

        jLabel15.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Data Produk");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(347, 347, 347)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout rootPanel5Layout = new javax.swing.GroupLayout(rootPanel5);
        rootPanel5.setLayout(rootPanel5Layout);
        rootPanel5Layout.setHorizontalGroup(
            rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanel5Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32)
                    .addComponent(jLabel28))
                .addGap(10, 10, 10)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanel5Layout.createSequentialGroup()
                        .addComponent(resetBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(saveBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(IDProduktxt)
                    .addComponent(namaProduktxtnew)
                    .addComponent(hargaAslitxt)
                    .addComponent(jmlprodukmasuktxt))
                .addGap(212, 212, 212))
            .addGroup(rootPanel5Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(JScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(rootPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(refreshBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateBtnProduk)
                .addGap(41, 41, 41)
                .addComponent(deleteBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        rootPanel5Layout.setVerticalGroup(
            rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanel5Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(IDProduktxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaProduktxtnew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(hargaAslitxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jmlprodukmasuktxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rootPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtnProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardProdukLayout = new javax.swing.GroupLayout(pnlCardProduk);
        pnlCardProduk.setLayout(pnlCardProdukLayout);
        pnlCardProdukLayout.setHorizontalGroup(
            pnlCardProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        pnlCardProdukLayout.setVerticalGroup(
            pnlCardProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardProduk, "pnlCard4");

        rootPanel4.setBackground(new java.awt.Color(243, 223, 193));
        rootPanel4.setPreferredSize(new java.awt.Dimension(597, 340));

        tabelRiwayatHutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "Jumlah", "Tanggal Bayar", "Status Hutang", "Sisa Hutang"
            }
        ));
        tabelRiwayatHutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRiwayatHutangMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelRiwayatHutang);

        saveBtnHutang.setText("Save");
        saveBtnHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnHutangActionPerformed(evt);
            }
        });

        deleteBtnHutang.setText("Delete");
        deleteBtnHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnHutangActionPerformed(evt);
            }
        });

        updateBtnHutang.setText("Update");
        updateBtnHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnHutangActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel23.setText("ID Riwayat Hutang :");

        Jumlahtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JumlahtxtActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel25.setText("Jumlah :");

        refreshBtnHutang.setText("Refresh");
        refreshBtnHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnHutangActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel26.setText("Status Hutang :");

        jLabel27.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        jLabel27.setText("Sisa Hutang :");

        resetBtnHutang.setText("Reset");
        resetBtnHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnHutangActionPerformed(evt);
            }
        });

        lunasRadioBtn.setText("Lunas");
        lunasRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunasRadioBtnActionPerformed(evt);
            }
        });

        belumlunasRadioBtn.setText("Belum Lunas");
        belumlunasRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                belumlunasRadioBtnActionPerformed(evt);
            }
        });

        belumbyrRadioBtn.setText("Belum Dibayar");

        jPanel16.setBackground(new java.awt.Color(221, 190, 168));

        jLabel17.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel17.setText("Laporan Riwayat Hutang");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(300, 300, 300))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel17.setBackground(new java.awt.Color(221, 190, 168));

        jLabel16.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel16.setText("Data Riwayat Hutang");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(308, 308, 308))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout rootPanel4Layout = new javax.swing.GroupLayout(rootPanel4);
        rootPanel4.setLayout(rootPanel4Layout);
        rootPanel4Layout.setHorizontalGroup(
            rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanel4Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(23, 23, 23)
                        .addComponent(IDRiwayatHutangtxt))
                    .addGroup(rootPanel4Layout.createSequentialGroup()
                        .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))
                        .addGap(48, 48, 48)
                        .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sisaHutangtxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(rootPanel4Layout.createSequentialGroup()
                                .addComponent(lunasRadioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                .addComponent(belumlunasRadioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Jumlahtxt)
                            .addGroup(rootPanel4Layout.createSequentialGroup()
                                .addComponent(belumbyrRadioBtn)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(rootPanel4Layout.createSequentialGroup()
                                .addComponent(resetBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(229, 229, 229))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
            .addGroup(rootPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(refreshBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateBtnHutang)
                .addGap(38, 38, 38)
                .addComponent(deleteBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        rootPanel4Layout.setVerticalGroup(
            rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanel4Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(IDRiwayatHutangtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(Jumlahtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(lunasRadioBtn))
                    .addComponent(belumlunasRadioBtn))
                .addGap(18, 18, 18)
                .addComponent(belumbyrRadioBtn)
                .addGap(18, 18, 18)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sisaHutangtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rootPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtnHutang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCardHutangLayout = new javax.swing.GroupLayout(pnlCardHutang);
        pnlCardHutang.setLayout(pnlCardHutangLayout);
        pnlCardHutangLayout.setHorizontalGroup(
            pnlCardHutangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        pnlCardHutangLayout.setVerticalGroup(
            pnlCardHutangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardHutang, "pnlCard5");

        pnlCardTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCardTransaksiMouseClicked(evt);
            }
        });

        rootPanel6.setBackground(new java.awt.Color(241, 242, 238));
        rootPanel6.setPreferredSize(new java.awt.Dimension(597, 340));

        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "Jumlah Transaksi", "Tanggal Pembayaran"
            }
        ));
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelTransaksi);

        saveBtnTransaksi.setText("Save");
        saveBtnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnTransaksiActionPerformed(evt);
            }
        });

        deleteBtnTransaksi.setText("Delete");
        deleteBtnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnTransaksiActionPerformed(evt);
            }
        });

        updateBtnTransaksi.setText("Update");
        updateBtnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnTransaksiActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel33.setText("ID Transaksi :");

        jmlTransaksitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmlTransaksitxtActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel35.setText("Jumlah Transaksi :");

        refreshBtnTransaksi.setText("Refresh");
        refreshBtnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnTransaksiActionPerformed(evt);
            }
        });

        resetBtnTransaksi.setText("Reset");
        resetBtnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnTransaksiActionPerformed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(191, 183, 182));

        jLabel13.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel13.setText("Data Transaksi");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(333, 333, 333))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(191, 183, 182));

        jLabel14.setFont(new java.awt.Font("Bodoni MT", 1, 18)); // NOI18N
        jLabel14.setText("Laporan Transaksi");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(323, 323, 323))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout rootPanel6Layout = new javax.swing.GroupLayout(rootPanel6);
        rootPanel6.setLayout(rootPanel6Layout);
        rootPanel6Layout.setHorizontalGroup(
            rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(rootPanel6Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel33))
                .addGap(30, 30, 30)
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(rootPanel6Layout.createSequentialGroup()
                        .addComponent(resetBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addComponent(saveBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jmlTransaksitxt)
                    .addComponent(IDTransaksitxt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rootPanel6Layout.createSequentialGroup()
                        .addGap(0, 101, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rootPanel6Layout.createSequentialGroup()
                        .addComponent(refreshBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(updateBtnTransaksi)))
                .addGap(42, 42, 42)
                .addComponent(deleteBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        rootPanel6Layout.setVerticalGroup(
            rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanel6Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(IDTransaksitxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jmlTransaksitxt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(rootPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout pnlCardTransaksiLayout = new javax.swing.GroupLayout(pnlCardTransaksi);
        pnlCardTransaksi.setLayout(pnlCardTransaksiLayout);
        pnlCardTransaksiLayout.setHorizontalGroup(
            pnlCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        pnlCardTransaksiLayout.setVerticalGroup(
            pnlCardTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );

        pnlCard.add(pnlCardTransaksi, "pnlCard6");

        jSplitPane1.setRightComponent(pnlCard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardBtnActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnlCard, "pnlCard1");
    }//GEN-LAST:event_dashboardBtnActionPerformed

    private void pembeliBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pembeliBtnActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnlCard, "pnlCard2");
    }//GEN-LAST:event_pembeliBtnActionPerformed

    private void barangBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangBtnActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnlCard, "pnlCard3");
    }//GEN-LAST:event_barangBtnActionPerformed

    private void produkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produkBtnActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnlCard, "pnlCard4");
    }//GEN-LAST:event_produkBtnActionPerformed

    private void hutangBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hutangBtnActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnlCard, "pnlCard5");
    }//GEN-LAST:event_hutangBtnActionPerformed

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin keluar ?", "Yakin ?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_LogoutBtnActionPerformed

    private void tabelPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPembeliMouseClicked
        // TODO add your handling code here:
        int i = tabelPembeli.getSelectedRow();
        TableModel model = tabelPembeli.getModel();
        IDPembelitxt.setText(model.getValueAt(i, 0).toString());
        namaPembelitxt.setText(model.getValueAt(i, 1).toString());
        alamattxt.setText(model.getValueAt(i, 2).toString());
        noHptxt.setText(model.getValueAt(i, 3).toString());

    }//GEN-LAST:event_tabelPembeliMouseClicked

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void saveBtnPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnPembeliActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            //query insert data ke dalam database mysql, kurang tgl pembelian tipe date
            pst = con.prepareStatement("INSERT INTO data_pembeli (id_pembeli,id_transaksi, nama_pembeli,alamat,nomor_hp) VALUES(?,?,?,?,?)");
            //validasi ketika textField kosong

            if (IDPembelitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID pembeli tidak boleh kosong");
            } else if (namaPembelitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nama pembeli tidak boleh kosong");
            } else if (alamattxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "alamat tidak boleh kosong");
            } else if (noHptxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nomor hp tidak boleh kosong");
            } else {
                //digunakan untuk memasukkan data ke masing2 variabel textfield seperti namaTxt, dll

                //misal bingung pst sama rs bisa diliat di variabel diatas
                pst.setString(1, IDPembelitxt.getText());
                pst.setString(2, IDPembelitxt.getText());
                pst.setString(3, namaPembelitxt.getText());
                pst.setString(4, alamattxt.getText());
                pst.setString(5, noHptxt.getText());

                pst.executeUpdate();

                //setelah nginput data kasih ini biar textfieldnya kosong lagi
                IDPembelitxt.setText("");
                namaPembelitxt.setText("");
                alamattxt.setText("");
                noHptxt.setText("");

                //setelah daftar muncul pop up dafatar berhasil dan akan tampil frame baru
                JOptionPane.showMessageDialog(null, "Input Berhasil");

            }

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveBtnPembeliActionPerformed

    private void deleteBtnPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnPembeliActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            int row = tabelPembeli.getSelectedRow();
            String value = (tabelPembeli.getModel().getValueAt(row, 0).toString());
            String sql = "DELETE FROM data_pembeli WHERE id_pembeli=" + value;
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel) tabelPembeli.getModel();
            model.setRowCount(0);
            fetchDataTabelPembeli();
            JOptionPane.showMessageDialog(rootPane, "Data terhapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_deleteBtnPembeliActionPerformed

    private void updateBtnPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnPembeliActionPerformed
        // TODO add your handling code here:
        try {

            Connection con = connectionHelper.getConnection();
            int row = tabelPembeli.getSelectedRow();
            String value = (tabelPembeli.getModel().getValueAt(row, 0).toString());
            String sql = "UPDATE data_pembeli SET id_pembeli=?, nama_pembeli=?, alamat=?, nomor_hp=?WHERE id_pembeli=" + value;
            if (IDPembelitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID pembeli tidak boleh kosong");
            } else if (namaPembelitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nama pembeli tidak boleh kosong");
            } else if (alamattxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "alamat tidak boleh kosong");
            } else if (noHptxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nomor hp tidak boleh kosong");
            } else {
                pst = con.prepareStatement(sql);
                pst.setString(1, IDPembelitxt.getText());
                pst.setString(2, namaPembelitxt.getText());
                pst.setString(3, alamattxt.getText());
                pst.setString(4, noHptxt.getText());

                pst.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) tabelPembeli.getModel();
                model.setRowCount(0);
                fetchDataTabelPembeli();
                IDPembelitxt.setText("");
                namaPembelitxt.setText("");
                alamattxt.setText("");
                noHptxt.setText("");

                JOptionPane.showMessageDialog(null, "Update Selesai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Pilih baris dulu");
        }
    }//GEN-LAST:event_updateBtnPembeliActionPerformed

    private void namaPembelitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaPembelitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaPembelitxtActionPerformed

    private void noHptxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noHptxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noHptxtActionPerformed

    private void refreshBtnPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnPembeliActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelPembeli.getModel();
        model.setRowCount(0);
        fetchDataTabelPembeli();
    }//GEN-LAST:event_refreshBtnPembeliActionPerformed

    private void resetBtnPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnPembeliActionPerformed
        // TODO add your handling code here:
        IDPembelitxt.setText("");
        namaPembelitxt.setText("");
        alamattxt.setText("");
        noHptxt.setText("");
    }//GEN-LAST:event_resetBtnPembeliActionPerformed

    private void tabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangMouseClicked
        // TODO add your handling code here:
        int i = tabelBarang.getSelectedRow();
        TableModel model = tabelBarang.getModel();
        IDBarangtxt.setText(model.getValueAt(i, 0).toString());
        namaProduktxt.setText(model.getValueAt(i, 1).toString());
        HargaJualtxt.setText(model.getValueAt(i, 2).toString());
        jmlbrgkeluartxt.setText(model.getValueAt(i, 3).toString());
        StokBarangtxt.setText(model.getValueAt(i, 4).toString());
    }//GEN-LAST:event_tabelBarangMouseClicked

    private void SaveBtnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnBarangActionPerformed
        // TODO add your handling code here:

        try {
            Connection con = connectionHelper.getConnection();
            //query insert data ke dalam database mysql
            pst = con.prepareStatement("INSERT INTO data_barang (id_barang, idRiwayat_hutang,id_transaksi,id_produk, nama_produk, harga_jual, produk_terjual, stok_barang) VALUES(?,?,?,?,?,?,?,?)");
            //validasi ketika textField kosong

            if (IDBarangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID barang tidak boleh kosong");
            } else if (namaProduktxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nama produk tidak boleh kosong");
            } else if (HargaJualtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "harga jual tidak boleh kosong");
            } else if (jmlbrgkeluartxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "jumlah barang keluar tidak boleh kosong");
            } else if (StokBarangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "stok barang tidak boleh kosong");
            } else {
                //digunakan untuk memasukkan data ke masing2 variabel textfield seperti namaTxt, dll

                //misal bingung pst sama rs bisa diliat di variabel diatas
                pst.setString(1, IDBarangtxt.getText());
                pst.setString(2, IDBarangtxt.getText());
                pst.setString(3, IDBarangtxt.getText());
                pst.setString(4, IDBarangtxt.getText());
                pst.setString(5, namaProduktxt.getText());
                pst.setString(6, HargaJualtxt.getText());
                pst.setString(7, jmlbrgkeluartxt.getText());
                pst.setString(8, StokBarangtxt.getText());
                pst.executeUpdate();

                //setelah nginput data kasih ini biar textfieldnya kosong lagi
                IDBarangtxt.setText("");
                namaProduktxt.setText("");
                HargaJualtxt.setText("");
                jmlbrgkeluartxt.setText("");
                StokBarangtxt.setText("");

                //setelah daftar muncul pop up dafatar berhasil dan akan tampil frame baru
                JOptionPane.showMessageDialog(null, "Input Berhasil");

            }

        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_SaveBtnBarangActionPerformed

    private void deleteBtnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnBarangActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            int row = tabelBarang.getSelectedRow();
            String value = (tabelBarang.getModel().getValueAt(row, 0).toString());
            String sql = "DELETE FROM data_barang WHERE id_barang=" + value;
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
            model.setRowCount(0);
            fetchDataTabelBarang();
            JOptionPane.showMessageDialog(rootPane, "Data terhapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_deleteBtnBarangActionPerformed

    private void updateBtnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnBarangActionPerformed
        // TODO add your handling code here:
        try {

            Connection con = connectionHelper.getConnection();
            int row = tabelBarang.getSelectedRow();
            String value = (tabelBarang.getModel().getValueAt(row, 0).toString());
            String sql = "UPDATE data_barang SET id_barang=?, nama_produk=?, harga_jual=?, produk_terjual=?, stok_barang=? WHERE id_barang=" + value;
            if (IDBarangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID barang tidak boleh kosong");
            } else if (namaProduktxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nama produk tidak boleh kosong");
            } else if (HargaJualtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "harga jual tidak boleh kosong");
            } else if (jmlbrgkeluartxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "jumlah barang keluar tidak boleh kosong");
            } else if (StokBarangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "stok barang tidak boleh kosong");
            } else {
                pst = con.prepareStatement(sql);
                pst.setString(1, IDBarangtxt.getText());
                pst.setString(2, namaProduktxt.getText());
                pst.setString(3, HargaJualtxt.getText());
                pst.setString(4, jmlbrgkeluartxt.getText());
                pst.setString(5, StokBarangtxt.getText());
                pst.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
                model.setRowCount(0);
                fetchDataTabelBarang();
                IDBarangtxt.setText("");
                namaProduktxt.setText("");
                HargaJualtxt.setText("");
                jmlbrgkeluartxt.setText("");
                StokBarangtxt.setText("");
                JOptionPane.showMessageDialog(null, "Update Selesai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Pilih baris dulu");
        }
    }//GEN-LAST:event_updateBtnBarangActionPerformed

    private void namaProduktxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaProduktxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaProduktxtActionPerformed

    private void jmlbrgkeluartxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmlbrgkeluartxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmlbrgkeluartxtActionPerformed

    private void refreshBtnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnBarangActionPerformed
        // TODO add your handling code here: // nama tabel di desain
        DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
        model.setRowCount(0);
        fetchDataTabelBarang();
    }//GEN-LAST:event_refreshBtnBarangActionPerformed

    private void resetBtnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnBarangActionPerformed
        // TODO add your handling code here:
        IDBarangtxt.setText("");
        namaProduktxt.setText("");
        HargaJualtxt.setText("");
        jmlbrgkeluartxt.setText("");
        StokBarangtxt.setText("");
    }//GEN-LAST:event_resetBtnBarangActionPerformed

    private void tabelProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelProdukMouseClicked
        // TODO add your handling code here:
        int i = tabelProduk.getSelectedRow();
        TableModel model = tabelProduk.getModel();
        IDProduktxt.setText(model.getValueAt(i, 0).toString());
        namaProduktxtnew.setText(model.getValueAt(i, 1).toString());
        hargaAslitxt.setText(model.getValueAt(i, 2).toString());
        jmlprodukmasuktxt.setText(model.getValueAt(i, 3).toString());

    }//GEN-LAST:event_tabelProdukMouseClicked

    private void saveBtnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnProdukActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            //query insert data ke dalam database mysql
            pst = con.prepareStatement("INSERT INTO data_produk (id_produk, id_barang, nama_produk,harga_asli,produk_masuk) VALUES(?,?,?,?,?)");
            //validasi ketika textField kosong

            if (IDProduktxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID produk tidak boleh kosong");
            } else if (namaProduktxtnew.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nama produk tidak boleh kosong");
            } else if (hargaAslitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "harga asli tidak boleh kosong");
            } else if (jmlprodukmasuktxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "jumlah produk masuk tidak boleh kosong");
            } else {
                //digunakan untuk memasukkan data ke masing2 variabel textfield seperti namaTxt, dll

                //misal bingung pst sama rs bisa diliat di variabel diatas
                pst.setString(1, IDProduktxt.getText());
                pst.setString(3, namaProduktxtnew.getText());
                pst.setString(4, hargaAslitxt.getText());
                pst.setString(5, jmlprodukmasuktxt.getText());
                pst.setString(2, IDProduktxt.getText());

                pst.executeUpdate();

                //setelah nginput data kasih ini biar textfieldnya kosong lagi
                IDProduktxt.setText("");
                namaProduktxtnew.setText("");
                hargaAslitxt.setText("");
                jmlprodukmasuktxt.setText("");

                //setelah daftar muncul pop up dafatar berhasil dan akan tampil frame baru
                JOptionPane.showMessageDialog(null, "Input Berhasil");

            }

        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveBtnProdukActionPerformed

    private void deleteBtnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnProdukActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            int row = tabelProduk.getSelectedRow();
            String value = (tabelProduk.getModel().getValueAt(row, 0).toString());
            String sql = "DELETE FROM data_produk WHERE id_produk=" + value;
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel) tabelProduk.getModel();
            model.setRowCount(0);
            fetchDataTabelProduk();
            JOptionPane.showMessageDialog(rootPane, "Data terhapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_deleteBtnProdukActionPerformed

    private void updateBtnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnProdukActionPerformed
        // TODO add your handling code here:
        try {

            Connection con = connectionHelper.getConnection();
            int row = tabelProduk.getSelectedRow();
            String value = (tabelProduk.getModel().getValueAt(row, 0).toString());
            String sql = "UPDATE data_produk SET id_produk=?, nama_produk=?, harga_asli=?, produk_masuk=? WHERE id_produk=" + value;
            if (IDProduktxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID produk tidak boleh kosong");
            } else if (namaProduktxtnew.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "nama produk tidak boleh kosong");
            } else if (hargaAslitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "harga asli tidak boleh kosong");
            } else if (jmlprodukmasuktxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "jumlah produk masuk tidak boleh kosong");
            } else {
                pst = con.prepareStatement(sql);
                pst.setString(1, IDProduktxt.getText());
                pst.setString(2, namaProduktxtnew.getText());
                pst.setString(3, hargaAslitxt.getText());
                pst.setString(4, jmlprodukmasuktxt.getText());

                pst.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) tabelProduk.getModel();
                model.setRowCount(0);
                fetchDataTabelProduk();
                IDProduktxt.setText("");
                namaProduktxtnew.setText("");
                hargaAslitxt.setText("");
                jmlprodukmasuktxt.setText("");

                JOptionPane.showMessageDialog(null, "Update Selesai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Pilih baris dulu");
        }
    }//GEN-LAST:event_updateBtnProdukActionPerformed

    private void namaProduktxtnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaProduktxtnewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaProduktxtnewActionPerformed

    private void jmlprodukmasuktxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmlprodukmasuktxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmlprodukmasuktxtActionPerformed

    private void refreshBtnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnProdukActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelProduk.getModel();
        model.setRowCount(0);
        fetchDataTabelProduk();
    }//GEN-LAST:event_refreshBtnProdukActionPerformed

    private void resetBtnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnProdukActionPerformed
        // TODO add your handling code here:
        IDProduktxt.setText("");
        namaProduktxtnew.setText("");
        hargaAslitxt.setText("");
        jmlprodukmasuktxt.setText("");

    }//GEN-LAST:event_resetBtnProdukActionPerformed

    private void tabelRiwayatHutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRiwayatHutangMouseClicked
        // TODO add your handling code here:
        String status_hutang = null;
        if (lunasRadioBtn.isSelected()) {
            status_hutang = "Lunas";
        } else if (belumlunasRadioBtn.isSelected()) {
            status_hutang = "Belum Lunas";
        } else if (belumbyrRadioBtn.isSelected()) {
            status_hutang = "Belum Dibayar";
        }
        int i = tabelRiwayatHutang.getSelectedRow();
        TableModel model = tabelRiwayatHutang.getModel();
        IDRiwayatHutangtxt.setText(model.getValueAt(i, 0).toString());
        Jumlahtxt.setText(model.getValueAt(i, 1).toString());
        sisaHutangtxt.setText(model.getValueAt(i, 4).toString());
        // kurang untuk tanggal pembayaran dan status hutang

    }//GEN-LAST:event_tabelRiwayatHutangMouseClicked

    private void saveBtnHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnHutangActionPerformed
        // TODO add your handling code here:
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String status_hutang = null;
        if (lunasRadioBtn.isSelected()) {
            status_hutang = "Lunas";
        } else if (belumlunasRadioBtn.isSelected()) {
            status_hutang = "Belum Lunas";
        } else if (belumbyrRadioBtn.isSelected()) {
            status_hutang = "Belum Dibayar";
        }
        try {
            Connection con = connectionHelper.getConnection();
            //query insert data ke dalam database mysql, kurang tgl pembelian tipe date
            pst = con.prepareStatement("INSERT INTO datariwayat_hutang (idRiwayat_hutang,id_transaksi,id_barang,id_pembeli,jumlah_hutang,tgl_pembayaran,jumlahSisa_hutang,status_hutang) VALUES(?,?,?,?,?,?,?,?)");
            //validasi ketika textField kosong

            if (IDRiwayatHutangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Riwayat Hutang tidak boleh kosong");
            } else if (Jumlahtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "jumlah hutang tidak boleh kosong");
            } else if (sisaHutangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "sisa hutang tidak boleh kosong");
            } else {
                //digunakan untuk memasukkan data ke masing2 variabel textfield seperti namaTxt, dll

                //misal bingung pst sama rs bisa diliat di variabel diatas
                pst.setString(1, IDRiwayatHutangtxt.getText());
                pst.setString(2, IDRiwayatHutangtxt.getText());
                pst.setString(3, IDRiwayatHutangtxt.getText());
                pst.setString(4, IDRiwayatHutangtxt.getText());
                pst.setString(5, Jumlahtxt.getText());
                pst.setString(6, formatter.format(date));

                pst.setString(7, sisaHutangtxt.getText());
                pst.setString(8, status_hutang);
                pst.executeUpdate();

                //setelah nginput data kasih ini biar textfieldnya kosong lagi
                IDRiwayatHutangtxt.setText("");
                Jumlahtxt.setText("");
                sisaHutangtxt.setText("");

                //setelah daftar muncul pop up dafatar berhasil dan akan tampil frame baru
                JOptionPane.showMessageDialog(null, "Input Berhasil");

            }

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveBtnHutangActionPerformed

    private void deleteBtnHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnHutangActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            int row = tabelRiwayatHutang.getSelectedRow();
            String value = (tabelRiwayatHutang.getModel().getValueAt(row, 0).toString());
            String sql = "DELETE FROM datariwayat_hutang WHERE idRiwayat_hutang=" + value;
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel) tabelRiwayatHutang.getModel();
            model.setRowCount(0);
            fetchDataTabelRiwayatHutang();
            JOptionPane.showMessageDialog(rootPane, "Data terhapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_deleteBtnHutangActionPerformed

    private void updateBtnHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnHutangActionPerformed
        // TODO add your handling code here:
         String status_hutang = null;
        if (lunasRadioBtn.isSelected()) {
            status_hutang = "Lunas";
        } else if (belumlunasRadioBtn.isSelected()) {
            status_hutang = "Belum Lunas";
        } else if (belumbyrRadioBtn.isSelected()) {
            status_hutang = "Belum Dibayar";
        }
        try {

            Connection con = connectionHelper.getConnection();
            int row = tabelRiwayatHutang.getSelectedRow();
            String value = (tabelRiwayatHutang.getModel().getValueAt(row, 0).toString());
            String sql = "UPDATE datariwayat_hutang SET idRiwayat_hutang=?, jumlah_hutang=?, jumlahSisa_hutang=?, status_hutang =? WHERE idRiwayat_hutang=" + value;
            if (IDRiwayatHutangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Riwayat Hutang tidak boleh kosong");
            } else if (Jumlahtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah Hutang tidak boleh kosong");
            } else if (sisaHutangtxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sisa Hutang tidak boleh kosong");
            } else {
                pst = con.prepareStatement(sql);
                pst.setString(1, IDRiwayatHutangtxt.getText());
                pst.setString(2, Jumlahtxt.getText());
                pst.setString(3, sisaHutangtxt.getText());
                 pst.setString(4, status_hutang);
                pst.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) tabelRiwayatHutang.getModel();
                model.setRowCount(0);
                fetchDataTabelRiwayatHutang();
                IDRiwayatHutangtxt.setText("");
                Jumlahtxt.setText("");
                sisaHutangtxt.setText("");

                JOptionPane.showMessageDialog(null, "Update Selesai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_updateBtnHutangActionPerformed

    private void JumlahtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JumlahtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahtxtActionPerformed

    private void refreshBtnHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnHutangActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelRiwayatHutang.getModel();
        model.setRowCount(0);
        fetchDataTabelRiwayatHutang();
    }//GEN-LAST:event_refreshBtnHutangActionPerformed

    private void resetBtnHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnHutangActionPerformed
        // TODO add your handling code here:
        IDRiwayatHutangtxt.setText("");
        Jumlahtxt.setText("");
        sisaHutangtxt.setText("");

    }//GEN-LAST:event_resetBtnHutangActionPerformed

    private void lunasRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunasRadioBtnActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_lunasRadioBtnActionPerformed

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        // TODO add your handling code here:

        int i = tabelTransaksi.getSelectedRow();
        TableModel model = tabelTransaksi.getModel();
        IDTransaksitxt.setText(model.getValueAt(i, 0).toString());
        jmlTransaksitxt.setText(model.getValueAt(i, 1).toString());
        // kurang untuk tanggal pembayaran


    }//GEN-LAST:event_tabelTransaksiMouseClicked

    private void saveBtnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnTransaksiActionPerformed
        // TODO add your handling code here:
        // copy 4
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Connection con = connectionHelper.getConnection();
            //query insert data ke dalam database mysql, kurang tgl pembelian tipe date
            pst = con.prepareStatement("INSERT INTO data_transaksi (id_transaksi,id_barang,idRiwayat_hutang,id_pembeli,jumlah_pembayaran,tgl_pembayaran) VALUES(?,?,?,?,?,CURRENT_DATE)");
            //validasi ketika textField kosong

            if (IDTransaksitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Transaksi tidak boleh kosong");
            } else if (jmlTransaksitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah Transaksi tidak boleh kosong");
            } else {
                //digunakan untuk memasukkan data ke masing2 variabel textfield seperti namaTxt, dll

                //misal bingung pst sama rs bisa diliat di variabel diatas
                pst.setString(1, IDTransaksitxt.getText());
                pst.setString(2, IDTransaksitxt.getText());
                pst.setString(3, IDTransaksitxt.getText());
                pst.setString(4, IDTransaksitxt.getText());
                pst.setString(5, jmlTransaksitxt.getText());

                pst.executeUpdate();

                //setelah nginput data kasih ini biar textfieldnya kosong lagi
                IDTransaksitxt.setText("");
                jmlTransaksitxt.setText("");

                //setelah daftar muncul pop up dafatar berhasil dan akan tampil frame baru
                JOptionPane.showMessageDialog(null, "Input Berhasil");

            }

        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveBtnTransaksiActionPerformed

    private void deleteBtnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnTransaksiActionPerformed
        // TODO add your handling code here:
        try {
            Connection con = connectionHelper.getConnection();
            int row = tabelTransaksi.getSelectedRow();
            String value = (tabelTransaksi.getModel().getValueAt(row, 0).toString());
            String sql = "DELETE FROM data_transaksi WHERE id_transaksi=" + value;
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel) tabelTransaksi.getModel();
            model.setRowCount(0);
            fetchDataTransaksi();
            JOptionPane.showMessageDialog(rootPane, "Data terhapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_deleteBtnTransaksiActionPerformed

    private void updateBtnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnTransaksiActionPerformed
        // TODO add your handling code here:
        try {

            Connection con = connectionHelper.getConnection();
            int row = tabelTransaksi.getSelectedRow();
            String value = (tabelTransaksi.getModel().getValueAt(row, 0).toString());
            String sql = "UPDATE data_transaksi SET id_transaksi=?,jumlah_pembayaran=? WHERE id_transaksi=" + value;
            if (IDTransaksitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID transaksi tidak boleh kosong");
            } else if (jmlTransaksitxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "jumlah transaksi tidak boleh kosong");
            } else {
                pst = con.prepareStatement(sql);
                pst.setString(1, IDTransaksitxt.getText());
                pst.setString(2, jmlTransaksitxt.getText());

                pst.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) tabelTransaksi.getModel();
                model.setRowCount(0);
                fetchDataTransaksi();
                IDTransaksitxt.setText("");
                jmlTransaksitxt.setText("");

                JOptionPane.showMessageDialog(null, "Update Selesai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Pilih baris dulu");
        }
    }//GEN-LAST:event_updateBtnTransaksiActionPerformed

    private void jmlTransaksitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmlTransaksitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmlTransaksitxtActionPerformed

    private void refreshBtnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnTransaksiActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelTransaksi.getModel();
        model.setRowCount(0);
        fetchDataTransaksi();
    }//GEN-LAST:event_refreshBtnTransaksiActionPerformed

    private void resetBtnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetBtnTransaksiActionPerformed

    private void belumlunasRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_belumlunasRadioBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_belumlunasRadioBtnActionPerformed

    private void transaksiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiBtnActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnlCard, "pnlCard6");
    }//GEN-LAST:event_transaksiBtnActionPerformed

    private void pnlCardTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCardTransaksiMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_pnlCardTransaksiMouseClicked

    private void transaksiBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiBtnMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_transaksiBtnMouseClicked

    private void LogoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutBtnMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_LogoutBtnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField HargaJualtxt;
    private javax.swing.JTextField IDBarangtxt;
    private javax.swing.JTextField IDPembelitxt;
    private javax.swing.JTextField IDProduktxt;
    private javax.swing.JTextField IDRiwayatHutangtxt;
    private javax.swing.JTextField IDTransaksitxt;
    private javax.swing.JScrollPane JScrollPane4;
    private javax.swing.JTextField Jumlahtxt;
    private javax.swing.JButton LogoutBtn;
    private javax.swing.JButton SaveBtnBarang;
    private javax.swing.JTextField StokBarangtxt;
    private javax.swing.JTextField alamattxt;
    private javax.swing.JButton barangBtn;
    private javax.swing.JRadioButton belumbyrRadioBtn;
    private javax.swing.JRadioButton belumlunasRadioBtn;
    private javax.swing.JButton dashboardBtn;
    private javax.swing.JButton deleteBtnBarang;
    private javax.swing.JButton deleteBtnHutang;
    private javax.swing.JButton deleteBtnPembeli;
    private javax.swing.JButton deleteBtnProduk;
    private javax.swing.JButton deleteBtnTransaksi;
    private javax.swing.JTextField hargaAslitxt;
    private javax.swing.JButton hutangBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jmlTransaksitxt;
    private javax.swing.JLabel jml_pelanggan1;
    private javax.swing.JLabel jml_pelanggan4;
    private javax.swing.JLabel jml_pelanggan5;
    private javax.swing.JTextField jmlbrgkeluartxt;
    private javax.swing.JTextField jmlprodukmasuktxt;
    private javax.swing.JLabel judulLabel1;
    private javax.swing.JRadioButton lunasRadioBtn;
    private javax.swing.JTextField namaPembelitxt;
    private javax.swing.JTextField namaProduktxt;
    private javax.swing.JTextField namaProduktxtnew;
    private javax.swing.JTextField noHptxt;
    private javax.swing.JButton pembeliBtn;
    private javax.swing.JPanel pnlCard;
    private javax.swing.JPanel pnlCardBarang;
    private javax.swing.JPanel pnlCardDashboard;
    private javax.swing.JPanel pnlCardHutang;
    private javax.swing.JPanel pnlCardPembeli;
    private javax.swing.JPanel pnlCardProduk;
    private javax.swing.JPanel pnlCardTransaksi;
    private javax.swing.JButton produkBtn;
    private javax.swing.JButton refreshBtnBarang;
    private javax.swing.JButton refreshBtnHutang;
    private javax.swing.JButton refreshBtnPembeli;
    private javax.swing.JButton refreshBtnProduk;
    private javax.swing.JButton refreshBtnTransaksi;
    private javax.swing.JButton resetBtnBarang;
    private javax.swing.JButton resetBtnHutang;
    private javax.swing.JButton resetBtnPembeli;
    private javax.swing.JButton resetBtnProduk;
    private javax.swing.JButton resetBtnTransaksi;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JPanel rootPanel1;
    private javax.swing.JPanel rootPanel4;
    private javax.swing.JPanel rootPanel5;
    private javax.swing.JPanel rootPanel6;
    private javax.swing.JButton saveBtnHutang;
    private javax.swing.JButton saveBtnPembeli;
    private javax.swing.JButton saveBtnProduk;
    private javax.swing.JButton saveBtnTransaksi;
    private javax.swing.JTextField sisaHutangtxt;
    private javax.swing.JTable tabelBarang;
    private javax.swing.JTable tabelPembeli;
    private javax.swing.JTable tabelProduk;
    private javax.swing.JTable tabelRiwayatHutang;
    private javax.swing.JTable tabelTransaksi;
    private javax.swing.JLabel totalHutang;
    private javax.swing.JLabel totalPenghutang;
    private javax.swing.JButton transaksiBtn;
    private javax.swing.JButton updateBtnBarang;
    private javax.swing.JButton updateBtnHutang;
    private javax.swing.JButton updateBtnPembeli;
    private javax.swing.JButton updateBtnProduk;
    private javax.swing.JButton updateBtnTransaksi;
    // End of variables declaration//GEN-END:variables
}
