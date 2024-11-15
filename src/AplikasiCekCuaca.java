import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;

public class AplikasiCekCuaca extends javax.swing.JFrame {
    private static final String API_KEY = "d264aa1e7cc4af37485ac03173e15d6f"; // Hanya kunci API saja
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String CITY_FILE_PATH = "data/cities.txt";
    private static final String WEATHER_FILE_PATH = "data/weather_data.csv";
    private DefaultTableModel tableModel;

    // Fungsi untuk mengambil data cuaca dari API
    public String dapatkanDataCuaca(String cityName) {
        String response = "";
        try {
            // Buat URL lengkap dengan memasukkan cityName, API_KEY, dan bahasa (lang=id)
            String urlString = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric&lang=id";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public void menampilkanCuaca(String cityName) throws JSONException {
        // Dapatkan data cuaca dalam bentuk JSON
        String jsonData = dapatkanDataCuaca(cityName);
        JSONObject jsonObjek = new JSONObject(jsonData);

        // Ambil deskripsi cuaca dan suhu dari JSON
        String deskripsiCuaca = jsonObjek.getJSONArray("weather").getJSONObject(0).getString("description");
        String kodeIkon = jsonObjek.getJSONArray("weather").getJSONObject(0).getString("icon"); // Point 1: Dapatkan kode ikon cuaca
        double suhu = jsonObjek.getJSONObject("main").getDouble("temp");

        // Set teks deskripsi cuaca dan suhu ke JLabel
        labelDeskripsi.setText(deskripsiCuaca);
        labelTemperatur.setText(suhu + "°C");

        // Buat URL untuk ikon cuaca berdasarkan kode ikon
        String urlIkon = "http://openweathermap.org/img/wn/" + kodeIkon + "@2x.png";

        try {
            // Load ikon dari URL dan set ke JLabel
            ImageIcon ikonCuaca = new ImageIcon(new URL(urlIkon));
            labelIconCuaca.setIcon(ikonCuaca);
        } catch (Exception e) {
            e.printStackTrace();
            labelIconCuaca.setText("Gagal memuat ikon cuaca");
        }
    }
    
    private void simpanKotaLokasiKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cities.txt"))) {
            for (int i = 0; i < comboBoxDaftarKota.getItemCount(); i++) {
                writer.write(comboBoxDaftarKota.getItemAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     private void muatKotaLokasiKeFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cities.txt"))) {
            String city;
            while ((city = reader.readLine()) != null) {
                comboBoxDaftarKota.addItem(city);
            }
        } catch (IOException e) {
            // Jika file tidak ditemukan, abaikan saja
            System.out.println("No saved cities found.");
        }
    }
     
      private void simpanDataCuacaKeFile(String cityName, String weatherDescription, double temperature) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("weather_data.csv", true))) {
            // Menuliskan data dalam format CSV
            writer.write(cityName + "," + weatherDescription + "," + temperature + "°C");
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Data cuaca berhasil disimpan!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data cuaca.");
            e.printStackTrace();
        }
    }
      
      // Metode untuk menghapus kota yang dipilih dari ComboBox dan menyimpannya ke file
private void hapusKotaLokasi() {
    String selectedCity = (String) comboBoxDaftarKota.getSelectedItem();
    if (selectedCity != null) {
        comboBoxDaftarKota.removeItem(selectedCity);
        simpanKotaLokasiKeFile(); // Simpan perubahan setelah kota dihapus
        JOptionPane.showMessageDialog(this, "Kota " + selectedCity + " berhasil dihapus dari daftar favorit.");
    } else {
        JOptionPane.showMessageDialog(this, "Tidak ada kota yang dipilih untuk dihapus.");
    }
}
    
    public AplikasiCekCuaca() {
        initComponents();
        System.out.println("C:\\Users\\Asus\\OneDrive\\Documents\\Tugas6_CekCuaca\\Simpan " + System.getProperty("user.dir"));
        muatKotaLokasiKeFile();

    // Inisialisasi model tabel
        tableModel = new DefaultTableModel(new String[]{"Nama Kota", "Cuaca", "Suhu"}, 0);
        tabelCuaca.setModel(tableModel);     
    }

    public void itemStateChanged(java.awt.event.ItemEvent evt) throws JSONException {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboBoxDaftarKota.getSelectedItem();
            menampilkanCuaca(selectedCity);  // Panggil metode untuk menampilkan cuaca kota yang dipilih
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textNamaKota = new javax.swing.JTextField();
        comboBoxDaftarKota = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        buttonCekCuaca = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        buttonSimpan = new javax.swing.JButton();
        buttonData = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelCuaca = new javax.swing.JTable();
        buttonKeluar = new javax.swing.JButton();
        labelDeskripsi = new javax.swing.JLabel();
        labelTemperatur = new javax.swing.JLabel();
        labelIconCuaca = new javax.swing.JLabel();
        buttonHapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Nama Kota :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Daftar Kota :");

        textNamaKota.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        comboBoxDaftarKota.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Icon Cuaca  :");

        buttonCekCuaca.setBackground(new java.awt.Color(204, 255, 204));
        buttonCekCuaca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonCekCuaca.setText("Cek Cuaca");
        buttonCekCuaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCekCuacaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Deskripsi Cuaca :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Temperatur Suhu :");

        buttonSimpan.setBackground(new java.awt.Color(204, 255, 204));
        buttonSimpan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonSimpan.setText("Simpan Lokasi");
        buttonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpanActionPerformed(evt);
            }
        });

        buttonData.setBackground(new java.awt.Color(204, 255, 204));
        buttonData.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonData.setText("Muat Data Cuaca");
        buttonData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDataActionPerformed(evt);
            }
        });

        tabelCuaca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelCuaca);

        buttonKeluar.setBackground(new java.awt.Color(204, 255, 204));
        buttonKeluar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonKeluar.setText("KELUAR");
        buttonKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKeluarActionPerformed(evt);
            }
        });

        labelDeskripsi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        labelTemperatur.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        labelIconCuaca.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        buttonHapus.setBackground(new java.awt.Color(204, 255, 204));
        buttonHapus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonHapus.setText("Hapus");
        buttonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonSimpan)
                                .addGap(63, 63, 63)
                                .addComponent(buttonData))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textNamaKota, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBoxDaftarKota, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(45, 45, 45)
                                        .addComponent(labelIconCuaca, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(buttonCekCuaca))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(240, 240, 240)
                                        .addComponent(buttonHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(2, 2, 2)
                                .addComponent(labelDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTemperatur, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textNamaKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(comboBoxDaftarKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCekCuaca, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelIconCuaca)
                    .addComponent(buttonHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(labelDeskripsi))
                    .addComponent(labelTemperatur, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 153, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("APLIKASI CEK CUACA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(jLabel1)
                .addContainerGap(350, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCekCuacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCekCuacaActionPerformed
    // Action untuk tombol "Cek Cuaca"
     String cityName = textNamaKota.getText();
        if (!cityName.isEmpty()) {
         try {
             menampilkanCuaca(cityName);
             
            boolean exists = false;
                for (int i = 0; i < comboBoxDaftarKota.getItemCount(); i++) {
                if (comboBoxDaftarKota.getItemAt(i).equalsIgnoreCase(cityName)) {
                    exists = true;
                    break;
                }
             }
             
             if (!exists) {
                 comboBoxDaftarKota.addItem(cityName);
                 simpanKotaLokasiKeFile(); // Simpan kota ke file setelah penambahan
             }
         } catch (JSONException ex) {
             Logger.getLogger(AplikasiCekCuaca.class.getName()).log(Level.SEVERE, null, ex);
         }
        } else {
            JOptionPane.showMessageDialog(this, "Silakan masukkan nama kota!");
        }
    }//GEN-LAST:event_buttonCekCuacaActionPerformed
    private void comboBoxKotaFavoritItemStateChanged(java.awt.event.ItemEvent evt) {                                                     
       if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboBoxDaftarKota.getSelectedItem();
            textNamaKota.setText(selectedCity); // Mengisi txtCityName dengan kota yang dipilih
        }
    }                                              

    private void buttonDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDataActionPerformed
    tableModel.setRowCount(0); // Hapus data lama di tabel sebelum memuat data baru

        try (BufferedReader reader = new BufferedReader(new FileReader("weather_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    // Menambahkan data ke dalam tabel
                    tableModel.addRow(new Object[]{data[0], data[1], data[2]});
                }
            }
            JOptionPane.showMessageDialog(this, "Data cuaca berhasil dimuat ke tabel.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data cuaca.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_buttonDataActionPerformed

    private void buttonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanActionPerformed
    // Action untuk tombol "Simpan ke Favorit"
        String cityName = textNamaKota.getText();
        String weatherDescription = labelDeskripsi.getText();
        String temperature = labelTemperatur.getText();

        if (!cityName.isEmpty() && !weatherDescription.isEmpty() && !temperature.isEmpty()) {
            simpanDataCuacaKeFile(cityName, weatherDescription, Double.parseDouble(temperature.replace("°C", "")));
        } else {
            JOptionPane.showMessageDialog(this, "Data cuaca tidak lengkap. Pastikan semua data sudah terisi.");
        }
    }//GEN-LAST:event_buttonSimpanActionPerformed

    private void buttonKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKeluarActionPerformed
    // Tambahkan action listener untuk tombol keluar
    buttonKeluar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0); // Menutup aplikasi
        }
    });
    }//GEN-LAST:event_buttonKeluarActionPerformed

    private void buttonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapusActionPerformed
    // Tambahkan action listener untuk tombol hapus
    buttonHapus.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            hapusKotaLokasi();
        }
    });
    }//GEN-LAST:event_buttonHapusActionPerformed

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
            java.util.logging.Logger.getLogger(AplikasiCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplikasiCekCuaca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCekCuaca;
    private javax.swing.JButton buttonData;
    private javax.swing.JButton buttonHapus;
    private javax.swing.JButton buttonKeluar;
    private javax.swing.JButton buttonSimpan;
    private javax.swing.JComboBox<String> comboBoxDaftarKota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDeskripsi;
    private javax.swing.JLabel labelIconCuaca;
    private javax.swing.JLabel labelTemperatur;
    private javax.swing.JTable tabelCuaca;
    private javax.swing.JTextField textNamaKota;
    // End of variables declaration//GEN-END:variables
}
