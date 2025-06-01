INSERT INTO ITEM (item_name, item_description, price) VALUES ('Laptop', 'A high-performance laptop', 1200.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Smartphone', 'A latest-gen smartphone', 800.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Headphones', 'Noise-cancelling headphones', 150.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Desktop PC', 'High Performance Desktop PC', 2150.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Mouse', 'High DPI Mouse', 50.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Keyboard', 'Mechanical Keyboard', 100.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Speakers', 'Very Loud Speakers', 150.00);

-- Insert addresses
INSERT INTO ADDRESS (street, house_number, postal_code) VALUES
                                                            ('Main Street', '1', '1000AA'),
                                                            ('Second Avenue', '5', '2000BB');

INSERT INTO WEBSHOP_USER (username, password, role) VALUES
                                                ('admin', 'pass123', 'ADMIN'),
                                                ('zero', 'cool', 'CUSTOMER');

-- Assign addresses to users
UPDATE WEBSHOP_USER SET address_id = 1 WHERE username = 'admin';
UPDATE WEBSHOP_USER SET address_id = 2 WHERE username = 'zero';
