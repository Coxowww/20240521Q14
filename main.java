import java.io.*;
import java.util.Arrays;

class BSort implements Runnable {
    private String inputFile;
    private String outputFile;

    public BSort(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            String line;
            double[] data = new double[100000];
            int index = 0;

            while ((line = reader.readLine()) != null) {
                data[index] = Double.parseDouble(line);
                index++;
            }

            bubbleSort(data);

            for (double num : data) {
                writer.write(Double.toString(num));
                writer.newLine();
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bubbleSort(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}

class QSort implements Runnable {
    private String inputFile;
    private String outputFile;

    public QSort(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            String line;
            double[] data = new double[100000];
            int index = 0;

            while ((line = reader.readLine()) != null) {
                data[index] = Double.parseDouble(line);
                index++;
            }

            quickSort(data, 0, data.length - 1);

            for (double num : data) {
                writer.write(Double.toString(num));
                writer.newLine();
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quickSort(double[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(double[] arr, int low, int high) {
        double pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        double temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter("bdata.txt"));
            BufferedWriter qWriter = new BufferedWriter(new FileWriter("qdata.txt"));

            for (int i = 0; i < 100000; i++) {
                bWriter.write(Double.toString(Math.random() * 10000));
                bWriter.newLine();
                qWriter.write(Double.toString(Math.random() * 10000));
                qWriter.newLine();
            }

            bWriter.close();
            qWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(new BSort("bdata.txt", "bresult.txt"));
        Thread t2 = new Thread(new QSort("qdata.txt", "qresult.txt"));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
