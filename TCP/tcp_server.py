import socket

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

HOST = '127.0.0.1'
PORT = 12345

server_socket.bind((HOST, PORT))
server_socket.listen(1)
print(f"[TCP Server] Menunggu koneksi di {HOST}:{PORT}...")

client_socket, client_address = server_socket.accept()
print(f"[TCP Server] Terhubung ke {client_address[0]}:{client_address[1]}.")

try:
    while True:
        #Terima pesan dari client
        data = client_socket.recv(1024).decode('utf-8')
        if not data or data.lower() == 'exit':
            print("[TCP Server] Koneksi ditutup oleh client.")
            break
        print(f"[TCP Server] Pesan dari client: {data}")
        response = f"Pesan diterima: {data}"
        #Kirim balasan ke client
        client_socket.sendall(response.encode('utf-8'))
except Exception as e:
    print(f"[TCP Server] Terjadi kesalahan: {e}")
finally:
    client_socket.close()
    server_socket.close()
    print("[TCP Server] Koneksi ditutup.")