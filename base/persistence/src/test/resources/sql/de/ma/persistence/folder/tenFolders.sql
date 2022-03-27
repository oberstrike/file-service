DO
$$
    DECLARE
        i integer := 0;
        id text;
    BEGIN
        FOR i IN 1..25 LOOP
                id := 'FCqQJDPuqRvtMGYPX2nA' || i::text;
                RAISE NOTICE '%', id;

                i := + 1; EXIT WHEN i > 10;
                INSERT INTO folder (folder_id, name, version) VALUES (id, 'zrdz', 0);
            END LOOP;

    END
$$;