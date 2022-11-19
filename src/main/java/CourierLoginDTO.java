public class CourierLoginDTO {
    private String login;
    private String password;

    public CourierLoginDTO() {
    }

    public CourierLoginDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierLoginDTO fromCourierCreationDTO(CourierCreationDTO courierCreationDTO) {
        return new CourierLoginDTO(courierCreationDTO.getLogin(), courierCreationDTO.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
