INSERT INTO app.recipe
VALUES ('228cce54-fc2a-431e-9dfe-eb3a7a52092a',
        '2023-02-24 18:56:00.359944+03',
        '2023-02-24 18:56:00.372946+03',
        'Buckwheat with chicken'),
       ('1fcc1846-9501-48cd-80fe-fb89d45de9f0',
        '2023-02-24 18:56:00.359944+03',
        '2023-02-24 18:56:00.372946+03',
        'Buckwheat with chicken and apple');

INSERT INTO app.product_instance
VALUES ('f6b3cd46-80d8-405a-aa1b-c372c6561c9c',
        '128fbf51-9bdc-4a8e-ab59-2e73cad47551',
        150),
       ('db56456c-5424-4a6f-99dc-334d6dae385c',
        'fff82d3e-74b0-4125-8258-4e66c7ff7cdf',
        75),
       ('3f6bebcc-d2a2-455a-b423-babb0ddc2563',
        '128fbf51-9bdc-4a8e-ab59-2e73cad47551',
        125),
       ('3a9c476c-076d-4cc0-ba78-415171833397',
        'fff82d3e-74b0-4125-8258-4e66c7ff7cdf',
        80),
       ('cc989004-b8cb-4688-9951-37782d000b60',
        '529dc035-56a7-43ad-a9cf-8a870e9dbfde',
        45);

INSERT INTO app.recipe_product
VALUES ('228cce54-fc2a-431e-9dfe-eb3a7a52092a',
        'f6b3cd46-80d8-405a-aa1b-c372c6561c9c'),
       ('228cce54-fc2a-431e-9dfe-eb3a7a52092a',
        'db56456c-5424-4a6f-99dc-334d6dae385c'),
       ('1fcc1846-9501-48cd-80fe-fb89d45de9f0',
        '3f6bebcc-d2a2-455a-b423-babb0ddc2563'),
       ('1fcc1846-9501-48cd-80fe-fb89d45de9f0',
        '3a9c476c-076d-4cc0-ba78-415171833397'),
       ('1fcc1846-9501-48cd-80fe-fb89d45de9f0',
        'cc989004-b8cb-4688-9951-37782d000b60');