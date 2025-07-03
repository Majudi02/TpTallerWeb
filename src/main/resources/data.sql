/*
INSERT
IGNORE INTO Etiqueta (id, nombre) VALUES
(1, 'Vegana'),
(2, 'Vegetariana'),
(3, 'Proteica'),
(4, 'Sin Gluten');


INSERT
IGNORE INTO Restaurante (id, nombre, descripcion, rutaImagen, calle, numero, localidad, zona) VALUES
(111, 'Pizzería Don Pepe', 'Especialistas en pizza a la piedra', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Rivadavia', 12345, 'Morón', 'Oeste'),
(112, 'La Trattoria', 'Comida italiana tradicional', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle Lavalle', 305, 'San Justo', 'Oeste'),
(113, 'Sushi House', 'Sushi fresco y variado', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Santa Fe', 4560, 'Palermo', 'Norte'),
(114, 'El Rincón Mexicano', 'Auténtica comida mexicana', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Mitre', 789, 'Avellaneda', 'Sur'),
(115, 'Bistro Francés', 'Gastronomía francesa fina', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle Belgrano', 1200, 'Lomas de Zamora', 'Sur'),
(116, 'Hamburguesas La 5', 'Hamburguesas gourmet', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. del Libertador', 8900, 'Vicente López', 'Norte'),
(117, 'Veggie Delight', 'Opciones vegetarianas', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle Güemes', 321, 'Banfield', 'Sur'),
(118, 'Marisquería La Ola', 'Mariscos frescos y sabrosos', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Costanera Rafael Obligado', 1590, 'CABA', 'Este'),
(119, 'Café Central', 'Café y postres deliciosos', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Corrientes', 850, 'CABA', 'Centro'),
(120, 'Panadería San Juan', 'Pan artesanal y pastelería', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle San Martín', 221, 'Lanús', 'Sur');


INSERT
IGNORE INTO Restaurante_tiposComida (restaurante_id, tiposComida) VALUES
(111, 'Vegetariana'),
(112, 'Vegetariana'),
(113, 'Sin Gluten'),
(114, 'Vegetariana'),
(115, 'Proteica'),
(116, 'Proteica'),
(116, 'Vegetariana'),
(117, 'Vegana'),
(117, 'Vegetariana'),
(117, 'Proteica'),
(117, 'Sin Gluten'),
(118, 'Proteica'),
(118, 'Sin Gluten'),
(119, 'Vegetariana'),
(120, 'Vegetariana');


INSERT
IGNORE INTO UsuarioNutriya (id, email, password, tokenConfirmacion, confirmado) VALUES
(100, 'pizzeria@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(101, 'trattoria@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(102, 'sushi@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(103, 'mexicano@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(104, 'bistro@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(105, 'hamburguesas@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(106, 'veggie@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(107, 'marisqueria@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(108, 'cafe@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true),
(109, 'panaderia@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true);

INSERT
IGNORE INTO UsuarioRestaurante (id, restaurante_id) VALUES
(100, 111),
(101, 112),
(102, 113),
(103, 114),
(104, 115),
(105, 116),
(106, 117),
(107, 118),
(108, 119),
(109, 120);




-- Insertar en la tabla padre (UsuarioNutriya)
INSERT IGNORE INTO UsuarioNutriya (id, email, password, tokenConfirmacion, confirmado)
VALUES (200, 'cliente@nutriya.com', '$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L', null, true);

-- Insertar en la tabla hija (Cliente)
INSERT IGNORE INTO Cliente (id, nombre, edad, pesoActual, pesoDeseado, altura, objetivo)
VALUES (200, 'Juan Pérez', 30, 80, 70, 1.75, 'Bajar de peso');


INSERT IGNORE INTO Cliente_Etiqueta (cliente_id, etiqueta_id)
VALUES
(200, 1);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(1, 111, 'Pizza Margherita', 'La clásica pizza con tomate, mozzarella y albahaca.', '/assets/imagenesPlatos/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg', 9.50, 800, 30, 25, 90),
(2, 111, 'Pizza de Pepperoni', 'Pizza con salsa de tomate, mozzarella y abundante pepperoni.', '/assets/imagenesPlatos/8c5a3d9f-1e2b-4c77-91c4-827ad8fca581.jpg', 11.00, 900, 35, 30, 95),
(3, 111, 'Fugazzetta con Queso', 'Pizza blanca con mucha cebolla y mozzarella.', '/assets/imagenesPlatos/f3d90127-5b63-4b77-a26e-22c0737ab497.jpg', 10.50, 850, 28, 27, 85);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (1, 2);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (3, 2);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(4, 112, 'Spaghetti Carbonara', 'Pasta al huevo con guanciale, yema de huevo y pecorino.', '/assets/imagenesPlatos/b2487be9-d67b-4b19-bf74-871edf0309d2.jpg', 14.00, 700, 25, 28, 80),
(5, 112, 'Lasagna Bolognesa', 'Capas de pasta, ragú de carne, bechamel y queso.', '/assets/imagenesPlatos/ca0fbc67-3f1b-40db-93f2-cfb2196b24fd.jpg', 16.50, 850, 35, 30, 90),
(6, 112, 'Risotto de Hongos', 'Arroz Arborio cremoso con variedad de hongos silvestres.', '/assets/imagenesPlatos/e9af5c3c-787b-4aa0-8e3c-ae4ec0b97195.jpg', 15.00, 600, 20, 25, 75);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (6, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (6, 2);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(7, 113, 'Nigiri Salmón (2u)', 'Delicado nigiri con salmón fresco.', '/assets/imagenesPlatos/43ab1298-97c4-4283-8059-f21b5c5b0941.jpg', 8.00, 300, 18, 12, 30),
(8, 113, 'Roll California (8u)', 'Roll con kanikama, palta y pepino.', '/assets/imagenesPlatos/d5f16cf9-212e-4cd3-90ff-38c9244dfc85.jpg', 12.00, 400, 20, 15, 50),
(9, 113, 'Sashimi Mixto (6u)', 'Variedad de cortes de pescado fresco.', '/assets/imagenesPlatos/9e2b715a-51d8-426a-80e0-b9607f27256c.jpg', 18.00, 350, 25, 10, 20);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (7, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (7, 4);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (9, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (9, 4);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(10, 114, 'Tacos al Pastor (3u)', 'Tortillas de maíz con carne de cerdo adobada, piña y cilantro.', '/assets/imagenesPlatos/17cc3094-e56d-4d87-ae44-02f603473d3e.jpg', 13.50, 600, 30, 20, 50),
(11, 114, 'Enchiladas de Pollo', 'Tortillas rellenas de pollo, bañadas en salsa roja y queso.', '/assets/imagenesPlatos/a1fdb129-b8fc-4a6a-b1fd-64bb8c5369f0.jpg', 14.00, 650, 35, 22, 45),
(12, 114, 'Guacamole con Totopos', 'Aguacate fresco machacado con tomate, cebolla, cilantro y limón, servido con totopos.', '/assets/imagenesPlatos/3e79c0d4-b871-4e1a-9661-998a3e6e9cf9.jpg', 8.50, 400, 8, 35, 25);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (12, 1);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (12, 4);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(13, 115, 'Sopa de Cebolla', 'Clásica sopa francesa gratinada con queso.', '/assets/imagenesPlatos/fd904de0-66a4-4a12-9c02-cfa7b5f53764.jpg', 9.00, 300, 12, 10, 20),
(14, 115, 'Coq au Vin', 'Pollo cocido lentamente en vino tinto con champiñones.', '/assets/imagenesPlatos/58f340c7-c225-43d1-a8a5-838ae6b21f07.jpg', 22.00, 550, 40, 30, 15),
(15, 115, 'Crème brûlée', 'Postre cremoso de vainilla con una capa de azúcar caramelizado.', '/assets/imagenesPlatos/19b20287-c6b1-4dd7-9426-2a7fa6876aa5.jpg', 7.00, 450, 6, 35, 40);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (14, 3);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(16, 116, 'Hamburguesa Clásica', 'Carne 180gr, lechuga, tomate, cebolla, pepinillos y aderezo especial.', '/assets/imagenesPlatos/c3bd4b57-079a-4232-8d61-9b8e39c9ebf2.jpg', 12.00, 700, 45, 35, 40),
(17, 116, 'Hamburguesa Doble Queso', 'Doble carne, doble queso cheddar, panceta y cebolla caramelizada.', '/assets/imagenesPlatos/44e713a1-cc82-4374-a239-a5d1347e5bb1.jpg', 15.00, 900, 55, 40, 45),
(18, 116, 'Hamburguesa Veggie', 'Medallón de lentejas, espinaca, lechuga, tomate y aderezo vegano.', '/assets/imagenesPlatos/b76de70e-8102-4bfa-9a1c-408df1280646.jpg', 11.50, 600, 25, 20, 50);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (16, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (17, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (18, 1);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (18, 2);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (18, 3);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(19, 117, 'Ensalada de Quinoa Proteica', 'Quinoa, garbanzos, vegetales de estación, tofu marinado y semillas.', '/assets/imagenesPlatos/d13a84c2-7fbd-4cb1-90fe-9821fae467a0.jpg', 13.00, 450, 35, 15, 40),
(20, 117, 'Wrap de Vegetales Asados', 'Tortilla integral con vegetales asados, hummus y espinaca fresca.', '/assets/imagenesPlatos/4f5ed7d3-8a16-4a9b-bb93-2227683a6899.jpg', 11.00, 400, 10, 10, 45),
(21, 117, 'Sopa de Lentejas Casera', 'Sopa reconfortante con lentejas, zanahorias, apio y especias.', '/assets/imagenesPlatos/7754ea21-9010-42a8-a09b-a71b23d6636a.jpg', 9.50, 350, 18, 12, 40);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 1);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 2);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 4);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (20, 1);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (20, 2);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 1);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 2);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 4);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(22, 118, 'Paella de Mariscos', 'Arroz con azafrán, camarones, mejillones, calamares y almejas.', '/assets/imagenesPlatos/baedbfcc-013b-41c6-8109-a9f7d6468ce4.jpg', 28.00, 700, 40, 25, 50),
(23, 118, 'Rabas a la Romana', 'Anillos de calamar fritos, tiernos por dentro y crocantes por fuera.', '/assets/imagenesPlatos/e3deffcf-6c8d-4df3-87f2-a34bb8049382.jpg', 17.50, 550, 25, 30, 20),
(24, 118, 'Pescado del Día al Horno', 'Pescado fresco del día, acompañado de vegetales al vapor.', '/assets/imagenesPlatos/aad91ae7-b0a9-417f-85ee-f41109170a7f.jpg', 24.00, 600, 35, 20, 15);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (22, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (24, 3);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (24, 4);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(25, 119, 'Tostado de Jamón y Queso', 'Clásico tostado con jamón y queso, en pan de molde.', '/assets/imagenesPlatos/df5b5908-2806-4fbb-af67-bf54cb84d6bc.jpg', 6.00, 350, 20, 15, 30),
(26, 119, 'Medialunas (3u)', 'Crujientes medialunas de manteca, ideales para el desayuno.', '/assets/imagenesPlatos/355f4145-8509-49b2-9159-2adce711b4f3.jpg', 4.50, 300, 8, 12, 25),
(27, 119, 'Torta Oreo', 'Porción de torta con base de galletas Oreo, crema y trozos de chocolate.', '/assets/imagenesPlatos/ea57d58f-e424-4eb5-8a65-4c2a5191a19e.jpg', 8.00, 450, 6, 30, 50);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (27, 2);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio, calorias, proteinas, grasas, carbohidratos) VALUES
(28, 120, 'Pan de Masa Madre', 'Hogaza de pan artesanal con masa madre, de corteza crujiente.', '/assets/imagenesPlatos/02fd6620-9929-4a87-865f-efc9832c232d.jpg', 7.00, 400, 12, 15, 55),
(29, 120, 'Facturas Mixtas (6u)', 'Surtido de facturas frescas para el desayuno o la merienda.', '/assets/imagenesPlatos/4e5cda47-8676-4c3c-8c9a-fcad30fdfe57.jpg', 9.00, 500, 10, 18, 65),
(30, 120, 'Budín de Limón', 'Suave budín de limón con glaseado cítrico.', '/assets/imagenesPlatos/b09aa9e4-9735-45de-b0c4-c5767b8769a8.jpg', 8.50, 450, 8, 20, 50);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (28, 2);
INSERT
IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (30, 2);

 */