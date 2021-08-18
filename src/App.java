import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class App {
    static final Scanner input = new Scanner(System.in);

    static String noPenjualan() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz12341567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return (saltStr);
    }

    static void showData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;
        try {
            fileInput = new FileReader("database/transaction.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("Database Not Found");
            System.err.println("Please Check your Database File");
            return;
        }

        System.out.println("==========================================================================");
        System.out.println("|                              Tampil Data                               |");
        System.out.println("==========================================================================");
        System.out.println("| No.Penjualan | Jenis | Ukuran |   Harga   | Jumlah | disc(%) |  Total  |");
        System.out.println("==========================================================================");

        String data = bufferInput.readLine();
        while (data != null) {
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            System.out.printf("|   %8s   ", stringToken.nextToken());
            System.out.printf("|   %1s   ", stringToken.nextToken());
            System.out.printf("|   %1s    ", stringToken.nextToken());
            System.out.printf("|  %5s    ", stringToken.nextToken());
            System.out.printf("| %3s    ", stringToken.nextToken());
            System.out.printf("|  %3s    ", stringToken.nextToken());
            System.out.printf("| %7s |", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }
        System.out.println("==========================================================================");

        bufferInput.close();
    }

    static void showMenu() throws IOException {
        String selectedMenu;
        System.out.println("==============================");
        System.out.println("   Menu Aplikasi Susu Kotak");
        System.out.println("==============================");
        System.out.println("[1] Tampilkan Data transaksi");
        System.out.println("[2] Tambahkan Data");
        System.out.println("[3] Cari Data");
        System.out.println("[4] Exit");
        System.out.println("==============================");
        System.out.print("PILIH MENU [1-3]> ");
        selectedMenu = input.next();
        System.out.print("\n");

        switch (selectedMenu) {
            case "1":
                showData();
                break;
            case "2":
                addData();
                break;
            case "3":
                searchData();
                break;
            case "4":
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan salah!");

        }
    }

    static void searchData() throws IOException {
        System.out.print("masukkan Kata Kunci >");
        input.nextLine();
        String searchData = input.nextLine();

        String[] keywords = searchData.split("\\s+");

        checkData(keywords);
    }

    static void checkData(String[] keywords) throws IOException {
        FileReader fileInput = new FileReader("database/transaction.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist;

        System.out.println("==========================================================================");
        System.out.println("|                               Cari Data                                |");
        System.out.println("==========================================================================");
        System.out.println("| No.Penjualan | Jenis | Ukuran |   Harga   | Jumlah | disc(%) |  total  |");
        System.out.println("==========================================================================");

        while (data != null) {

            isExist = true;

            for (String keyword : keywords) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            if (isExist) {
                StringTokenizer stringToken = new StringTokenizer(data, ",");

                System.out.printf("|   %8s   ", stringToken.nextToken());
                System.out.printf("|   %1s   ", stringToken.nextToken());
                System.out.printf("|   %1s    ", stringToken.nextToken());
                System.out.printf("|  %5s    ", stringToken.nextToken());
                System.out.printf("| %3s    ", stringToken.nextToken());
                System.out.printf("|  %3s    ", stringToken.nextToken());
                System.out.printf("| %7s |", stringToken.nextToken());
                System.out.print("\n");

            }

            data = bufferInput.readLine();
        }

        System.out.println("==========================================================================");
        bufferInput.close();
    }

    static String checkProduct(String[] inputs) throws IOException {
        FileReader fileInput = new FileReader("database/product.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String dataCheck = null;
        boolean isExist;
        String data = bufferInput.readLine();

        while (data != null) {
            StringTokenizer st = new StringTokenizer(data, ",");
            isExist = st.nextToken().equalsIgnoreCase(inputs[0]) && st.nextToken().equalsIgnoreCase(inputs[1]);
            if (isExist) {
                dataCheck = data;
            }
            data = bufferInput.readLine();
        }
        bufferInput.close();
        return dataCheck;
    }

    static void addData() throws IOException {
        FileWriter fileOutput = new FileWriter("database/transaction.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        String jenis = null, ukuran = null, jumlah = null, harga = null;
        Integer total, disc = 0;
        System.out.println("===============================================");
        System.out.println("| jenis susu |    Harga sesuai ukuran susu    |");
        System.out.println("|             =================================");
        System.out.println("|            | 1(kecil) | 2(sedang) | 3(besar)|");
        System.out.println("===============================================");
        System.out.println("|  Dancow A  | Rp15000  | Rp20000   | Rp25000 |");
        System.out.println("|  Bendera B | Rp15000  | Rp19000   | Rp23000 |");
        System.out.println("|  SGM C     | Rp15000  | Rp18000   | Rp22000 |");
        System.out.println("===============================================\n");
        boolean ulang = true;
        while (ulang) {
            while (ulang) {
                System.out.print("masukkan jenis Susu (A-B)> ");
                jenis = input.next();
                if (!jenis.matches("[a-cA-C]")) {
                    System.err.println("pilihan anda tidak sesuai, ulangi dari awal!! \n");
                    break;
                }
                System.out.print("masukkan ukuran Susu (1-3)> ");
                input.nextLine();
                ukuran = input.nextLine();
                if (!ukuran.matches("[1-3]")) {
                    System.err.println("pilihan anda tidak sesuai, ulangi dari awal!! \n");
                    break;
                }
                System.out.print("masukkan jumlah Susu > ");
                jumlah = input.nextLine();
                if (!jumlah.matches("[0-9]*")) {
                    System.err.println("pilihan anda tidak sesuai, ulangi dari awal!! \n");
                    break;
                }
                ulang = false;
            }
        }

        String[] data = { jenis, ukuran };
        String dataCheck = checkProduct(data);
        if (dataCheck != null) {
            StringTokenizer st = new StringTokenizer(dataCheck, ",");
            while (st.hasMoreTokens()) {
                harga = st.nextToken();
            }
            if (Integer.parseInt(ukuran) == 3 && Integer.parseInt(jumlah) >= 10) {
                disc = 10;
                System.out.println("anda mendapat diskon 10%");
            } else if (Integer.parseInt(ukuran) == 2
                    || Integer.parseInt(ukuran) == 3 && Integer.parseInt(jumlah) >= 5) {
                disc = 5;
                System.out.println("anda mendapat diskon 5%");
            } else if (Integer.parseInt(ukuran) == 1 && Integer.parseInt(jumlah) >= 5) {
                disc = 2;
                System.out.println("anda mendapat diskon 2%");
            }
            total = (Integer.parseInt(harga) * Integer.parseInt(jumlah))
                    - ((Integer.parseInt(harga) * Integer.parseInt(jumlah)) * disc / 100);

            System.out.println("total harga " + total);
            Boolean buy = getYesOrNo("apakah anda ingin membayarnya (y/n)>");
            if (buy) {
                bufferOutput.write(noPenjualan() + "," + jenis + "," + ukuran + "," + harga + "," + jumlah + "," + disc
                        + "," + total);
                bufferOutput.newLine();
                bufferOutput.flush();
                System.out.println("berhasil membeli data disimpan di menu tampilkan transaksi!!");
            }
        } else {
            System.err.println("Data yang anda masukkan tidak sesuai dengan data dalam tabel !!");
        }

        bufferOutput.close();
    }

    static boolean getYesOrNo(String message) {
        boolean isRunning;
        System.out.print("\n" + message);
        String selectedUser = input.next();

        while (!selectedUser.equalsIgnoreCase("y") && !selectedUser.equalsIgnoreCase("n")) {
            System.err.println("Silahkan Masukkan y atau n");
            System.out.println("\n" + message);
            selectedUser = input.next();
        }
        isRunning = selectedUser.equalsIgnoreCase("y");
        return isRunning;
    }

    public static void main(String args[]) throws IOException {
        boolean isRunning = true;
        while (isRunning) {

            showMenu();

            isRunning = getYesOrNo("Apakah anda ingin melanjutkannya? [y/n]>");
        }

    }
}