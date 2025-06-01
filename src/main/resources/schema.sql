CREATE TABLE ITEM (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      item_name VARCHAR(255) NOT NULL,
                      item_description VARCHAR(255),
                      price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE COMMENT (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         item_id BIGINT NOT NULL,
                         username VARCHAR(255) NOT NULL,
                         content TEXT NOT NULL,
                         FOREIGN KEY (item_id) REFERENCES ITEM(id) ON DELETE CASCADE
);


CREATE TABLE ADDRESS (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         street VARCHAR(255) NOT NULL,
                         house_number VARCHAR(50) NOT NULL,
                         postal_code VARCHAR(50) NOT NULL
);

CREATE TABLE WEBSHOPUSER (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(50) NOT NULL,
                      address_id BIGINT REFERENCES ADDRESS(id) ON DELETE CASCADE
);
