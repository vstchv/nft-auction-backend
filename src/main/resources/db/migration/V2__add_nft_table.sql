CREATE TABLE IF NOT EXISTS nfts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC,
    likes INT,
    image_url TEXT,
    creator INT REFERENCES users(id) ON DELETE CASCADE,
    current_holder INT REFERENCES users(id) ON DELETE CASCADE,
    date_created TIMESTAMPTZ DEFAULT NOW()
);
