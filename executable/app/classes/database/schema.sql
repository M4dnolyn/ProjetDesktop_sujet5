CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE medicaments (
    code TEXT PRIMARY KEY,
    designation TEXT NOT NULL,
    quantite INTEGER NOT NULL CHECK (quantite >= 0),
    prix_unitaire REAL NOT NULL CHECK (prix_unitaire >= 0),
    date_peremption TEXT NOT NULL
);
