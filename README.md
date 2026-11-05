# App Name: nyemil_kuy

## Deskripsi Aplikasi

nyemil_kuy adalah aplikasi pemesanan makanan dan minuman (kopi dan camilan) berbasis Android dengan fitur sederhana dirancang dengan antarmuka pengguna yang modern, bersih, dan minimalis menggunakan tema warna hijau dan putih. Pengembangan aplikasi ini diarahkan pada penerapan User Interface (UI) yang interaktif serta alur navigasi aplikasi yang mulus dari tahap autentikasi sampai pada konfirmasi pemesanan dalam pengantaran. Aplikasi ini menggunakan arsitektur data lokal (Single List code pada kotlin) untuk menyimulasikan fungsionalitas aplikasi secara dinamis tanpa membeli atau mengintergrasikan sebuah database atau server.

## Alur Penggunaan dan Fitur Aplikasi

Aplikasi nyemil_kuy memiliki alur yang saling terhubung secara fungsional. Berikut adalah penjelasan detail mengenai fitur dan alur penggunaannya:

1. **Splash Screen**
   Layar pembuka aplikasi yang menampilkan logo dan identitas visual aplikasi sebelum pengguna masuk ke menu utama.
   <br>
   <img width="250" alt="splash" src="https://github.com/user-attachments/assets/1da01114-3bcc-4845-9b8c-9514060ecb47" />
   
2. **Autentikasi Pengguna (Welcome, Register, dan Login)**
   Pengguna baru akan diarahkan ke halaman Welcome/Sign Up. Pengguna dapat mendaftarkan akun baru melalui halaman Register dengan mengisi nama lengkap, username, dan kata sandi. Setelah berhasil mendaftar, data akan disimpan sementara di dalam memori aplikasi, dan pengguna dapat masuk melalui halaman Login menggunakan kredensial yang telah didaftarkan.
   
   *Layar Pilihan Login atau Register | Pendaftaran (Register) | Masuk (Login)*
   <br>
   <img width="250" alt="signup" src="https://github.com/user-attachments/assets/c88b8e59-b917-4474-b5df-f005fa064369" /> <img width="250" alt="register" src="https://github.com/user-attachments/assets/c0b4779e-31f9-492c-97c3-4f350862cbfa" /> <img width="250" alt="login" src="https://github.com/user-attachments/assets/4dba4441-7488-4866-b111-4f991f30c882" />

3. **Halaman Utama (Home)**
   Setelah berhasil masuk, pengguna akan melihat halaman utama yang menampilkan salam sapaan beserta nama pengguna. Halaman ini memuat daftar produk (makanan dan minuman) dalam bentuk Grid (dua kolom). Setiap produk dilengkapi dengan gambar, deskripsi bahan, harga, dan tombol untuk menambahkan item ke dalam keranjang.
   <br>
   <img width="250" alt="home" src="https://github.com/user-attachments/assets/fc46e9d7-d374-4dee-8667-90955a883787" />

4. **Keranjang Belanja (Cart)**
   Ketika pengguna menekan tombol tambah pada produk atau mengakses menu Order pada navigasi bawah, aplikasi akan memunculkan keranjang belanja dalam bentuk Bottom Sheet (panel yang muncul dari bawah layar). Di sini, pengguna dapat mengatur jumlah item (tambah/kurang). Sistem akan secara otomatis (real-time) menghitung Subtotal, Ongkos Kirim, Pajak, dan Total tagihan.
   <br>
   <img width="250" alt="cart" src="https://github.com/user-attachments/assets/097efa19-bf31-4efb-a284-87b3340f8efa" />

5. **Form Pengiriman (Order)**
   Setelah pengguna menekan tombol proses pembayaran (Proceed Checkout), aplikasi akan mengarahkan pengguna ke halaman pengiriman. Pada halaman ini, pengguna diwajibkan untuk memverifikasi nama lengkap, memasukkan alamat lengkap pengiriman, dan menambahkan detail lokasi atau catatan khusus untuk kurir.
   <br>
   <img width="250" alt="order" src="https://github.com/user-attachments/assets/76a7a56a-dfad-4bcf-9c5e-b812d09d639d" />

6. **Konfirmasi Pesanan (Success)**
   Setelah formulir pengiriman diserahkan, pengguna akan menerima layar konfirmasi pesanan sukses. Layar ini menampilkan ucapan terima kasih dan instruksi bahwa pesanan sedang diproses dan akan segera diantarkan oleh kurir. Tombol konfirmasi pada halaman ini akan mengembalikan pengguna ke halaman utama dan mengosongkan isi keranjang.
   <br>
   <img width="250" alt="success" src="https://github.com/user-attachments/assets/9d2243a0-a003-4254-b897-49aa6282b306" />

7. **Manajemen Profil (Profile)**
   Pengguna dapat mengakses halaman profil melalui navigasi bawah. Halaman profil menampilkan status pesanan yang sedang aktif secara langsung (real-time summary). Selain itu, pengguna diberikan akses untuk memperbarui atau mengubah nama akun yang sedang digunakan, yang kemudian akan diperbarui ke seluruh antarmuka aplikasi.
   
   *Layar Profil Pengguna | Layar yang sudah diubah Nama Profil*
   <br>
   <img width="250" alt="profile" src="https://github.com/user-attachments/assets/2feac7be-dd48-43ec-9458-ffbf6e770dde" /> <img width="250" alt="chnage name" src="https://github.com/user-attachments/assets/ba74a38c-cfa0-4b35-beaf-064be7b5b065" />


## Teknologi yang Digunakan

- Bahasa Pemrograman: Kotlin
- Antarmuka Pengguna: XML Layouts (Material Design Components, Custom Shape Drawables), untuk penyimpanan foto dan aset lainnya
- Navigasi dan Tampilan: RecyclerView, LinearLayoutManager, dan BottomSheetDialogFragment
- Penghubung Tampilan: ViewBinding
- Manajemen Data: Kotlin Singleton Object (penyimpanan data sementara dalam siklus hidup aplikasi)

## Mekanisme Autentikasi Tanpa Database (Simulasi)

Aplikasi ini didesain sebagai purwarupa (prototype) yang berfokus pada kelancaran antarmuka dan alur pengguna. Oleh karena itu, aplikasi tidak terhubung dengan basis data eksternal seperti SQLite atau Firebase.

Fungsi pendaftaran (Register) dan masuk (Login) disimulasikan melalui logika validasi dasar dan penyimpanan sesi menggunakan variabel global di dalam struktur `object` (Singleton) bawaan Kotlin. Berikut adalah penjabaran teknisnya:

1. Penyimpanan Sesi Pengguna
   Aplikasi menggunakan objek statis bernama `UserManager` untuk menyimpan nama pengguna yang sedang aktif. Data ini tersimpan di memori (RAM) selama aplikasi berjalan.

```kotlin
object UserManager {
    var userName: String = "Pelanggan"
}
