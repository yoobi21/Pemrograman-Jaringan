
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) {
        // Tentukan nomor pintu (port) tempat server menunggu pesan
        int nomorPort = 9876;
        
        System.out.println("Komputer Server sedang bersiap dan menunggu pesan...");

        // Membuka "pintu jaringan" (Socket) khusus untuk server di port 9876
        // Menggunakan try-with-resources agar socket otomatis tertutup jika selesai/error
        try (DatagramSocket serverSocket = new DatagramSocket(nomorPort)) {
            
            // Kotak penampung data (buffer) berukuran 1024 byte
            byte[] kotakTerima = new byte[1024];

            // Server akan terus-menerus mendengarkan pesan selamanya (looping)
            while (true) {
                // Menyiapkan amplop kosong untuk menerima paket dari teman (Client)
                DatagramPacket paketMasuk = new DatagramPacket(kotakTerima, kotakTerima.length);
                
                // Server menunggu sampai ada pesan masuk (berhenti sejenak sampai pesan datang)
                serverSocket.receive(paketMasuk);

                // Mengubah isi amplop (data byte) menjadi teks biasa (String)
                String pesanAsli = new String(paketMasuk.getData(), 0, paketMasuk.getLength()).trim();

                // Mencatat alamat rumah (IP) dan nomor pintu si pengirim pesan
                InetAddress alamatPengirim = paketMasuk.getAddress();
                int portPengirim = paketMasuk.getPort();

                System.out.println("Server menerima pesan: " + pesanAsli);

                // Jika pesan dari teman adalah "EXIT", beri tahu di layar server
                if (pesanAsli.equalsIgnoreCase("EXIT")) {
                    System.out.println("Teman telah keluar dari percakapan.");
                }

                // 3. Mengubah seluruh teks pesan menjadi HURUF KAPITAL
                String pesanKapital = pesanAsli.toUpperCase();
                byte[] dataKirim = pesanKapital.getBytes();

                // 4. Membuat amplop balasan untuk dikirim balik ke alamat & port pengirim asal
                DatagramPacket paketBalik = new DatagramPacket(
                    dataKirim, 
                    dataKirim.length, 
                    alamatPengirim, 
                    portPengirim
                );
                
                // Mengirimkan kembali pesan berhuruf kapital ke Client
                serverSocket.send(paketBalik);
                System.out.println("Server mengirim balik: " + pesanKapital);
                System.out.println("----------------------------------------");
            }

        } catch (IOException e) {
            // Jika ada gangguan jaringan, tampilkan pesan errornya
            System.out.println("Terjadi kesalahan jaringan pada server: " + e.getMessage());
        }
    }
}