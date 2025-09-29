-- CREATE TABLE users (
--     id INTEGER PRIMARY KEY,
--     name TEXT NOT NULL,
--     username TEXT NOT NULL UNIQUE,
--     email TEXT,
--     age INTEGER,
--     created_at DATETIME DEFAULT CURRENT_TIMESTAMP
-- );

-- ALTER TABLE users ADD COLUMN status TEXT;

-- DROP TABLE users;

-- id é automatico, posto sozinho
-- INSERT INTO users(name, username)
-- VALUES('hugo', 'hugi');

-- INSERT INTO users(name, username) VALUES('yan', 'yanpi');

-- INSERT INTO users(name, username) VALUES('eu', 'hehe'), ('voce', 'true'), ('adriano', 'sussa');

-- SELECT * FROM users;
-- LIMIT 2, WHERE name='hugo'

-- UPDATE users SET email = 'newemail@gmail.com' WHERE id = 1;

-- DELETE FROM users WHERE id = 2;

-- POSSO TRABALHAR COM FOREIGN KEYS TB MAS FICA PRA DPS OU ENT MANTENHO ASSIM

CREATE TABLE posts(
    id INTEGER PRIMARY KEY,
    user_id INTEGER REFERENCES users(id), -- foreign key
    title TEXT NOT NULL,
    body TEXT NOT NULL
);


-- é bom fazer 2 tables, uma pra animais, uma pra usuarios 