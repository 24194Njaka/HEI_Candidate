CREATE USER hei_candidate WITH PASSWORD 'vote1234';
CREATE DATABASE propagade;
\c propagade;



-- 3. Attribution des privilèges de connexion
GRANT CONNECT ON DATABASE postgres TO hei_candidate;

-- 4. Autoriser l'usage du schéma public (où sont vos tables)
GRANT USAGE ON SCHEMA public TO hei_candidate;

-- 5. Attribution des permissions sur les tables existantes
-- (À exécuter APRÈS avoir créé les tables avec votre compte admin)
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE candidate TO hei_candidate;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE vote TO hei_candidate;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE voter TO hei_candidate;

-- 6. TRES IMPORTANT : Privilèges sur les séquences (pour les colonnes SERIAL / AUTO_INCREMENT)
-- Sans cela, votre code Java plantera lors des INSERT car il ne pourra pas incrémenter les ID.
GRANT USAGE, SELECT ON SEQUENCE candidate_id_seq TO hei_candidate;
GRANT USAGE, SELECT ON SEQUENCE vote_line_id_seq TO hei_candidate;
GRANT USAGE, SELECT ON SEQUENCE voter_id_seq TO hei_candidate;

-- 7. Optionnel : Donner les droits par défaut pour les futures tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO hei_candidate;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO hei_candidate;