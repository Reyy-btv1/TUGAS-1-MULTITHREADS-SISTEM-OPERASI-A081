#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


// Mutex untuk sinkronisasi output
pthread_mutex_t mutex;

// Fungsi untuk menampilkan output secara aman
void print_safe(const char *text) {
  pthread_mutex_lock(&mutex);

  printf("%s\n", text);

  pthread_mutex_unlock(&mutex);
}

// Thread 1: Faktorial
void *factorial_thread(void *arg) {
  int number = 5;
  long long factorial = 1;

  char output[100];

  sprintf(output, "[Thread Faktorial] Menghitung faktorial %d", number);

  print_safe(output);

  for (int i = 1; i <= number; i++) {
    factorial *= i;
  }

  sprintf(output, "[Thread Faktorial] %d! = %lld", number, factorial);

  print_safe(output);

  return NULL;
}

// Thread 2: Fibonacci
void *fibonacci_thread(void *arg) {
  int jumlah = 10;

  long long a = 0;
  long long b = 1;
  long long next;

  char output[300];
  char temp[30];

  output[0] = '\0';

  print_safe("[Thread Fibonacci] Menghasilkan 10 angka");

  for (int i = 0; i < jumlah; i++) {
    sprintf(temp, "%lld ", a);
    strcat(output, temp);

    next = a + b;
    a = b;
    b = next;
  }

  print_safe(output);

  return NULL;
}

// Thread 3: Membaca file
void *file_thread(void *arg) {
  FILE *file;
  char line[256];

  print_safe("[Thread File] Membaca file: data.txt");

  file = fopen("data.txt", "r");

  if (file == NULL) {
    print_safe("[Thread File] File tidak ditemukan.");
    return NULL;
  }

  while (fgets(line, sizeof(line), file)) {
    print_safe(line);
  }

  fclose(file);

  return NULL;
}

int main() {
  pthread_t thread1;
  pthread_t thread2;
  pthread_t thread3;

  // Inisialisasi mutex
  pthread_mutex_init(&mutex, NULL);

  printf("=== PROGRAM MULTITHREADING C ===\n\n");

  // Membuat 3 thread
  pthread_create(&thread1, NULL, factorial_thread, NULL);

  pthread_create(&thread2, NULL, fibonacci_thread, NULL);

  pthread_create(&thread3, NULL, file_thread, NULL);

  // Menunggu semua thread selesai
  pthread_join(thread1, NULL);
  pthread_join(thread2, NULL);
  pthread_join(thread3, NULL);

  // Menghapus mutex
  pthread_mutex_destroy(&mutex);

  printf("\n=== SEMUA THREAD SELESAI ===\n");

  return 0;
}