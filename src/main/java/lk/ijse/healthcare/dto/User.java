package lk.ijse.healthcare.dto;

public class User {
        private String userId;
        private String userName;
        private String password;
        private String employeeId;

        public User() {

        }

        public User(String userId, String userName, String password,String employeeId) {
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
        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

}



