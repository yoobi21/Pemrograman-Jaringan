
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String alamatServer = "127.0.0.1"; // Alamat komputer sendiri (Localhost)
        int portServer = 9876;             // Pintu tujuan di komputer server

        System.out.println("=== APLIKASI CHAT SEDERHANA (Ketik 'EXIT' untuk keluar) ===");

        // Membuka alat ketik (Scanner) untuk membaca keyboard dan Socket untuk jaringan
        try (Scanner keyboard = new Scanner(System.in);
             DatagramSocket clientSocket = new DatagramSocket()) {

            // 6. Proses berulang (looping) selama belum mengetik "EXIT"
            while (true) {
                // 1. Membaca input teks ketikan dari keyboard
                System.out.print("\nMasukkan pesan Anda: ");
                String pesanKetik = keyboard.nextLine();

                // Mengubah teks ketikan menjadi bentuk byte agar bisa dikirim lewat jaringan
                byte[] dataKirim = pesanKetik.getBytes();
                InetAddress tujuanServer = InetAddress.getByName(alamatServer);

                // Membungkus data ke dalam paket jaringan
                DatagramPacket paketKirim = new DatagramPacket(
                    dataKirim, 
                    dataKirim.length, 
                    tujuanServer, 
                    portServer
                );

                // 2. Mengirim pesan tersebut ke Server
                clientSocket.send(paketKirim);

                // Jika yang diketik adalah "EXIT", hentikan perulangan (keluar aplikasi)
                if (pesanKetik.equalsIgnoreCase("EXIT")) {
                    System.out.println("Keluar dari program. Sampai jumpa!");
                    break;
                }

                // Menyiapkan kotak kosong untuk menerima balasan dari server
                byte[] kotakTerimaBalasan = new byte[1024];
                DatagramPacket paketBalasanMasuk = new DatagramPacket(kotakTerimaBalasan, kotakTerimaBalasan.length);

                // Menunggu balasan dari server
                clientSocket.receive(paketBalasanMasuk);

                // Mengubah data balasan menjadi teks biasa
                String pesanBalasan = new String(
                    paketBalasanMasuk.getData(), 
                    0, 
                    paketBalasanMasuk.getLength()
                );

                // 5. Client menerima balasan dari Server dan menampilkannya di terminal
                System.out.println("Balasan dari Server (Huruf Besar): " + pesanBalasan);
            }

        } catch (IOException e) {
            // Menangani error jika server tidak aktif atau ada masalah jaringan
            System.out.println("Terjadi kesalahan pada client: " + e.getMessage());
        }
    }
}