CREATE TABLE category (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role_id BIGINT REFERENCES roles(id) NOT NULL
);

CREATE TABLE listings (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          price DOUBLE PRECISION NOT NULL,
                          created_at TIMESTAMP(6) NOT NULL,
                          user_id BIGINT REFERENCES users(id) NOT NULL
);

CREATE TABLE category_listing (
                                  id BIGSERIAL PRIMARY KEY,
                                  category_id BIGINT REFERENCES category(id) NOT NULL,
                                  listing_id BIGINT REFERENCES listings(id) NOT NULL
);


CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          content VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP(6) NOT NULL,
                          listing_id BIGINT REFERENCES listings(id) NOT NULL,
                          user_id BIGINT REFERENCES users(id) NOT NULL
);

CREATE TABLE messages (
                          id BIGSERIAL PRIMARY KEY,
                          sender_id BIGINT REFERENCES users(id) NOT NULL,
                          receiver_id BIGINT REFERENCES users(id) NOT NULL,
                          content VARCHAR(255) NOT NULL,
                          sent_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE promotions (
                            id BIGSERIAL PRIMARY KEY,
                            listing_id BIGINT REFERENCES listings(id) NOT NULL,
                            user_id BIGINT REFERENCES users(id) NOT NULL,
                            payment_amount DOUBLE PRECISION NOT NULL,
                            start_date TIMESTAMP(6) NOT NULL,
                            end_date TIMESTAMP(6) NOT NULL
);

CREATE TABLE ratings (
                         id BIGSERIAL PRIMARY KEY,
                         rating INTEGER NOT NULL,
                         reviewer_id BIGINT REFERENCES users(id) NOT NULL,
                         user_id BIGINT REFERENCES users(id) NOT NULL
);

CREATE TABLE sales_history (
                               id BIGSERIAL PRIMARY KEY,
                               listing_id BIGINT UNIQUE REFERENCES listings(id) NOT NULL,
                               seller_id BIGINT REFERENCES users(id) NOT NULL,
                               buyer_id BIGINT REFERENCES users(id) NOT NULL,
                               sale_date TIMESTAMP(6) NOT NULL
);
