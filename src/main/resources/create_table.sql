
DROP SCHEMA IF EXISTS public cascade;
CREATE SCHEMA public

    CREATE TABLE public.store
    (
        product_id   serial PRIMARY KEY,
        product_name VARCHAR NOT NULL,
        price        numeric NOT NULL,
        quantity     integer NOT NULL
    );

INSERT INTO public.store (product_id, product_name, price, quantity)
VALUES (1, 'beer', 50, '30'),
       (2, 'cola', 20, '20'),
       (3, 'soap', 30, 10);