INSERT IGNORE INTO Etiqueta (id, nombre) VALUES
(1, 'Vegana'),
(2, 'Vegetariana'),
(3, 'Proteica'),
(4, 'Sin Gluten');


INSERT IGNORE INTO Restaurante (id, nombre, descripcion, rutaImagen, calle, numero, localidad, zona) VALUES
(111, 'Pizzería Don Pepe', 'Especialistas en pizza a la piedra', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Siempreviva', 742, 'Springfield', 'Norte'),
(112, 'La Trattoria', 'Comida italiana tradicional', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle Falsa', 123, 'Springfield', 'Centro'),
(113, 'Sushi House', 'Sushi fresco y variado', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Central', 45, 'Springfield', 'Sur'),
(114, 'El Rincón Mexicano', 'Auténtica comida mexicana', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Paseo del Sol', 89, 'Springfield', 'Este'),
(115, 'Bistro Francés', 'Gastronomía francesa fina', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Boulevard Libertad', 12, 'Springfield', 'Centro'),
(116, 'Hamburguesas La 5', 'Hamburguesas gourmet', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Principal', 250, 'Springfield', 'Norte'),
(117, 'Veggie Delight', 'Opciones vegetarianas', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle Verde', 78, 'Springfield', 'Oeste'),
(118, 'Marisquería La Ola', 'Mariscos frescos y sabrosos', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Av. Marina', 56, 'Springfield', 'Este'),
(119, 'Café Central', 'Café y postres deliciosos', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Plaza Mayor', 1, 'Springfield', 'Centro'),
(120, 'Panadería San Juan', 'Pan artesanal y pastelería', '/assets/imagenesRestaurantes/52e9188f-06db-4598-bdd9-47fdbe88c8bd.png', 'Calle Panaderos', 22, 'Springfield', 'Norte');


INSERT IGNORE INTO Restaurante_tiposComida (restaurante_id, tiposComida) VALUES
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


INSERT IGNORE INTO UsuarioNutriya (id, email, password, tokenConfirmacion, confirmado) VALUES
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

INSERT IGNORE INTO UsuarioRestaurante (id, restaurante_id) VALUES
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


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(1, 111, 'Pizza Margherita', 'La clásica pizza con tomate, mozzarella y albahaca.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 9.50),
(2, 111, 'Pizza de Pepperoni', 'Pizza con salsa de tomate, mozzarella y abundante pepperoni.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 11.00),
(3, 111, 'Fugazzetta con Queso', 'Pizza blanca con mucha cebolla y mozzarella.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 10.50);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (1, 2);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (3, 2);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(4, 112, 'Spaghetti Carbonara', 'Pasta al huevo con guanciale, yema de huevo y pecorino.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 14.00),
(5, 112, 'Lasagna Bolognesa', 'Capas de pasta, ragú de carne, bechamel y queso.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 16.50),
(6, 112, 'Risotto de Hongos', 'Arroz Arborio cremoso con variedad de hongos silvestres.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 15.00);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (6, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (6, 2);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(7, 113, 'Nigiri Salmón (2u)', 'Delicado nigiri con salmón fresco.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 8.00),
(8, 113, 'Roll California (8u)', 'Roll con kanikama, palta y pepino.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 12.00),
(9, 113, 'Sashimi Mixto (6u)', 'Variedad de cortes de pescado fresco.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 18.00);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (7, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (7, 4);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (9, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (9, 4);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(10, 114, 'Tacos al Pastor (3u)', 'Tortillas de maíz con carne de cerdo adobada, piña y cilantro.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 13.50),
(11, 114, 'Enchiladas de Pollo', 'Tortillas rellenas de pollo, bañadas en salsa roja y queso.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 14.00),
(12, 114, 'Guacamole con Totopos', 'Aguacate fresco machacado con tomate, cebolla, cilantro y limón, servido con totopos.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 8.50);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (12, 1);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (12, 4);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(13, 115, 'Sopa de Cebolla', 'Clásica sopa francesa gratinada con queso.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 9.00),
(14, 115, 'Coq au Vin', 'Pollo cocido lentamente en vino tinto con champiñones.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 22.00),
(15, 115, 'Crème brûlée', 'Postre cremoso de vainilla con una capa de azúcar caramelizado.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 7.00);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (14, 3);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(16, 116, 'Hamburguesa Clásica', 'Carne 180gr, lechuga, tomate, cebolla, pepinillos y aderezo especial.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 12.00),
(17, 116, 'Hamburguesa Doble Queso', 'Doble carne, doble queso cheddar, panceta y cebolla caramelizada.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 15.00),
(18, 116, 'Hamburguesa Veggie', 'Medallón de lentejas, espinaca, lechuga, tomate y aderezo vegano.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 11.50);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (16, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (17, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (18, 1);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (18, 2);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (18, 3);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(19, 117, 'Ensalada de Quinoa Proteica', 'Quinoa, garbanzos, vegetales de estación, tofu marinado y semillas.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 13.00),
(20, 117, 'Wrap de Vegetales Asados', 'Tortilla integral con vegetales asados, hummus y espinaca fresca.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 11.00),
(21, 117, 'Sopa de Lentejas Casera', 'Sopa reconfortante con lentejas, zanahorias, apio y especias.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 9.50);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 1);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 2);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (19, 4);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (20, 1);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (20, 2);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 1);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 2);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (21, 4);



INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(22, 118, 'Paella de Mariscos', 'Arroz con azafrán, camarones, mejillones, calamares y almejas.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 28.00),
(23, 118, 'Rabas a la Romana', 'Anillos de calamar fritos, tiernos por dentro y crocantes por fuera.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 17.50),
(24, 118, 'Pescado del Día al Horno', 'Pescado fresco del día, acompañado de vegetales al vapor.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 24.00);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (22, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (24, 3);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (24, 4);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(25, 119, 'Tostado de Jamón y Queso', 'Clásico tostado con jamón y queso, en pan de molde.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 6.00),
(26, 119, 'Medialunas (3u)', 'Crujientes medialunas de manteca, ideales para el desayuno.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 4.50),
(27, 119, 'Torta Oreo', 'Porción de torta con base de galletas Oreo, crema y trozos de chocolate.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 8.00);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (27, 2);


INSERT IGNORE INTO Plato (id, restaurante_id, nombre, descripcion, imagen, precio) VALUES
(28, 120, 'Pan de Masa Madre', 'Hogaza de pan artesanal con masa madre, de corteza crujiente.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 7.00),
(29, 120, 'Facturas Mixtas (6u)', 'Surtido de facturas frescas para el desayuno o la merienda.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpg', 9.00),
(30, 120, 'Budín de Limón', 'Suave budín de limón con glaseado cítrico.', '/assets/imagenesPlatos/883bd26e-9967-49dc-8201-b571a0c52d53.jpgg', 8.50);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (28, 2);
INSERT IGNORE INTO Plato_Etiqueta (plato_id, etiqueta_id) VALUES (30, 2);