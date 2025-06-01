INSERT INTO ITEM (item_name, item_description, price) VALUES ('Laptop', 'A high-performance laptop', 1200.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Smartphone', 'A latest-gen smartphone', 800.00);
INSERT INTO ITEM (item_name, item_description, price) VALUES ('Headphones', 'Noise-cancelling headphones', 150.00);

-- Insert addresses
INSERT INTO ADDRESS (street, house_number, postal_code) VALUES
                                                            ('Main Street', '1', '1000AA'),
                                                            ('Second Avenue', '5', '2000BB');

INSERT INTO WEBSHOPUSER (username, password, role) VALUES
                                                ('admin', 'pass123', 'ADMIN'),
                                                ('zero', 'cool', 'CUSTOMER');

-- Assign addresses to users
UPDATE WEBSHOPUSER SET address_id = 1 WHERE username = 'admin';
UPDATE WEBSHOPUSER SET address_id = 2 WHERE username = 'zero';
