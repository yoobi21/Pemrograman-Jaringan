using System;
using System.Collections.Generic;
using System.IO; // Diperlukan untuk File.WriteAllText dan File.ReadAllText
using System.Text.Json; // Diperlukan untuk JSON
using System.Xml.Serialization; // Diperlukan untuk XML

namespace PraktikumStream
{
    // --- KODE MAHASISWA DARI GAMBAR ---
    [Serializable]
    public class Mahasiswa
    {
        public int NIM { get; set; }
        public string Nama { get; set; }
        public string Jurusan { get; set; }
        public List<string> MataKuliah { get; set; }

        // Constructor kosong wajib ada untuk proses Deserialisasi XML
        public Mahasiswa() { }

        public Mahasiswa(int nim, string nama, string jurusan, List<string> matkul)
        {
            NIM = nim;
            Nama = nama;
            Jurusan = jurusan;
            MataKuliah = matkul;
        }

        public void TampilkanInfo()
        {
            Console.WriteLine($"NIM          : {NIM}");
            Console.WriteLine($"Nama         : {Nama}");
            Console.WriteLine($"Jurusan      : {Jurusan}");
            Console.WriteLine($"Matkul       : {string.Join(", ", MataKuliah)}");
            Console.WriteLine("-----------------------------------------");
        }
    }

    // --- KELAS PROGRAM UTAMA UNTUK TUGAS STREAM ---
    class Program
    {
        static void Main(string[] args)
        {
            // 1. Membuat objek mahasiswa
            List<string> matkul1 = new List<string> { "Pemrograman C#", "Basis Data", "Struktur Data" };
            Mahasiswa mhs1 = new Mahasiswa(12345678, "Budi", "Teknik Informatika", matkul1);

            Console.WriteLine("=== DATA ASLI ===");
            mhs1.TampilkanInfo();

            // Tentukan path/nama file
            string jsonFilePath = "mahasiswa.json";
            string xmlFilePath = "mahasiswa.xml";

            // ==========================================
            // PROSES JSON (Serialisasi -> Simpan -> Baca -> Deserialisasi)
            // ==========================================
            Console.WriteLine("\n=== PROSES JSON ===");
            
            // A. Serialisasi ke JSON String
            // Opsi WriteIndented agar JSON rapi saat dibaca manusia
            JsonSerializerOptions options = new JsonSerializerOptions { WriteIndented = true };
            string jsonString = JsonSerializer.Serialize(mhs1, options);
            
            // B. Simpan ke File fisik (.json)
            File.WriteAllText(jsonFilePath, jsonString);
            Console.WriteLine($"[INFO] Data JSON berhasil disimpan ke file: {jsonFilePath}");

            // C. Baca kembali dari File fisik
            string jsonFromFile = File.ReadAllText(jsonFilePath);
            
            // D. Deserialisasi kembali menjadi objek
            Mahasiswa mhsFromJson = JsonSerializer.Deserialize<Mahasiswa>(jsonFromFile);
            Console.WriteLine("[INFO] Data JSON berhasil dibaca dan di-deserialize:");
            mhsFromJson.TampilkanInfo();


            // ==========================================
            // PROSES XML (Serialisasi -> Simpan -> Baca -> Deserialisasi)
            // ==========================================
            Console.WriteLine("\n=== PROSES XML ===");

            // A. Serialisasi ke XML String
            XmlSerializer xmlSerializer = new XmlSerializer(typeof(Mahasiswa));
            string xmlString = "";

            // Menggunakan StringWriter untuk menangkap hasil serialisasi XML ke dalam string
            using (StringWriter sw = new StringWriter())
            {
                xmlSerializer.Serialize(sw, mhs1);
                xmlString = sw.ToString();
            }

            // B. Simpan ke File fisik (.xml)
            File.WriteAllText(xmlFilePath, xmlString);
            Console.WriteLine($"[INFO] Data XML berhasil disimpan ke file: {xmlFilePath}");

            // C. Baca kembali dari File fisik
            string xmlFromFile = File.ReadAllText(xmlFilePath);

            // D. Deserialisasi kembali menjadi objek
            Mahasiswa mhsFromXml;
            using (StringReader sr = new StringReader(xmlFromFile))
            {
                mhsFromXml = (Mahasiswa)xmlSerializer.Deserialize(sr);
            }
            Console.WriteLine("[INFO] Data XML berhasil dibaca dan di-deserialize:");
            mhsFromXml.TampilkanInfo();

            Console.WriteLine("\nTekan Enter untuk keluar...");
            Console.ReadLine();
        }
    }
}