CREATE TABLE IF NOT EXISTS nft_ownership (
    id SERIAL PRIMARY KEY,
    nft_id INT REFERENCES nfts(id) ON DELETE CASCADE,
    previous_owner_id INT REFERENCES users(id) ON DELETE CASCADE,
    new_owner_id INT REFERENCES users(id) ON DELETE CASCADE,
    transfer_date TIMESTAMPTZ DEFAULT NOW()
);
