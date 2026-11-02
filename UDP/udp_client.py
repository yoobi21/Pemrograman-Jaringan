import socket
import time
import random

# 1. Inisialisasi socket UDP
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

HOST = '127.0.0.1'
PORT = 54321

print("[UDP CLIENT] Mulai mengirim data sensor otomatis per 2 detik...")
print("Tekan Ctrl+C untuk menghentikan client.")

try:
    while True:
        # Simulasi data sensor acak
        suhu = round(random.uniform(25.0, 35.0), 2)
        message = f"Data Sensor Suhu: {suhu}C"

        # 2. Mengirim data langsung tanpa connect terlebih dahulu
        client_socket.sendto(message.encode('utf-8'), (HOST, PORT))
        print(f"[Dikirim] -> {message}")

        time.sleep(2)
except KeyboardInterrupt:
    print("\n[UDP CLIENT] Pengiriman dihentikan oleh pengguna.")
finally:
    client_socket.close()