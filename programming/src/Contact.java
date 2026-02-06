import java.io.Serializable;

public class Contact implements Serializable {

    private String phone;
    private String group;
    private String name;
    private String gender;
    private String address;
    private String birthDate;
    private String email;

    public Contact() {
    }

    public Contact(String phone, String group, String name, String gender,
                   String address, String birthDate, String email) {
        this.phone = phone;
        this.group = group;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
    }

    //GETTER
    public String getPhone() {
        return phone;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    //SETTER
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return phone + "," + group + "," + name + "," + gender + "," +
                address + "," + birthDate + "," + email;
    }
}
