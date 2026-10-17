
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: "
                        + socket.getInetAddress());

                Thread clientThread = new Thread(() -> {
                    try (
                        BufferedReader input = new BufferedReader(
                            new InputStreamReader(
                                socket.getInputStream()));
                        PrintWriter output = new PrintWriter(
                            socket.getOutputStream(), true)
                    ) {
                        output.println("Welcome! Type 'exit' to disconnect.");

                        String message;
                        while ((message = input.readLine()) != null) {
                            System.out.println("Client: " + message);

                            if (message.equalsIgnoreCase("exit")) {
                                output.println("Goodbye!");
                                break;
                            }

                            output.println("Server received: " + message);
                        }
                    } catch (IOException e) {
                        System.out.println("Client connection error: "
                                + e.getMessage());
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException ignored) {
                            // Socket cleanup
                        }
                        System.out.println("Client disconnected.");
                    }
                });

                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
              }
