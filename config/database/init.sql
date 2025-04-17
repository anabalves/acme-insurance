DO $$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database WHERE datname = 'db_acme_insurance'
   ) THEN
      CREATE DATABASE db_acme_insurance;
END IF;
END
$$;