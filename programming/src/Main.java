import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactManager manager = new ContactManager();

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println();
            System.out.println("****************************************************");
            System.out.println("*                                                  *");
            System.out.println("*                  QUẢN LÝ DANH BẠ                 *");
            System.out.println("*                                                  *");
            System.out.println("****************************************************");
            System.out.println("*  1. Xem danh sách                                *");
            System.out.println("*  2. Thêm mới                                     *");
            System.out.println("*  3. Cập nhật                                     *");
            System.out.println("*  4. Xóa                                          *");
            System.out.println("*  5. Tìm kiếm                                     *");
            System.out.println("*  6. Đọc dữ liệu từ file                          *");
            System.out.println("*  7. Lưu dữ liệu vào file                         *");
            System.out.println("*--------------------------------------------------*");
            System.out.println("*  0. Thoát chương trình                           *");
            System.out.println("****************************************************");

            System.out.print(">>> Nhập lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    manager.showContacts();
                    break;
                case 2:
                    manager.addContact();
                    break;
                case 3:
                    manager.updateContact();
                    break;
                case 4:
                    manager.deleteContact();
                    break;
                case 5:
                    manager.searchContact();
                    break;
                case 6:
                    manager.readFromFile();
                    break;
                case 7:
                    manager.saveToFile();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Chọn sai, thử lại!");
            }
        } while (choice != 0);
    }
}
