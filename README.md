# nyemil_kuy

## Deskripsi Aplikasi

nyemil_kuy adalah aplikasi pemesanan makanan dan minuman (kopi dan camilan) berbasis Android. Aplikasi ini dirancang dengan antarmuka pengguna yang modern, bersih, dan minimalis menggunakan tema warna hijau dan putih. Pengembangan aplikasi ini difokuskan pada penerapan User Interface (UI) dan User Experience (UX) yang interaktif serta alur navigasi aplikasi yang mulus dari tahap autentikasi hingga konfirmasi pemesanan. Aplikasi ini menggunakan arsitektur data lokal (Single List/Object pada Kotlin) untuk menyimulasikan fungsionalitas aplikasi secara dinamis tanpa memerlukan integrasi basis data eksternal.

## Alur Penggunaan dan Fitur Aplikasi

Aplikasi nyemil_kuy memiliki alur yang saling terhubung secara fungsional. Berikut adalah penjelasan detail mengenai fitur dan alur penggunaannya:

1. Splash Screen
   Layar pembuka aplikasi yang menampilkan logo dan identitas visual aplikasi sebelum pengguna masuk ke menu utama.

2. Autentikasi Pengguna (Welcome, Register, dan Login)
   Pengguna baru akan diarahkan ke halaman Welcome/Sign Up. Pengguna dapat mendaftarkan akun baru melalui halaman Register dengan mengisi nama lengkap, username, dan kata sandi. Setelah berhasil mendaftar, data akan disimpan sementara di dalam memori aplikasi, dan pengguna dapat masuk melalui halaman Login menggunakan kredensial yang telah didaftarkan.

3. Halaman Utama (Home)
   Setelah berhasil masuk, pengguna akan melihat halaman utama yang menampilkan salam sapaan beserta nama pengguna. Halaman ini memuat daftar produk (makanan dan minuman) dalam bentuk Grid (dua kolom). Setiap produk dilengkapi dengan gambar, deskripsi bahan, harga, dan tombol untuk menambahkan item ke dalam keranjang.

4. Keranjang Belanja (Cart)
   Ketika pengguna menekan tombol tambah pada produk atau mengakses menu Order pada navigasi bawah, aplikasi akan memunculkan keranjang belanja dalam bentuk Bottom Sheet (panel yang muncul dari bawah layar). Di sini, pengguna dapat mengatur jumlah item (tambah/kurang). Sistem akan secara otomatis (real-time) menghitung Subtotal, Ongkos Kirim, Pajak, dan Total tagihan.

5. Form Pengiriman (Order)
   Setelah pengguna menekan tombol proses pembayaran (Proceed Checkout), aplikasi akan mengarahkan pengguna ke halaman pengiriman. Pada halaman ini, pengguna diwajibkan untuk memverifikasi nama lengkap, memasukkan alamat lengkap pengiriman, dan menambahkan detail lokasi atau catatan khusus untuk kurir.

6. Konfirmasi Pesanan (Success)
   Setelah formulir pengiriman diserahkan, pengguna akan menerima layar konfirmasi pesanan sukses. Layar ini menampilkan ucapan terima kasih dan instruksi bahwa pesanan sedang diproses dan akan segera diantarkan oleh kurir. Tombol konfirmasi pada halaman ini akan mengembalikan pengguna ke halaman utama dan mengosongkan isi keranjang.

7. Manajemen Profil (Profile)
   Pengguna dapat mengakses halaman profil melalui navigasi bawah. Halaman profil menampilkan status pesanan yang sedang aktif secara langsung (real-time summary). Selain itu, pengguna diberikan akses untuk memperbarui atau mengubah nama akun yang sedang digunakan, yang kemudian akan diperbarui ke seluruh antarmuka aplikasi.

## Teknologi yang Digunakan

* Bahasa Pemrograman: Kotlin

* Antarmuka Pengguna: XML Layouts (Material Design Components, Custom Shape Drawables)

* Navigasi dan Tampilan: RecyclerView, LinearLayoutManager, dan BottomSheetDialogFragment

* Penghubung Tampilan: ViewBinding

* Manajemen Data: Kotlin Singleton Object (penyimpanan data sementara dalam siklus hidup aplikasi)

## Mekanisme Autentikasi Tanpa Database (Simulasi)

Aplikasi ini didesain sebagai purwarupa (prototype) yang berfokus pada kelancaran antarmuka dan alur pengguna. Oleh karena itu, aplikasi tidak terhubung dengan basis data eksternal seperti SQLite atau Firebase.

Fungsi pendaftaran (Register) dan masuk (Login) disimulasikan melalui logika validasi dasar dan penyimpanan sesi menggunakan variabel global di dalam struktur `object` (Singleton) bawaan Kotlin. Berikut adalah penjabaran teknisnya:

1. Penyimpanan Sesi Pengguna
   Aplikasi menggunakan objek statis bernama `UserManager` untuk menyimpan nama pengguna yang sedang aktif. Data ini tersimpan di memori (RAM) selama aplikasi berjalan.

```
object UserManager {
    var userName: String = "Pelanggan"
}

```

2. Logika Pendaftaran (RegisterActivity)
   Sistem mengekstraksi input pengguna dan melakukan validasi sederhana. Jika kolom terisi, nama pengguna langsung disimpan ke dalam objek Singleton sebagai penanda sesi aktif.

```
val username = binding.etRegUsername.text.toString().trim()
val password = binding.etRegPassword.text.toString().trim()

// Validasi keberadaan data
if (username.isNotEmpty() && password.isNotEmpty()) {
    // Menyimpan sesi secara lokal tanpa request ke server
    UserManager.userName = if (name.isNotEmpty()) name else username
    // Sistem melanjutkan navigasi ke HomeActivity...
}

```

3. Logika Masuk (LoginActivity)
   Mekanisme login mengadopsi prinsip yang serupa. Setelah memastikan kolom tidak kosong, sistem memercayai input pengguna dan langsung memperbarui sesi aktif.

```
val username = binding.etUsername.text.toString().trim()
val password = binding.etPassword.text.toString().trim()

if (username.isNotEmpty() && password.isNotEmpty()) {
    // Menetapkan sesi pengguna saat ini
    UserManager.userName = username
    // Sistem melanjutkan navigasi ke HomeActivity...
}

```

Pendekatan ini sangat efektif untuk mendemonstrasikan purwarupa fungsional kepada evaluator, memastikan alur UI/UX dapat diuji secara utuh tanpa terhambat oleh kompleksitas integrasi peladen (server) backend.

## Cara Menjalankan Aplikasi

1. Unduh kode sumber aplikasi atau lakukan proses kloning repositori (git clone) ke penyimpanan lokal Anda.

2. Buka perangkat lunak Android Studio.

3. Pilih opsi "Open" dan navigasikan ke direktori folder nyemil_kuy yang telah diunduh.

4. Tunggu hingga Android Studio menyelesaikan proses sinkronisasi Gradle (pastikan perangkat terhubung ke internet).

5. Sambungkan perangkat Android fisik Anda menggunakan kabel USB (pastikan mode USB Debugging aktif) atau siapkan Android Virtual Device (Emulator).

6. Klik tombol "Run" pada panel atas Android Studio atau gunakan pintasan Shift + F10 untuk mengompilasi dan memasang aplikasi pada perangkat.

## Screenshot Tampilan Aplikasi

Catatan: Untuk memastikan gambar di bawah ini dapat dimuat pada GitHub, pastikan Anda telah membuat folder bernama "screenshots" di direktori utama repositori ini dan meletakkan semua file gambar .jpg ke dalam folder tersebut sesuai dengan nama yang tertulis.

Layar Splash Screen

Layar Pilihan Autentikasi

Layar Pendaftaran (Register)

Layar Masuk (Login)

Layar Utama (Home Menu)

Layar Keranjang Belanja (Cart)

Layar Pengisian Alamat Pengiriman (Order)

Layar Konfirmasi Pesanan (Success)

Layar Profil Pengguna

Layar Ubah Nama Profil