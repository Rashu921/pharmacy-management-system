package lk.ijse.healthcare.tm;

public class UserTm {
        private String userId;
        private String userName;
        private String password;
        private String employeeId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

        public UserTm() {

        }

        public UserTm(String userId, String userName, String password,String employeeId) {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
            this.employeeId = employeeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

