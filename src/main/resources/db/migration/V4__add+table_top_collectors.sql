CREATE TABLE IF NOT EXISTS top_collectors (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    nft_count INT,
    PRIMARY KEY (user_id)
);
