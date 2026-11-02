import socket

# 1. Inisialisasi socket UDP (SOCK_DGRAM)
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

HOST = '127.0.0.1'
PORT = 54321

# 2. Bind socket (UDP tidak menggunakan listen/accept)
server_socket.bind((HOST, PORT))
print(f"[UDP SERVER] Berjalan di {HOST}:{PORT}. Siap menerima paket data...")

try:
    while True:
        # 3. Langsung menerima data beserta alamat pengirimnya
        data, client_address = server_socket.recvfrom(1024)
        message = data.decode('utf-8')
        print(f"[UDP LOG] Dari {client_address}: {message}")

        if message.lower() == 'stop-server':
            print("[UDP SERVER] Perintah stop diterima.")
            break
finally:
    server_socket.close()
    print("[UDP SERVER] Server dimatikan.")