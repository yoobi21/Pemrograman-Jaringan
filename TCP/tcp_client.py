import socket

#Pasang Socket TCP

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

#Hubungkan ke server
HOST = '127.0.0.1'
PORT = 12345

#2 menghubungkan ke server
print(f"[TCP Client]Menghubungkan ke server {HOST}:{PORT}...")
client_socket.connect((HOST, PORT))
print(f"[TCP Client] Terhubung ke server {HOST}:{PORT}.")

print("Ketik pesan untuk dikirim ke server (ketik 'exit' untuk keluar):")
try:
    while True:
        message = input(">")
        #Kirim pesan ke server
        client_socket.sendall(message.encode('utf-8'))
        
        if message.lower() == 'exit':
            print("[TCP Client] Keluar dari program.")
            break
        
        #Terima balasan dari server
        response = client_socket.recv(1024).decode('utf-8')
        print(f"[TCP Client] Balasan dari server: {response}")
finally:
    client_socket.close()
    print("[TCP Client] Koneksi ditutup.")