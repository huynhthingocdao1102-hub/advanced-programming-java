import java.util.*;
import java.io.*;

public class ContactManager {

    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private Scanner sc = new Scanner(System.in);
    private String filePath = "contacts.csv";

    //HIỂN THỊ DANH BẠ
    public void showContacts() {
        if (contacts.isEmpty()) {
            System.out.println("\nDanh bạ rỗng!");
            return;
        }

        System.out.println("\n================ DANH SÁCH DANH BẠ ================");
        System.out.printf("%-12s | %-20s | %-10s | %-6s | %-15s | %-12s | %-20s\n",
                "SĐT", "Họ Tên", "Nhóm", "Giới", "Địa chỉ", "Ngày sinh", "Email");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            System.out.printf("%-12s | %-20s | %-10s | %-6s | %-15s | %-12s | %-20s\n",
                    c.getPhone(), c.getName(), c.getGroup(), c.getGender(),
                    c.getAddress(), c.getBirthDate(), c.getEmail());
        }

        System.out.println("====================================================");
    }

    //THÊM DANH BẠ
    public void addContact() {
        System.out.println("\n====== THÊM DANH BẠ ======");

        String phone;
        while (true) {
            System.out.print("Số điện thoại (10 số): ");
            phone = sc.nextLine();
            if (phone.matches("\\d{10}")) break;
            System.out.println("❌ SĐT không hợp lệ!");
        }

        System.out.print("Nhóm: ");
        String group = sc.nextLine();

        String name;
        while (true) {
            System.out.print("Họ tên: ");
            name = sc.nextLine();
            if (name.length() == 0) {
                System.out.println("❌ Tên không được rỗng!");
            } else if (name.length() > 100) {
                System.out.println("❌ Tên không được quá 100 ký tự!");
            } else {
                break;
            }
        }

        System.out.print("Giới tính: ");
        String gender = sc.nextLine();

        System.out.print("Địa chỉ: ");
        String address = sc.nextLine();

        String birthDate;

        while (true) {
            System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
            birthDate = sc.nextLine().trim();

            String[] arr = birthDate.split("-");

            if (arr.length != 3) {
                System.out.println("❌ Sai định dạng! Ví dụ: 1999-02-11");
                continue;
            }

            int year, month, day;

            try {
                year = Integer.parseInt(arr[0]);
                month = Integer.parseInt(arr[1]);
                day = Integer.parseInt(arr[2]);
            } catch (Exception e) {
                System.out.println("❌ Ngày sinh không hợp lệ!");
                continue;
            }

            // kiểm tra năm
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);

            if (year < 1900 || year > currentYear) {
                System.out.println("❌ Năm sinh không hợp lệ!");
                continue;
            }

            // kiểm tra tháng
            if (month < 1 || month > 12) {
                System.out.println("❌ Tháng không hợp lệ!");
                continue;
            }

            // kiểm tra ngày
            if (day < 1 || day > 31) {
                System.out.println("❌ Ngày không hợp lệ!");
                continue;
            }

            break;
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (email.matches("^(.+)@(.+)$")) break;
            System.out.println("❌ Email không hợp lệ!");
        }

        Contact c = new Contact(phone, group, name, gender, address, birthDate, email);
        contacts.add(c);

        autoSave();

        System.out.println("✅ Thêm danh bạ thành công!");
    }

    //TÌM KIẾM
    public void searchContact() {
        System.out.print("\nNhập tên hoặc SĐT cần tìm: ");
        String key = sc.nextLine();

        boolean found = false;
        for (Contact c : contacts) {
            if (c.getPhone().contains(key) || c.getName().toLowerCase().contains(key.toLowerCase())) {
                System.out.println(c);
                found = true;
            }
        }

        if (!found) System.out.println("❌ Không tìm thấy!");
    }

    //CẬP NHẬT DANH BẠ

    public void updateContact() {
        System.out.println("\n====== CẬP NHẬT DANH BẠ ======");
        System.out.print("Nhập số điện thoại cần sửa: ");
        String phone = sc.nextLine();

        Contact c = findContactByPhone(phone);
        if (c == null) {
            System.out.println("❌ Không tìm thấy danh bạ!");
            return;
        }

        System.out.println("👉 Nhấn Enter nếu muốn giữ nguyên giá trị cũ");

        System.out.print("Nhóm mới (" + c.getGroup() + "): ");
        String group = sc.nextLine();
        if (!group.isEmpty()) c.setGroup(group);

        System.out.print("Họ tên mới (" + c.getName() + "): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) {
            if (name.length() <= 100) {
                c.setName(name);
            } else {
                System.out.println("❌ Tên quá dài (tối đa 100 ký tự). Giữ nguyên tên cũ!");
            }
        }

        System.out.print("Giới tính mới (" + c.getGender() + "): ");
        String gender = sc.nextLine();
        if (!gender.isEmpty()) c.setGender(gender);

        System.out.print("Địa chỉ mới (" + c.getAddress() + "): ");
        String address = sc.nextLine();
        if (!address.isEmpty()) c.setAddress(address);

        System.out.print("Ngày sinh mới (" + c.getBirthDate() + "): ");
        String birthDate = sc.nextLine();
        if (!birthDate.isEmpty()) {
            String[] arr = birthDate.split("/");
            if (arr.length == 3) {
                try {
                    int year = Integer.parseInt(arr[2]);
                    Calendar cal = Calendar.getInstance();
                    int currentYear = cal.get(Calendar.YEAR);
                    if (year <= currentYear) {
                        c.setBirthDate(birthDate);
                    } else {
                        System.out.println("❌ Ngày sinh không hợp lệ. Giữ nguyên giá trị cũ!");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Ngày sinh sai định dạng. Giữ nguyên giá trị cũ!");
                }
            }
        }

        System.out.print("Email mới (" + c.getEmail() + "): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) {
            if (email.matches("^(.+)@(.+)$")) {
                c.setEmail(email);
            } else {
                System.out.println("❌ Email không hợp lệ. Giữ nguyên email cũ!");
            }
        }

        autoSave();

        System.out.println("✅ Cập nhật danh bạ thành công!");
    }

    //XÓA
    public void deleteContact() {
        System.out.print("\nNhập SĐT cần xóa: ");
        String phone = sc.nextLine();

        Contact c = findContactByPhone(phone);
        if (c == null) {
            System.out.println("❌ Không tìm thấy!");
            return;
        }

        System.out.print("Bạn có chắc muốn xóa? (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            contacts.remove(c);
            autoSave();
            System.out.println("✅ Xóa thành công!");
        }
    }

    //ĐỌC FILE CSV
    public void readFromFile() {
        System.out.print("Đọc file sẽ xóa danh bạ hiện tại. Tiếp tục? (Y/N): ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) return;

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("❌ Không tìm thấy file!");
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            contacts.clear();

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] arr = line.split(",");
                if (arr.length == 7) {
                    Contact c = new Contact(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
                    contacts.add(c);
                }
            }

            br.close();
            System.out.println("✅ Đọc file thành công!");
            showContacts();

        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }

    //LƯU FILE CSV
    public void saveToFile() {
        System.out.print("Lưu file sẽ ghi đè dữ liệu cũ. Tiếp tục? (Y/N): ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) return;

        writeFile();
        System.out.println("✅ Lưu file thành công!");
    }

    private void autoSave() {
        writeFile();
    }

    private void writeFile() {
        try {
            File folder = new File("data");
            if (!folder.exists()) folder.mkdirs();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            bw.write("Phone,Group,Name,Gender,Address,BirthDate,Email");
            bw.newLine();

            for (Contact c : contacts) {
                bw.write(c.getPhone() + "," + c.getGroup() + "," + c.getName() + "," +
                        c.getGender() + "," + c.getAddress() + "," + c.getBirthDate() + "," + c.getEmail());
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            System.out.println("❌ Lỗi lưu file: " + e.getMessage());
        }
    }

    //ÌM CONTACT
    private Contact findContactByPhone(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) return c;
        }
        return null;
    }
}
