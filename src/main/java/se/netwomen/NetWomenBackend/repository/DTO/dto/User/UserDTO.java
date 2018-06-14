package se.netwomen.NetWomenBackend.repository.DTO.dto.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDTO {

        @Id
        @GeneratedValue
        private Long id;
        private String firstName;
        private String surName;
        private String userName;
        private String email;
        private String password;
        private String cookie;

        protected UserDTO(){}

        public UserDTO(String firstName, String surName, String userName, String email, String password) {
            this.firstName = firstName;
            this.surName = surName;
            this.userName = userName;
            this.email = email;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getSurName() {
            return surName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getCookie() {
            return cookie;
        }

        public void setCookie(String cookie) {
            this.cookie = cookie;
        }


}
