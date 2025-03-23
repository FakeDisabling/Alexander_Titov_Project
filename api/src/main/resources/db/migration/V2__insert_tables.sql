INSERT INTO category (title) VALUES
                                 ('Electronics'),
                                 ('Clothing'),
                                 ('Furniture'),
                                 ('Books'),
                                 ('Toys');


INSERT INTO roles (name) VALUES
                             ('User'),
                             ('Admin');

INSERT INTO users (username, email, phone, password_hash, role_id) VALUES
                                                                       ('john_doe', 'john@example.com', '1234567890', '$2b$12$NqI3Cl.P/jARDt0.aE5ETuQ09ID6C3lrfTDq6NB0wtiOP1Sv8snx6', 1),
                                                                       ('jane_doe', 'jane@example.com', '0987654321', '$2b$12$JQdRXvfYlFz6LFUl9vD1ZeB6yzr7oIrIOUxZuYzxRCtzNXshyVrHC', 2),
                                                                       ('alice_smith', 'alice@example.com', '1122334455', '$2b$12$qa7cjdaFAVxZ9s8nuDnlueTDSddJZ4zWKE7eHIOlzm1iMH15DWbIm', 1),
                                                                       ('bob_brown', 'bob@example.com', '5566778899', '$2b$12$HrtONp1QfkyP23gdsJ3dZOjSdzvjo6iNFaoZb8cecXBjrpMMZ5.Z.', 2),
                                                                       ('charlie_white', 'charlie@example.com', '6677889900', '$2b$12$gM3WDNtBACjOGzstzBdwoen2y3LUjQDi7vadaEpd9Hd52fntuu7k.', 1);


INSERT INTO listings (title, description, price, created_at, user_id) VALUES
                                                                                                    ('Laptop', 'High performance laptop', 1200.50,  NOW(),  1),
                                                                                                    ('T-Shirt', 'Cotton white t-shirt', 19.99, NOW(),  2),
                                                                                                    ('Sofa', 'Comfortable leather sofa', 850.75, NOW(), 3),
                                                                                                    ('Harry Potter', 'Fantasy book', 15.99, NOW(),  4),
                                                                                                    ('Lego Set', 'Construction toy', 45.00, NOW(), 5);

INSERT INTO comments (content, created_at, listing_id, user_id) VALUES
                                                                    ('Great product!', NOW(), 1, 2),
                                                                    ('Looks amazing', NOW(), 3, 1),
                                                                    ('Love this book!', NOW(), 4, 5),
                                                                    ('Perfect gift for kids', NOW(), 5, 3),
                                                                    ('Fast delivery, thank you!', NOW(), 2, 4);

INSERT INTO messages (sender_id, receiver_id, content, sent_at) VALUES
                                                                    (1, 2, 'Hello, is this still available?', NOW()),
                                                                    (2, 1, 'Yes, it is!', NOW()),
                                                                    (3, 4, 'Can you ship this to Canada?', NOW()),
                                                                    (5, 3, 'Is there a discount for bulk purchase?', NOW()),
                                                                    (4, 5, 'How soon can you deliver?', NOW());


INSERT INTO ratings (rating, reviewer_id, user_id) VALUES
                                                       (2, 3, 1),
                                                       (5, 1, 3),
                                                       (4, 2, 5),
                                                       (3, 4, 2),
                                                       (5, 5, 4),
                                                       (1, 2, 3),
                                                       (3, 1, 4),
                                                       (4, 3, 5),
                                                       (2, 5, 2),
                                                       (5, 4, 1),
                                                       (4, 1, 5),
                                                       (3, 2, 4),
                                                       (2, 3, 3),
                                                       (5, 4, 2),
                                                       (1, 5, 1);


INSERT INTO sales_history (listing_id, seller_id, buyer_id, sale_date) VALUES
                                                                           (1, 1, 2, NOW()),
                                                                           (3, 1, 4, NOW()),
                                                                           (5, 1, 1, NOW()),
                                                                           (4, 1, 3, NOW()),
                                                                           (2, 2, 5, NOW());

INSERT INTO category_listing(category_id, listing_id) VALUES
                                                          (1, 1),
                                                          (2, 2),
                                                          (3, 3),
                                                          (4, 4),
                                                          (5, 5),
                                                          (1, 5);

INSERT INTO promotions (listing_id, user_id, payment_amount, start_date, end_date) VALUES
                                                          (1, 1, 50.00, NOW(), NOW() + INTERVAL '30 days'),
                                                          (3, 3, 30.00, NOW(), NOW() + INTERVAL '20 days'),
                                                          (5, 5, 40.00, NOW(), NOW() + INTERVAL '10 days'),
                                                          (4, 4, 100.00, NOW(), NOW() + INTERVAL '15 days'),
                                                          (2, 2, 15.00, NOW(), NOW() + INTERVAL '5 days');