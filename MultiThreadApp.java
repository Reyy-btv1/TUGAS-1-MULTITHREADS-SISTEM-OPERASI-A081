import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Class untuk sinkronisasi output
class Output {
    public static synchronized void print(String text) {
        System.out.println(text);
    }
}

// Thread 1: Menghitung Faktorial
class FactorialThread extends Thread {

    private int number;

    public FactorialThread(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        long factorial = 1;

        Output.print("[Thread Faktorial] Menghitung faktorial " + number);

        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }

        Output.print("[Thread Faktorial] " + number + "! = " + factorial);
    }
}

// Thread 2: Menghasilkan Fibonacci
class FibonacciThread extends Thread {

    private int jumlah;

    public FibonacciThread(int jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public void run() {

        Output.print("[Thread Fibonacci] Menghasilkan " + jumlah + " angka");

        long a = 0;
        long b = 1;

        StringBuilder hasil = new StringBuilder();

        for (int i = 0; i < jumlah; i++) {
            hasil.append(a);

            if (i < jumlah - 1) {
                hasil.append(" ");
            }

            long next = a + b;
            a = b;
            b = next;
        }

        Output.print("[Thread Fibonacci] " + hasil);
    }
}

// Thread 3: Membaca file
class FileThread extends Thread {

    private String namaFile;

    public FileThread(String namaFile) {
        this.namaFile = namaFile;
    }

    @Override
    public void run() {

        Output.print("[Thread File] Membaca file: " + namaFile);

        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(namaFile));

            String line;

            while ((line = reader.readLine()) != null) {
                Output.print("[Thread File] " + line);
            }

            reader.close();

        } catch (IOException e) {
            Output.print("[Thread File] Terjadi kesalahan: "
                    + e.getMessage());
        }
    }
}

// Main Program
public class MultiThreadApp {

    public static void main(String[] args) {

        System.out.println("=== PROGRAM MULTITHREADING JAVA ===");

        // Membuat 3 thread
        FactorialThread thread1 = new FactorialThread(5);
        FibonacciThread thread2 = new FibonacciThread(10);
        FileThread thread3 = new FileThread("data.txt");

        // Menjalankan thread
        thread1.start();
        thread2.start();
        thread3.start();

        // Menunggu semua thread selesai
        try {
            thread1.join();
            thread2.join();
            thread3.join();

        } catch (InterruptedException e) {
            System.out.println("Thread utama terganggu.");
        }

        System.out.println("=== SEMUA THREAD SELESAI ===");
    }
}