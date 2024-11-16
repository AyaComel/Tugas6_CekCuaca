# Tugas6_CekCuaca
 Tugas 6 - Cahya Hayyuni 2210010118

# 1. Deskripsi Program
Aplikasi ini memungkinkan pengguna untuk:
• Integrasi dengan API cuaca eksternal (misalnya OpenWeatherMap) untuk mendapatkan data cuaca secara real-time

• Tampilkan data cuaca dalam bentuk gambar berdasarkan kondisi cuaca (cerah, berawan, hujan, dan sebagainya)

• Menyimpan data cuaca dalam format CSV untuk digunakan di masa mendatang.

# 2. Komponen GUI: JFrame, JPanel, JLabel, JTextField, JButton, JComboBox
- JFrame: Window utama aplikasi.
- JPanel: Panel untuk menampung komponen.
- JTextField untuk input nama kota.
- JComboBox untuk menampilkan daftar lokasi favorit.
- JButton untuk menghapus.
- JButton untuk keluar.
- JButton untuk tombol "Cek Cuaca".
- JButton untuk menyimpan lokasi favorit.
- JButton untuk mengekspor data ke CSV.
- JButton untuk memuat data dari CSV ke tabel.
- JLabel untuk menampilkan ikon cuaca.
- JTable untuk menampilkan data cuaca yang tersimpan di file CSV.
   
# 3. Logika Program: API eksternal, penampilan gambar
- Koneksi API: Buat fungsi untuk mengambil data dari API OpenWeatherMap berdasarkan kota yang dimasukkan.
- Parsing JSON: Ekstrak kondisi cuaca dari data JSON untuk menampilkan status (cerah, berawan, hujan, dll.).
- Menyimpan data cuaca ke dalam file CSV, sehingga pengguna dapat melihat data cuaca yang tersimpan di masa lalu.

# 4. Events:
Menggunakan ActionListener dan ItemListener untuk menangani interaksi pengguna:
# a. ActionListener untuk tombol Cek Cuaca
Mengambil data cuaca berdasarkan nama kota yang dimasukkan dan menampilkan informasi cuaca pada aplikasi.
~~~
private void buttonCekActionPerformed(java.awt.event.ActionEvent evt) {                                          
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
    }
~~~
# b. ItemListener pada JComboBox untuk memilih lokasi cuaca
Mengambil data cuaca untuk kota yang dipilih dari JComboBox dan menampilkannya di aplikasi.
~~~
private void comboBoxKotaItemStateChanged(java.awt.event.ItemEvent evt) {                                                     
       if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboBoxDaftarKota.getSelectedItem();
            textNamaKota.setText(selectedCity); // Mengisi txtCityName dengan kota yang dipilih
        }
    }
~~~
# 5. Variasi:
Aplikasi ini beberapa macam variasi tambahan :

# Daftar lokasi favorit
sehingga dapat dipilih dengan cepat dari JComboBox
~~~
private void buttonCekActionPerformed(java.awt.event.ActionEvent evt) {
...             
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

private void buttonSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                             
    // Action untuk tombol "Simpan ke Favorit"
        String cityName = textNamaKota.getText();
        String weatherDescription = labelDeskripsi.getText();
        String temperature = labelTemperatur.getText();

        if (!cityName.isEmpty() && !weatherDescription.isEmpty() && !temperature.isEmpty()) {
            simpanDataCuacaKeFile(cityName, weatherDescription, Double.parseDouble(temperature.replace("°C", "")));
        } else {
            JOptionPane.showMessageDialog(this, "Data cuaca tidak lengkap. Pastikan semua data sudah terisi.");
        }
    }         

~~~
# Tombol menyimpan data dari tabel ke dalam file CSV atau teks 
agar data cuaca dapat diakses kembali
~~~
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
~~~
~~~ 
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
~~~
# fitur untuk memuat data cuaca yang tersimpan dan menampilkannya di JTable.
~~~
 private void buttonDataActionPerformed(java.awt.event.ActionEvent evt) {                                           
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
    }   
~~~

# 6. Contoh Gambar pada saat di Run
![](https://github.com/AyaComel/Tugas6_CekCuaca/blob/main/Tugas6.png)

## Indikator Penilaian:

| No  | Komponen         |  Persentase  |
| :-: | --------------   |   :-----:    |
|  1  | Komponen GUI     |    10    |
|  2  | Logika Program   |    20    |
|  3  |  Events          |    10    |
|  4  | Kesesuaian UI    |    20    |
|  5  | Memenuhi Variasi |    40    |
|     | TOTAL        | 100 |

# Pembuat
Nama     : Cahya Hayyuni
NPM      : 2210010118
